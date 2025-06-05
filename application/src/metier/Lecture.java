package src.metier;

import src.Controleur;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class Lecture                  */
/*---------------------------------*/
abstract class Lecture 
{

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public static void initMpm( MPM graphe, String lien )
	{
		String nomFichier = lien      .substring( lien      .lastIndexOf("/") + 1 );
		String extension  = nomFichier.substring( nomFichier.lastIndexOf('.') + 1 );

		switch ( extension )
		{
			case "mpm" ->
			{
				Lecture.initTachesMPM( graphe, lien );
				Lecture.initPrcMPM   ( graphe, lien );
				Lecture.initSvtMPM   ( graphe, lien );
			}
			case "mpm2" ->
			{
				Lecture.initTachesMPM2( graphe, lien );
				Lecture.initPrcMPM    ( graphe, lien );
				Lecture.initSvtMPM    ( graphe, lien );
			}
			default ->{ System.out.println( "Erreur d'extension du fichier" ); }
		}
		
	}

	/*-------------------------------*/
	/* Méthodes de Lecture           */
	/*-------------------------------*/
	// InitTaches
	public static void initTachesMPM( MPM graphe, String lien )
	{
		String[] ligne = null;
		String   nom   = null;
		int      duree = 0;
		Tache    tache = null;

		graphe.ajouterTache( new Tache( "DÉBUT", 0, 0 ) );
		try 
		{
			Scanner scFic = new Scanner ( new File( lien ), "UTF-8" );
			
			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				nom   = ligne[0].trim();
				duree = Integer.parseInt(ligne[1].trim());
				
				tache = new Tache( nom, duree );

				// Ajout de la tache 
				graphe.ajouterTache(tache);
			}
			scFic.close();
		} catch (Exception e) { e.printStackTrace(); }

		graphe.ajouterTache ( new Tache( "FIN", 0 ) );
	}

	public static void initTachesMPM2( MPM graphe, String lien )
	{
		int duree, dteTot, dteTard, niveau, posX, posY;

		String[] ligne = null;
		String   nom   = null;
		Tache    tache = null;

		List<Tache> lstPrc ;

		duree = dteTot = dteTard = niveau = posX = posY = 0;

		try 
		{
			Scanner scFic = new Scanner ( new File( lien ), "UTF-8" );
			
			/*
			t.getNom     ()
			t.getDuree   ()
			prc             + "|"
			t.getDte_tot ()
			t.getDte_tard()
			t.getNiveau  ()
			t.getPosX    ()
			t.getPosY    ()
			 */


			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				nom     =                   ligne[0].trim()  ;
				duree   = Integer.parseInt( ligne[1].trim() );
				dteTot  = Integer.parseInt( ligne[3].trim() );
				dteTard = Integer.parseInt( ligne[4].trim() );
				niveau  = Integer.parseInt( ligne[5].trim() );
				posX    = Integer.parseInt( ligne[6].trim() );
				posY    = Integer.parseInt( ligne[7].trim() );
				
				tache = new Tache( nom, duree, dteTot, dteTard, niveau, posX, posY );

				graphe.ajouterTache(tache);
			}
			scFic.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	// InitPrc
	public static void initPrcMPM( MPM graphe, String lien )
	{
		String[] ligne  = null;
		String[] tabPrc = null;
		Tache    tache  = null;
		int      cptL   = 1;

		try 
		{
			Scanner scFic = new Scanner ( new File( lien ), "UTF-8" );
			
			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				tache = graphe.getTache( cptL );

				// cas il a des prc
				if ( ligne.length > 2 && !ligne[2].trim().isEmpty() )
				{
					tabPrc = ligne[2].split(",");
					
					for (String prec : tabPrc ) 
					{
						prec = prec.trim();
						for ( Tache t : graphe.getListTache() ) 
						{
							if ( t.getNom().equals(prec) ) 
							{
								tache.ajouterPrc( t );
								break;
							}
						}
					}
				}
				else
					tache.ajouterPrc( graphe.getTache(0) );
				
				cptL++;
			}
			scFic.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	// InitSvt
	public static void initSvtMPM( MPM graphe, String lien )
	{
		Tache fin = graphe.getTache( graphe.getListTache().size() - 1 );
		
		for ( Tache t : graphe.getListTache() )
			if ( t.getNbSvt() == 0 && !t.getNom().equals("FIN") ) fin.ajouterPrc( t );
	}
}
