package abstraction.producteur.ameriquelatine;
//modidifié et créé par swerne16
	// 07/04/2017 modifié par lolotteisyoung
	// 14/04/17 modifié par lolotteisyoung et AnaisBel
//26/04 Adrien
public class Stock {
	
// tout ce qui est produit est stocké, on gère les ventes à partir des stocks.
	private int stock; 


	public Stock() {
		this.stock=1000;
	}
	public void ajout(int a){
		this.stock+=a;
	}
	public void retrait(int a){
		this.stock-=a;
	}
	public void miseAJourStock(int stock) {
		this.stock=stock;
	}
	
	public int getStock(){
		return this.stock;
	}
	
}


	
	
	

