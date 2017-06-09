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
	private ArrayList<Integer> prod;

	public Stock(Acteur a) {
		this.act=a;
		this.stock= new Indicateur("4_PROD_AMER_stock", a ,10000);
		MondeV1.LE_MONDE.ajouterIndicateur(this.stock);
		this.prod=new ArrayList<Integer>(19);
		for (int i=0; i<19; i++){
			prod.add(0);
		}
	}
	public void ajout(int a, int i){
		this.prod.set(i, a); 
	}
	
	public void retrait(int a){ 
		int i=0;
		int b=a-this.prod.get(i);
		if (this.prod.get(i)<=a){
			this.prod.set(i, 0);
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
				this.prod.set(i,0);
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
	public ArrayList<Integer> getProd(){
		return this.prod;
	}
	public void setProd(int i, int a){
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


	
	
	

