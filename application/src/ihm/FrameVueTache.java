package src.ihm;

import src.Controleur;
import src.metier.Tache;

import javax.swing.*;

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
		this.setSize    ( 1000, 700 );
		this.setLocation(  500,  40 );

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.panelVueTache = new PanelVueTache( this.ctrl, this.tache );

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add( this.panelVueTache );


		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}