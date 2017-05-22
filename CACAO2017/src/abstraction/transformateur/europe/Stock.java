
//** Classe gérant le Stock (stockCacao, stockChocolat)
//** authors : Blois Philippe, 
//**           Charloux Jean, 
//**           Halzuet Guillaume,
//**		   Stourm Théo ***///

package abstraction.transformateur.europe;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Stock {

	private double stockCacao;
	private double stockChocolat;
	public static final int DATE_PEREMPTION = 5;
	public static final int STOCK_MAX_CACAO = 50000;
	public static final int STOCK_MAX_CHOCOLAT = 70000;
	public static final int STOCK_MIN=5000;
	private Journal journal;
	
	public Stock(){
		this(0,44000);
		this.journal=new Journal("Journal du Stock des Transformateurs d'Europe");
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	public Stock(double stockCacao, double stockChocolat){
		this.stockCacao=stockCacao;
		this.stockChocolat=stockChocolat;
	}
	
	public double getStockCacao(){
		return this.stockCacao;
	}
	
	public double getStockChocolat(){
		return this.stockChocolat;
	}
	
	public void ajoutCacao(double cacao){ //ajout cacao au stock
		this.stockCacao+=cacao;
	}
	
	public void retraitCacao(double cacao){ //retrait cacao
		if (cacao>=0) {
			if (this.getStockCacao()-cacao<=0) {
				this.journal.ajouter("T'as pas assez de cacao pour faire ça !!");
			} else {
				this.stockCacao-=cacao;
			}
		} else {
			this.journal.ajouter("Tu soustrais du négatif !!");
		}
	}
	
	public void ajoutChocolat(double chocolat){ //ajout chocolat
		this.stockChocolat+=chocolat;
	}
	
	public void retraitChocolat(double chocolat){ //retrait chocolat
		if (chocolat>=0) {
			if (this.getStockChocolat()-chocolat<0) {
				this.journal.ajouter("T'as pas assez de chocolat pour faire ça !!");
			} else {
				this.stockChocolat-=chocolat;
			}
		} else {
			this.journal.ajouter("Tu ajoutes du négatif !!");
		}
	}
	
	public String toString(){
		return "Le stock de <b>cacao</b> est de: <b><font color=\"purple\">"+this.getStockCacao()+"</font></b> , celui de <b>chocolat</b> est de: <b><font color=\"purple\">"+this.getStockChocolat()+"</font></b>";
	}
	
}
