package abstraction.producteur.ameriquelatine;
//26/04 Adrien

import java.util.ArrayList;
import java.util.List;

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;
import abstraction.producteur.cotedivoire.contrats.Devis;
import abstraction.producteur.cotedivoire.contrats.IContratProd;


public class Producteur implements IProducteur, Acteur, IContratProd  {
	public String nom;
	private Tresorerie treso;
	private double qtevendue;
	private Stock stock ;
	private Recolte recolte ;
	private double coursActuel;
	private Indicateur quantiteVendue;
	private Indicateur qtemiseenvente;
	private Indicateur production;
	private Journal journal;
	public ArrayList<Devis> ldevis ;
	private final static int PROD_MOY=20000 ;
	
	public Producteur(){
		this.nom="Producteur AmeriqueLatine" ;
		this.stock=new Stock(this);
		this.recolte=new Recolte(0.8, this, stock) ;
		this.treso=new Tresorerie(stock, recolte, this);
		this.quantiteVendue=new Indicateur("4_PROD_AMER_quantiteVendue", this,qtevendue);
		MondeV1.LE_MONDE.ajouterIndicateur(this.quantiteVendue) ;
		this.journal=new Journal("Producteur Amerique Latine");
		this.qtemiseenvente=new Indicateur("4_PROD_AMER_qtemiseenvente", this,0);//this.quantiteMiseEnvente()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.qtemiseenvente);
		this.production=new Indicateur("4_PROD_AMER_production", this,this.recolte.getQterecoltee()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.production);
		MondeV1.LE_MONDE.ajouterJournal(this.journal);
		this.ldevis = new ArrayList<Devis>() ;
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
	public void setCoursActuel(double cours){
		this.coursActuel=cours;
	}
	public double getQteVendue(){
		return this.quantiteVendue.getValeur();
	}
	public void notificationVente(double quantite, double coursActuel) {
		this.journal.ajouter("--- notif vente ---");
		this.stock.retrait((int)quantite);
		this.treso.encaissement(coursActuel*quantite);
		this.journal.ajouter(" retrait de Stock  =  "+(int)quantite+" --> "+this.stock.getStock());//<font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
		this.quantiteVendue.setValeur(this, quantite);
		this.production.setValeur(this, this.recolte.getQterecoltee());
		this.setCoursActuel(coursActuel);
		String stock=new String(""+this.stock.getStock());
		String solde=new String(""+this.treso.getTresorerie());
		if (this.journal!=null){
			this.journal.ajouter(" valeur de Stock  =  <font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de Solde  =  <font color=\"maroon\">"+solde+"</font> euros au <b>step</b> "+Monde.LE_MONDE.getStep());
			this.journal.ajouter(" valeur de la quantite vendue  =  <font color=\"maroon\">"+quantite+"</font> tonnes de fèves au <b>step</b> au prix de "+this.getCoursActuel()+"$ par tonne"+Monde.LE_MONDE.getStep());
			}
		this.journal.ajouter("--- fin notif vente---");
		recolte.setSurfaceCultivable(qtemiseenvente.getValeur());
	}
	public double quantiteMiseEnvente() {
		int qte=0;
		if (this.stock.getStock()>=8000){
			qte=(int)(this.stock.getStock());
		}
		else {
			qte=(int)(0.8*this.stock.getStock());
		}
		this.journal.ajouter("mis en vente :"+qte);
		this.qtemiseenvente.setValeur(this,qte);
		return qte;
	}

	public void next() {
		if(Monde.LE_MONDE.getStep()%26==0){
			treso.investissement();
		}
		recolte.miseAJourIndice();
		if (Monde.LE_MONDE.getStep()<=19){ // Avant le step 19, on ajoute à chaque step dans prod
			this.stock.ajout(this.recolte.getQterecoltee(), Monde.LE_MONDE.getStep()-1);
		}
		else {
			ArrayList<Double> copie=new ArrayList<Double>(stock.getProd());
			for (int i=0; i<this.stock.getProd().size()-1;i++){
				this.stock.setProd(i, copie.get(i+1)); // On  crée une copie où on décale toutes les valeurs
			}
			this.stock.ajout(this.recolte.getQterecoltee(), this.stock.getProd().size()-1); // On ajoute les futures récoltes à la fin de la liste (les plus récentes)
		}
		journal.ajouter("ajout recolte :"+this.recolte.getQterecoltee()+"--> "+this.stock.getStock());
		this.treso.decaissement(treso.cout());
		this.treso.licenciement();
		this.treso.recrutement();
		for (int i=0; i<this.ldevis.size(); i++){
			if(this.ldevis.get(i).getDebut() >= Monde.LE_MONDE.getStep()-26 ){
				this.stock.retrait(this.ldevis.get(i).getQttFinale());
				this.treso.encaissement(this.ldevis.get(i).getQttFinale()*this.ldevis.get(i).getPrix()) ;
				}
		}	
		}
		
	

	public void envoieDevis(Devis d) {
		// TODO Auto-generated method stub
		this.ldevis.add(d);
	}

	public void qttLivrablePrix() {
		int somme=0;
		for (int i=0; i<this.ldevis.size(); i++){
			if(this.ldevis.get(i).getDebut() >= Monde.LE_MONDE.getStep()-26 ){
				somme+=this.ldevis.get(i).getQttLivrable();
			}
		}
		for (int i=0; i<this.ldevis.size(); i++){
			if(this.ldevis.get(i).getDebut() == Monde.LE_MONDE.getStep()){
				if (PROD_MOY/(2*this.ldevis.size())-somme > this.ldevis.get(i).getQttVoulue()){
					this.ldevis.get(i).setQttLivrable(this.ldevis.get(i).getQttVoulue());
			}
				else{
					this.ldevis.get(i).setQttLivrable(0);
			}
				this.ldevis.get(i).setPrix(0.9*this.coursActuel);
			}
		}	
	}

	public void notifContrat() {
		// TODO Auto-generated method stub
		
	}
}
