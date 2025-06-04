package src.metier;

import java.util.List;
import java.util.ArrayList;


/*---------------------------------*/
/*  Class Tache                    */
/*---------------------------------*/
public class Tache
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private String nom;
	private int    duree; 

	private int    dte_au_tot; 
	private int    dte_au_tard;

	private int    niveau;

	private List<Tache> lstPrc;
	private List<Tache> lstSvt;

	
	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public Tache ( String nom, int duree, int dte_au_tot )
	{
		this.nom   =    nom;
		this.duree =  duree;

		this.dte_au_tot  = dte_au_tot;
		this.dte_au_tard = -1;
		
		this.niveau = -1;
		
		this.lstPrc = new ArrayList<Tache>();
		this.lstSvt = new ArrayList<Tache>();
	}

	public Tache ( String nom, int duree )
	{
		this( nom, duree, -1 );
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public String      getNom     () { return this.nom          ; }
	public int         getDuree   () { return this.duree        ; }
	public int         getDte_tot () { return this.dte_au_tot   ; }
	public int         getDte_tard() { return this.dte_au_tard  ; }
	public int         getNiveau  () { return this.niveau;        }

	public List<Tache> getlstPrc  () { return this.lstPrc       ; }
	public List<Tache> getlstSvt  () { return this.lstSvt       ; }

	public int         getNbPrc   () { return this.lstPrc.size(); }
	public int         getNbSvt   () { return this.lstSvt.size(); }

	public int calculerMarge      () { return  this.getDte_tard() - this.getDte_tot(); }


	/*-------------------------------*/
	/* Modificateurs                 */
	/*-------------------------------*/
	public void ajouterPrc( Tache t )
	{
		if ( t != null && ! this.lstPrc.contains( t ) && ! t.lstSvt.contains( this ) )
		{
			this.lstPrc.add( t );
			t.lstSvt.add( this );
		}
	}

	public void setDateTot( int val )
	{
		if ( this.dte_au_tot == -1 || val > this.dte_au_tot )
		{
			this.dte_au_tot = val;
		}
	}


	public void setDateTard( int val )
	{
		if ( this.dte_au_tard == -1 || val < this.dte_au_tard )
		{
			this.dte_au_tard = val;
		}
	}

	public void setNiveau( int niveau )
	{
		this.niveau = niveau;
	}
	

	/*------------------*/
	/* toString         */
	/*------------------*/
	public String toString( char dateRef, DateFr date, int nbJourMax )
	{
		String sRet    = "";

		// nom : durée jour(s)
		sRet += this.getNom() +   " : " +   this.getDuree()   + 
				( this.getDuree() > 1 ? " jours" : " jour" )  +   "\n"; //avec ou sans s 

		// Date au plus tôt
		sRet += "  date au plus tôt  : " +  date.toString( dateRef, this.dte_au_tot, nbJourMax  ) +  "\n";

		// Date au plus tard
		sRet += "  date au plus tard : " +  date.toString( dateRef, this.dte_au_tard, nbJourMax ) +  "\n";

		// Marge
		sRet += "  marge             : "   +  this.calculerMarge()    + 
				( ( this.calculerMarge() > 1 ) ? " jours" : " jour")  + "\n";

		// Précédents
		if (this.getNbPrc() > 0)
		{
			sRet += "  liste des tâches précédentes : \n    ";
			for (int cpt = 0; cpt < this.getNbPrc(); cpt++)
			{
				sRet += this.getlstPrc().get(cpt).getNom();
				if (cpt < this.getNbPrc() - 1) sRet += ", ";
			}
		}
		else { sRet += "  pas de tâche précédente"; }
		sRet += " \n";

		// Suivants
		if (this.getNbSvt() > 0)
		{
			sRet += "  liste des tâches suivantes : \n    ";
			for (int cpt = 0; cpt < this.getNbSvt(); cpt++)
			{
				sRet += this.getlstSvt().get(cpt).getNom();
				if (cpt < this.getNbSvt() - 1) sRet += ",";
			}
		}
		else { sRet += "  pas de tâche suivante"; }
		sRet += " \n";

		return sRet;
	}

}