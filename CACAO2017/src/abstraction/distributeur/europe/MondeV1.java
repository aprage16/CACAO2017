
// classe codée par Numa/Walid
package abstraction.distributeur.europe;
import abstraction.producteur.ameriquelatine.*;
import abstraction.producteur.cotedivoire.*;
import abstraction.producteur.cotedivoire.contrats.*;
import abstraction.transformateur.europe.Transformateur;
import abstraction.transformateur.usa.*;
import abstraction.distributeur.amerique.*;
import java.util.ArrayList;
import abstraction.fourni.Monde;
import abstraction.transformateur.usa.TransformateurUsa;
import abstraction.transformateur.europe.*;
public class MondeV1 extends Monde {
	
	/**
	 *Création de l'instance Monde pour la version 1
	 */
	

	public void peupler(){
		Producteur pAmeriqueLatine = new Producteur();
		ProductionCoteDIvoire p2 = new ProductionCoteDIvoire();
		
		//
		
		this.ajouterActeur(pAmeriqueLatine);
		this.ajouterActeur(p2);
		
		TransformateurUsa t1 = new TransformateurUsa();
		
		Transformateur t2= new Transformateur();
		
		this.ajouterActeur(t1);
		this.ajouterActeur(t2);
		
		AgentContratPT AgentPT = new AgentContratPT();
		AgentPT.addProd(pAmeriqueLatine);
		AgentPT.addProd(p2);
		AgentPT.addTrans(t1);
		AgentPT.addTrans(t2);
		
		this.ajouterActeur(AgentPT);
		
		MarcheProd Marche = new MarcheProd();
		Marche.addProducteur(pAmeriqueLatine);
		Marche.addProducteur(p2);
		Marche.addTransformateur(t1);
		Marche.addTransformateur(t2);
		
		this.ajouterActeur(Marche);
		
		Distributeur d1 = new Distributeur();
		DistributeurUS d2 = new DistributeurUS();
		
		this.ajouterActeur(d1);
		this.ajouterActeur(d2);
		
		
		Marche MarcheDT = new Marche();
		MarcheDT.getDistrib().add(d1);
		MarcheDT.getDistrib().add(d2);
		MarcheDT.getTransfo().add(t1);
		MarcheDT.getTransfo().add(t2);
		this.ajouterActeur(MarcheDT);
		
		MarcheClients MarcheClients = new MarcheClients();
		MarcheClients.addDistributeur(d1);
		MarcheClients.addDistributeur(d2);
		this.ajouterActeur(MarcheClients);


	}
	

}
