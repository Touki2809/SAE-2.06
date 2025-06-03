package src.metier;

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
	private List<CheminCritique> ensCheminCritiques;


	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public MPM( char dateRef, DateFr dateInit )
	{
		MPM.dateRef  = dateRef;
		MPM.dateInit = new DateFr( dateInit ); 

		this.ensTaches          = new ArrayList<Tache>         ();
		this.ensCheminCritiques = new ArrayList<CheminCritique>();

		this.initMpm();
		// this.initDteTot();
		// this.initDteTard();
		this.intiNiveau();

	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public static char          getFlagDate  ()     { return MPM.dateRef; }
	public static DateFr        getDate      ()     { return MPM.dateInit; }
	public List<Tache>          getListTache ()     { return this.ensTaches ;           }
	public Tache                getTache(int index) { return this.ensTaches.get(index); }
	public List<CheminCritique> getListCheminCritique() {
        return this.ensCheminCritiques;
    }


	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public int calculerNiveau( Tache tache ) 
	{
		//Déja def ?
		if ( tache.getNiveau() != -1 ) return tache.getNiveau();

		if ( tache.getNom().equals( "DÉBUT" ) ) { tache.setNiveau( 0 ); return 0; }

		// DÉBUT  ?
		if ( tache.getNbPrc() == 0 ) tache .setNiveau( 0 );

		// min ?
		int niveau = 10000;
		for ( Tache tPrc : tache.getlstPrc() ) 
		{
			int niveauPrc = calculerNiveau( tPrc )+1;

			if ( niveauPrc != -1 && niveauPrc < niveau ) 
			{
				niveau = niveauPrc;
			}
		}
	
		tache.setNiveau(niveau);

		return niveau;
	}	

	public void calculerDateNiveauTot(int niveau)
	{
		for ( Tache t : this.ensTaches )
		{
			if ( t.getNiveau() == niveau ) 
			{
				int max = 0;
				for ( Tache tPrc : t.getlstPrc() ) 
				{
					if ( tPrc.getDte_tot() + tPrc.getDuree() > max )
						max = tPrc.getDte_tot() + tPrc.getDuree();
				}

				t.setDteTot( max );
			}
		}
	}

	public void calculerDateNiveauTard(int niveau) 
	{
		for (Tache t : this.ensTaches) 
		{
			if ( t.getNiveau() == niveau ) 
			{
				int min = 10000;
				for ( Tache tPrc : t.getlstSvt() ) 
				{
					if ( tPrc.getDte_tard() - t.getDuree() < min )
						min = tPrc.getDte_tard() - t.getDuree();
				}

				if ( min == 10000 ) t.setDteTard(t.getDte_tot());
				else                t.setDteTard( min );
			}
		}
	}


	public void calculerCheminCritique()
    {
        List<CheminCritique> pileChemin = new ArrayList<CheminCritique>();
        Tache                tacheActu  = this.ensTaches.get(0);
        CheminCritique       cheminActu = new CheminCritique();
        
        pileChemin.add(cheminActu);
        cheminActu.ajouterTache(tacheActu);
        
        while (!pileChemin.isEmpty())
        {
            if (tacheActu.getNbSvt()!= 0)
            {
                for ( Tache t : tacheActu.getlstSvt() )
                {
                    if(t.calculerMarge() == 0)
                    {
                        pileChemin.remove(cheminActu);
                        CheminCritique cheminTmp = new CheminCritique(cheminActu);
                        cheminTmp.ajouterTache(t);
                        pileChemin.add(cheminTmp);
                    }
                }
            }
            else
            {
                this.ensCheminCritiques.add(cheminActu);
                pileChemin.remove(cheminActu);
            }
            
            if (!pileChemin.isEmpty()) cheminActu = pileChemin.get(pileChemin.size()-1);
            if (!cheminActu.getCheminCritique().isEmpty())
                tacheActu  = cheminActu.getCheminCritique().get(cheminActu.getCheminCritique().size()-1);
        }
        
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
			scFic = new Scanner ( new File( "../data/test_long.data" ), "UTF-8" );
			
			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				nom   = ligne[0].trim();
				duree = Integer.parseInt(ligne[1].trim());
				
				tache = new Tache( nom, duree );

				// Ajout de la tache 
				this.ensTaches.add(tache);

				// precedents ?
				if ( ligne.length > 2 && !ligne[2].trim().isEmpty() )
				{
					String[] precedences = ligne[2].split(",");
					
					for (String prec : precedences)
						for ( Tache t : this.ensTaches )
							if ( t.getNom().equals( prec.trim() ) ) tache.ajouterPrc( t );
				}
				else
				{
					tache.ajouterPrc( this.ensTaches.get(0) );
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
			this.calculerNiveau( t );
	}


	

	/*-------------------*/
	/* toString          */
	/*-------------------*/
	public String toString()
	{
		String sRet = "";

		int nbJourMax = this.getTache( this.ensTaches.size() -1 ).getDte_tard();

		for ( Tache t : this.ensTaches )
			sRet += t.toString( MPM.getFlagDate(), MPM.getDate(), nbJourMax ) + "\n";
		
		return sRet;
	}

}