package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.List;

import abstraction.distributeur.europe.Distributeur;
import abstraction.distributeur.europe.IDistributeur;
import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class DistributeurUS implements IDistributeur, DistribClient{
	public static String  nomIndicateurStock = "1_DISTR_US_stock";
	public static String nomIndicateurFonds = "1_DISTR_US_fonds";
	public static String nomIndicateurAchetee="1_DISTR_US_quantiteAchetee";
	public static String nomIndicateurVendue="1_DISTR_US_quantiteVendue";
	public static double fondsIni = 50000.0;
	public static double stockIni = 6.25;
	public static double prixKg=10*Math.pow(10,-6);
	public static double uniteChoc=1000;
	public static double coefAleatoire=0.9+Math.random()*0.2;;
	public static final double[] CONSO_PREVUE={80,80,80,120,80,80,80,180,80,80,80,80,80,80,80,80,80,80,80,80,80,150,80,80,80,260};
	public static int tempsPerim=6;
	public static double fondsMini=500000000;
	
	private Gestion gestion;
	//private Demande demande;
	private String nom;
	
	private Indicateur fonds;
	private Indicateur stock;
	private Indicateur quantiteAchetee;
	private Indicateur quantiteVendue;
	
	private Journal journalTest;
	
	public DistributeurUS(Gestion gestion, String nom,double prixAchat, double prixVentePerdue, Indicateur fonds, Indicateur stock, Indicateur achetee, Indicateur vendu, Journal journal){
		this.gestion=gestion;
		this.nom=nom;
		this.fonds=fonds;
		this.stock=stock;
		this.quantiteAchetee=achetee;
		this.quantiteVendue=vendu;
		this.journalTest=journal;
	}
	
	
	public DistributeurUS(){


		this.gestion= new Gestion(new ArrayList<Double>(tempsPerim), fondsIni);
		//this.demande=new Demande(Demande.commandeIni);
		this.nom="distributeurUS";
		
		this.stock = new Indicateur(nomIndicateurStock, this, stockIni);
		this.fonds = new Indicateur(nomIndicateurFonds, this, fondsIni);
		this.quantiteAchetee = new Indicateur(nomIndicateurAchetee, this, 0);
		this.quantiteVendue = new Indicateur(nomIndicateurVendue, this, 0);
		
		this.journalTest=new Journal("journalTest");
		
    	Monde.LE_MONDE.ajouterIndicateur( this.stock );
    	Monde.LE_MONDE.ajouterIndicateur( this.fonds );
    	Monde.LE_MONDE.ajouterIndicateur(this.quantiteAchetee);
    	Monde.LE_MONDE.ajouterIndicateur(this.quantiteVendue);
    	Monde.LE_MONDE.ajouterJournal(this.getJournal());
    	
    	
    	this.getGestion().addStock(stockIni);
	}
	
	public double getPrixMax(){
		return this.calculPrixMax();
	}
	
	public double calculPrixMax(){
		return Math.max(0,Math.min(8, ((this.getFonds()-fondsMini)/(MarcheClients.demandeUS-this.getGestion().sumStock()))));
	}
	
	public void next(){
		this.getAchetee().setValeur(this, 0);
		this.getVendue().setValeur(this, 0);
		for (int k=0;k<this.getGestion().getStock().size()-1;k++){
			this.getGestion().setStock(k, this.getGestion().getStock().get(k+1));
		}
			
		this.getGestion().getStock().set(this.getGestion().getStock().size()-1,0.0);
		 
		
		//System.out.println(Monde.LE_MONDE.getStep()+" "+this.getGestion().getDemande().demandeStep());
		//DemandeMonde.vendusUS=this.getDemande().getCommande();
		
		//On n'a plus besoin de tout ça vu que le marche client s'en occupe maintenat
		/*if (this.getGestion().getStock()>=this.getDemande().getCommande()){
		
			this.setStock(this.getGestion().getStock()-this.getDemande().getCommande());
			this.setFonds(this.getGestion().getFonds()+this.getDemande().getCommande()*prixKg*uniteChoc);
		}
		else{
			double vendu=this.getGestion().getStock();
			this.getGestion().setStock(0);
			this.getGestion().setFonds(this.getGestion().getFonds()+vendu*prixKg*uniteChoc);
		}
		
		this.getDemande().setCommande(this.getDemande().demandeStep());
		coefAleatoire=0.9+Math.random()*0.2;*/
	}

	
	
	public List<Double> getStock() {
		return this.getGestion().getStock();
	}

	/*public void setStock(double stock) {
		this.getGestion().setStock(stock);
		this.stock.setValeur(this, stock);
	}*/

	public double getFonds() {
		return this.getGestion().getFonds();
	}

	public void setFonds(double fonds){
		this.getGestion().setFonds(fonds);
		this.fonds.setValeur(this, fonds);
	}


	public Gestion getGestion(){
		return this.gestion;
	}


	
	
	public void notif(Vente vente){
		double stockCourant=this.getGestion().getStock().get(this.getGestion().getStock().size()-1);
		this.getGestion().setStock(this.getGestion().getStock().size()-1, stockCourant+vente.getQuantite());
		this.getGestion().setFonds(this.getGestion().getFonds()-vente.getPrix());
		this.getJournal().ajouter("quantitee achetee : "+vente.getQuantite());
		this.getJournal().ajouter("prix obtenu : "+vente.getPrix());
		this.getAchetee().setValeur(this, this.getAchetee().getValeur()+vente.getQuantite());
	}

	public String getNom() {
		return this.nom;
	}

	
	public Indicateur getIndicateurStock() {
		return this.stock;
	}
	
	public Indicateur getAchetee(){
		return this.quantiteAchetee;
	}
	public Indicateur getVendue(){
		return this.quantiteVendue;
	}


	public Indicateur getSolde() {
		return this.fonds;
	}
	
	/*public Demande getDemande(){
		return this.demande;
	}
	
	public void setDemande(Demande demande){
		this.demande=demande;
	}*/
	
	
	public int hashCode() {//donne un critère d'ordre qui permet de l'utiliser en clé de hashMap
		return this.getNom().hashCode();
	}
	public Journal getJournal(){
		
		return this.journalTest;
	}


	@Override
	public double getPrixClient() {
		return 10;
	}


	@Override
	public double getMisEnVente() {
		return this.getGestion().sumStock();
	}


	@Override
	public void notifVente(Vente vente) {
		this.getGestion().vendreStock(vente.getQuantite());
		this.stock.setValeur(this, this.stock.getValeur()-vente.getQuantite());
		this.setFonds(this.getFonds()+vente.getPrix()*vente.getQuantite());
		this.fonds.setValeur(this, this.fonds.getValeur()+vente.getQuantite()*vente.getPrix());
		this.getVendue().setValeur(this, this.getVendue().getValeur()+vente.getQuantite());
	}



}
