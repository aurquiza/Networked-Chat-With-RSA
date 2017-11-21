/* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Chat extends JFrame{
	private static Chat CONTAINER = null;
	
	private Chat() {

	      // Default Constructor only exists to defeat instantiation.

		JPanel container = new JPanel();
		this.add(container);
		this.add(Menu.getMenu());
		this.setSize(1000, 500);
		this.setTitle("Let's get the party started!");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	   }

	public static Chat getChatContainer() {

	      if(CONTAINER == null) {
	         CONTAINER = new Chat();
	      }

	      return CONTAINER;
	   }
}

