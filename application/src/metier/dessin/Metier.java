package src.metier.dessin;

import src.metier.mpm.Tache;

import java.util.List;
import java.util.ArrayList;

public class Metier
{
	private List<Figure> lstFigure;

	public Metier()
	{
		this.lstFigure = new ArrayList<Figure>(); 

	}


	public void ajouterFigure ( Tache t,char fig, int x, int y, int tx, int ty )
	{
		switch ( fig ) 
		{
			case 'R' -> this.lstFigure.add ( new Rectangle ( t, x, y, tx, ty ) );
		}
		System.out.println(fig);
	}

	public int    getNbFigure () { return this.lstFigure.size(); }

	public Figure getFigure ( int num )
	{
		return this.lstFigure.get(num);
	}

	// On rerche la figure dont le point x y fait partie de la figure
	// On retourne l'indice de la première figure trouvée

	public Integer getIndiceFigure ( int x, int y )
	{
		for (int cpt = 0; cpt<this.lstFigure.size(); cpt++ )
			if ( this.lstFigure.get(cpt).possede ( x, y ) )
				return cpt;

		return null;
	}

	public void deplacerFigure ( Integer numFigure, int x, int y )
	{
		if ( numFigure != null && numFigure >=0 && numFigure < this.lstFigure.size() )
		{
			this.lstFigure.get(numFigure).deplacerX(x);
			this.lstFigure.get(numFigure).deplacerY(y);
		}
		
	}
}