package application.metier;

import iut.algo.Clavier;

import java.io.File;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;



/*---------------------------------*/
/*  Class MPM                      */
/*---------------------------------*/
public class MPM
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private char                 dateRef;
	private DateFr               dateInit; 

	private List<Tache>          ensTaches;
	//private List<CheminCritique> ensCheminCritiques;


	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public MPM( char dateRef, DateFr dateInit )
	{
		this.dateRef  = dateRef;
		this.dateInit = new DateFr( dateInit ); 

		this.ensTaches          = new ArrayList<Tache>         ();
		//this.ensCheminCritiques = new ArrayList<CheminCritique>();

		this.initMpm();
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public char                 getFlagDate  ()     { return this.dateRef;              }
	public DateFr               getDate      ()     { return this.dateInit;             }
	public List<Tache>          getListTache ()     { return this.ensTaches ;           }
	public Tache                getTache(int index) { return this.ensTaches.get(index); }
	//public List<CheminCritique> getListChemin(){ return this.ensCheminCritiques; }



	/*-------------------------------*/
	/* INITIALISATION                */
	/*-------------------------------*/
	private void initMpm()
	{
		String[] ligne;
		String   nom;
		int      duree;
		Tache    tache;
		Scanner  scFic;
		
		try 
		{
			scFic = new Scanner ( new File( "../data/test.data" ), "UTF-8" );
			
			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				nom   = ligne[0].trim();
				duree = Integer.parseInt(ligne[1].trim());
				
				tache = new Tache( nom, duree );

				// Ajout de la tache 
				this.ensTaches.add(tache);

				// a des precedents ?
				if ( ligne.length > 2 && !ligne[2].trim().isEmpty() )
				{
					String[] precedences = ligne[2].split(",");
					
					for (String prec : precedences)
						for ( Tache t : this.ensTaches )
							if ( t.getNom().equals( prec.trim() ) ) tache.ajouterPrc( t );
				}
				
			}

		} catch (Exception e) { e.printStackTrace(); }

		// Appel de dateMin 
		for ( int cpt =0 ; cpt < this.ensTaches.size(); cpt++ )
		{
			Tache t = this.ensTaches.get(cpt);
			
			t.dateTot();
		}


		Tache fin = this.ensTaches.get(this.ensTaches.size()-1);
		fin.setDteTard(fin.getDte_tot());


		// Appel de dateTard 
		for ( int cpt =this.ensTaches.size()-2 ; cpt >= 0 ; cpt-- )
		{
			Tache t = this.ensTaches.get(cpt);
			
			t.dateTard();
		}

	}

	
	/*-------------------*/
	/* toString          */
	/*-------------------*/
	public String toString()
	{
		String sRet = "";

		int nbJourMax = this.getTache( this.ensTaches.size() -1 ).getDte_tard();

		for ( Tache t : this.ensTaches )
			sRet += t.toString( this.getFlagDate(), this.getDate(), nbJourMax ) + "\n";
		
			return sRet;
	}

	/*-------------------*/
	/* main              */
	/*-------------------*/
	public static void main(String[] args)
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

		System.out.println( mpm );
	}
}