package abstraction.transformateur.europe;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.interfacemarche.transformateur;;

public class Transformateur implements transformateur, Acteur  {

	private String nom;
	private Stock s;
	private Tresorerie compte;
	private double prixmin=4000;
	
	private Indicateur stockchocolat;
	private Indicateur tresorerie;
	
	
	
	public Transformateur (Stock s, Tresorerie compte){
		this.s=s;
		this.compte=compte;
		this.stockchocolat=new Indicateur("3_TRAN_EU_stock",this,this.s.getStockChocolat());
		this.tresorerie=new Indicateur("3_TRAN_EU_solde",this,this.compte.getCompte());
		Monde.LE_MONDE.ajouterIndicateur( this.stockchocolat );
		Monde.LE_MONDE.ajouterIndicateur( this.tresorerie );
	}
	
	public Transformateur(){
		this(new Stock(),new Tresorerie());
	}
	
	public double getprixMin() {
		double a = 1000*Math.random();
		double stockchocolat=this.s.getStockChocolat();
		if (stockchocolat<100){
			return 10000;
		}
		else{
			this.prixmin+=a;
			return this.prixmin;
		}
		// TODO Auto-generated method stub
	}
	
	public void notif(double prix, double quantité) {
		// TODO Auto-generated method stub
		this.s.setstockchocolat(this.s.getStockChocolat()-quantité);
		double chiffre_daffaire=prix*quantité;
		this.compte.ajoutChiffredaffaire(chiffre_daffaire);
		this.tresorerie.setValeur(this, this.compte.getCompte());
	}
	
	public int hashCode() {
		return this.getNom().hashCode();
	}
	
	public String getNom() {
		return "Transformateur "+this.nom;
	}
	
	public Stock getStock(){
		return this.s;
	}
	
	public double QteSouhaite(){
		double stockCacao=this.s.getStockCacao();
		double stockMax=this.s.getStockMax();
		return stockMax-stockCacao; //test
		
	}
	
	public void Transformation(){
		this.s.setstockchocolat(this.s.getStockCacao()*0.6);
		this.s.setstockcacao(0);
	}
	
	public void notificationAchat(double prix, double quantite){
		this.s.setstockcacao(quantite);
		double achat = prix*quantite;
		this.compte.retraitAchat(achat);
		this.stockchocolat.setValeur(this, this.s.getStockChocolat());
		this.tresorerie.setValeur(this, this.compte.getCompte());
	}
		
	public void next(){
		Transformation();
	}
}
