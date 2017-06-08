package abstraction.producteur.cotedivoire;

import java.util.ArrayList;

// by fcadre 

public class Stock{
	
	private double stock; 
	private ArrayList<Double> stocks; 		// variable d'instance représentant le stock
	public static final double STOCK_MAX = 250000; //le stock physique maximal est de 250000 tonnes
	/**
	 * Constructeur de Stock
	 * @param stock
	 */
	public Stock(){ 
		this.stock=0; 
		this.stocks= new ArrayList<Double>();  
	}

	/**
	 * Accesseur
	 * @return la valeur du stock
	 */
	public double getStock(){ 
		return this.stock; 
	}
	
	public ArrayList<Double> getStocks(){ 
		return this.stocks; 
	}
	
	/**
	 * Methode pour ajouter la production au stock 
	 * ou enlever du stock la production vendue
	 * @param stock double positif ou negatif
	 */
	public void addStock(double stock){ 
		if(this.getStock()+stock<STOCK_MAX){
			this.stock += stock;  
		}else{
			this.stock = STOCK_MAX;  
		}
	}
	
	public void perissabiliteStock(double stock){ 
		int taille = this.stocks.size();
		double stockparstep = stock - this.getStocks().get(taille);
		if(taille<18){  
			this.addStock(stockparstep); 
		}else{
			this.addStock(stockparstep);
			for(int i=taille+1; i>1; i++){   
				this.stocks.add(i, this.getStocks().get(i-1));
				this.stocks.remove(i-1); 
			}
		}
	}
}
