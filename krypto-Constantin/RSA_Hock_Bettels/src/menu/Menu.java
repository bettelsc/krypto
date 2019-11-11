package menu;
import java.math.BigInteger;
import java.util.Scanner;

import math.Calculation;
import encode.Encode;
import file.CFile;
import attack.FermatFactorization;

public class Menu {
	
	BigInteger lFirstValue;
	BigInteger lSecondValue;
	String plaintext;
	BigInteger n;
	BigInteger phiofN;
	BigInteger e;
	BigInteger d;
	BigInteger dguess;
	BigInteger[] cipher = {BigInteger.ONE};
	boolean running = true;
	String option;
	String cipherString;
	Encode enc = new Encode();
	FermatFactorization hackerConsti = new FermatFactorization();
	Calculation calc = new Calculation();
	Scanner sc = new Scanner(System.in);
	CFile file = new CFile();
	
	private void utilityForCase(String optionInCase)
	{
		System.out.println("--------------------------------------------------------------------------------------------------------");
		System.out.println("Um wieder zum Men� zu gelangen 'menu' eintragen, eine beliebige andere Taste um das Programm zu beenden:");
		optionInCase = sc.next();
		openGenMenu(optionInCase);
	}
	
	private void openGenMenu(String option)
	{
		switch (option)
		{
		case "menu":
			openMenu();
			break;
		default:
			break;
		}
	}
	private boolean openMenu()
	{
		System.out.println("1. Zeige p an: -p");
		System.out.println("2. Zeige q an: -q");
		System.out.println("3. Zeige phi(n) an: -phi");
		System.out.println("4. Zeige e an: -e");
		System.out.println("5. Zeige d an: -d");
		System.out.println("6. Zeige den Klartext an: -plain");
		System.out.println("7. Zeige den Chiffretext an: -chiff");
		System.out.println("8. Verschl�ssele deine Nachricht (neuer Text): -e");
		System.out.println("9. Entschl�ssele die Chiffre (wenn vorher neu verschl�sselt wurde mit neuem Text und Schl�ssel): -d");
		System.out.println("10. Attackiere die Chiffre mittels Fermat-Faktorisierung: -a");
		System.out.println("11. Exit: -exit");
		
		System.out.println("Geben Sie einen Befehl ein: ");
		String option = sc.next();
		String optionInCase = "";
		
		switch (option)
		{
			case "-p":
				System.out.println("Der Wert f�r p ist: " + lFirstValue);
				utilityForCase(optionInCase);
				break;
			case "-q":
				System.out.println("Der Wert f�r q ist: " + lSecondValue);
				utilityForCase(optionInCase);
				break;
			case "-phi":
				System.out.println("Der Wert f�r phi(n) ist: " + phiofN);
				utilityForCase(optionInCase);
				break;
			case "-e":
				System.out.println("Der Wert f�r e ist: " + e);
				utilityForCase(optionInCase);
				break;
			case "-d":
				System.out.println("Der Wert f�r d ist: " + d);
				utilityForCase(optionInCase);
				break;
			case "-plain":
				System.out.println("Der Klartext ist: " + plaintext);
				utilityForCase(optionInCase);
				break;
			case "-chiff":
				System.out.println("Der Chiffretext ist: " + cipherString);
				utilityForCase(optionInCase);
				break;
			case "-enc":
				System.out.print("Geben Sie nun einen Text f�r die Verschl�sselung ein: ");
				plaintext = sc.nextLine();
				cipher = enc.encodeMessage(plaintext, e, n);
				for(int i = 0; i < cipher.length;i++)
				{
					System.out.print(cipher[i]+" ");
				}
				utilityForCase(optionInCase);
				break;
			case "-dec":
				d = calc.generateKeys(lFirstValue, lSecondValue)[1];
				String decryptedMessage = enc.decodeMessage(cipher, d, n);
				System.out.println("Entschl�sselt: " + decryptedMessage);
				utilityForCase(optionInCase);
				break;
			case "-a":
				BigInteger[] keys = hackerConsti.getKeys(n);
				System.out.println("Schl�ssel d: " + keys[0] + " Schl�ssel e: " + keys[1]);
				utilityForCase(optionInCase);
				break;
			case "-exit":
				running = false;
				break;
			default:
				System.out.println("Kein Befehl erkannt!");
				break;
			}
		return running;		
	}
	
	public void getInput() {
		
		System.out.println("-----------------------------------------------------------\n"
						 + "RSA-VERSCHL�SSELUNG: \n"
						 + "Ablauf des Algorithmus wie in der Spezifikation gefordert.\n"
						 + "-----------------------------------------------------------");
		
		int attempts = 0;
		//4295589499 und 4295589509
		//Eingabe von p und q
		do {
			
			if (attempts > 0)
			{
			System.out.println("Versuche es nochmal, die eigebenen Zahlen sind entweder zu klein oder zu gro�. \n");
			}
			System.out.println("Geben Sie die erste Zahl ein: ");
			lFirstValue = sc.nextBigInteger();
			sc.nextLine();
			
			System.out.println("Geben Sie die zweite Zahl ein: ");
			lSecondValue = sc.nextBigInteger();
			sc.nextLine();
			
			attempts++;
			
		} while((!calc.isInRange(lFirstValue) && !calc.isInRange(lSecondValue)) && (!calc.isPrime(lFirstValue, 100)
				&& !calc.isPrime(lSecondValue, 100)));
		
		System.out.println("Werte (p, q) sind:" + "(" + lFirstValue + ", " + lSecondValue + ")");
		System.out.println("-------------------------------------------------------------------");
		//Input Plaintext
		System.out.println("Geben Sie den zu verschl�sselnden Text ein: ");
		plaintext = sc.next();
		System.out.println("Klartext ist: " + plaintext);
		//Berechnen von phi(n)
		phiofN = calc.CalculatePhiFunction(lFirstValue, lSecondValue);
		n = lFirstValue.multiply(lSecondValue);
		file.write("Phi von " + n + ": " + phiofN , "phi.txt");
		
		//Eingabe von �ffentlichem Schl�ssel
		System.out.println("Gib einen Schl�ssel e an, der zwischen 1 und " 
		+  phiofN
		+ " liegt und zu " + phiofN + " teilerfremd ist: ");
		e = sc.nextBigInteger();
		
		//Pr�fung von e, aber ohne Konsequenz
		if((e.gcd(phiofN)).compareTo(BigInteger.valueOf(1)) != 0)
		{
			e = calc.generateKeys(lFirstValue, lSecondValue)[0];
		}
		
		d = calc.generateKeys(lFirstValue, lSecondValue)[1];
		
		file.write("Public Key: " + e + "\n" + "Private Key: " + d + "\n", "keys.txt");
		
		BigInteger[] cipher = enc.encodeMessage(plaintext, e, n);
		
		BigInteger[] testValue = cipher.clone();
		
		for(int i = 0; i < testValue.length; i++)
		{
			testValue[i] = testValue[i].mod(BigInteger.valueOf(enc.getAlphabetArray().length));
		}
		cipherString = enc.formatMessagetoString(testValue);
		
		System.out.println("Ciphertext ist: " + cipherString);
		
		System.out.println("------------------------------------------");
		System.out.println("Gib den privaten Schl�ssel d ein, um die nachricht wieder zu entschl�sseln: ");
		
		dguess = sc.nextBigInteger();
		
		if (dguess.equals(d))
		{
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println("Der Klartext ist: " + enc.decodeMessage(cipher, d, n));
		}
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("Die Verschl�sselung wurde einmal wie gew�nscht durchgef�hrt.");
		System.out.println("------------------------------------------------------------------------------------------");

		System.out.println("Sie haben jetzt die M�glichkeit, dass Programm zu beenden oder \n"
				+ "zu einem Men� zu gelangen, in dem Sie sich s�mtliche Werte noch einmal anzeigen lassen k�nnen \n"
				+ "und einen Angriff auf die Verschl�sselung mit den gespeicherten Werten starten k�nnen. \n"
				+ "Um zu dem Menu zu gelangen 'menu' eingeben, um das Programm zu beenden eine beliebige Taste dr�cken.");
		
		option = sc.next();
		
		openGenMenu(option);
	}
}



