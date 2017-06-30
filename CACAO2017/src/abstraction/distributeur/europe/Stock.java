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
	private double[] prix;
	
	public Stock(){
		double[] tableau = new double[6];
		double[] prix = new double[6];
		this.stock=tableau;
		this.prix = prix;
	}
	
	public void setPrix(double prix){
		this.prix[0] = prix;
	}

	public String getPrix(){
		return "" + this.prix[0] + this.prix[1] + this.prix[2] + this.prix[3];
	}
	
	public void vieillirStock(){

			
		for (int i=5;i>0;i--){
			this.stock[i]=this.stock[i-1];
			this.prix[i]=this.prix[i-1];

		}
		
		this.stock[0]=0;
		this.prix[0]=0;
	}
	public void ajoutStock(double s){
		
		this.stock[0]+=s;
	}
	public void retraitStock(double demande){
		
		int i=5;
		
		while (i>=0 && demande>0){
			
			if (stock[i]-demande>=0){
				stock[i]=stock[i]-demande;
				demande=0;
			}
			else{
				demande=demande-stock[i];
				stock[i]=0;
			}
			i--;
		}
	}
	public String afficherStock(){
		String res = "";
		for (int i=0; i<6; i++){
			res = res + " , " + this.stock[i];
		}
		return res;
	}

	public String afficherPrix(){
		String res = "";
		for (int i=0; i<6; i++){
			res = res + " , " + this.prix[i];
		}
		return res;
	}	
	
	public double getPrixVente(){
		double prixVente = 0;
		double total = 0;
		for (int i=0; i<6; i++){
			if (this.stock[i]!=0){
				total = total + this.stock[i];
			}
		}
		for (int i=0; i<6; i++){
			if (this.stock[i]!=0){
				prixVente = prixVente + this.prix[i]*(this.stock[i]/total);
			}
		}
		return prixVente;
	}
	
	public double totalStock(){
		double total=0;
		
		for (int i=0;i<6;i++){
			total+=this.stock[i];
		}
		return total;
	}
	
}
