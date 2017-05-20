package abstraction.transformateur.usa;

public class StockProduitsFinis {

	/**
	 * @author Arthur Prager
	 */
	/* modif souchu*/
	private double chocolat;

	public StockProduitsFinis (double chocolat){
		this.chocolat=chocolat;
	}

	public double getStockChocolat(){
		return this.chocolat;
	}

	public void setStockChocolat(double chocolat){
		this.chocolat=chocolat;
	}

	public void rajouterChoco(double chocolat){
		this.chocolat+=chocolat;
	}

	public void enleverChoco(double chocolat){
		this.chocolat-=chocolat;
	}

}
