/* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
		container.setLayout(new GridLayout(1,2));
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(new BorderLayout());

		JLabel clientName = new JLabel("ClientName");
		JTextField clientNameText = new JTextField(10);
		JButton joinChat = new JButton("Join Chat");
		JButton leaveChat = new JButton("Leave Chat");
		
		JPanel loginPart = new JPanel();
		
		loginPart.add(clientName);
		loginPart.add(clientNameText);
		loginPart.add(joinChat);
		loginPart.add(leaveChat);	
		
		left.add(loginPart, BorderLayout.NORTH);
		
		MessageBox mb = new MessageBox();
		left.add(mb, BorderLayout.CENTER);
		
		JPanel messagePart = new JPanel();
		JTextField typeMessage = new JTextField(25);
		JButton sendMessage = new JButton("Send");
		messagePart.add(typeMessage);
		messagePart.add(sendMessage);
		left.add(messagePart, BorderLayout.SOUTH);
		
		right.add(ClientList.getClientList());

		container.add(left);
		container.add(right);
		this.add(container);
		setJMenuBar(Menu.getMenu());
		this.setSize(800, 500);
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

