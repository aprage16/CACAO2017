package abstraction.distributeur.amerique;

import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Acteur;

public interface DistribClient extends Acteur{

	public double getPrixClient();//Le prix que va devoir payer le client à ce step
	public double getMisEnVente();//La quantité mise en vente
	public void notifVente(Vente vente);//notifie de ce qui a ete mis en vente
}
