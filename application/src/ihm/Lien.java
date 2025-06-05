package src.ihm;

import java.awt.BasicStroke;
import javax.swing.JLabel;

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

		// part 1 : ligne
		g2.setColor (couleurFleche);
		g2.setStroke( new BasicStroke( 2 ) );
		g2.drawLine ( debut.x, debut.y, arrivee.x, arrivee.y );

		// part 2 : texte
		g2.setFont(  new JLabel().getFont() );

		String duree        = this.getTachePrc().getTache().getDuree() + "";
		int    largeurTexte = 10;
		int    hauteurTexte = 15;
		int    largeurRect  = largeurTexte  * 2;
		int    hauteurRect  = hauteurTexte  * 2;


		g2.setColor( couleurFond );
		g2.fillRect( millieu.x - largeurRect / 2,
		             millieu.y - hauteurRect / 2,
					 largeurRect                ,
					 hauteurRect                 );

		g2.setColor   ( new Color( 255, 25, 25 ) );
		g2.drawString (  duree,
		                 millieu.x - largeurTexte / 2,
						 millieu.y + hauteurTexte / 2 -2 ); //-2 est juste pour centrer 
		

		// part 3 : tête
		int longueurFleche = 12;
		int largeurFleche  = 7;

		// Calcul de la direction
		double dx    = arrivee.x - debut.x;
		double dy    = arrivee.y - debut.y;
		double angle = Math.atan2(dy, dx);

		// Pointe
		Point pointA = new Point(arrivee.x, arrivee.y);

		// Bases
		Point pointH = new Point( (int) ( pointA.x - longueurFleche * Math.cos(angle) + largeurFleche * Math.sin(angle) / 2),    // Coin gauche x
		                          (int) ( pointA.y - longueurFleche * Math.sin(angle) - largeurFleche * Math.cos(angle) / 2)  ); // Coin gauche y

		Point pointG = new Point( (int) ( pointA.x - longueurFleche * Math.cos(angle) - largeurFleche * Math.sin(angle) / 2),    // Coin droit x
		                          (int) ( pointA.y - longueurFleche * Math.sin(angle) + largeurFleche * Math.cos(angle) / 2)  ); // Coin droit y

		int[] xPoints = { pointA.x, pointH.x, pointG.x };
		int[] yPoints = { pointA.y, pointH.y, pointG.y };

		g2.setColor(couleurFleche);
		g2.fillPolygon(xPoints, yPoints, 3);
	}
}
