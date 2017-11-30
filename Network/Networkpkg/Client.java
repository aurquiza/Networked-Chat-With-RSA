package Networkpkg;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Client extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	//private JLabel connect;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private int flag = 0;

	
		//constructor
		public Client(String host){
			super("Client");
			serverIP = host;
			userText = new JTextField();
			userText.setEditable(false);
			userText.addActionListener(
					new ActionListener(){
					public void actionPerformed(ActionEvent event){
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
			);
			add(userText, BorderLayout.NORTH);
			
		     JMenuBar bar = new JMenuBar();
		     setJMenuBar(bar);
		     JMenu FileMenu = new JMenu( "File");
		     
		     JMenuItem connect = new JMenuItem( "Connect" );
		     FileMenu.add(connect);
		     
		     connect.addActionListener(
		    		 	new ActionListener() {
		    		 		public void actionPerformed(ActionEvent event) {
		    		 			startRunning();
		    		 		}
		    		 	
		    		 	
		    		 	}
		    		 );
		     

			
		    bar.add(FileMenu);
			chatWindow = new JTextArea();
			add(new JScrollPane(chatWindow));
			setSize(300, 150); //Sets the window size
			setVisible(true);
			
			//startRunning();
		}
		
		//connect to server
		public void startRunning(){
			try{
				connectToServer();
				setupStreams();
				new whileChatting();
			}catch(EOFException eofException){
				showMessage("\n Client terminated the connection");
			}catch(IOException ioException){
				System.out.print("Error with setup");
				//ioException.printStackTrace();
			}
		}
		
		//connect to server
		private void connectToServer() throws IOException{
			showMessage("Attempting connection... \n");
			connection = new Socket(InetAddress.getByName(serverIP), 1998);
			showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
		}
		
		//set up streams
		private void setupStreams() throws IOException{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			
			input = new ObjectInputStream(connection.getInputStream());
			showMessage("\n The streams are now set up! \n");
		}

		//Close connection
		private void closeConnection(){
			showMessage("\n Closing the connection!");
			ableToType(false);
			try{
				output.close();
				input.close();
				connection.close();
			}catch(IOException ioException){
				//ioException.printStackTrace();
				System.out.println("Error with closing");
			}
		}
		
		//send message to server
		private void sendMessage(String message){
			try{
				output.writeObject("CLIENT - " + message);
				output.flush();
				showMessage("\nCLIENT - " + message);
			}catch(IOException ioException){
				chatWindow.append("\n Oops! Something went wrong!");
			}
		}
		
		//update chat window
		private void showMessage(final String message){
			SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				}
			);
		}
		
		//allows user to type
		private void ableToType(final boolean tof){
			SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
			);
		}
		
		
		public static void main(String[] args)
		{

			String ipAdd = "10.107.214.141";
			
			Client clientTest = new Client(ipAdd);
			clientTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		

private class whileChatting implements Runnable {
	
	public whileChatting() {
		
		new Thread(this).start();
		
	}
	
	public void run() {
		
		ableToType(true);
		
		do{
			try{
				
				message = (String) input.readObject();
				if(message == "END") {
					flag = 1;
				}
				else {
					showMessage("\n" + message);
				}
				
			
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("Unknown data received!");
			}
			catch(IOException e) {
				showMessage("Error IOException");
			}
			
		}while(flag == 0);	
	
		closeConnection();
		
	}
	
	
	
}

}
