package com.rsampdev.inTheDark;

enum Command {

	HELP("help"), QUIT("quit"), YES("yes"), NO("no"), EXPLORE("explore"), STATS("stats");

	private String command;

	private Command(String command) {
		this.command = command;
	}

	String getCommand() {
		return this.command;
	}

}
