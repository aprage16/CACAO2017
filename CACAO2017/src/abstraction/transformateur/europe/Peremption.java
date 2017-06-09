package abstraction.transformateur.europe;

import java.util.ArrayList;

public class Peremption {
	
	private ArrayList<Double> Liste_Stock;
	public static final int TEMPS_PEREMPTION=10;
	
	
	public Peremption() {
		this.Liste_Stock= new ArrayList<Double>(10);
		for (int i=0; i<Liste_Stock.size(); i++) {
			this.Liste_Stock.add(i,0.0);
		}
	}
	public double getValeurIndice (int i) {
		return this.Liste_Stock.get(i);
	}
	
	public ArrayList<Double> getListe() {
		return this.Liste_Stock;
	}
	
	public void setListe (ArrayList<Double> L) {
		this.Liste_Stock=L;
	}
	
	public void setListeIndice (int i, double valeur) {
		this.Liste_Stock.set(i, valeur);
	}
	
	public void MiseAJourNext(Transformateur T) {
		for (int i=1; i<this.getListe().size(); i++) {
			this.setListeIndice(i, this.getValeurIndice(i-1));
		}
		this.setListeIndice(0, T.getStock().getStockCacao()/Transformateur.RATIO_CACAO_CHOCO);
	}
	
	public void RetraitVente(double QteVendue) {
		double a=QteVendue;
		int i=0;
		while (a>0) {
			a=a-this.Liste_Stock.get(i);
			this.setListeIndice(i, 0);
			i=i-1;
		}
		this.setListeIndice(i+1, -a);
	}
}









