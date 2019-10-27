package math;

import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;


//Braucht ‹berarbeitung.. ich hab was kaputt gemacht und weiﬂ nicht was:(
public class Calculation {
	
	public boolean isInRange(BigInteger aNumber) {
		if ((BigInteger.valueOf(2).pow(32).compareTo(aNumber) == -1 && aNumber.compareTo(BigInteger.valueOf(2).pow(64)) == -1)) {
			return true;
		}
		return false;
	}
	
	public BigInteger calculateGGT(BigInteger aFirstNumber, BigInteger aSecondNumber)
	{
		if (aFirstNumber.compareTo(BigInteger.valueOf(0)) == 0) 
		{
			return aSecondNumber;
		}
		return calculateGGT(aSecondNumber.mod(aFirstNumber), aFirstNumber);
	}
	
	public int calculatePhiFunction(BigInteger aNumber)
	{
		int lPhi = 1;
		
		for (BigInteger it = aNumber; it.compareTo(BigInteger.valueOf(2)) == 1; it = it.subtract(BigInteger.ONE)) {
			System.out.println(it);
			if (calculateGGT(it, aNumber).compareTo(BigInteger.valueOf(1)) == 0) {
				lPhi++;
			}
		}
		return lPhi;
	}
	
	public BigInteger PhiforDummies(BigInteger firstValue, BigInteger secondValue) {
		BigInteger Phi = firstValue.subtract(BigInteger.ONE).multiply(secondValue.subtract(BigInteger.ONE));
		return Phi;
	}
	
	//Hat BigInteger integriert.
	private BigInteger aPowbModc(BigInteger a, BigInteger b, BigInteger c) 
	{
		
		BigInteger result = BigInteger.valueOf(1);
		for (BigInteger iterator = b; iterator.compareTo(BigInteger.valueOf(0)) == 0 ; iterator.subtract(BigInteger.ONE))
		{
			result = result.multiply(a);
			result = result.mod(c);
		}
		return result.mod(c);
	}
	
	private BigInteger aMulbModc(BigInteger a, BigInteger b, BigInteger mod)
	{
		return a.multiply(b).mod(mod);
	}
    //Hier wird es noch falsch gemacht:(
	public boolean millerRabinTest(BigInteger aNumber, int k)
	{
		BigInteger lPrimMinusOne = aNumber.add(BigInteger.valueOf(-1));
		while (lPrimMinusOne.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) { 
			lPrimMinusOne = lPrimMinusOne.divide(BigInteger.valueOf(2));
		}
		
		Random lBase = new Random();

		for (int lIterator = 0; lIterator < k; lIterator++) {
			BigInteger lRandValue =  BigInteger.valueOf(Math.abs(lBase.nextInt()));
			
			BigInteger laValue = lRandValue.mod(aNumber).add(BigInteger.valueOf(-1)), temp = lPrimMinusOne;

			BigInteger lModValue = aPowbModc(laValue, temp, aNumber);
			
			while (temp.compareTo(aNumber.add(BigInteger.valueOf(-1))) != 0 && lModValue.compareTo(BigInteger.valueOf(1)) != 0 && lModValue.compareTo(aNumber.add(BigInteger.valueOf(-1)))!= 0)
			{
				lModValue = aMulbModc(lModValue, lModValue, aNumber);
				temp = temp.multiply(BigInteger.valueOf(2));
			}
			if (lModValue.compareTo(aNumber.add(BigInteger.valueOf(-1))) != 0 && temp.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
				return false; 
			}
		}
		return true;
	}
	
	public boolean isPrime(BigInteger aNumber, int k)
	{
		if (aNumber.compareTo(BigInteger.valueOf(1)) <= 0)
		{
			return false;	
        }
		else if (aNumber.compareTo(BigInteger.valueOf(3)) == 0)
		{
            return true;
        } 
		else if (aNumber.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0)
		{
			return false;
        } 
		else
		{
			return millerRabinTest(aNumber, k);
        }
	}
	
	//Test-Main
	public static void main(String[] args)
	{
		Calculation calculation = new Calculation();
        //Groﬂe Zahlenbereiche, Gibt Primzahlen zwischen 2^32 und 2^64 aus, PrimzahlTest ist aber noch inkorrekt
//		for(BigInteger i = BigInteger.valueOf(2).pow(64); i.compareTo(BigInteger.valueOf(2).pow(32))== 1; i = i.subtract(BigInteger.ONE)  ) {
//			if(calculation.isPrime(i, 10)) {
//				System.out.println("Primzahl " + i);
//			}
//		}
	}
	
	public void generateKeys(int aPrivateKey, int aPublicKey, int aPhiOfN)
	{
		//do somthg
	}
		
}