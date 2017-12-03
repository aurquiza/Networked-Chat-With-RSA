package mainpkg;
import java.math.BigInteger;
import java.util.Vector;

import Interfacepkg.Chat;
import Securitypkg.*;

public class Session 
{

	public static void main(String[] args)
	{
		String msg = "Meet me outside SCE at 10pm.AB";
		Vector <BigInteger> block = new Vector<BigInteger>();

		Chat.getChatContainer();
    
		System.out.println("Testing standard out");
		RSA sec = new RSA(Chat.getFirstPrime(), Chat.getSecondPrime()); 
		System.out.println("First: " + Chat.getFirstPrime() + " Second: " + Chat.getSecondPrime());

	}

}
