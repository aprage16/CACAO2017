package abstraction.producteur.ameriquelatine;

import java.util.ArrayList;

import abstraction.distributeur.europe.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//modidifié et créé par swerne16
	// 07/04/2017 modifié par lolotteisyoung
	// 14/04/17 modifié par lolotteisyoung et AnaisBel
//26/04 Adrien
public class Stock {
	
// tout ce qui est produit est stocké, on gère les ventes à partir des stocks.
	private Acteur act;
	private Indicateur stock; 
	private ArrayList<Double> prod;

	public Stock(Acteur a) {
		this.act=a;
		this.stock= new Indicateur("4_PROD_AMER_stock", a ,1000);
		MondeV1.LE_MONDE.ajouterIndicateur(this.stock);
		this.prod=new ArrayList<Double>(19);
		for (int i=0; i<19; i++){
			prod.add(0.0);
		}
	}
	public void ajout(double a, int i){
		if(this.getStock()>=40000){
			this.prod.set(i, 0.0);
		}else{
			this.prod.set(i, a); 
		}
	}
	
	public void retrait(double a){ 
		int i=0;
		double b=a-this.prod.get(i);
		if (this.prod.get(i)<=a){
			this.prod.set(i, 0.0);
		}
		else {
			this.prod.set(i, this.prod.get(i)-a);
		}
		while (this.prod.get(i)==0){
			if (this.prod.get(i+1)<=b){
				i++;
				//Integer element = this.prod.get(i);
				//this.prod.remove(element);
				b=b-this.prod.get(i);
				this.prod.set(i,0.0);
			}
			else {
				i++;
				//Integer element = this.prod.get(i);
				//this.prod.remove(element);
				this.prod.set(i, this.prod.get(i)-b);
			}	
		}
	}
	
	public void miseAJourStock(int stock) {
		this.stock.setValeur(this.act, stock);
	}
	public ArrayList<Double> getProd(){
		return this.prod;
	}
	public void setProd(int i, double a){
		this.prod.set(i, a);
	}
	
	public int getStock(){
		int S=0;
		for (int i=0; i<this.prod.size(); i++){
			S+=this.prod.get(i);
		}
		this.stock.setValeur(this.act,S);
		return (int)(stock.getValeur());
	}
	
}


	
	
	

