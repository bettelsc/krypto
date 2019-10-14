package io;

import java.util.Scanner;
import java.lang.Math;

public class MainIO {

	private int mFirstValue;
	private int mSecondValue;
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Geben Sie die erste Zahl ein: ");
		int lFirstValue = sc.nextInt();
		sc.nextLine();
		System.out.println("Geben Sie die zweite Zahl ein: ");
		int lSecondValue = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Geben Sie nun den zu verschlüsselnden Text ein: ");
		String lPlainText = sc.nextLine();
		
		sc.close();
	}
	
	private boolean inputValidation(int aFirstValue, int aSecondValue) {
		
		boolean isValid = false;
		
		if ((Math.pow(2, 32) < aFirstValue && aFirstValue < Math.pow(2, 64)) 
				&& (Math.pow(2, 32) < aSecondValue && aSecondValue < Math.pow(2, 64)))
		{
			if (isRelativelyPrime(aFirstValue, aSecondValue))
			{
				isValid = true;
			}
		}
		
		return isValid;	
	}
	
	private boolean isRelativelyPrime(int aFirstValue, int aSecondValue) {
		
		boolean isRelPrime = false;
		
		return isRelPrime;
		
	}
	
	private int calculatePhiFunction(int aFirstValue, int aSecondValue)
	{
		int n = aFirstValue * aSecondValue;
		
		int phiofN = 0;
		
		return phiofN;
		
	}

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





















