import javax.swing.*;

import java.awt.Graphics2D;
import java.awt.Color;

/*---------------------------------*/
/*  Class Lien                     */
/*---------------------------------*/
public class Lien
{
	private PanelTache tachePrc;
	private PanelTache tacheSvt;

	private int        duree;

	private Color      couleur;

	
	public Lien( PanelTache tachePrc, PanelTache tacheSvt, int duree )
	{
		this.tachePrc = tachePrc;
		this.tacheSvt = tacheSvt;

		this.duree    = duree;

		this.couleur = Color.BLACK;
	}

 	public PanelTache getTacheSvt  ()        { return this.tacheSvt; }
	public PanelTache getTachePrc  ()        { return this.tachePrc; }

	public void       setColourFond(Color c) {        this.couleur  = c; }

	public void drawArrow(Graphics2D g2)
	{
		int x1 = this.tachePrc.getX() + this.tachePrc.getWidth();
		int y1 = this.tachePrc.getY() + this.tachePrc.getHeight()/2;

		int x2 = this.tacheSvt.getX();
		int y2 = this.tacheSvt.getY() + this.tacheSvt.getHeight()/2;

		g2.drawLine(x1, y1, x2, y2); // la ligne
		
		
		int x3 = x2 - 5;
		int y3 = y2 - 5;

		g2.drawLine( x2, y2, x3, y3 ); // la diagonale supérieur

		int x4 = x2 + 5;
		int y4 = y2 + 5;
		
		g2.drawLine( x2, y2, x4, y4 ); // la diagonale inférieur
	}
}