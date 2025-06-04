package src.ihm;

import src.Controleur;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import java.util.List;


/*---------------------------------*/
/*  Class PanelBtn                 */
/*---------------------------------*/
public class PanelBtn extends JPanel implements ActionListener
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private FrameMPM frame;
	private JButton  btnTot;
	private JButton  btnTard;
	private JButton btnCritique;
	private int      cptDate = 0;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public PanelBtn( FrameMPM frameMpm ) 
	{
		this.frame   = frameMpm;
		this.cptDate = 1;


		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		this.btnTot  = new JButton("+ tôt");

		this.btnTard = new JButton("+ tard");
		this.btnTard.setEnabled(false);

		this.btnCritique = new JButton("Chemin critique");
		this.btnCritique.setEnabled(false); // Désactivé par défaut
		this.add(this.btnCritique);
		this.btnCritique.addActionListener(this);


		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */
		this.add(this.btnCritique);
		this.add(this.btnTot);
		this.add(this.btnTard);

		/* ------------------------------ */
		/* Activation des composants      */
		/* ------------------------------ */
		this.btnTot.addActionListener( this);
		this.btnTard.addActionListener( this);
		this.btnCritique.addActionListener(this);
	}

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.btnTot) 
		{
			this.frame.calculerDate( this.cptDate, '-' );
			this.cptDate++;
		}
		else if (e.getSource() == this.btnTard) 
		{
			this.cptDate--;
			this.frame.calculerDate( this.cptDate, '+' );
		}
		else if (e.getSource() == this.btnCritique)
		{
			this.frame.afficherCheminCritique();
		}
		this.majBtn();
	}

	private void majBtn() 
	{
		int maxNiveau = this.frame.getCtrl().getGraphe().getListTache().get(this.frame.getCtrl().getGraphe().getListTache().size() - 1).getNiveau();

		if (  this.btnTot.isEnabled() ) this.btnTot.setEnabled ( this.cptDate <= maxNiveau );
		if ( !this.btnTot.isEnabled() ) this.btnTard.setEnabled( this.cptDate > 0 );

		
		if ( !this.btnTot.isEnabled() && !this.btnTard.isEnabled() ) 
		{
			this.btnCritique.setEnabled( true );
		}

	}
}
