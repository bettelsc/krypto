package io;

import java.util.Scanner;
import java.lang.Math;
import math.Calculation;
import encode.Encode;

public class MainIO {

	private int mFirstValue;
	private int mSecondValue;
	static Calculation calc = new Calculation();
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Geben Sie die erste Zahl ein: ");
		long lFirstValue = sc.nextLong();
		sc.nextLine();
		System.out.println("Geben Sie die zweite Zahl ein: ");
		long lSecondValue = sc.nextLong();
		sc.nextLine();
		
		//MainIO main = new MainIO();
		
//		while(!main.inputValidation(lFirstValue, lSecondValue)) {
//			System.out.println("Versuche es nochmal.");
//			
//			System.out.println("Geben Sie die erste Zahl ein: ");
//			lFirstValue = sc.nextInt();
//			sc.nextLine();
//			
//			System.out.println("Geben Sie die zweite Zahl ein: ");
//			lSecondValue = sc.nextInt();
//			sc.nextLine();
//			
//		}
		
		//Ab hier geht es mit Long nicht mehr.
		if(calc.calculateGGT(lFirstValue, lSecondValue) == (long)1) {
			long n = lFirstValue*lSecondValue;
			System.out.println(calc.calculatePhiFunction(n));
		}
		System.out.println("Geben Sie nun den zu verschlüsselnden Text ein: ");
		String lPlainText = sc.nextLine();
		sc.close();
		
		Encode encoder = new Encode();
		encoder.encodeText(lPlainText);
		
	}
	
	private boolean inputValidation(long aFirstValue, long aSecondValue) {
		
		boolean isValid = false;
		
		if ((Math.pow(2, 32) <= aFirstValue && aFirstValue < Math.pow(2, 64)) 
				&& (Math.pow(2, 32) < aSecondValue && aSecondValue <= Math.pow(2, 64)))
		{	
			if (calc.isPrimeNumber(aFirstValue, aSecondValue))
			{
				isValid = true;
			}
		}
		
		return isValid;	
	}
	
//	private boolean isRelativelyPrime(int aFirstValue, int aSecondValue) {
//		
//		boolean isRelPrime = false;
//		
//		return isRelPrime;
//		
//	}
//	
//	private int calculatePhiFunction(int aFirstValue, int aSecondValue)
//	{
//		int n = aFirstValue * aSecondValue;
//		
//		int phiofN = 0;
//		
//		return phiofN;
//		
//	}

	public int getFirstValue() {
		return mFirstValue;
	}

	public void setFirstValue(int aFirstValue) {
		this.mFirstValue = aFirstValue;
	}

	public int getSecondValue() {
		return mSecondValue;
	}

	public void setSecondValue(int aSecondValue) {
		this.mSecondValue = aSecondValue;
	}
	
}