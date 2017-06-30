package abstraction.transformateur.europe;

public interface IDistriContrat {

	public void receptionDevis(Devis devis); //mémoriser les devis fourni par l'acteur intermédiaire
	
	public void demandeQuantite(); //appelée une fois les devis initialiser par transfo avec quantite et prix --> indiquer quantite souhaitée sur chaque devis 
	
	public void contreProposition(); //appelée une fois la quantite dispo fournie --> autre prix 
	
	public void acceptationFinale(); //les trasnfo acceptent le nouveau prix ou non, sinon possibilité d'accepter avec le prix initial
}
