package src.metier;

import java.util.ArrayList;

/*---------------------------------*/
/*  Class CheminCritique           */
/*---------------------------------*/
public class CheminCritique
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private ArrayList<Tache> cheminCritique;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public CheminCritique()
	{
		this.cheminCritique = new ArrayList<Tache>();
	}
	
	public CheminCritique(CheminCritique c)
	{
		this.cheminCritique = new ArrayList<Tache>();
		
		for (Tache t : c.cheminCritique) 
			this.cheminCritique.add(t);
	}

	/*-------------------------------*/
	/* Modificateurs                */
	/*-------------------------------*/	
	public void ajouterTache(Tache t)
	{
		this.cheminCritique.add(t);
	}
	
	public ArrayList<Tache> getCheminCritique(){return this.cheminCritique;}
	

	/*-------------------------------*/
	/* Méthodes                      */
	/*-------------------------------*/
	public String toString()
	{
		String res ="";
		
		for ( Tache t : this.cheminCritique)
			res += t.getNom() + ";";
		
		return res+"\n";
	}
}