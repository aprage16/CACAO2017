package abstraction.producteur.cotedivoire.contrats;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class AgentContrat implements Acteur{
	private List<IContratProd> producteurs; 
	private List<IContratTrans> transformateurs;
	
	
	public AgentContrat () {
		this.producteurs= new ArrayList<IContratProd>(); 
		this.transformateurs= new ArrayList<IContratTrans>(); 
	}
	
	public List<IContratProd> getProducteurs() {
		return this.producteurs;
	}
	
	public List<IContratTrans> getTransformateurs() {
		return this.transformateurs;
	}
	
	public void addProd(IContratProd prod){
		this.getProducteurs().add(prod);
	}
	public void addTrans(IContratTrans trans){
		this.getTransformateurs().add(trans);
	}

	@Override
	public String getNom() {
		return "Agent contrat";
	}

	@Override
	public void next() {
		List<Devis> l = new ArrayList<Devis>();
		if (Monde.LE_MONDE.getStep()%26==1){    // Contrat tout les 1er next de chaque année.
			for(IContratProd p : this.getProducteurs()) {     // Création de l'ensemble des devis.
				for (IContratTrans t : this.getTransformateurs()){
					l.add(new Devis(p,t));
				}
			}
			for (IContratTrans t : this.getTransformateurs()){  // Création et envoie de la list des devis dans lesquels t est impliqué.
				List<Devis> lt = new ArrayList<Devis>();
				for (Devis d : l){
					if (d.getTrans()==t){
						lt.add(d);
					}
				}
				t.envoieDevis(lt);
			}
			for (IContratProd p : this.getProducteurs()){  // Création et envoie de la list des devis dans lesquels p est impliqué.
				List<Devis> lp = new ArrayList<Devis>();
				for (Devis d : l){
					if (d.getProd()==p){
						lp.add(d);
					}
				}
				p.envoieDevis(lp);
			}
			
			for (IContratTrans t : this.getTransformateurs()){  // Les transfo vont modifiés la qttVoulue de chaque devis
				t.qttVoulue();
			}
			for(IContratProd p : this.getProducteurs()){  // Les prod vont modifiés prix et qttLivrable en fct de qttVoulue et de leur possibilité
				p.qttLivrablePrix();
			}
			for (IContratTrans t : this.getTransformateurs()){     // Transfo modifient la qttFinale qui sera la valeur recu chaque next pdt 1an.
				t.finContrat();
			}
			for(IContratProd p : this.getProducteurs()){   // Indique aux prod que les nego sont finis, ils peuvent donc récupérer l'info de la qttFinale
				p.notifContrat();
			}
		}
		
	}

}
