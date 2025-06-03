package application;

import application.metier.*;
import application.ihm.ihmCUI;

import iut.algo.Clavier;

/*---------------------------------*/
/*  Class Controleur               */
/*---------------------------------*/
public class Controleur 
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
    private MPM    graphe;
    private ihmCUI ihmCUI;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
    public Controleur() 
	{
		DateFr dateInit;
		char   dateRef;


		//Saisie 
		System.out.println("Veuillez entrer votre choix :");
        System.out.println("D - Date de début"            );
        System.out.println("F - Date de fin"              );
		System.out.print  ("Votre choix (D/F) : "         );
		dateRef = Clavier.lire_char();

		System.out.print("Veuillez entrer la date  (format jj/mm/aaaa) : ");
		dateInit = new DateFr( Clavier.lireString() );



		MPM mpm = new MPM( dateRef, dateInit );

		this.ihmCUI = new ihmCUI(this);
    }

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public DateFr getDateDebut() 
	{
	    return this.graphe.getDateDebut();
	}

	public Tache getTache(int index) 
	{
	    return this.graphe.getListTache().get(index);
	}

	public int getNbTaches() 
	{
	    return this.graphe.getListTache().size();
	}



	/*------------------*/
	/* Affichage        */
	/*------------------*/
	public void toSring ()
	{
		this.ihmCUI.afficherMPM();;
	}


	/*------------------*/
	/* MAIN             */
	/*------------------*/
    public static void main(String[] args) 
	{
		Controleur controleur = new Controleur();
		controleur.toSring();
	}

}
