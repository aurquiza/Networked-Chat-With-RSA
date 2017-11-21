package mainpkg;
import Interfacepkg.Chat;
import Securitypkg.*;

public class Session {

	public static void main(String[] args)
	{
		Chat.getChatContainer();
		System.out.println("Testing standard out");
		RSA sec = new RSA(149,157);
	}

}
