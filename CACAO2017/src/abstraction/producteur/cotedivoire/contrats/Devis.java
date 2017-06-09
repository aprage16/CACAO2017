package abstraction.producteur.cotedivoire.contrats;

public class Devis {
	private IContratProd nomProd; 
	private IContratTrans nomTrans; 
	public double qttVoulue;
	public double qttLivrable; 
	public double qttFinale; 
	public double prix;
	private boolean deverrouille; 
	
	
	public Devis(IContratProd nomProd, IContratTrans nomTrans) {
		this.nomProd=nomProd; 
		this.nomTrans=nomTrans; 
		this.qttVoulue=0; 
		this.qttLivrable=0; 
		this.qttFinale=0; 
		this.prix=0;
		this.deverrouille=true; 
	}
	
	public void setVerrouillage () {
		this.deverrouille= false;
	}

	public double getQttVoulue() {
		return qttVoulue;
	}


	public void setQttVoulue(double qttVoulue) {
		if (this.deverrouille){
			this.qttVoulue = qttVoulue;
		}
	}


	public double getQttLivrable() {
		return qttLivrable;
	}


	public void setQttLivrable(double qttLivrable) {
		if (this.deverrouille){
			this.qttLivrable = qttLivrable;
		}
	}


	public double getQttFinale() {
		return qttFinale;
	}


	public void setQttFinale(double qttFinale) {
		if (this.deverrouille){
			this.qttFinale = qttFinale;
		}
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		if (this.deverrouille){
			this.prix = prix;
		}
	}


	public IContratProd getProd() {
		return this.nomProd;
	}


	public IContratTrans getTrans() {
		return this.nomTrans;
	}
	
	
}
