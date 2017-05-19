package abstraction.transformateur.europe;

public class Tresorerie {

	private double compte;
	
	public Tresorerie(double compte){
		this.compte=compte;
	}
	
	public Tresorerie(){
		this(0);
	}
	
	public double getCompte(){
		return this.compte;
	}
	
	public void setCompte(double compte){
		this.compte=compte;
	}
	
	public void credit(double montant){
		if (montant >=0){
			this.setCompte(this.getCompte()+montant);
		}
	}
		
	
	public void debit(double montant){
		if(montant>=0){
			this.setCompte(this.getCompte()-montant);
		}
	}
}
