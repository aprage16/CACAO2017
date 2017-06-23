package abstraction.producteur.cotedivoire;

import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;


// by fcadre 

public class Stock{
	
	private Acteur a;
	public Indicateur stock; 
	private ArrayList<Double> stocks; 		// variable d'instance représentant le stock
	public static final double STOCK_MAX = 250000; //le stock physique maximal est de 250000 tonnes
	public static final double COUT_STOCK = 300; 
	/**
	 * Constructeur de Stock
	 * @param stock
	 */
	public Stock(Acteur a){ 
		this.a=a;
		this.stock=new Indicateur("6_PROD_COT_stock",a,0.0); 
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
		this.stocks= new ArrayList<Double>();  
		this.stocks.add(0.0);
	}

	/**
	 * Accesseur
	 * @return la valeur du stock
	 */
	public double getStock(){ 
		return this.stock.getValeur(); 
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
			this.stock.setValeur(this.a, this.stock.getValeur()+stock);
			if (stock<0){
				setStocks(stock);
			}
			else {
				this.getStocks().add(stock-this.getStocks().get(this.getStocks().size()-1));
			}
		}else{
			this.stock.setValeur(this.a,STOCK_MAX) ;
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
