package io;

import java.util.Scanner;
import java.math.BigInteger;

import math.Calculation;
import encode.Encode;

public class MainIO {

	private BigInteger mFirstValue;
	private BigInteger mSecondValue;
	static Calculation calc = new Calculation();
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Geben Sie die erste Zahl ein: ");
		BigInteger lFirstValue = sc.nextBigInteger();
		sc.nextLine();
		System.out.println("Geben Sie die zweite Zahl ein: ");
		BigInteger lSecondValue = sc.nextBigInteger();
		sc.nextLine();
		
//		while(!calc.isInRange(lFirstValue) && !calc.isInRange(lSecondValue)) {
//			System.out.println("Versuche es nochmal.");
//			
//			System.out.println("Geben Sie die erste Zahl ein: ");
//			lFirstValue = sc.nextBigInteger();
//			sc.nextLine();
//			
//			System.out.println("Geben Sie die zweite Zahl ein: ");
//			lSecondValue = sc.nextBigInteger();
//			sc.nextLine();
//			
//		}
		
		if(calc.calculateGGT(lFirstValue, lSecondValue).compareTo(BigInteger.valueOf(1)) == 0) {

//			BigInteger n = lFirstValue.multiply(lSecondValue);
//			System.out.println(calc.calculatePhiFunction(n));
			
			if(calc.isPrime(lFirstValue, 10) && calc.isPrime(lSecondValue, 10)) {
				BigInteger n = lFirstValue.multiply(lSecondValue);
				BigInteger phiofN = calc.PhiforDummies(lFirstValue, lSecondValue);
				
				System.out.println("Geben Sie nun den zu verschlüsselnden Text ein: ");
				String lPlainText = sc.nextLine();
				
				Encode enc = new Encode();
				BigInteger[] cipher = enc.encodeMessage(lPlainText, BigInteger.valueOf(23), BigInteger.valueOf(143));
				for(int i = 0; i < cipher.length;i++) {
					System.out.print(cipher[i]+" ");
				}
				//Richtige Eingabe-Werte
				//BigInteger[] cipher = enc.encodeMessage(lPlainText, e, n);
			}
		}
		sc.close();
	}
	
	public BigInteger getFirstValue() {
		return mFirstValue;
	}

	public void setFirstValue(BigInteger aFirstValue) {
		this.mFirstValue = aFirstValue;
	}

	public BigInteger getSecondValue() {
		return mSecondValue;
	}

	public void setSecondValue(BigInteger aSecondValue) {
		this.mSecondValue = aSecondValue;
	}
	
}