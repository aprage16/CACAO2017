package abstraction.transformateur.usa.interfacemarche;

public interface transformateur {  /* à modifier Transformateur( mettre majuscule ) */
	/*Souchu*/
	public double getprixMin();
	public void notif(double prix,double quantité);
	
	public double QteSouhaite();
	
	public void notificationAchat(double quantite, double prix);
	
	
	public int hashCode();


}

