package abstraction.distributeur.europe;

public class Tresorerie{
	private double treso;
	
	public Tresorerie(double treso){
		this.treso = treso;
	}
	
	public double getTreso(){
		return this.treso;
	}
	
	public void variationTreso(double p){
		this.treso += p;
	}
	
	public void paiement(double p){
		if(p>=0){
			variationTreso(-p);
		}
	}
	
	public void encaissement(double e){
		if(e>=0){
			variationTreso(e);
		}
	}

	@Override
	public String toString() {
		return "Tresorerie [treso=" + treso + "]";
	}



	
	
	
		
}
