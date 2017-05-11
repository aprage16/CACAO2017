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
	private TransfoChocolat Transfo;
	private Tresorerie tresorie=new Tresorerie(500000);
	private int uniteventechocolat=10000;//10000 tonnes
	private double bornesmax=80000000;
	private double bornesmin=40000000;
	private ArrayList<Integer> prixmatprem = new ArrayList<Integer>();
	private double venteChocolat;
	private double achatCacao;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	private Journal journal;

	/* Nos indicateurs sont :
	 * -Compte courant de la Trésorie
	 * -Dernières vente de Chocolat
	 * -Derniers achat de Cacao
	 */
	
	public double getVenteChocolat(){
		double tampon = this.venteChocolat;
		this.venteChocolat=0;
		return tampon;
	}
	
	public double getAchatCacao(){
		return this.achatCacao;
	}
	
	
	public TransformateurUsa(){
		prixmatprem.add(350);//Prix matière première à la tonne en euros.
		prixmatprem.add(25);
		prixmatprem.add(400);
		finis = new StockProduitsFinis(200*uniteventechocolat);
		premiere =new StockMatPremiere(1000000,1000000,1000000,1000000);
		Transfo =new TransfoChocolat(premiere,finis);
		this.venteChocolat=0;
		this.achatCacao=0;
		this.achats= new Indicateur("5_TRAN_USA_achats",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.achats);
		this.ventes= new Indicateur("5_TRAN_USA_ventes",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.ventes);
		this.solde=new Indicateur("5_TRAN_USA_solde",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);
	}
	
	public TransformateurUsa(Monde mo){
		this();
		mo.ajouterIndicateur(this.achats);
		mo.ajouterIndicateur(this.ventes);
		mo.ajouterIndicateur(this.solde);		
	}

	public void next(){
		produirechocolat();
		achetermatièrepremière();
		this.achats.setValeur(this, this.getAchatCacao());
		this.ventes.setValeur(this, this.getVenteChocolat());
		this.solde.setValeur(this, this.tresorie.getCompteCourant());
	}
	//souchu
	public void achetermatièrepremière(){
		double[] stocks=new double[3];
		stocks[0]=premiere.getLait();
		stocks[1]=premiere.getSucre();
		stocks[2]=premiere.getLecitine();
		for (int i=0;i<3;i++){
			if (tresorie.getCompteCourant()-((1000000-stocks[i])*prixmatprem.get(i))>0){
				tresorie.setCompteCourant(tresorie.getCompteCourant()-((1000000-stocks[i])*prixmatprem.get(i)));
			}
		}
		premiere.setLait(1000000);
		premiere.setLecitine(1000000);
		premiere.setSucre(1000000);
	}

	public void produirechocolat(){
		double StockSouhaite =200*uniteventechocolat;
		Transfo.produireChoco(StockSouhaite-finis.getStockChocolat());
	}

	public double getprixMin(){
		if (finis.getStockChocolat()<uniteventechocolat){
			//journal.ajouter("Prix min="+bornesmax+1);
			return bornesmax+1;
		}
		else if (finis.getStockChocolat()<200*uniteventechocolat){
			double prix= bornesmax-((finis.getStockChocolat()-10*uniteventechocolat)/(190*uniteventechocolat)*(bornesmin-bornesmax));
			//journal.ajouter("Prix min="+prix);
			return prix;
		}
		else{
			//journal.ajouter("Prix min="+bornesmin);
			return bornesmin;
		}
	}
	@Override
	public void notif(double prix, double quantité) {
		this.finis.enleverChoco(quantité);
		this.tresorie.setCompteCourant(this.tresorie.getCompteCourant()+quantité*prix);	
		}

	public double QteSouhaite(){
		double q= 1000000;
		return q-this.premiere.getCacao();

	}
	@Override
	public String getNom() {
		return "Transfo Usa";
	}

	public void notificationAchat(double achete, double prix){
		this.tresorie.setCompteCourant(tresorie.getCompteCourant()-prix);
		this.premiere.setCacao(premiere.getCacao()+achete);
		this.venteChocolat+=achete;
	}

	public int hashCode() {
		return this.getNom().hashCode();
	}
	
	public String toString(){
		return "Nom = "+this.getNom()+"Tresorie ="+this.tresorie.getCompteCourant()+" StockChoco "+this.finis.getStockChocolat();
	}

	public void test(){
		System.out.println(this.toString());
		this.notif(6000, 100*uniteventechocolat);
		System.out.println(this.toString());
		this.produirechocolat();
		System.out.println(this.toString());
		this.achetermatièrepremière();
		System.out.println(this.toString());
		
		
	}

	public static void main(String[] arg){
		TransformateurUsa nous = new TransformateurUsa();
		nous.test();
		nous.next();
		nous.test();
	}
}
