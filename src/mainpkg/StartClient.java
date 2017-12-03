package mainpkg;

import Interfacepkg.Chat;
import Securitypkg.RSA;

public class StartClient
{
	public static void main(String[] args)
	{
		Chat.getChatContainer();
		System.out.println("Testing standard out");
		
		RSA sec;// = new RSA(149,157); 
	}
}
