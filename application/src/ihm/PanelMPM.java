package src.ihm;

import src.Controleur;
import src.metier.mpm.*;
import src.metier.dessin.*;
import src.metier.dessin.Rectangle;
import src.metier.*;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class PanelMPM                 */
/*---------------------------------*/
public class PanelMPM extends JPanel
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur       ctrl;
	private FrameMPM         frame;
	private Graphics2D g2;

	

	public PanelMPM( Controleur ctrl, FrameMPM frame)
	{
		this.ctrl     = ctrl;
		this.frame    = frame;
		this.setLayout(null);
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.initFigures();
	}

	/*-------------------------------*/
	/* Paint                         */
	/*-------------------------------*/
	public void paintComponent(Graphics g)
	{
		Figure fig;

		super.paintComponent(g);

		g2 = (Graphics2D) g;
		
				
		// Dessiner l'ensemble des figures
		for ( int cptFigure=0; cptFigure<this.ctrl.getNbFigure(); cptFigure++)
		{
			fig = this.ctrl.getFigure(cptFigure);

			if ( fig instanceof Rectangle)
			{
				int x = fig.getCentreX() - fig.getTailleX()/2;
				int y = fig.getCentreY() - fig.getTailleY()/2;
				int w = fig.getTailleX();
				int h = fig.getTailleY();

				// Rectangle principal
				g2.drawRect(x, y, w, h);

				// Ligne horizontale sous le nom (à 1/3 de la hauteur)
				int yH = y + h/3;
				g2.drawLine(x, yH, x + w, yH);

				// Ligne verticale au centre (pour séparer dt1/dt2)
				int xV = x + w/2;
				g2.drawLine(xV, yH, xV, y + h);

				// Nom centré dans la partie haute
				String nom = fig.getTache().getNom();
				int nomWidth = g2.getFontMetrics().stringWidth(nom);
				g2.drawString(nom, x + (w-nomWidth)/2, y + h/6 + g2.getFontMetrics().getAscent()/2);

				// dt1 à gauche, dt2 à droite, centrés verticalement dans la partie basse
				String dt1 = String.valueOf( fig.getTache().getDte_tot () );
				String dt2 = String.valueOf( fig.getTache().getDte_tard() );
				int dtY = yH + (2*h/3 + g2.getFontMetrics().getAscent())/2 - h/6;

				int dt1Width = g2.getFontMetrics().stringWidth(dt1);
				int dt2Width = g2.getFontMetrics().stringWidth(dt2);

				g2.drawString(dt1, x + (w/4) - dt1Width/2, dtY);
				g2.drawString(dt2, x + (3*w/4) - dt2Width/2, dtY);
			}
			
		}
	}


	/*-------------------------------*/
	/* Initialisation des figures     */
	/*-------------------------------*/
	public void initFigures()
	{
		// On ajoute les figures du graphe MPM
		for ( int cptTache=0; cptTache<this.ctrl.getNbTaches(); cptTache++)
		{
			Tache t = this.ctrl.getTache( cptTache );

			//	public Rectangle ( Tache tache, int centreX, int centreY, int tailleX, int tailleY )
			//                                  
			
		}
	}

	/*-----------------------------------------*/
	/* Définition de la classe interne Adapter */
	/*-----------------------------------------*/
	private class GereSouris extends MouseAdapter
	{
		Integer numFigure;
		int     posX, posY;

		public void mousePressed (MouseEvent e)
		{
			this.numFigure = PanelMPM.this.ctrl.getIndiceFigure ( e.getX(), e.getY() );
			this.posX = e.getX();
			this.posY = e.getY();
		}

		public void mouseDragged (MouseEvent e)
		{
			if ( this.numFigure != null )
			{
				PanelMPM.this.ctrl.deplacerFigure( this.numFigure, e.getX()-this.posX, e.getY()-this.posY );

				this.posX = e.getX();
				this.posY = e.getY();
				
				PanelMPM.this.repaint();
			}
		}
	}
}