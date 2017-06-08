package abstraction.producteur.cotedivoire;

import java.util.ArrayList;

// by fcadre 

public class Stock{
	
	private int stock; 
	private ArrayList<Double> stocks; 		// variable d'instance représentant le stock
	private static final int STOCK_MAX = 250000; //le stock physique maximal est de 250000 tonnes
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
	
	/**
	 * Methode pour ajouter la production au stock 
	 * ou enlever du stock la production vendue
	 * @param stock double positif ou negatif
	 */
	public void addStock(double stock){ 
		this.stock += stock; 
	}
	
	public void perssissabiliteStock(){ 
		int taille = this.stocks.size(); 
		double stockmaj = this.getStock()+stock;
		if(taille<18){ 
			if(stockmaj<STOCK_MAX && stockmaj>=0){
				this.stocks.add((double)stock); 
			}else{ 
				if(stockmaj<0){ 
					this.stocks.add(0.0); 
				}else{
					this.stocks.add((double)STOCK_MAX); 
				}
			}
		}else{
			for(int i=1; i<taille+1; i++){ 
				 
			}
		}
	}
}
