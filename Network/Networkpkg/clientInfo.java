package Networkpkg;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
public class clientInfo {
	
	public String name;
	public InetAddress address;
	public int port;
	public Socket clientSocket;
	public ObjectInputStream inputClient;
	public ObjectOutputStream outputClient;

	
	public clientInfo(String name, InetAddress address, int port, Socket clientSocket, ObjectInputStream inputClient, ObjectOutputStream outputClient) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.clientSocket = clientSocket;
		this.inputClient = inputClient;
		this.outputClient = outputClient;
		
	}
	
	public String getName() {
		return name;
	}
	
	public Socket getSocket() {
		return clientSocket;
	}
	public ObjectInputStream getOBIS() {
		return inputClient;
	}
	public ObjectOutputStream getOBOS() {
		return outputClient;
	}

}	
