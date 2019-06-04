package com.rsampdev.inTheDark;

enum Command {

	HELP("help"), QUIT("quit"), YES("yes"), NO("no"), EXPLORE("explore"), STATS("stats");

	private String value;

	private Command(String value) {
		this.value = value;
	}

	String getValue() {
		return this.value;
	}

}
