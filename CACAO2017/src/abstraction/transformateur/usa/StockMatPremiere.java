package abstraction.transformateur.usa;

public class StockMatPremiere { 
	/*modif souhu*/
	private double cacao;
	private double lait;
	private double sucre;
	private double lecitine;
	
	public StockMatPremiere (double Cacao,double Lait, double Sucre, double Lecitine){
		this.cacao= Cacao;
		this.lait=Lait;
		this.sucre=Sucre;
		this.lecitine=Lecitine;
	}
	
	public StockMatPremiere(){
		this(0,0,0,0);
	}
	
	public double getCacao(){
		return this.cacao;
	}
	
	public double getLait(){
		return this.lait;		
	}
	
	public double getSucre(){
		return this.sucre;
	}
	
	public double getLecitine(){
		return this.lecitine;
	}
	
	public void setIngredient(int i,double quantite){
		switch(i){
		case 0:this.setCacao(quantite);
		case 1:this.setLait(quantite);
		case 2:this.setSucre(quantite);
		case 3:this.setLecitine(quantite);		
		}
	}
	
	public double getIngredient(int i){
		switch(i){
		case 0:return this.getCacao();
		case 1:return this.getLait();
		case 2:return this.getSucre();
		case 3:return this.getLecitine();		
		}
		return 0;
	}

	
	public void setCacao(double cacao){
		this.cacao=cacao;
	}
	
	public void setLait(double Lait){
		this.lait=Lait;		
	}
	
	public void setSucre(double Sucre){
		this.sucre=Sucre;
	}
	
	public void setLecitine(double Lecitine){
		this.lecitine=Lecitine;
	}

	public void enleverLecitine(double Lecitine){
		this.lecitine-=Lecitine;
	}
	public void enleverSucre(double Surcre){
		this.sucre-=sucre;;
	}
	public void enleverLait(double Lait){
		this.lait-=Lait;
	}
	public void eneleverCacao(double Cacao){
		this.cacao-=Cacao;
	}

	
	
}
