package com.rsampdev.inTheDark;

class Quest {

	private String name;
	private boolean completed;
	private QuestEventCompleter[] eventsToComplete;

	Quest(String name, QuestEventCompleter[] eventsToComplete) {
		this.eventsToComplete = eventsToComplete;
		this.completed = false;
		this.name = name;
	}

	String getName() {
		return name;
	}

	boolean isCompleted() {
		return completed;
	}

	QuestEventCompleter[] getEventsToComplete() {
		return eventsToComplete;
	}

	void checkForCompletion(QuestEvent event) {
		boolean check = true;

		for (QuestEventCompleter questEventCompleter : eventsToComplete) {
			if (questEventCompleter.event.equals(event)) {
				questEventCompleter.complete();
			}

			if (questEventCompleter.completed == false) {
				check = false;
			}
		}

		if (check) {
			this.completed = true;
		}
	}
	
}

class QuestEventCompleter {
	boolean completed = false;
	QuestEvent event;

	QuestEventCompleter(QuestEvent event) {
		this.event = event;
	}

	void complete() {
		this.completed = true;
	}
}

enum QuestEvent {
	
	NONE, PLAYER_DEATH, SPIDER_KILLED, UNDEAD_SHAMBLER_KILLED, CAVE_GREMLIN_KILLED, CAVE_ORC_KILLED, SKELETON_WARRIOR_KILLED, CAVE_TROLL_KILLED;
	
	static QuestEvent CURRENT_EVENT = NONE;
	
}
