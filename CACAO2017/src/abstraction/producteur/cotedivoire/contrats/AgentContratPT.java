package abstraction.producteur.cotedivoire.contrats;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class AgentContratPT implements Acteur{
	private List<IContratProd> producteurs; 
	private List<IContratTrans> transformateurs;
	public static List<IContratTrans> demandeurs;
	public Journal journal;
	
	
	public AgentContratPT () {
		this.producteurs= new ArrayList<IContratProd>(); 
		this.transformateurs= new ArrayList<IContratTrans>(); 
		this.demandeurs= new ArrayList<IContratTrans>();
		this.journal= new Journal (""+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
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
	
	public static List<IContratTrans> getDemandeurs () {
		return demandeurs;
	}
	
	public static void demandeDeContrat(IContratTrans t) {
		AgentContratPT.getDemandeurs().add(t);
	}

	@Override
	public String getNom() {
		return "Agent contrat Producteur Transformateurs";
	}

	@Override
	public void next() {
		List<Devis> l = new ArrayList<Devis>();
		for(IContratProd p : this.getProducteurs()) {     // Création de l'ensemble des devis.
			for (IContratTrans t : this.getDemandeurs()){
				l.add(new Devis(p,t));
				this.journal.ajouter("Création du devis entre "+p.getClass().getName()+" et "+t.getClass().getName());
			}
		}
		for (IContratTrans t : this.getDemandeurs()){  // Création et envoie de la list des devis dans lesquels t est impliqué.
			for (Devis d : l){
				if (d.getTrans()==t){
					t.envoieDevis(d);
				}
			}
		}
		for (IContratProd p : this.getProducteurs()){  // Création et envoie de la list des devis dans lesquels p est impliqué.
			for (Devis d : l){
				if (d.getProd()==p){
					p.envoieDevis(d);
				}
			}
		}

		for (IContratTrans t : this.getDemandeurs()){  // Les transfo vont modifiés la qttVoulue de chaque devis
			t.qttVoulue();
			for (Devis d : l){
				this.journal.ajouter("Le transformateur veut "+d.getQttVoulue()+" avec "+d.getProd());
			}
		}
		for(IContratProd p : this.getProducteurs()){  // Les prod vont modifiés prix et qttLivrable en fct de qttVoulue et de leur possibilité
			p.qttLivrablePrix();
			for (Devis d : l){
				this.journal.ajouter("Le producteur "+d.getProd()+" veut "+d.getQttLivrable());
			}
		}
		for (IContratTrans t : this.getDemandeurs()){     // Transfo modifient la qttFinale qui sera la valeur recu chaque next pdt 1an.
			t.finContrat();
			for (Devis d : l){
				this.journal.ajouter("Le devis final est "+d.getQttFinale()+ " entre " + d.getProd() +" et "+d.getTrans()+" au prix de "+d.getPrix());
			}
		}
		for (Devis d : l){								// On bloque les setters des variables. 
			d.setVerrouillage();
		}
		for(IContratProd p : this.getProducteurs()){   // Indique aux prod que les nego sont finis, ils peuvent donc récupérer l'info de la qttFinale
			p.notifContrat();
		}
		this.demandeurs=new ArrayList<IContratTrans>();
	}
		

}
