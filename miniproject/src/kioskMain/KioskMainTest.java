package kioskMain;

import db.Init;
import main.gui.MainGUI;
import orders.gui.OrderGUI;

public class KioskMainTest {

	public static void main(String[] args) {
		try {
			Init.init();
		} catch (Exception e) {
			// TODO: handle exception
		}
		new MainGUI();
	}

}
