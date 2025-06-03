package src.metier.dessin;

import src.metier.mpm.Tache;

public class Rectangle extends Figure
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private Tache tache;


	public Rectangle ( Tache tache, int centreX, int centreY, int tailleX, int tailleY )
	{
		super(centreX, centreY, tailleX, tailleY);

		this.tache = tache;
	}

	public boolean possede ( int x, int y )
	{
		int x1 = this.getCentreX() - this.getTailleX() /2;
		int x2 = this.getCentreX() + this.getTailleX() /2;
		int y1 = this.getCentreY() - this.getTailleY() /2;
		int y2 = this.getCentreY() + this.getTailleY() /2;
		
		return ( ( x1<= x && x<=x2 ) && (y1<= y && y<=y2 ) ); 
	}

	public Tache getTache() { return this.tache;}
}