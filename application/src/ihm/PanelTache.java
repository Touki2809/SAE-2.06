package src.ihm;

import src.Controleur;
import src.metier.Tache;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Color;



public class PanelTache extends JPanel
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur ctrl;
	private Tache      tache;
	private FrameMPM   frame;

	private JLabel     lblDteTot;
	private JLabel     lblDteTard;

	private boolean estCritique = false;
	
	public PanelTache( Controleur ctrl, FrameMPM frame, Tache tache )
	{
		this.ctrl  = ctrl;
		this.frame = frame;
		this.tache = tache;

		this.setLayout( new GridLayout( 2, 1 ) );

		JLabel lblTitre;
		JPanel panelDate;
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		lblTitre        = new JLabel( tache.getNom(), SwingConstants.CENTER );
		this.lblDteTot  = new JLabel( " ", SwingConstants.CENTER  );
		this.lblDteTard = new JLabel( " ", SwingConstants.CENTER  );
		this.majAffichage();

		lblTitre       .setBorder( new LineBorder( Color.BLACK, 1 ) );
		this.lblDteTot .setBorder( new LineBorder( Color.BLACK, 1 ) );
		this.lblDteTard.setBorder( new LineBorder( Color.BLACK, 1 ) );

		this.lblDteTot .setForeground( new Color(   0, 128, 128 ) );
		this.lblDteTard.setForeground( new Color( 255,  25,  25 ) );

		panelDate = new JPanel( new GridLayout( 1, 2 ) );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */
		this.add( lblTitre );
		this.add( panelDate     );
		panelDate.add( this.lblDteTot  );
		panelDate.add( this.lblDteTard );

		
		/* ------------------------------ */
		/* Activation des composants      */
		/* ------------------------------ */
		GereSouris gereSouris = new GereSouris();
		this.addMouseListener(gereSouris);
		this.addMouseMotionListener(gereSouris);
	}

	public Tache  getTache() { return this.tache   ; }
	
	public void majAffichage() 
	{
		if ( this.tache.getDte_tot()  == -1 ) this.lblDteTot  .setText( " " );
		else                                  this.lblDteTot  .setText( this.tache.getDte_tot() +"");

		if ( this.tache.getDte_tard() == -1 ) this.lblDteTard .setText( " " );
		else                                  this.lblDteTard .setText( this.tache.getDte_tard()+"" );

	}

	public void setCritique(boolean estCritique) 
	{
        this.estCritique = estCritique;
        if (estCritique)  this.setBorder( new LineBorder(Color.RED,   3 ) );
        else              this.setBorder( new LineBorder(Color.BLACK, 0 ) );
    }

    public boolean isCritique()
    {
        return this.estCritique;
    }

	/* ------------------------------ */
	/* Gestion souris, class interne  */
	/* ------------------------------ */
	private class GereSouris extends MouseAdapter 
	{
		private int posX;
		private int posY;
		private boolean selectionee = false;

		public void mousePressed( MouseEvent e ) 
		{
			selectionee = true;
			this.posX   = e.getX();
			this.posY   = e.getY();
		}

		public void mouseReleased( MouseEvent e ) 
		{
			selectionee = false;
		}

		public void mouseDragged(MouseEvent e) 
		{
			if ( selectionee ) 
			{
				int nvPosX = getX() + e.getX() - this.posX;
				int nvPosY = getY() + e.getY() - this.posY;

				PanelTache.this.setLocation( nvPosX, nvPosY );
				
				PanelTache.this.frame.repaint();
			}
		}
	}

}
