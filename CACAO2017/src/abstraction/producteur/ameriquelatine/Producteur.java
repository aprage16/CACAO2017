
package abstraction.producteur.ameriquelatine;
//26/04 Adrien

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

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
	private Indicateur cout;
	private Indicateur cours;
	private Indicateur qtemiseenvente;
	private Indicateur recoltee;
	private Journal journal;
	
	public Producteur(){
		this.nom="Producteur AmeriqueLatine" ;
		this.recolte=new Recolte(0.8) ;
		this.stock=new Stock(recolte) ;
		this.ventes=new GestionVentes(stock) ;
		stock.setGestionVente(ventes) ;
		this.treso=new Tresorerie(stock);
		this.quantiteVendue=new Indicateur("4_PROD_AMER_quantiteVendue", this,0.0);
		MondeV1.LE_MONDE.ajouterIndicateur(this.quantiteVendue) ;
		this.solde=new Indicateur("4_PROD_AMER_solde", this, treso.getTresorerie()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.solde);
		this.stockind=new Indicateur("4_PROD_AMER_stock", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockind);
		this.stockInt=new Indicateur("4_PROD_AMER_stockInt", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockInt);
		this.cout=new Indicateur("4_PROD_AMER_cout", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.cout);
		this.cours=new Indicateur("4_PROD_AMER_cours", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.cours);
		this.qtemiseenvente=new Indicateur("4_PROD_AMER_qtemiseenvente", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.qtemiseenvente);
		this.recoltee=new Indicateur("4_PROD_AMER_recoltee", this,0.0) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.recoltee);
		this.journal=new Journal("Journal de Prod Amerique Latine");
		MondeV1.LE_MONDE.ajouterJournal(this.journal);
		
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
	public int stockintermediaire(){
		return this.stock.getInitial()+this.recolte.getQterecoltee();
	}
	public void miseAJourStock(){
		this.stock.miseAJourStock((int)(this.stockintermediaire()-this.qtevendue));
	}

	public void notificationVente(double quantite, double coursActuel) {
		this.coursActuel=coursActuel;
		this.qtevendue=quantite;
		this.setCoursActuel(coursActuel);
		this.ventes.setQuantiteVendue(quantite);
		this.quantiteVendue.setValeur(this, quantite);
		miseAJourStock() ; 
		this.solde.setValeur(this, this.treso.getTresorerie());
		this.stockind.setValeur(this, this.stock.getInitial());
		this.stockInt.setValeur(this, this.stockintermediaire());
		this.cout.setValeur(this, this.treso.cout());
		this.cours.setValeur(this, coursActuel);
		this.recoltee.setValeur(this, this.recolte.getQterecoltee());
		this.qtemiseenvente.setValeur(this, this.quantiteMiseEnvente());
	}
	
	public double quantiteMiseEnvente() {
		return (int)(0.8*this.stockintermediaire());
	}
	
	public void next() {
		this.treso.setTresorerie(this.treso.getTresorerie()+coursActuel*qtevendue-treso.cout());
		recolte.miseAJourIndice(); //mise à jour de l'indice de recolte
		String stock=new String(""+this.stock.getInitial());
		String solde=new String(""+this.treso.getTresorerie());
		String quantitevendue=new String(""+this.qtevendue);
		if (this.journal!=null){
			this.journal.ajouter(" valeur de Stock  =  <font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de Stock  =  <font color=\"maroon\">"+solde+"</font> millions d'euros au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de Stock  =  <font color=\"maroon\">"+quantitevendue+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
			
			}
		
	}
}
