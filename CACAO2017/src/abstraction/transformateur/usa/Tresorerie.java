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
		if(montant>=0){
			this.comptecourant+=montant;
		}
		else{
			throw new Error();
		}
	}

	public void removeMoney(double montant){
		if (montant>=0){
			this.comptecourant-=montant;
		}
		else{
			throw new Error();
		}
		
	}

	public double getCompteCourant(){
		return this.comptecourant;
	}

}
