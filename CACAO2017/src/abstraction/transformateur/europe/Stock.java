
/* Classe gérant le stock de chocolat et de cacao
 * 
authors : Blois Philippe, 
          Charloux Jean, 
          Halzuet Guillaume,
		  Stourm Théo 
*/

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
	
	
	/**
	 * @objectif: Constructeur de la classe
	 * 
	 * @param stockCacao
	 * @param stockChocolat
	 */
	public Stock(double stockCacao, double stockChocolat){ 
		this.stockCacao=stockCacao;
		this.stockChocolat=stockChocolat;
	}
	
	
	/**
	 * @objectif: Constructeur par chainage
	 */
	public Stock(){
		this(0,44000);
		this.journal=new Journal("Journal du Stock des Transformateurs d'Europe");
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	
	/**
	 * @objectif: Constructeur par recopie
	 * 
	 * @param s
	 */
	public Stock(Stock s){ 
		this.stockCacao=s.stockCacao;
		this.stockChocolat=s.stockChocolat;
	}
	
	
	/**
	 * @return le stock de cacao
	 */
	public double getStockCacao(){
		return this.stockCacao;
	}
	

	/**
	 * @return le stock de chocolat
	 */
	public double getStockChocolat(){
		return this.stockChocolat;
	}
	
	
	/**
	 * @objectif: Setter de cacao
	 * 
	 * @param cacao
	 */
	public void setStockCacao(double cacao){
		this.stockCacao=cacao;
	}
	
	
	/**
	 * @objectif: Setter de chocolat
	 * 
	 * @param chocolat
	 */
	public void setStockChocolat(double chocolat){
		this.stockChocolat=chocolat;
	}
	
	
	/**
	 * @objectif: Ajouter du cacao dans les stocks
	 * 
	 * @param cacao
	 */
	public void ajoutCacao(double cacao){
		this.stockCacao+=cacao;
	}
	
	
	/**
	 * @objectif: Retirer du cacao dans les stocks
	 * 
	 * @param cacao
	 */
	public void retraitCacao(double cacao){
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
	
	
	/**
	 * @objectif: Ajouter du chocolat dans les stocks
	 * 
	 * @param chocolat
	 */
	public void ajoutChocolat(double chocolat){
		this.stockChocolat+=chocolat;
	}
	
	
	/**
	 * @objectif: Retirer du chocolat dans les stocks
	 * 
	 * @param chocolat
	 */
	public void retraitChocolat(double chocolat){
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
	
	
	/**
	 * @return un string affichant le stock de cacao et de chocolat
	 */
	public String toString(){
		return "Le stock de <b>cacao</b> est de: <b><font color=\"purple\">"+this.getStockCacao()+"</font></b> , celui de <b>chocolat</b> est de: <b><font color=\"purple\">"+this.getStockChocolat()+"</font></b>";
	}
	
}
