package abstraction.producteur.cotedivoire;
import abstraction.producteur.cotedivoire.contrats.*;

import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.ameriquelatine.IProducteur;



// by fcadre, comments by antoineroson

public class ProductionCoteDIvoire implements Acteur, IProducteur, IContratProd{
	public static final int  PRODUCTIONMOYENNE = 1650000/26; //+ 42673; // Production moyenne de la cote d'ivoire en tonne + le reste du monde
	public static final double VARIATIONALEATOIREPRODUCTION = 0.05; 
	
	private int  production; //Liste des productions par périodes
	private Stock stock;          // Représente notre stock 
	private Treso tresorerie;     // Représente notre trésorerie
	private Indicateur productionIndicateur;	
	private Indicateur stockIndicateur;
	private Indicateur tresoIndicateur;
	private Indicateur vente;	
	private Journal journal;	//Introduction du Journal pour avoir une visibilité sur 
								//l'évolution des différents paramètres.
	private List<Devis> devisprod;
	
	//Cf marché
	public int hashCode() {
		return this.getNom().hashCode();
	}
	
	//Constructeur Production cote d'ivoire
	public ProductionCoteDIvoire(int prods, Stock stock, Treso treso){ 
		this.production = prods; 
		this.stock=stock;
		this.tresorerie = treso; 
	}
	//Constructeur sans paramètre
	public ProductionCoteDIvoire() {
		this.production = 0;
		this.stock= new Stock();
		this.tresorerie= new Treso();
		this.productionIndicateur=new Indicateur("6_PROD_COT_production",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur( this.productionIndicateur );
		this.stockIndicateur = new Indicateur("6_PROD_COT_stock",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.stockIndicateur);
		this.tresoIndicateur = new Indicateur("6_PROD_COT_treso",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.tresoIndicateur);
		this.vente= new Indicateur("6_PROD_COT_vente",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.vente);
		this.journal = new Journal("Journal de "+getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		
	}

	//Accesseur quantité produite
	public int getQuantiteProd(){ 
		return this.production;   
		// Récupére la dernière production sur la période
	}

	// Méthode varitation random de la production
	public void variationProduction(int periode){
		
		//Création d'une enveloppe (prod_min->prod_max) autour de la 
		//production moyenne annuelle divisée par le nombre de next en 1 année (26)
		
		double prod_min = PRODUCTIONMOYENNE - (double)(PRODUCTIONMOYENNE*VARIATIONALEATOIREPRODUCTION); 
		double prod_max = PRODUCTIONMOYENNE + (double)(PRODUCTIONMOYENNE*VARIATIONALEATOIREPRODUCTION);
		double prod = 0; 
		
		//Durant une année: 
		//Octobre à Février: Production = production moyenne + 50%
		//Mars: Production = production moyenne + 33%
		//Avril: Production = production moyenne - 33%
		//Mai à Juillet: Production = production moyenne - 50% 
		//Août: Production = production moyenne - 33%
		//Septembre: Production = production moyenne + 33%
		
		if (periode<26){ 
			if ((periode>=0 && periode<= 4)||periode>=19){
				prod_min += PRODUCTIONMOYENNE*0.5; 
				prod_max += PRODUCTIONMOYENNE*0.5; 
				prod = prod_min + (double)Math.random()*(prod_max - prod_min); // Production random entre prod_min et prod_max
			}else{ 
				if(periode==5||periode==18){ 
					prod_min += PRODUCTIONMOYENNE*(1/3); 
					prod_max += PRODUCTIONMOYENNE*(1/3); 
					prod = prod_min + (double)Math.random()*(prod_max - prod_min);
				}else{ 
					if(periode==6||periode==17){ 
						prod_min -= PRODUCTIONMOYENNE*(1/3); 
						prod_max -= PRODUCTIONMOYENNE*(1/3); 
						prod = prod_min + (double)Math.random()*(prod_max - prod_min);
					}else{
						prod_min -= PRODUCTIONMOYENNE*0.5; 
						prod_max -= PRODUCTIONMOYENNE*0.5; 
						prod = prod_min + (double)Math.random()*(prod_max - prod_min);
					}
				}
			}
		}else{ 
			int reste = periode%26; 
			if ((reste>=0 && reste<= 4)||reste>=19){
				prod_min += PRODUCTIONMOYENNE*0.5; 
				prod_max += PRODUCTIONMOYENNE*0.5; 
				prod = prod_min + (double)Math.random()*(prod_max - prod_min); // Production random entre prod_min et prod_max
			}else{ 
				if(reste==5||reste==18){ 
					prod_min += PRODUCTIONMOYENNE*(1/3); 
					prod_max += PRODUCTIONMOYENNE*(1/3); 
					prod = prod_min + (double)Math.random()*(prod_max - prod_min);
				}else{ 
					if(reste==6||reste==17){ 
						prod_min -= PRODUCTIONMOYENNE*(1/3); 
						prod_max -= PRODUCTIONMOYENNE*(1/3); 
						prod = prod_min + (double)Math.random()*(prod_max - prod_min);
					}else{
						prod_min -= PRODUCTIONMOYENNE*0.5; 
						prod_max -= PRODUCTIONMOYENNE*0.5; 
						prod = prod_min + (double)Math.random()*(prod_max - prod_min);
					}
				}
			}
		}
		this.production=(int)prod; // ajout dans la liste de production
		this.stock.addStock((int)prod);
		this.productionIndicateur.setValeur(this, (int)prod);
		this.journal.ajouter("Valeur de Production: "+this.production+" à l'étape du Monde: "+Monde.LE_MONDE.getStep());
	}
	
	//Accesseur Nom
	public String getNom() {
		return "Production Cote d'Ivoire"; 
	}

	public double quantiteMiseEnvente() {   // correspond a la quantité mise en vente//
		return this.stock.getStock(); 
	}


	public void notificationVente(double quantite, double coursActuel) {	// grace a la notification de vente on met a jour // 
		this.vente.setValeur(this,quantite);
		this.stock.addStock(-quantite);
		this.tresorerie.addBenef(quantite*coursActuel - this.stock.getStock()*Treso.COUTS);
		this.tresoIndicateur.setValeur(this,this.tresorerie.getCa());
	}
	
	//NEXT "Centre du programme -> Passage à la période suivante" 
	
	public void next() {
		this.variationProduction(Monde.LE_MONDE.getStep());
		this.stockIndicateur.setValeur(this,this.stock.getStock());
	}

	// GESTION des Contrats et Devis
	
	
	public void envoieDevis(List<Devis> l) {
		this.devisprod=l;
	}

	public void qttLivrablePrix() {
		for (int i=0; i<this.devisprod.size();i++){
			this.devisprod.get(i).setQttLivrable(10);
			this.devisprod.get(i).setPrix(3000);
		}
	}

	public void notifContrat() {
	}
}
