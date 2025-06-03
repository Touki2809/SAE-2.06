package src.ihm;

import src.Controleur;
import src.metier.Tache;
import src.metier.CheminCritique;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
	
	private List<List<Tache>> tachesParNiveau = new ArrayList<>();
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

		// Récupérer la liste des tâches
		List<Tache> lstTaches = this.ctrl.getGraphe().getListTache();

		// Déterminer le niveau de chaque tâche
		// Regrouper les tâches par niveau
		int maxNiveau = 0;
		for (Tache t : lstTaches) 
		{
			if (t.getNiveau() > maxNiveau)
				maxNiveau = t.getNiveau();
		}

		// Tache rangée par niveau
		this. tachesParNiveau = new ArrayList<>();
		for (int cptNv = 0; cptNv <= maxNiveau; cptNv++) 
			tachesParNiveau.add( new ArrayList<>() );
		
		for (Tache t : lstTaches) 
		{
			if ( t.getNiveau()  >= 0)  
			{
				tachesParNiveau.get( t.getNiveau() ).add(t);
			}
		}

		//--- 
		// Placement simple des panels de tâches 
		//--- 
		int panelDim = 40;
		int hGap     = 60;
		int vGap     = 60;

		for (int niveau = 0; niveau <= maxNiveau; niveau++) 
		{
			List<Tache> tachesNiveau = tachesParNiveau.get( niveau );
			int         nbTache      = tachesNiveau.size();

			// 
			int totalH  = nbTache * panelDim + ( nbTache - 1 ) * vGap;
			int centreY = ( this.frame.getHeight() - totalH ) / 2;
			int x       = 50 + niveau * ( panelDim + hGap );

			// Placement 
			for ( int cptT = 0; cptT < nbTache; cptT++ ) 
			{
				Tache tache = tachesNiveau.get( cptT );
				int       y = centreY + cptT * ( panelDim + vGap );

				PanelTache panelTache = new PanelTache( this.ctrl, this.frame, tache );
				panelTache.setBounds( x, y, panelDim, panelDim );

				this.lstPanelTache.add( panelTache );
				
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
						this.lstLien.add(new Lien( panelTache, 
												   panelSvt  , 
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
		
		// Permet au JScrollPane de défiler correctement selon la taille du graphe
        this.setPreferredSize(new java.awt.Dimension(2000, 1000));
	}

	/*-------------------------------*/
	/* Accesseurs                    */
	/*-------------------------------*/
	public List<PanelTache>  getLstPanelTache   () { return this.lstPanelTache;   }
	public List<List<Tache>> getTachesParNiveau () { return this.tachesParNiveau; }
	public List<Lien>        getLstLien         () { return this.lstLien;         }

	/*-------------------------------*/
	/*     PAINT                     */
	/*-------------------------------*/
	protected void paintComponent( Graphics g ) 
	{
		super.paintComponent(g);
		
		if ( this.lstLien != null && ! this.lstLien.isEmpty() ) 
		{
			this.g2 = (Graphics2D) g;
			for (Lien lien : this.lstLien) 
			{
				boolean crit1 = lien.getTachePrc().isCritique();
				boolean crit2 = lien.getTacheSvt().isCritique();

				Color couleur = Color.BLACK;
				if ( crit1 && crit2 )  couleur = Color.RED;

				lien.dessiner(this.g2, couleur, this.frame.getBackground());
			}
		}
	}

	public void maj() {
		for (PanelTache pt : this.lstPanelTache) {
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
