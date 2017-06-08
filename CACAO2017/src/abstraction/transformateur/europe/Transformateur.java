/* Classe gérant notre acteur globalement 
	- Intéractions avec le marché,
 	- Processus de transformation du cacao en chocolat,
	- Ventes et Achat, 
	- Péremption, 
	- Indicateurs et notifications 

authors : Blois Philippe, 
          Charloux Jean, 
          Halzuet Guillaume,
		  Stourm Théo 
*/


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
	
	private double quantiteVendue=0; // Pour le journal
	private double quantiteAchetee=0;// Pour le journal
	private double qtedemandee;// Pour le journal
	private double prixMoyendeVente=0;// Pour le journal
	private double prixMoyendAchat=0;// Pour le journal 
	private double compteurAchat=0;// Pour le journal 
	private double compteurVente=0;// Pour le journal 
	
	public static final int CACAO_NECESSAIRE = 30800; // Stock nécessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 44000; // Stock nécessaire par mois à vendre (calculé selon la demande européenne)
	public static final double RATIO_CACAO_CHOCO=0.7; // Ratio de transformation entre le cacao et le chocolat
	public static final double PRIX_MIN=0.004; // Prix minimum de vente du chocolat sur le marché
	
	private Journal journal;
	private Indicateur stockChocolat;
	private Indicateur tresorerie;
	private Indicateur commande;

	
	/** 
	 * @objectif: Constructeur de la classe.
	 * 
	 * @param s
	 * @param compte
	 */
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

	
	/**
	 * @objectif: Constructeur par chainage
	 */
	public Transformateur(){
		this(new Stock(),new Tresorerie());
	}

	
	/** 
	 * @return: Le prix minimum de vente sur le marché 
	 * 			en fonction de nos stocks de chocolat.
	 */
	public double getprixMin() {
		double stockChocolat=this.s.getStockChocolat();
		if (stockChocolat<Stock.STOCK_MIN){ // On se fixe un stock minimum de "secours" et si on le dépasse on renvoie une valeur qui doit couper la boucle du marché.
			return 1000000;
		}
		else{
			this.prixmin=PRIX_MIN+PRIX_MIN*Stock.STOCK_MIN/this.s.getStockChocolat(); //calcul le nouveau prix minimum auquel on souhaite vendre
			System.out.println("prix min de transfo eu : "+prixmin);				  // en tenant compte du stock de chocolat que l'on a.
			return this.prixmin;
			}
	}
	
	
	/**
	 * 
	 */
	public int hashCode() {
		return this.getNom().hashCode();
	}

	
	/**
	 * @return le nom de notre transformateur
	 */
	public String getNom() {
		return "Transformateur EUROPE";
	}

	
	/**
	 * @return l'état de nos stocks
	 */
	public Stock getStock(){
		return this.s;
	}

	
	/**
	 * @return La quantité de cacao nécessaire, 
	 * établie en fonction de nos stocks de chocolat
	 */
	public double QteSouhaite(){
		double stockCacao=this.s.getStockCacao();
		double stockChocolat=this.s.getStockChocolat();
		double quantiteSouhaitee;
		if (stockChocolat < Stock.STOCK_MAX_CHOCOLAT){ // On vérifie si notre stock de chocolat est inférieur a la quantité qu'on vend par mois
			if (stockCacao>=CACAO_NECESSAIRE){ // On vérifie si le cacao nécessaire pour atteindre notre objectif de chocolat est présent ou non, s'il l'est on achète rien
				quantiteSouhaitee=0;
			}else{
				quantiteSouhaitee=CACAO_NECESSAIRE-stockCacao; // On achète ce qui est suffisant pour produire CHOCOLAT_NECESSAIRE tonnes de chocolat
			}
		}else{
			return quantiteSouhaitee=0; // On achète rien si on a trop de chocolat par rapport à ce que l'on vend
		}
		this.commande.setValeur(this, quantiteSouhaitee); // L'indicateur donne la quantité commandée au producteurs pendant le next
		this.qtedemandee=quantiteSouhaitee;
		return quantiteSouhaitee;
	}

	
	/**
	 * @objectif: Processus de transformation du cacao en chocolat,
	 *  appellée à chaque next
	 */
	public void transformation(){ 
		if (this.s.getStockChocolat()<CHOCOLAT_NECESSAIRE){// On vérifie que stock actuel <= stock max	
			this.s.ajoutChocolat(this.s.getStockCacao()/RATIO_CACAO_CHOCO); // On remplit notre stock tout le temps de sorte à avoir 44000
			this.s.setStockCacao(0); // Retrait du cacao nécessaire à la transformation
			}
		}
	
	
	
	/**
	 * @objectif: Prendre en compte que le chocolat se périmme
	 * On considère notre stock de chocolat perissable en 10 semaines, 
	 * le stockage dans une liste permet de supprimer la quantité produite 
	 * il y a 10 semaines de notre stock
	 */
	public void modifPeremption(){  
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

	
	/**
	 * @objectif: Fonction utilisée dans le marché, elle nous indique 
	 * combien de cacao nous avons acheté et à quel prix
	 * 
	 * @param quantite, la quantité de cacao achetée
	 * @param prix, le prix unitaire du cacao achetée
	 */
	public void notificationAchat(double quantite, double prix){
		this.s.ajoutCacao(quantite);
		double achat = prix*quantite;
		this.compte.debit(achat); //mettre cette ligne en commentaire pour observer la tréso 
					// le retrait de cette ligne désactive le payement aux producteurs: on ne gagne que 11000€
		                          // de ventes alors qu'on paye 10^7 : unités à revoir
		this.stockChocolat.setValeur(this, this.s.getStockChocolat());
		this.tresorerie.setValeur(this, this.compte.getCompte());
		prixMoyendAchat+=prix;
		compteurAchat+=1;
		quantiteAchetee+=quantite;
	}

	
	/**
	 * @objectif: Fonction utilisée dans le marché, elle nous indique 
	 * combien de chocolat nous avons vendu et à quel prix
	 * 
	 * @param quantite, la quantité de chocolat vendu
	 * @param prix, le prix unitaire du chocolat vendu
	 */
	public void notif(double prix, double quantite) {
		//System.out.println("vendu au prix de : "+prix+" avec une quantité de : "+quantite);
		prix=prix*1000000;
		this.s.retraitChocolat(quantite);
		double chiffreAffaire=prix*quantite;
		this.compte.credit(chiffreAffaire);
		this.tresorerie.setValeur(this, this.compte.getCompte());
		quantiteVendue+=quantite;
		prixMoyendeVente+=prix;
		compteurVente+=1;
	}

	
	/**
	 * @objectif: Remet toutes nos variables à 0
	 */
	public void Miseajour(){
		quantiteAchetee=0;
		quantiteVendue=0;
		prixMoyendAchat=0;
		compteurAchat=0;
		prixMoyendeVente=0;
		compteurVente=0;
	}

	
	/**
	 * @objectif: Fonction permetant de remplir les différents élements de notre journal
	 */
	public void Transactions (){
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color =\"red\"> "+quantiteAchetee+"</font></b> de cacao a été acheté au <b>prix unitaire</b> de : <font color=\"red\"> "+prixMoyendAchat/compteurAchat+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
		this.journal.ajouter(" ");
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color=\"green\">"+quantiteVendue+"</font></b> de chocolat a été vendu au <b>prix unitaire</b> de : <font color=\"green\"> "+prixMoyendeVente/compteurVente+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
		this.journal.ajouter(" ");
		this.journal.ajouter(this.s.toString());
		this.journal.ajouter(" ");
		this.journal.ajouter(this.compte.toString());
		this.journal.ajouter(" ");
		this.journal.ajouter(" la quantitee demandee aux producteurs est de : <b>"+this.qtedemandee+"</b>");
		this.journal.ajouter(" ");
		this.journal.ajouter(" ");
	}

	
	/**
	 * @objectif: Passer à l'étape suivante en mettant à jour
	 */
	public void next(){
		transformation();
		Transactions();
		Miseajour();
		//modifPeremption();
		//System.out.println(s.toString());
	}
}