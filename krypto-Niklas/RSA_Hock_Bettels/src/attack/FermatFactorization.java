package attack;
import java.math.BigInteger;
import calc.Calculation;

public class FermatFactorization
{
	public BigInteger[] getKeys(BigInteger n)
	{	
		BigInteger x = BigInteger.valueOf((long) Math.sqrt(n.doubleValue()+1));
		BigInteger r = x.multiply(x).subtract(n);
		
		for(int i = 0; i < 8; i++)
		{
			double a = Math.sqrt(r.doubleValue());
			a = Math.round(a);
			if(r.compareTo(BigInteger.valueOf((long) (a*a))) == 0)
			{
				break;
			}
			x = x.add(BigInteger.ONE);
			r = x.multiply(x).subtract(n);
		}
		//wurzel aus r ist der gesuchte y-wert
		BigInteger y = BigInteger.valueOf((long) Math.sqrt(r.doubleValue()));
		
		//aus gesuchtem x und y werten werden p und q berechnet.
		BigInteger p = x.add(y);
		BigInteger q = x.subtract(y);
		
		System.out.println("p: " + p + " q: " + q);

		//Errechnen der Schlüssel
		Calculation calc = new Calculation();
		BigInteger[] keys = calc.generateKeys(p, q);
		return keys;
	}
}
