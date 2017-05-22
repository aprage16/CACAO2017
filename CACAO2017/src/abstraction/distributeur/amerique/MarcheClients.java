package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import abstraction.distributeur.europe.IDistributeur;

public class MarcheClients {
	public static double fideliteIni=0.0;

	private HashMap<IDistributeur, Double> fidelites;
	//private ArrayList<Double> fidelite;

	public MarcheClients(){
		this.fidelites=new  HashMap<IDistributeur, Double>();//ArrayList<IDistributeur>();
		//	this.fidelite=new ArrayList<Double>();
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
	/*public ArrayList<Double> getFidelite(){
		return this.fidelite;
	}*/
	/*
	public void addDistributeur(IDistributeur distributeur){
		this.getDistributeur().add(distributeur);
		this.getFidelite().add(fideliteIni);
	}
	 */
	public void setFidelite(IDistributeur distributeur, double fidelite){
		this.fidelites.put(distributeur, fidelite);// int index=this.getDistributeur().indexOf(distributeur);
		//this.getFidelite().
	}
	public double getFidelite(IDistributeur distributeur) {
		return this.fidelites.get(distributeur);
	}
}