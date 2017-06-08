package abstraction.producteur.ameriquelatine;
//Modifié par lolotteisyoung 14/04/2017
//Modifié par lolotteisyoung 24/04/2017
//26/04 Adrien

public class Tresorerie {
	public final static int CHARGESPROD=5000000;// ($) charges fixes (hors coût de stock)
	public final static double COUTSTOCK=100; // (plutôt 500~1000 dans la réalité)
	public final static double COUTSALARIE=15800; // realite (47 400) ($) prix d'un salarie (recolte) par tonne de cacao récolté et par an
	private double tresorerie; // argent en banque
	private Stock stock;
	private Recolte recolte;
	

	public Tresorerie(Stock stock, Recolte recolte){
		this.stock = stock ;
		this.tresorerie = 10000 ;
		this.recolte=recolte;
	}
	public double getTresorerie(){
		 return this.tresorerie ;
	}
	public double cout(){
		return CHARGESPROD + stock.getStock()*COUTSTOCK+this.recolte.getQterecoltee()*COUTSALARIE/26; //le cout du salarie est par AN !! Ce pourquoi on doit diviser par 26 (nombre de next par an)
	}
	public void encaissement(double a){
		this.tresorerie+=a;
	}
	public void decaissement(double a){
		this.tresorerie-=a;
	}
}
