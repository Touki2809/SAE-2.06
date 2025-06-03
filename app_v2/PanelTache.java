import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;



public class PanelTache extends JPanel
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur ctrl;
	private Tache      tache;
	private FrameMPM   frame;

	private JLabel     lblTitre;
	private JLabel     lblDteTot;
	private JLabel     lblDteTard;


	public PanelTache( Controleur ctrl, FrameMPM frame, Tache tache )
	{
		this.ctrl  = ctrl;
		this.frame = frame;
		this.tache = tache;

		this.setLayout( new GridLayout( 2, 1 ) );

		JPanel panelDate;
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.lblTitre   = new JLabel( tache.getNom     ()    , SwingConstants.CENTER );
		this.lblDteTot  = new JLabel( tache.getDte_tot () +"", SwingConstants.CENTER );
		this.lblDteTard = new JLabel( tache.getDte_tard() +"", SwingConstants.CENTER );

		panelDate = new JPanel( new GridLayout( 1, 2 ) );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */
		this.add( this.lblTitre );
		this.add( panelDate     );
		panelDate.add( this.lblDteTot  );
		panelDate.add( this.lblDteTard );		

		/* ------------------------------ */
		/* Activation des composants      */
		/* ------------------------------ */

		
	}
}
