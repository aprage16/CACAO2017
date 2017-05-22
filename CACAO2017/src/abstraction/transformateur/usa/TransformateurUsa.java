package abstraction.transformateur.usa;
import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.interfacemarche.*;
//Souchu
public class TransformateurUsa implements transformateur,Acteur{
	private StockProduitsFinis finis;
	private StockMatPremiere premiere;
	private TransfoChocolat transfo;
	private Tresorerie tresorerie;
	private static final int Uniteventechocolat=1000;//10000 tonnes
	private static final double Bornesmax=0.008;//Une unité d'argent =1 million d'euro
	private static final double Bornesmin=0.004;
	private static final double Stockdesire=100*Uniteventechocolat;
	private static final double Prixstockage=0.25*Bornesmin/(24);//Le prix du stokage par an est de 25% de la valeur des marchandises stockées
	private ArrayList<Double> prixmatprem;
	private double venteChocolat;
	private double achatCacao;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	private Journal journal;
	private double step;

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
		journal=new Journal("TransfoUsa");
		this.tresorerie=new Tresorerie(100);
		prixmatprem = new ArrayList<Double>();
		prixmatprem.add(0.000350);//Prix matière première à la tonne en euros.
		prixmatprem.add(0.000025);
		prixmatprem.add(0.000400);
		finis = new StockProduitsFinis(Stockdesire);
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
		//Monde.LE_MONDE.ajouterJournal(journal);
	}

	public void next(){
		produirechocolat();		
		payerstock();
		achetermatierepremiere();
		miseAJourJournal();
		System.out.print(journal);
		if(this.achats!=null){
			this.achats.setValeur(this, this.getAchatCacao());
			this.ventes.setValeur(this, this.MiseAJourVente());
			this.solde.setValeur(this, this.tresorerie.getCompteCourant());
			this.achatCacao=0;
		}
	}
	//souchu
	
	private void miseAJourJournal(){
		step++;
		journal.ajouter("Journal Usa : step "+step);
		journal.ajouter("");
		journal.ajouter("");
		journal.ajouter("Notre Stock de Choco est "+this.finis.getStockChocolat());
		journal.ajouter("Notre Stock de Cacao est "+this.premiere.getCacao());
		journal.ajouter("Notre Stock de Lait est "+this.premiere.getIngredient(1));
		journal.ajouter("Notre Stock de Sucre est "+this.premiere.getIngredient(2));
		journal.ajouter("Notre Stock de Lecitine est "+this.premiere.getIngredient(3));
	}

	private void payerstock(){
		double avant=this.tresorerie.getCompteCourant();
		this.tresorerie.removeMoney(this.finis.getStockChocolat()*Prixstockage*Bornesmin);	
		for (int i=0;i<3;i++){
			this.tresorerie.removeMoney(this.premiere.getIngredient(i)*Prixstockage);	
		}
		journal.ajouter("Cout stockage = "+(avant-this.tresorerie.getCompteCourant()));
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
		transfo.produireChoco(Stockdesire-finis.getStockChocolat());
	}

	public double getprixMin(){
		if (finis.getStockChocolat()<Uniteventechocolat){
			journal.ajouter("Prix min="+Bornesmax+1);
			return Bornesmax+1;
		}
		else if (finis.getStockChocolat()<Stockdesire){
			double prix= Bornesmax-((finis.getStockChocolat()-1*Uniteventechocolat)/((Stockdesire/Uniteventechocolat-1)*Uniteventechocolat)*(Bornesmax-Bornesmin));
			journal.ajouter("Prix min="+prix);
			return prix;
		}
		else{
			journal.ajouter("Prix min="+Bornesmin);
			return Bornesmin;
		}
	}
	@Override
	public void notif(double prix, double quantite) {
		//System.out.println(prix+"    "+quantité);
		this.venteChocolat+=quantite;
		this.finis.enleverChoco(quantite);
		this.tresorerie.setCompteCourant(this.tresorerie.getCompteCourant()+quantite*prix);	
	}

	public double QteSouhaite(){
		
		if (Stockdesire-this.premiere.getCacao()>=0){
			journal.ajouter("Quantite souhaitée "+(Stockdesire-this.premiere.getCacao()));
			return Stockdesire-this.premiere.getCacao();
		}	
		journal.ajouter("Quantite souhaitée="+0);
		return 0;
	}
	@Override
	public String getNom() {
		return "Transfo Usa";
	}

	public void notificationAchat(double achete, double prix){
		journal.ajouter("On a acheté pour un prix unitaire de  "+prix/1000000+"Et tant de tonne  "+achete);
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

}
