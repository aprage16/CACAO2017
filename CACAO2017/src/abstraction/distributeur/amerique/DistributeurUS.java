package abstraction.distributeur.amerique;

import abstraction.distributeur.europe.IDistributeur;
import abstraction.distributeur.europe.Vente;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class DistributeurUS implements IDistributeur{
	private Treso treso;

	private Indicateur fonds;
	private Indicateur stock;

	
	public DistributeurUS(Treso treso, Indicateur fonds, Indicateur stock){
		this.treso=treso;
		this.fonds=fonds;
		this.stock=stock;
	}
	
	public DistributeurUS(){


		this.treso= new Treso(5000000000.0,62.5,new Demande(62.5));
		
		this.stock = new Indicateur("1_DISTR_US_stock", this, 62.5);
		this.fonds = new Indicateur("1_DISTR_US_fonds", this, 5000000000.0);
		
    		Monde.LE_MONDE.ajouterIndicateur( this.stock );
    		Monde.LE_MONDE.ajouterIndicateur( this.fonds );

	}
	
	
	public void next(){
		//System.out.println(Monde.LE_MONDE.getStep()+" "+this.getTreso().getDemande().demandeStep());
		if (this.getTreso().getStock()>=this.getTreso().getDemande().getCommande()){
		
			this.getTreso().setStock(this.getTreso().getStock()-this.getTreso().getDemande().getCommande());
			this.getTreso().setFonds(this.getTreso().getFonds()+this.getTreso().getDemande().getCommande()*10*10000000);//On vend 10$ le kg et l'unité est 10ktonnes
		}
		else{
			double vendu=this.getTreso().getStock();
			this.getTreso().setStock(0);
			this.getTreso().setFonds(this.getTreso().getFonds()+vendu*10*10000000);
		}
		
		this.getTreso().getDemande().setCommande(this.getTreso().getDemande().demandeStep());
	}

	
	
	public double getStock() {
		return this.getTreso().getStock();
	}

	public void setStock(double stock) {
		this.getTreso().setStock(stock);
	}

	public double getFonds() {
		return this.getTreso().getFonds();
	}



	public Treso getTreso(){
		return this.treso;
	}


	
	public double getPrixMax(){
		return this.getTreso().prixMax();
	}
	
	public void notif(Vente vente){
		this.getTreso().setStock(this.getTreso().getStock()+vente.getQuantite());
		this.getTreso().setFonds(this.getTreso().getFonds()-vente.getPrix());
	}

	public String getNom() {
		return "Distributeur USA";
	}

	@Override
	public Indicateur getIndicateurStock() {
		return this.stock;
	}


	public Indicateur getSolde() {
		return this.fonds;
	}
	
		
	
}
