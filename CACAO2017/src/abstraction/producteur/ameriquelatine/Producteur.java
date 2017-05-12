
package abstraction.producteur.ameriquelatine;
//26/04 Adrien

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Producteur implements IProducteur, Acteur {
	public String nom;
	private GestionVentes ventes;
	private Tresorerie treso;
	private double coursActuel;
	private double qtevendue;
	private Stock stock ;
	private Recolte recolte ;
	private Indicateur quantiteVendue;
	private Indicateur solde; //Trésorerie
	private Indicateur stockind ;
	private Indicateur stockInt;

	
	public Producteur(){
		this.nom="Producteur AmeriqueLatine" ;
		this.recolte=new Recolte(0.8) ;
		this.stock=new Stock(recolte) ;
		this.ventes=new GestionVentes(stock) ;
		stock.setGestionVente(ventes) ;
		this.treso=new Tresorerie(stock);
		this.quantiteVendue=new Indicateur("4_PROD_AMER_quantiteVendue", this,0.0);
		MondeV1.LE_MONDE.ajouterIndicateur(this.quantiteVendue) ;
		this.solde=new Indicateur("4_PROD_AMER_solde", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.solde);
		this.stockind=new Indicateur("4_PROD_AMER_stock", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockind);
		this.stockInt=new Indicateur("4_PROD_AMER_stockInt", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockInt);
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public int hashCode() {
		return this.getNom().hashCode() ;
	}


	public void setCoursActuel(double coursActuel){
		this.coursActuel=coursActuel;
	}
	public void setQtevendue(double qte){
		this.qtevendue=qte;
	}
	public double getQteVendue(){
		return this.qtevendue;
	}
	

	public void notificationVente(double quantite, double coursActuel) {
		this.treso.setTresorerie(this.treso.getTresorerie()+coursActuel*quantite-treso.cout()); //dans le next?
		this.setCoursActuel(coursActuel);
		this.ventes.setQuantiteVendue(quantite);
		this.quantiteVendue.setValeur(this, quantite);
		stock.miseAJourStock() ; 
		this.solde.setValeur(this, this.treso.getTresorerie());
		this.stockind.setValeur(this, this.stock.getInitial());
		this.stockInt.setValeur(this, this.stock.stockintermediaire());
	}
	
	public double quantiteMiseEnvente() {
		return this.ventes.getQuantiteMiseEnVente();
	}
	
	public void next() {
		recolte.miseAJourIndice(); //mise à jour de l'indice de recolte
		
	}
}
