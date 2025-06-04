package src.ihm;

import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Point;
import java.awt.Color;

/*---------------------------------*/
/*  Class Lien                     */
/*---------------------------------*/
public class Lien
{
	/*-------------------------------*/
	/* Attributs                     */
	/*-------------------------------*/
	private PanelTache tachePrc;
	private PanelTache tacheSvt;

	private int        duree;

	/*-------------------------------*/
	/* Constructeur                  */
	/*-------------------------------*/
	public Lien( PanelTache tachePrc, PanelTache tacheSvt, int duree )
	{
		this.tachePrc = tachePrc;
		this.tacheSvt = tacheSvt;

		this.duree    = duree;
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public PanelTache getTacheSvt  () { return this.tacheSvt; }
	public PanelTache getTachePrc  () { return this.tachePrc; }

	/*-------------------------------*/
	/* Dessin                        */
	/*-------------------------------*/
	public void dessiner(Graphics2D g2, Color couleurFleche, Color couleurFond) 
	{
		Point debut = new Point( tachePrc.getX() + tachePrc.getWidth(),
		                         tachePrc.getY() + tachePrc.getHeight() / 2 );

		// plusieurs prc pour svt ?
		int gapV  = 0;
		int nbPrc = tacheSvt.getTache().getlstPrc().size();
		if ( nbPrc > 1 )
		{
			int indicePrecedent = tacheSvt.getTache().getlstPrc().indexOf( tachePrc.getTache() );
			gapV = indicePrecedent - ( nbPrc - 1 );
		}

		Point arrivee = new Point( tacheSvt.getX(),
		                           tacheSvt.getY() + tacheSvt.getHeight() / 2 + gapV );

		Point millieu = new Point( ( debut.x + arrivee.x ) / 2, 
		                           ( debut.y + arrivee.y ) / 2 );

		// part 1 : - -
		g2.setColor(couleurFleche);
		g2.setStroke(new java.awt.BasicStroke(1.5f));
		g2.drawLine(debut.x, debut.y, arrivee.x, arrivee.y);

		// part 2 : texte
		g2.setFont( new javax.swing.JLabel().getFont() );

		String texte        = this.getTachePrc().getTache().getDuree() + "";
		int    largeurTexte = g2.getFontMetrics().stringWidth(texte);
		int    hauteurTexte = g2.getFontMetrics().getAscent();
		int    largeurRect  = largeurTexte  * 2;
		int    hauteurRect  = hauteurTexte  * 2;

		g2.setColor(couleurFond);
		g2.fillRect(millieu.x - largeurRect / 2, millieu.y - hauteurRect / 2, largeurRect, hauteurRect);

		g2.setColor(new Color(255, 25, 25));
		g2.drawString(texte, millieu.x - largeurTexte / 2, millieu.y + hauteurTexte / 2 - 2);
		


		// part 3 : |>
		int longueurFleche = 12;
		int largeurFleche  = 7;

		// Calcul de la direction
		double dx    = arrivee.x - debut.x;
		double dy    = arrivee.y - debut.y;
		double angle = Math.atan2(dy, dx);

		// Pointe
		Point point1 = new Point(arrivee.x, arrivee.y);

		// Bases
		Point point2 = new Point( (int) ( point1.x - longueurFleche * Math.cos(angle) + largeurFleche * Math.sin(angle) / 2),    // Coin gauche x
		                          (int) ( point1.y - longueurFleche * Math.sin(angle) - largeurFleche * Math.cos(angle) / 2)  ); // Coin gauche y

		Point point3 = new Point( (int) ( point1.x - longueurFleche * Math.cos(angle) - largeurFleche * Math.sin(angle) / 2),    // Coin droit x
		                          (int) ( point1.y - longueurFleche * Math.sin(angle) + largeurFleche * Math.cos(angle) / 2)  ); // Coin droit y

		int[] xPoints = { point1.x, point2.x, point3.x };
		int[] yPoints = { point1.y, point2.y, point3.y };

		g2.setColor(couleurFleche);
		g2.fillPolygon(xPoints, yPoints, 3);


	}
}
