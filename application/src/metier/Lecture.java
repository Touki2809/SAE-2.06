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
		Lecture.initTaches( graphe, lien );
		Lecture.initPrc   ( graphe, lien );
		Lecture.initSvt   ( graphe, lien );
	}

	/*-------------------------------*/
	/* Méthodes de Lecture           */
	/*-------------------------------*/
	// InitTaches
	public static void initTaches( MPM graphe, String lien )
	{
		String[] ligne = null;
		String   nom   = null;
		int      duree = 0;
		Tache    tache = null;
		Scanner  scFic = null;

		graphe.ajouterTache( new Tache( "DÉBUT", 0, 0 ) );
		try 
		{
			scFic = new Scanner ( new File( lien ), "UTF-8" );
			
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

	// InitPrc
	public static void initPrc( MPM graphe, String lien )
	{
		String[] ligne = null;
		Tache    tache = null;
		Scanner  scFic = null;
		int      cptL  = 1;

		try 
		{
			scFic = new Scanner ( new File( lien ), "UTF-8" );
			
			while ( scFic.hasNextLine() )
			{
				ligne = scFic.nextLine().split("\\|");

				tache = graphe.getTache( cptL );

				// cas il a des prc
				if ( ligne.length > 2 && !ligne[2].trim().isEmpty() )
				{
					String[] tabPrc = ligne[2].split(",");
					
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
				else // pas de prc
				{
					tache.ajouterPrc( graphe.getTache(0) );
				}
				cptL++;
			}
			scFic.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	// InitSvt
	public static void initSvt( MPM graphe, String lien )
	{
		Tache fin = graphe.getTache( graphe.getListTache().size() - 1 );
		
		for ( Tache t : graphe.getListTache() )
		{
			if ( t.getNbSvt() == 0 && !t.getNom().equals("FIN") )
			{
				fin.ajouterPrc( t );
			}
		}
	}

}
