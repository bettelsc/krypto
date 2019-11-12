package menu;
import java.math.BigInteger;
import java.util.Scanner;

import calc.Calculation;
import encode.Encode;
import file.CFile;
import attack.FermatFactorization;

/**
 * Menü Klasse
 * @author Constantin Bttels
 * @author Niklas Hock
 */
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
	
	/**
	 * Hilfsfunktion um von jedem Menüpunkt aus, wieder zum Menü zu kommen. Zudem Codereduktion
	 * @param optionInCase: entweder zurück zum Menü oder Abbruch
	 * @author Constantin Bettels
	 */
	private void utilityForCase(String optionInCase)
	{
		System.out.println("--------------------------------------------------------------------------------------------------------");
		System.out.println("Um wieder zum Menü zu gelangen 'menu' eintragen, eine beliebige andere Taste um das Programm zu beenden:");
		optionInCase = sc.next();
		openGenMenu(optionInCase);
	}
	
	/**
	 * Hilfsfunktion für das Menü
	 * @param option: entweder zurück zum Menü oder Abbruch
	 * @author Constantin Bettels
	 */
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
	
	/**
	 * Methode zum Anzeigen und Funktionsgeben eines benutzerfreundlichen Menüs.
	 * @return running: ob das menü noch offen ist.
	 * @author Niklas Hock
	 * @author Constantin Bettels
	 */
	private boolean openMenu()
	{
		System.out.println("1. Zeige p an: -p");
		System.out.println("2. Zeige q an: -q");
		System.out.println("3. Zeige phi(n) an: -phi");
		System.out.println("4. Zeige e an: -e");
		System.out.println("5. Zeige d an: -d");
		System.out.println("6. Zeige den Klartext an: -plain");
		System.out.println("7. Zeige den Chiffretext an: -chiff");
		System.out.println("10. Attackiere die Chiffre mittels Fermat-Faktorisierung: -a");
		System.out.println("11. Exit: -exit");
		
		System.out.println("Geben Sie einen Befehl ein: ");
		String option = sc.next();
		String optionInCase = "";
		
		switch (option)
		{
			case "-p":
				System.out.println("Der Wert für p ist: " + lFirstValue);
				utilityForCase(optionInCase);
				break;
			case "-q":
				System.out.println("Der Wert für q ist: " + lSecondValue);
				utilityForCase(optionInCase);
				break;
			case "-phi":
				System.out.println("Der Wert für phi(n) ist: " + phiofN);
				utilityForCase(optionInCase);
				break;
			case "-e":
				System.out.println("Der Wert für e ist: " + e);
				utilityForCase(optionInCase);
				break;
			case "-d":
				System.out.println("Der Wert für d ist: " + d);
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
			case "-a":
				BigInteger[] keys = hackerConsti.getKeys(n);
				System.out.println("Schlüssel d: " + keys[0] + " Schlüssel e: " + keys[1]);
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
	
	/**
	 * Methode zur Kapselung benutzereingegebener Werte, falls ein Nutzer z.B anstatt einer Zahl
	 * einen Buchstaben eingibt, soll das Programm schließlich nicht abstürzen
	 * @param value: welcher Parameter gesucht wird
	 * @return localValue: vom Nutzer eingegebener Wert
	 * @author Constantin Bettels
	 * @author Niklas Hock
	 */
	private BigInteger safeValue(String value)
	{
		int iterator = 0;
		boolean invalid = true;
		BigInteger localValue = BigInteger.ZERO;
		
		do
		{
			//erst wenn die schleife einmal durchgelaufen ist, da das System sonst noch einen BigInt als Eingabe erwartet
			if (iterator > 0) {
				
				System.out.println("------------------------------------------------");
				System.out.println("Bitte eine Zahl für " + value + " eingeben: ");
			
				if (sc.hasNextBigInteger())
				{
					localValue = sc.nextBigInteger();
					sc.nextLine();
					invalid = false;
				}
				else
				{
					System.out.println("Offenbar wurde etwas anderes als eine Zahl eingegeben.");
					sc.nextLine();
				}
			}
			iterator++;
			
		} while (invalid);
		
		return localValue;
	}
	
	/**
	 * Hauptmethode, die den kompletten Programmablauf inne hat.
	 * @author Niklas Hock
	 * @author Constantin Bettels
	 */
	public void getInput() {
		
		System.out.println("-----------------------------------------------------------\n"
						 + "RSA-VERSCHLÜSSELUNG: \n"
						 + "Ablauf des Algorithmus wie in der Spezifikation gefordert.\n"
						 + "-----------------------------------------------------------");
		
		//boolean für Schleife für die Eingabe von p und q
		boolean running = true;
		
		do
		{	
			System.out.println("Sie können wählen zwischen eigenen eigenen Werten für p und q und zufällig generierten. \n"
					+ "Denken Sie die Zahlen müssen: p != q und 2^32 < p,q < 2^64 sowie ggT(p,q) = 1 erfüllen. \n"
					+ "Tippen Sie 'own' für eigene Werte, 'random' für zufällige Werte für p und q:");
			String option = sc.next();
			
			//switch-case mit den Optionen, die zur Verfügung stehen
			switch (option)
			{
			case "own":
				//nutzereingegebene Werte für p und q
				do { lFirstValue = safeValue("p"); } while (!calc.isInRange(lFirstValue) | !calc.isPrime(lFirstValue, 100));
				do { lSecondValue = safeValue("q"); } while (!calc.isInRange(lSecondValue) | !calc.isPrime(lSecondValue, 100));
				running = false;
				if (lFirstValue.equals(lSecondValue))
				{
					System.out.println("p und q dürfen nicht gleich sein");
					running = true;
				}
				break;
			case "random":
				//zufällige werte mit korrekten Beschränkungen von p und q
				lFirstValue = calc.generateRandomPrimeNumber();
				lSecondValue = calc.generateRandomPrimeNumber();
				running = false;
				if (lFirstValue.equals(lSecondValue))
				{
					System.out.println("p und q dürfen nicht gleich sein");
					running = true;
				}
				break;
			default:
				//Wenn der Nutzer einen falschen Befehl eingibt, wird das switch-case quasi wiederholt 
				System.out.println("--------------------------------------------------------");
				System.out.println("Kein gültiger Befehl.");
				System.out.println("--------------------------------------------------------");
				break;
			}
		
		} while (running);
		
		System.out.println("Werte (p, q) sind:" + "(" + lFirstValue + ", " + lSecondValue + ")");
		System.out.println("-------------------------------------------------------------------");
		//Input Plaintext
		System.out.println("Geben Sie den zu verschlüsselnden Text ein: ");
		plaintext = sc.next();
		//Berechnen von phi(n)
		phiofN = calc.CalculatePhiFunction(lFirstValue, lSecondValue);
		//Berechnen von n
		n = lFirstValue.multiply(lSecondValue);
		//schreiben von Phi(n) in Textdatei
		file.write("Phi von " + n + ": " + phiofN , "phi.txt");
		
		//boolean für Schleife für die Eingabe von e
		boolean runningE = true;
		
		do {
			
			System.out.println("Sie können wählen zwischen einem eigenen Wert für den öffentlichen Schlüssel e oder Sie lassen sich diesen berechnen. \n"
					+ "Denken Sie die Zahl muss: ggT(e, phi(n)) = 1 und 1 < e < phi(n) erfüllen. phi(n) = " + phiofN + ". \n"
					+ "Tippen Sie 'own' für einen eigenen, 'calculate' für den berechneten Wert:");
			String optionE = sc.next();
			
			//switch-case mit den Optionen, die zur Verfügung stehen
			switch (optionE)
			{		
			case "own":
				//Eingabe des öffentlichen Exponentens
				do { e = safeValue("e"); } while (e.gcd(phiofN).compareTo(BigInteger.valueOf(1)) != 0);
				runningE = false;
				//e muss größer als 1 sein
				if (e.compareTo(BigInteger.ONE) == 1) {
					runningE = true;
				}
				break;
			case "calculate":
				//Berechnung des öffentlichen Schlüssels
				e = calc.generateKeys(lFirstValue, lSecondValue)[0];
				System.out.println("Public Key: " + e);
				runningE = false;
				break;
			default:
				//Wenn der Nutzer einen falschen Befehl eingibt, wird das switch-case quasi wiederholt 
				System.out.println("--------------------------------------------------------");
				System.out.println("Kein gültiger Befehl.");
				System.out.println("--------------------------------------------------------");
				break;
			}
		
		} while (runningE);
		
		//Berechnung von d schon hier, um ihn in Textdatei zu schreiben
		d = calc.generateKeys(lFirstValue, lSecondValue)[1];
		
		//Public und Private Key in lokaler Textdatei
		file.write("Public Key: " + e + "\n" + "Private Key: " + d + "\n", "keys.txt");
		
		//Verschlüsseln als Nummern repräsentation
		BigInteger[] cipher = enc.encodeMessage(plaintext, e, n);
		
		//Klon der Verschlüsselung für string-repräsentation
		BigInteger[] testValue = cipher.clone();
		
		//Aufgrund negativer Werte
		for(int i = 0; i < testValue.length; i++)
		{
			testValue[i] = testValue[i].mod(BigInteger.valueOf(enc.getAlphabetArray().length));
		}
		
		//String Repräsentation vom Chiffretext
		cipherString = enc.formatMessagetoString(testValue);
		
		System.out.println("Ciphertext ist: " + cipherString);		
		//selbes wie für e
		boolean runningD = true;
		
		do
		{
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.println("Gib den privaten Schlüssel d ein, um die nachricht wieder zu entschlüsseln. \n"
					+ "Der Private Schlüssel berechnet sich wie folgt: e * d = 1 mod phi(n) \n"
					+ "Tippen Sie 'own' für einen eigenen Schlüssel, 'calculate' für den berechneten: ");
			
			String optionD = sc.next();
			
			switch (optionD)
			{
			case "own":
				//Eingabe des öffentlichen Exponentens
				do { dguess = safeValue("d"); } while (((dguess.multiply(e)).mod(phiofN)).compareTo(BigInteger.ONE) != 0);
				runningD = false;
				break;
			case "calculate":
				//Berechnung des privaten Schlüssels
				d = calc.generateKeys(lFirstValue, lSecondValue)[1];
				System.out.println("Private Key: " + d);
				runningD = false;
				break;
			default:
				//Wenn der Nutzer einen falschen Befehl eingibt, wird das switch-case quasi wiederholt 
				System.out.println("--------------------------------------------------------");
				System.out.println("Kein gültiger Befehl.");
				System.out.println("--------------------------------------------------------");
				break;
			}
			
		} while(runningD);
		
		file.write("Klartext: " + plaintext + "\n" + "Chiffretext: " + cipherString, "texts.txt");
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Der Klartext ist: " + plaintext);
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("Die Verschlüsselung wurde einmal wie gewünscht durchgeführt.");
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("Sie haben jetzt die Möglichkeit, dass Programm zu beenden oder \n"
				+ "zu einem Menü zu gelangen, in dem Sie sich sämtliche Werte noch einmal anzeigen lassen können \n"
				+ "und einen Angriff auf die Verschlüsselung mit den gespeicherten Werten starten können. \n"
				+ "Um zu dem Menu zu gelangen 'menu' eingeben, um das Programm zu beenden eine beliebige Taste drücken.");
		
		option = sc.next();
		
		//Nachdem das Programm einmal durchgelaufen ist, gibt es die Option ein Menü zu öffnen, in dem man sich alle Werte anzeigen
		//lassen kann und den Fermat Attack ausführen.
		openGenMenu(option);
	}
}



