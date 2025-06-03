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
        return tacheToString(index, dateDebut);
    }

    public String tacheToString(int index, DateFr dateDebut) {
        var t = controleur.getTache(index);
        String sRet = "";

        sRet += t.getNom() +   " : " +   t.getDuree()   +
                ( t.getDuree() > 1 ? " jours" : " jour" )  +   "\n";

        sRet += "  date au plus tôt  : " +  dateDebut.toString( t.getDte_tot() ) +  "\n";
        sRet += "  date au plus tard : " +  dateDebut.toString(t.getDte_tard() ) +  "\n";
        sRet += "  marge             : "   +  t.calculerMarge()    +
                ( ( t.calculerMarge() > 1 ) ? " jours" : " jour")  + "\n";

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
