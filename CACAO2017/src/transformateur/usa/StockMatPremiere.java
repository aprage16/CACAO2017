package transformateur.usa;

public class StockMatPremiere {
	
	private int Cacao;
	private int Lait;
	private int Sucre;
	private int Vanille;
	private int Lecithine;
	private int Epice;
	
	public StockMatPremiere (int Cacao,int Lait, int Sucre, int Vanille, int Lecithine,int Epice){
		this.Cacao= Cacao;
		this.Lait=Lait;
		this.Sucre=Sucre;
		this.Vanille=Vanille;
		this.Lecithine=Lecithine;
		this.Epice=Epice;
	}
	
	public int getCacao(){
		return this.Cacao;
	}
	
	public int getLait(){
		return this.Lait;		
	}
	
	public int getSucre(){
		return this.Sucre;
	}
	
	public int getVanille(){
		return this.Vanille;
	}
	
	public int getLecithine(){
		return this.Lecithine;
	}
	
	public int getEpice(){
		return this.Epice;
	}
	
	
}
