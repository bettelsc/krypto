package math;

import java.math.BigInteger;
import java.util.Random;

public class Calculation {
	
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);
	private static final BigInteger THREE = BigInteger.valueOf(3);

	public boolean isInRange(BigInteger aNumber)
	{
		if ((TWO.pow(32).compareTo(aNumber) == -1
				&& aNumber.compareTo(TWO.pow(64)) == -1))
		{
			return true;
		}
		return false;
	}

	public BigInteger calculateGGT(BigInteger aFirstNumber, BigInteger aSecondNumber)
	{
		if (aFirstNumber.compareTo(ZERO) == 0)
		{
			return aSecondNumber;
		}
		return calculateGGT(aSecondNumber.mod(aFirstNumber), aFirstNumber);
	}

	public BigInteger PhiforDummies(BigInteger firstValue, BigInteger secondValue)
	{
		BigInteger Phi = firstValue.subtract(ONE).multiply(secondValue.subtract(ONE));
		return Phi;
	}

	private static BigInteger uniformRandom(BigInteger bottom, BigInteger top)
	{
        Random rnd = new Random();
        BigInteger res;
        
        do {
        	
        	res = new BigInteger(top.bitLength(), rnd);
            
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        
        return res;
	}

	// Muss wieder gefixet werden
	public boolean millerRabinTest(BigInteger aNumber, int k)
	{
		BigInteger lPrimMinusOne = aNumber.subtract(ONE);
		BigInteger copy = lPrimMinusOne;
		int end = 0;
		
		while (lPrimMinusOne.mod(TWO).compareTo(ZERO) == 0) 
		{
			end++;
			lPrimMinusOne = lPrimMinusOne.divide(TWO);
		}

		for (int lIterator = 0; lIterator < k; lIterator++)
		{
			BigInteger a = uniformRandom(TWO, copy);
            BigInteger lValue = a.modPow(lPrimMinusOne, aNumber);
			
			if (lValue.equals(ONE) || lValue.equals(copy))
			{
				continue;
			}
			
			int iterator = 1;
			
			for (; iterator < end; iterator++)
			{
				lValue = lValue.modPow(TWO, aNumber);
				
				if (lValue.equals(ONE))
				{
					return false;
				}
				if (lValue.equals(copy))
				{
					break;
				}
			}
			if (iterator == end) {
				
				return false;
			}
		}
		return true;
	}
	
	public boolean isPrime(BigInteger aNumber, int k)
	{
		if (aNumber.compareTo(ONE) <= 0)
		{
			return false;
		}
		else if (aNumber.compareTo(THREE) == 0)
		{
			return true;
		}
		else if (aNumber.mod(TWO).compareTo(ZERO) == 0)
		{
			return false;
		}
		else
		{
			return millerRabinTest(aNumber, k);
		}
	}
	
	public BigInteger[] generateKeys(BigInteger aPrimeNumber, BigInteger anotherPrimeNumber) {

		BigInteger[] lKeys = new BigInteger[3];
		BigInteger lPhiofN = PhiforDummies(aPrimeNumber, anotherPrimeNumber);
		BigInteger lPublicKey = ZERO;
		BigInteger lPrivateKey = ZERO;

		//Es sollte vielleicht anders gefunden werden
		for (BigInteger iterator = TWO; iterator.compareTo(lPhiofN) < 0; iterator = iterator.add(ONE))
		{
			if (calculateGGT(iterator, lPhiofN).compareTo(ONE) == 0) 
			{
				lPublicKey = iterator;
				iterator = lPhiofN;
			}
		}
		BigInteger[] lExtendedEuclideanAlgorithmResult = extendedEuclid(lPublicKey, lPhiofN);
		lPrivateKey = lExtendedEuclideanAlgorithmResult[0];

		lKeys[0] = lPublicKey;
		lKeys[1] = lPrivateKey;
		lKeys[2] = lPhiofN;

		return lKeys;
	}

	/**
	 * Extended Euclidean Algorithm function: a * x + b * y = greatestCommonDivisor(a, b)
	 * @param aNumber = a
	 * @param aPhiofN = b
	 * @return ResultSet: 1st Entry is the Multiplicative Inverse, if the 3rd equals 1,
	 * 		              2nd Entry is a check number, which needs to be 0
	 * 
	 * @author Constantin Bettels
	 */
	public BigInteger[] extendedEuclid(BigInteger aNumber, BigInteger aPhiofN)
	{
		BigInteger x = ZERO;
		BigInteger y = ZERO;
		//Copies for the arithmetical action: xCopy - quotient * x , equivalent for y
		BigInteger xCopy = ONE;
		BigInteger yCopy = ZERO;
		BigInteger phiCopy = aPhiofN;
		
		//Result Array
		BigInteger[] resultSet = new BigInteger[3];
		
		while(!aPhiofN.equals(ZERO))
		{
			//quotient remainder Array storages the remaining values for next loop
			BigInteger[] qRem = aNumber.divideAndRemainder(aPhiofN);
			BigInteger quotient = qRem[0];
			
			//temp-Value to not lose information
			BigInteger temp = aNumber;
			aNumber = aPhiofN;
			aPhiofN = qRem[1];
			
			temp = x;
			x = xCopy.subtract(quotient.multiply(x));
			xCopy = temp;
			
			temp = y;
			y = yCopy.subtract(quotient.multiply(y));
			yCopy = temp;
		}
		
		if (xCopy.compareTo(ZERO) == -1) {
			
			resultSet[0] = xCopy.add(phiCopy);
		}
		else
		{
			resultSet[0] = xCopy;
		}
		
		resultSet[1] = yCopy;
		resultSet[2] = aNumber;
		
		//everything we need from this function to call our inverseFunction
		return resultSet;
	}
	
	/**
	 * Function could easily be integrated in the euclidean algorithm itself, but I thought it would 
	 * be a better style, doing it this way.
	 * @param extendedEuclideanAlgorithmResult: storages every value that is important / maybe will be
	 * @return the multiplicative Inverse of the given number from the euclidean method
	 */
	public BigInteger calculateInverse(BigInteger[] extendedEuclideanAlgorithmResult)
	{
		//if the gcd(a,b) = 1, it returns the inverse
		if (extendedEuclideanAlgorithmResult[2].equals(ONE))
		{
			return extendedEuclideanAlgorithmResult[0];
		}
		//An error message and 0 as the result
		else
		{
			System.out.println("There is no multiplicative Inverse!");
			return ZERO;
		}
	}
	
	// Test-Main
	public static void main(String[] args) 
	{
		Calculation calculation = new Calculation();
		
		BigInteger[] x = calculation.extendedEuclid(THREE, BigInteger.valueOf(26));
		
		BigInteger result = calculation.calculateInverse(x);
		
		System.out.println("Multiplikatives Inverses: " + result);
		
		BigInteger[] keys = calculation.generateKeys(BigInteger.valueOf(3), BigInteger.valueOf(7));
		
		System.out.println("PublicKey: " + keys[0]);
		System.out.println("PrivateKey: " + keys[1]);
		System.out.println("Phi of N: " + keys[2]);
	}
}








