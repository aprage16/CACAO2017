package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
		if (this.ind.getValeur()!=Double.parseDouble(this.field.getText()))
			this.ind.setValeur(new Utilisateur(), Double.parseDouble(this.field.getText()) );
	}

	public void update(Observable o, Object arg) {
		DecimalFormat dc = new DecimalFormat("0.00");
		String formattedText = dc.format(ind.getValeur());
		this.field.setText(formattedText);
	}

}