package Networkpkg;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Client extends JFrame
{
	//private JLabel connect;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	
	private String host;
	private String port;
	
	private Socket connection;
	private boolean isConnected = false;

	
		//constructor
		public Client(String host, String port)
		{
			this.host = host;
			this.port = port;
			startRunning();
		}
		
		public boolean checkConnectionStatus()
		{
			return isConnected;
		}
		
		//connect to server
		public void startRunning()
		{
			try
			{
				connectToServer();
				
				if(connection == null)
				{
					System.err.println("connection socket is null!");
					isConnected = false;
					return;
				}
				
				setupStreams();
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
		public void sendMessage(String message)
		{
			try
			{
				output.writeObject("CLIENT - " + message);
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
		
		do{
			try
			{
				message = (String) input.readObject();
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				System.err.println("ClassNotFound Exception triggered...");
			}
			catch(IOException e) 
			{
				System.err.println("IO Exception triggered...");
			}
			
		}while(isConnected);	
	
		closeConnection();
		
	}
	
	
	
}

}
