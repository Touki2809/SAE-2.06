package src.metier;

import src.Controleur;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class LectureEcriture          */
/*---------------------------------*/
public class LectureEcriture 
{
	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public LectureEcriture( MPM graphe, String lien, int type )
	{
		switch ( type )
		{
			case 1  -> this.initMpm( graphe, lien );
			case 2  -> System.out.println("Méthode d'écriture non implémentée.");
			default -> System.out.println("Type de lecture/écriture non reconnu.");
		}
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	private void initMpm( MPM graphe, String lien )
	{
		this.initTaches( graphe, lien );
		this.initPrc   ( graphe, lien );
		this.initSvt   ( graphe, lien );
	}

	/*-------------------------------*/
	/* Méthodes de Lecture           */
	/*-------------------------------*/
	public void initTaches( MPM graphe, String lien )
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

	public void initPrc( MPM graphe, String lien )
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
				else // deb est son prc
				{
					tache.ajouterPrc( graphe.getTache(0) );
				}
				cptL++;
			}
			scFic.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void initSvt( MPM graphe, String lien )
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



	/*-------------------------------*/
	/* Méthodes d'Écriture           */
	/*-------------------------------*/
	public void sauvegarde( MPM graphe, String nomFichier )
	{
		String prc;
		File   fichier = new File( "./../data/save/" + nomFichier + ".data" );
		
		try
		{
			PrintWriter pw = new PrintWriter( new FileOutputStream(  "./../data/save/" + nomFichier + ".data" ) );
			for( Tache t : graphe.getListTache() )
			{
				prc = "";
				if ( t.getlstPrc() != null )
				{
					for( int cpt = 0; cpt < t.getlstPrc().size(); cpt++ )
					{
						if ( cpt < t.getlstPrc().size() - 1 ) prc += t.getlstPrc().get(cpt) + ",";
						else                                  prc += t.getlstPrc().get(cpt);
					}
				}
				pw.println( t.getNom() + "|" + t.getDuree() + "|" + prc );
			}

			pw.close();
	
		} catch (Exception e) { e.printStackTrace(); }
	}
}
