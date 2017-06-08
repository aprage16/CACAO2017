package abstraction.distributeur.amerique;

public interface DistribClient {

	public double getPrixClient();//Le prix que va devoir payer le client à ce step
	public double getMisEnVente();//La quantité mise en vente
	public void notifVente();//notifie de ce qui a ete mis en vente
}
