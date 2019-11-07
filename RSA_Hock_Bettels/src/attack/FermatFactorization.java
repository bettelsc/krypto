package attack;
import java.math.BigInteger;
import java.util.Scanner;

import math.Calculation;

public class FermatFactorization {
	
	public BigInteger[] getKeys(BigInteger n) {
		
		//Wurzel aus n als Startwert.
		BigInteger x = sqrt(n);
		System.out.println(x);
		//r wird geprüft, bis es quadratisch ist.
		BigInteger r = BigInteger.ZERO;
		//wurzel aus r ist der gesuchte y-wert
		BigInteger y = sqrt(r);
		
		//aus gesuchtem x und y werten werden p und q berechnet.
		BigInteger p = x.add(y);
		BigInteger q = x.subtract(y);
		Calculation calc = new Calculation();
		BigInteger[] keys = calc.generateKeys(p, q);
		return keys;
		
	}
	
	private BigInteger sqrt(BigInteger n) {
		BigInteger nSqr = BigInteger.ZERO;
		for(BigInteger i = BigInteger.ONE; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
			System.out.println(i);
			if(i.multiply(i).compareTo(n) == 0) {
				nSqr = i;
				break;
			}
		}
		return nSqr;
	}
	
	public static void main(String[] args) {
		FermatFactorization hack = new FermatFactorization();
		Scanner sc = new Scanner(System.in);
		BigInteger b = sc.nextBigInteger();
		System.out.println(hack.sqrt(b));
	}
}
