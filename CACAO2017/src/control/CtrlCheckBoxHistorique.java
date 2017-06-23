package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

import presentation.FenetreHistorique;
import abstraction.fourni.Historique;

public class CtrlCheckBoxHistorique  extends WindowAdapter implements ActionListener, Observer {

	private JCheckBox checkBox;
	private FenetreHistorique fenetreHistorique;

	public CtrlCheckBoxHistorique(JCheckBox checkBox, FenetreHistorique fenetreHistorique) {
		this.checkBox = checkBox;
		this.fenetreHistorique = fenetreHistorique;
	}

	public void actionPerformed(ActionEvent e) {
		this.fenetreHistorique.setVisible(this.checkBox.isSelected());
	}

	public void update(Observable o, Object arg) {
		if (this.fenetreHistorique.isVisible()) {
			Historique h = (Historique)o;
			this.fenetreHistorique.setLabel(h.toHtml());
		}
	}

	public void windowClosing(WindowEvent arg0) {
		this.fenetreHistorique.setVisible(false);
		this.checkBox.setSelected(false);
	}

}