//Interface codée par Walid/Julien

package abstraction.distributeur.europe;
import abstraction.fourni.*;

public interface IDistributeur extends Acteur{
	public double getPrixMax();
	public void notif(Vente vente);
	public Indicateur getIndicateurStock();
	public Indicateur getSolde();
}
