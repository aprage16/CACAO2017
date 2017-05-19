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
	public static final int CACAO_NECESSAIRE = 30800; //stock necessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 44000; //stock necessaire par mois à vendre (calculé selon la demande européenne)
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
		this.s.retraitChocolat(quantité);
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
		double stockChocolat=this.s.getStockChocolat();
		if (stockChocolat<=CHOCOLAT_NECESSAIRE && stockChocolat < Stock.STOCK_MAX_CHOCOLAT){ //on vérifie si notre stock de chocolat est inférieur a la qte qu'on vend par mois
			if (stockCacao>=(CHOCOLAT_NECESSAIRE-stockChocolat)*0.7){ //On vérifie si le cacao nécessaire pour atteindre notre objectif de chocolat est présent ou non, s'il l'est on achète rien
				return 0;
			}else{
				return ((CHOCOLAT_NECESSAIRE-stockChocolat)*0.7)-stockCacao; //on achète ce qui est suffisant pour produire CHOCOLAT_NECESSAIRE tonnes de chocolat
			}
		}else{
			return 0; //on achète rien si on a trop de chocolat par rapport à ce que l'on vend
		}
		
	}
	
	public void Transformation(){
		this.s.ajoutChocolat(this.s.getStockCacao()*0.7);
		this.s.retraitChocolat(this.s.getStockCacao());
	}
	
	public void notificationAchat(double prix, double quantite){
		this.s.ajoutCacao(quantite);
		double achat = prix*quantite;
		this.compte.retraitAchat(achat);
		this.stockchocolat.setValeur(this, this.s.getStockChocolat());
		this.tresorerie.setValeur(this, this.compte.getCompte());
	}
		
	public void next(){
		Transformation();
	}
}
