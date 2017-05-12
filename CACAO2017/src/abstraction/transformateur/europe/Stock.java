package abstraction.transformateur.europe;

public class Stock {

	private double stockcacao;
	private double stockchocolat;
	private double stockMax;
	
	public Stock(){
		this.stockcacao=10;
		this.stockchocolat=10;
		this.stockMax=100;
	}
	
	public double getStockCacao(){
		return this.stockcacao;
	}
	
	public double getStockChocolat(){
		return this.stockchocolat;
	}
	
	public double getStockMax(){
		return this.stockMax;
	}
	
	public void setstockcacao(double cacao){
		this.stockcacao=this.getStockCacao()+cacao;
	}
	
	public void setstockchocolat(double choco){
		this.stockchocolat=this.getStockChocolat()+choco;
	}
	
	public String toString(){
		return "Le stock de cacao est de: "+this.getStockCacao()+", celui de chocolat est de: "+this.getStockChocolat();
	}
	
}
