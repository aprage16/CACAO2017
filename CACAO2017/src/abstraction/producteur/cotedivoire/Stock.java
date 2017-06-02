package abstraction.producteur.cotedivoire;


// by fcadre 

public class Stock{
	
	private double stock; 		// variable d'instance représentant le stock
	
	/**
	 * Constructeur de Stock
	 * @param stock
	 */
	public Stock(double stock){ 
		this.stock= stock;  
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
}
