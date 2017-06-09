package abstraction.producteur.cotedivoire;

import java.util.ArrayList;


// by fcadre 

public class Stock{
	
	private double stock; 
	private ArrayList<Double> stocks; 		// variable d'instance représentant le stock
	public static final double STOCK_MAX = 250000; //le stock physique maximal est de 250000 tonnes
	public static final double COUT_STOCK = 300; 
	/**
	 * Constructeur de Stock
	 * @param stock
	 */
	public Stock(){ 
		this.stock=0; 
		this.stocks= new ArrayList<Double>();  
		this.stocks.add(0.0);
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
	public void setStocks(double d){
		double val = this.getStocks().get(this.getStocks().size()-1);
		this.getStocks().remove(this.getStocks().size()-1);
		this.getStocks().add(d+val);
		
	}

	public void addStock(double stock){ 
		if(this.getStock()+stock<STOCK_MAX){
			this.stock += stock;
			if (stock<0){
				setStocks(stock);
			}
			else {
				this.getStocks().add(stock-this.getStocks().get(this.getStocks().size()-1));
			}
		}else{
			this.stock = STOCK_MAX;  
			this.getStocks().add(STOCK_MAX-this.getStock());
		}
		
	}
	
	public void perissabiliteStock(){ 
		int taille = this.getStocks().size();
		if(taille>18){
			this.getStocks().remove(0);
		}
	}
}
