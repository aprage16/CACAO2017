package abstraction.transformateur.europe;

import abstraction.fourni.Journal;

public class Stock {

	private double stockCacao;
	private double stockChocolat;
	public static final int STOCK_MAX_CACAO = 50000;
	public static final int STOCK_MAX_CHOCOLAT = 70000;
	private Journal journal;
	
	
	public Stock(){
		this.stockCacao=0;
		this.stockChocolat=44000;
		this.journal=new Journal("Journal Transformateur Europe Stock");
	}
	
	public double getStockCacao(){
		return this.stockCacao;
	}
	
	public double getStockChocolat(){
		return this.stockChocolat;
	}
	
	
	public void ajoutCacao(double cacao){
		if (cacao>=0) {
			if (this.getStockCacao()<=0) {
				this.journal.ajouter("Stock de cacao négatif !!");
			} else {
				this.stockCacao+=cacao;
			}
		} else {
			this.journal.ajouter("Tu ajoutes du négatif !!");
		}
	}
	
	public void retraitCacao(double cacao){
		if (cacao>=0) {
			if (this.getStockCacao()-cacao<=0) {
				this.journal.ajouter("T'as pas assez de cacao pour faire ça !!");
			} else {
				this.stockCacao-=cacao;
			}
		} else {
			this.journal.ajouter("Tu soustrais du négatif !!");
		}
	}
	
	
	public void ajoutChocolat(double chocolat){
		if (chocolat>0) {
			if (this.getStockChocolat()<0) {
				this.journal.ajouter("Stock de chocolat négatif !!");
			} else {
				this.stockChocolat+=chocolat;
			}
		} else {
			this.journal.ajouter("Tu ajoutes du négatif !!");
		}
	}
	
	
	public void retraitChocolat(double chocolat){
		if (chocolat>0) {
			if (this.getStockChocolat()-chocolat<0) {
				this.journal.ajouter("T'as pas assez de chocolat pour faire ça !!");
			} else {
				this.stockChocolat-=chocolat;
			}
		} else {
			this.journal.ajouter("Tu ajoutes du négatif !!");
		}
	}
	
	
	public String toString(){
		return "Le stock de cacao est de: "+this.getStockCacao()+", celui de chocolat est de: "+this.getStockChocolat();
	}
	
}
