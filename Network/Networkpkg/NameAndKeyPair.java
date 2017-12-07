package Networkpkg;


import java.io.*;
import java.math.BigInteger;
import java.util.Vector;
//Eric Leon, Alexis Urquiza, Mariia Melnikova 
//This class is the name and key pair class that will hold the name and key of every client that joins the server
public class NameAndKeyPair implements Serializable
{
	//variables for the public key and name
	private Vector<BigInteger> publicKey;
	private String name;
	
	public NameAndKeyPair(Vector<BigInteger> pubKey, String name)
	{
		this.name = name;
		this.publicKey = pubKey;
	}
	
	//get the clients public key
	public Vector<BigInteger> getPubKey()
	{
		return publicKey;
	}
	
	//get the clients name
	public String getName()
	{
		return name;
	}
}
