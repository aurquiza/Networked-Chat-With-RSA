package Interfacepkg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;

import Networkpkg.NameAndKeyPair;

/* 
 * Client list has an updated list of all
 * users connected to chat. Only clients that are currently connected
 * will be shown. Since list is same for all users, Singleton design is used
 */ 

public class ClientList {
	
	private static ClientList clientlist = null;
	private static JPanel clientPanel = null;
	private JList<String> list;
	private static Vector<String> currentList = new Vector<String>();
	
	private ClientList(){
		clientPanel = new JPanel();
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//list.setVisibleRowCount(23);
		//list = new JTextArea(23, 30);
//		list.setEditable(false);
//		list.setLineWrap(true);
//		list.setWrapStyleWord(true);
		JScrollPane panel = new JScrollPane(list);
		panel.setLayout(new ScrollPaneLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Clients Online:"));
		panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.setPreferredSize(new Dimension(280, 390));
		clientPanel.add(panel, BorderLayout.CENTER);
	}
	
	public void addNewClient(String user)
	{
		currentList.addElement(user);
		list.setListData(currentList);
		//list.append(name + "\n");
	}
	
	public void removeClient(String user)
	{
		currentList.remove(user);
		list.setListData(currentList);
	}
	
	public List<String> getChosenClients()
	{
		return list.getSelectedValuesList();
	}
	
	public static JPanel getClientList()
	{

	      if(clientlist == null)
	      {
	         clientlist = new ClientList();
	      }
	      return clientPanel;
	}
	
	public static ClientList getClientBox()
	{
		if(clientlist == null)
			clientlist = new ClientList();
		
		return clientlist;
	}
}
