package math;

//import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;

public class Calculation {

	public boolean isInRange(BigInteger aNumber) {
		if ((BigInteger.valueOf(2).pow(32).compareTo(aNumber) == -1
				&& aNumber.compareTo(BigInteger.valueOf(2).pow(64)) == -1)) {
			return true;
		}
		return false;
	}

	public BigInteger calculateGGT(BigInteger aFirstNumber, BigInteger aSecondNumber) {
		if (aFirstNumber.compareTo(BigInteger.valueOf(0)) == 0) {
			return aSecondNumber;
		}
		return calculateGGT(aSecondNumber.mod(aFirstNumber), aFirstNumber);
	}


	public BigInteger CalculatePhiFunction(BigInteger firstValue, BigInteger secondValue) {
		BigInteger Phi = firstValue.subtract(BigInteger.ONE).multiply(secondValue.subtract(BigInteger.ONE));
		return Phi;
	}


	private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
	}

	public boolean millerRabinTest(BigInteger aNumber, int k)
	{
		BigInteger lPrimMinusOne = aNumber.add(BigInteger.valueOf(-1));
		
		int end = 0;
		
		BigInteger copy = lPrimMinusOne;
		
		while (lPrimMinusOne.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) 
		{
			end++;
			lPrimMinusOne = lPrimMinusOne.divide(BigInteger.valueOf(2));
		}

		for (int lIterator = 0; lIterator < k; lIterator++)
		{
			BigInteger a = uniformRandom(BigInteger.valueOf(2), copy);
            BigInteger lValue = a.modPow(lPrimMinusOne, aNumber);
			
			if (lValue.equals(BigInteger.ONE) || lValue.equals(copy))
			{
				continue;
			}
			
			int iterator = 1;
			
			for (; iterator < end; iterator++)
			{
				lValue = lValue.modPow(BigInteger.valueOf(2), aNumber);
				
				if (lValue.equals(BigInteger.ONE))
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
	public boolean isPrime(BigInteger aNumber, int k) {
		if (aNumber.compareTo(BigInteger.valueOf(1)) <= 0) {
			return false;
		} else if (aNumber.compareTo(BigInteger.valueOf(3)) == 0) {
			return true;
		} else if (aNumber.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
			return false;
		} else {
			return millerRabinTest(aNumber, k);
		}
	}

	public BigInteger[] generateKeys(BigInteger aPrimeNumber, BigInteger anotherPrimeNumber) {

		BigInteger[] lKeys = new BigInteger[3];

		BigInteger lPhiofN = CalculatePhiFunction(aPrimeNumber, anotherPrimeNumber);

		//int lEnd = lPhiofN.divide(BigInteger.valueOf(2)).intValue();

		BigInteger lPublicKey = BigInteger.ZERO;
		BigInteger lPrivateKey = BigInteger.ZERO;

		for (BigInteger iterator = BigInteger.valueOf(2); iterator.compareTo(lPhiofN) < 0; iterator = iterator.add(BigInteger.ONE)) {
			if (calculateGGT(iterator, lPhiofN).compareTo(BigInteger.ONE) == 0) {
				lPublicKey = iterator;
				iterator = lPhiofN;
			}
		}
		lPrivateKey = lPublicKey.modInverse(lPhiofN);

		lKeys[0] = lPublicKey;
		lKeys[1] = lPrivateKey;
		lKeys[2] = lPhiofN;

		
		return lKeys;
	}
//	// Hier soll der erweiterte Euklidische Algorithmus benutzt werden. Ich kack ab:D
//	private BigInteger modInverse(BigInteger aNumber, BigInteger aMod) {
//		aNumber = aNumber.mod(aMod);
//		for (BigInteger iterator = BigInteger.ZERO; iterator.compareTo(aMod) < 0; iterator = iterator.add(BigInteger.ONE)) {
//			System.out.println(iterator);
//			if (aNumber.multiply(iterator).mod(aMod).compareTo(BigInteger.ONE) == 0) {
//				return iterator;
//			}
//		}
//		return BigInteger.ONE;
//	}
}