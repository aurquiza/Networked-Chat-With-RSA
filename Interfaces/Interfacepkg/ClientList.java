package Interfacepkg;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/* 
 * Client list has an updated list of all
 * users connected to chat. Only clients that are currently connected
 * will be shown. Since list is same for all users, Singleton design is used
 */

public class ClientList {
	
	private static ClientList clientlist = null;
	private static JPanel clientScrollPane = null;
	
	private ClientList(){
		clientScrollPane = new JPanel();
		JLabel names = new JLabel("Clients Online:");
		clientScrollPane.add(names);
		//populate panel with clients
		
	}
	
	public static JPanel getClientList() {

	      if(clientlist == null) {
	         clientlist = new ClientList();
	      }

	      return clientScrollPane;
	   }
}
