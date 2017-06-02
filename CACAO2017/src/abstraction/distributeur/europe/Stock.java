//Classe codée par Walid
package abstraction.distributeur.europe;
import java.util.ArrayList;
/*

class QuantiteChoco {
	private Chocolats typeChoco;
	public QuantiteChoco(Chocolats typeChoco, int quantite){
		this.typeChoco = typeChoco;
		this.quantite = quantite;
	}
	public Chocolats getTypeChoco() {
		return typeChoco;
	}
	public void setTypeChoco(Chocolats typeChoco) {
		this.typeChoco = typeChoco;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	private int quantite;
	
}

public class Stock {

	
	private ArrayList<QuantiteChoco> stock;
	
	public Stock(ArrayList<QuantiteChoco> stock){
		this.stock = stock;
	}

	public ArrayList<QuantiteChoco> getStock() {
		return stock;
	}

	public void setStock(ArrayList<QuantiteChoco> stock) {
		this.stock = stock;
	}
	
	public void addElement(Chocolats Choco, int nombre_de_choco){
			this.getStock().add(new QuantiteChoco(Choco, nombre_de_choco));
	}
	
	public void deleteElement(Chocolats Choco, int nombre_de_choco){
			this.getStock().remove(new QuantiteChoco(Choco, nombre_de_choco));
	}
	
	public int nbChoco(){
		int nb = 0;
		for (int i=0; i<this.getStock().size(); i++){
			nb = nb + this.getStock().get(i).getQuantite();
		}
		return nb;
	}
	
}
*/


//Classe V2 codée par Julien


public class Stock{
	
	private double[] stock;
	
	public Stock(){
		double[] tableau = new double[6];
		this.stock=tableau;
	}
	
	
	public void vieillirStock(){
		double[] var = new double[6];
		var=this.stock;
		
		for (int i=1;i<6;i++){
			this.stock[i]=var[i-1];
		}
		this.stock[0]=0;
	}
	public void ajoutStock(double s){
		
		this.stock[0]+=s;
	}
	public void retraitStock(double demande){
		
		double var = demande;
		int i=0;
		
		while (i<6 && var>0){
			
			if (stock[i]-var<=0){
				stock[i]=stock[i]-demande;
				var=0;
			}
			else{
				double var2=var;
				var2=var-stock[i];
				stock[i]=stock[i]-var;
				var2=var;
			}
			
		}
	}
	
	public double totalStock(){
		double total=0;
		
		for (int i=0;i<6;i++){
			total+=this.stock[i];
		}
		return total;
	}
	
}
