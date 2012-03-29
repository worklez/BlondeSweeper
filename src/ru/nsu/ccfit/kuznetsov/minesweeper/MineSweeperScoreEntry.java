package ru.nsu.ccfit.kuznetsov.minesweeper;

public class MineSweeperScoreEntry {
	private MineSweeperLevel level;
	private int time;
	private int flagsSet;
	private String name;

	public MineSweeperScoreEntry(String name, MineSweeperLevel level, int time, int flagsSet) {
		this.name = name;
		this.level = level;
		this.time = time;
		this.flagsSet = flagsSet;
	}

	public MineSweeperScoreEntry() {
	}

	public MineSweeperLevel getLevel() {
		return level;
	}
	public void setLevel(MineSweeperLevel level) {
		this.level = level;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getFlagsSet() {
		return flagsSet;
	}
	public void setFlagsSet(int flagsSet) {
		this.flagsSet = flagsSet;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
