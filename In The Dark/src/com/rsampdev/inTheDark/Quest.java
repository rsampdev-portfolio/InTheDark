package com.rsampdev.inTheDark;

import java.util.ArrayList;
import java.util.Comparator;

class Quest implements Cloneable {

	private String name;
	private boolean completed;
	private QuestEventCompleter[] eventsToComplete;

	private static ArrayList<Quest> QUESTS = new ArrayList<Quest>();
	private static int QUEST_SIZE = QUESTS.size();

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
				break;
			}

			if (questEventCompleter.completed == false) {
				check = false;
			}
		}

		if (check) {
			this.completed = true;
		}
	}

	double getCompletionPercentage() {
		double top = 0;

		for (QuestEventCompleter questEventCompleter : eventsToComplete) {
			if (questEventCompleter.completed) {
				top++;
			}
		}

		double completionPercentage = (top / this.getEventsToComplete().length) * 100;

		return completionPercentage;
	}

	static Quest getKillOneSpiderQuest() {
		QuestEventCompleter[] eventsToComplete = { new QuestEventCompleter(QuestEvent.SPIDER_KILLED) };
		Quest killOneSpiderQuest = new Quest("Kill 1 Spider", eventsToComplete);
		return killOneSpiderQuest;
	}

	static Quest getKillTwoSpidersQuest() {
		QuestEventCompleter[] eventsToComplete = { new QuestEventCompleter(QuestEvent.SPIDER_KILLED), new QuestEventCompleter(QuestEvent.SPIDER_KILLED) };
		Quest killOneSpiderQuest = new Quest("Kill 2 Spiders", eventsToComplete);
		return killOneSpiderQuest;
	}

	static Quest getKillThreeSpidersQuest() {
		QuestEventCompleter[] eventsToComplete = { new QuestEventCompleter(QuestEvent.SPIDER_KILLED), new QuestEventCompleter(QuestEvent.SPIDER_KILLED),
				new QuestEventCompleter(QuestEvent.SPIDER_KILLED) };
		Quest killOneSpiderQuest = new Quest("Kill 3 Spiders", eventsToComplete);
		return killOneSpiderQuest;
	}

	static void prepare() {
		Quest.QUESTS.add(getKillOneSpiderQuest());
		Quest.QUESTS.add(getKillTwoSpidersQuest());
		Quest.QUESTS.add(getKillThreeSpidersQuest());
		QUEST_SIZE = QUESTS.size();
	}

	static Quest getRandomQuest() throws Exception {
		Quest quest = (Quest) QUESTS.get(Tools.DICE.nextInt(QUEST_SIZE)).clone();
		return quest;
	}

	static class SortQuestsByName implements Comparator<Quest> {

		public int compare(Quest a, Quest b) {
			return a.getName().compareTo(b.getName());
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
