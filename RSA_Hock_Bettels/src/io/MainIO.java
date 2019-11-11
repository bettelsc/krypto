package io;

import menu.Menu;

public class MainIO
{
	public static void main(String[] args)
	{
		Menu menu = new Menu();
		menu.getInput();
		while(menu.openMenu());
		
	}
}