package abstraction.producteur.ameriquelatine;
//modidifié et créé par swerne16
	// 07/04/2017 modifié par lolotteisyoung
	// 14/04/17 modifié par lolotteisyoung et AnaisBel
//26/04 Adrien
public class Stock {
	
// tout ce qui est produit est stocké, on gère les ventes à partir des stocks.
	private int initial; //stock initial
	private Recolte recolte; //récolte de la période
	private GestionVentes vente;
	

	public Stock(Recolte recolte) {
		this.initial=1000;
		this.recolte=recolte ;
	}
	
	public void setGestionVente(GestionVentes vente){
		this.vente=vente;
	} // Pq modifier gestion Vente?
	
	public void miseAJourStock(int stock) {
		this.initial=stock;
	}
	
	public int getInitial(){
		return this.initial;
	}
	
}


	
	
	

