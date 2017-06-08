package abstraction.transformateur.usa;

import java.util.ArrayList;

public class Decision {
	private ArrayList<Double> Historique;
	
	public Decision(){
		Historique = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			Historique.add(33000.);
		}
	}
	
	public ArrayList<Double> getHistorique(){
		return this.Historique;
	}

	public void ajouterAchat(double quantite){
		if (this.getHistorique().size()<12){
			this.getHistorique().add(quantite);
		}
		else{
			this.getHistorique().clear();
			this.getHistorique().add(quantite);
		}
	}
	
	public double getQuantiteVoulue(){
		double somme=0;
		for (double s:Historique){
			somme+=s;
		}
		return (0.7*somme/this.Historique.size());
	}
	public String toString(){
		String s="";
		for (int i=0;i<this.Historique.size();i++){
			s+=" ---- "+this.Historique.get(i);
		}
		return s+"--- Quantité Voulue = "+this.getQuantiteVoulue();
	}
	
	
	
}
