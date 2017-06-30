package abstraction.transformateur.usa;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.cotedivoire.contrats.AgentContratPT;
import abstraction.producteur.cotedivoire.contrats.Devis;
import abstraction.producteur.cotedivoire.contrats.IContratTrans;
import abstraction.transformateur.europe.ITransfoContrat;

//Souchu
public class TransformateurUsa implements ITransformateurMarcheDistrib,ITransformateurMarcheProducteur,Acteur, IContratTrans, ITransfoContrat{
//public class TransformateurUsa implements ITransformateurMarcheDistrib,ITransformateurMarcheProducteur,Acteur, IContratTrans{
	private StockProduitsFinis stockChocolat;
	private StockMatPremiere stockMatierePremiere;
	private TransfoChocolat transfo;
	private Tresorerie tresorerie;
	private static final int Uniteventechocolat=1000;//10000 tonnes
	private static final double Bornesmax=0.008;//Une unité d'argent =1 million d'euro
	private static final double Bornesmin=0.004;
	private double StockdesireMatierePremiere;
	private double StockdesireChocolat;
	private static final double Prixstockage=0.25*Bornesmin/(24);//Le prix du stokage par an est de 25% de la valeur des marchandises stockées
	private static final double CoutFixe=0;
	private ArrayList<Double> prixmatprem;
	private double venteChocolat;
	private double achatCacao;
	private Indicateur solde;
	private Indicateur stockChoco;
	private Indicateur stockCacao;
	private Indicateur ventesChoco;
	private Indicateur achatsCacao;
	public static Journal LE_JOURNAL_USA;
	private double step;
	private List<Devis> devis;
	private Decision priseDecisions;
	public static double[] CACAO_NECESSAIRE_PREVISION ={48,48, 48, 72, 48, 48, 48, 108, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 90,48, 48, 48, 156};
	public static final double PART_CONTRAT_TD=0.6;
	private List<abstraction.transformateur.europe.Devis> devisDistributeur;
	public static final double prixMoyendeVente=900000;

	/* Nos indicateurs sont :
	 * -Compte courant de la Trésorie
	 * -Dernières vente de Chocolat
	 * -Derniers achat de Cacao
	 */

	public double MiseAJourVente(){
		double tampon = this.venteChocolat;
		this.venteChocolat=0;
		return tampon;
	}

	public double getAchatCacao(){
		return this.achatCacao;
	}


	public TransformateurUsa(){
		StockdesireMatierePremiere=400*Uniteventechocolat;
		StockdesireChocolat=400*Uniteventechocolat;
		step=0;
		LE_JOURNAL_USA=new Journal("Transformateur USA");
		this.tresorerie=new Tresorerie(100000);
		prixmatprem = new ArrayList<Double>();
		prixmatprem.add(0.000350);//Prix matière première à la tonne en euros.
		prixmatprem.add(0.000025);
		prixmatprem.add(0.000400);
		stockChocolat = new StockProduitsFinis();
		stockMatierePremiere =new StockMatPremiere(StockdesireMatierePremiere,StockdesireMatierePremiere,StockdesireMatierePremiere,StockdesireMatierePremiere);
		transfo =new TransfoChocolat(stockMatierePremiere,stockChocolat);
		this.venteChocolat=0;
		this.achatCacao=0;
		this.stockCacao=new Indicateur("5_TRAN_USA_stockCacao",this,40000.);
		this.stockChoco=new Indicateur("5_TRAN_USA_stockChoco",this,StockdesireMatierePremiere);
		this.achatsCacao=new Indicateur("5_TRAN_USA_achatCacao",this,0.0);
		this.ventesChoco=new Indicateur("5_TRAN_USA_venteCacao",this,0.0);
		this.solde=new Indicateur("5_TRAN_USA_solde",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);	
		Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
		Monde.LE_MONDE.ajouterIndicateur(this.stockChoco);	
		Monde.LE_MONDE.ajouterIndicateur(this.ventesChoco);
		Monde.LE_MONDE.ajouterIndicateur(this.achatsCacao);	
		Monde.LE_MONDE.ajouterJournal(LE_JOURNAL_USA);
		this.priseDecisions=new Decision();
		this.devis=new ArrayList<Devis>();

	}

	public void next(){	
		this.stockChocolat.miseAJour();
		produirechocolat();		
		payerstock();
		payerCoutFixes();
		achetermatierepremiere();
		miseAJourJournal();	
		double vente=this.MiseAJourVente();
		this.ventesChoco.setValeur(this, vente);
		LE_JOURNAL_USA.ajouter("Nous avons vendu "+vente+" de tonnes de chocolat");
		this.priseDecisions.ajouterVente(vente);
		this.solde.setValeur(this, this.tresorerie.getCompteCourant());
		this.achatsCacao.setValeur(this, achatCacao);
		this.achatCacao=0;
		this.stockCacao.setValeur(this,this.stockMatierePremiere.getCacao());
		this.stockChoco.setValeur(this, stockChocolat.getStockChocolat());
		if ((Monde.LE_MONDE.getStep()%12)==0 && Monde.LE_MONDE.getStep()!=0){
			this.StockdesireChocolat=this.priseDecisions.getStockDesire();
			System.out.println(this.priseDecisions.getStockDesire());
		}
		if (step%13==0){
			AgentContratPT.demandeDeContrat(this);
		}
	}
	//souchu

	private void miseAJourJournal(){
		step++;
		LE_JOURNAL_USA.ajouter("Journal Usa : step "+step);
		LE_JOURNAL_USA.ajouter("");
		LE_JOURNAL_USA.ajouter("");
		LE_JOURNAL_USA.ajouter("Notre Stock de Choco est "+this.stockChocolat.getStockChocolat());
		LE_JOURNAL_USA.ajouter("Notre Stock de Cacao est "+this.stockMatierePremiere.getCacao());
		LE_JOURNAL_USA.ajouter("Notre Stock de Lait est "+this.stockMatierePremiere.getIngredient(1));
		LE_JOURNAL_USA.ajouter("Notre Stock de Sucre est "+this.stockMatierePremiere.getIngredient(2));
		LE_JOURNAL_USA.ajouter("Notre Stock de Lecitine est "+this.stockMatierePremiere.getIngredient(3));

		LE_JOURNAL_USA.ajouter("Notre stock de chocolat se présente comme ceci: "+this.stockChocolat);
	}

	private void payerCoutFixes(){
		this.tresorerie.removeMoney(this.CoutFixe);
	}

	private void payerstock(){
		double avant=this.tresorerie.getCompteCourant();
		this.tresorerie.removeMoney(this.stockChocolat.getStockChocolat()*Prixstockage);	
		for (int i=0;i<3;i++){
			this.tresorerie.removeMoney(this.stockMatierePremiere.getIngredient(i)*Prixstockage);	
		}
		LE_JOURNAL_USA.ajouter("Cout stockage = "+(avant-this.tresorerie.getCompteCourant()));
	}

	public void achetermatierepremiere(){
		for (int i=1;i<4;i++){
			if (tresorerie.getCompteCourant()-((StockdesireMatierePremiere-this.stockMatierePremiere.getIngredient(i))*prixmatprem.get(i-1))>0){
				tresorerie.setCompteCourant(tresorerie.getCompteCourant()-((StockdesireMatierePremiere-this.stockMatierePremiere.getIngredient(i))*prixmatprem.get(i-1)));
				this.stockMatierePremiere.setIngredient(i, StockdesireMatierePremiere);
			}
		}
	}
	private void produirechocolat(){
		transfo.produireChoco(StockdesireChocolat-stockChocolat.getStockChocolat());
	}

	public double getprixMin(){
		if (stockChocolat.getStockChocolat()<=Uniteventechocolat){
			return Bornesmax+1;
		}
		else if (stockChocolat.getStockChocolat()<StockdesireChocolat){
			double prix= Bornesmax-((stockChocolat.getStockChocolat()-Uniteventechocolat)/((StockdesireChocolat/Uniteventechocolat-1)*Uniteventechocolat)*(Bornesmax-Bornesmin));
			return prix;
		}
		else{
			return Bornesmin;
		}
	}



	@Override
	public void notif(double prix, double quantite) {
		this.venteChocolat+=quantite;
		this.stockChocolat.enleverChoco(quantite);
		this.tresorerie.setCompteCourant(this.tresorerie.getCompteCourant()+quantite*prix);	
	}

	public double QteSouhaite(){

		if (StockdesireMatierePremiere-this.stockMatierePremiere.getCacao()>=0){
			LE_JOURNAL_USA.ajouter("Quantite souhaitée "+(StockdesireMatierePremiere-this.stockMatierePremiere.getCacao()));
			return StockdesireMatierePremiere-this.stockMatierePremiere.getCacao();
		}	
		LE_JOURNAL_USA.ajouter("Quantite souhaitée="+0);
		return 0;
	}
	@Override
	public String getNom() {
		return "Transfo Usa";
	}

	public void notificationAchat(double achete, double prix){
		LE_JOURNAL_USA.ajouter("On a acheté pour un prix unitaire de  "+prix/1000000+"Et tant de tonne  "+achete);
		LE_JOURNAL_USA.ajouter(""+this.priseDecisions);
		this.priseDecisions.ajouterAchat(achete);
		this.tresorerie.setCompteCourant(tresorerie.getCompteCourant()-prix*achete/1000000);
		this.stockMatierePremiere.setCacao(stockMatierePremiere.getCacao()+achete);
		this.achatCacao+=achete;
	}

	public int hashCode() {
		return this.getNom().hashCode();
	}

	public String toString(){
		return "Nom = "+this.getNom()+"tresorerie ="+this.tresorerie.getCompteCourant()+" StockChoco "+this.stockChocolat.getStockChocolat();
	}

	/*public void test(){
		this.notificationAchat(100*Uniteventechocolat, 200);
		System.out.println(this.toString());
		this.notif(6000, 100*Uniteventechocolat);	
	}*/

	/*public void testPrixMin(){
		System.out.println("Prixmin= "+this.getprixMin());
		this.notif(10000, Uniteventechocolat);
	}*/

	@Override
	public void envoieDevis(Devis d) {
		this.devis.add(d);	
	}

	@Override
	public void qttVoulue() {
		for (Devis d:devis){ 
			d.setQttVoulue(this.priseDecisions.getQuantiteVoulue());
		}
	}

	@Override
	//Souchu
	public void finContrat() {
		double minPrix=1000;int meilleurPartenaire=0;double quantitévoulue=this.priseDecisions.getQuantiteVoulue();
		for (int i=0;i<this.devis.size();i++){//On cherche le partenaire le moins cher, on considère que c'est le meilleur
			if (devis.get(i).getPrix()<minPrix){
				meilleurPartenaire=i;
			}
		}
		if (devis.get(meilleurPartenaire).getQttLivrable()>=0.8*quantitévoulue){ // Le meilleurPartenaire peut livrer 80% du Cacao désiré
			devis.get(meilleurPartenaire).setQttFinale(0.8*quantitévoulue);//On lui commande donc cette quantité
			if (devis.get(Math.abs(meilleurPartenaire-1)).getQttLivrable()>=0.2*quantitévoulue){
				devis.get(Math.abs(meilleurPartenaire-1)).setQttFinale(0.2*quantitévoulue);//Si l'autre producteur peut produire le reste, on lui commande le reste (20% du Cacao désiré)
			}
			else {
				devis.get(Math.abs(meilleurPartenaire-1)).setQttFinale(devis.get(Math.abs(meilleurPartenaire-1)).getQttLivrable());//Sinon on lui commande le reste (moins de 20% du Cacao désiré)
			}
		}
		else{
			devis.get(meilleurPartenaire).setQttFinale(devis.get(meilleurPartenaire).qttLivrable);//Si le meilleurPartenaire n'est pas en mesure de fournir 80% de ce que l'on veut,on commande tout ce qu'il a et on se rabat sur l'autre
			if (devis.get(Math.abs(meilleurPartenaire-1)).getQttLivrable()>=(quantitévoulue-devis.get(meilleurPartenaire).qttLivrable)){//On regarde si le deuxième choix de partenaire est capable de nosu fournir
				devis.get(Math.abs(meilleurPartenaire-1)).setQttFinale((quantitévoulue-devis.get(meilleurPartenaire).qttLivrable));
			}
			else {
				devis.get(Math.abs(meilleurPartenaire-1)).setQttFinale(devis.get(Math.abs(meilleurPartenaire-1)).getQttLivrable());//Sinon, on prend tout ce qu'il lui reste
			}
		}
	}

	@Override
	public void propositionInitiale(abstraction.transformateur.europe.Devis d) {
		if (d.getDistri().equals(abstraction.distributeur.europe.Distributeur.class.getName())){
			devisDistributeur.add(0, d);
		}
		else{
			devisDistributeur.add(1, d);
		}
		double moyenne=0;
		for (double elt : CACAO_NECESSAIRE_PREVISION){
			moyenne+=elt;
		}
		moyenne=moyenne/CACAO_NECESSAIRE_PREVISION.length;
		double quantiteTotale=moyenne*PART_CONTRAT_TD;
		d.setQ1(quantiteTotale);
		d.setP1(prixMoyendeVente);
		
	}
	
	@Override
	public void quantiteFournie() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptationInitiale() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notification() {
		// TODO Auto-generated method stub
		
	}

}
