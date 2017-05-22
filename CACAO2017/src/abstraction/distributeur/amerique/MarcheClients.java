package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import abstraction.distributeur.europe.IDistributeur;

public class MarcheClients {
	public static double fideliteIni=0.0;

	private HashMap<IDistributeur, Double> fidelites;//Hashmap met en relation deux objets ici le distributeur et la fidelite
	

	public MarcheClients(){
		this.fidelites=new  HashMap<IDistributeur, Double>();
		
	}

	public List<IDistributeur> getDistributeur(){
		//	return this.distributeur;
		Set<IDistributeur> keys = this.fidelites.keySet();
		List<IDistributeur> res = new ArrayList<IDistributeur>();
		for (IDistributeur d : keys) {
			res.add(d);
		}
		return res;
	}
	
	public void addDistributeur(IDistributeur distributeur){
		this.setFidelite(distributeur, fideliteIni);
	}
	 
	public void setFidelite(IDistributeur distributeur, double fidelite){
		this.fidelites.put(distributeur, fidelite);
	}
	public double getFidelite(IDistributeur distributeur) {
		return this.fidelites.get(distributeur);
	}
	
}