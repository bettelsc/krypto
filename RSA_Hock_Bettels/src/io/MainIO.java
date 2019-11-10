package io;

import java.math.BigInteger;
import menu.Menu;

public class MainIO {

	private BigInteger mFirstValue;
	private BigInteger mSecondValue;
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.getInput();
		while(menu.openMenu());
	}
	
	public BigInteger getFirstValue() {
		return mFirstValue;
	}

	public void setFirstValue(BigInteger aFirstValue) {
		this.mFirstValue = aFirstValue;
	}

	public BigInteger getSecondValue() {
		return mSecondValue;
	}

	public void setSecondValue(BigInteger aSecondValue) {
		this.mSecondValue = aSecondValue;
	}
	
}