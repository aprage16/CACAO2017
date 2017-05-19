package abstraction.producteur.cotedivoire;
import java.util.ArrayList; 

public class Treso {
	//modifie by @antoineroson
	//by fcadre
	private ArrayList<Integer> benefmensuel;
	private double ca; // Chiffre d'affaire sur la période d'un next
	private double benefice;  
	public static final int COUTS = 474; 
	
	public Treso(){
		this.ca = 0;  
		this.benefice = 0; 
	}
	
	public double getCa(){ 
		return this.ca; 
	}
	public double getBenefice(){ 
		return this.benefice;  
	}

	public void addBenef(double a){
		this.ca += a; 
	}
}
