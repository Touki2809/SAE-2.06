package src.ihm;

import src.Controleur;
import javax.swing.*;

/*---------------------------------*/
/*  Class FrameMPM                 */
/*---------------------------------*/
public class FrameMPM extends JFrame
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
    private Controleur ctrl;
    private PanelMPM   panelMPM;

	/*---------------------------------*/
	/*  Constructeur                   */
	/*---------------------------------*/
    public FrameMPM(Controleur ctrl)
    {
        this.ctrl     = ctrl;

        this.setTitle("MPM");
        this.setSize(1000, 800);
        this.setLocation(50, 50);
		
		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.panelMPM = new PanelMPM( this.ctrl, this );
		

		/*-------------------------------*/
		/* Ajout des composants          */
		/*-------------------------------*/
		this.add( this.panelMPM );

		/*-------------------------------*/
		this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

    public void maj()
    {
        panelMPM.repaint();
    }
}
