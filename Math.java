package math;

public class Math {
	
	
	public int calculateGGT(int aFirstNumber, int aSecondNumber)
	{
		if (aFirstNumber == 0) 
		{
			return aSecondNumber;
		}
		return calculateGGT(aSecondNumber % aFirstNumber, aFirstNumber);
	}
	
	public int calculatePhiFunction(int aNumber)
	{
		int lPhi = 1;
		
		for (int it = 2; it < aNumber; it++) {
			
			if (calculateGGT(it, aNumber) == 1) {
				
				lPhi++;
			}
		}
		return lPhi;
	}
	
	public boolean isPrime(int aNumber)
	{
		boolean lIsPrime = false;
		int lPrimeCounter = 0;
		
		for (int it = 1; it <= (int)(aNumber / 2); it++) 
		{
			if (aNumber % it == 0)
			{
				lPrimeCounter++;
			}
		}
		System.out.println(lPrimeCounter);
		
		if (lPrimeCounter <= 2) 
		{
			lIsPrime = true;
		}
		return lIsPrime;
	}
	
	public boolean isPrimeNumber(int aNumber, int aStartNumber)
	{

		if (aNumber <= 2)
		{
			return (aNumber == 2) ? true : false;
		}
		if (aNumber % aStartNumber == 0) {
			
			return false;
		}
		if (aStartNumber * aStartNumber > aNumber) {
			
			return true;
		}

		return isPrimeNumber(aNumber, aStartNumber + 1);
	}
	
	public static void main(String[] args)
	{
		Math math = new Math();
		
		int number = 429496729;
		
		if (math.isPrime(number))
		{	
			System.out.println("ITERATIVE: " + number + " ist eine Primzahl");
		}
		else 
		{
			System.out.println("ITERATIVE: " + number + " ist keine Primzahl");
		}
		
		if (math.isPrimeNumber(number, 2))
		{
			System.out.println("RECURSIVE: " + number + " ist eine Primzahl");
		}
		else
		{
			System.out.println("RECURSIVE: " + number + " ist keine Primzahl");
		}
		
		System.out.println(math.calculateGGT(49, 2));
		System.out.println(math.calculatePhiFunction(7));
		
	}
	
}
















