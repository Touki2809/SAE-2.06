
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

	private List<Tache> lstPrc;
	private List<Tache> lstSvt;

	
	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public Tache ( String nom, int duree )
	{
		this.nom   =    nom;
		this.duree =  duree;

		this.dte_au_tot  = 0;
		this.dte_au_tard = 0;
		
		this.lstPrc = new ArrayList<Tache>();
		this.lstSvt = new ArrayList<Tache>();
	}


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

	//public void ajouterSvt( Tache t )
	//{
	//	if ( ! this.lstSvt.contains( t ) ) this.lstSvt.add( t );
	//}

	public void dateMin()
	{
		for (Tache t : this.lstPrc) 
		{
			if (t != null) 
			{
				int dateFin = t.getDte_tot() + t.getDuree();
				
				if ( dateFin > this.dte_au_tot) this.dte_au_tot = dateFin;
			}
		}
	}

	public void dateMax()
	{
		for (Tache t : this.lstSvt) 
		{
			if (t != null) 
			{
				int dateFin= t.getDte_tard() - this.getDuree();
				
				if ( dateFin < this.dte_au_tot) this.dte_au_tard = dateFin;
			}
		}
	}
	
	public void dateTot()
	{
		int valMin = 0;

		if ( this.lstPrc.size() != 0 && this.lstPrc.get(0) != null)
		{
			valMin = this.lstPrc.get(0).getDte_tard() + this.getDuree();
		}
		
		// parcous prc
		for(int cpt = 1 ; cpt < this.lstPrc.size() ; cpt++)
		{
			if(this.lstPrc.get(cpt) != null)
			{
				if(this.lstPrc.get(cpt).getDte_tard() + this.getDuree() < valMin)
				{
					valMin = this.lstPrc.get(cpt).getDte_tard() - this.getDuree();
				}
			}
		}
		

		this.dte_au_tot = valMin;
	}

	public void dateTard()
	{
		int valMax = 0;

		if ( this.lstSvt.size() != 0 && this.lstSvt.get(0) != null)
		{
			valMax = this.lstSvt.get(0).getDte_tard() - this.getDuree();
		}

		// parcous svt
		for(int cpt = 1 ; cpt < this.lstSvt.size() ; cpt++)
		{	
			if(this.lstSvt.get(cpt) != null)
			{
				if(this.lstSvt.get(cpt).getDte_tard() - this.getDuree() > valMax)
				{
					valMax = this.lstSvt.get(cpt).getDte_tard() - this.getDuree();
				}
			}
		}

		this.dte_au_tard = valMax;
	}


	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public String getNom     () { return this.nom          ; }
	public int    getDuree   () { return this.duree        ; }
	public int    getDte_tot () { return this.dte_au_tot   ; }
	public int    getDte_tard() { return this.dte_au_tard  ; }
	public int    getNbPrc   () { return this.lstPrc.size(); }
	public int    getNbSvt   () { return this.lstSvt.size(); }

	public void setDteTard(int date) { this.dte_au_tard = date; }
	public void setDteTot (int date) { this.dte_au_tot  = date; }

	public Tache  getSvt(int indice) 
	{
		if(this.lstSvt.get(indice) == null) return null;
		
		return this.lstSvt.get(indice);
	}

	public Tache  getPrc(int indice) 
	{
		if( this.lstPrc.get(indice) == null ) return null;
		
		return this.lstPrc.get(indice);
	}

	public int calculerMarge()
	{
		return  this.getDte_tard() - this.getDte_tot();
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
	            sRet += this.getPrc(cpt).getNom();
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
	            sRet += this.getSvt(cpt).getNom();
	            if (cpt < this.getNbSvt() - 1) sRet += ",";
	        }
	    }
	    else { sRet += "  pas de tâche suivante"; }
	    sRet += " \n";

	    return sRet;
	}

}