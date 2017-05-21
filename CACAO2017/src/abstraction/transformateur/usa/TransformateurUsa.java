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
	private Tresorerie tresorie=new Tresorerie(5);
	private static final int Uniteventechocolat=1000;//1000 tonnes
	private static final double Bornesmax=0.008;//Une unité d'argent =1 million d'euro
	private static final double Bornesmin=0.004;
	private static final double Stockdesire=2000*Uniteventechocolat;
	private static final double Prixstockage=0.25*Bornesmin/(24*1000);//Le prix du stokage par an est de 25% de la valeur des marchandises stockées
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
		prixmatprem = new ArrayList<Double>();
		prixmatprem.add(0.000350);//Prix matière première à la tonne en euros.
		prixmatprem.add(0.000025);
		prixmatprem.add(0.000400);
		finis = new StockProduitsFinis(Stockdesire);
		premiere =new StockMatPremiere(Stockdesire/2,Stockdesire,Stockdesire,Stockdesire);
		transfo =new TransfoChocolat(premiere,finis);
		this.venteChocolat=0;
		this.achatCacao=0;
		System.out.println();
		this.achats= new Indicateur("5_TRAN_USA_achats",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.achats);
		this.ventes= new Indicateur("5_TRAN_USA_ventes",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.ventes);
		this.solde=new Indicateur("5_TRAN_USA_solde",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);	
	}

	public void next(){
		produirechocolat();
		miseAJourJournal();
		payerstock();
		achetermatierepremiere();
		
		if(this.achats!=null){
			this.achats.setValeur(this, this.getAchatCacao());
			this.ventes.setValeur(this, this.MiseAJourVente());
			this.solde.setValeur(this, this.tresorie.getCompteCourant());
			this.achatCacao=0;
		}
		//System.out.println(journal);
	}
	//souchu
	
	private void miseAJourJournal(){
		step++;
		journal.ajouter("Journal Usa : step "+step);
		journal.ajouter("");
		journal.ajouter("");
		journal.ajouter("Notre Stock de Choco est "+this.finis.getStockChocolat());
		journal.ajouter("Notre Stock de Cacao est "+this.premiere.getCacao());
	}

	private void payerstock(){
		double avant=this.tresorie.getCompteCourant();
		this.tresorie.removeMoney(this.finis.getStockChocolat()*Prixstockage*Bornesmin);	
		for (int i=0;i<3;i++){
			this.tresorie.removeMoney(this.premiere.getIngredient(i)*Prixstockage);	
		}
		journal.ajouter("Cout stockage = "+(avant-this.tresorie.getCompteCourant()));
	}

	public void achetermatierepremiere(){
		for (int i=1;i<4;i++){
			if (tresorie.getCompteCourant()-((Stockdesire-this.premiere.getIngredient(i))*prixmatprem.get(i-1))>0){
				tresorie.setCompteCourant(tresorie.getCompteCourant()-((Stockdesire-this.premiere.getIngredient(i))*prixmatprem.get(i-1)));
				this.premiere.setIngredient(i, Stockdesire-this.premiere.getIngredient(i));
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
		else if (finis.getStockChocolat()<200*Uniteventechocolat){
			double prix= Bornesmax-((finis.getStockChocolat()-1*Uniteventechocolat)/((200-1)*Uniteventechocolat)*(Bornesmax-Bornesmin));
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
		this.tresorie.setCompteCourant(this.tresorie.getCompteCourant()+quantite*prix);	
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
		journal.ajouter("On a acheté pour un prix unitaire de  "+prix+"Et tant de tonne  "+achete);
		this.tresorie.setCompteCourant(tresorie.getCompteCourant()-prix*achete);
		this.premiere.setCacao(premiere.getCacao()+achete);
		this.achatCacao+=achete;
	}

	public int hashCode() {
		return this.getNom().hashCode();
	}

	public String toString(){
		return "Nom = "+this.getNom()+"Tresorie ="+this.tresorie.getCompteCourant()+" StockChoco "+this.finis.getStockChocolat();
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
