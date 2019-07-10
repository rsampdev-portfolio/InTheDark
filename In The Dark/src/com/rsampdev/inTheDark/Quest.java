package com.rsampdev.inTheDark;

enum Quest {

	QUEST(null);

	private int count;
	private QuestEvent[] events;

	private Quest(QuestEvent[] events) {
		this.count = events.length;
		this.events = events;
	}

	int getCount() {
		return count;
	}

	QuestEvent[] getEvents() {
		return events;
	}

}

enum QuestEvent {

	QUEST_EVENT;

}
