package abstraction.producteur.cotedivoire;
import abstraction.producteur.cotedivoire.contrats.*;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.ameriquelatine.IProducteur;



// by fcadre, comments by antoineroson

public class ProductionCoteDIvoire implements Acteur, IProducteur, IContratProd{
	public static final int  PRODUCTIONMOYENNE = 1650000/26; //+ 42673 Production moyenne de la cote d'ivoire en tonne + le reste du monde
	public static final double VARIATIONALEATOIREPRODUCTION = 0.05; 
	
	private int  production; //Liste des productions par périodes
	private Stock stock;          // Représente notre stock 
	private Treso tresorerie;     // Représente notre trésorerie
	private Indicateur productionIndicateur;	
	//private Indicateur stockIndicateur;
	//private Indicateur tresoIndicateur;
	private Indicateur vente;	
	private Journal journal;	//Introduction du Journal pour avoir une visibilité sur 
								//l'évolution des différents paramètres.
	private List<Devis> devisprod;
	public double coursactuel;
	
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
		this.stock= new Stock(this);
		this.tresorerie= new Treso(this);
		this.productionIndicateur=new Indicateur("6_PROD_COT_production",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur( this.productionIndicateur );
		this.vente= new Indicateur("6_PROD_COT_vente",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.vente);
		this.journal = new Journal(""+getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		this.devisprod= new ArrayList<Devis>();
		
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
		//System.out.println(this.stock.getStock()+"  avant ajout de production ");
		this.stock.addStock((int)prod);
		//System.out.println(this.stock.getStock()+"  apres ajout de production ");
		if(this.stock.getStock()>=Stock.STOCK_MAX){
			//System.out.println("ca n'arrive jamais...");
			//System.exit(0);
			this.stock.addStock((int)-prod);
			prod = prod/2;
			this.stock.addStock((int)prod);
			if(this.stock.getStock()>=Stock.STOCK_MAX){ 
				this.stock.addStock((int)-prod);
				prod = prod/2;
				this.stock.addStock((int)prod);
				if(this.stock.getStock()>=Stock.STOCK_MAX){ 
					this.stock.addStock((int)-prod);
					prod = prod/2; 
					this.stock.addStock((int)prod);
				}
			}
		}
		this.production=(int)prod; // ajout dans la liste de production
		this.tresorerie.addBenef(- prod*Stock.COUT_STOCK);	
		this.productionIndicateur.setValeur(this, (int)prod);
		this.journal.ajouter("Valeur de Production: "+this.production+" à l'étape du Monde: "+Monde.LE_MONDE.getStep());
	}
	
	//Accesseur Nom
	public String getNom() {
		return "Production Cote d'Ivoire"; 
	}

	public double quantiteMiseEnvente() {   // correspond a la quantité mise en vente//
		int s =0; 
		for (Devis d : this.devisprod){
			if (Monde.LE_MONDE.getStep()-d.getDebut()<26)
				s+=d.getQttFinale();
		}
		return this.stock.getStock()-s; 
	}


	public void notificationVente(double quantite, double coursActuel) {	// grace a la notification de vente on met a jour // 
		this.vente.setValeur(this,quantite);
		//System.out.println(this.stock.getStock()+"  avant retrait des ventes");
		this.stock.addStock(-quantite);
		//System.out.println(this.stock.getStock()+"  après retrait des ventes");
		this.tresorerie.addBenef(quantite*coursActuel);
		this.coursactuel=coursActuel;
		
	}
	
	//NEXT "Centre du programme -> Passage à la période suivante" 
	
	public void next() {
		this.tresorerie.addBenef(- this.stock.getStock()*Treso.COUTS_FIXES-Treso.COUTS_SALARIAUX);
		if(Monde.LE_MONDE.getStep()%26==1 && Monde.LE_MONDE.getStep()>1){ 
			this.tresorerie.addBenef(- Treso.COUTS_MAINTENANCE);
		}
		this.variationProduction(Monde.LE_MONDE.getStep());
		this.stock.perissabiliteStock();
		livraisonDesContrats();
	}

	// GESTION des Contrats et Devis
	
	
	public void envoieDevis(Devis d) {
		this.devisprod.add(d);
	}

	public void qttLivrablePrix() {
		int s =0;
		for (int i=0; i<this.devisprod.size();i++){
			if (Monde.LE_MONDE.getStep()-this.devisprod.get(i).getDebut()<26){
				this.devisprod.get(i).setPrix(0.9*this.coursactuel);
				s+=this.devisprod.get(i).getQttFinale();
				if(s>0.7*this.getQuantiteProd()){
					this.devisprod.get(i).setQttLivrable(0);
				}
				else {
					if (s+this.devisprod.get(i).getQttVoulue()<0.7*PRODUCTIONMOYENNE){
						this.devisprod.get(i).setQttLivrable(this.devisprod.get(i).getQttVoulue());
					}
					else {
						this.devisprod.get(i).setQttLivrable(0.7*PRODUCTIONMOYENNE-s);
					}
				}
			}
		}
	}

	public void notifContrat() {
	}
	
	public void livraisonDesContrats () {
		for (Devis d : this.devisprod){
			if (Monde.LE_MONDE.getStep()-d.getDebut()<26){
				this.tresorerie.addBenef(d.getPrix()*d.getQttFinale());
				this.stock.addStock(-d.getQttFinale());
				this.journal.ajouter("On livre "+d.getQttFinale()+" a "+d.getTrans()+" au prix de "+d.getPrix());
			}
		}
	}
}
