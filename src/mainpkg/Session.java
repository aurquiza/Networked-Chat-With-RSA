package mainpkg;
import Interfacepkg.Chat;
import Securitypkg.*;

public class Session 
{

	public static void main(String[] args)
	{
		Chat.getChatContainer();
		System.out.println("Testing standard out");
		@SuppressWarnings("unused")
		RSA sec = new RSA(Chat.getFirstPrime(), Chat.getSecondPrime()); 
		System.out.println("First: " + Chat.getFirstPrime() + " Second: " + Chat.getSecondPrime());
	}

}
