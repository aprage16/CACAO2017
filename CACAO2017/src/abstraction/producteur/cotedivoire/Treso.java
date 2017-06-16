package abstraction.producteur.cotedivoire;
import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde; 

public class Treso {
	//modifie by @antoineroson
	//by fcadre
	private ArrayList<Integer> benefmensuel;
	private Indicateur ca; // Chiffre d'affaire sur la période d'un next
	private double benefice;  
	public static final int COUTS = 474; //coûts de logistique par tonne de cacao produite 
	private Acteur a;
	
	//On initialise la tréso de notre acteur à 0
	public Treso(Acteur a){
		this.ca = new Indicateur ("6_PROD_COT_tresorerie",a,0.0);
		Monde.LE_MONDE.ajouterIndicateur( this.ca ); 
		this.a=a;
		this.benefice = 0; 
	}
	
	//Getter du chiffre d'affaire 
	public double getCa(){ 
		return this.ca.getValeur(); 
	}
	
	//Methode permettant d'ajouter le produit des ventes à la tréso 
	public void addBenef(double b){
		this.ca.setValeur(this.a, b+ this.ca.getValeur());
	}
}
