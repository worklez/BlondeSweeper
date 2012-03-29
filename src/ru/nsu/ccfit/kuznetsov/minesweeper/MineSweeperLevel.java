package ru.nsu.ccfit.kuznetsov.minesweeper;

public class MineSweeperLevel {

	private int width;
	private int height;
	private int mines;
	private String name;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMines() {
		return mines;
	}

	public String getName() {
		return name;
	}

	public MineSweeperLevel(int width, int height, int mines, String name) {
		this.width = width;
		this.height = height;
		this.mines = mines;
		this.name = name;
	}
	public MineSweeperLevel() {
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public void setName(String name) {
		this.name = name;
	}
}