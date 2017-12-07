package Networkpkg;

import java.io.*;
import java.net.*;

import javax.swing.*;

import Interfacepkg.DataChunk;

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
			server = new ServerSocket(1997); 
			
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
				
				DataChunk clientInput;
				NameAndKeyPair clientInformation;
				
				//whenever a client is added to the server we send it to the actual server
				clientInformation = (NameAndKeyPair) in.readObject();
				
				// update the server's list and then update the clients' list
				clientList.addElement(new clientInfo(out, clientInformation));
				updateUsersOnJoin(clientInformation);
				sendClientList(out, clientInformation.getName());
				
				while(b)
				{
					clientInput = (DataChunk) in.readObject();
					
					NameAndKeyPair sendTo = clientInput.getNames();
					String userName = sendTo.getName();
					for(clientInfo inf : clientList)
					{
						if(userName.equals(inf.getNameNKey().getName()))
						{
							ObjectOutputStream sendeeOut = inf.getOBOS();
							sendeeOut.writeObject(clientInput);
						}
					}
					//System.out.println("Client: " + clientInput);
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
						break;
					}
				}
				//System.out.println("Theres an error with the communication thread");
			}

			
		}
		
		
	}
	
	
}