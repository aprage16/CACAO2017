package abstraction.distributeur.europe;

import java.util.ArrayList;
import abstraction.transformateur.usa.interfacemarche.*;
import abstraction.fourni.Acteur;

public class Marche implements Acteur{
	
	private ArrayList<IDistributeur> distributeur;
	private ArrayList<transformateur> transformateur;
	
	static final double PRIXVENTEMIN=20;
	static final double PRIXVENTEMAX=400000000;
	
	static final double PRIXACHATMIN=20;
	static final double PRIXACHATMAX=400000000;
	
	static final double UNITE=10000;
	
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
			test_t[i] = (transformateur.get(i).getprixMin()>=PRIXVENTEMIN) &&	(transformateur.get(i).getprixMin()<=PRIXVENTEMAX);
			test_d[i] = (distributeur.get(i).getPrixMax()>=PRIXACHATMIN) &&  (distributeur.get(i).getPrixMax()<=PRIXACHATMAX);
		}

		
		// On vérifie qu'une transaction soit possible
		
		
		int prioDistri=0;
		int prioTransfo=0;
		
		prioDistri = this.indiceMaximum();
		prioTransfo = this.indiceMinimum();
					
		 boolean testPrix = (distributeur.get(prioDistri).getPrixMax()<transformateur.get(prioTransfo).getprixMin()) ? false : true;
		 
			// Initialisation des deux variables définissant l'indice du distributeur et transformateur prioritaires

			System.out.println(testPrix);
			System.out.println(distributeur.get(prioDistri).getPrixMax());
			System.out.println(transformateur.get(prioTransfo).getprixMin());
		 	while ((testFourchettePrix(test_t)||testFourchettePrix(test_d)) && testPrix){
			
			// Recherche de minimum & maximum
			

			
			
			// Définition du prix
			
			double prix = (distributeur.get(prioDistri).getPrixMax()+transformateur.get(prioTransfo).getprixMin())/2;
			
			int autreDistri=Math.abs(prioDistri-1);
			int autreTransfo=Math.abs(prioTransfo-1);
			
			System.out.println(prix);
			System.out.println(distributeur.get(prioDistri).getPrixMax());
			System.out.println(transformateur.get(prioTransfo).getprixMin());
			// Actualisation des prix d'achat et de vente
			
			distributeur.get(prioDistri).notif(new Vente(prix, UNITE));
			distributeur.get(autreDistri).notif(new Vente(prix, 0));
			transformateur.get(prioTransfo).notificationAchat(UNITE, prix);
			transformateur.get(autreTransfo).notificationAchat(0, prix);

			
		 	}
			
		}

	@Override
	public String getNom() {
		return "Marche Distributeur / Transformateur";
	}
	
	public void next(){
		this.Echanges();
	}

	
		
}

