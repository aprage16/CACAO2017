package abstraction.contrats;

public class Devis {
	private IContratProd nomProd; 
	private IContratTrans nomTrans; 
	public double qttVoulue;
	public double qttLivrable; 
	public double qttFinale; 
	public double prix; 
	
	
	public Devis(IContratProd nomProd, IContratTrans nomTrans) {
		this.nomProd=nomProd; 
		this.nomTrans=nomTrans; 
		this.qttVoulue=0; 
		this.qttLivrable=0; 
		this.qttFinale=0; 
		this.prix=0;
	}


	public double getQttVoulue() {
		return qttVoulue;
	}


	public void setQttVoulue(double qttVoulue) {
		this.qttVoulue = qttVoulue;
	}


	public double getQttLivrable() {
		return qttLivrable;
	}


	public void setQttLivrable(double qttLivrable) {
		this.qttLivrable = qttLivrable;
	}


	public double getQttFinale() {
		return qttFinale;
	}


	public void setQttFinale(double qttFinale) {
		this.qttFinale = qttFinale;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}


	public IContratProd getProd() {
		return this.nomProd;
	}


	public void setNomProd(IContratProd nomProd) {
		this.nomProd = nomProd;
	}


	public IContratTrans getTrans() {
		return this.nomTrans;
	}


	public void setNomTrans(IContratTrans nomTrans) {
		this.nomTrans = nomTrans;
	}
	
	
}
