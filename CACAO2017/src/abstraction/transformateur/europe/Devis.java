package abstraction.transformateur.europe;

public class Devis {
	
	private ITransfoContrat transfo;
	private IDistriContrat distri;
	private double quantitedisponible;
	private double prixinitial;
	private double quantitevoulue;
	private double quantitefinale;
	private double contrepropositionprix;
	private boolean acceptationT;
	private boolean acceptationD;
	
	public Devis(ITransfoContrat t,IDistriContrat d, double q1, double p1, double q2, double q3, double p2, boolean acceptationT, boolean acceptationD){
		this.transfo=t;
		this.distri=d;
		this.quantitedisponible=q1;
		this.prixinitial=p1;
		this.quantitevoulue=q2;
		this.quantitefinale=q3;
		this.contrepropositionprix=p2;
		this.acceptationT=acceptationT;
		this.acceptationD=acceptationD;
	}
	
	public Devis(ITransfoContrat t,IDistriContrat d){
		this.transfo=t;
		this.distri=d;
		quantitedisponible=0; //correspond à Q1
		prixinitial=0; //correspond à P1
		quantitevoulue=0;//correspond à Q2
		quantitefinale=0;//correspond à Q3
		contrepropositionprix=0;//correspond à P2
		acceptationT=false;
		acceptationD=false;
	}
	
	public ITransfoContrat getTransfo(){
		return this.transfo;
	}
	
	public IDistriContrat getDistri(){
		return this.distri;
	}
	
	public double getQ1(){
		return this.quantitedisponible;
	}
	
	public double getQ2(){
		return this.quantitevoulue;
	}
	
	public double getQ3(){
		return this.quantitefinale;
	}
	
	public double getP1(){
		return this.prixinitial;
	}
	
	public double getP2(){
		return this.contrepropositionprix;
	}
	
	public boolean getChoixT(){
		return this.acceptationT;
	}
	
	public boolean getChoixD(){
		return this.acceptationD;
	}
	
	public void setQ1(double q1){
		this.quantitedisponible=q1;
	}
	
	public void setQ2(double q2){
		this.quantitevoulue=q2;
	}
	
	public void setQ3(double q3){
		this.quantitefinale=q3;
	}
	
	public void setP1(double p1){
		this.prixinitial=p1;
	}
	
	public void setP2(double p2){
		this.contrepropositionprix=p2;
	}
	
	public void setChoixT(boolean T){
		this.acceptationT=T;
	}
	
	public void setChoixD(boolean D){
		this.acceptationD=D;
	}
}

