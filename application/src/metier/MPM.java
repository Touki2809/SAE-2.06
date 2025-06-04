package src.metier;

import iut.algo.Clavier;

import java.io.File;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

import java.util.Queue;
import java.util.LinkedList;

import java.awt.Point;

/*---------------------------------*/
/*  Class MPM                      */
/*---------------------------------*/
public class MPM
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private static char   dateRef;
	private static DateFr dateInit; 

	private List<List<Tache>>    ensTachesParNiveau;
	private List<Tache>          ensTaches;
	private List<CheminCritique> ensCheminCritiques;


	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public MPM( char dateRef, DateFr dateInit )
	{
		MPM.dateRef  = dateRef;
		MPM.dateInit = new DateFr( dateInit ); 

		this.ensTachesParNiveau  = new ArrayList<List<Tache>>();
		this.ensTaches           = new ArrayList<Tache>         ();
		this.ensCheminCritiques  = new ArrayList<CheminCritique>();

		Lecture.initMpm( this, "../data/import/1.data" );
		
		this.setListTacheParNiveau();
		this.calculerNiveau();
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public static char          getFlagDate  ()         { return MPM.dateRef;               }
	public static DateFr        getDate      ()         { return MPM.dateInit;              }
	public List<List<Tache>>    getListTacheParNiveau() { return this.ensTachesParNiveau;   }
	public List<Tache>          getListTache ()         { return this.ensTaches ;           }
	public List<CheminCritique> getListCheminCritique() { return this.ensCheminCritiques;   }
	public Tache                getTache(int index)     { return this.ensTaches.get(index); }
	public int                  getNbTache()            { return this.ensTaches.size();     }

	public Integer getNiveau(Tache t)
	{
		for ( int cpt = 0; cpt < this.ensTachesParNiveau.size(); cpt++ )
			for ( Tache tache : this.ensTachesParNiveau.get(cpt) )
				if ( tache.equals( t ) ) return cpt;
		
		return null;
	}

	public int getLong()
	{

		int maxLong = 0;
		for ( Tache t : this.ensTaches )
		{
			if ( t.getNom().length() > maxLong ) maxLong = t.getNom().length();
		}
		return maxLong;
	}

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public void ajouterTache( Tache t )
	{
		if ( !this.ensTaches.contains(t) )
			this.ensTaches.add( t );
	}
	
	public void calculerNiveau() 
	{
		// définit DÉBUT à 0
		this.ensTaches.get( 0 ).setNiveau( 0 );

		// parcours en largeur -> on modif que si le nv de tSvt est < ou =1
		boolean modifie = true;
		do
		{
			modifie = false;
			for ( Tache t : this.ensTaches )
			{
				for ( Tache tSvt : t.getlstSvt() )
				{
					if ( tSvt.getNiveau() == -1 || tSvt.getNiveau() < t.getNiveau() + 1 )
					{
						tSvt.setNiveau(t.getNiveau() + 1);
						modifie = true;
					}
				}
			}
		} while ( modifie );

		// mes à jours la list pour l'ihm
		this.setListTacheParNiveau();
	}

	public void setListTacheParNiveau( )
	{
		this.ensTachesParNiveau = new ArrayList<List<Tache>>();

		// Regrouper les tâches par niveau
		int maxNiveau = 0;
		for (Tache t : this.ensTaches) 
		{
			if (t.getNiveau() > maxNiveau)
				maxNiveau = t.getNiveau();
		}

		// Tache rangée par niveau
		this. ensTachesParNiveau = new ArrayList<>();
		for (int cptNv = 0; cptNv <= maxNiveau; cptNv++) 
			this.ensTachesParNiveau.add( new ArrayList<>() );
		
		// Ajout des tâches
		for (Tache t : this.ensTaches) 
		{
			if ( t.getNiveau()  >= 0)  
			{
				this.ensTachesParNiveau.get( t.getNiveau() ).add(t);
			}
		}
	}

	//public void setPosX


	public void calculerDateNiveauTot(int niveau)
	{
		if ( niveau < 0 || niveau >= this.ensTachesParNiveau.size() ) return;

		for ( Tache t : this.ensTachesParNiveau.get( niveau ) )
		{
			int max = 0;
			
			for ( Tache tPrc : t.getlstPrc() ) 
			{
				if ( tPrc.getDte_tot() + tPrc.getDuree() > max )
					max = tPrc.getDte_tot() + tPrc.getDuree();
			}
			
			t.setDateTot( max );		
		}
	}

	public void calculerDateNiveauTard(int niveau) 
	{
		if ( niveau < 0 || niveau >= this.ensTachesParNiveau.size() ) return;

		for ( Tache t : this.ensTachesParNiveau.get( niveau ) )
		{
			int min = 10000;
			
			for ( Tache tSvt : t.getlstSvt() ) 
			{
				if ( tSvt.getDte_tard() - t.getDuree() < min )
					min = tSvt.getDte_tard() - t.getDuree();
			}
			
			if ( min == 10000 ) t.setDateTard( t.getDte_tot() );
			else                t.setDateTard( min            );
		}
	}


	/*--------------------------------*/
	/* Calculer le chemin critique    */
	/*--------------------------------*/
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
	/* Sauvegarder et Charger        */
	/*-------------------------------*/
	public void charger( String chemin )
	{
		this.ensTaches         .clear();
		this.ensTachesParNiveau.clear();
		this.ensCheminCritiques.clear();

		Lecture.initMpm( this, chemin );

		this.calculerNiveau();
	}

	public boolean sauvegarder( String chemin )
	{
		return Ecriture.sauvegarde( this , chemin );
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