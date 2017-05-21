package abstraction.transformateur.europe;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.interfacemarche.transformateur;;

public class Transformateur implements transformateur, Acteur  {

	private String nom;
	private Stock s;
	private Tresorerie compte;
	private double prixmin;
	private double[] peremption=new double[Stock.DATE_PEREMPTION];
	public static final int CACAO_NECESSAIRE = 30800; //stock necessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 44000; //stock necessaire par mois à vendre (calculé selon la demande européenne)
	public static final int STOCK_MIN=5000;
	public static final double RATIO_CACAO_CHOCO=0.7;
	public static final int PRIX_MIN=20;
	
	
	private Indicateur stockChocolat;
	private Indicateur tresorerie;
	private Indicateur commande;
	
	
	public Transformateur (Stock s, Tresorerie compte){
		this.s=s;
		this.compte=compte;
		this.stockChocolat=new Indicateur("3_TRAN_EU_stock_chocolat",this,this.s.getStockChocolat());
		this.commande=new Indicateur("3_TRAN_EU_commande_actuelle",this,0.0);
		this.tresorerie=new Indicateur("3_TRAN_EU_solde",this,this.compte.getCompte());
		Monde.LE_MONDE.ajouterIndicateur( this.stockChocolat );
		Monde.LE_MONDE.ajouterIndicateur( this.commande );
		Monde.LE_MONDE.ajouterIndicateur( this.tresorerie );
	}
	
	public Transformateur(){
		this(new Stock(),new Tresorerie());
	}
	
	public double getprixMin() {
		double stockchocolat=this.s.getStockChocolat();
		if (stockchocolat<STOCK_MIN){
			return 1000000000;
		}
		else{
			this.prixmin=PRIX_MIN+PRIX_MIN*STOCK_MIN/s.getStockChocolat(); //
			return this.prixmin;
		}
	}
	
	public void notif(double prix, double quantite) {
		this.s.retraitChocolat(quantite);
		double chiffredaffaire=prix*quantite;
		this.compte.credit(chiffredaffaire);
		this.tresorerie.setValeur(this, this.compte.getCompte());
		
	}
	
	public int hashCode() {
		return this.getNom().hashCode();
	}
	
	public String getNom() {
		return "Transformateur EUROPE";
	}
	
	public Stock getStock(){
		return this.s;
	}
	
	public double QteSouhaite(){
		double stockCacao=this.s.getStockCacao();
		double stockChocolat=this.s.getStockChocolat();
		double quantiteSouhaitee;
		if (stockChocolat<=CHOCOLAT_NECESSAIRE && stockChocolat < Stock.STOCK_MAX_CHOCOLAT){ //on vérifie si notre stock de chocolat est inférieur a la qte qu'on vend par mois
			if (stockCacao>=CACAO_NECESSAIRE){ //On vérifie si le cacao nécessaire pour atteindre notre objectif de chocolat est présent ou non, s'il l'est on achète rien
				quantiteSouhaitee=0;
			}else{
				quantiteSouhaitee=CACAO_NECESSAIRE-stockCacao; //on achète ce qui est suffisant pour produire CHOCOLAT_NECESSAIRE tonnes de chocolat
			}
		}else{
			return quantiteSouhaitee=0; //on achète rien si on a trop de chocolat par rapport à ce que l'on vend
		}
		this.commande.setValeur(this, quantiteSouhaitee); //l'indicateur donne la quantité commandée au producteurs pendant le next
		return quantiteSouhaitee;
	}
	
	public void transformation(){
		if (this.s.getStockChocolat()<=CHOCOLAT_NECESSAIRE){ //on vérifie que stock actuel <= Stock max
			this.s.ajoutChocolat(CHOCOLAT_NECESSAIRE-this.s.getStockChocolat()); //on remplit notre stock tout le temps de sorte à avoir 44000
			this.s.retraitCacao(CACAO_NECESSAIRE-this.s.getStockCacao());
		}
	}
	
	public void modifPeremption(){
		double[] peremp=new double[peremption.length];
		double estPerime=peremption[5];
		peremp[0]=this.s.getStockCacao()*RATIO_CACAO_CHOCO;
		for (int i=0;i<peremp.length-1;i++){
			peremp[i+1]=peremption[i];
		}
		peremption=peremp;
		this.s.retraitChocolat(estPerime);
	}
	
	public void notificationAchat(double prix, double quantite){
		this.s.ajoutCacao(quantite);
		double achat = prix*quantite;
		this.compte.debit(achat); //mettre cette ligne en commentaire pour observer la tréso 
					// le retrait de cette ligne désactive le payement aux producteurs: on ne gagne que 11000€
		                          // de ventes alors qu'on paye 10^7 : unités à revoir
		this.stockChocolat.setValeur(this, this.s.getStockChocolat());
		this.tresorerie.setValeur(this, this.compte.getCompte());
	}
		
	public void next(){
		transformation();
		modifPeremption();
	}
}
