package Networkpkg;

import java.io.*;
import java.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server
{
	private ServerSocket server;
	
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

	//get stream to send and receive data
//	private void setupStreams() throws IOException{
//		output = new ObjectOutputStream(connection.getOutputStream());
//		
//		
//		
//		//showMessage("\n Streams are now setup \n");
//	}
	
//	//during the chat conversation
//	private void whileChatting() throws IOException{
//		String message = " You are now connected! ";
//		sendMessage(message);
//		//ableToType(true);
//		do{
//			try{
////				message = (String) input.readObject();
//				System.out.println("\n" + message);
//			}catch(ClassNotFoundException classNotFoundException){
//				//showMessage("The user has sent an unknown object!");
//				System.out.println("There is an error in while chatting");
//			}
//		}while(!message.equals("CLIENT - END"));
//	}
	
//	public void closeConnection(){
//		//showMessage("\n Closing Connections... \n");
//		//ableToType(false);
//		try{
//			output.close(); //Closes the output path to the client
//			input.close(); //Closes the input path to the server, from the client.
//			connection.close(); //Closes the connection between you can the client
//		}catch(IOException ioException){
//			System.out.println("There is an error in close connection");
//			//ioException.printStackTrace();
//		}
//	}
	
//	//Send a mesage to the client
//	private void sendMessage(String message){
//		try{
//			output.writeObject("SERVER - " + message);
//			output.flush();
//			System.out.println("\nSERVER -" + message);
//		}catch(IOException ioException){
//			chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
//		}
//	}

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
				//PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
				//BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
				
				String clientInput;
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