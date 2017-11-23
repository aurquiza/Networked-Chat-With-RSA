package Networkpkg;

import java.io.*;
import java.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server
{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server(){
//		super("Server");
//		userText = new JTextField();
//		userText.setEditable(false);
//		userText.addActionListener(
//			new ActionListener(){
//				public void actionPerformed(ActionEvent event){
//					sendMessage(event.getActionCommand());
//					userText.setText("");
//				}
//			}
//		);
//		add(userText, BorderLayout.NORTH);
//		chatWindow = new JTextArea();
//		add(new JScrollPane(chatWindow));
//		setSize(300, 150); //Sets the window size
//		setVisible(true);
//		
		startRunning();
	}
	
	public void startRunning(){
		try{
			server = new ServerSocket(1996, 100); 
			while(true){
				try{
					//Trying to connect and have conversation
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException){
					System.out.println("\n Server ended the connection! ");
				} finally{
					closeConnection(); //Changed the name to something more appropriate
				}
			}
		} catch (IOException ioException){
			System.out.println("There is an error with the server");
		}
	}
	//wait for connection, then display connection information
	private void waitForConnection() throws IOException{
		System.out.println(" Waiting for someone to connect... \n");
		
		connection = server.accept();
		System.out.println(" Now connected to " + connection.getInetAddress().getHostName());
		
		
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(connection.getInputStream());
		
		//showMessage("\n Streams are now setup \n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException{
		String message = " You are now connected! ";
		sendMessage(message);
		//ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				System.out.println("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				//showMessage("The user has sent an unknown object!");
				System.out.println("There is an error in while chatting");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	public void closeConnection(){
		//showMessage("\n Closing Connections... \n");
		//ableToType(false);
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			connection.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			System.out.println("There is an error in close connection");
			//ioException.printStackTrace();
		}
	}
	
	//Send a mesage to the client
	private void sendMessage(String message){
		try{
			output.writeObject("SERVER - " + message);
			output.flush();
			System.out.println("\nSERVER -" + message);
		}catch(IOException ioException){
			chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	
//	//update chatWindow
//	private void showMessage(final String text){
//		SwingUtilities.invokeLater(
//			new Runnable(){
//				public void run(){
//					chatWindow.append(text);
//				}
//			}
//		);
//	}
	
//	private void ableToType(final boolean tof){
//		SwingUtilities.invokeLater(
//			new Runnable(){
//				public void run(){
//					userText.setEditable(tof);
//				}
//			}
//		);
//	}
//	
	public static void main(String[] args)
	{
		Server serverTest = new Server();
	
	}
	
	
	
}
