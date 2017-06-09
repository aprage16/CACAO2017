package abstraction.transformateur.usa;

import java.util.ArrayList;

public class Decision {
	private ArrayList<Double> historiqueAchat;
	private ArrayList<Double> historiqueVente;
	
	public Decision(){
		historiqueAchat = new ArrayList<Double>();
		historiqueVente = new ArrayList<Double>();
		for(int i=0;i<12;i++){
			historiqueAchat.add(33000.);
		}
	}
	
	public ArrayList<Double> getHistoriqueAchat(){
		return this.historiqueAchat;
	}
	
	public ArrayList<Double> getHistoriqueVente(){
		return this.historiqueVente;
	}

	public void ajouterAchat(double quantite){
		if (this.getHistoriqueAchat().size()<12){
			this.getHistoriqueAchat().add(quantite);
		}
		else{
			this.getHistoriqueAchat().clear();
			this.getHistoriqueAchat().add(quantite);
		}
	}
	
	public void ajouterVente(double quantite){
		if (this.getHistoriqueVente().size()<12){
			this.getHistoriqueVente().add(quantite);
		}
		else{
			this.getHistoriqueVente().clear();
			this.getHistoriqueVente().add(quantite);
		}
	}
	
	public double getQuantiteVoulue(){
		double somme=0;
		for (double s:historiqueAchat){
			somme+=s;
		}
		return (0.7*(somme/this.historiqueAchat.size()));
	}
	
	public double getStockDesire(){
		double maxVente=0;
		for (int i=0;i<this.historiqueVente.size();i++){
			if (this.historiqueVente.get(i)>maxVente){
				maxVente=this.historiqueVente.get(i);
			}
		}
		return maxVente*1.2;
	}
	public String toString(){
		String s="";
		for (int i=0;i<this.historiqueAchat.size();i++){
			s+=" ---- "+this.historiqueAchat.get(i);
		}
		return s+"--- Quantité Voulue = "+this.getQuantiteVoulue();
	}
	
	
	
}
