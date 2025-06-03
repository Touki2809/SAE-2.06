package src;

import src.metier.MPM;
import src.metier.DateFr;
import src.metier.Tache;
import src.ihm.FrameMPM;

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
		//System.out.println("A - Auto"                     );
		System.out.print  ("Votre choix (D/F) : "         );
		dateRef = Clavier.lire_char();

		if (dateRef != 'A' ) 
		{
			System.out.print("Veuillez entrer la date  (format jj/mm/aaaa) : ");
				dateInit = new DateFr( Clavier.lireString() );
		}


		this.graphe = new MPM( dateRef, dateInit );

		
		this.ihm = new FrameMPM( this );
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/

	public MPM getGraphe() 
	{
		return this.graphe;
	}

	public void calculerDate(int niveau, char type)
	{
		if (type == '-')
		{
			this.graphe.calculerDateNiveauTot(niveau);
		}
		else if (type == '+')
		{
			this.graphe.calculerDateNiveauTard(niveau);
		}
	}

	/*------------------*/
	/* MAIN             */
	/*------------------*/
	public static void main(String[] args) 
	{
		new Controleur();
	}

}
