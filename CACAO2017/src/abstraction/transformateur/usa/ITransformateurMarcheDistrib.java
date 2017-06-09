package abstraction.transformateur.usa;

public interface ITransformateurMarcheDistrib {
	
	public double getprixMin();
	
	public void notif(double prix,double quantité);
	
	public void notificationAchat(double prix, double qte);

	public double QteSouhaite();
}
