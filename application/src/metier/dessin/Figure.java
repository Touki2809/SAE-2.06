package src.metier.dessin;

import src.metier.mpm.Tache;

/*------------------------*/
/* Class Figure           */
/*------------------------*/
public abstract class Figure
{
	/*---------------*/
	/* Attributs     */
	/*---------------*/
	private int centreX;
	private int centreY;
	private int tailleX;
	private int tailleY;

	/*---------------*/
	/* Constructeur  */
	/*---------------*/
	public Figure ( int centreX, int centreY, int tailleX, int tailleY )
	{
		this.centreX = centreX;
		this.centreY = centreY;

		this.tailleX = tailleX;
		this.tailleY = tailleY;
	}

	public abstract Tache getTache();

	public int getCentreX(){ return this.centreX; }
	public int getCentreY(){ return this.centreY; }
	public int getTailleX(){ return this.tailleX; }
	public int getTailleY(){ return this.tailleY; }

	void deplacerX (int x) { this.centreX += x; }
	void deplacerY (int y) { this.centreY += y; }
	void setTailleX(int x) { this.tailleX  = x; }
	void setTailleY(int y) { this.tailleY  = y; }


	public abstract boolean possede ( int x, int y );
}
