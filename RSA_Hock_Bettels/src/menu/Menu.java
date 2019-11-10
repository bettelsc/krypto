package menu;
import java.math.BigInteger;
import java.util.Scanner;

import math.Calculation;
import encode.Encode;
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
	
	public boolean openMenu() {
		System.out.println("1. Zeige p an: -p");
		System.out.println("2. Zeige q an: -q");
		System.out.println("3. Zeige phi(n) an: -pn");
		System.out.println("4. Verschlüssele deine Nachricht: -e");
		System.out.println("5. Entschlüssele die Chiffre: -d");
		System.out.println("6. Attackiere die Chiffre mittels Fermat-Faktorisierung: -a");
		System.out.println("7. Exit: -exit");
		
		
		System.out.println("Geben Sie einen Befehl ein: ");
		String option = sc.next();
		
		
		switch (option) {
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
			for(int i = 0; i < cipher.length;i++) {
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
			System.out.println("Schlüssel d: " + keys[0] + " Schlüssel e: " + keys[1]);
			break;
		case "-exit":
			running = false;
			break;
		default:
			System.out.println("Kein Befehl erkannt!");
			break;
		}
		return running;
//		//Angriff:
//		FermatFactorization hackerConsti = new FermatFactorization();
//		System.out.println("Angriff:");
//		BigInteger[] keys = hackerConsti.getKeys(n);
//		System.out.println("Schlüssel d: " + keys[0] + " Schlüssel e: " + keys[1]);
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
		
		//Primzahlüberprüfung(muss noch erweitert werden
		if(calc.isPrime(lFirstValue, 100) && calc.isPrime(lSecondValue, 100)) {
			System.out.println("Primzahlem");
		}
		
		//Test, ob Zahlen in richtigem Wertebereich
//		while(!calc.isInRange(lFirstValue) && !calc.isInRange(lSecondValue)) {
//			System.out.println("Versuche es nochmal.");
//			
//			System.out.println("Geben Sie die erste Zahl ein: ");
//			lFirstValue = sc.nextBigInteger();
//			sc.nextLine();
//			
//			System.out.println("Geben Sie die zweite Zahl ein: ");
//			lSecondValue = sc.nextBigInteger();
//			sc.nextLine();
//			
//		}
		
		//Berechnen von ph(n)
		phiofN = calc.CalculatePhiFunction(lFirstValue, lSecondValue);
		n = lFirstValue.multiply(lSecondValue);
		
		//Eingabe von öffentlichem Schlüssel
		System.out.println("Gib einen Schlüssel e an, der zwischen 1 und " 
		+  phiofN
		+ " liegt und zu " + phiofN + " teilerfremd ist: ");
		e = sc.nextBigInteger();
		
		//Prüfung von e, aber ohne Konsequenz
		if(calc.calculateGGT(e, phiofN).compareTo(BigInteger.valueOf(1)) != 0) {
			e = calc.generateKeys(lFirstValue, lSecondValue)[0];
		}
		
	}
}
