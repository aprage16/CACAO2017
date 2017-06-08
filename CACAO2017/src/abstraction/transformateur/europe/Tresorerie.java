/* Classe gérant la trésorerie de notre acteur.


authors : Blois Philippe, 
          Charloux Jean, 
          Halzuet Guillaume,
		  Stourm Théo 
*/

package abstraction.transformateur.europe;

public class Tresorerie {

	private double compte;
	
	public static final int COUT_SALARIE = 9500000;// 5000 employés coutant 1900€ par next.
	public static final int COUT_TRANSFORMATION = 200; // Une tonne de chocolat nous coute 200€ à être transformée.
	
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
	private void setCompte(double compte){
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
	 * @Objectif: Retirer au compte la valeur @param.
	 * 
	 * @param montant
	 */
	public void debit(double montant){
		if(montant>=0){
			this.setCompte(this.getCompte()-montant);
		}
	}
	
	
	/**
	 * @objectif: Chiffrer le cout de transformation du cacao en chocolat,
	 * cette fonction prendra en compte le nombre de salarié, nos infrastructures,
	 * mais surtout la quantité de cacao achetée.
	 * 
	 * @param cacao
	 */
	public void cout_transformation(double cacao){
		debit(COUT_SALARIE);// Chaque next on paie nos salariés
		debit(COUT_TRANSFORMATION * cacao);
	}
	
	
	/** 
	 * Objectif: Afficher la valeur du compte, notamment dans le journal.
	 */
	public String toString(){
		return ("L'état du <b>solde</b> actuellement est de : <b><font color=\"blue\">"+compte+"</font></b> euros");
	}
}