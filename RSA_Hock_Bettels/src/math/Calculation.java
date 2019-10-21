package math;

import java.util.Random;
import java.lang.Math;

public class Calculation {
	
	//Fertig.
	public long calculateGGT(long aFirstNumber, long aSecondNumber)
	{
		if (aSecondNumber == 0) 
		{
			return aFirstNumber;
		}
		return calculateGGT(aSecondNumber, aFirstNumber % aSecondNumber);
	}
	//Fertig.
	public long calculatePhiFunction(long aNumber)
	{
		long lPhi = 1;
		
		for (long it = 2; it < aNumber; it++) {
			
			if (calculateGGT(it, aNumber) == 1) {
				
				lPhi++;
			}
		}
		return lPhi;
	}
	
	public boolean isPrimNumber(int number) {
		boolean isPrim = false;
		
		if(number % 2 != 0 || number == 2) {
			int p = number-1;
			Random rand = new Random();
			while(p % 2 == 0) {
				p = p/2;
			}
			int random = rand.nextInt(number) + 2;
			System.out.println("Random "+random);
			while(Math.pow(random, p) % number != p || Math.pow(random, p) % number != 1) {
				if(p != number-1) {
					p*=2;
					
				}
				
				if(p == number-1) {
					break;
				}
			}
			isPrim = true;
		}
		
		return isPrim;
	}
	
//	//Weniger geil
//	public boolean isPrime(int aNumber)
//	{
//		boolean lIsPrime = false;
//		int lPrimeCounter = 0;
//		
//		for (int it = 1; it <= (int)(aNumber / 2); it++) 
//		{
//			if (aNumber % it == 0)
//			{
//				lPrimeCounter++;
//			}
//		}
//		System.out.println(lPrimeCounter);
//		
//		if (lPrimeCounter <= 2) 
//		{
//			lIsPrime = true;
//		}
//		return lIsPrime;
//	}
//	
//	//Geiler Dorsch.
//	public boolean isPrimeNumber(long aNumber, long aStartNumber)
//	{
//
//		if (aNumber <= 2)
//		{
//			return (aNumber == 2) ? true : false;
//		}
//		if (aNumber % aStartNumber == 0) {
//			
//			return false;
//		}
//		if (aStartNumber * aStartNumber > aNumber) {
//			
//			return true;
//		}
//
//		return isPrimeNumber(aNumber, aStartNumber + 1);
//	}
	public static void main(String[] args) {
		Calculation calc = new Calculation();
		for(int i = 2; i <= 100; i++) {
			if(calc.isPrimNumber(i)) {
				System.out.println(i + " ist eine Primzahl.");
			}
		}
		
	}
}