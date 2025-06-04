import javax.swing.*;

public class FrameSave extends JFrame
{
	private Controleur ctrl;
	
	private JTextField txtNom;

	public FrameSave( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setTitle   ( "Sauvegarder" );
		this.setSize    ( 1000, 700 );
		this.setLocation(  500,  40 );
		
		

		this.add( this.txtNom );
	}
}