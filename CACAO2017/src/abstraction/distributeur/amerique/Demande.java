package abstraction.distributeur.amerique;

import abstraction.fourni.Monde;

public class Demande {
	

	static double augStVal; //3%
	static double augPaques;// 4% des ventes de chocolat au total
	static double augHalloween; //10%
	static double augNoel; // 8% des ventes de chocolat au total
	static double demandeTAnnee=7.5*Math.pow(10,2)-augStVal-augPaques-augHalloween-augNoel;
	
	private double commande;
	
	public Demande(double commande){
		this.commande=commande;
	}
	

	public double getCommande(){
		return this.commande;
	}
	 
	public void setCommande(double commande){
		this.commande=commande;
	}

	
	
	/*Donne la demande du step en cours en fonction de la date et de différents paramètres, un peu d'aléatoire ?
	public double demandeStep1(){
		return demandeKgAnnee/24;
	}*/
	
	/*Prend en compte les fêtes*/
	public double demandeStep(){
		double augSaisonniere=0;
		int date=Monde.LE_MONDE.getStep();
		double coefaleatoire=0.9+Math.random()*0.2; //coefficient aléatoire permettant de faire varier la demande "linéaire" périodique
		date=Monde.LE_MONDE.getStep();
		date=date%24;
		if (date==2 || date==3){
			augSaisonniere=augStVal;
		}
		if (date==7 || date==8){
			augSaisonniere=augPaques;
		}
		if (date==20 || date==21){
			augSaisonniere=augHalloween;
		}
		if (date==22 || date==23){
			augSaisonniere=augNoel;
		}
		
		return ((demandeTAnnee/24)+augSaisonniere)*coefaleatoire;
	}
	
}
