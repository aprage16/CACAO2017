package abstraction.distributeur.europe;

public class Vente {
	
	@Override
	public String toString() {
		return "Vente [prix=" + prix + ", quantite=" + quantite + "]";
	}

	private double prix;
	private double quantite;
	
	public Vente(double p, double q){
		prix=p;
		quantite=q;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	
}
