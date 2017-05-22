package abstraction.producteur.ameriquelatine;
//Modifié par lolotteisyoung 14/04/2017
//Modifié par lolotteisyoung 24/04/2017
//26/04 Adrien

public class Tresorerie {
	public final static int CHARGESPROD=10000000;// ($) charges fixes (hors coût de stock)
	public final static double COUTSTOCK=1000;
	public final static double COUTSALARIE=47400; // ($) prix d'un salarie (recolte) par tonne de cacao récolté produite et par an
//	private double resultat; 
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
		return CHARGESPROD + stock.getStock()*COUTSTOCK+this.recolte.getQterecoltee()*COUTSALARIE; //on paye le cout du stock le mois d'après !!!
	}
	public void encaissement(double a){
		this.tresorerie+=a;
	}
	public void decaissement(double a){
		this.tresorerie-=a;
	}
}
