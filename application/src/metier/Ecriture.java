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
	public static void sauvegarde( MPM graphe, String nomFichier )
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
