package UnitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import Securitypkg.*;

public class RSATesting {

	@Test
	public void checkRSA() 
	{
		RSA r = new RSA(11,13);
		int expectedP = 11;
		int expectedQ = 13;
		
		assertEquals(expectedP,r.getPVal());
		assertEquals(expectedQ, r.getQVal());
		assertEquals(143, r.getNVal());
		assertEquals(120, r.getMVal());
		assertEquals(7, r.getE());
		assertEquals(103, r.getDVal());
	}

	@Test
	public void checkEuclidAlgo()
	{
		
		int gcd1 = RSA.getGCD(10, 45);
		int gcd2 = RSA.getGCD(1701,  3768);
		int gcd3 = RSA.getGCD(5, 3);
		int gcd4 = RSA.getGCD(108, 5);

		
		assertEquals(5, gcd1);
		assertEquals(3, gcd2);
		assertEquals(1, gcd3);
		assertEquals(1, gcd4);
	}
	
	@Test
	public void checkGCDAlgo()
	{
		RSA r = new RSA(7,19);
		int e1 = r.getE();
		
		assertEquals(108, r.getMVal());
		assertEquals(5, e1);
		assertEquals(65, r.getDVal());
		//System.err.println("actual value of e " + e1);
	}
}
