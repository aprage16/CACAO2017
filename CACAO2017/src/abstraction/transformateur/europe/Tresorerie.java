/* Classe gérant la trésorerie de notre acteur.

authors : Blois Philippe, 
          Charloux Jean, 
          Halzuet Guillaume,
		  Stourm Théo 
*/

package abstraction.transformateur.europe;

public class Tresorerie {

	private double compte;
	
	
	/**
	 * @objectif: Constructeur de la classe
	 */
	public Tresorerie(double compte){
		this.compte=compte;
	}
	
	
	/**
	 * @objectif: Constructeur par chainage
	 */
	public Tresorerie(){
		this(0);
	}
	
	
	/** 
	 * @objectif: Getter du compte
	 */
	public double getCompte(){
		return this.compte;
	}
	
	
	/** 
	 * Objectif: Setter de notre compte
	 * 
	 * @param compte
	 */
	public void setCompte(double compte){
		this.compte=compte;
	}
	
	
	/** 
	 * Objectif: Ajouter au compte la valeur @param.
	 * 
	 * @param montant
	 */
	public void credit(double montant){
		if (montant >=0){
			this.setCompte(this.getCompte()+montant);
		}
	}

	
	/** 
	 * Objectif: Retirer au compte la valeur @param.
	 * 
	 * @param montant
	 */
	public void debit(double montant){
		if(montant>=0){
			this.setCompte(this.getCompte()-montant);
		}
	}
	
	
	/** 
	 * Objectif: Afficher la valeur du compte, notamment dans le journal.
	 */
	public String toString(){
		return ("L'état du <b>solde</b> actuellement est de : <b><font color=\"blue\">"+(int)compte+"</font></b> euros");
	}
}