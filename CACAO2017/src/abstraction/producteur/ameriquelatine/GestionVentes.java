package abstraction.producteur.ameriquelatine;
//modifié et créé par lolotteisyoung 14/04/2017
//24/06 Adrien

public class GestionVentes {

	private Stock stock;
	private double quantiteVendue;
	private Producteur producteur;
	
	public GestionVentes(Stock stock){
		this.stock=stock;
		this.quantiteVendue=0;
	}
	
	public int getQuantiteMiseEnVente(){
		return (int)(0.8*this.producteur.stockintermediaire());
	}
	
	public double getQuantiteVendue(){
		return this.quantiteVendue;
	}
	public void setQuantiteVendue(double qteVendue){
		this.quantiteVendue=qteVendue;
	}
}