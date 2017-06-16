/* Classe gérant notre acteur globalement 
	- Intéractions avec le marché,
 	- Processus de transformation du cacao en chocolat,
	- Ventes et Achat, 
	- Péremption, 
	- Indicateurs et notifications 
*/
/**
 * @authors Blois Philippe, 
           Charloux Jean, 
           Halzuet Guillaume,
		   Stourm Théo 
 */


package abstraction.transformateur.europe;
import java.util.List;
import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.cotedivoire.contrats.Devis;
import abstraction.producteur.cotedivoire.contrats.*;

import abstraction.transformateur.usa.*;
public class Transformateur implements ITransformateurMarcheDistrib, Acteur,IContratTrans  {

	private Stock s;
	private Tresorerie compte;
	private double prixmin;
	private Date date= new Date();

	
	private double quantiteVendue=0; // Pour le journal
	private double quantiteAchetee=0;// Pour le journal
	private double qtedemandee;// Pour le journal
	private double prixMoyendeVente=0;// Pour le journal
	private double prixMoyendAchat=0;// Pour le journal 
	private double compteurAchat=0;// Pour le journal 
	private double compteurVente=0;// Pour le journal 
	private Peremption peremp= new Peremption();
	
	public static final int CACAO_NECESSAIRE = 30800; // Stock nécessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 44000; // Stock nécessaire par mois à vendre (calculé selon la demande européenne)
	public static final double RATIO_CACAO_CHOCO=0.7; // Ratio de transformation entre le cacao et le chocolat
	public static final double PRIX_MIN=0.004; // Prix minimum de vente du chocolat sur le marché
	public static final double PRIX_MAX=0.008;
	
	private Journal journal;
	private Indicateur stockChocolat;
	private Indicateur tresorerie;
	private Indicateur commande;
	private Indicateur prixdevente;

	
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
		this.prixdevente=new Indicateur("3_TRAN_EU_prixdevente",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur( this.stockChocolat );
		Monde.LE_MONDE.ajouterIndicateur( this.commande );
		Monde.LE_MONDE.ajouterIndicateur( this.tresorerie );
		Monde.LE_MONDE.ajouterIndicateur(this.prixdevente);
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
		if (stockChocolat<Stock.STOCK_MIN || prixmin<0.004){ // On se fixe un stock minimum de "secours" et si on le dépasse on renvoie une valeur qui doit couper la boucle du marché.
			return 1000000000;
		}
		else{
			this.prixmin=PRIX_MAX-PRIX_MAX*Stock.STOCK_MIN/this.stockChocolat.getValeur(); //calcul le nouveau prix minimum auquel on souhaite vendre
			//System.out.println("prix min de transfo eu : "+prixmin);				  // en tenant compte du stock de chocolat que l'on a.
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
	
	
	public void CoutStock(){
		double cout=0;
		if (this.stockChocolat.getValeur()>=CHOCOLAT_NECESSAIRE){
			cout=(this.s.getStockChocolat()-CHOCOLAT_NECESSAIRE)*10000;
			System.out.println(cout+"est le cout des stock");
		}
		this.compte.debit(cout);
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
		this.s.ajoutCacao(quantite);
		this.compte.credit(achat);
		prixMoyendAchat+=prix;
		compteurAchat+=1;
		quantiteAchetee+=quantite;
	}

	/**
	 * @objectif: Prendre en compte que le chocolat se périmme
	 * On considère notre stock de chocolat perissable en 10 semaines, 
	 * le stockage dans une liste permet de supprimer la quantité produite 
	 * il y a 10 semaines de notre stock
	 */
	
	/*public void modifPeremption(double quantite){  
		double[] peremp=new double[peremption.length];
		double estPerime=peremption[4];
		peremp[0]=this.s.getStockCacao()*RATIO_CACAO_CHOCO;
		for (int i=0;i<peremp.length-1;i++){
			peremp[i+1]=peremption[i];
		}
		peremption=peremp;
		if (estPerime>=quantite && this.stockChocolat.getValeur()-estPerime>=0){
			this.stockChocolat.setValeur(this, this.stockChocolat.getValeur()-estPerime);
		}
		else{
			this.stockChocolat.setValeur(this, this.stockChocolat.getValeur()-quantite);
		}
	}*/
	
	/**
	 * @objectif: Fonction utilisée dans le marché, elle nous indique 
	 * combien de chocolat nous avons vendu et à quel prix
	 * 
	 * @param quantite, la quantité de chocolat vendu
	 * @param prix, le prix unitaire du chocolat vendu
	 */
	public void notif(double prix, double quantite) {
		prix=prix*1000000;
		this.s.retraitChocolat(quantite);
		this.compte.credit(prix*quantite);
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
		prixmin=PRIX_MAX;
		for (int i=0;i<14;i++){
			date=date.lendemain();
		}
	}

	
	/**
	 * @objectif: Fonction permetant de remplir les différents élements de notre journal
	 */
	public void Journal (){
		this.stockChocolat.setValeur(this, this.s.getStockChocolat());
		this.commande.setValeur(this, (int)quantiteAchetee );
		this.tresorerie.setValeur(this, this.compte.getCompte());
		this.prixdevente.setValeur(this, this.prixMoyendeVente);
		this.journal.ajouter("<b> La date du jour est : "+date+"</b>");
		this.journal.ajouter("");
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color =\"red\"> "+(int)quantiteAchetee+"</font></b> de cacao a été acheté au <b>prix unitaire</b> de : <font color=\"red\"> "+(int)prixMoyendAchat/compteurAchat+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
		this.journal.ajouter(" ");
		this.journal.ajouter("Une <b>quantité</b> de : <b><font color=\"green\">"+(int)quantiteVendue+"</font></b> de chocolat a été vendu au <b>prix unitaire</b> de : <font color=\"green\"> "+prixMoyendeVente/compteurVente+"</font> euros à l'étape du Monde: "+Monde.LE_MONDE.getStep());
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
	 * @objectif: Implémenter les contrats avec les producteurs
	 */
	
	private List<Devis> l;
	
	@Override
	public void envoieDevis(List<Devis> l) { //récupère la liste des différents devis
		this.l=l;
	}


	@Override
	public void qttVoulue() { //quantité demandée aux producteurs
		for (Devis d : l){
			d.setQttVoulue(CACAO_NECESSAIRE*0.5);
		}
	}

	@Override
	public void finContrat() {

		double q[]=new double[l.size()];
		double p[]=new double[l.size()];
		double qttVoulue=l.get(0).getQttVoulue();
		q[0]=l.get(0).getQttLivrable();
		q[1]=l.get(1).getQttLivrable();
		p[0]=l.get(0).getPrix();
		p[1]=l.get(1).getPrix();
		
		//distribution des 90 points restants
		if (p[0]<p[1] && q[0]>=qttVoulue*0.95){ //prix de zero inf a prix de un et qte de zero suffisante
			l.get(0).setQttFinale(0.95*qttVoulue);
			l.get(1).setQttFinale(0.05*qttVoulue);
		} else if (p[0]<p[1] && q[0]<=qttVoulue*0.95) { //prix de zero inf a prix de un et qte de zero insuffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(0.05*qttVoulue);
		} else if (p[1]<p[0] && q[1]>=qttVoulue*0.95){ //prix de un inf a prix de zero et qte un suffisante
			
			l.get(0).setQttFinale(0.05*qttVoulue);
			l.get(1).setQttFinale(0.95*qttVoulue);
		} else if (p[1]<p[0] && q[1]<=qttVoulue*0.95) { //prix de un inf a prix de zero et qte un insuffisante
			l.get(1).setQttFinale(q[1]);
			l.get(0).setQttFinale(0.05*qttVoulue);
		} else if (p[1]==p[0] && q[1]>=qttVoulue*0.50 && q[0]>=qttVoulue*0.50) { //prix égal et qtes suffisantes
			l.get(0).setQttFinale(0.5*qttVoulue);
			l.get(1).setQttFinale(0.5*qttVoulue);
		} else { //prix égal et qte de un et zero insuffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(q[1]);
		} 
	}
	
	/**
	 * @objectif: Passer à l'étape suivante en mettant à jour
	 */
	public void next(){
		peremp.RetraitVente(quantiteVendue);
		peremp.MiseAJourNext(this);
		Journal();
		transformation();
		CoutStock();
		Miseajour();
		//System.out.println("notre compte est de : "+this.compte.getCompte());
		//System.out.println(this.tresorerie.getValeur()+"est la veleur de la tresorerie en tant qu'indicateur");
	}

}