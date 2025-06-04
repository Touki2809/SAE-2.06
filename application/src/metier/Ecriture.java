package src.metier;

import src.Controleur;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class Ecriture                 */
/*---------------------------------*/
abstract class Ecriture
{
	

	public static boolean sauvegarde( MPM graphe, String nomFichier )
	{
		// Le fichier exciste ?
		File dossier = new File("./../data/save/");

		if ( dossier.exists() && dossier.isDirectory() )
		{
			File[] fichiers = dossier.listFiles();

			for (File f : fichiers)
				if ( f.getName().equals( nomFichier + ".mpm2" ) || f.isDirectory() ) return false;
		}
		
		String prc;
		try
		{
			/*
			Nom|Duree|lstPrc|niveau|posX|posY
			*/
			
			PrintWriter pw = new PrintWriter( new FileOutputStream(  "./../data/save/" + nomFichier + ".mpm2" ) );
			for( Tache t : graphe.getListTache() )
			{
				prc = "";
				if ( t.getlstPrc() != null )
				{
					//Calcul de la liste des précédents
					for( int cpt = 0; cpt < t.getlstPrc().size(); cpt++ )
					{
						if ( cpt < t.getlstPrc().size() - 1 ) prc += t.getlstPrc().get(cpt).getNom() + ",";
						else                                  prc += t.getlstPrc().get(cpt).getNom();
					}
				}


				pw.println( t.getNom   () + "|" +
				            t.getDuree () + "|" +
				            prc           + "|" +
				            t.getNiveau() + "|" +
				            t.getPosX  () + "|" +
				            t.getPosY  ()         );
			}

			pw.close();
	
		} catch (Exception e) { e.printStackTrace(); }

		
		return true;
	}

	
}
