import javax.swing.*;

public class FrameMPM extends JFrame
{
    private Controleur ctrl;
    private PanelMPM panelMPM;

    public FrameMPM( Controleur ctrl )
    {
        JScrollPane spMPM;

        this.setTitle   ("MPM");
        this.setSize    (1000, 1000);
        this.setLocation(40,40);

        /*-------------------------------*/
        /* Création des composants       */
        /*-------------------------------*/
        this.panelMPM = new PanelMPM( ctrl, this );

        spMPM = new JScrollPane( this.panelMPM );

        /*-------------------------------*/
        /* Positionnement des composants */
        /*-------------------------------*/
        this.setContentPane(spMPM); // <-- Ajoute le JScrollPane à la JFrame

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}