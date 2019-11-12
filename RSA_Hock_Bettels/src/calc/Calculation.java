package calc;

import java.math.BigInteger;
import java.util.Random;


public class Calculation 
{
	//Makros für die gängisten Werte
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);
	private static final BigInteger THREE = BigInteger.valueOf(3)	;

	/**
	 * @param aNumber: zu prüfende Zahl
	 * @return ob Zahl im gewünschten Bereich ist
	 * @author Niklas Hock
	 */
	public boolean isInRange(BigInteger aNumber)
	{
		if ((TWO.pow(32).compareTo(aNumber) == -1
				&& aNumber.compareTo(TWO.pow(64)) == -1))
		{
			return true;
		}
		return false;
	}

	/**
	 * Berechnet phi-Funktion, wenn die beiden Werte Primzahlen sind.
	 * @param firstValue
	 * @param secondValue
	 * @author Niklas Hock
	 * @return phi
	 */
	public BigInteger CalculatePhiFunction(BigInteger firstValue, BigInteger secondValue)
	{
		BigInteger Phi = firstValue.subtract(ONE).multiply(secondValue.subtract(ONE));
		return Phi;
	}

	/**
	 * Methode für eine zufällige Basis im MillerRabinTest
	 * @param bottom: untere Grenze
	 * @param top: obere Grenze
	 * @return zufällige Zahl
	 */
	private static BigInteger uniformRandom(BigInteger bottom, BigInteger top)
	{
        Random rnd = new Random();
        BigInteger res;
        
        do {
        	
        	res = new BigInteger(top.bitLength(), rnd);
            
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        
        return res;
	}

	/**
	 * Implementierung des Miller-Rabin-Tests
	 * @param aNumber: welche Zahl getestet werden soll
	 * @param k: Anzahl an Iterationen, da der Miller-Rabin nur eine Warscheinlichkeit zurückgibt,
	 * dass es warscheinlich eine Primzahl bzw. zusammengesetzt ist.
	 * @return true, wenn es eine Primzahl ist, false wenn es keine ist.
	 */
	public boolean millerRabinTest(BigInteger aNumber, int k)
	{
		//p-1 für millerRabin
		BigInteger lPrimMinusOne = aNumber.subtract(ONE);
		//"Arbeits-"kopie, damit der ursprungswert einmal gespeichert ist.
		BigInteger copy = lPrimMinusOne;
		int end = 0;
		
		//Zahl wird erstmal wie im MillerRabin gemacht so lange durch 2 geteilt wie es geht
		while (lPrimMinusOne.mod(TWO).compareTo(ZERO) == 0) 
		{
			end++;
			lPrimMinusOne = lPrimMinusOne.divide(TWO);
		}

		//Iteriert k-mal
		for (int lIterator = 0; lIterator < k; lIterator++)
		{
			//zufällige Basis
			BigInteger a = uniformRandom(TWO, copy);
			//modPow funktion von BigInteger
            BigInteger lValue = a.modPow(lPrimMinusOne, aNumber);
			
            //Wenn es es 1 oder -1 ist, weiter
			if (lValue.equals(ONE) || lValue.equals(copy))
			{
				continue;
			}
			
			int iterator = 1;
			
			//so oft wie die Zahl oben durch 2 geteilt wurde
			for (; iterator < end; iterator++)
			{
				lValue = lValue.modPow(TWO, aNumber);
				
				//Kommt hierbei 1 raus, ist es keine Primzahl
				if (lValue.equals(ONE))
				{
					return false;
				}
				if (lValue.equals(copy))
				{
					break;
				}
			}
			//ist aus irgendeinem Grund der Iterator gleich der Schranke sofort false zurück
			if (iterator == end) {
				
				return false;
			}
		}
		//Ist er in der Schleife nicht zwischendurch rausgegangen, ist es eine Primzahl
		return true;
	}
	
	/**
	 * Eigentliche Primzahlüberprüfung ist der Miller-Rabin, einfache Fälle werden allerdings hier schon
	 * abgearbeitet, innendrin wird der MillerRabin ganz normal ausgeführt
	 * @param aNumber: zu prüfende Zahl
	 * @param k: Anzahl Iterationen
	 * @return ob es eine Primzahl ist
	 */
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
	
	/**
	 * Methode zur Generierung der Schlüssel
	 * @param aPrimeNumber: p
	 * @param anotherPrimeNumber: q
	 * @return Array mit den Schlüsseln und phi(n)
	 */
	public BigInteger[] generateKeys(BigInteger aPrimeNumber, BigInteger anotherPrimeNumber) {

		//Array für Werte
		BigInteger[] lKeys = new BigInteger[3];
		//Phi
		BigInteger lPhiofN = CalculatePhiFunction(aPrimeNumber, anotherPrimeNumber);
		BigInteger lPublicKey = ZERO;
		BigInteger lPrivateKey = ZERO;

		//Berechnung des öffentlichen Schlüssels mit ggT(e,phi(n)) = 1
		for (BigInteger iterator = TWO; iterator.compareTo(lPhiofN) < 0; iterator = iterator.add(ONE))
		{
			if ((iterator.gcd(lPhiofN)).compareTo(ONE) == 0) 
			{
				lPublicKey = iterator;
				iterator = lPhiofN;
			}
		}
		
		//Erweiterter euklidischer Algorithmus zur Berechnung des Privaten Schlüssels
		BigInteger[] lExtendedEuclideanAlgorithmResult = extendedEuclid(lPublicKey, lPhiofN);
		BigInteger result = calculateInverse(lExtendedEuclideanAlgorithmResult);
		lPrivateKey = result;

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
		
		//Wenn errechnetes Inverses negativ ist
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
	 * be a better style doing it this way.
	 * @param extendedEuclideanAlgorithmResult: storages every value that is important / maybe will be important
	 * @return returns the multiplicative Inverse of the given number from the euclidean method
	 */
	private BigInteger calculateInverse(BigInteger[] extendedEuclideanAlgorithmResult)
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
	
	/**
	 * 
	 * @return: zufällige Primzahl, die die Beschränkungen
	 */
	public BigInteger generateRandomPrimeNumber()
	{
		//untere Grenze 2^32
		BigInteger lowerBound = new BigInteger("4294967296");
		//obere Grenze 2^64
		BigInteger upperBound = new BigInteger("18446744073709551616");
		
		BigInteger bound = upperBound.subtract(lowerBound);
		
		Calculation calc = new Calculation();
		Random rnd = new Random();
		//maximale Bitlänge für die Zufallszahl
		int lLengthInBit = upperBound.bitLength();
		
		//zufälliger BigInteger
		BigInteger result = new BigInteger(lLengthInBit, rnd);
		
		//Wenn das Ergebnis kleiner als die untere Grenze ist, diese addieren
		if (result.compareTo(lowerBound) < 0)
		{
			result = result.add(lowerBound);
		}
		//Wenn es größer als die Grenze ist, modulo die Grenze und die untere addiert
		if (result.compareTo(bound) >= 0)
		{
			result = result.mod(bound).add(lowerBound);
		}
		
		//Rekursiv solange auführen, wie die erstellte Zahl keine primzahl ist.
		if (calc.isPrime(result, 100))
		{
			return result;
		}
		else
		{
			generateRandomPrimeNumber();
			
		}
		return result;
	}
}



