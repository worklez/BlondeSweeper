package ru.nsu.ccfit.kuznetsov.minesweeper.gui;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MineSweeperCellButton extends JButton {

	private static final long serialVersionUID = -1508329800082463577L;

	private int i, j;
	private boolean clickable = true;
	
	public MineSweeperCellButton(int i, int j) {
		super();
//		setSize(getPreferredSize());
		this.i = i;
		this.j = j;
		
//		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(0x99, 0xcc, 0x33)));
//		setBackground(new Color(0xaa, 0xee, 0x44));
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(0xfc, 0x2f, 0xb8)));
		setBackground(new Color(0xff, 0xcf, 0xcd));
		setMargin(new Insets(0, 0, 0, 0));
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean isClickable() {
		return clickable;
	}
}
