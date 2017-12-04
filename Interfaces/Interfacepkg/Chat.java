 /* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;
import Controllerpkg.*;
import Networkpkg.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

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

	  private JTextField clientName;
	  //private JLabel status;

	  
	  static BigInteger firstPrime;
	  static BigInteger secondPrime;
	  
	  // vector of initial values sent to server, contains:
	  // client name, public and private keys, server address and port
	  

	  private JTextField clientKey;
	  private MessageBox mb;
	  
	  private Client client = null;

	
	private Chat() 
	{
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		JPanel left = new JPanel();
		JPanel connectionPanel = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(new BorderLayout());
		connectionPanel.setLayout(new GridLayout(2,4));
		JLabel createName = new JLabel(" Your Name");
		clientName = new JTextField();
		connectButton = new JButton("Join Chat");

		connectButton.setEnabled(false); //set enabled until user inputs all data
		leaveChat = new JButton("Leave Chat");
		leaveChat.setEnabled(false); 

		serverAddressPrompt = new JLabel(" Server Address");
		serverPortPrompt = new JLabel(" Server Port");
		addressInfo = new JTextField();
		portInfo = new JTextField();
		clientName.setText(JOptionPane.showInputDialog(this, "Type in your Name:"));
		clientName.setEnabled (false);
		connectionPanel.add(createName);
		connectionPanel.add(clientName);
		connectionPanel.add(connectButton);
		connectButton.addActionListener(new JoinChatEventHandler(this));
		connectionPanel.add(leaveChat);	
		leaveChat.setEnabled(false);
		leaveChat.addActionListener(new LeaveChatEventHandler(this));
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

		initiateOption(); // get prime numbers generated based on user"s choice
		getConnectionInfo(); // get port and IP address from user input
	}


	public JTextField getIPaddress()
	{
		return addressInfo;
	}
	
	public JTextField getPort()
	{
		return portInfo;
	}
	

	public JTextField getNameInfo()
	{
		return clientName;
	}

	// first and second prime numbers
	// are generated from file or from user input
	public static BigInteger getFirstPrime()
	{
		return firstPrime;
	}
	
	public static BigInteger getSecondPrime()
	{
		return secondPrime;
	}
	
	private void setFirstPrime(String num)
	{

		firstPrime = new BigInteger(num);
	}
	
	private void setSecondPrime(String num)
	{
		secondPrime = new BigInteger(num);
	}
	
	void readPrimes(String fileName)
	{
		 
		String line;
		ArrayList<String> primes = new ArrayList<String>(); // change size later
		int counter = 0;
		
		try {
	           // FileReader reads text files in the default encoding.
	           FileReader fileReader = 
	               new FileReader(fileName);

	           // Always wrap FileReader in BufferedReader.
	           BufferedReader bufferedReader = 
	               new BufferedReader(fileReader);

	           // read one line from file
	           
	           while((line = bufferedReader.readLine()) != null) {
	        	   primes.add(line);
	        	   counter++;
	           } 
	           
        	   System.out.println(counter);
        	   Random rand = new Random();
        	   int value = rand.nextInt(counter-1);
	           setFirstPrime(primes.get(value));
	           
	           value = rand.nextInt(counter-1);
	           setSecondPrime(primes.get(value));
	           
	           bufferedReader.close();
	           fileReader.close();
	       }
	       catch(FileNotFoundException ex) {
	           System.out.println(
	               "Unable to open file '" + 
	               fileName + "'");                
	       }
	       catch(IOException ex) {
	           System.out.println(
	               "Error reading file '" 
	               + fileName + "'");                  
	           // Or we could just do this: 
	           ex.printStackTrace();
	       }
	}

	// setter
	public void allowAccess(Client cl)
	{
		client = cl;
		message.setEnabled(true);
		sendButton.setEnabled(true);
		connectButton.setEnabled(false);
		leaveChat.setEnabled(true);
		MessageBox.addMessage("You can send and receive messages now\n");
	}
	
	// setter
	public void appendMessage(String msg)
	{
		MessageBox.addMessage(msg);
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
		//String fileName;
		int secondChoice;
		String message = "Would you like to generate Public/Private Key pair Yourself?\n"
			    + "Press Yes to create prime numbers\n"
			    + "No will generate random prime numbers";
		choice = JOptionPane.showConfirmDialog(null, message, "Generate Key Options", JOptionPane.YES_NO_OPTION);
	        if (choice == JOptionPane.YES_OPTION) {
	        	secondChoice = JOptionPane.showConfirmDialog(null, "Choose your File to load from (YES)\nInput yor own primes(NO)?", "Generate Key Options", JOptionPane.YES_NO_OPTION);
		        if (secondChoice == JOptionPane.YES_OPTION) {
		        	JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // specify current working directory
					int result = fileChooser.showOpenDialog(this);
					if (result == JFileChooser.APPROVE_OPTION) {
					    File selectedFile = fileChooser.getSelectedFile();
					    //fileName = selectedFile.getName(); // store filename 
					    //System.out.println("Selected file: " + fileName  +  ", and path: "+ selectedFile.getAbsolutePath());
					    //System.out.println(selectedFile.getAbsolutePath());
					    readPrimes(selectedFile.getAbsolutePath());
					}
		          return;
		        }
		        else
		        {
		        	setFirstPrime(JOptionPane.showInputDialog(this, "Type in your First Prime Number:"));
		        	setSecondPrime(JOptionPane.showInputDialog(this, "Type in your Second Prime Number:"));
		        }
	        }
	        else {
	        	readPrimes("Resource//primeNumbers.rsc");
	        }
	}
	
	void getConnectionInfo()
	{
		addressInfo.setText(JOptionPane.showInputDialog(this, "Type in IP Address:"));
		portInfo.setText(JOptionPane.showInputDialog(this, "Type in Port:"));
		connectButton.setEnabled(true);
		leaveChat.setEnabled(true);
	}
	
	public void callCloseConnection()
	{
		connectButton.setEnabled(true);
		leaveChat.setEnabled(false);
		client.closeConnection();
	}
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}

