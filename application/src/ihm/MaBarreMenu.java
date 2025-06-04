package src.ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import src.Controleur;

public class MaBarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauvegarder;
	private JMenuItem menuiQuitter;

	public MaBarreMenu( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		/*----------------------------*/
		/* Création des composants    */
		/*----------------------------*/

		// la JMenuBar
		JMenuBar menuBar = new JMenuBar();

		// les JMenu
		JMenu menuFichier   = new JMenu( "Fichier" );

		// les JItemMenu

		this.menuiOuvrir      = new JMenuItem( "Ouvrir"      );
		this.menuiSauvegarder = new JMenuItem( "Sauvegarder" );
		this.menuiQuitter     = new JMenuItem( "Quitter"     );
		

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		// des JItemMenu dans les JMenu
		menuFichier.add( this.menuiOuvrir      );
		menuFichier.add( this.menuiSauvegarder );
		menuFichier.add( this.menuiQuitter     );

		// des JMenu dans la JMenuBar
		menuBar.add( menuFichier );

		this.add( menuBar );

		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		
		this.menuiOuvrir      .addActionListener ( this );
		this.menuiSauvegarder .addActionListener ( this );
		this.menuiQuitter     .addActionListener ( this );

	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.menuiOuvrir      )
		{
			JFileChooser fileChooser = new JFileChooser( "./../data/" );
			if ( fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION )
				this.ctrl.charger ( fileChooser.getSelectedFile().getAbsolutePath() );
			
			this.ctrl.majIhm();
		}
		if ( e.getSource() == this.menuiSauvegarder )
		{
			FrameSave frameSave = new FrameSave( this.ctrl );
		}
		if ( e.getSource() == this.menuiQuitter     )
		{
			System.exit(0);
		}
	}
}