package com.rsampdev.inTheDark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

class Quest implements Cloneable, Serializable {

	private static final long serialVersionUID = 5446104460463308659L;

	private String name;
	private boolean completed;
	private double experienceReward;
	private ArrayList<Item> itemReward = new ArrayList<Item>();
	private ArrayList<QuestEventCompleter> eventsToComplete = new ArrayList<QuestEventCompleter>();

	private static ArrayList<Quest> QUESTS = new ArrayList<Quest>();
	private static int QUEST_SIZE = QUESTS.size();

	Quest(String name, double experienceReward, QuestEvent[] eventsToComplete, Item[] itemReward) {
		for (QuestEvent questEvent : eventsToComplete) {
			this.eventsToComplete.add(new QuestEventCompleter(questEvent));
		}

		for (Item item : itemReward) {
			this.itemReward.add(item);
		}

		this.experienceReward = experienceReward;
		this.completed = false;
		this.name = name;
	}

	String getName() {
		return name;
	}

	double getExperienceReward() {
		return experienceReward;
	}

	boolean isCompleted() {
		return completed;
	}

	ArrayList<QuestEventCompleter> getEventsToComplete() {
		return eventsToComplete;
	}

	void checkForCompletion() {
		boolean check = true;

		for (QuestEventCompleter questEventCompleter : eventsToComplete) {
			if (!questEventCompleter.completed) {
				check = false;

				if (questEventCompleter.event.equals(QuestEvent.CURRENT_EVENT)) {
					questEventCompleter.complete();
					break;
				}
			}
		}

		if (check) {
			this.completed = true;
		}

		QuestEvent.CURRENT_EVENT = QuestEvent.NONE;
	}

	String getCompletionPercentage() {
		double top = 0;

		for (QuestEventCompleter questEventCompleter : eventsToComplete) {
			if (questEventCompleter.completed) {
				top++;
			}
		}

		double completionPercentage = (top / this.getEventsToComplete().size()) * 100;

		return String.format("%.2f", completionPercentage);
	}

	void doleOutRewardsTo(Player player) {
		player.addExperience(experienceReward);
		for (Item item : itemReward) {
			player.addItem(item);
		}
	}

	static Quest getKillOneSpiderQuest() {
		QuestEvent[] eventsToComplete = { QuestEvent.SPIDER_KILLED };
		Item[] itemReward = { Item.SPIDER_JERKY };
		Quest killOneSpiderQuest = new Quest("Kill 1 Spider", 100, eventsToComplete, itemReward);
		return killOneSpiderQuest;
	}

	static Quest getKillTwoSpidersQuest() {
		QuestEvent[] eventsToComplete = { QuestEvent.SPIDER_KILLED, QuestEvent.SPIDER_KILLED };
		Item[] itemReward = { Item.SPIDER_JERKY, Item.SPIDER_JERKY };
		Quest killOneSpiderQuest = new Quest("Kill 2 Spiders", 200, eventsToComplete, itemReward);
		return killOneSpiderQuest;
	}

	static Quest getKillThreeSpidersQuest() {
		QuestEvent[] eventsToComplete = { QuestEvent.SPIDER_KILLED, QuestEvent.SPIDER_KILLED, QuestEvent.SPIDER_KILLED };
		Item[] itemReward = { Item.SPIDER_JERKY, Item.SPIDER_JERKY, Item.SPIDER_JERKY };
		Quest killOneSpiderQuest = new Quest("Kill 3 Spiders", 300, eventsToComplete, itemReward);
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

class QuestEventCompleter implements Serializable {
	private static final long serialVersionUID = -7963257072886120621L;

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
