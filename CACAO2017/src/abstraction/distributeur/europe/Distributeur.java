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
	private Indicateur stockI;
	private Indicateur fondsI;
	private String nom;
	private double fonds;
	private Journal journal;
	
	public Distributeur(Vente vente, double stock, double qteDemandee){ // penser à redocoder en enlevant les arguments du constructeur
		this.derniereVente = vente;
		this.stock = stock;
	}
	
	public Distributeur(){
		this.nom = "Distributeur europe";
		this.derniereVente = new Vente(0.002,2);
		this.stock = 40000;
		this.fonds = 100;
 	    this.journal = new Journal("Journal de "+this.nom);
 	    Monde.LE_MONDE.ajouterJournal(this.journal);
		
		this.fondsI = new Indicateur("2_DISTR_EU_fonds", this, 100);
		this.stockI = new Indicateur("2_DISTR_EU_stocks", this, 40000);
		
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
		if(this.stock>300){
			System.out.println("prix final = " + (((prixTransfo*1.2>0.007)&&(prixTransfo*1.2 <= 0.008)) ? prixTransfo*1.2 : 0.007));
			return (((prixTransfo*1.2>0.007)&&(prixTransfo*1.2 <= 0.008)) ? prixTransfo*1.2 : 0.007);
			
		}
		else{
			System.out.println("prix final = " +  ( (prixTransfo*1.2>0.007)&&(prixTransfo*1.5 <= 0.008) ? prixTransfo*1.5 : 0.007));
			 return ( (prixTransfo*1.2>0.007)&&(prixTransfo*1.5 <= 0.008) ? prixTransfo*1.5 : 0.007);
		}
	}
	
	public void notif(Vente vente){
		this.setVente(vente);
		if (this.stock-vente.getQuantite()>0){
			this.setStock(this.stock-vente.getQuantite());
			this.fonds = this.fonds+vente.getPrix()*vente.getQuantite();
			this.fondsI.setValeur(this, this.fonds+vente.getPrix()*vente.getQuantite());
			this.stockI.setValeur(this, this.stock);
		}
		else {
			double stock_manquant = this.stock; 
			this.setStock(0);
			//-vente.getPrix()*stock_manquant
			this.fonds = this.fonds+vente.getPrix()*stock_manquant;
			this.fondsI.setValeur(this, this.fonds+vente.getPrix()*stock_manquant);
			this.stockI.setValeur(this, this.stock);			
		}
		
		journal.ajouter("Opération Réalisée"+vente.toString());

	}
	
	public void next(){}
	
	public String getNom(){
		return "";
	}
}
