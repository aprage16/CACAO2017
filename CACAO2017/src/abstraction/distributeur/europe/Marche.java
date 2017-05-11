package abstraction.distributeur.europe;

import java.util.ArrayList;
import abstraction.transformateur.usa.interfacemarche.*;
import abstraction.fourni.Acteur;

public class Marche implements Acteur{
	
	private ArrayList<IDistributeur> distributeur;
	private ArrayList<transformateur> transformateur;
	
	double prixVenteMin=20;
	double prixVenteMax=40;
	
	double prixAchatMin=20;
	double prixAchatMax=40;
	
	double unite=1000;
	
	public Marche(ArrayList<IDistributeur> distributeur, ArrayList<transformateur> transformateur){
		this.distributeur=distributeur;
		this.transformateur=transformateur;
	}
	
	public Marche(){
		this.distributeur = new ArrayList<IDistributeur>();
		this.transformateur = new ArrayList<transformateur>();
	}
	
	public ArrayList<IDistributeur> getDistrib(){
		return this.distributeur;
	}
	
	public ArrayList<transformateur> getTransfo(){
		return this.transformateur;
	}
	public boolean testFourchettePrix(boolean[] test_prix){
		for (int i=0; i<test_prix.length; i++){
			if ((test_prix[i]) == true){
				return true;
			}
		}
		return false;
	}
	
	public int indiceMaximum(){
		int indice_max = 0;
		for (int i=1; i<this.distributeur.size(); i++){
			indice_max = ((this.distributeur.get(i).getPrixMax()>this.distributeur.get(indice_max).getPrixMax()) ? i : indice_max);
		}
		return indice_max;
	}
	
	public int indiceMinimum(){
		int indice_min = 0;
		for (int i=1; i<this.distributeur.size(); i++){
			indice_min = ((this.distributeur.get(i).getPrixMax()<this.distributeur.get(indice_min).getPrixMax()) ? i : indice_min);
		}
		return indice_min;		
	}
	
	public void Echanges(){
		
	
       // Définition des tests vérifiant si la boucle doit s'arrêter ou non 
		
		
		// On vérifie si les prix sont dans la fourchette autorisée
		boolean[] test_t = new boolean[this.transformateur.size()];
		boolean[] test_d = new boolean[this.transformateur.size()];
		for (int i=0; i<this.transformateur.size(); i++){
			test_t[i] = (transformateur.get(i).getprixMin()>=prixVenteMin) &&	(transformateur.get(i).getprixMin()<=prixVenteMax);
			test_d[i] = (distributeur.get(i).getPrixMax()>=prixAchatMin) &&  (distributeur.get(i).getPrixMax()<=prixAchatMax);
		}

		
		// On vérifie qu'une transaction soit possible
		
		
		int prioDistri=0;
		int prioTransfo=0;
		
		prioDistri = this.indiceMaximum();
		prioTransfo = this.indiceMinimum();
					
		 boolean testPrix = (distributeur.get(prioDistri).getPrixMax()<transformateur.get(prioTransfo).getprixMin()) ? false : true;
		 
			// Initialisation des deux variables définissant l'indice du distributeur et transformateur prioritaires

			
		 	if ((testFourchettePrix(test_t)||testFourchettePrix(test_d)) && testPrix){
			
			// Recherche de minimum & maximum
			

			}
			
			// Définition du prix
			
			double prix = (distributeur.get(prioDistri).getPrixMax()+transformateur.get(prioTransfo).getprixMin())/2;
			
			int autreDistri=Math.abs(prioDistri-1);
			int autreTransfo=Math.abs(prioTransfo-1);
			
			
			// Actualisation des prix d'achat et de vente
			
			distributeur.get(prioDistri).notif(new Vente(prix, unite));
			distributeur.get(autreDistri).notif(new Vente(prix, 0));
			transformateur.get(prioTransfo).notificationAchat(unite, prix);
			transformateur.get(autreTransfo).notificationAchat(0, prix);
			distributeur.get(prioDistri).getIndicateurStock().setValeur(distributeur.get(prioDistri), prix*unite);
			distributeur.get(autreDistri).getIndicateurStock().setValeur(distributeur.get(prioDistri), prix*unite);
			
			
			
		}

	@Override
	public String getNom() {
		return "Marche Distributeur / Transformateur";
	}
	
	public void next(){
		this.Echanges();
	}

	
		
}

