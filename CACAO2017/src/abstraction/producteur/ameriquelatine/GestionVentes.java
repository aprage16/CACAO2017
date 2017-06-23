package abstraction.producteur.ameriquelatine;
//modifié et créé par lolotteisyoung 14/04/2017
//24/06 Adrien

public class GestionVentes {

	private Stock stock;
	private double quantiteVendue;
	
	public GestionVentes(Stock stock){
		this.stock=stock;
		this.quantiteVendue=0;
	}
}