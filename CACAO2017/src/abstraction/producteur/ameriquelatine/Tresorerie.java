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
	public final static double COUTSTOCK=100;
	private double resultat; 
	private double tresorerie; // argent en banque
	private Stock stock;

//Méthode qui renvoie un chiffre d'affaire;
//Méthode qui calcule les charges totales;
//Méthode qui actualise la trésorerie;
//qté vendue et cours actuel depuis producteur;
	
	public Tresorerie(Stock stock){
		this.stock = stock ;
		this.tresorerie = 10000 ;
	}

	public double getTresorerie(){
		 return this.tresorerie ;
	}
	
	public double cout(){
		return CHARGESPROD + stock.getInitial()*COUTSTOCK; //on paye le cout du stock le mois d'après !!!
	}
	
	public void setTresorerie(double tresorerie){
		this.tresorerie = tresorerie ;
	}
}
