package abstraction.fourni;

import java.util.ArrayList;
import java.util.Observable;
/**
 * Classe modelisant un journal. 
 * Un journal sert a garder trace de messages qu'on y depose.
 * Cela peut etre particulierement utilse en phase de debogage : 
 * plutot que d'afficher des messages sur la console pour tracer
 * le deroulement de l'execution, mieux vaut ajouter des chaines
 * dans un journal. On peut ainsi avoir la trace de differents
 * acteurs sans que les messages s'entrelacent sur la console.
 * 
 * Vous pourriez etre amenes a creer des instances de cette classe.
 *
 * @author Romuald Debruyne
 */
public class Journal  extends Observable{
	private String nom;            // le nom du journal
	private ArrayList<String>liste;// la liste des messages deposes sur le journal

	/**
	 * Initialise le journal avec le nom nom et une liste de messages vide   
	 * @param nom le nom du journal
	 */
	public Journal(String nom) {
		this.nom = nom;
		this.liste=new ArrayList<String>();
	}
	/**
	 * @return Retourne le nom du journal
	 */
	public String getNom() {
		return this.nom;
	}
	/**
	 * @return Retourne le nombre de messages sur le journal
	 */
	public int getTaille() {
		return this.liste.size();
	}
	/**
	 * Ajoute le message s sur le journal
	 * @param s le message a ajouter sur le journal
	 */
	public void ajouter(String s) {
		this.liste.add(s);
		this.setChanged();
	}
	/**
	 * Si i>=0 et i<this.getTaille(), retourne le message d'index i.
	 * Sinon retourne null.
	 * @param i
	 * @return
	 */
	public String get(int i) {
		if (i>=0 && i<this.getTaille()) {
			return this.liste.get(i);
		}
		else {
			//System.out.println("Appel de get("+i+") sur un journal de taille "+this.getTaille());
			return null;
		}
	}
	/**
	 * Retourne une chaine de caracteres agregeant les messages 
	 * du journal separes par un retour a la ligne
	 */
	public String toString() {
		String s="";
		for (int i=0; i<this.getTaille(); i++) {
			s+=this.get(i).toString()+"\n";
		}
		return s;
	}
	/**
	 * @return Retourne une chaine de caracteres agregeant les messages
	 * du journal avec des balises html pour le passage a la ligne
	 * entre chaque message
	 * Cette methode est utilisee pour l'affichage
	 */
	public String toHtml() {
		String s="<html>...<br/>";
		for (int i=Math.max(0,this.getTaille()-200); i<this.getTaille(); i++) {
			s+=this.get(i).toString()+"<br/>";
		}
		return s+"</html>";
	}
}
