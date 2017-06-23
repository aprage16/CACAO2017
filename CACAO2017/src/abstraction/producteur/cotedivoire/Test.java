package abstraction.producteur.cotedivoire;

import abstraction.fourni.Monde;

public class Test {
	public static void main (String[] args) {
		MarcheProd marche= new MarcheProd();
		System.out.println(marche.getCoursActuel());
		marche.setQuantiteAchetableGlobale(2000);
		marche.setQuantiteVoulueGlobale(3000);
		marche.EvolutionDuCours();
		System.out.println(marche.getCoursActuel());
		marche.setQuantiteAchetableGlobale(3000);
		marche.setQuantiteVoulueGlobale(2000);
		marche.EvolutionDuCours();
		System.out.println(marche.getCoursActuel());
		
		ProductionCoteDIvoire prod= new ProductionCoteDIvoire(); 
		System.out.println(prod.quantiteMiseEnvente());
		System.out.println(prod.getQuantiteProd());
		prod.variationProduction(Monde.LE_MONDE.getStep());
		System.out.println(prod.quantiteMiseEnvente());
		System.out.println(prod.getQuantiteProd());
		prod.variationProduction(Monde.LE_MONDE.getStep());
		System.out.println(prod.quantiteMiseEnvente());
		System.out.println(prod.getQuantiteProd());
	}
}
