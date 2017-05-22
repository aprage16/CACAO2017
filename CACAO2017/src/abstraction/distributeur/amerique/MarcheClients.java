package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import abstraction.distributeur.europe.IDistributeur;

public class MarcheClients {
	public static double satisfactionIni=0.0;
	public static double prixVenteIni;
	
	private HashMap<IDistributeur, Double> satisfactions;//Hashmap met en relation deux objets ici le distributeur et la satisfaction
	private HashMap<IDistributeur, Double> prixVente;
	

	public MarcheClients(){
		this.satisfactions=new  HashMap<IDistributeur, Double>();
		this.prixVente=new HashMap<IDistributeur, Double>();
		
	}

	public List<IDistributeur> getDistributeur(){
		//	return this.distributeur;
		Set<IDistributeur> keys = this.satisfactions.keySet();
		List<IDistributeur> res = new ArrayList<IDistributeur>();
		for (IDistributeur d : keys) {
			res.add(d);
		}
		return res;
	}
	
	public void addDistributeur(IDistributeur distributeur){
		this.setSatisfaction(distributeur, satisfactionIni);
		this.setPrixVente(distributeur, prixVenteIni);
	}
	 
	public void setSatisfaction(IDistributeur distributeur, double satisfaction){
		this.satisfactions.put(distributeur, satisfaction);
	}
	
	public void setPrixVente(IDistributeur distributeur, double prixVente){
		this.prixVente.put(distributeur, prixVente);
	}
	public double getSatisfaction(IDistributeur distributeur) {
		return this.satisfactions.get(distributeur);
	}
}