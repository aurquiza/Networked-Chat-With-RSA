 /* Chat class contains the Frame for all UI elements
 * and implements a Singleton design pattern
 * since we'll only need one instance of the
 * "parent" container for each client
 */

package Interfacepkg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	  private JLabel serverAddressPrompt;
	  private JLabel serverPortPrompt;
	  private JTextField addressInfo;
	  private JTextField portInfo;
	  private JTextField message;
	  private JTextField clientName;
	  //private JLabel status;


	  // Network Items
	  private boolean running;
	  private boolean connected;
	  private boolean serverContinue;
	  private ServerSocket serverSocket;
	  private Socket commSocket;
	  private PrintWriter out;
	  private BufferedReader in;
	  
	  BigInteger firstPrime;
	  BigInteger secondPrime;
	
	private Chat() {

	      // Default Constructor only exists to defeat instantiation.
		connected = false;
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
		JButton leaveChat = new JButton("Leave Chat");
		serverAddressPrompt = new JLabel(" Server Address");
		serverPortPrompt = new JLabel(" Server Port");
		addressInfo = new JTextField();
		portInfo = new JTextField();
		clientName.setText(JOptionPane.showInputDialog(this, "Type in your Name:"));
		clientName.setEnabled (false);
		connectionPanel.add(createName);
		connectionPanel.add(clientName);
		connectionPanel.add(connectButton);
		connectionPanel.add(leaveChat);	
		connectionPanel.add(serverAddressPrompt);
		connectionPanel.add(addressInfo);
		connectionPanel.add(serverPortPrompt);
		connectionPanel.add(portInfo);	
		connectionPanel.setBackground(new Color(204, 255, 245));
		
		left.add(connectionPanel, BorderLayout.NORTH);
		
		MessageBox mb = new MessageBox(); // message history
		left.add(mb, BorderLayout.CENTER);
		
		JPanel messagePart = new JPanel();
		message = new JTextField(25);
		message.setEnabled (false);
		message.addActionListener( this );
		sendButton = new JButton("Send");
		sendButton.addActionListener( this );
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

	
	public JTextField getNameText()
	{
		return clientName;
	}
	public static Chat getChatContainer() {

	      if(CONTAINER == null) {
	         CONTAINER = new Chat();
	      }

	      return CONTAINER;
	   }
	// first and second prime numbers
	// are generated from file or from user input
	public BigInteger getFirstPrime()
	{
		return this.firstPrime;
	}
	
	public BigInteger getSecondPrime()
	{
		return this.secondPrime;
	}
	
	private void setFirstPrime(String num)
	{

		this.firstPrime = new BigInteger(num);
	}
	
	private void setSecondPrime(String num)
	{
		this.secondPrime = new BigInteger(num);
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
	
	void initiateOption() {
		
		int choice;
		String fileName;
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
					    fileName = selectedFile.getName(); // store filename 
					    System.out.println("Selected file: " + fileName  +  ", and path: "+ selectedFile.getAbsolutePath());
					    System.out.println(selectedFile.getAbsolutePath());
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
	        	readPrimes("Resource\\primeNumbers.rsc");
	        }
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

