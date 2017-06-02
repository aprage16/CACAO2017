package abstraction.transformateur.usa;
public class TransfoChocolat {
	
	private StockMatPremiere premiere;
	private StockProduitsFinis finis;
	public final static double[] Recette = {0.6, 0.15, 0.2, 0.05};//pourcentage de matiere premiere pour realiser une unite de produit fini. new double[4];
	
	private double[] stocks=new double[4];
	final static public int SUCRE =2;
	final static public int CACAO =0;
	final static public int LECITINE =3;
	final static public int LAIT =1;
	/*souchu*/
	public TransfoChocolat( StockMatPremiere premiere,StockProduitsFinis finis){
		this.finis=finis;
		this.premiere=premiere;
		
		
	}
	
	private void lireStock(){
		stocks[CACAO]=premiere.getCacao();
		stocks[LAIT]=premiere.getLait();
		stocks[SUCRE]=premiere.getSucre();
		stocks[LECITINE]=premiere.getLecitine();
	}
	
	public void produireChoco(double quantité){	
		double[] demande=new double[4];
		lireStock();
		boolean suffisant=true;
		double maxmanque=0;
		for (int i=0;i<Recette.length;i++){
			demande[i]=Recette[i]*quantité;
		}
		for (int i=0;i<Recette.length;i++){
			if(demande[i]>stocks[i]){
				suffisant=false;
			}
		}
		if (suffisant){
			for (int i=0;i<Recette.length;i++){
				stocks[i]-=demande[i];
			}
			finis.ajouterChocolat(quantité);
		}
		else{
			for (int i=0;i<Recette.length;i++){
				if((demande[i]-stocks[i])/demande[i]>maxmanque){
					maxmanque=(demande[i]-stocks[i])/demande[i];
				}
			}
			for (int i=0;i<Recette.length;i++){
				stocks[i]=stocks[i]-demande[i]*(1-maxmanque);
			}
			finis.ajouterChocolat(quantité*(1-maxmanque));
		}
		premiere.setCacao(stocks[0]);
		premiere.setLait(stocks[1]);
		premiere.setSucre(stocks[2]);
		premiere.setLecitine(stocks[3]);		
	}
	
	public static void test(){
		StockMatPremiere premiere= new StockMatPremiere(1000,1000,1000,1000);
		StockProduitsFinis finis = new StockProduitsFinis();
		TransfoChocolat transfo =new TransfoChocolat(premiere,finis);
		System.out.println(premiere.getCacao());
		System.out.println(finis.getStockChocolat());
		transfo.produireChoco(1700); //Changer les valeurs de chocolat produit pour tester le bon fonctionnement du script
		System.out.println(premiere.getCacao());
		System.out.println(finis.getStockChocolat());
	}
	public static void main(String[] args) {
		//test();
	}
	
	
	
	
	
	
	
	
	

}
