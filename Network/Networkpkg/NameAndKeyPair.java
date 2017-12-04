package Networkpkg;

import java.math.BigInteger;
import java.util.Vector;

public class NameAndKeyPair 
{
	private Vector<BigInteger> publicKey;
	private String name;
	
	public NameAndKeyPair(Vector<BigInteger> pubKey, String name)
	{
		this.name = name;
		this.publicKey = pubKey;
	}
	
	public Vector<BigInteger> getPubKey()
	{
		return publicKey;
	}
	
	public String getName()
	{
		return name;
	}
}
