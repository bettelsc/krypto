	package encode;

import java.math.BigInteger;

public class Encode
{
	//Vordefiniertes Alphabet.
	private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-,!? ";
	static private char[] alphabetArray = alphabet.toCharArray();
	
	public char[] getAlphabetArray()
	{
		return alphabetArray;
	}
	
	public BigInteger[] encodeMessage(String message, BigInteger e, BigInteger mod)
	{
		BigInteger[] cipher = formatMessagetoInt(message);
		
	    for(int i = 0; i < cipher.length; i++)
	    {
	    	cipher[i] = cipher[i].modPow(e, mod);
	    }
		return cipher;
	}
	
	public String decodeMessage(BigInteger[] cipher, BigInteger d, BigInteger mod)
	{
		for(int i = 0; i < cipher.length; i++)
		{
			cipher[i] = cipher[i].modPow(d, mod);
		}
		return formatMessagetoString(cipher);
	}
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


