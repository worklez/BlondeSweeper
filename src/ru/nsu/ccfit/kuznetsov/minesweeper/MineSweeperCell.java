package ru.nsu.ccfit.kuznetsov.minesweeper;

public class MineSweeperCell {
	private boolean opened;
	private boolean flagged;
	private boolean mined;
	private int value;
	
	public MineSweeperCell() {
		setFlagged(false);
		setMined(false);
		setOpened(false);
		setValue(0);
	}
	
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public boolean isFlagged() {
		return flagged;
	}
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	public boolean isMined() {
		return mined;
	}
	public void setMined(boolean mined) {
		this.mined = mined;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void incValue() {
		this.value++;
	}

}
