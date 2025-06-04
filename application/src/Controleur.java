package src;

import src.metier.MPM;
import src.metier.DateFr;
import src.metier.Tache;

import src.ihm.FrameMPM;
import src.ihm.FrameVueTache;
import src.ihm.PanelVueTache;

import java.awt.Panel;
import java.awt.Point;

import iut.algo.Clavier;

/*---------------------------------*/
/*  Class Controleur               */
/*---------------------------------*/
public class Controleur 
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private MPM      graphe;
	private FrameMPM ihm;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public Controleur() 
	{
		DateFr dateInit = new DateFr();
		char   dateRef;


		//Saisie 
		System.out.println("Veuillez entrer votre choix :");
		System.out.println("D - Date de début"            );
		System.out.println("F - Date de fin"              );
		//System.out.println("A - Auto"                     ); //pour skip
		System.out.print  ("Votre choix (D/F) : "         );
		dateRef = Clavier.lire_char();

		if (dateRef != 'A' ) 
		{
			System.out.print("Veuillez entrer la date  (format jj/mm/aaaa) : ");
			try { dateInit = new DateFr( Clavier.lireString() ); } 
			catch (Exception e)  
			{
				 System.out.println("Erreur de saisie, défaut  " + dateInit.toString( "jj/mm/aaaa" ) ); 
			}
		}

		this.graphe = new MPM( dateRef, dateInit );
		this.ihm    = new FrameMPM( this );
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public MPM getGraphe() 
	{
		return this.graphe;
	}

	/*-------------------------------*/
	/* Modificateurs                */
	/*-------------------------------*/
	public Point getPosIhmSave()
	{
		return new Point( this.ihm.getX() + this.ihm.getWidth () / 2 ,
		                  this.ihm.getY() + this.ihm.getHeight() / 2  );
	}

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public void calculerDate(int niveau, char type)
	{
		if (type == '-') this.graphe.calculerDateNiveauTot(niveau);
		else             this.graphe.calculerDateNiveauTard(niveau);
	}

	public void majIhm()
	{
		this.ihm.fermer();
		this.ihm = new FrameMPM( this );
	}

	public void charger( String chemin )
	{
		this.graphe.charger( chemin );
	}

	public boolean sauvegarder( String chemin )
	{
		return this.graphe.sauvegarder( chemin );
	}

	public void afficherVueTache ( Tache t )
	{
		new FrameVueTache ( this, t );
	}

	/*------------------*/
	/* MAIN             */
	/*------------------*/
	public static void main(String[] args) 
	{
		new Controleur();
	}

}
