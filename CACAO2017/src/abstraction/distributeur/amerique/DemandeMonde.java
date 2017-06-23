/*package abstraction.distributeur.amerique;//A améliorer, remplacer les deux distrib

import abstraction.distributeur.europe.IDistributeur;
import abstraction.fourni.Monde;

public class DemandeMonde {
	public static double augStVal; //3%
	public static double augPaques;// 4% des ventes de chocolat au total
	public static double augHalloween; //10%
	public static double augNoel; // 8% des ventes de chocolat au total
	public static double nbStepAnnees=26;
	public static double commandesAnneeTotales=6000;
	public static double commandesStepFixe=(commandesAnneeTotales-commandesAnneeTotales*(augStVal+augPaques+augHalloween+augNoel))/nbStepAnnees;
	public static double pourcentageFixe=0.40;
	public static double pourcentageFluctuantTotal=0.2;
	public static double vendusUS;
	public static double vendusEU;
	
	
	private double demandeUS;
	private double demandeEU;
	
	public DemandeMonde(){
		
	}
	
	public double getDemandeUS(){
		this.calculDemandes();
		return this.demandeUS;
	}
	public double getDemandeEU(){
		this.calculDemandes();
		return this.demandeEU;
	}
	
	public void setDemandeUS(double US){
		this.demandeUS=US;
	}
	public void setDemandeEU(double EU){
		this.demandeEU=EU;
	}
	
	/* A Calculer, dépend de la fidélité (on fait tourner le monde une fois sans rien pour avoir la demande du client, 
	
	
	public void calculDemandes(){
		double augSaisonniere=0;
		int date=Monde.LE_MONDE.getStep()%26;
		if (date==4){
			augSaisonniere=augStVal;
		}
		if (date==13){
			augSaisonniere=augPaques;
		}
		if (date==22){
			augSaisonniere=augHalloween;
		}
		if (date==26){
			augSaisonniere=augNoel;
		}
		
		double commandesTotalesStep=(commandesStepFixe+augSaisonniere*commandesAnneeTotales)*DistributeurUS.coefAleatoire;
		double prixUS=(((DistribClient) Monde.LE_MONDE.getActeur("distributeurUS")).getPrixClient());
		double prixEU=(((DistribClient) Monde.LE_MONDE.getActeur("Distributeur europe")).getPrixClient());
		double p=prixEU/(prixUS+prixEU);
		
		//double satisfactionUS=(((DistribClient) Monde.LE_MONDE.getActeur("distributeurUS")).getVendu())/vendusUS;
		//double satisfactionEU=(((DistribClient) Monde.LE_MONDE.getActeur("distributeurUS")).getVendu())/vendusUS;
		double satisfactionTotale=satisfactionUS/(satisfactionUS+satisfactionEU);
		
		double fluctuant= pourcentageFluctuantTotal*(0.5*p+0.3*satisfactionTotale+0.2*DistributeurUS.coefAleatoire);
		
		this.setDemandeUS(commandesTotalesStep*(pourcentageFixe+this.calculFluctuant()));
		this.setDemandeEU(commandesTotalesStep*(1-this.getDemandeUS()));
	}
	
	
}*/
