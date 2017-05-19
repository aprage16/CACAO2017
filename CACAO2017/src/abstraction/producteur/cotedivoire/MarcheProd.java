package abstraction.producteur.cotedivoire;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.producteur.ameriquelatine.IProducteur;
import abstraction.transformateur.usa.interfacemarche.transformateur;


public class MarcheProd implements Acteur{ // Kevin et Adrien.
	
	private ArrayList<IProducteur> producteurs; //liste des producteurs
	private ArrayList<transformateur> transformateurs ; // liste des transformateurs
	public double coursMin = 2500 ; // prix min du cours du cacao par tonne
	public double coursMax = 3500 ; // prix max du cours du cacao par tonne
	
	private double quantiteAchetableGlob ;
	private double quantiteVoulueGlob ;
	private double coursActuel;
	private Indicateur qteprod1;
	private Indicateur qteprod2;
	private Indicateur qtetrans1;
	private Indicateur qtetrans2;

	
	public MarcheProd(ArrayList<IProducteur> producteurs,ArrayList<transformateur> transformateurs){
		this.producteurs=producteurs;
		this.transformateurs=transformateurs;
		this.quantiteAchetableGlob=0.0;
		this.quantiteVoulueGlob=0.0;
		this.coursActuel=3000;
	}
	public MarcheProd() {
		this.producteurs= new ArrayList<IProducteur>();  // a modifier par IProducteur une fois que les interfaces seront créees.
		this.transformateurs= new ArrayList<transformateur>(); // a modifier par ITransformateur une fois que les interfaces seront créees.
		this.quantiteAchetableGlob=0.0;
		this.quantiteVoulueGlob=0.0;
		this.coursActuel=3000;
		this.qteprod1= new Indicateur("prod1",this,0.0);
		this.qteprod2= new Indicateur("prod2",this,0.0);
		this.qtetrans1= new Indicateur("trans1",this,0.0);
		this.qtetrans2= new Indicateur("trans2",this,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.qteprod1);
		Monde.LE_MONDE.ajouterIndicateur(this.qteprod2);
		Monde.LE_MONDE.ajouterIndicateur(this.qtetrans1);
		Monde.LE_MONDE.ajouterIndicateur(this.qtetrans2);


	}
	
	public void addProducteur (IProducteur p) {
		this.producteurs.add(p);
	}
	public void addTransformateur (transformateur t) {
		this.transformateurs.add(t);
	}
	public double getQuantiteAchetable() {
		return this.quantiteAchetableGlob;
	}
	public double getQuantiteVoulue() {
		return this.quantiteVoulueGlob;
	}
	public double getCoursActuel() {
		return this.coursActuel;
	}
	public void setQuantiteAchetableGlob (double quantite) {
		this.quantiteAchetableGlob=quantite;
	}
	public void setQuantiteVoulueGlob (double quantite) {
		this.quantiteVoulueGlob=quantite;
	}
	public String getNom() {
		return "Marché du cacao";
	}
	public void Bourse () {
		// La bourse va faire évoluer le coursActuel en fonction de l'offre et de la demande bornée entre coursMin et coursMax
		if (this.quantiteAchetableGlob>=this.quantiteVoulueGlob){
				if (this.coursActuel>this.coursMin){
					this.coursActuel=0.99*this.coursActuel;
				}
		}
		else {
			if (this.coursActuel<this.coursMax){
				this.coursActuel=1.01*this.coursActuel;
			}
		}
	}
	public void next() { 
		setQuantiteAchetableGlob(0.0);
		setQuantiteVoulueGlob(0.0);
		Map<IProducteur, Integer> Prod = new HashMap<IProducteur,Integer>();
		Map<transformateur, Integer> Trans = new HashMap<transformateur, Integer>();
		//On creer une table de hashage qui correspond a un tableau IProd/ quantite 
		
		for (int i=0 ; i<this.producteurs.size(); i++) {
			Prod.put(this.producteurs.get(i), (int)this.producteurs.get(i).quantiteMiseEnvente());
		}
		for (int i=0 ; i<this.transformateurs.size(); i++) {
			Trans.put(this.transformateurs.get(i), (int)this.transformateurs.get(i).QteSouhaite());
		}
		// si on réecrit la meme ligne juste en changeant la valeur du integer ca modifie juste sa valeur donc 
		// c'est un moyen de garder en mémoire la valeur pour chaque prod et transformateurs
		int qttEnVente=0;
		for (IProducteur p : Prod.keySet()){
			qttEnVente += Prod.get(p);
		}
		setQuantiteAchetableGlob(qttEnVente);       //on définit la quantité globale en vente sur le marché.
		int qttSouhaitee=0; 
		for (transformateur t : Trans.keySet()){
			qttSouhaitee+= Trans.get(t);
		}
		setQuantiteVoulueGlob(qttSouhaitee);      // On définit la quantite globale voulue sur le marché.
		if (qttEnVente>=qttSouhaitee) {
			for (transformateur t : Trans.keySet()){
				t.notificationAchat(Trans.get(t),this.getCoursActuel());
			}
			for (IProducteur p : Prod.keySet()){
				p.notificationVente(Prod.get(p)-((Prod.get(p)/qttEnVente)*qttSouhaitee),this.getCoursActuel());
			}
		}
		else {
			for (transformateur t : Trans.keySet()){
				t.notificationAchat((Trans.get(t)/qttSouhaitee)*qttEnVente,this.getCoursActuel());
			}
			for (IProducteur p : Prod.keySet()){
				p.notificationVente(Prod.get(p),this.getCoursActuel());
			}
		}
		Bourse();
		this.qteprod1.setValeur(this, Prod.get(this.producteurs.get(0)));
		this.qteprod2.setValeur(this, Prod.get(this.producteurs.get(1)));
		this.qtetrans1.setValeur(this, Trans.get(this.transformateurs.get(0)));
		this.qtetrans2.setValeur(this, Trans.get(this.transformateurs.get(1)));

		
	}
}
