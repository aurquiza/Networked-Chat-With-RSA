package Networkpkg;

import Interfacepkg.Observer;
import Interfacepkg.Subject;

import java.io.*;
import java.math.BigInteger;
import java.util.Vector;

public class Users implements Observer 
{
	private PrintWriter out;
	private String userName;
	private Vector<BigInteger> publicKey;
	private Subject clientsOnline;
	
	public Users(Subject clientsOnline, PrintWriter out, String userName)
	{
		this.clientsOnline = clientsOnline;
		
	}
	@Override
	public void update()
	{
		// probaly send object using outstream to user letting them know of the update on the client list
		
	}

}
