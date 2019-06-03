package com.rsampdev.inTheDark;

enum Command {

	HELP("help"), QUIT("quit");

	private String value;

	private Command(String value) {
		this.value = value;
	}

	String getValue() {
		return this.value;
	}

}
