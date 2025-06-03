import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

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
	private Controleur       ctrl;
	private FrameMPM         frame;
	
	private List<PanelTache> lstPanelTache;
	private List<Lien>       lstLien;

	public PanelMPM( Controleur ctrl, FrameMPM frame)
	{
		this.ctrl     = ctrl;
		this.frame = frame;
		this.lstPanelTache = new ArrayList<PanelTache>();
		this.setLayout(null);
		
		// AJOUT IMPORTANT: Assurer que les niveaux sont calculés avant utilisation
		for (int i = 0; i < this.ctrl.getNbTaches(); i++) {
			this.ctrl.getTache(i).getNiveau(); 
		}
		
		// Trouver le niveau max valide
		int niveauMax = 0;
		for (int i = 0; i < this.ctrl.getNbTaches(); i++) {
			int niveau = this.ctrl.getTache(i).getNiveau();
			if (niveau > niveauMax) niveauMax = niveau;
		}
		
		// Création des composants - utiliser le niveau 0 par défaut si -1
		ArrayList<ArrayList<PanelTache>> niveaux = new ArrayList<>();
		for (int i = 0; i <= niveauMax; i++) {
			niveaux.add(new ArrayList<>());
		}
		
		for (int i = 0; i < this.ctrl.getNbTaches(); i++) {
			Tache t = this.ctrl.getTache(i);
			PanelTache pt = new PanelTache(this.ctrl, this.frame, t);
			this.lstPanelTache.add(pt);
			
			int niveau = t.getNiveau();
			if (niveau < 0) niveau = 0; // Si niveau non initialisé, on le met à 0
			
			niveaux.get(niveau).add(pt);
		}
		
		// Placement des panels
		int largeurPanel = 100;
		int hauteurPanel = 60;
		int espaceX = 40;
		int espaceY = 30;

		for (int niveau = 0; niveau <= niveauMax; niveau++) {
			ArrayList<PanelTache> panels = niveaux.get(niveau);
			for (int i = 0; i < panels.size(); i++) {
				PanelTache pt = panels.get(i);
				int x = 50 + niveau * (largeurPanel + espaceX);
				int y = 50 + i * (hauteurPanel + espaceY);
				pt.setBounds(x, y, largeurPanel, hauteurPanel);
				this.add(pt);
			}
		}
		
		this.setPreferredSize(new Dimension(800, 600));
	}

	private List<PanelTache> getLstPrc( PanelTache panelTache )
	{
		if ( panelTache == null || panelTache.getTache() == null ) return null;
		
		List<PanelTache> lstPrc = new ArrayList<PanelTache>();

		for( PanelTache panelTachePrc : this.lstPanelTache )
		{
			for ( Tache t : panelTache.getTache().getlstSvt() )
			{
				if ( panelTache.getTache().equals( t ) )
					lstPrc.add( panelTachePrc );
			}
		}

		return lstPrc;
	
	}

	public void maj()
	{
	    this.repaint();
	}
	
	public void deplacerFigure(Tache tache, int dx, int dy)
	{
	    // Cherche le PanelTache correspondant à la tâche
	    for (PanelTache pt : this.lstPanelTache)
	    {
	        if (pt.getTache().equals(tache))
	        {
	            // Déplace le panel
	            pt.setLocation(pt.getX() + dx, pt.getY() + dy);
	            break;
	        }
	    }
	    this.repaint();
	}
}
