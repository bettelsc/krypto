package io;

import java.util.Scanner;
import java.lang.Math;

public class MainIO {

	private int mFirstValue;
	private int mSecondValue;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Geben Sie die erste Zahl ein: ");
		int lFirstValue = sc.nextInt();
		sc.nextLine();
		System.out.println("Geben Sie die zweite Zahl ein: ");
		int lSecondValue = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Geben Sie nun den zu verschlüsselnden Text ein: ");
		String lPlainText = sc.nextLine();
		
		sc.close();
	}

	public int getFirstValue() {
		return mFirstValue;
	}

	public void setFirstValue(int aFirstValue) {
		this.mFirstValue = aFirstValue;
	}

	public int getSecondValue() {
		return mSecondValue;
	}

	public void setSecondValue(int aSecondValue) {
		this.mSecondValue = aSecondValue;
	}
	
}





















