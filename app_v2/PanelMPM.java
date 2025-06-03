import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class PanelMPM extends JPanel
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur ctrl;
	private List<PanelTache> lstPanelTache;

	public PanelMPM( Controleur ctrl, FrameMPM frame)
	{
		this.ctrl = ctrl;
		this.lstPanelTache = new ArrayList<PanelTache>();


		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		for ( int cpt=0; cpt<this.ctrl.getNbTaches(); cpt++)
			this.lstPanelTache.add ( new PanelTache( ctrl, frame, this.ctrl.getTache( cpt ) ) );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */
		for ( PanelTache p : this.lstPanelTache )
		{
			// mettre un setBounds 
			this.add( p );
		}
	}
}
