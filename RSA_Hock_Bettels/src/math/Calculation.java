package math;

import java.lang.Math;
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

	public int calculatePhiFunction(BigInteger aNumber) {
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

	// PROBLEM
	private BigInteger aPowbModc(BigInteger a, BigInteger b, BigInteger c) {

		BigInteger result = a;
		result = result.pow(b.intValue());
//		result = result.mod(c);
//		for (BigInteger iterator = b; iterator.compareTo(BigInteger.valueOf(0)) == 0; iterator = iterator.subtract(BigInteger.ONE)) {
//			
//			
//		}
		return result.mod(c);
	}

	private BigInteger aMulbModc(BigInteger a, BigInteger b, BigInteger mod) {
		return a.multiply(b).mod(mod);
	}

	// Muss wieder gefixet werden
	public boolean millerRabinTest(BigInteger aNumber, int k) {
		BigInteger lPrimMinusOne = aNumber.subtract(BigInteger.ONE);
		while (lPrimMinusOne.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
			lPrimMinusOne = lPrimMinusOne.divide(BigInteger.valueOf(2));
		}

		Random lBase = new Random();

		for (int lIterator = 0; lIterator < k; lIterator++) {
			BigInteger lRandValue = BigInteger.valueOf(Math.abs(lBase.nextInt()));

			BigInteger laValue = lRandValue.mod(aNumber).subtract(BigInteger.ONE), temp = lPrimMinusOne;
			
			System.out.println("laValue " + laValue);

			BigInteger lModValue = aPowbModc(laValue, temp, aNumber);
			BigInteger lModValue1 = laValue.modPow(temp, aNumber);
			
			System.out.println("Eigene: " + lModValue + " Vorgegebene: " + lModValue1);

			//PROBLEM
			while (temp.compareTo(aNumber.subtract(BigInteger.ONE)) != 0
					&& lModValue.compareTo(BigInteger.valueOf(1)) != 0
					&& lModValue.compareTo(aNumber.subtract(BigInteger.ONE)) != 0) {
				
				lModValue = aMulbModc(lModValue, lModValue, aNumber);
				temp = temp.multiply(BigInteger.valueOf(2));
			}
			if (lModValue.compareTo(aNumber = aNumber.subtract(BigInteger.ONE)) != 0 && temp.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isPrime(BigInteger aNumber, int k) {
		if (aNumber.compareTo(BigInteger.valueOf(1)) <= 0) {
			
			System.out.println("1");
			
			return false;
		} else if (aNumber.compareTo(BigInteger.valueOf(3)) == 0) {
			
			System.out.println("2");
			
			return true;
		} else if (aNumber.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
			
			System.out.println("3");
			
			return false;
		} else {
			
			System.out.println("MillerRabin wird angewandt");
			
			return millerRabinTest(aNumber, k);
		}
	}

	public BigInteger[] generateKeys(BigInteger aPrimeNumber, BigInteger anotherPrimeNumber) {

		BigInteger[] lKeys = new BigInteger[3];

		BigInteger lPhiofN = PhiforDummies(aPrimeNumber, anotherPrimeNumber);

		int lEnd = lPhiofN.divide(BigInteger.valueOf(2)).intValue();

		BigInteger lPublicKey = BigInteger.ZERO;
		BigInteger lPrivateKey = BigInteger.ZERO;

		for (BigInteger iterator = BigInteger.valueOf(2); iterator.compareTo(lPhiofN) < 0; iterator = iterator.add(BigInteger.ONE)) {
			if (calculateGGT(iterator, lPhiofN).compareTo(BigInteger.ONE) == 0) {
				lPublicKey = iterator;
				iterator = lPhiofN;
			}
		}
		//lPrivateKey = modInverse(lPublicKey, lPhiofN);
		lPrivateKey = lPublicKey.modInverse(lPhiofN);

		lKeys[0] = lPublicKey;
		lKeys[1] = lPrivateKey;
		lKeys[2] = lPhiofN;

		
		return lKeys;
	}

	// Hier soll der erweiterte Euklidische Algorithmus benutzt werden. Ich kack ab:D
	private BigInteger modInverse(BigInteger aNumber, BigInteger aMod) {
		aNumber = aNumber.mod(aMod);
		for (BigInteger iterator = BigInteger.ZERO; iterator.compareTo(aMod) < 0; iterator = iterator.add(BigInteger.ONE)) {
			System.out.println(iterator);
			if (aNumber.multiply(iterator).mod(aMod).compareTo(BigInteger.ONE) == 0) {
				return iterator;
			}
		}
		return BigInteger.ONE;
	}

	// Test-Main
	public static void main(String[] args) {
		Calculation calculation = new Calculation();
//		BigInteger[] keys = calculation.generateKeys(BigInteger.valueOf(11), BigInteger.valueOf(13));
//		System.out.println("e "+keys[0]);
//		System.out.println("d "+keys[1]);
//		System.out.println("phiofN "+keys[2]);
		
		System.out.println(calculation.isPrime(BigInteger.valueOf(11), 10));
	}
}