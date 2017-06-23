package abstraction.distributeur.europe;

import static org.junit.Assert.*;

import org.junit.Test;

import abstraction.fourni.Monde;

public class MondeV1Test {

	@Test
	public void testNext() {
		Monde.LE_MONDE = new MondeV1(); 
		Monde.LE_MONDE.peupler();
		for (int i=0; i<1000; i++)
			Monde.LE_MONDE.next();
	}

}
