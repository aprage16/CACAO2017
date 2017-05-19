package abstraction.distributeur.amerique;

class Gestion {
	private double fonds;
	private double stock;
	
	public Gestion(double stock, double fonds){
		this.fonds=fonds;
		this.stock=stock;
	}
	
	
	
	public double getFonds() {
		return fonds;
	}



	public double getStock() {
		return stock;
	}




	public void setFonds(double fonds){
		this.fonds=fonds;
	}

	public void setStock(double stock){
		this.stock=stock;
	}
	

	
}
