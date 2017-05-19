package abstraction.transformateur.europe;

public class Stock {

	private double stockCacao;
	private double stockChocolat;
	public static final int DATE_PEREMPTION = 5;
	public static final int STOCK_MAX_CACAO = 50000;
	public static final int STOCK_MAX_CHOCOLAT = 70000;
	
	
	public Stock(){
		this.stockCacao=0;
		this.stockChocolat=44000;
	}
	
	public double getStockCacao(){
		return this.stockCacao;
	}
	
	public double getStockChocolat(){
		return this.stockChocolat;
	}
	
	
	public void ajoutCacao(double cacao){
		this.stockCacao+=cacao;
	}
	
	public void retraitCacao(double cacao){
		this.stockCacao-=cacao;
	}
	
	public void ajoutChocolat(double chocolat){
		this.stockChocolat+=chocolat;
	}
	
	public void retraitChocolat(double chocolat){
		this.stockChocolat-=chocolat;
	}
	
	public String toString(){
		return "Le stock de cacao est de: "+this.getStockCacao()+", celui de chocolat est de: "+this.getStockChocolat();
	}
	
}
