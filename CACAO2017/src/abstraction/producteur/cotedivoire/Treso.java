package abstraction.producteur.cotedivoire;
import java.util.ArrayList; 

public class Treso {
	//modifie by @antoineroson
	//by fcadre
	private ArrayList<Integer> benefmensuel;
	private double ca; // Chiffre d'affaire sur la période d'un next
	private double coutdeprod; //Cout de production au kilo
	private double coutacheminement; //Cout d acheminement
	private double coutmaindoeuvre; 
	private double benefice;  
	
	public Treso(){
		this.ca = 0; 
		this.coutdeprod = 0; 
		this.coutacheminement = 0;
		this.coutmaindoeuvre = 0; 
		this.benefice = 0; 
	}
	
	public double getCa(){ 
		return this.ca; 
	}
	public double getCoutdeprod(){ 
		return this.coutdeprod; 
	}
	public double getCoutacheminement(){ 
		return this.coutacheminement; 
	}
	public double getCoutmaindoeuvre(){ 
		return this.coutmaindoeuvre; 
	}
	public double getBenefice(){ 
		return this.benefice;  
	}

	public void addCa(double a){
		this.ca+=a;
	}
	
}
