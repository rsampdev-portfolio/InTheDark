package com.rsampdev.inTheDark;

import java.util.Scanner;

public class Terminal {

	private static Scanner TERMINAL = new Scanner(System.in);

	static String getInput() {
		return TERMINAL.nextLine().toLowerCase().trim();
	}

	static void close() {
		TERMINAL.close();
	}

}
