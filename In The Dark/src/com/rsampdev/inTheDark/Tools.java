package com.rsampdev.inTheDark;

import java.util.Random;
import java.util.Scanner;

class Tools {

	static String LISTENER = "";
	static Random DICE = new Random();

	static String getInputFrom(Scanner terminal) {
		return terminal.nextLine().toLowerCase().trim();
	}

}
