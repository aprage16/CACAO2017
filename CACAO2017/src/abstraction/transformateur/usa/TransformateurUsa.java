package abstraction.transformateur.usa;
import java.util.ArrayList;
import java.util.List;

import abstraction.producteur.cotedivoire.contrats.*;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.interfacemarche.*;
//Souchu
public class TransformateurUsa implements transformateur,Acteur, IContratTrans{
	private StockProduitsFinis finis;
	private StockMatPremiere premiere;
	private TransfoChocolat transfo;
	private Tresorerie tresorerie;
	private static final int Uniteventechocolat=1000;//10000 tonnes
	private static final double Bornesmax=0.008;//Une unité d'argent =1 million d'euro
	private static final double Bornesmin=0.004;
	private double Stockdesire;
	private static final double Prixstockage=0.25*Bornesmin/(24);//Le prix du stokage par an est de 25% de la valeur des marchandises stockées
	private static final double CoutFixe=200;
	private ArrayList<Double> prixmatprem;
	private double venteChocolat;
	private double achatCacao;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	public static Journal LE_JOURNAL_USA;
	private double step;
	private List<Devis> devis;
	private Decision priseDecisions;

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
		step=0;
		LE_JOURNAL_USA=new Journal("Journal de Transformateur USA");
		this.tresorerie=new Tresorerie(1000);
		prixmatprem = new ArrayList<Double>();
		prixmatprem.add(0.000350);//Prix matière première à la tonne en euros.
		prixmatprem.add(0.000025);
		prixmatprem.add(0.000400);
		finis = new StockProduitsFinis();
		finis.ajouterChocolat(Stockdesire/3);
		premiere =new StockMatPremiere(Stockdesire/2,Stockdesire,Stockdesire,Stockdesire);
		transfo =new TransfoChocolat(premiere,finis);
		this.venteChocolat=0;
		this.achatCacao=0;
		this.achats= new Indicateur("5_TRAN_USA_achats",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.achats);
		this.ventes= new Indicateur("5_TRAN_USA_ventes",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.ventes);
		this.solde=new Indicateur("5_TRAN_USA_solde",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);	
		Monde.LE_MONDE.ajouterJournal(LE_JOURNAL_USA);
		this.priseDecisions=new Decision();
		Stockdesire=400*Uniteventechocolat;
	}

	public void next(){
		this.finis.miseAJour();
		produirechocolat();		
		payerstock();
		payerCoutFixes();
		achetermatierepremiere();
		miseAJourJournal();
		if(this.achats!=null){
			this.achats.setValeur(this, this.getAchatCacao());
			double vente=this.MiseAJourVente();
			this.priseDecisions.ajouterVente(vente);
			this.ventes.setValeur(this, vente);
			this.solde.setValeur(this, this.tresorerie.getCompteCourant());
			this.achatCacao=0;
		}
		if ((Monde.LE_MONDE.getStep()%12)==0 && Monde.LE_MONDE.getStep()!=0){
			this.Stockdesire=this.priseDecisions.getStockDesire();
			System.out.println(this.priseDecisions.getStockDesire());
		}
	}
	//souchu
	
	private void miseAJourJournal(){
		step++;
		LE_JOURNAL_USA.ajouter("Journal Usa : step "+step);
		LE_JOURNAL_USA.ajouter("");
		LE_JOURNAL_USA.ajouter("");
		LE_JOURNAL_USA.ajouter("Notre Stock de Choco est "+this.finis.getStockChocolat());
		LE_JOURNAL_USA.ajouter("Notre Stock de Cacao est "+this.premiere.getCacao());
		LE_JOURNAL_USA.ajouter("Notre Stock de Lait est "+this.premiere.getIngredient(1));
		LE_JOURNAL_USA.ajouter("Notre Stock de Sucre est "+this.premiere.getIngredient(2));
		LE_JOURNAL_USA.ajouter("Notre Stock de Lecitine est "+this.premiere.getIngredient(3));
	}
	
	private void payerCoutFixes(){
		this.tresorerie.removeMoney(this.CoutFixe);
	}

	private void payerstock(){
		double avant=this.tresorerie.getCompteCourant();
		this.tresorerie.removeMoney(this.finis.getStockChocolat()*Prixstockage);	
		for (int i=0;i<3;i++){
			this.tresorerie.removeMoney(this.premiere.getIngredient(i)*Prixstockage);	
		}
		LE_JOURNAL_USA.ajouter("Cout stockage = "+(avant-this.tresorerie.getCompteCourant()));
	}

	public void achetermatierepremiere(){
		for (int i=1;i<4;i++){
			if (tresorerie.getCompteCourant()-((Stockdesire-this.premiere.getIngredient(i))*prixmatprem.get(i-1))>0){
				tresorerie.setCompteCourant(tresorerie.getCompteCourant()-((Stockdesire-this.premiere.getIngredient(i))*prixmatprem.get(i-1)));
				this.premiere.setIngredient(i, Stockdesire);
			}
		}
	}
	private void produirechocolat(){
		this.LE_JOURNAL_USA.ajouter("Production de chocolat : "+(Stockdesire-finis.getStockChocolat()));
		transfo.produireChoco(Stockdesire-finis.getStockChocolat());
	}

	public double getprixMin(){
		if (finis.getStockChocolat()<=Uniteventechocolat){
			//LE_JOURNAL_USA.ajouter("1Prix min="+Bornesmax+1);
			return Bornesmax+1;
		}
		else if (finis.getStockChocolat()<Stockdesire){
			double prix= Bornesmax-((finis.getStockChocolat()-Uniteventechocolat)/((Stockdesire/Uniteventechocolat-1)*Uniteventechocolat)*(Bornesmax-Bornesmin));
			//LE_JOURNAL_USA.ajouter("2Prix min="+prix);
			//LE_JOURNAL_USA.ajouter(""+(finis.getStockChocolat()));
			return prix;
		}
		else{
			return Bornesmin;
		}
	}
	
	
	
	@Override
	public void notif(double prix, double quantite) {
		this.venteChocolat+=quantite;
		this.finis.enleverChoco(quantite);
		this.tresorerie.setCompteCourant(this.tresorerie.getCompteCourant()+quantite*prix);	
	}

	public double QteSouhaite(){
		
		if (Stockdesire-this.premiere.getCacao()>=0){
			LE_JOURNAL_USA.ajouter("Quantite souhaitée "+(Stockdesire-this.premiere.getCacao()));
			return Stockdesire-this.premiere.getCacao();
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
		this.premiere.setCacao(premiere.getCacao()+achete);
		this.achatCacao+=achete;
	}

	public int hashCode() {
		return this.getNom().hashCode();
	}

	public String toString(){
		return "Nom = "+this.getNom()+"tresorerie ="+this.tresorerie.getCompteCourant()+" StockChoco "+this.finis.getStockChocolat();
	}

	public void test(){
		this.notificationAchat(100*Uniteventechocolat, 200);
		System.out.println(this.toString());
		this.notif(6000, 100*Uniteventechocolat);	
	}
	
	public void testPrixMin(){
		System.out.println("Prixmin= "+this.getprixMin());
		this.notif(10000, Uniteventechocolat);
	}

	@Override
	public void envoieDevis(List<Devis> l) {
		this.devis=l;	
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

}
