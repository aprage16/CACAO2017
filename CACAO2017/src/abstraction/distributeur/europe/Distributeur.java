/*Classe codée par Julien/Walid 
 */
package abstraction.distributeur.europe;
import abstraction.fourni.v0.*;
import abstraction.fourni.*;
import java.util.ArrayList;
import java.util.List;
public class Distributeur implements Acteur,IDistributeur{
	private Vente derniereVente; // derniere vente effectuee sur le marche
	private double stock;
	private double qteDemandee;
	private Indicateur stockI;
	private Indicateur fondsI;
	private String nom;
	private double fonds;
	private Journal journal;
	
	public Distributeur(Vente vente, double stock, double qteDemandee){ // penser à redocoder en enlevant les arguments du constructeur
		this.derniereVente = vente;
		this.stock = stock;
		this.qteDemandee = qteDemandee;
	}
	
	public Distributeur(){
		this.nom = "Distributeur europe";
		this.derniereVente = new Vente(2000,10);
		this.stock = 400;
		this.qteDemandee = 100;
		this.fonds = 100;
 	    this.journal = new Journal("Journal de "+this.nom);
 	    Monde.LE_MONDE.ajouterJournal(this.journal);
		
		this.fondsI = new Indicateur("2_DISTR_EU_stock", this, 100);
		this.stockI = new Indicateur("2_DISTR_EU_fonds", this, 0.1);
		
    	Monde.LE_MONDE.ajouterIndicateur( this.fondsI );
    	Monde.LE_MONDE.ajouterIndicateur( this.stockI );
        
	}
	
	public Indicateur getIndicateurStock(){
		return this.fondsI;
	}
	
	public Indicateur getSolde(){
		return this.stockI;
	}
	public double getStock() {
		return this.stock;
	}


	public void setStock(double stock) {
		this.stock = stock;
	}


	public double getQteDemandee() {
		return qteDemandee;
	}


	public void setQteDemandee(double qteDemandee) {
		this.qteDemandee = qteDemandee;
	}


	public Vente getDerniereVente() {
		return derniereVente;
	}


	public void setVente(Vente vente) {
		this.derniereVente = vente;
	}

	public Journal getJournal(){
		return this.journal;
	}
	
	public double getPrixMax(){
		double prixTransfo;
		prixTransfo = this.getDerniereVente().getPrix();
		double coeff = qteDemandee/this.stock;
		double prix = (1/coeff)*prixTransfo;

		return prix;
	}
	
	public void notif(Vente vente){
		this.setVente(vente);
		if (this.stock-vente.getQuantite()>0){
			this.setStock(this.stock-vente.getQuantite());
			this.fondsI.setValeur(this, this.fonds+vente.getPrix()*vente.getQuantite());
			this.stockI.setValeur(this, this.stock);
		}
		else {
			this.setStock(0);
			double stock_manquant = Math.abs(this.stock-vente.getQuantite()); 
			this.fondsI.setValeur(this, this.fonds+vente.getPrix()*vente.getQuantite()-vente.getPrix()*stock_manquant);
			this.stockI.setValeur(this, this.stock);			
		}

	}
	
	public void next(){}
	
	public String getNom(){
		return "";
	}
}
