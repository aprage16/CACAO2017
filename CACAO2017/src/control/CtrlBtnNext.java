package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import abstraction.fourni.Monde;

public class CtrlBtnNext implements ActionListener {

   private Monde monde;
   private int nb;
   
	public CtrlBtnNext(Monde monde, int nb) {
		this.monde = monde;
		this.nb=nb;
	}
	public void actionPerformed(ActionEvent arg0) {
		for (int i=1; i<=this.nb; i++) 
		this.monde.next();
	}
}
