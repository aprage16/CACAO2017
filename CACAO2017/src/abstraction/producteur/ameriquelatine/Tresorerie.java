package abstraction.producteur.ameriquelatine;
//Modifié par lolotteisyoung 14/04/2017
//Modifié par lolotteisyoung 24/04/2017
//26/04 Adrien

//import abstraction.fourni.Acteur;
//import abstraction.fourni.Indicateur;
//import abstraction.fourni.Journal;
//import abstraction.fourni.Monde; 


public class Tresorerie {
//private double ca; //chiffres d'affaires de la période

	public final static int CHARGESPROD=10000000;// ($) charges fixes (hors coût de stock)
	public final static double COUTSTOCK=1000;
	private double resultat; 
	private double tresorerie; // argent en banque
	private Stock stock;

	public Tresorerie(Stock stock){
		this.stock = stock ;
		this.tresorerie = 10000 ;
	}
	public double getTresorerie(){
		 return this.tresorerie ;
	}
	public double cout(){
		return CHARGESPROD + stock.getStock()*COUTSTOCK; //on paye le cout du stock le mois d'après !!!
	}
	public void encaissement(double a){
		this.tresorerie+=a;
	}
	public void decaissement(double a){
		this.tresorerie-=a;
	}
}
