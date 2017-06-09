package abstraction.transformateur.usa;

import java.util.ArrayList;
import abstraction.transformateur.usa.TransformateurUsa;

public class StockProduitsFinis {

	/**
	 * @author Arthur Prager
	 */
	/* modif souchu*/
	private ArrayList<Double> chocolat;

	public StockProduitsFinis (ArrayList<Double> chocolat){
		this.chocolat=chocolat;
		
	}
	
	public StockProduitsFinis(){
		ArrayList<Double> a=new ArrayList<Double>(6);
		for(int i=0;i<6;i++){
			a.add(40000.);
		}
		this.chocolat=a;
	}

	public double getStockChocolat(){
		double somme=0;
		for (int i=0;i<6;i++){
			somme+=this.chocolat.get(i);
		}
		return somme;
	}

	public void setStockChocolat(ArrayList<Double> chocolat){
		this.chocolat=chocolat;
	}
	
	public void miseAJour(){
		abstraction.transformateur.usa.TransformateurUsa.LE_JOURNAL_USA.ajouter("Perte de Chocolat"+this.chocolat.get(5));
		for (int i=5;i>0;i--){
			this.chocolat.set(i, this.chocolat.get(i-1));
		}
		this.chocolat.set(0, 0.0);
	}
	
	public void ajouterChocolat(double chocolat){
		TransformateurUsa.LE_JOURNAL_USA.ajouter("Production chocolat = "+chocolat);
		this.chocolat.set(0,chocolat);
	}

	public void enleverChoco(double chocolatAEnlever){
		for (int i=5;i>-1;i--){
			if (chocolatAEnlever>this.chocolat.get(i)){
				chocolatAEnlever-=this.chocolat.get(i);
				this.chocolat.set(i, 0.0);
			}
			else{
				this.chocolat.set(i, this.chocolat.get(i)-chocolatAEnlever);
				chocolatAEnlever=0;
			}
		}
	}
	
	public String toString(){
		String s="Stock de Chocolat :";
		for(int i=0;i<6;i++){
			s+=this.chocolat.get(i)+"---";
		}
		return s;
	}
	
	public static void main(String[] args) {
		StockProduitsFinis test = new StockProduitsFinis();
		System.out.println(test);
		test.miseAJour();
		System.out.println(test);
		test.miseAJour();
		System.out.println(test);
		test.ajouterChocolat(15000);
		System.out.println(test);
		test.miseAJour();
		System.out.println(test);
		System.out.println(test.getStockChocolat());
		test.enleverChoco(125000);
		System.out.println(test.getStockChocolat());
	}

}
