package application;

import application.metier.MPM;
import application.metier.DateFr;
import application.metier.Tache;

import application.ihm.ihmCUI;


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
        this.graphe = new MPM();
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
