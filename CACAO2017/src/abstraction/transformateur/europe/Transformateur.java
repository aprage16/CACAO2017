
//** Classe gérant notre acteur globalement (intéractions avec le marché, processus de transformation du cacao en chocolat,
//**										 ventes, achat, péremption, indicateurs et notifications)
//** authors : Blois Philippe, 
//**           Charloux Jean, 
//**           Halzuet Guillaume,
//**		   Stourm Théo ***///


package abstraction.transformateur.europe;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.interfacemarche.transformateur;

public class Transformateur implements transformateur, Acteur  {

	private Stock s;
	private Tresorerie compte;
	private double prixmin;
	private double[] peremption=new double[Stock.DATE_PEREMPTION];
	public static final int CACAO_NECESSAIRE = 30800; //stock necessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 44000; //stock necessaire par mois à vendre (calculé selon la demande européenne)
	public static final double RATIO_CACAO_CHOCO=0.7;
	public static final double PRIX_MIN=0.004;
	private Journal journal;
	private Indicateur stockChocolat;
	private Indicateur tresorerie;
	private Indicateur commande;
	
	public Transformateur (Stock s, Tresorerie compte){
		this.s=s;
		this.compte=compte;
		this.stockChocolat=new Indicateur("3_TRAN_EU_stock_chocolat",this,this.s.getStockChocolat());
		this.commande=new Indicateur("3_TRAN_EU_commande_actuelle",this,0.0);
		this.tresorerie=new Indicateur("3_TRAN_EU_solde",this,this.compte.getCompte());
		this.journal = new Journal("Journal de Transformateur Europe");
		Monde.LE_MONDE.ajouterIndicateur( this.stockChocolat );
		Monde.LE_MONDE.ajouterIndicateur( this.commande );
		Monde.LE_MONDE.ajouterIndicateur( this.tresorerie );
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	public Transformateur(){
		this(new Stock(),new Tresorerie());
	}
	
	public double getprixMin() {
		double stockChocolat=this.s.getStockChocolat();
		if (stockChocolat<Stock.STOCK_MIN){ // on se fixe un stock minimum de "secours" et si on le dépasse on renvoie une valeur qui doit couper la boucle du marché
			return 1000000;
		}
		else{
			this.prixmin=PRIX_MIN+PRIX_MIN*Stock.STOCK_MIN/stockChocolat; //calcul le nouveau prix minimum auquel on souhaite vendre en 
																		  //tenant compte du stock de chocolat que l'on a
			return this.prixmin;
		}
	}
	
	
	public void notif(double prix, double quantite) {
		//System.out.println("vendu au prix de : "+prix+" avec une quantité de : "+quantite);
		this.s.retraitChocolat(quantite);
		double chiffreAffaire=prix*quantite;
		this.compte.credit(chiffreAffaire);
		this.tresorerie.setValeur(this, this.compte.getCompte());
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color=\"green\">"+quantite+"</font></b> de chocolat a été vendu au <b>prix unitaire</b> de : <font color=\"green\"> "+prix+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
		this.journal.ajouter(" ");
		this.journal.ajouter(this.s.toString());
		this.journal.ajouter(" ");
		this.journal.ajouter(this.compte.toString());
		this.journal.ajouter(" ");
		this.journal.ajouter(" ");
		this.journal.ajouter(" ");
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
	
	public void transformation(){ //processus de transformation du cacao en chocolat, appellée chaque next
		if (this.s.getStockChocolat()<=CHOCOLAT_NECESSAIRE){ //on vérifie que stock actuel <= Stock max
			this.s.ajoutChocolat(CHOCOLAT_NECESSAIRE-this.s.getStockChocolat()); //on remplit notre stock tout le temps de sorte à avoir 44000
			this.s.retraitCacao(CACAO_NECESSAIRE-this.s.getStockCacao()); //retrait du cacao nécessaire à la transformation
		}
	}
	
	public void modifPeremption(){ // on considère notre stock de chocolat perissable en 10 semaines, le stockage dans une liste permet de 
								  // supprimer la quantité produite il y a 10 semaines de notre stock 
		double[] peremp=new double[peremption.length];
		double estPerime=peremption[4];
		peremp[0]=this.s.getStockCacao()*RATIO_CACAO_CHOCO;
		for (int i=0;i<peremp.length-1;i++){
			peremp[i+1]=peremption[i];
		}
		peremption=peremp;
		//System.out.println(peremp[4]);
		//System.out.println(estPerime);
		this.s.retraitChocolat(estPerime);
	}
	
	public void notificationAchat(double prix, double quantite){
		this.s.ajoutCacao(quantite);
		double achat = prix*quantite;
		//this.compte.debit(achat); //mettre cette ligne en commentaire pour observer la tréso 
					// le retrait de cette ligne désactive le payement aux producteurs: on ne gagne que 11000€
		                          // de ventes alors qu'on paye 10^7 : unités à revoir
		this.stockChocolat.setValeur(this, this.s.getStockChocolat());
		this.tresorerie.setValeur(this, this.compte.getCompte());
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color =\"red\"> "+quantite+"</font></b> de cacao a été acheté au <b>prix unitaire</b> de : <font color=\"red\"> "+prix+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
		this.journal.ajouter(" ");
	}
		
	public void next(){ //passage à l'étape suivante
		transformation();
		//modifPeremption();
		//System.out.println(s.toString());
	}
}
