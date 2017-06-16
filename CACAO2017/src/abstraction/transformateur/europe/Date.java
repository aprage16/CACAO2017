package abstraction.transformateur.europe;

public class Date implements Comparable<Date> {
	private int jour;
	private int mois;
	private int annee;

	public Date(int jour, int mois, int annee) {
		if (Date.coherente(jour, mois, annee)) {
			this.jour = jour;
			this.mois = mois;
			this.annee = annee;
		} else {
			this.jour=1;
			this.mois=1;
			this.annee=1900;
		}
	}
	public Date() {
		this( 1, 1, 2017);
	}
	public int getJour() {
		return this.jour;
	}
	public int getMois() {
		return this.mois;
	}
	public int getAnnee() {
		return this.annee;
	}
	public void setJour( int jour) {
		if (Date.coherente(jour,this.getMois(),this.getAnnee())) {
			this.jour = jour;
		}
	}
	public void setMois(int mois) {
		if (Date.coherente(this.getJour(), mois, this.getAnnee())) {
			this.mois = mois;
		}
	}
	public void setAnnee(int annee) {
		if (Date.coherente(this.getJour(), this.getMois(), annee)) {
			this.annee = annee;
		}
	}
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (o instanceof Date) {
			Date od = (Date)o;
			return this.getJour()==od.getJour() 
					&& this.getMois()==od.getMois()
					&& this.getAnnee()==od.getAnnee();
		} else {
			return false;
		}
	}
	private static boolean bissextile(int a) {
		return  (((a%400)==0)||(((a%4)==0)&& ((a%100)!=0)));
	}
	private static int nbJoursMois(int m, int a) {
		int nbJ;
		if (m==4 || m==6 || m==9 || m==11) {
			nbJ=30;
		}
		else {
			if (m==2) {
				if (Date.bissextile(a)) {
					nbJ=29;
				}
				else {
					nbJ=28;
				}
			}
			else { //1, 3, 5, 7, 8, 10 ou 12
				nbJ=31;
			}
		}
		return nbJ;
	}
	private static boolean coherente(int j, int m, int a) {
		return (m>0
				&& m<13
				&& j>0
				&& j<=Date.nbJoursMois(m,a));
	}

	public boolean before(Date d) {
		return this.getAnnee()<d.getAnnee() 
				|| (this.getAnnee()==d.getAnnee() 
				&& ( this.getMois()<d.getMois() 
						|| (this.getMois()==d.getMois() 
						&& this.getJour()<d.getJour())));
	}

	public Date lendemain() {
		int j=this.getJour()+1;
		int m=this.getMois();
		int a=this.getAnnee();
		if (j>Date.nbJoursMois(m, a)) {
			j=1;
			m++;
			if (m>12) {
				m=1; 
				a++;
			}
		}
		return new Date(j,m,a);
	}
	public String toString() {
		String resultat="";
		if (this.getJour()<10) {
			resultat +="0";
		}
		resultat +=this.getJour()+"/";
		if (this.getMois()<10) {
			resultat +="0";
		}
		resultat +=this.getMois()+"/";
		if (this.getAnnee()<1000) {
			resultat +="0";
		}
		if (this.getAnnee()<100) {
			resultat +="0";
		}
		if (this.getAnnee()<10) {
			resultat +="0";
		}
		resultat +=this.getAnnee()+" ";
		return resultat;

	}
	public int compareTo(Date d) {
		if (this.equals(d)) {
			return 0;
		} else {
			if (this.before(d)) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}