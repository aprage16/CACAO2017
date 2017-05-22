package abstraction.distributeur.europe;

import java.util.ArrayList;
import abstraction.transformateur.usa.interfacemarche.*;
import abstraction.fourni.Acteur;

public class Marche implements Acteur{


	private static double bornesmin=0.004;
	private static double bornesmax=0.008;
	private ArrayList<IDistributeur> distributeur;
	private ArrayList<transformateur> transformateur;
	private ArrayList<IDistributeur> distributeurActif;
	private ArrayList<transformateur> transformateurActif;
	private boolean OnEchange;
	private double prixmoyen;

	double unite=1000;

	public Marche(ArrayList<IDistributeur> distributeur, ArrayList<transformateur> transformateur){
		this.distributeur=distributeur;
		this.transformateur=transformateur;
	}

	public ArrayList<IDistributeur> getDistrib(){
		return this.distributeur;
	}

	public ArrayList<transformateur> getTransfo(){
		return this.transformateur;
	}

	public int indiceMaximum(){
		int indice_max = 0;
		for (int i=1; i<this.distributeurActif.size(); i++){
			indice_max = ((this.distributeurActif.get(i).getPrixMax()>this.distributeurActif.get(indice_max).getPrixMax()) ? i : indice_max);
		}
		return indice_max;
	}

	public int indiceMinimum(){
		int indice_min = 0;
		for (int i=1; i<this.transformateurActif.size(); i++){
			indice_min = ((this.transformateurActif.get(i).getprixMin()<this.transformateurActif.get(indice_min).getprixMin()) ? i : indice_min);
		}
		return indice_min;		
	}

	public void Actif(){
		this.distributeurActif=new ArrayList<IDistributeur>();
		this.transformateurActif=new ArrayList<transformateur>();
		for (transformateur a:this.transformateur){
			if (a.getprixMin()>=bornesmin&&a.getprixMin()<=bornesmax){
				transformateurActif.add(a);
			}
		}
		for (IDistributeur a:this.distributeur){
			if (a.getPrixMax()>=bornesmin && a.getPrixMax()<=bornesmax){
				this.distributeurActif.add(a);
			}
		}
	}

	public void Echanges(){
		IDistributeur prioD;
		transformateur prioT;
		this.Actif();
		if (this.distributeurActif.isEmpty() || this.transformateurActif.isEmpty()){
			this.OnEchange=false;
		}
		if (OnEchange){
			prioD= this.distributeur.get(this.indiceMaximum());
			prioT=this.transformateur.get(this.indiceMinimum());
			prixmoyen=(prioD.getPrixMax()+prioT.getprixMin())/2;
			prioD.notif(new Vente(prixmoyen,unite));
			prioT.notif(prixmoyen,unite);
		}
	}

	@Override
	public String getNom() {
		return "Marche Distributeur / Transformateur";
	}

	public void next(){
		this.OnEchange=true;
		while(this.OnEchange){
			this.Echanges();
			
		}
	}



}