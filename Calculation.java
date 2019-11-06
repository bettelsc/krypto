package math;

import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;

public class Calculation {
	
	public boolean isInRange(long aNumber) {
		
		if ((Math.pow(2, 32) < aNumber) && (aNumber < Math.pow(2, 64))) {
			
			return true;
		}
		return false;
	}
	
	public long calculateGGT(long aFirstNumber, long aSecondNumber)
	{
		if (aFirstNumber == 0) 
		{
			return aSecondNumber;
		}
		return calculateGGT(aSecondNumber % aFirstNumber, aFirstNumber);
	}
	
	public long calculatePhiFunction(long aNumber)
	{
		int lPhi = 1;
		
		for (int it = 2; it < aNumber; it++) {
			
			if (calculateGGT(it, aNumber) == 1) {
				
				lPhi++;
			}
		}
		return lPhi;
	}
	
	private long aPowbModc(long a, long b, long c) 
	{
		long result = 1;
		
		for (int iterator = 0; iterator < b; iterator++)
		{
			result = result * a;
			result = result % c;
		}
		
		return result % c;
	}
	
	private long aMulbModc(long a, long b, long mod)
	{
		return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
	}

	public boolean millerRabinTest(long aNumber, int k)
	{
		long lPrimMinusOne = aNumber - 1;
		while (lPrimMinusOne % 2 == 0) { lPrimMinusOne = lPrimMinusOne/2; }
		
		Random lBase = new Random();
		
		for (int lIterator = 0; lIterator < k; lIterator++) {
			
			System.out.println("TEST");
			
			long lRandValue = Math.abs(lBase.nextLong());
			
			long laValue = lRandValue % (aNumber - 1) + 1, temp = lPrimMinusOne;
			
			long lModValue = aPowbModc(laValue, temp, aNumber);
			
			while (temp != aNumber - 1 && lModValue != 1 && lModValue != aNumber - 1)
			{
				lModValue = aMulbModc(lModValue, lModValue, aNumber);
				temp = temp * 2;
			}
			if (lModValue != aNumber - 1 && temp % 2 == 0) { return false; }
		}
		return true;
	}
	
	public boolean isPrime(long aNumber, int k)
	{
		if (aNumber <= 1)
		{
			return false;	
        } 
		else if (aNumber <= 3)
		{
            return true;
        } 
		else if (aNumber % 2 == 0)
		{
            return false;
        } 
		else
		{
            return millerRabinTest(aNumber, k);
        }
	}
	
	
	public static void main(String[] args)
	{
		Calculation calculation = new Calculation();
		
		int number = 15;
		
		boolean prime = calculation.isPrime(number, 1000);
		
		if (prime)
		{
			System.out.println(number + " is prime");
		}
		else
		{
			System.out.println(number + " is composite");
		}
		
		/*if (calculation.isPrime(number))
		{	
			System.out.println("ITERATIVE: " + number + " ist eine Primzahl");
		}
		else 
		{
			System.out.println("ITERATIVE: " + number + " ist keine Primzahl");
		}
		
		if (calculation.isPrimeNumber(number, 2))
		{
			System.out.println("RECURSIVE: " + number + " ist eine Primzahl");
		}
		else
		{
			System.out.println("RECURSIVE: " + number + " ist keine Primzahl");
		}
		
		System.out.println(calculation.calculateGGT(4, 8));
		System.out.println(calculation.calculatePhiFunction(567890));*/
		
	}
	
	public void generateKeys(int aPrivateKey, int aPublicKey, int aPhiOfN)
	{	
	}
	
	
}















