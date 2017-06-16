package abstraction.producteur.cotedivoire;
import java.util.ArrayList;
import abstraction.transformateur.usa.*;
import java.util.HashMap;
import java.util.Map;

import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.producteur.ameriquelatine.IProducteur;



public class MarcheProd implements Acteur{ // Kevin et Adrien.
	
	private ArrayList<IProducteur> producteurs; //liste des producteurs
	private ArrayList<ITransformateurMarcheDistrib> transformateurs ; // liste des transformateurs
	public static final int COURSMIN = 2500 ; // prix min du cours du cacao par tonne
	public static final int COURSMAX = 3500 ; // prix max du cours du cacao par tonne
	
	private double quantiteAchetableGlobale ;
	private double quantiteVoulueGlobale ;
	private double coursActuel;
	private Journal journal;

	
	public MarcheProd(ArrayList<IProducteur> producteurs,ArrayList<ITransformateurMarcheDistrib> transformateurs){
		this.producteurs=producteurs;
		this.transformateurs=transformateurs;
		this.quantiteAchetableGlobale=0.0;
		this.quantiteVoulueGlobale=0.0;
		this.coursActuel=3000;
	}
	public MarcheProd() {
		this.producteurs= new ArrayList<IProducteur>();  // a modifier par IProducteur une fois que les interfaces seront créees.
		this.transformateurs= new ArrayList<ITransformateurMarcheDistrib>(); // a modifier par ITransformateur une fois que les interfaces seront créees.
		this.quantiteAchetableGlobale=0.0;
		this.quantiteVoulueGlobale=0.0;
		this.coursActuel=3000;
		this.journal= new Journal ("Le journal du "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	public void addProducteur (IProducteur p) {
		this.producteurs.add(p);
	}
	public void addTransformateur (ITransformateurMarcheDistrib t) {
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
		this.journal.ajouter("<font color=\"maroon\">----------------------------</font>");
		setQuantiteAchetableGlobale(0.0);
		setQuantiteVoulueGlobale(0.0);
		Map<IProducteur, Integer> Prod = new HashMap<IProducteur,Integer>();
		Map<ITransformateurMarcheDistrib, Integer> Trans = new HashMap<ITransformateurMarcheDistrib, Integer>();
		//On creer une table de hashage qui correspond a un tableau IProd/ quantite 
		
		for (int i=0 ; i<this.producteurs.size(); i++) {
			Prod.put(this.producteurs.get(i), (int)this.producteurs.get(i).quantiteMiseEnvente());
			this.journal.ajouter("<font color=\"orange\">"+((Acteur)this.producteurs.get(i)).getNom()+"</font> METS EN VENTE "+(int)this.producteurs.get(i).quantiteMiseEnvente()+" A L ETAPE "+Monde.LE_MONDE.getStep());
		}
		for (int i=0 ; i<this.transformateurs.size(); i++) {
			Trans.put(this.transformateurs.get(i), (int)this.transformateurs.get(i).QteSouhaite());
			this.journal.ajouter("<font color=\"blue\">"+((Acteur)this.transformateurs.get(i)).getNom()+"</font> SOUHAITE "+(int)this.transformateurs.get(i).QteSouhaite()+" A L ETAPE "+Monde.LE_MONDE.getStep());
		}
		// si on réecrit la meme ligne juste en changeant la valeur du integer ca modifie juste sa valeur donc 
		// c'est un moyen de garder en mémoire la valeur pour chaque prod et transformateurs
		int qttEnVente=0;
		for (IProducteur p : Prod.keySet()){
			qttEnVente += Prod.get(p);
		}
		setQuantiteAchetableGlobale(qttEnVente);       //on définit la quantité globale en vente sur le marché.
		int qttSouhaitee=0; 
		for (ITransformateurMarcheDistrib t : Trans.keySet()){
			if (Trans.get(t)>=0){
				qttSouhaitee+= Trans.get(t); //ON ADDITIONNE QUE SI LES QUANTITES SONT POSITIVES evite de fausser les valeurs
				
			}
		}
		setQuantiteVoulueGlobale(qttSouhaitee);      // On définit la quantite globale voulue sur le marché.
		
		
		//Ventes en pourcentages pour les producteur, Achats réalisés = Quantité souaitée
		if (qttEnVente>=qttSouhaitee) {			
			for (ITransformateurMarcheDistrib t : Trans.keySet()){	//Achats
				if (Trans.get(t)>=0){			//gestion des demandes négatives
					t.notificationAchat(Trans.get(t),this.getCoursActuel());
					this.journal.ajouter("<font color=\"blue\">"+((Acteur)t).getNom()+"</font><font color=\"green\"> ACHETE qqttEnvente>qttSouhaitee </font>"+Trans.get(t)+" A L ETAPE "+Monde.LE_MONDE.getStep());
				}
				else {
					t.notificationAchat(0, this.getCoursActuel());
					this.journal.ajouter("<font color=\"blue\">"+((Acteur)t).getNom()+"</font><font color=\"red\"> DEMANDE UNE VALEUR NEGATIVE </font> A L ETAPE "+Monde.LE_MONDE.getStep());
					this.journal.ajouter("<font color=\"blue\">"+((Acteur)t).getNom()+"</font><font color=\"red\"> ACHETE </font>"+0+" A L ETAPE "+Monde.LE_MONDE.getStep());
				}
			}
			for (IProducteur p : Prod.keySet()){	//Ventes
				p.notificationVente((((double)Prod.get(p)/qttEnVente)*qttSouhaitee), this.getCoursActuel());
				//pourcentage de la quantite mise en vente fois la quentite vendue (quantite vendue = quantite soihaitee)
				this.journal.ajouter("<font color=\"orange\">"+((Acteur)p).getNom()+"</font> VENDS "+(((double)Prod.get(p)/qttEnVente)*qttSouhaitee)+" A L ETAPE "+Monde.LE_MONDE.getStep());
					}
		}
		
		
		//Quantité en vente inférieure à la quantité souhaitée --> Les producteurs vendent tout 
		else {
			for (ITransformateurMarcheDistrib t : Trans.keySet()){	//Achats
				if (Trans.get(t)>=0){		//Gestion des demandes négatives
					t.notificationAchat(((double)Trans.get(t)/qttSouhaitee)*qttEnVente,this.getCoursActuel());
					this.journal.ajouter("<font color=\"blue\">"+((Acteur)t).getNom()+"</font><font color=\"green\"> ACHETE qqtEnvente inf  a qttSouhaitee </font>"+((double)Trans.get(t)/qttSouhaitee)*qttEnVente+" A L ETAPE "+Monde.LE_MONDE.getStep());
					}
				else{
					t.notificationAchat(0,this.getCoursActuel());
					this.journal.ajouter("<font color=\"blue\">"+((Acteur)t).getNom()+"</font><font color=\"red\"> Achete 0" + " DEMANDE UNE VALEUR NEGATIVE </font>" +Monde.LE_MONDE.getStep());
				}
				
			}
			
			for (IProducteur p : Prod.keySet()){	//Ventes
				p.notificationVente(Prod.get(p),this.getCoursActuel());
				this.journal.ajouter("<font color=\"orange\">"+((Acteur)p).getNom()+"</font> VENDS "+Prod.get(p)+" A L ETAPE "+Monde.LE_MONDE.getStep());
			}
			
				
		
		}
		EvolutionDuCours();

		
	}
}
