package presentation;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Test;

public class FenetrePrincipaleTest {

	@Test
	public void test() {
		FenetrePrincipale fp = new FenetrePrincipale();
		for (int i=0; i<100; i++)
			((JButton) fp.getRootPane().getContentPane().getComponent(2)).doClick();
	}

}
