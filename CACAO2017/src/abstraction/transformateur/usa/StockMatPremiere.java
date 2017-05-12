package abstraction.transformateur.usa;

public class StockMatPremiere {
	/*modif souhu*/
	private double Cacao;
	private double Lait;
	private double Sucre;
	private double Lecitine;
	
	public StockMatPremiere (double Cacao,double Lait, double Sucre, double Lecitine){
		this.Cacao= Cacao;
		this.Lait=Lait;
		this.Sucre=Sucre;
		this.Lecitine=Lecitine;
	}
	
	public StockMatPremiere(){
		this(0,0,0,0);
	}
	
	public double getCacao(){
		return this.Cacao;
	}
	
	public double getLait(){
		return this.Lait;		
	}
	
	public double getSucre(){
		return this.Sucre;
	}
	
	public double getLecitine(){
		return this.Lecitine;
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
		this.Cacao=cacao;
	}
	
	public void setLait(double Lait){
		this.Lait=Lait;		
	}
	
	public void setSucre(double Sucre){
		this.Sucre=Sucre;
	}
	
	public void setLecitine(double Lecitine){
		this.Lecitine=Lecitine;
	}

	public void enleverLecitine(double Lecitine){
		this.Lecitine-=Lecitine;
	}
	public void enleverSucre(double Surcre){
		this.Sucre-=Sucre;;
	}
	public void enleverLait(double Lait){
		this.Lait-=Lait;
	}
	public void eneleverCacao(double Cacao){
		this.Cacao-=Cacao;
	}

	
	
}
