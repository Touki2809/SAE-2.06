package application.metier;

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
	private DateFr               D; //date de début du projet

	private List<Tache>          ensTaches;
	//private List<CheminCritique> ensCheminCritiques;


	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public MPM()
	{
		this.D = new DateFr( 1, 6, 2025 ); // date de début du projet

		this.ensTaches          = new ArrayList<Tache>         ();
		//this.ensCheminCritiques = new ArrayList<CheminCritique>();

		this.initMpm();
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public DateFr          getDateDebut (){ return this.D; }
	public List<Tache>     getListTache (){ return this.ensTaches ; }
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

		for ( Tache t : this.ensTaches )
			sRet += t.toString( this.getDateDebut() ) + "\n";
		
			return sRet;
	}

	/*-------------------*/
	/* main              */
	/*-------------------*/
	public static void main(String[] args)
	{
		MPM mpm = new MPM();

		System.out.println( mpm );
	}
}