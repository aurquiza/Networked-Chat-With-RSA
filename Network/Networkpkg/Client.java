package Networkpkg;
import Interfacepkg.Chat;
import Interfacepkg.DataChunk;
import Securitypkg.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Eric Leon, Alexis Urquiza, Mariia Melnikova 
//This class is for client and will connect the client to the server and be able to send messages between 
//other clients as well.
public class Client extends JFrame
{
	
	//set up output and input streams
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	//set up the host and port
	private String host;
	private String port;
	
	//setuo the socket, instance of GUI, and boolean to see if it is connected
	private Socket connection;
	private boolean isConnected = false;
	private Chat gui;
	
		//constructor
		public Client(String host, String port, Chat gui)
		{
			this.host = host;
			this.port = port;
			this.gui = gui;
			
			//start running the client code
			startRunning();
		}
		
		//checks the connection status
		public boolean checkConnectionStatus()
		{
			return isConnected;
		}
		
		//connect to server
		public void startRunning()
		{
			//try and catch to connect to the server and start running
			try
			{	
				//connect server
				connectToServer();
				
				if(connection == null)
				{
					System.err.println("connection socket is null!");
					isConnected = false;
					return;
				}
				//set up streams
				setupStreams();
				
				// send object which has user's information
				RSA clientRSA = gui.getRSA();
				String clientName = gui.getActualName();
				NameAndKeyPair clientNameNKey = new NameAndKeyPair(clientRSA.getPubKey(), clientName);
				
				output.writeObject(clientNameNKey);
				output.flush();
				
				//keep running while chatting
				new whileChatting();
			}
			catch(EOFException eofException)
			{
				System.err.println("Client terminated the connection");
			}
			catch(IOException ioException)
			{
				System.err.print("Error with setup");
				isConnected = false;
			}
		}
		
		//connect to server
		private void connectToServer() throws IOException
		{
			try 
			{
				System.out.println("Inside Client: " + port + " " + host);
				connection = new Socket(host, Integer.parseInt(port));
				isConnected = true;
				

				
			}
			catch (NumberFormatException e)
			{

				System.err.println("NumberFormatException Caught!");
			}
		}
		
		//set up streams
		private void setupStreams() throws IOException
		{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(connection.getInputStream());
		}

		//Close connection
		public void closeConnection()
		{
			try
			{
				output.close();
				input.close();
				connection.close();
			}
			catch(IOException ioException)
			{

				System.err.println("Error with closing");
			}
		}
		
		//send message to server
		public void sendMessage(DataChunk chunkOfData)
		{
			try
			{
				output.writeObject(chunkOfData);
				output.flush();
			}
			catch(IOException ioException)
			{
				System.err.println("Client failed sending the message");
			}
		}
		

private class whileChatting implements Runnable
{
	
	public whileChatting() 
	{
		new Thread(this).start();
	}

	public void run() 
	{
		//client will be sending messages here
		do {
			try
			{
				
				Object o = input.readObject();
				
				if(o instanceof NameAndKeyPair)
				{
					NameAndKeyPair sentData = (NameAndKeyPair)o;
					gui.updateClientList(sentData);
				}
				else
				{
					DataChunk message = (DataChunk) o;
					if(message == null)
						System.err.println("Oh no! client recieved null dataChunk!");
					else
					{
						System.out.println("Recieved message from: " + message.getSender());
						gui.appendMessage(message);
					}
				}
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				System.err.println("ClassNotFound Exception triggered...");
			}
			catch(IOException e) 
			{	
				//System.err.println("IO Exception triggered...");
				isConnected = false;
			}
			
		}while(isConnected);
	
		closeConnection();
		
	}
}

}
