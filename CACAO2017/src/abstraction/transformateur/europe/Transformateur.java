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


public class Transformateur implements ITransformateurMarcheDistrib, Acteur,IContratTrans, ITransfoContrat  {

	private Stock s;
	private Tresorerie compte;
	private double prixmin;
	private Date date = new Date();
	
	private double quantiteVendue=0; // Pour le journal
	private double quantiteAchetee=0;// Pour le journal
	private double qtedemandee;// Pour le journal
	private double prixMoyendeVente=0;// Pour le journal
	private double prixMoyendAchat=0;// Pour le journal 
	private double compteurAchat=0;// Pour le journal 
	private double compteurVente=0;// Pour le journal 
	private Peremption peremp= new Peremption();
	private int step=0;
	private double[] prixContrat = new double[2];
	private double[] qttContrat = new double[2];

	private List<abstraction.transformateur.europe.Devis> devisDistributeur;

	public static final int CACAO_NECESSAIRE = 30800; // Stock nécessaire par mois pour avoir 44000 chocolats
	public static final int CHOCOLAT_NECESSAIRE = 90000; // Stock nécessaire par mois à vendre (calculé selon la demande européenne)
	public static final double RATIO_CACAO_CHOCO=0.7; // Ratio de transformation entre le cacao et le chocolat
	public static final double PART_MARCHE=0.4; // Part du marché mondiale que nous avons (les américains ont 1-PART_MARCHE)
	public static final double PRIX_MIN=0.004; // Prix minimum de vente du chocolat sur le marché
	public static final double PRIX_MAX=0.008;
	public static final int COUT_ANNEXE=1000000; //couts annexes comportant les salaires et tout les couts potentiels autre que le cacao
	public static double[] CACAO_NECESSAIRE_PREVISION ={32,32,32,48,32,32,32,72,32,32,32,32,32,32,32,32,32,32,32,32,32,60,32,32,32,104};
	public static final double RATIO_CONTRAT_PRODUCTEUR= 0.75; // Proportion de la quantité prévisionnelle minimum sur un an que l'on demande pour le contrat avec les producteurs
	public static final double PART_CONTRAT_TD=0.9;
	public static final double TAUX_ACCEPTATION_CONTRAT=0.8;
	
	//ces static sont celles correspondantes aux contrats distributeurs
	public static double QD1=0;
	public static double QD2=0;
	public static double PD1=0;
	public static double PD2=0;
	
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
		this.l=new ArrayList<Devis>();
	}
	
	/** 
	 * @return: Le prix minimum de vente sur le marché 
	 * 			en fonction de nos stocks de chocolat.
	 */
	public double getprixMin() {
		double stockChocolat=this.s.getStockChocolat();
		if (stockChocolat<Stock.STOCK_MIN || prixmin<0.004){ // On se fixe un stock minimum de "secours" et si on le dépasse on renvoie une valeur qui doit couper la boucle du marché.
			return 0;
		}
		else{
			this.prixmin=PRIX_MAX-PRIX_MIN*this.stockChocolat.getValeur()/CHOCOLAT_NECESSAIRE; //calcul le nouveau prix minimum auquel on souhaite vendre
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
			} else {
			 // On achète ce qui est suffisant pour produire CHOCOLAT_NECESSAIRE tonnes de chocolat
				quantiteSouhaitee=CACAO_NECESSAIRE_PREVISION[step%26]*1000-getmin_tab(CACAO_NECESSAIRE_PREVISION);
			}
		} else {
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
		if ((this.s.getStockChocolat()+this.s.getStockCacao()/RATIO_CACAO_CHOCO)<CHOCOLAT_NECESSAIRE){// On vérifie que stock actuel <= stock max	
			this.s.ajoutChocolat(this.s.getStockCacao()/RATIO_CACAO_CHOCO); // On remplit notre stock tout le temps de sorte à avoir 44000
			this.s.setStockCacao(0); // Retrait du cacao nécessaire à la transformation
		} else {
			this.s.setStockCacao(CHOCOLAT_NECESSAIRE); // On remplit notre stock tout le temps de sorte à avoir 44000
			this.s.setStockCacao(0);
		}
		this.s.ajoutCacao(this.qttContrat[0]);
		this.s.ajoutCacao(this.qttContrat[1]);
		this.compte.debit(this.prixContrat[0]);
		this.compte.debit(this.prixContrat[1]);
	}
	
	public void CoutStock(){
		double cout=0;
		if (this.stockChocolat.getValeur()>=CHOCOLAT_NECESSAIRE){
			cout=(this.s.getStockChocolat()-CHOCOLAT_NECESSAIRE)*1000000;
			//System.out.println(cout+"est le cout des stock");
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
		//System.out.println("le prix de vente du cacao est de : "+ prix);
		//System.out.println("la quantite vendue de cacao est de : "+ quantite);
		this.compte.debit(achat); 
		this.s.ajoutCacao(quantite);
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
		this.step++;
		if (step>26){
			step=0;
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
		this.journal.ajouter("La quantité recue par contrat avec le producteur 0 est de : "+this.qttContrat[0]+" au prix de : "+this.prixContrat[0]);
		this.journal.ajouter("La quantité recue par contrat avec le producteur 1 est de : "+this.qttContrat[1]+" au prix de : "+this.prixContrat[1]);
		this.journal.ajouter(" ");
		this.journal.ajouter("---------------------------------------------------------------------------------------------------------------------------------------------");
		this.journal.ajouter(" ");
	}
	
	/**
	 * @objectif: Implémenter les contrats avec les producteurs
	 */
	
	private List<Devis> l;

	public void envoieDevis(Devis d) { //récupère la liste des différents devis
		this.l.add(d);
	}


	@Override
	public void qttVoulue() { //quantité demandée aux producteurs
		for (Devis d : l){
			d.setQttVoulue(CACAO_NECESSAIRE/2);
	//		d.setQttVoulue(getmin_tab(CACAO_NECESSAIRE_PREVISION)*RATIO_CONTRAT_PRODUCTEUR);
		}
	}
	
	/**
	 * 
	 * @param tab
	 * @return le minimum du tableau rentré en paramètre
	 */
	public double getmin_tab(double[] tab){
		double res = tab[0];
		for (int i=1;i<tab.length;i++){
			if (tab[i]<res){
				res=tab[i];
			}
		}
		return res;
	}
	
	public double getmoyenne_tab(double[] cACAO_NECESSAIRE_PREVISION2){
		double res = 0;
		for (int i=0;i<cACAO_NECESSAIRE_PREVISION2.length;i++){
				res+=cACAO_NECESSAIRE_PREVISION2[i];
			}
		return res/cACAO_NECESSAIRE_PREVISION2.length;
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
		this.prixContrat[0]=p[0];
		this.prixContrat[1]=p[1];
		//choix des contrats en pourcentage de la quantité voulue
		if (p[0]<p[1] && q[0]>=qttVoulue*0.95 && q[1]>=qttVoulue*0.05){ //prix de zero inf a prix de un et qte de zero suffisante
			l.get(0).setQttFinale(0.95*qttVoulue);
			l.get(1).setQttFinale(0.05*qttVoulue);
		} else if (p[0]<p[1] && q[0]>=qttVoulue*0.95 && q[1]<qttVoulue*0.05){ //prix de zero inf a prix de un et qte de zero suffisante
			l.get(0).setQttFinale(0.95*qttVoulue);
			l.get(1).setQttFinale(q[1]);
		} else if (p[0]<p[1] && q[0]<=qttVoulue*0.95 && q[1]>=qttVoulue*0.05) { //prix de zero inf a prix de un et qte de zero insuffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(0.05*qttVoulue);
		} else if (p[0]<p[1] && q[0]<=qttVoulue*0.95 && q[1]<qttVoulue*0.05) { //prix de zero inf a prix de un et qte de zero insuffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(q[1]);
		} else if (p[1]<p[0] && q[1]>=qttVoulue*0.95 && q[0]>=0.05*qttVoulue){ //prix de un inf a prix de zero et qte un suffisante
			l.get(0).setQttFinale(0.05*qttVoulue);
			l.get(1).setQttFinale(0.95*qttVoulue);
		} else if (p[1]<p[0] && q[1]>=qttVoulue*0.95 && q[0]<0.05*qttVoulue){ //prix de un inf a prix de zero et qte un suffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(0.95*qttVoulue);
		} else if (p[1]<p[0] && q[1]<=qttVoulue*0.95 && q[0]>=0.05*qttVoulue) { //prix de un inf a prix de zero et qte un insuffisante
			l.get(1).setQttFinale(q[1]);
			l.get(0).setQttFinale(0.05*qttVoulue);
		} else if (p[1]<p[0] && q[1]<=qttVoulue*0.95 && q[0]<0.05*qttVoulue) { //prix de un inf a prix de zero et qte un insuffisante
			l.get(1).setQttFinale(q[1]);
			l.get(0).setQttFinale(q[0]);
		} else if (p[1]==p[0] && q[1]>=qttVoulue*0.50 && q[0]>=qttVoulue*0.50) { //prix égal et qtes suffisantes
			l.get(0).setQttFinale(0.5*qttVoulue);
			l.get(1).setQttFinale(0.5*qttVoulue);
		} else { //prix égal et qte de un et zero insuffisante
			l.get(0).setQttFinale(q[0]);
			l.get(1).setQttFinale(q[1]);
		} 
		this.qttContrat[0]=l.get(0).getQttFinale();
		this.qttContrat[1]=l.get(1).getQttFinale();
	}
	
	public void coutAnnexe(){
		if (this.step%2==0){
			this.compte.debit(COUT_ANNEXE);
		}
	}
	
	/**
	 * @objectif Faire une prevision du cacao à demander aux producteurs
	 * @param arg
	 * @return un tableau de 26 valeurs correspondant à la quantité previsionnelle de cacao qu'on doit demander tout les next aux producteurs (sans tenir compte des stocks)
	 */
	public double[] prevision_naif(double[] arg){
		int taille = arg.length;
		double [] res = new double[taille];
		for (int i=0; i<taille; i++){
			res[i]=arg[i]*RATIO_CACAO_CHOCO*PART_MARCHE;
		}
		return res;
	}
	
	public void impots(){
		this.compte.debit(0.42*this.compte.getCompte());
	}
	
	/**
	 * @objectif Faire une prevision du cacao à demander aux producteurs en tenant compte des pics
	 * @param arg
	 * @return un tableau de 26 valeurs correspondant à la quantité previsionnelle de cacao qu'on doit demander tout les next aux producteurs en tenant compte des stocks
	 */
	public double[] prevision_pics(double[] arg){
		int taille = arg.length;
		double [] res = prevision_naif(arg);
		double moyenne_tab = getmoyenne_tab(arg);
		for (int i=0; i<taille; i++){
			if (res[(26+i)%26]>=1.5*moyenne_tab){
				res[(26+i)%26]=0.45*arg[i]*RATIO_CACAO_CHOCO*PART_MARCHE;
				res[(26+i-1)%26]+=0.25*arg[i]*RATIO_CACAO_CHOCO*PART_MARCHE;
				res[(26+i-2)%26]+=0.15*arg[i]*RATIO_CACAO_CHOCO*PART_MARCHE;
				res[(26+i-3)%26]+=0.15*arg[i]*RATIO_CACAO_CHOCO*PART_MARCHE;
			}
		}
		return res;
	}

	public void propositionInitiale(abstraction.transformateur.europe.Devis d) {
		if (d.getDistri().equals(abstraction.distributeur.europe.Distributeur.class.getName())){
			devisDistributeur.add(0, d);
		} else {
			devisDistributeur.add(1, d);
		}
		double moyenne=getmoyenne_tab(CACAO_NECESSAIRE_PREVISION);
		double quantiteTotale=moyenne*PART_CONTRAT_TD*1000*26;
		d.setQ1(quantiteTotale);
		d.setP1(prixMoyendeVente);
	}

	@Override
	public void quantiteFournie() {
		double Qdemandee0=devisDistributeur.get(0).getQ2();
		double Qdemandee1=devisDistributeur.get(1).getQ2();
		double Qfournie0=getmoyenne_tab(CACAO_NECESSAIRE_PREVISION)*PART_CONTRAT_TD*1000*26*(Qdemandee0/(Qdemandee0+Qdemandee1));
		double Qfournie1=getmoyenne_tab(CACAO_NECESSAIRE_PREVISION)*(Qdemandee1/(Qdemandee0+Qdemandee1));
		devisDistributeur.get(0).setQ2(Qfournie0);
		devisDistributeur.get(1).setQ2(Qfournie1);
	}

	@Override
	public void acceptationInitiale() {
		double contreprix0=devisDistributeur.get(0).getP2();
		double contreprix1=devisDistributeur.get(1).getP2();
		if (Math.abs(devisDistributeur.get(0).getP1()-contreprix0)<=devisDistributeur.get(0).getP1()*TAUX_ACCEPTATION_CONTRAT){
			devisDistributeur.get(0).setChoixT(false);
		} else {
			devisDistributeur.get(0).setChoixT(true);
		}
		if (Math.abs(devisDistributeur.get(1).getP1()-contreprix1)<=devisDistributeur.get(1).getP1()*TAUX_ACCEPTATION_CONTRAT){
			devisDistributeur.get(1).setChoixT(false);
		} else {
			devisDistributeur.get(1).setChoixT(true);
		}
	}

	@Override
	public void notification() {
		if (this.devisDistributeur.get(0).getChoixD() && this.devisDistributeur.get(0).getChoixT()){
			QD1=this.devisDistributeur.get(0).getQ3();
			QD2=this.devisDistributeur.get(1).getQ3();
			PD1=this.devisDistributeur.get(0).getP2();
			PD2=this.devisDistributeur.get(1).getP2();
		} else if (this.devisDistributeur.get(0).getChoixD() && !this.devisDistributeur.get(0).getChoixT()){
			QD1=this.devisDistributeur.get(0).getQ3();
			QD2=this.devisDistributeur.get(1).getQ3();
			PD1=this.devisDistributeur.get(0).getP1();
			PD2=this.devisDistributeur.get(1).getP1();
		} else{
			QD1=0;
			QD2=0;
			PD1=0;
			PD2=0;
		}
	}
		
	/**
	 * @objectif: Passer à l'étape suivante en mettant à jour
	 */
	public void next(){
		//this.s.retraitChocolat(QD1/26+QD2/26);
		//this.compte.credit(PD1+PD2);
		peremp.RetraitVente(quantiteVendue);
		peremp.MiseAJourNext(this);
		if (this.step%26==0){
			AgentContratPT.demandeDeContrat(this);
		}
		Journal();
		transformation();
		impots();
		CoutStock();
		Miseajour();
	}
	
}

