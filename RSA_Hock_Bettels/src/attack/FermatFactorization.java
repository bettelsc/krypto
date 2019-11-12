package attack;
import java.math.BigInteger;
import calc.Calculation;

/**
*@author Niklas Hock
*Klasse, die die Faktorisierung nach Fermat implementiert, die verwendet werden kann, um die RSA-Verschlüsselung zu attackieren.
*
*/
public class FermatFactorization
{
	/**
	*@author Niklas Hock
	*Methode, die P und Q aus N errechnet, indem R = X^2 - N auf Quadriertheit 
	*geprüft wird und dann die Wurzel aus R = Y und X mittels der 3. binomischen Formel den Wert P = X + Y und Q = X - Y ergeben.
	*/
	public BigInteger[] getKeys(BigInteger n)
	{	
		BigInteger x = BigInteger.valueOf((long) Math.sqrt(n.doubleValue()+1));
		BigInteger r = x.multiply(x).subtract(n);
		
		/*Wenn P und Q weit auseinander liegen, kann es sein, dass 20 Iterationen nicht ausreichen,
		 dies soll hiermit die Einschränkung der Faktorisierung darstellen*/
		for(int i = 0; i < 20; i++)
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
		//Die Wurzel aus r ist der gesuchte y-wert
		BigInteger y = BigInteger.valueOf((long) Math.sqrt(r.doubleValue()));
		
		//aus gesuchtem x und y werten werden p und q berechnet.
		BigInteger p = x.add(y);
		BigInteger q = x.subtract(y);
		
		System.out.println("p: " + p + " q: " + q);

		//Errechnen der Schlüssel mit Schlüsselfunktion
		Calculation calc = new Calculation();
		BigInteger[] keys = calc.generateKeys(p, q);
		return keys;
	}
}
