package src.ihm;

import javax.swing.*;
import src.Controleur;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

public class FrameSave extends JFrame implements ActionListener
{
	private Controleur ctrl;
	
	private JTextField txtNom;
	private JButton    btnNom;

	public FrameSave( Controleur ctrl )
	{
		JPanel panelTmp;

		this.ctrl = ctrl;

		this.setTitle   ( "Sauvegarder" );
		this.setLayout( new GridLayout( 2, 1 ) );
		
		this.txtNom = new JTextField( "Sauvegarde", 20 );
		this.btnNom = new JButton( "Enregistrer" );

		
		panelTmp = new JPanel();
		panelTmp.add( new JLabel("Nom fichier : ") );
		panelTmp.add( this.txtNom );
		this.add( panelTmp );

		panelTmp = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
		panelTmp.add( this.btnNom );
		this.add( panelTmp );

		this.pack();
		this.setLocation( (int)this.ctrl.getPosIhmSave().getX() - this.getWidth () / 2,
		                  (int)this.ctrl.getPosIhmSave().getY() - this.getHeight() / 2 );
						  
		this.btnNom.addActionListener(this);
		
		this.setVisible( true );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == this.btnNom )
		{
			if ( this.ctrl.sauvegarder( this.txtNom.getText().trim()) )
			{
				JOptionPane.showMessageDialog( this, "Sauvegarde réussie", "Information", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
			else 
			{
				JOptionPane.showMessageDialog( this, "Existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}