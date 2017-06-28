
package abstraction.producteur.ameriquelatine;
//Modifié par lolotteisyoung 14/04/2017
//Modifié par lolotteisyoung 24/04/2017
//26/04 Adrien

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Tresorerie {
	private Acteur act;
	public final static int CHARGESPROD=5000000;// ($) charges fixes (hors coût de stock)
	public final static double COUTSTOCK=500; 
	private double coutSalarie=47400 ; //  prix d'un salarie (recolte) par tonne de cacao récolté et par an
	private Indicateur tresorerie; // argent en banque
	private Stock stock; 
	private Recolte recolte;
	

	public Tresorerie(Stock stock, Recolte recolte, Acteur a){
		this.act=a;
		this.stock = stock ;
		this.tresorerie = new Indicateur("4_PROD_AMER_solde", a,10000000);
		MondeV1.LE_MONDE.ajouterIndicateur(this.tresorerie);
		this.recolte=recolte;
	}
	public double getTresorerie(){
		 return this.tresorerie.getValeur() ;
	}
	public double cout(){
		return CHARGESPROD + stock.getStock()*COUTSTOCK+this.recolte.getQterecoltee()*coutSalarie/26; //le cout du salarie est par AN !! Ce pourquoi on doit diviser par 26 (nombre de next par an)
	}
	
	public void encaissement(double a){
		this.tresorerie.setValeur(act, this.tresorerie.getValeur()+a);
	}
	public void decaissement(double a){
		this.tresorerie.setValeur(act, this.tresorerie.getValeur()-a);
	}
	
	public void investissement(){
		double solde =this.getTresorerie();
		if(solde > 1000000000 ){
			this.decaissement(solde*0.2);
		}
	}
	
	public void licenciement(){
		double solde=this.getTresorerie();
		if ((solde<800000)&&(this.coutSalarie>=30000)){
			this.coutSalarie=this.coutSalarie-500;
		}
	}
	
	public void recrutement(){
		double solde=this.getTresorerie();
		if ((solde>1000000000)&&(this.coutSalarie<=65000)){
			this.coutSalarie=this.coutSalarie+500;
		}
	}
}
