/* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Chat extends JFrame{
	private static Chat CONTAINER = null;
	
	private Chat() {

	      // Default Constructor only exists to defeat instantiation.

		JPanel container = new JPanel();
		JLabel clientName = new JLabel("ClientName");
		JTextField clientNameText = new JTextField(20);
		JButton connectMe = new JButton("Connect Me");
		container.add(clientName);
		container.add(clientNameText);
		container.add(connectMe);
		this.add(container);
		setJMenuBar(Menu.getMenu());
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

