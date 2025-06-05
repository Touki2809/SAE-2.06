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
		int nbNivea = this.tache.getNiveau();
		List<String> lstPrcTemp = new ArrayList<String>();
		for  ( int cptN=0; cptN<nbNivea; cptN++ )
		{
			for ( Tache tPrc : this.ctrl.getGraphe().getListTacheParNiveau().get(cptN) )
			{
				if ( ! lstPrcTemp.contains( tPrc.getNom() ) ) 
				{
					lstPrcTemp.add( tPrc.getNom() );
				}
			}
		}
		JList<String> lstPrc = new JList<>( lstPrcTemp.toArray(new String[0]) );


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

		panelPrc.add( new JLabel( "Précédents : ") );
		panelPrc.add( new JLabel() );
		panelPrc.add( lstPrc );



		panelBtn.add( this.btnSupprimer );
		panelBtn.add( this.btnModifier  );

		this.add( panelSaisie );
		this.add( panelPrc    );
		this.add( panelBtn    );

		

	}
}

