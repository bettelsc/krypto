package menu;
import java.math.BigInteger;
import java.util.Scanner;

import math.Calculation;
import encode.Encode;
import file.CFile;
import attack.FermatFactorization;
//import file.File;

public class Menu {
	
	BigInteger lFirstValue;
	BigInteger lSecondValue;
	BigInteger n;
	BigInteger phiofN;
	BigInteger e;
	BigInteger[] cipher = {BigInteger.ONE};
	Calculation calc = new Calculation();
	Encode enc = new Encode();
	FermatFactorization hackerConsti = new FermatFactorization();
	boolean running = true;
	Scanner sc = new Scanner(System.in);
	CFile file = new CFile();
	
	public boolean openMenu()
	{
		System.out.println("1. Zeige p an: -p");
		System.out.println("2. Zeige q an: -q");
		System.out.println("3. Zeige phi(n) an: -pn");
		System.out.println("4. Verschlüssele deine Nachricht: -e");
		System.out.println("5. Entschlüssele die Chiffre: -d");
		System.out.println("6. Attackiere die Chiffre mittels Fermat-Faktorisierung: -a");
		System.out.println("7. Exit: -exit");
		
		System.out.println("Geben Sie einen Befehl ein: ");
		String option = sc.nextLine();
		
		switch (option)
		{
			case "-p":
				System.out.println(lFirstValue);
				break;
			case "-q":
				System.out.println(lSecondValue);
				break;
			case "-pn":
				System.out.println(phiofN);
				break;
			case "-e":
				System.out.print("Geben Sie nun einen Text für die Verschlüsselung ein: ");
				String lPlainText = sc.nextLine();
				cipher = enc.encodeMessage(lPlainText, e, n);
				for(int i = 0; i < cipher.length;i++)
				{
					System.out.print(cipher[i]+" ");
				}
				break;
			case "-d":
				BigInteger d = e.modInverse(phiofN);
				String decryptedMessage = enc.decodeMessage(cipher, d, n);
				System.out.println("Entschlüsselt: " + decryptedMessage);
				break;
			case "-a":
				BigInteger[] keys = hackerConsti.getKeys(n);
				System.out.println("Schlüssel e: " + keys[0] + " Schlüssel d: " + keys[1]);
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
		
		//Eingabe von p und q
		
		//4295589499 und 4295589509
		System.out.println("Geben Sie die erste Zahl ein: ");
		lFirstValue = sc.nextBigInteger();
		sc.nextLine();
		System.out.println("Geben Sie die zweite Zahl ein: ");
		lSecondValue = sc.nextBigInteger();
		sc.nextLine();
		
		//Primzahlüberprüfung(muss noch erweitert werden)
		while(!calc.isPrime(lFirstValue, 100) && !calc.isPrime(lSecondValue, 100) && lFirstValue.gcd(lSecondValue).compareTo(BigInteger.ONE) != 0) {
			System.out.println("Ihre Eingabe war leider nicht korrekt, sollen 2 "
					+ "zufällige Primzahlen generiert werden?: y/n");
			String option = sc.next();
			switch (option) {
			case "y":
				System.out.println("Passende Werte werden generiert...");
				//Generiere zufällige Zahl, die passend ist.
				lFirstValue = null;
				lSecondValue = null;
				break;
			case "n":
				System.out.println("Dann geben sie p und q bitte erneut ein:");
				System.out.println("Geben Sie die erste Zahl ein: ");
				lFirstValue = sc.nextBigInteger();
				sc.nextLine();
				System.out.println("Geben Sie die zweite Zahl ein: ");
				lSecondValue = sc.nextBigInteger();
				sc.nextLine();
				break;
			default:
				System.out.println("Der Befehl wurde nicht erkannt! Versuchen sie es erneut.");
				break;
			}
		}
		
		//Test, ob Zahlen in richtigem Wertebereich
		while(!calc.isInRange(lFirstValue) && !calc.isInRange(lSecondValue)) 
		{
			System.out.println("Versuche es nochmal, die eigebenen Zahlen sind entweder zu klein oder zu groß. \n");
			
			System.out.println("Geben Sie die erste Zahl ein: ");
			lFirstValue = sc.nextBigInteger();
			sc.nextLine();
			
			System.out.println("Geben Sie die zweite Zahl ein: ");
			lSecondValue = sc.nextBigInteger();
			sc.nextLine();
		}
		//Berechnen von ph(n)
		phiofN = calc.CalculatePhiFunction(lFirstValue, lSecondValue);
		n = lFirstValue.multiply(lSecondValue);
		file.write("Phi von " + n + ": " + phiofN , "phi.txt");
		
		//Eingabe von öffentlichem Schlüssel
		System.out.println("Gib einen Schlüssel e an, der zwischen 1 und " 
		+  phiofN
		+ " liegt und zu " + phiofN + " teilerfremd ist: ");
		e = sc.nextBigInteger();
		
		//Prüfung von e, aber ohne Konsequenz
		if((e.gcd(phiofN)).compareTo(BigInteger.valueOf(1)) != 0)
		{
			System.out.println("Der eingebene Schlüssel ist nicht geeignet, soll ein passender Schlüssel generiert werden? y/n");
			String option = sc.next();
			switch (option) {
			case "y":
				System.out.println("Passender Wert wird generiert...");
				//Generiere zufällige Zahl, die passend ist.
				Calculation calc = new Calculation();
				e = calc.generateKeys(lFirstValue, lSecondValue)[0];
				break;
			case "n":
				System.out.println("Dann geben sie e bitte erneut ein:");
				e = sc.nextBigInteger();
				sc.nextLine();
				break;
			default:
				System.out.println("Der Befehl wurde nicht erkannt! Versuchen sie es erneut.");
				break;
			}
			
		}
		
	}
}
