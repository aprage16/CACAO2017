package abstraction.distributeur.amerique;

import java.util.List;

class Gestion {
	private double fonds;
	private /*List<Double>*/ double stock;
	
	public Gestion(/*List<Double> stock*/ double stock, double fonds){
		this.fonds=fonds;
		this.stock=stock;
	}
	
	
	
	public double getFonds() {
		return fonds;
	}



	public /*List<Double>*/ double getStock() {
		return stock;
	}




	public void setFonds(double fonds){
		this.fonds=fonds;
	}
	
	public void setStock(double stock){
		this.stock=stock;
	}

	/*public void addStock(double newStock){
		if (this.getStock().size()<6){
			this.stock.add(newStock);
		}
		else{
			for (int k=0;k<this.getStock().size()-1;k++){
				this.stock.set(k, this.getStock().get(k+1));
				this.stock.set(this.getStock().size()-1, newStock);
			}
		}
	}*/
	

	
}
