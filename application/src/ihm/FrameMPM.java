package src.ihm;

import src.Controleur;

import javax.swing.*;

import java.awt.BorderLayout;

import java.awt.event.*;
import java.awt.Point;

/*---------------------------------*/
/*  Class FrameMPM                 */
/*---------------------------------*/
public class FrameMPM extends JFrame
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private Controleur  ctrl;

	private MaBarreMenu barMenu;
	private PanelMPM    panelMPM;
	private PanelBtn    panelBtn;
	private JScrollPane spMPM;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public FrameMPM( Controleur ctrl )
	{
		this.ctrl = ctrl;

		JScrollPane spMPM;

		this.setTitle   ( "MPM"     );
		//this.setSize    ( 1000, 700 );
		//this.setLocation(  500,  40 );
		
		this.setLayout( new BorderLayout() );

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.barMenu = new MaBarreMenu( ctrl );

		this.panelMPM = new PanelMPM( ctrl, this );

		spMPM = new JScrollPane( this.panelMPM , 
		                         JScrollPane.VERTICAL_SCROLLBAR_ALWAYS  ,
		                         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );

		this.panelBtn = new PanelBtn( this );

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.setJMenuBar( this.barMenu );
		this.add( panelBtn, BorderLayout.NORTH  );
		this.add( spMPM   , BorderLayout.CENTER );

		/*-------------------------------*/
		/* Gestion des événements        */
		/*-------------------------------*/
		this.addComponentListener( new GereFramePlateau( this ) );

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		spMPM.getVerticalScrollBar().setValue( 4827 );

	}

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public void fermer () { this.dispose();     }
	public void maj    () { this.panelMPM.maj();}

	public void calculerDate(int cpt, char type)
	{
		this.ctrl.calculerDate( cpt, type );

		this.maj();
	}

	public void afficherCheminCritique() 
	{
		this.ctrl.getGraphe().calculerCheminCritique();
		this.panelMPM.afficherCheminCritique();
	}

	public void setMajPos ( int x, int y, int type )
	{
		this.ctrl.setMajPos( x, y, type );
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public Controleur getCtrl() { return this.ctrl; }

	public int getPosX() { return this.getX(); }
	public int getPosY() { return this.getY(); }



	/*---------------------------------------*/
	/*        class GereFramePlateau         */
	/*---------------------------------------*/
	private class GereFramePlateau extends ComponentAdapter
	{
		private FrameMPM frame;

		//Construcueur 
		public GereFramePlateau ( FrameMPM frame) { this.frame = frame; }

		//Méthode qui permet de savoir si la frame est déplacé
		public void componentMoved ( ComponentEvent e) 
		{ 
			this.frame.setMajPos ( this.frame.getX(), this.frame.getY(), 1 );
		}
	}
}