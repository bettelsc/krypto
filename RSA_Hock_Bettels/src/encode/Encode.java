	package encode;

import java.math.BigInteger;

/**
*@author Niklas Hock
*Klasse, die die Verschlüsselung der RSA-Verschlüsselungstheorie implementiert.
*/
public class Encode
{
	//Vordefiniertes Alphabet.
	private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-,!? ";
	static private char[] alphabetArray = alphabet.toCharArray();
	
	//Getter für das Alphabet für Aufruf in Menü
	public char[] getAlphabetArray()
	{
		return alphabetArray;
	}
	
	/**
	*@author Niklas Hock
	*Methode, die einen eingegebenen String verschlüsselt,
	*indem er in eine numerische Repräsentation umgewandelt wird und die einzelnen Zahlen dann mit Exponent E modulo N codiert werden.
	*zurückgegeben wird ein Array, was die Chiffre darstellt.
	*/
	public BigInteger[] encodeMessage(String message, BigInteger e, BigInteger mod)
	{
		BigInteger[] cipher = formatMessagetoInt(message);
		
	    for(int i = 0; i < cipher.length; i++)
	    {
	    	cipher[i] = cipher[i].modPow(e, mod);
	    }
		return cipher;
	}
	
	/**
	*@author Niklas Hock
	*Methode, die eine eingegebene Chiffre decodiert, indem die einzelnen Zahlen der Chiffre mit dem Exponenten D modulo N decodiert werden,
	*Danach wird das entschlüsselte cipher-array in einer String-Repräsentation zurückgegeben.
	*/
	public String decodeMessage(BigInteger[] cipher, BigInteger d, BigInteger mod)
	{
		for(int i = 0; i < cipher.length; i++)
		{
			cipher[i] = cipher[i].modPow(d, mod);
		}
		return formatMessagetoString(cipher);
	}
	
	/**
	*@author Niklas Hock
	*Methode, die aus einem String ein Integer-Array erstellt, 
	*indem die Position des Buchstabens im gegebenen Alphabet als die numerische Repräsentation gewählt wird.
	*/
	//Repräsentation der Message als BigInteger-Array
	public BigInteger[] formatMessagetoInt(String message)
	{
		BigInteger[] messageList = new BigInteger[message.length()];

		for(int j = 0; j < message.length(); j++)
		{
			for(int i = 0; i < alphabetArray.length; i++)
			{
				if(message.charAt(j) == alphabetArray[i])
				{
					messageList[j] = BigInteger.valueOf(i);
				}
			}
		}
		return messageList;
	}
	
	/**
	*@author Niklas Hock
	*Methode, die aus einem Integer-Array einen String erstellt, 
	*indem die übereinstimmende Position von Zahl und Alphabet-Position das passende Zeichen bestimmen.
	*Zurückgegeben wird ein String, der mit String.copyValueof(messageArray) erstellt wurde.
	*/
	//Repräsentation der Message als String
	public String formatMessagetoString(BigInteger[] messageList)
	{
		String message = "";
		char[] messageArray = new char[messageList.length];
		
		for(int j = 0; j < messageList.length; j++)
		{
			for(int i = 0; i < alphabetArray.length; i++)
			{
				if(messageList[j].equals(BigInteger.valueOf(i)))
				{
					messageArray[j] = alphabetArray[i];
				}
			}
		}
		message = String.copyValueOf(messageArray);
		return message;
	}
}


