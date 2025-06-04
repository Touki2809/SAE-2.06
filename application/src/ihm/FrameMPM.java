package src.ihm;

import src.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*---------------------------------*/
/*  Class FrameMPM                 */
/*---------------------------------*/
public class FrameMPM extends JFrame
{
	private Controleur  ctrl;

	private MaBarreMenu barMenu;
	private PanelMPM    panelMPM;
	private PanelBtn    panelBtn;

	public FrameMPM( Controleur ctrl )
	{
		this.ctrl = ctrl;

		JScrollPane spMPM;

		this.setTitle   ( "MPM"     );
		this.setSize    ( 1000, 700 );
		this.setLocation(  500,  40 );
		this.setLayout(new BorderLayout());

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.barMenu = new MaBarreMenu( ctrl );

		this.panelMPM = new PanelMPM( ctrl, this );
		spMPM = new JScrollPane(this.panelMPM, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.panelBtn = new PanelBtn( this );

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.setJMenuBar( this.barMenu );
		this.add( panelBtn, BorderLayout.NORTH  );
		this.add( spMPM   , BorderLayout.CENTER );

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void maj()
	{
		this.panelMPM.maj();
	}


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

	public void fermer() { this.dispose(); }

	public Controleur getCtrl() { return this.ctrl; }
}