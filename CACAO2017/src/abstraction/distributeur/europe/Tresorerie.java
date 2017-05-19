package abstraction.distributeur.europe;

public class Tresorerie {
	private int treso;
	
	public Tresorerie(int treso){
		this.treso = treso;
	}
	
	public int getTreso(){
		return this.treso;
	}
	
	public void setTreso(int p){
		this.treso += p;
	}
	
	public void paiement(int p){
		setTreso(p);
	}
	
	public void encaissement(int e){
		setTreso(-e);
	}

}
