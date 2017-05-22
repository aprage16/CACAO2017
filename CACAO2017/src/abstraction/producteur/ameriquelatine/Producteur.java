
package abstraction.producteur.ameriquelatine;
//26/04 Adrien

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Producteur implements IProducteur, Acteur {
	public String nom;
	private Tresorerie treso;
	private double qtevendue;
	private Stock stock ;
	private Recolte recolte ;
	private double coursActuel;
	private Indicateur quantiteVendue;
	private Indicateur solde; //Trésorerie
	private Indicateur stockind ;
	private Indicateur qtemiseenvente;
	private Journal journal;
	
	public Producteur(){
		this.nom="Producteur AmeriqueLatine" ;
		this.recolte=new Recolte(0.8) ;
		this.stock=new Stock();
		this.treso=new Tresorerie(stock);
		this.quantiteVendue=new Indicateur("4_PROD_AMER_quantiteVendue", this,qtevendue);
		MondeV1.LE_MONDE.ajouterIndicateur(this.quantiteVendue) ;
		this.solde=new Indicateur("4_PROD_AMER_solde", this, treso.getTresorerie()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.solde);
		this.stockind=new Indicateur("4_PROD_AMER_stock", this,this.stock.getStock()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockind);
		this.qtemiseenvente=new Indicateur("4_PROD_AMER_qtemiseenvente", this,this.quantiteMiseEnvente()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.qtemiseenvente);
		this.journal=new Journal("Journal de Prod Amerique Latine");
		MondeV1.LE_MONDE.ajouterJournal(this.journal);
	}
	public String getNom(){
		return this.nom;
	}
	
	public int hashCode() {
		return this.getNom().hashCode() ;
	}
	public double getCoursActuel(){
		return this.coursActuel;
	}
	
	public void setQtevendue(double qte){
		this.qtevendue=qte;
	}
	public double getQteVendue(){
		return this.qtevendue;
	}
	public void notificationVente(double quantite, double coursActuel) {
		this.stock.retrait((int)quantite);
		this.treso.encaissement(coursActuel*quantite);
		this.journal.ajouter(" retrait de Stock  =  "+(int)quantite+" --> "+this.stock.getStock());//<font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
		this.quantiteVendue.setValeur(this, quantite);
		this.solde.setValeur(this, this.treso.getTresorerie());
		this.stockind.setValeur(this, this.stock.getStock());
		this.qtemiseenvente.setValeur(this, this.quantiteMiseEnvente());
	}
	public double quantiteMiseEnvente() {
		return (int)(0.8*this.stock.getStock());
	}
	
	public void next() {
		// rec
		recolte.miseAJourIndice(); //mise à jour de l'indice de recolte
		this.stock.ajout(this.recolte.getQterecoltee());
		this.treso.decaissement(treso.cout());
		String stock=new String(""+this.stock.getStock());
		String solde=new String(""+this.treso.getTresorerie());
		String quantitevendue=new String(""+this.qtevendue);
		if (this.journal!=null){
			this.journal.ajouter(" valeur de Stock  =  <font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de Solde  =  <font color=\"maroon\">"+solde+"</font> millions d'euros au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de la quantite vendue  =  <font color=\"maroon\">"+quantitevendue+"</font> tonnes de fèves au <b>step</b> au prix de "+this.getCoursActuel()+"$ par tonne"+Monde.LE_MONDE.getStep());
			
			}
	}
}
