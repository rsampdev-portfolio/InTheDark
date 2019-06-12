package com.rsampdev.inTheDark;

enum Command {

	HELP("help"), QUIT("quit"), SAVE("save"), YES("yes"), NO("no"), EXPLORE("explore"), INVENTORY("inventory"), USE("use"), LEVEL("level"), STATS("stats");

	private String command;

	private Command(String command) {
		this.command = command;
	}

	String getCommand() {
		return this.command;
	}

}
