package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

import presentation.FenetreJournal;
import abstraction.fourni.Journal;

public class CtrlCheckBoxJournal  implements ActionListener, Observer {

    private JCheckBox box;
    private FenetreJournal fJournal;
    
    public CtrlCheckBoxJournal(JCheckBox box, FenetreJournal fJournal) {
    	this.box = box;
    	this.fJournal = fJournal;
    }
    
	public void actionPerformed(ActionEvent e) {
			this.fJournal.setVisible(this.box.isSelected());
	}

	public void update(Observable arg0, Object arg1) {
		if (this.fJournal.isVisible()) {
			Journal j = (Journal) arg0;
			this.fJournal.setLabel(j.toHtml());
		}
	}

}