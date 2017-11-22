package mainpkg;
import Securitypkg.*;
import java.math.BigInteger;

public class Session {

	public static void main(String[] args)
	{
		System.out.println("Testing standard out");
		RSA sec = new RSA(BigInteger.valueOf(149),BigInteger.valueOf(157));
	}

}
