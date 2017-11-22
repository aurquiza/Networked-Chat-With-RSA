package mainpkg;
import Securitypkg.*;
import java.math.BigInteger;

public class Session {

	public static void main(String[] args)
	{
		long s = 32416187567L;
		System.out.println("Testing standard out");
		RSA sec = new RSA(BigInteger.valueOf(s),BigInteger.valueOf(32416188223L));
	}

}
