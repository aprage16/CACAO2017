package abstraction.transformateur.europe;

public interface ITransfoContrat {

	public void propositionInitiale(Devis devis); //annote le devis en fonction de quantité dispo et prix initial
	
	public void quantiteFournie();// annoter les devis en fournissant la quantite possible a fournir sur chaque devis
	
	public void acceptationInitiale();//accepte le prix fourni par les distri ou non
	
	public void notification(); //appelée une fois les distri avertis du contrat final
}
