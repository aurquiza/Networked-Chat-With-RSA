package Interfacepkg;

import java.math.BigInteger;
import java.util.Vector;

public class DataChunk
{
	// data that both gets sent to both server and client
	private String sender;
	
	// data that gets sent to the server to process
	private Vector<Vector<BigInteger>> encondedMessages;
	private Vector<String> names;
	
	// data that gets sent to client to process
	private Vector<BigInteger> sentMsg;
	
	public DataChunk(String sender, Vector<Vector<BigInteger>> encondedMessages, Vector<String> names)
	{
		this.sender = sender;
		this.encondedMessages = encondedMessages;
		this.names = names;
		
		sentMsg = null;
	}
	
	public DataChunk(String sender, Vector<BigInteger> sentMsg)
	{
		this.sender = sender;
		this.sentMsg = sentMsg;
	}
	

	public String getSender() 
	{
		return sender;
	}

	public Vector<Vector<BigInteger>> getEncondedMessages() {
		return encondedMessages;
	}
	
	public Vector<String> getNames()
	{
		return names;
	}
	
	public Vector<BigInteger> getSentMsg()
	{
		return sentMsg;
	}

}
