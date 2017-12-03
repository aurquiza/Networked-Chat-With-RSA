 /* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;
import Controllerpkg.*;
import Networkpkg.Client;

import java.net.*; 
import java.io.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class Chat extends JFrame implements ActionListener{
	private static Chat CONTAINER = null;
	  // GUI items
	  private JButton sendButton; // send message
	  //private JButton serverButton;
	  private JButton connectButton; // join chat
	  private JButton leaveChat; // leave chat
	  private JLabel serverAddressPrompt;
	  private JLabel serverPortPrompt;
	  private JTextField addressInfo;
	  private JTextField portInfo;
	  private JTextField message;
	  private JTextField clientKey;
	  private MessageBox mb;
	  
	  private Client client = null;
	
	private Chat() 
	{

	      // Default Constructor only exists to defeat instantiation.
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		JPanel left = new JPanel();
		JPanel connectionPanel = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(new BorderLayout());
		connectionPanel.setLayout(new GridLayout(2,4));
		JLabel createKeys = new JLabel(" Create Keys");
		clientKey = new JTextField();
		connectButton = new JButton("Join Chat");
		leaveChat = new JButton("Leave Chat");
		serverAddressPrompt = new JLabel(" Server Address");
		serverPortPrompt = new JLabel(" Server Port");
		addressInfo = new JTextField();
		portInfo = new JTextField();
		
		connectionPanel.add(createKeys);
		connectionPanel.add(clientKey);
		connectionPanel.add(connectButton);
		connectButton.addActionListener(new JoinChatEventHandler(this));
		connectionPanel.add(leaveChat);	
		leaveChat.setEnabled(false);
		connectionPanel.add(serverAddressPrompt);
		connectionPanel.add(addressInfo);
		connectionPanel.add(serverPortPrompt);
		connectionPanel.add(portInfo);
		connectionPanel.setBackground(new Color(204, 255, 245));
		
		left.add(connectionPanel, BorderLayout.NORTH);
		
		mb = new MessageBox(); // message history
		left.add(mb, BorderLayout.CENTER);
		
		JPanel messagePart = new JPanel();
		message = new JTextField(25);
		message.setEnabled (false);
		//message.addActionListener( this );
		sendButton = new JButton("Send");
		sendButton.addActionListener( new SendMessageHandler(this) );
		sendButton.setEnabled (false); // keep enabled until connected to server
		
		messagePart.add(message);
		messagePart.add(sendButton);
		messagePart.setBackground(new Color(204, 255, 245));
		left.add(messagePart, BorderLayout.SOUTH);
		
		right.add(ClientList.getClientList());
		
		right.setBackground(new Color(204, 255, 245));
		container.add(left);
		container.add(right);

		this.add(container);
		setJMenuBar(Menu.getMenu());
		this.setSize(800, 500);
		this.setTitle("Networked Chat with RSA");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		initiateOption();
	}

	public JTextField getText()
	{
		return clientKey;
	}
	
	// setter
	public void allowAccess(Client cl)
	{
		client = cl;
		message.setEnabled(true);
		sendButton.setEnabled(true);
		connectButton.setEnabled(false);
		leaveChat.setEnabled(true);
	}
	
	// setter
	public void appendMessage(String msg)
	{
		mb.addMessage(msg);
	}
	
	// getter
	public String getPortInfo()
	{
		return portInfo.getText();
	}
	
	// getter
	public String getIPInfo()
	{
		return addressInfo.getText();
	}
	
	// getter
	public String getMessage()
	{
		return message.getText();
	}
	
	// getter
	public Client getClientSocket()
	{
		return client;
	}
	
	public static Chat getChatContainer() 
	{
		if(CONTAINER == null)
		{
			CONTAINER = new Chat();
		}
		
	    return CONTAINER;
	}
	
	void initiateOption() 
	{
		
		int choice;
		String message = "Would you like to generate Public/Private Key pair Yourself?\n"
			    + "Press Yes to create prime numbers\n"
			    + "No will generate random prime numbers";
		choice = JOptionPane.showConfirmDialog(null, message, "Generate Key Options", JOptionPane.YES_NO_OPTION);
	        if (choice == JOptionPane.YES_OPTION) {
	          return;
	        }
	        else {
	           getText().setEditable(false); // if user chooses NO option, set text field to uneditable
	        }
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}

