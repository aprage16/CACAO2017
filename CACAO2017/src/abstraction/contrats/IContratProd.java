package abstraction.contrats;

import java.util.List;

public interface IContratProd {
	// Enregistre les devis où le prod est impliqué. 
	public void envoieDevis(List<Devis> l);
	//Modifie les variables d'instances qttLivrable et prix de chaque devis enregistré dans envoieDevis en fonction de la qttVoulue.
	public void qttLivrablePrix();
	// Correspond a la notif de fin de contrat donc on récupère les infos dont on a besoin comme qttFinale et prix. 
	public void notifContrat();
}
