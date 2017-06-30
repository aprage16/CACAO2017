
package abstraction.producteur.ameriquelatine;

import abstraction.distributeur.europe.MondeV1;

import abstraction.fourni.Monde;
//MOdifié par Lolotteisyoung et AnaisBel
//26/04 Adrien



public class Recolte {
	
	public Producteur prod;
	public double surfaceCultivable; //(realite 30000) Constante : surface cultivable max en hectare
	private double indiceRecolte; // Indicateur variant entre 0 et 1 : pourcentage pour fixer nb de fèves totales
	
	public Recolte(double indiceRecolte, Producteur prod){// indice initial
		this.indiceRecolte=indiceRecolte;
		this.surfaceCultivable=30000;
		this.prod=prod;
	}

	public int getQterecoltee() {
		return (int)(this.getSurfaceCultivable()*indiceRecolte);
	}
	
	public double getSurfaceCultivable(){
		return this.surfaceCultivable;
	}
	
	public void setSurfaceCultivable(double quantite){
		if(prod.getQteVendue()/quantite>=0.9 && this.getSurfaceCultivable()<50000){
			this.surfaceCultivable+=1000;
		}
		if(prod.getQteVendue()/quantite<0.5 && this.getSurfaceCultivable()>20000){
			this.surfaceCultivable-=1000;
		}
		
	}
	public int intemperie(){
		return (int) (Math.random()*52);
	}
	
	public void miseAJourIndice() {
	//* une année = 26 next
		if(intemperie()==50){
			this.indiceRecolte=0;
		}else{
			int periode = MondeV1.LE_MONDE.getStep();
			double[] indice = {0.6,0.6,0.6,0.6,0.5,0.7,0.7,0.6,0.5,0.5,0.4,0.4,0.3,0.3,0.4,0.5,0.6,0.7,0.8,0.9,0.9,0.8,0.8,0.7,0.6,0.6};
			this.indiceRecolte= indice[(periode)%26]+Math.random()*0.1;
		}
	}
}

