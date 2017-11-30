package Interfacepkg;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

/* 
 * Client list has an updated list of all
 * users connected to chat. Only clients that are currently connected
 * will be shown. Since list is same for all users, Singleton design is used
 */ 

public class ClientList {
	
	private static ClientList clientlist = null;
	private static JPanel clientPanel = null;
	
	private ClientList(){
		clientPanel = new JPanel();
		JTextArea list = new JTextArea(23, 30);
		list.setEditable(false);
		list.setLineWrap(true);
		list.setWrapStyleWord(true);
		JScrollPane panel = new JScrollPane(list);
		panel.setLayout(new ScrollPaneLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Clients Online:"));
		panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		clientPanel.add(panel, BorderLayout.CENTER);
	}
	
	public static JPanel getClientList() {

	      if(clientlist == null) {
	         clientlist = new ClientList();
	      }
	      return clientPanel;
	   }
}
