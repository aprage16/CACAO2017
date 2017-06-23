package abstraction.producteur.cotedivoire;
import java.util.ArrayList; 

public class Treso {
	//modifie by @antoineroson
	//by fcadre
	private ArrayList<Integer> benefmensuel;
	private double ca; // Chiffre d'affaire sur la période d'un next
	private double benefice;  
	public static final int COUTS = 474; //coûts de logistique par tonne de cacao produite 
	
	//On initialise la tréso de notre acteur à 0
	public Treso(){
		this.ca = 0;  
		this.benefice = 0; 
	}
	
	//Getter du chiffre d'affaire 
	public double getCa(){ 
		return this.ca; 
	}
	
	//Methode permettant d'ajouter le produit des ventes à la tréso 
	public void addBenef(double a){
		this.ca += a; 
	}
}
