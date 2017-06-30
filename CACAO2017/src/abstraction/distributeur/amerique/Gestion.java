package abstraction.distributeur.amerique;

import java.util.ArrayList;
import java.util.List;

class Gestion {
	private double fonds;
	private List<Double> stock;
	
	public Gestion(List<Double> stock, double fonds){// la liste de stock est de 6 éléments
		this.fonds=fonds;
		this.stock=stock;
	}
	
	
	
	public double getFonds() {
		return fonds;
	}



	public List<Double> getStock() {
		return stock;
	}
	
	public void setStock(int index, double newStock){
		this.stock.set(index, newStock);
	}




	public void setFonds(double fonds){
		this.fonds=fonds;
	}
	
	
	public void addStock(double newStock){
		if (this.getStock().size()<DistributeurUS.tempsPerim){
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
		for (int k=0;k<DistributeurUS.tempsPerim;k++){
			if (commande>0){
				double courant=Math.min(commande, this.getStock().get(k));
				this.setStock(k, this.getStock().get(k)-courant);
				commande-=courant;
			}
		}
	}
	

	
}
