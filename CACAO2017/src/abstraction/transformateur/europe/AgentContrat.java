package abstraction.transformateur.europe;

import java.util.ArrayList;
import java.util.List;
import abstraction.fourni.*;

public class AgentContrat implements Acteur{

	private List<Devis> l;
	private List<ITransfoContrat> transformateurs;
	private List<IDistriContrat> distributeurs;
	
	public AgentContrat(){
		List<Devis> l = new ArrayList<Devis>();
		this.transformateurs= new ArrayList<ITransfoContrat>(); 
		this.distributeurs= new ArrayList<IDistriContrat>(); 
	}
	
	public List<ITransfoContrat> getTransformateurs() {
		return this.transformateurs;
	}
	
	public List<IDistriContrat> getDistributeurs() {
		return this.distributeurs;
	}
	
	public void addTransfo(ITransfoContrat t){
		this.getTransformateurs().add(t);
	}
	
	public void addDistri(IDistriContrat d){
		this.getDistributeurs().add(d);
	}
	
	public void next(){
		for(ITransfoContrat t : this.getTransformateurs()) {     // Création de l'ensemble des devis.
			for (IDistriContrat d : this.getDistributeurs()){
				Devis devis=new Devis(t,d);
				l.add(devis);
				t.propositionInitiale(devis);
				d.receptionDevis(devis);
			}
		}
		for (IDistriContrat d : this.getDistributeurs()){ 
			d.demandeQuantite();
		}
		for(ITransfoContrat t : this.getTransformateurs()) {
			t.quantiteFournie();
		}
		for (IDistriContrat d : this.getDistributeurs()){
			d.contreProposition();
		}
		for(ITransfoContrat t : this.getTransformateurs()) { 
			t.acceptationInitiale();
		}
		for (IDistriContrat d : this.getDistributeurs()){
			d.acceptationFinale();
		}
		for(ITransfoContrat t : this.getTransformateurs()) { 
			t.notification();
		}
	}

	@Override
	public String getNom() {
		return "AgentContratTransfoDistri";
	}

				
}
