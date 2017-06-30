package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.List;

import abstraction.distributeur.europe.IDistributeur;
import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Transformateur;
import abstraction.transformateur.europe.Devis;
import abstraction.transformateur.europe.IDistriContrat;

public class DistributeurUS implements IDistributeur, DistribClient, IDistriContrat{
	public static String  nomIndicateurStock = "1_DISTR_US_stock";
	public static String nomIndicateurFonds = "1_DISTR_US_fonds";
	public static String nomIndicateurAchat = "1_DISTR_US_quantitee_Achetee";
	public static String nomIndicateurVente = "1_DISTR_US_quantitee_Vendue";
	public static double fondsIni = 50000.0;
	public static double stockIni = 6.25;
	public static double prixKg=10*Math.pow(10,-6);
	public static double uniteChoc=1000;
	public static double coefAleatoire=0.9+Math.random()*0.2;;
	public static final double[] CONSO_PREVUE={80,80,80,120,80,80,80,180,80,80,80,80,80,80,80,80,80,80,80,80,80,150,80,80,80,260};
	public static int tempsPerim=6;
	public static double fondsMin=500000;
	
	private Gestion gestion;
	private Demande demande;
	private String nom;
	private List<Devis> devis;
	
	private Indicateur fonds;
	private Indicateur stock;
	private Indicateur quantitee_Achetee;
	private Indicateur quantitee_Vendue;
	

	
	public DistributeurUS(Gestion gestion, Demande demande, String nom, List<Devis> devis, Indicateur fonds, Indicateur stock, Indicateur achat, Indicateur vente){
		this.gestion=gestion;
		this.demande=demande;
		this.nom=nom;
		this.devis=devis;
		this.fonds=fonds;
		this.stock=stock;
		this.quantitee_Achetee=achat;
		this.quantitee_Vendue=vente;
	}
	
	
	public DistributeurUS(){


		this.gestion= new Gestion(new ArrayList<Double>(tempsPerim), fondsIni);
		this.demande=new Demande(Demande.commandeIni);
		this.nom="distributeurUS";
		this.devis=new ArrayList<Devis>(2);
		
		this.stock = new Indicateur(nomIndicateurStock, this, stockIni);
		this.fonds = new Indicateur(nomIndicateurFonds, this, fondsIni);
		this.quantitee_Achetee=new Indicateur(nomIndicateurAchat,this,0);
		this.quantitee_Vendue=new Indicateur(nomIndicateurVente,this,0);
		
		
    	Monde.LE_MONDE.ajouterIndicateur( this.stock );
    	Monde.LE_MONDE.ajouterIndicateur( this.fonds );
    	Monde.LE_MONDE.ajouterIndicateur(this.quantitee_Achetee);
    	Monde.LE_MONDE.ajouterIndicateur(this.quantitee_Vendue);
    	
    	
    	
    	this.getGestion().addStock(stockIni);
	}
	
	
	public void next(){
		this.quantitee_Achetee.setValeur(this, 0);
		this.quantitee_Vendue.setValeur(this, 0);
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


	
	public double getPrixMax(){
		
		return this.prixMax();
	}
	
	public void notif(Vente vente){
		double stockCourant=this.getGestion().getStock().get(this.getGestion().getStock().size()-1);
		this.getGestion().setStock(this.getGestion().getStock().size()-1, stockCourant+vente.getQuantite());
		this.getGestion().setFonds(this.getGestion().getFonds()-vente.getPrix());
		this.quantitee_Achetee.setValeur(this, this.quantitee_Achetee.getValeur()+vente.getQuantite());
	}

	public String getNom() {
		return this.nom;
	}

	
	public Indicateur getIndicateurStock() {
		return this.stock;
	}


	public Indicateur getSolde() {
		return this.fonds;
	}
	
	public Demande getDemande(){
		return this.demande;
	}
	
	public void setDemande(Demande demande){
		this.demande=demande;
	}
	
	public double prixMax(){
		double prixmax=Math.random()*0.08;
		return prixmax;
	}
	
	public int hashCode() {//donne un critère d'ordre qui permet de l'utiliser en clé de hashMap
		return this.getNom().hashCode();
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
		this.quantitee_Vendue.setValeur(this, this.quantitee_Vendue.getValeur()+vente.getQuantite());
	}


	@Override
	public void receptionDevis(Devis devis) {
		// TODO Auto-generated method stub
		if (devis.getTransfo() instanceof Transformateur){
			this.devis.add(0,devis);
		}
		else{
			this.devis.add(1,devis);
		}
	}


	@Override
	public void demandeQuantite() {
		// TODO Auto-generated method stub
		this.devis.get(0).setQ2(Math.min(926250*(0.3+0.9*this.devis.get(0).getP1()/(this.devis.get(0).getP1()+this.devis.get(1).getP1())), this.devis.get(0).getQ1()));
		this.devis.get(1).setQ2(Math.min(0.9*926250-this.devis.get(0).getQ2(), this.devis.get(1).getQ1()));
	}


	@Override
	public void contreProposition() {
		// TODO Auto-generated method stub
		if (this.devis.get(0).getQ2()<=this.devis.get(0).getQ1()){
			this.devis.get(0).setP2(this.devis.get(0).getP1()*0.5);
		} else{
			this.devis.get(0).setP2(this.devis.get(0).getP1()*0.9);
		}
		if (this.devis.get(1).getQ2()<=this.devis.get(1).getQ1()){
			this.devis.get(1).setP2(this.devis.get(1).getP1()*0.5);
		}else{
			this.devis.get(1).setP2(this.devis.get(1).getP1()*0.9);
		}
		
		
	}


	@Override
	public void acceptationFinale() {
		// TODO Auto-generated method stub
		this.devis.get(0).setChoixD(true);
		this.devis.get(1).setChoixD(true);
		
	}



}