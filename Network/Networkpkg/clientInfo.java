package Networkpkg;

import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Vector;

//Eric Leon, Alexis Urquiza, Mariia Melnikova 
//This is the clientinfo class that will hold the information of every client that joins the server
public class clientInfo 
{	
	//the clients name and key and the output stream
	public ObjectOutputStream outputClient;
	private NameAndKeyPair nameAndKey;
	
	public clientInfo(ObjectOutputStream outputClient, NameAndKeyPair nameAndKey)
	{
		this.outputClient = outputClient;
		this.nameAndKey = nameAndKey;

		
	}
	
	//return the client's output stream
	public ObjectOutputStream getOBOS() {
		return outputClient;
	}
	
	//return the clients name and key
	public NameAndKeyPair getNameNKey() {
		return nameAndKey;
	}
	
	

}	
