package Networkpkg;

import java.io.*;
import java.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Server
{
	private ServerSocket server;
	private Vector<clientInfo> clientList  = new Vector<clientInfo>();
	
	//constructor
	public Server()
	{
		startRunning();
	}
	
	public void startRunning()
	{
		try
		{
			server = new ServerSocket(1998); 
			
			//Trying to connect and have conversation
			 new waitForConnection();
			
		} catch (IOException ioException){
			System.out.println("There is an error with the server");
		}
	}
	
	public void updateUsers(NameAndKeyPair newClient)
	{
		for(clientInfo currentClient : clientList)
		{
			ObjectOutputStream clientOut = currentClient.getOBOS();
			
			try {
				clientOut.writeObject(newClient);
			} catch (IOException e) {

				System.err.println("sending new client info failed :c");
			}
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
					//add client info to the clientList vector;
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
			
			try 
			{

				ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
				
				String clientInput;
				NameAndKeyPair clientInformation;
				clientInformation = (NameAndKeyPair) in.readObject();
				clientList.addElement(new clientInfo(out, clientInformation));
				updateUsers(clientInformation);
				
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
				// TODO Auto-generated catch block
				System.out.println("Theres an error with the communication thread");
			}

			
		}
		
		
	}
	
	
}