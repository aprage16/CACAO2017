package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import abstraction.distributeur.europe.IDistributeur;
import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheClients implements Acteur{
	
	
	public static double satisfactionIni=0.0;
	public static double prixVenteIni;
	public static double augStVal; //3%
	public static double augPaques;// 4% des ventes de chocolat au total
	public static double augHalloween; //10%
	public static double augNoel; // 8% des ventes de chocolat au total
	public static double nbStepAnnees=26;
	public static double commandesAnneeTotales=6000;
	public static double commandesStepFixe=(commandesAnneeTotales-commandesAnneeTotales*(augStVal+augPaques+augHalloween+augNoel))/nbStepAnnees;
	public static double pourcentageFixeTotal=0.8;
	public static double pourcentageFluctuantTotal=0.2;

	private HashMap<DistribClient, Double> satisfactions;//Hashmap met en relation deux objets ici le distributeur et la satisfaction
	
 	
	public MarcheClients(){
		this.satisfactions=new  HashMap<DistribClient, Double>();

	}
	
	public HashMap<DistribClient, Double> getSatisfactions(){
		return this.satisfactions;
	}
	
	public List<DistribClient> getDistributeurs(){
		//	return this.distributeur;
		Set<DistribClient> keys = this.satisfactions.keySet();
		List<DistribClient> res = new ArrayList<DistribClient>();
		for (DistribClient d : keys) {
			res.add(d);
		}
		return res;
	}
	

	
	public void addDistributeur(DistribClient distributeur){
		this.setSatisfactions(distributeur, 0);
	}
	
	public void setSatisfactions(DistribClient distributeur, double vendu){
		this.satisfactions.put(distributeur, vendu);
	}
	
	public HashMap<DistribClient, Double> calculDemandesIni(double somPrix, HashMap<DistribClient, Double> prixVente, double somSatisfactions){
		double augSaisonniere=0;
		HashMap<DistribClient, Double> demandes=new HashMap<DistribClient, Double>();
		int date=Monde.LE_MONDE.getStep()%26;
		if (date==4){
			augSaisonniere=augStVal;
		}
		if (date==8){
			augSaisonniere=augPaques;
		}
		if (date==22){
			augSaisonniere=augHalloween;
		}
		if (date==26){
			augSaisonniere=augNoel;
		}
		
		double commandesTotalesStep=(commandesStepFixe+augSaisonniere*commandesAnneeTotales)*DistributeurUS.coefAleatoire;
		
		for (int k=0;k<this.getDistributeurs().size();k++){
			double pourcentageFixe=pourcentageFixeTotal*(1/this.getDistributeurs().size());
			double p=(somPrix-prixVente.get(this.getDistributeurs().get(k)))/somPrix;
			double s=this.getSatisfactions().get(this.getDistributeurs().get(k))/this.somSatisfactions();
			
			double pourcentageFluct=pourcentageFluctuantTotal*(0.5*p+0.3*s+0.2*DistributeurUS.coefAleatoire);
			demandes.put(this.getDistributeurs().get(k), commandesTotalesStep*(pourcentageFixe+pourcentageFluct));
			
		}
		return demandes;
	}
	


	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "marcheClients";
	}


	public HashMap<DistribClient, Double> getPrixVente() {
		HashMap<DistribClient, Double> prixVente;
		prixVente=new HashMap<DistribClient, Double>();
		for (int k=0;k<this.getDistributeurs().size();k++){
			prixVente.put(this.getDistributeurs().get(k), this.getDistributeurs().get(k).getPrixClient());
		}
		return prixVente;
	}
	
	public double somPrix(){
		double somPrix=0;
		for (int k=0;k<this.getDistributeurs().size();k++){
			somPrix+=this.getPrixVente().get(this.getDistributeurs().get(k));
		}
		return somPrix;
	}
	
	public double somSatisfactions(){
		double somSatisfactions=0;
		for (int k=0;k<this.getDistributeurs().size();k++){
			somSatisfactions+=this.getSatisfactions().get(this.getDistributeurs().get(k));
		}
		return somSatisfactions;
	}
	
	public HashMap<DistribClient, Double> getMisEnVente(){
		HashMap<DistribClient, Double> misEnVente=new HashMap<DistribClient, Double>();
		for (int k=0;k<this.getDistributeurs().size();k++){
			misEnVente.put(this.getDistributeurs().get(k), this.getDistributeurs().get(k).getMisEnVente());
		}
		return misEnVente;
	}
	
	public boolean restVente(HashMap<DistribClient, Double> misEnVente){
		for (int k=0; k<this.getDistributeurs().size();k++){
			if (misEnVente.get(this.getDistributeurs().get(k))!=0.0){
				return true;
			}
		}return false;
	}
	
	public boolean restDem(HashMap<DistribClient, Double> demandes){
		for (int k=0;k<this.getDistributeurs().size();k++){
			if (demandes.get(this.getDistributeurs().get(k))!=0){
				return true;
			}
		}return false;
	}
	
	
	public void distribDem(HashMap<DistribClient, Double> misEnVente, HashMap<DistribClient, Double> demandes, HashMap<DistribClient, Double> nonHonoree, double pasHonoree){
		int nbEnLice=0;
		ArrayList<DistribClient> enLice = new ArrayList<DistribClient>();
		for (int k=0;k<this.getDistributeurs().size();k++){//On teste tout les distribs, pour voir ceux qui vendent encore
			if (misEnVente.get(this.getDistributeurs().get(k))!=0){
				nbEnLice+=1;
				enLice.add(this.getDistributeurs().get(k));
			}
		}
		if (nbEnLice!=0){
			double commande=pasHonoree/nbEnLice;
			for (int k=0;k<nbEnLice;k++){
				demandes.put(enLice.get(k), commande);
				}
			}
		}
		
	
	
	public void next() {
		//Récupération des prix :
		HashMap<DistribClient, Double> prixVente=getPrixVente();
		
		//Calcul de la demande de chaque distrib:
		double somPrix=this.somPrix();
		double somSatisfactions=this.somSatisfactions();
		HashMap<DistribClient, Double> demandes=this.calculDemandesIni(somPrix, prixVente, somSatisfactions);
		
		// On demande les quantités mises en vente :
		HashMap<DistribClient, Double> misEnVente=getMisEnVente();
		
		//On fait un premier round, on fait tout vendre dans un premier temps en s'arrêtant au plus tôt :
		//On garde la demande non honorée : et on mémorise les satisfactions
		
		
		HashMap<DistribClient, Double> nonHonoree=new HashMap<DistribClient, Double>();
		double pasHonoree=0;
		
		boolean firstTurn=true;
		
		while (this.restVente(misEnVente) && this.restDem(demandes)){// tant qu'il reste des distributeurs avec du chocolat à vendre et de la demande
			
			for (int k=0;k<this.getDistributeurs().size();k++){
				
				
				if (misEnVente.get(this.getDistributeurs().get(k))>=demandes.get(this.getDistributeurs().get(k))){
					this.getDistributeurs().get(k).notifVente(new Vente(prixVente.get(this.getDistributeurs().get(k)),demandes.get(this.getDistributeurs().get(k))));
					misEnVente.put(this.getDistributeurs().get(k), misEnVente.get(this.getDistributeurs().get(k))-demandes.get(this.getDistributeurs().get(k)));
					if (firstTurn){
						this.satisfactions.put(this.getDistributeurs().get(k), 1.0);
					}
				}else{
					this.getDistributeurs().get(k).notifVente(new Vente(prixVente.get(this.getDistributeurs().get(k)),misEnVente.get(this.getDistributeurs().get(k))));
					nonHonoree.put(this.getDistributeurs().get(k), demandes.get(this.getDistributeurs().get(k))-misEnVente.get(this.getDistributeurs().get(k)));
					pasHonoree+=demandes.get(this.getDistributeurs().get(k))-misEnVente.get(this.getDistributeurs().get(k));
					misEnVente.put(this.getDistributeurs().get(k), 0.0);
					if (firstTurn){
						this.satisfactions.put(this.getDistributeurs().get(k), 1-(nonHonoree.get(this.getDistributeurs().get(k))/demandes.get(this.getDistributeurs().get(k))));
					}
				} 
				demandes.put(this.getDistributeurs().get(k), 0.0);
			} 
			
			this.distribDem(misEnVente, demandes, nonHonoree, pasHonoree);
		
		}
		
		
		
		
		
		
	}
	
	
}