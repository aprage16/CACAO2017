package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.List;

class Gestion {
	private double fonds;
	private List<Double> stock;
	
	public Gestion(List<Double> stock, double fonds){
		this.fonds=fonds;
		this.stock=stock;
	}
	
	
	
	public double getFonds() {
		return fonds;
	}



	public List<Double> getStock() {
		return stock;
	}




	public void setFonds(double fonds){
		this.fonds=fonds;
	}
	

	public void addStock(double newStock){
		if (this.getStock().size()<6){
			this.stock.add(newStock);
		}
		else{
			for (int k=0;k<this.getStock().size()-1;k++){
				this.stock.set(k, this.getStock().get(k+1));
				this.stock.set(this.getStock().size()-1, newStock);
			}
		}
	}
	
	public double sumStock(){
		double sum=0;
		for (int k=0;k<this.stock.size();k++){
			sum+=this.stock.get(k);
		}
		return sum;
	}
	
	public void vendreStock(double commande){
		if (commande>this.sumStock()){
			this.stock=new ArrayList<Double>();
		}
		while (commande>this.stock.get(0)){
			commande-=this.stock.get(0);
			this.stock.remove(0);
		}
		this.stock.set(0,this.stock.get(0)-commande);
		if (this.stock.get(0)<=0){
			this.stock.remove(0);
		}
		
	}
	

	
}
