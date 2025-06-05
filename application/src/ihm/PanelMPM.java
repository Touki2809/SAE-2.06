package src.ihm;

import src.Controleur;
import src.metier.Tache;
import src.metier.CheminCritique;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Point;

import java.util.List;
import java.util.ArrayList;

/*---------------------------------*/
/*  Class PanelMPM                 */
/*---------------------------------*/
public class PanelMPM extends JPanel
{
	/*-------------------------------*/
	/* Composants                    */
	/*-------------------------------*/
	private Controleur        ctrl;
	private FrameMPM          frame;
	
	private List<PanelTache>  lstPanelTache;
	private List<Lien>        lstLien;

	private Graphics2D        g2;

	public PanelMPM( Controleur ctrl, FrameMPM frame)
	{
		this.ctrl     = ctrl;
		this.frame    = frame;

		this.setLayout(null);

		/*-------------------------------*/
		/* Création des composants       */
		/*-------------------------------*/
		
		//---
		// PanelTache
		//---
		this.lstPanelTache = new ArrayList<PanelTache>();

		// Récupération des tâches
		List<List<Tache>> tachesParNiveau = this.ctrl.getGraphe().getListTacheParNiveau();

		int maxNiveau = tachesParNiveau.size() - 1;

		// cmb maxNv sur une col
		int maxTache = 0;
		for ( List<Tache> tachesNiveau : tachesParNiveau ) 
		{
			if ( tachesNiveau.size() > maxTache ) 
			{
				maxTache = tachesNiveau.size();
			}
		}

		// dimension
		int panell = this.ctrl.getGraphe().getLong() * 10;
		int panelL =  60;
		int hGap   = 100;
		int vGap   = 40;		
		int totalH = maxTache * panelL + (maxTache - 1) * vGap;
		int baseY  = 5000;

		for (int niveau = 0; niveau <= maxNiveau; niveau++) 
		{
			List<Tache> tachesNiveau = tachesParNiveau.get(niveau);
			int         nbTache      = tachesNiveau.size();

			// depart
			int defautH = nbTache * 60 + (nbTache - 1) * vGap;
			int defautY = baseY + (totalH - defautH) / 2;

			for (int cptT = 0; cptT < nbTache; cptT++) 
			{
				Tache tache = tachesNiveau.get(cptT);

				// cord
				int x = 50      + niveau * ( panell + hGap );
				int y = defautY + cptT   * ( 60 + vGap );

				PanelTache panelTache = new PanelTache( this.ctrl, this.frame, tache );
				panelTache.setBounds( x, y, panell, panelL );

				this.lstPanelTache.add ( panelTache );
			}
		}
		

		//---
		// Lien entre les tâches
		//---
		this.lstLien = new ArrayList<>();
		for (PanelTache panelTache : this.lstPanelTache) 
		{
			Tache tache = panelTache.getTache();
			for (Tache tacheSvt : tache.getlstSvt()) 
			{
				for ( PanelTache panelSvt : this.lstPanelTache  ) 
				{
					if ( panelSvt.getTache().equals( tacheSvt ) ) 
					{
						this.lstLien.add(new Lien( panelTache        , 
												   panelSvt          , 
												   tacheSvt.getDuree()  ) );
					}
				}
			}
		}

		/*--------------------------------*/
		/* Positionnement des composants  */
		/*--------------------------------*/
		for (PanelTache panelTache : this.lstPanelTache) 
		{
			this.add( panelTache );
		}
		
		// dim
        int largeurPanel = 50 + (maxNiveau + 1) * (panell + hGap) + 100;
        int hauteurPanel = 10000;

        this.setPreferredSize(new java.awt.Dimension(largeurPanel, hauteurPanel));
    }

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public List<PanelTache>  getLstPanelTache   () { return this.lstPanelTache;   }
	public List<Lien>        getLstLien         () { return this.lstLien;         }

	public Point getPos( int val ) { return new Point( this.lstPanelTache.get( val ).getX() ,
	                                                   this.lstPanelTache.get( val ).getY() ); }

	/*-------------------------------*/
	/*     PAINT                     */
	/*-------------------------------*/
	protected void paintComponent( Graphics g ) 
	{
		super.paintComponent(g);
		
		if ( this.lstLien != null && ! this.lstLien.isEmpty() ) 
		{
			this.g2 = (Graphics2D) g;

			for ( Lien lien : this.lstLien ) 
			{
				Tache tachePrc = lien.getTachePrc().getTache();
				Tache tacheSvt = lien.getTacheSvt().getTache();
				
				boolean crit1 = lien.getTachePrc().isCritique();
				boolean crit2 = lien.getTacheSvt().isCritique();

				Color couleur = Color.BLUE;

				if ( crit1 && crit2 && tachePrc.calculerMarge() == tacheSvt.calculerMarge() )
				{
					couleur = Color.RED;
				}

				lien.dessiner(this.g2, couleur, this.frame.getBackground());
			}
		}
	}

	public void maj() 
	{
		for (PanelTache pt : this.lstPanelTache) 
		{
			pt.majAffichage();
		}
		this.repaint();
	}

	public void afficherCheminCritique()
	{
		List<CheminCritique> lstChemins = this.ctrl.getGraphe().getListCheminCritique();
		for ( PanelTache p : this.lstPanelTache )
		{
			boolean estCritique = false;
			for (CheminCritique chemin : lstChemins)
			{
				if (chemin.getCheminCritique().contains(p.getTache()))
				{
					estCritique = true;
					break;
				}
			}
			p.setCritique(estCritique);
		}
		
		this.repaint();
	}
}
