package Networkpkg;

import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Vector;

public class clientInfo 
{
	public ObjectOutputStream outputClient;
	private NameAndKeyPair nameAndKey;
	
	public clientInfo(ObjectOutputStream outputClient, NameAndKeyPair nameAndKey)
	{
		this.outputClient = outputClient;
		this.nameAndKey = nameAndKey;

		
	}
	
	public ObjectOutputStream getOBOS() {
		return outputClient;
	}
	
	public NameAndKeyPair getNameNKey() {
		return nameAndKey;
	}
	
	

}	
