package abstraction.distributeur.europe;

public class Chocolats {
		private String chocolat;
		public Chocolats(String chocolat){
			if ((chocolat == "chocolat_au_lait") || chocolat == "chocolat_noir" || chocolat == "chocolat_blanc"){
				this.chocolat = chocolat;
			}
		}
		
		public String getChocolat(){
			return this.chocolat;
		}
}
