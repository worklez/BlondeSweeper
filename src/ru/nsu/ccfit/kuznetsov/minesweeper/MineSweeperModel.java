package ru.nsu.ccfit.kuznetsov.minesweeper;

import java.util.Random;

public class MineSweeperModel {

	private MineSweeperCell map[][];
	private int width;
	private int height;
	private int bombsCount;

	public MineSweeperModel(int width, int height, int bombsCount) {
		init(width, height, bombsCount);
	}
	
	public MineSweeperModel() {
		MineSweeperLevel level = MineSweeperSettings.getInstance().getLevel();
		init(level.getWidth(), level.getHeight(), level.getMines());
	}
	
	private void init(int width, int height, int bombsCount) {
		this.width = width;
		this.height = height;
		this.bombsCount = bombsCount;

		assert width >= 0 && width <= 16;
		assert height >= 0 && height <= 30;
		assert bombsCount >= 0 && bombsCount <= 99;

		map = new MineSweeperCell[height][width];		
	}
	
	public void fillWorld(int a, int b) {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				map[i][j] = new MineSweeperCell();
			}
		}

		Random r = new Random();

		while (bombsCount > 0) {
			int i = r.nextInt(height);
			int j = r.nextInt(width);
			if (i == a && j == b) continue;
			if (!map[i][j].isMined()) {
				bombsCount--;
				map[i][j].setMined(true);
				for (int x : new int[] { i - 1, i, i + 1 }) {
					for (int y : new int[] { j - 1, j, j + 1 }) {
						try {
							map[x][y].incValue();
						} catch (ArrayIndexOutOfBoundsException e) {

						}
					}
				}
			}
		}
	}

	public void open(int i, int j) {
		map[i][j].setOpened(true);
	}

	public boolean isOpened(int i, int j) {
		return map[i][j].isOpened();
	}

	public void setFlagged(int i, int j, boolean state) {
		map[i][j].setFlagged(state);
	}

	public boolean isFlagged(int i, int j) {
		return map[i][j].isFlagged();
	}

	public boolean isMined(int i, int j) {
		return map[i][j].isMined();
	}

	public int getCell(int i, int j) {
		return map[i][j].getValue();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void applyToWorld(MineSweeperWorldAction a) {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				a.actionPerfromed(i, j, map[i][j]);
			}
		}
	}

	public void debug() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (map[i][j].isMined()) {
					System.out.print("x ");
				} else {
					System.out.print(map[i][j].getValue() + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void debug1() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (!map[i][j].isOpened()) {
					System.out.print("X ");
				} else {
					if (map[i][j].isMined()) {
						System.out.print("x ");
					} else {
						System.out.print(map[i][j].getValue() + " ");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean onlyMinesLeaved() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (!map[i][j].isMined() && !map[i][j].isOpened()) {
					return false;
				}
			}
		}
		return true;
	}

	public int getMines() {
		return bombsCount;
	}
}