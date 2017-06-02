package abstraction.producteur.ameriquelatine;

import abstraction.distributeur.europe.MondeV1;


//MOdifié par Lolotteisyoung et AnaisBel
//26/04 Adrien
public class Recolte {
	public final static int SURFACE_CULTIVABLE=30000; //Constante : surface cultivable max en hectare
	private double indiceRecolte; // Indicateur variant entre 0 et 1 : pourcentage pour fixer nb de fèves totales
	public final static double INDICE_REEL = 1.54; // Ajustement de l'indice pour atteindre la production annuel
	
	public Recolte(double indiceRecolte){// indice initial
		this.indiceRecolte=indiceRecolte;
	}

	public int getQterecoltee() {
		return (int)(SURFACE_CULTIVABLE*indiceRecolte);

	}

	public void miseAJourIndice() {
	//* une année = 26 next
		int periode = MondeV1.LE_MONDE.getStep();
		double[] indice = {0.6,0.6,0.6,0.6,0.5,0.7,0.7,0.6,0.5,0.5,0.4,0.4,0.3,0.3,0.4,0.5,0.6,0.7,0.8,0.9,0.9,0.8,0.8,0.7,0.6,0.6};
		this.indiceRecolte= indice[periode%26]*INDICE_REEL+Math.random()*0.1;
	}
	
	
		
	
}
