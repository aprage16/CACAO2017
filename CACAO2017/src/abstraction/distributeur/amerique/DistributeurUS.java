package abstraction.distributeur.amerique;

import abstraction.distributeur.europe.IDistributeur;
import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class DistributeurUS implements IDistributeur{
	public static String  nomIndicateurStock = "1_DISTR_US_stock";
	public static String nomIndicateurFonds = "1_DISTR_US_fonds";
	public static double fondsIni = 5000.0;
	public static double stockIni = 62.5;
	public static double prixKg=10;
	public static double uniteChoc=1000;
	public static double coefAleatoire=0;
	
	private Gestion gestion;
	private Demande demande;
	
	private Indicateur fonds;
	private Indicateur stock;
	
	private Journal journalTest;
	
	public DistributeurUS(Gestion gestion, Demande demande, Indicateur fonds, Indicateur stock, Journal journal){
		this.gestion=gestion;
		this.demande=demande;
		this.fonds=fonds;
		this.stock=stock;
		this.journalTest=journal;
	}
	
	
	public DistributeurUS(){


		this.gestion= new Gestion(fondsIni,stockIni);
		this.demande=new Demande(Demande.commandeIni);
		
		this.stock = new Indicateur(nomIndicateurStock, this, stockIni);
		this.fonds = new Indicateur(nomIndicateurFonds, this, fondsIni);
		
		this.journalTest=new Journal("journalTest");
		
    	Monde.LE_MONDE.ajouterIndicateur( this.stock );
    	Monde.LE_MONDE.ajouterIndicateur( this.fonds );
    	Monde.LE_MONDE.ajouterJournal(this.getJournal());
    	

	}
	
	
	public void next(){
		//System.out.println(Monde.LE_MONDE.getStep()+" "+this.getGestion().getDemande().demandeStep());
		if (this.getGestion().getStock()>=this.getDemande().getCommande()){
		
			this.getGestion().setStock(this.getGestion().getStock()-this.getDemande().getCommande());
			this.getGestion().setFonds(this.getGestion().getFonds()+this.getDemande().getCommande()*prixKg*uniteChoc);
		}
		else{
			double vendu=this.getGestion().getStock();
			this.getGestion().setStock(0);
			this.getGestion().setFonds(this.getGestion().getFonds()+vendu*prixKg*uniteChoc);
		}
		
		this.getDemande().setCommande(this.getDemande().demandeStep());
		coefAleatoire=0.9+Math.random()*0.2;
	}

	
	
	public double getStock() {
		return this.getGestion().getStock();
	}

	public void setStock(double stock) {
		this.getGestion().setStock(stock);
	}

	public double getFonds() {
		return this.getGestion().getFonds();
	}



	public Gestion getGestion(){
		return this.gestion;
	}


	
	public double getPrixMax(){
		return this.prixMax();
	}
	
	public void notif(Vente vente){
		this.getGestion().setStock(this.getGestion().getStock()+vente.getQuantite());
		this.getGestion().setFonds(this.getGestion().getFonds()-vente.getPrix());
		this.getJournal().ajouter("quantitee achetee : "+vente.getQuantite());
		this.getJournal().ajouter("prix obtenu : "+vente.getPrix());
	}

	public String getNom() {
		return "Distributeur USA";
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
	
	public double prixMax(){//Premier test, avec ça on utilise tous nos fonds le premier mois
		double aacheter=this.getDemande().demandeStep()-this.getGestion().getStock();
		double prixmax=this.getGestion().getFonds()/aacheter;
		
		
		return prixmax;
	}
	
	public Journal getJournal(){
		return this.journalTest;
	}
}
