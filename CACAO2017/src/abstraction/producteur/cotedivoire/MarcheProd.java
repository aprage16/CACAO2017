package abstraction.producteur.cotedivoire;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.ameriquelatine.IProducteur;
import abstraction.transformateur.usa.interfacemarche.transformateur;


public class MarcheProd implements Acteur{ // Kevin et Adrien.
	
	private ArrayList<IProducteur> producteurs; //liste des producteurs
	private ArrayList<transformateur> transformateurs ; // liste des transformateurs
	public static final int COURSMIN = 2500 ; // prix min du cours du cacao par tonne
	public static final int COURSMAX = 3500 ; // prix max du cours du cacao par tonne
	
	private double quantiteAchetableGlobale ;
	private double quantiteVoulueGlobale ;
	private double coursActuel;
	private Journal journal;

	
	public MarcheProd(ArrayList<IProducteur> producteurs,ArrayList<transformateur> transformateurs){
		this.producteurs=producteurs;
		this.transformateurs=transformateurs;
		this.quantiteAchetableGlobale=0.0;
		this.quantiteVoulueGlobale=0.0;
		this.coursActuel=3000;
	}
	public MarcheProd() {
		this.producteurs= new ArrayList<IProducteur>();  // a modifier par IProducteur une fois que les interfaces seront créees.
		this.transformateurs= new ArrayList<transformateur>(); // a modifier par ITransformateur une fois que les interfaces seront créees.
		this.quantiteAchetableGlobale=0.0;
		this.quantiteVoulueGlobale=0.0;
		this.coursActuel=3000;
		this.journal= new Journal ("Le journal du "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	public void addProducteur (IProducteur p) {
		this.producteurs.add(p);
	}
	public void addTransformateur (transformateur t) {
		this.transformateurs.add(t);
	}
	public double getQuantiteAchetable() {
		return this.quantiteAchetableGlobale;
	}
	public double getQuantiteVoulue() {
		return this.quantiteVoulueGlobale;
	}
	public double getCoursActuel() {
		return this.coursActuel;
	}
	public void setQuantiteAchetableGlobale (double quantite) {
		this.quantiteAchetableGlobale=quantite;
	}
	public void setQuantiteVoulueGlobale (double quantite) {
		this.quantiteVoulueGlobale=quantite;
	}
	public String getNom() {
		return "Marché entre producteur et transformateur";
	}
	public void EvolutionDuCours () {
		// La bourse va faire évoluer le coursActuel en fonction de l'offre et de la demande bornée entre coursMin et coursMax
		if (this.quantiteAchetableGlobale>=this.quantiteVoulueGlobale){
				if (this.coursActuel>COURSMIN){
					this.coursActuel=0.99*this.coursActuel;
				}
		}
		else {
			if (this.coursActuel<COURSMAX){
				this.coursActuel=1.01*this.coursActuel;
			}
		}
	}
	public void next() { 
		setQuantiteAchetableGlobale(0.0);
		setQuantiteVoulueGlobale(0.0);
		Map<IProducteur, Integer> Prod = new HashMap<IProducteur,Integer>();
		Map<transformateur, Integer> Trans = new HashMap<transformateur, Integer>();
		//On creer une table de hashage qui correspond a un tableau IProd/ quantite 
		
		for (int i=0 ; i<this.producteurs.size(); i++) {
			Prod.put(this.producteurs.get(i), (int)this.producteurs.get(i).quantiteMiseEnvente());
			this.journal.ajouter(((Acteur)this.producteurs.get(i)).getNom()+" METS EN VENTE "+(int)this.producteurs.get(i).quantiteMiseEnvente()+" A L ETAPE "+Monde.LE_MONDE.getStep());
		}
		for (int i=0 ; i<this.transformateurs.size(); i++) {
			Trans.put(this.transformateurs.get(i), (int)this.transformateurs.get(i).QteSouhaite());
			this.journal.ajouter(((Acteur)this.transformateurs.get(i)).getNom()+" SOUHAITE "+(int)this.transformateurs.get(i).QteSouhaite()+" A L ETAPE "+Monde.LE_MONDE.getStep());
		}
		// si on réecrit la meme ligne juste en changeant la valeur du integer ca modifie juste sa valeur donc 
		// c'est un moyen de garder en mémoire la valeur pour chaque prod et transformateurs
		int qttEnVente=0;
		for (IProducteur p : Prod.keySet()){
			qttEnVente += Prod.get(p);
		}
		setQuantiteAchetableGlobale(qttEnVente);       //on définit la quantité globale en vente sur le marché.
		int qttSouhaitee=0; 
		for (transformateur t : Trans.keySet()){
			qttSouhaitee+= Trans.get(t);
		}
		setQuantiteVoulueGlobale(qttSouhaitee);      // On définit la quantite globale voulue sur le marché.
		if (qttEnVente>=qttSouhaitee) {
			for (transformateur t : Trans.keySet()){
				if (Trans.get(t)>=0){	
					t.notificationAchat(Trans.get(t),this.getCoursActuel());
					this.journal.ajouter(((Acteur)t).getNom()+" ACHETE "+Trans.get(t)+" A L ETAPE "+Monde.LE_MONDE.getStep());
					for (IProducteur p : Prod.keySet()){
						p.notificationVente((((double)Prod.get(p)/qttEnVente)*Trans.get(t)), this.getCoursActuel());
						this.journal.ajouter(((Acteur)p).getNom()+" VENDS "+(((double)Prod.get(p)/qttEnVente)*Trans.get(t))+" A L ETAPE "+Monde.LE_MONDE.getStep());
					}
				}
				else {
					t.notificationAchat(0, this.getCoursActuel());
					this.journal.ajouter(((Acteur)t).getNom()+" DEMANDE UNE VALEUR NEGATIVE A L ETAPE "+Monde.LE_MONDE.getStep());
					this.journal.ajouter(((Acteur)t).getNom()+" ACHETE "+0+" A L ETAPE "+Monde.LE_MONDE.getStep());
				}
			}
		}
		else {
			for (IProducteur p : Prod.keySet()){
				p.notificationVente(Prod.get(p),this.getCoursActuel());
				this.journal.ajouter(((Acteur)p).getNom()+" VENDS "+Prod.get(p)+" A L ETAPE "+Monde.LE_MONDE.getStep());
				for (transformateur t : Trans.keySet()){
					t.notificationAchat(((double)Trans.get(t)/qttSouhaitee)*qttEnVente,this.getCoursActuel());
					this.journal.ajouter(((Acteur)t).getNom()+" ACHETE "+((double)Trans.get(t)/qttSouhaitee)*qttEnVente+" A L ETAPE "+Monde.LE_MONDE.getStep());
				}
				
			}
		}
		EvolutionDuCours();

		
	}
}
