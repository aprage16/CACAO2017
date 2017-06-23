package abstraction.transformateur.europe;

import java.util.ArrayList;

public class Peremption {
	
	private ArrayList<Double> listeStock;
	public static final int TEMPS_PEREMPTION=10;
	
	
	public Peremption() {
		listeStock=new ArrayList<Double>(0);
		for (int i=0; i<=TEMPS_PEREMPTION; i++) {
			this.listeStock.add((double) 0);
		}
	}
	public double getValeurIndice (int i) {
		return this.listeStock.get(i);
	}
	
	public ArrayList<Double> getListe() {
		return this.listeStock;
	}
	
	public void setListe (ArrayList<Double> L) {
		this.listeStock=L;
	}
	
	public void setListeIndice (int i, double valeur) {
		this.listeStock.set(i, valeur);
	}
	
	public void MiseAJourNext(Transformateur T) {
		if (this.getValeurIndice(9)>0){
			T.getStock().retraitChocolat(this.getValeurIndice(9));
		}
		for (int i=1; i<this.getListe().size(); i++) {
			this.setListeIndice(i, this.getValeurIndice(i-1));
		}
		this.setListeIndice(0, T.getStock().getStockCacao()/Transformateur.RATIO_CACAO_CHOCO);
	}
	
	public void RetraitVente(double QteVendue) {
		double a=QteVendue;
		int i=9;
		while (a>=0 && i>=0) {
			a-=this.getValeurIndice(i);
			this.setListeIndice(i, 0);
			i--;
		}
		this.setListeIndice(i+1, -a);
	}
}









