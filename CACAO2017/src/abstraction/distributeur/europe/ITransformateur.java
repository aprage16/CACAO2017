// Interface codée par Walid/Julien
package abstraction.distributeur.europe;
import abstraction.fourni.*;

public interface ITransformateur extends Acteur{
	public int getPrixMin();
	
	public void notif(Vente vente);
}
