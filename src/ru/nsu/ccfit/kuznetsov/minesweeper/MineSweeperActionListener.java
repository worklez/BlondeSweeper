package ru.nsu.ccfit.kuznetsov.minesweeper;

public interface MineSweeperActionListener {
	void cellOpened(int i, int j);
	void cellBombed(int i, int j);
	void userWon();
	void timerAction();
	
}
