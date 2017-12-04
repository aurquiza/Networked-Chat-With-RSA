package mainpkg;
import java.math.BigInteger;
import java.util.Vector;

import Interfacepkg.Chat;
import Securitypkg.*;

public class Session 
{

	public static void main(String[] args)
	{
		String msg = "Meet me outside SCE at 10pm. And bring the memes....";
		Vector <BigInteger> block = new Vector<BigInteger>();

		Chat.getChatContainer();
    
		System.out.println("Testing standard out");
		RSA sec = new RSA(Chat.getFirstPrime(), Chat.getSecondPrime()); 
		boolean valid = sec.isInputValid();
		System.out.println("are the primes passed in valid?: " + valid);
		
//		block = sec.encryptM(msg);
//		String newM = sec.decryptM(block);
//		System.out.println(newM);
		
		System.out.println("First: " + Chat.getFirstPrime() + " Second: " + Chat.getSecondPrime());

	}

}
