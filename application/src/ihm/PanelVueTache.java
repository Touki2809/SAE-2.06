package src.ihm;

import src.Controleur;
import src.metier.Tache;

import javax.swing.*;
import java.util.List;
import java.awt.GridLayout;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class PanelVueTache            */
/*---------------------------------*/
public class PanelVueTache extends JPanel 
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur ctrl;
	private Tache      tache;

	private JTextField txtDuree;
	private JButton    btnSupprimer;
	private JButton    btnModifier;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public PanelVueTache( Controleur ctrl, Tache tache )
	{
		this.ctrl  = ctrl;
		this.tache = tache;
		this.setLayout(  new GridLayout( 4, 1)  );

		JPanel panelSaisie, panelPrc, panelSvt, panelBtn;

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		// panels
		panelSaisie = new JPanel( new GridLayout( 2, 2 ) );
		panelPrc    = new JPanel( );
		panelSvt    = new JPanel( );
		panelBtn    = new JPanel( );

		// Nom
		JTextField txtNom = new JTextField( this.tache.getNom() );
		txtNom.setEditable(false);
		
		//Durée
		this.txtDuree     = new JTextField( this.tache.getDuree());

		// prc
		String[] lstPrcTemp = new String[ this.tache.getNbPrc() ];
		for ( int cptT= 0; cptT<lstPrcTemp.length; cptT++ )
		{
			lstPrcTemp[cptT] = this.tache.getlstPrc().get( cptT ).getNom();
		}
		JList<String> lstPrc = new JList<>( lstPrcTemp );


		// Boutons
		this.btnSupprimer = new JButton( "Supprimer" );
		this.btnModifier  = new JButton( "Modifier" );

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		panelSaisie.add( new JLabel( "Nom : "  , SwingConstants.RIGHT ) );
		panelSaisie.add( txtNom );
		panelSaisie.add( new JLabel( "Durée : ", SwingConstants.RIGHT ) );
		panelSaisie.add( this.txtDuree );

		panelPrc.add( new JLabel( "Précédentes : ") );
		panelPrc.add( lstPrc );
		


		panelBtn.add( this.btnSupprimer );
		panelBtn.add( this.btnModifier  );

		this.add( panelSaisie );
		this.add( panelPrc    );
		this.add( panelBtn    );

		

	}

}
