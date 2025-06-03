
import iut.algo.Clavier;

import java.io.File;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

import java.util.Queue;
import java.util.LinkedList;

/*---------------------------------*/
/*  Class MPM                      */
/*---------------------------------*/
public class MPM
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private static char          dateRef;
	private static DateFr        dateInit; 

	private List<Tache>          ensTaches;
	//private List<CheminCritique> ensCheminCritiques;


	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public MPM( char dateRef, DateFr dateInit )
	{
		MPM.dateRef  = dateRef;
		MPM.dateInit = new DateFr( dateInit ); 

		this.ensTaches          = new ArrayList<Tache>         ();
		//this.ensCheminCritiques = new ArrayList<CheminCritique>();

		this.initMpm();
		this.initDteTot();
		this.initDteTard();
		this.intiNiveau();
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public char                 getFlagDate  ()     { return this.dateRef;              }
	public DateFr               getDate      ()     { return this.dateInit;             }
	public List<Tache>          getListTache ()     { return this.ensTaches ;           }
	public Tache                getTache(int index) { return this.ensTaches.get(index); }
	//public List<CheminCritique> getListChemin(){ return this.ensCheminCritiques; }


	public int calculerNiveau( Tache tache ) 
	{
		//Déja def ?
		if ( tache.getNiveau() != -1 ) return -1;

		// DÉBUT  ?
		if ( tache.getNbPrc() == 0 ) tache .setNiveau( 0 );
		

		// min ?
		int niveau = -1;
		for ( Tache tPrc : tache.getlstPrc() ) 
		{
			int niveauPrc = calculerNiveau( tPrc );

			if ( niveauPrc != -1 && niveauPrc < niveau ) 
			{
				niveau = niveauPrc;
			}
		}
	
		tache.setNiveau(niveau);

		System.out.println( niveau );

		return niveau;
	}	


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

		this.ensTaches.add ( new Tache( "DÉBUT", 0, 0 ) );

		try 
		{
			scFic = new Scanner ( new File( "test.data" ), "UTF-8" );
			
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

		this.ensTaches.add ( new Tache( "FIN", 0 ) );
		this.ensTaches.get( this.ensTaches.size()-1 ).ajouterPrc( this.ensTaches.get( this.ensTaches.size()-2 ));
	}

	public void initDteTot()
	{
		Tache tSvt;
		
		for ( Tache t : this.ensTaches )
		{
			for ( int cpt=0; cpt<t.getNbSvt(); cpt++)
			{
				tSvt = t.getlstSvt().get( cpt );

				tSvt.setDateTot( (t.getDte_tot() +  t.getDuree()) );
			}
		}
	}
	
	public void initDteTard()
	{
		Tache t,tPrc;

		t = this.ensTaches.get(this.ensTaches.size() -1);

		t.setDateTard( t.getDte_tot() );

		for(int cpt = this.ensTaches.size() -1; cpt >= 0 ; cpt--)
		{
			t = this.ensTaches.get(cpt);

			for(int cptT= 0; cptT < t.getNbPrc(); cptT++)
			{
				tPrc = t.getlstPrc().get(cptT);

				tPrc.setDateTard( t.getDte_tard() - tPrc.getDuree() );
			}
		}
	}
	
	public void intiNiveau ()
	{
		for ( Tache t : this.ensTaches )
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