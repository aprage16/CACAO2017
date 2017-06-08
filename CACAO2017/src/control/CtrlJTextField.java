package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

class Utilisateur implements Acteur {
	public String getNom() {
		return "utilisateur";
	}
	public void next() {
	}
}
public class CtrlJTextField  implements Observer, ActionListener {

    private JTextField field;
    private Indicateur ind;
    
    public CtrlJTextField(JTextField field, Indicateur ind) {
    	this.field = field;
    	this.ind = ind;
    }
    
	public void actionPerformed(ActionEvent e) {
		NumberFormat dc = NumberFormat.getInstance(Locale.FRANCE);
		dc.setMaximumFractionDigits(2);
		try {
			if (this.ind.getValeur()!=dc.parse(this.field.getText()).doubleValue())
				this.ind.setValeur(new Utilisateur(), dc.parse(this.field.getText()).doubleValue() );
		} catch (NumberFormatException | ParseException e1) {
			e1.printStackTrace();
		}
	}

	public void update(Observable o, Object arg) {
		NumberFormat dc = NumberFormat.getInstance(Locale.FRANCE);
		dc.setMaximumFractionDigits(2);
		dc.setMinimumFractionDigits(2);
		String formattedText = dc.format(ind.getValeur());
		this.field.setText(formattedText);
	}

}