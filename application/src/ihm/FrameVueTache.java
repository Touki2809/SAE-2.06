package src.ihm;

import src.Controleur;
import src.metier.Tache;

import javax.swing.*;

import java.awt.event.*;

/*---------------------------------*/
/*  Class FrameVueTache            */
/*---------------------------------*/
public class FrameVueTache extends JFrame
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private Controleur ctrl;
	private Tache      tache;

	private  PanelVueTache panelVueTache;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public FrameVueTache( Controleur ctrl, Tache tache )
	{
		this.ctrl = ctrl;
		this.tache = tache;

		this.setTitle   ( "Vue Tâche" );
		//this.setSize    (  400, 700 );
		this.setLocation(  500,  40 );

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.panelVueTache = new PanelVueTache( this.ctrl, this.tache );

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add( this.panelVueTache );

		
		/*-------------------------------*/
		/* Gestion des événements        */
		/*-------------------------------*/
		this.addComponentListener( new GereFramePlateau( this ) );


		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public void setMajPos( int x, int y, int type )
	{
		this.ctrl.setMajPos(  x, y, type );	
	}
	

	/*---------------------------------------*/
	/*        class GereFramePlateau         */
	/*---------------------------------------*/
	private class GereFramePlateau extends ComponentAdapter
	{
		private FrameVueTache frame;

		//Construcueur 
		public GereFramePlateau ( FrameVueTache frame) { this.frame = frame; }

		//Méthode qui permet de savoir si la frame est déplacé
		public void componentMoved ( ComponentEvent e) 
		{ 
			this.frame.setMajPos ( this.frame.getX(), this.frame.getY(), 2 );
		}
	}
}