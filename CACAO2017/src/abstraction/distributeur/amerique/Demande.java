package abstraction.distributeur.amerique;

import abstraction.fourni.Monde;

public class Demande {
	

	public static double augStVal=0.3; //3%
	public static double augPaques=0.4;// 4% des ventes de chocolat au total
	public static double augHalloween; //10%
	public static double augNoel=0.8; // 8% des ventes de chocolat au total
	public static double partDeMarche=0.30;
	public static double demandeTAnnee=2470;
	public static double demandeMoisBase=(demandeTAnnee-demandeTAnnee*(augStVal+augPaques+augHalloween+augNoel))/26;
	public static double commandeIni = 62.5;
	
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
		date=date%26;
		if (date==2){
			augSaisonniere=augStVal;
		}
		if (date==7){
			augSaisonniere=augPaques;
		}
		if (date==20){
			augSaisonniere=augHalloween;
		}
		if (date==26){
			augSaisonniere=augNoel;
		}
		
		return (demandeMoisBase+augSaisonniere)*coefaleatoire;
	}
	
}
