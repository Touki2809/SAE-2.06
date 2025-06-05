package src;

import src.metier.MPM;
import src.metier.DateFr;
import src.metier.Tache;

import src.ihm.FrameMPM;
import src.ihm.FrameVueTache;
import src.ihm.PanelVueTache;

import java.awt.Dimension;
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
	private MPM           graphe;
	private FrameMPM      ihm;
	private FrameVueTache frameTache;

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

		this.graphe     = new MPM( dateRef, dateInit );
		this.ihm        = new FrameMPM( this );
		this.frameTache = null;
		
		this.setInitPos();
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
		this.setInitPos();
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
		this.frameTache = new FrameVueTache ( this, t );
		this.setInitPos();
	}


	/*-------------------------------*/
	/* Positionnement des Frames     */
	/*-------------------------------*/
	public void setInitPos(  )
	{
		Dimension tailleEcran;
		int centreX, centreY;
		int h;
		int l1, l2;
		int aX, bX;
		int y;

		tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		centreX   = (int) ( tailleEcran.getWidth () - tailleEcran.getWidth () % 20 ) / 2;
		centreY   = (int) tailleEcran.getHeight() / 2;
	
		h = 700;

		l1 = 1000;
		l2 =  400;

		aX = centreX - ( l1 * 2/3 ) ;
		bX = aX + l1 + 20;

		y = centreY - h / 2;

		this.ihm.setLocation( aX, y );
		this.ihm.setSize    ( l1, h );

		if ( this.frameTache != null )
		{
			this.frameTache.setLocation( bX, y );
			this.frameTache.setSize    ( l2, h );
		}
	}

	public void setMajPos( int x, int y, int frame )
	{
		if ( frame == 1 )
		{
			if ( this.frameTache != null )  this.frameTache.setLocation( x + 1020, y );
					
		} 
		if ( frame == 2 ) 
		{
			if ( this.ihm != null ) this.ihm.setLocation( x - 1020, y );
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
