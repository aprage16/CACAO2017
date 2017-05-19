package abstraction.transformateur.usa;

public class Tresorerie {
	
	private double comptecourant;
	
	public Tresorerie(){
		this.comptecourant=0;
	}
	
	public Tresorerie(double treso){
		this.comptecourant=treso;
	}
	public void setCompteCourant(double comptecourant){
		this.comptecourant=comptecourant;
	}
	
	public void addMoney(double montant){
		this.comptecourant+=montant;
	}
	
	public void removeMoney(double montant){
		this.comptecourant-=montant;
	}
	
	public double getCompteCourant(){
		return this.comptecourant;
	}

}
