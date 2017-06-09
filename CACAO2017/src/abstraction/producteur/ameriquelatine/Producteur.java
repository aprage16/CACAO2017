package abstraction.producteur.ameriquelatine;
//26/04 Adrien

import java.util.ArrayList;
import java.util.List;

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
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
	private Indicateur solde; //Trésorerie
	private Indicateur stockind ;
	private Indicateur qtemiseenvente;
	private Indicateur production;
	private Journal journal;
	public List<Devis> ldevis ;
	
	public Producteur(){
		this.nom="Producteur AmeriqueLatine" ;
		this.recolte=new Recolte(0.8) ;
		this.stock=new Stock();
		this.treso=new Tresorerie(stock, recolte);
		this.quantiteVendue=new Indicateur("4_PROD_AMER_quantiteVendue", this,qtevendue);
		MondeV1.LE_MONDE.ajouterIndicateur(this.quantiteVendue) ;
		this.solde=new Indicateur("4_PROD_AMER_solde", this, treso.getTresorerie()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.solde);
		this.stockind=new Indicateur("4_PROD_AMER_stock", this,this.stock.getStock()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.stockind);
		this.journal=new Journal("Journal de Prod Amerique Latine");
		this.qtemiseenvente=new Indicateur("4_PROD_AMER_qtemiseenvente", this,0);//this.quantiteMiseEnvente()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.qtemiseenvente);
		this.production=new Indicateur("4_PROD_AMER_production", this,this.recolte.getQterecoltee()) ;
		MondeV1.LE_MONDE.ajouterIndicateur(this.production);
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
	public void setCoursActuel(double cours){
		this.coursActuel=cours;
	}
	public double getQteVendue(){
		return this.quantiteVendue.getValeur();
	}
	public void notificationVente(double quantite, double coursActuel) {
		this.journal.ajouter("--- notif vente ---");
/*a modiifier*/		this.stock.retrait((int)quantite);
		this.treso.encaissement(coursActuel*quantite);
		this.journal.ajouter(" retrait de Stock  =  "+(int)quantite+" --> "+this.stock.getStock());//<font color=\"maroon\">"+stock+"</font> tonnes de fèves au <b>step</b> "+Monde.LE_MONDE.getStep());
		this.quantiteVendue.setValeur(this, quantite);
		this.solde.setValeur(this, this.treso.getTresorerie());
		this.stockind.setValeur(this, this.stock.getStock());
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
	}
	public double quantiteMiseEnvente() {
		this.journal.ajouter("mis en vente :"+(int)(0.8*this.stock.getStock()));
		this.qtemiseenvente.setValeur(this,(int)(0.8*this.stock.getStock()));
		return (int)(0.8*this.stock.getStock());
	}
	
	public void next() {
		recolte.miseAJourIndice();
		if (Monde.LE_MONDE.getStep()<=19){ // Avant le step 19, on ajoute à chaque step dans prod
			this.stock.ajout(this.recolte.getQterecoltee(), Monde.LE_MONDE.getStep()-1);
		}
		else {
			ArrayList<Integer> copie=new ArrayList<Integer>(stock.getProd());
			for (int i=0; i<this.stock.getProd().size()-1;i++){
				this.stock.setProd(i, copie.get(i+1)); // On  crée une copie où on décale toutes les valeurs
			}
			this.stock.ajout(this.recolte.getQterecoltee(), this.stock.getProd().size()); // On ajoute les futures récoltes à la fin de la liste (les plus récentes)
		}
		journal.ajouter("ajout recolte :"+this.recolte.getQterecoltee()+"--> "+this.stock.getStock());
		this.treso.decaissement(treso.cout());
		}
	
	@Override
	public void envoieDevis(List<Devis> l) {
		// TODO Auto-generated method stub
		this.ldevis=l;
	}
	@Override
	public void qttLivrablePrix() {
		for (int i=0; i<this.ldevis.size(); i++){
			this.ldevis.get(i).setQttLivrable(2000);
			this.ldevis.get(i).setPrix(2000);
		}
	}
	@Override
	public void notifContrat() {
		// TODO Auto-generated method stub
		// Mettre prix et qté finale en tant que variable?
	}
}
