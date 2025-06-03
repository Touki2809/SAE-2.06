package src;

import src.metier.dessin.*;
import src.metier.mpm.*;
import src.metier.*;

import src.ihm.FrameMPM;

import iut.algo.Clavier;

public class Controleur 
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	/* interface */
	private FrameMPM     ihmMPM;

	/* MPM */
	private MPM      graphe;

	/* Modèle de dessin */
	private Metier   metier;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public Controleur() 
	{
		// Saisie pour MPM
		DateFr dateInit;
		char   dateRef;

		System.out.println("Veuillez entrer votre choix :");
		System.out.println("D - Date de début");
		System.out.println("F - Date de fin");
		System.out.print("Votre choix (D/F) : ");
		dateRef = Clavier.lire_char();

		System.out.print("Veuillez entrer la date (format jj/mm/aaaa) : ");
		dateInit = new DateFr(Clavier.lireString());

		// Création du graphe MPM et de son interface
		this.graphe = new MPM(dateRef, dateInit);
		this.ihmMPM = new FrameMPM(this);

		// Initialisation du modèle dessin sans interface associée
		this.metier = new Metier();

	}

	/*-------------------------------*/
	/* Méthodes liées au dessin      */
	/*-------------------------------*/
	public int getNbFigure() 
	{
		return this.metier.getNbFigure();
	}

	public Figure getFigure( int num ) 
	{
		return this.metier.getFigure( num );
	}

	public void ajouterFigure( Tache t, char fig, int x, int y, int tx, int ty ) 
	{
		this.metier.ajouterFigure( t, fig, x, y, tx, ty);
	}

	public Integer getIndiceFigure( int x, int y ) 
	{
		return this.metier.getIndiceFigure(x, y);
	}

	public void deplacerFigure( int numFig, int x, int y ) 
	{
		this.metier.deplacerFigure(numFig, x, y);
	}


	/*-------------------------------*/
	/* Méthodes liées à MPM          */
	/*-------------------------------*/
	public Tache getTache(int index) 
	{
		return this.graphe.getListTache().get(index);
	}

	public int getNbTaches() 
	{
		return this.graphe.getListTache().size();
	}

	/*-------------------------------*/
	/* Main                          */
	/*-------------------------------*/
	public static void main(String[] args) 
	{
		new Controleur();
	}
}
