package io;

import menu.Menu;

/**
 * Von dieser Klasse aus wird das Projekt gespeichert und ist eigentlich nur aus Sicherheitsgründen so aufgebaut,
 * dass von außen möglichst wenig einsehbar ist.
 * @author Niklas Hock
 */
public class MainIO
{
	public static void main(String[] args)
	{
		Menu menu = new Menu();
		menu.getInput();
	}
}