package abstraction.producteur.cotedivoire.contrats;

import java.util.List;

public interface IContratTrans {
	// Enregistre les devis dans lesquels le transformateur est impliqué. 
	public void envoieDevis(Devis devis);
	// Modifie la variable d'instance QttVoulue de chaque devis enregistré grace a envoieDevis. 
	public void qttVoulue();
	// Modifie la variable d'instance qttFinale qui correspond a la quantité que vous souhaitez acheter à chaque next pdt 1an.
	public void finContrat();
}
