package attack;
import java.math.BigInteger;
import java.util.Scanner;

import math.Calculation;

public class FermatFactorization {
	
	public BigInteger[] getKeys(BigInteger n) {
		
		BigInteger x = sqrt(n);
		BigInteger r = x.multiply(x).subtract(n);
		
		for(int i = 0; i < 8; i++) {
			double a = Math.sqrt(r.doubleValue());
			a = Math.round(a);
			if(r.compareTo(BigInteger.valueOf((long) (a*a))) == 0) {
				System.out.println(r+" "+a);
				break;
			}
			x = x.add(BigInteger.ONE);
			r = x.multiply(x).subtract(n);
		}
//		while(!isSquare(r)) {
//			System.out.println("Hi");
//			x = x.add(BigInteger.ONE);
//			r = x.multiply(x).subtract(n);
//		}
		
		//wurzel aus r ist der gesuchte y-wert
		BigInteger y = BigInteger.valueOf((long) Math.sqrt(r.doubleValue()));
		
		//aus gesuchtem x und y werten werden p und q berechnet.
		BigInteger p = x.add(y);
		BigInteger q = x.subtract(y);
		
		System.out.println("p: " + p + " q: " + q);
		System.out.println(p.multiply(q));
		
		//Errechnen der Schlüssel
		Calculation calc = new Calculation();
		BigInteger[] keys = calc.generateKeys(p, q);
		return keys;
	}
	
	private BigInteger sqrt(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		      BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			  if(mid.multiply(mid).compareTo(n) > 0) {
			    b = mid.subtract(BigInteger.ONE);
			  }
			  else { 
				  a = mid.add(BigInteger.ONE);
			  }
		  }
		  return a;
	}
	
	public static boolean isSquare(BigInteger n) 
	{
		for (BigInteger iterator = BigInteger.ZERO; 
				iterator.compareTo(n.divide(BigInteger.valueOf(2).add(BigInteger.valueOf(2)))) == 1;
				iterator = iterator.add(BigInteger.ONE))
		{
			if (iterator.multiply(iterator).compareTo(n) == 0)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		FermatFactorization hackerConsti = new FermatFactorization();
		Scanner sc = new Scanner(System.in);
		BigInteger b = sc.nextBigInteger();
		sc.close();
		BigInteger[] keys = hackerConsti.getKeys(b);
		System.out.println(keys[0] + " " + keys[1]);
		
	}
}
