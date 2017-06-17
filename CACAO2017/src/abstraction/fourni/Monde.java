package abstraction.fourni;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Classe modelisant "le monde" vu comme un regroupement d'acteurs, 
 * d'indicateurs et de journaux. 
 * 
 * Les acteurs/indicateurs/journaux que vous creerez devront etre
 * ajoutes a l'unique instance de cette classe designee par la 
 * variable LE_MONDE. Le bon endroit pour creer les instances 
 * d'acteurs et les ajouter au monde est dans la methode peupler(),
 * laquelle est appelee juste apres la creation de l'unique instance
 * du Monde.
 *
 * @author Romuald Debruyne
 */
public class Monde extends Observable {

	// Il n'y aura qu'une seule instance de la classe Monde.
	// Pour acceder facilement a cette unique
	// instance de Monde vous pouvez utiliser la reference 
	// statique LE_MONDE.
	public static Monde LE_MONDE;
	
	private int step;                         // Le numero d'etape en cours
	private ArrayList<Acteur> acteurs;        // La liste des acteurs
	private ArrayList<Indicateur> indicateurs;// La liste des indicateurs
	private ArrayList<Journal> journaux;      // La liste des journaux
	
	/**
	 * Initialise le monde de sorte que le numero d'etape soit 0, 
	 * et qu'il n'y ait pour l'heure aucun acteur/indicateur/journal.
	 */
	public Monde() {
		this.step=0;
		this.acteurs=new ArrayList<Acteur>();
		this.indicateurs=new ArrayList<Indicateur>();
		this.journaux=new ArrayList<Journal>();
	}
	/**
	 * Methode appelee juste apres la creation de l'unique 
	 * instance du Monde. C'est le bon endroit pour creer 
	 * vos instances d'acteurs et les ajouter au monde.
	 * 
	 * Dans la version fourniee deux acteurs sont crees et ajouter
	 * a titre d'exemple. Vous aurez bien evidemment a modifier 
	 * le corps de cette methode
	 */
	public void peupler() {
		// Methode a redefinir
	}
	/**
	 * @return Retourne le numero de l'etape en cours.
	 */
	public int getStep() {
		return this.step;
	}
	/**
	 * Ajoute l'acteur a au monde
	 * @param a l'acteur a ajouter
	 */
	public void ajouterActeur(Acteur a) {
		if (this.getActeur(a.getNom())==null) {
			this.acteurs.add(a);
		}
		else {
			System.out.println("addActeur : il existe deja un acteur de ce nom dans le monde...");
		}
	}
	/**
	 * Ajoute l'indicateur i au monde
	 * @param i l'idicateur a ajouter
	 */
	public void ajouterIndicateur(Indicateur i) {
		this.indicateurs.add(i);
	}
	/**
	 * Ajoute le journal j au monde
	 * @param j le journal a ajouter
	 */
	public void ajouterJournal(Journal j) {
		this.journaux.add(j);
	}
	/**
	 * @return Retourne la liste des acteurs du monde
	 */
	public ArrayList<Acteur> getActeurs() {
		return this.acteurs;
	}
	/**
	 * @param nom Le nom de l'acteur a retourner
	 * @return Si il existe dans le monde un acteur de nom nom, retourne cet acteur.
	 * Sinon, returne null. 
	 */
	public Acteur getActeur(String nom) {
		int i=0; 
		while (i<this.acteurs.size() && !this.acteurs.get(i).getNom().equals(nom)) {
			i++;
		}
		if (i<this.acteurs.size()) {
			return this.acteurs.get(i);
		}
		else {
			//System.out.println("getActeur(\""+nom+"\") retourne null car il n'y a pas d'acteur portant ce nom");
			return null;
		}
	}
	/** 
	 * @return Retourne la liste des indicateurs du monde
	 */
	public ArrayList<Indicateur> getIndicateurs() {
		return this.indicateurs;
	}
	/**
	 * @param nom le nom de l'indicateur a retourner
	 * @return Si il existe dans le Monde un indicateur de nom nom
	 * retourne cet indicateur. Sinon, affiche un message d'alerte 
	 * et retourne null.
	 */
	public Indicateur getIndicateur(String nom) {
		int i=0; 
		while (i<this.indicateurs.size() && !this.indicateurs.get(i).getNom().equals(nom)) {
			i++;
		}
		if (i<this.indicateurs.size()) {
			return this.indicateurs.get(i);
		}
		else {
			System.out.println("getIndicateur(\""+nom+"\") retourne null car il n'y a pas d'indicateur portant ce nom");
			return null;
		}
	}
	/**
	 * @return Retourne la liste des journaux du monde
	 */
	public ArrayList<Journal> getJournaux() {
		return this.journaux;
	}
	/**
	 * Methode appelee lorsque l'utilisateur clique sur le bouton NEXT de l'interface graphique.
	 * Cette methode incremente le numero d'etape puis appelle la methode next() de chaque acteur du monde.
	 */
	public void next() {
		this.step++;
		this.setChanged();
		this.notifyObservers("step");
		for (Acteur a : this.acteurs) {
			a.next();
		}
		for (Journal j : journaux) {
			j.notifyObservers("endNext");
		}
	}
}
