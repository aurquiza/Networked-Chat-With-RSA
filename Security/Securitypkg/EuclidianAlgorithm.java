/*
 * 
 * This class finds the greatest common divisor using the euclidian algorithm
 * 
 */

package Securitypkg;

class EuclidianAlgorithm 
{
	// instances that are used to compute and
	// store the greatest common divisor
	private int GCD;
	private int prime1;
	private int prime2;
	
	// constructor
	// @Param - prime1 - first number given
	// @Param - prime2 - second number given
	public EuclidianAlgorithm(int prime1, int prime2)
	{
		this.prime1 = prime1;
		this.prime2 = prime2;
	}
	
	// set new numbers for the private instances
	// @Param - num1 - first int passed in
	// @Param - num2 - second int passed in
	public void setNewNumbers(int num1, int num2)
	{
		prime1 = num1;
		prime2 = num2;
	}
	
	// this is the public method that can be called to 
	// compute the gcd. checks which number is bigger and
	// calls the algorithm accordingly
	public void computeGCD()
	{
		if(prime1 > prime2)
			GCD = startAlgorithm(prime1, prime2);
		else
			GCD = startAlgorithm(prime2, prime1);
	}
	
	// getter
	public int getGCD()
	{
		return GCD;
	}
	
	// algorithm that will compute the greatest common divisor and return it
	// @Param - biggest - assumes the biggest integer is passed between the two parameters
	// @Param - smallest - assumes the smallest integer is passed between the two parameters
	private int startAlgorithm(int biggest, int smallest)
	{
		int remainder = biggest % smallest;
		int prev = remainder;
		
		while(remainder != 0)
		{
			prev = remainder;
			
			biggest = smallest;
			smallest = remainder;
			
			remainder = biggest % smallest;
		}
		
		
		return prev;
	}

}
