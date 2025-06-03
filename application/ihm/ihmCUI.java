package application.ihm;

import application.Controleur;
import application.metier.DateFr;


public class ihmCUI 
{

    private Controleur controleur;

    public ihmCUI( Controleur controleur ) 
	{
        this.controleur = controleur;
    }

    /*------------------*/
    /* Affichage Tache  */
    /*------------------*/
    public String tacheToString(int index) 
	{
        DateFr dateDebut = controleur.getDateDebut();
        return tacheToString( index, this.controleur );
    }

    public String tacheToString(int index, char dateRef, int nbJourMax) 
	{
      String sRet    = "";
	  Tache  t       = this.controleur.getTache( index );

	    // nom : durée jour(s)
	    sRet += t.getNom() +   " : " +   t.getDuree()   + 
	            ( t.getDuree() > 1 ? " jours" : " jour" )  +   "\n"; //avec ou sans s 

	    // Date au plus tôt
	    sRet += "  date au plus tôt  : " +  date.toString( dateRef, t.getDte_tot(), nbJourMax  ) +  "\n";

		// Date au plus tard
	    sRet += "  date au plus tard : " +  date.toString( dateRef, t.getDte_tard(), nbJourMax ) +  "\n";

	    // Marge
	    sRet += "  marge             : "   +  t.calculerMarge()    + 
	            ( ( t.calculerMarge() > 1 ) ? " jours" : " jour")  + "\n";

	    // Précédents
	    if (t.getNbPrc() > 0)
	    {
	        sRet += "  liste des tâches précédentes : \n    ";
	        for (int cpt = 0; cpt < t.getNbPrc(); cpt++)
	        {
	            sRet += t.getPrc(cpt).getNom();
	            if (cpt < t.getNbPrc() - 1) sRet += ", ";
	        }
	    }
	    else { sRet += "  pas de tâche précédente"; }
	    sRet += " \n";

	    // Suivants
	    if (t.getNbSvt() > 0)
	    {
	        sRet += "  liste des tâches suivantes : \n    ";
	        for (int cpt = 0; cpt < t.getNbSvt(); cpt++)
	        {
	            sRet += t.getSvt(cpt).getNom();
	            if (cpt < t.getNbSvt() - 1) sRet += ",";
	        }
	    }
	    else { sRet += "  pas de tâche suivante"; }
	    sRet += " \n";

	    return sRet;
	}
    

    /*------------------*/
    /* Affichage MPM    */
    /*------------------*/
    public void afficherMPM() 
	{
        int nb = controleur.getNbTaches();

        for (int nbTache = 0; nbTache < nb; nbTache++) 
		{
            System.out.println( tacheToString( nbTache ) );
        }
    }
}
