package abstraction.producteur.ameriquelatine;
//MOdifié par Lolotteisyoung et AnaisBel
//26/04 Adrien
public class Recolte {
	public final static int SURFACE_CULTIVABLE=30000; //Constante : surface cultivable max en hectare
	private double indiceRecolte; // Indicateur variant entre 0 et 1 : pourcentage pour fixer nb de fèves totales
	
	public Recolte(double indiceRecolte){// indice initial
		this.indiceRecolte=indiceRecolte;
	}

	public int getQterecoltee() {
		return (int)(SURFACE_CULTIVABLE*indiceRecolte);

	}

	public void miseAJourIndice() {
		this.indiceRecolte=Math.random()*0.1+0.85 ; 
	}
	
	
}
