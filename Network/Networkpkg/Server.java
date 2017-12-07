package Networkpkg;

import java.io.*;
import java.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Server extends JFrame
{
	private ServerSocket server;
	private Vector<clientInfo> clientList  = new Vector<clientInfo>();
	private JTextArea history;
	private JLabel IPLabel;
	private JLabel portLabel;
	private String host = null;
	private int port = 0;
	
	
	//constructor
	public Server() 
	{
		
		startRunning();
		Container container  = getContentPane();
		container.setLayout(new FlowLayout());
		
		IPLabel = new JLabel();
		portLabel = new JLabel();
		
		try {
			host = InetAddress.getLocalHost().getHostAddress();
			port = server.getLocalPort();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There was an error with getting the IP address or getting the port...");
		}
		
		IPLabel.setText("Connect to: " + host);
		portLabel.setText("with Port: " + Integer.toString(port));
		
		history  = new JTextArea(10,40);
		history.setEditable(false);
		
		container.add(IPLabel);
		container.add(portLabel);
		container.add(new JScrollPane(history));
		container.setBackground(new Color(204, 255, 245));
		
		setSize(500, 250);
		setVisible(true);
		
		
		
		
		
	}
	
	public void startRunning()
	{
		try
		{

			server = new ServerSocket(0); 
			
			//Trying to connect and have conversation
			 new waitForConnection();
			
		} catch (IOException ioException){
			System.out.println("There is an error with the server");
		}
	}

	
	public void updateUsersOnJoin(NameAndKeyPair newClient)
	{
		
		for(clientInfo currentClient : clientList)
		{
			ObjectOutputStream clientOut = currentClient.getOBOS();
			
			try {
				clientOut.writeObject(newClient);
				//history.append(currentClient.getNameNKey().getName() + " joined the server\n");
			} catch (IOException e) {

				System.err.println("sending new client info failed :c");
			}
		}
	}
	
	public void updateUsersOnLeave(clientInfo leavingClient)
	{
		// make dummy NameAndKey that tell other clients to delete client
		NameAndKeyPair deletePair = new NameAndKeyPair(null, leavingClient.getNameNKey().getName());
		
		// first delete client from the server list
		clientList.remove(leavingClient);
		
		// then send delete to online users
		for(clientInfo clientInf : clientList)
		{
			ObjectOutputStream out = clientInf.getOBOS();
			try {
				out.writeObject(deletePair);
				//history.append(clientInf.getNameNKey().getName() + " left the server\n");
				out.flush();
			} catch (IOException e) {
				System.out.println("error sending delete client info");
			}
			
		}
	}
	
	public void sendClientList(ObjectOutputStream out, String clientName)
	{
		for(clientInfo cInfo : clientList)
		{
			NameAndKeyPair pair = cInfo.getNameNKey();
			if(!clientName.equals(pair.getName()))
			{
				try
				{
					out.writeObject(pair);
					out.flush();
				} 
				catch (IOException e) 
				{
					System.err.println("error trying to update user who joined!");
				}
			}
		}
	}
	
	public void printUsers() {
		
		for(clientInfo client : clientList) {
			NameAndKeyPair temp = client.getNameNKey();
			System.out.println("Client name: " +temp.getName());
		}
		
		
	}
	
	
	public static void main(String[] args)
	{
		Server serverTest = new Server();
	
	}
	
	private class waitForConnection implements Runnable
	{
		
		
		public waitForConnection()
		{
			new Thread(this).start();
		}
		
		public void run() 
		{
			while(true) 
			{
				try 
				{
					new communicationThread(server.accept());
					//printUsers();
				}
				catch(IOException e) 
				{
					System.out.println("There was an error with connecting to another client");
				}
			}
		}
	}
	
	
	
	private class communicationThread implements Runnable
	{
		private Socket connection;
		private boolean b = true;
		
		public communicationThread(Socket newClient) 
		{
			connection = newClient;
			new Thread(this).start();
		}
		
		public void run() 
		{
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			try 
			{

				out = new ObjectOutputStream(connection.getOutputStream());
				in = new ObjectInputStream(connection.getInputStream());
				
				String clientInput;
				NameAndKeyPair clientInformation;
				
				//whenever a client is added to the server we send it to the actual server
				clientInformation = (NameAndKeyPair) in.readObject();
				
				// update the server's list and then update the clients' list
				clientList.addElement(new clientInfo(out, clientInformation));
				history.append(clientInformation.getName() + " joined the server\n");
				updateUsersOnJoin(clientInformation);
				sendClientList(out, clientInformation.getName());
				
				while(b)
				{
					clientInput = (String) in.readObject();
					
					System.out.println("Client: " + clientInput);
				}
				
				out.close();
				in.close();
				connection.close();
			} 
			catch (IOException | ClassNotFoundException e)
			{
				for(clientInfo clientInf : clientList)
				{
					ObjectOutputStream clientOut = clientInf.getOBOS();
					NameAndKeyPair pair = clientInf.getNameNKey();
					if(clientOut.equals(out))
					{
						updateUsersOnLeave(clientInf);
						history.append(clientInf.getNameNKey().getName() + " left the server\n");
						break;
					}
				}
				//System.out.println("Theres an error with the communication thread");
			}

			
		}
		
		
	}
	
	
}