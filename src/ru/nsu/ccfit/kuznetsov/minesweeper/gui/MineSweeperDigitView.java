package ru.nsu.ccfit.kuznetsov.minesweeper.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ru.nsu.ccfit.kuznetsov.minesweeper.resources.MineSweeperResources;

public class MineSweeperDigitView extends JLabel {

	private static final long serialVersionUID = 2296532704761800488L;
	
	ImageIcon icons[];
	public MineSweeperDigitView() {
		super();
		icons = new ImageIcon[10];
		for (int i = 0; i < 10; ++i) {
			icons[i] = MineSweeperResources.getIcon(i + ".png");
		}
		setValue(0);
	}
	
	public void setValue(int val) {
		if (val >= 0 && val <= 9)
		setIcon(icons[val]);
	}
	
	static void setValue(MineSweeperDigitView[] view, int value) {
		int len = view.length;
		while (len-- > 0) {
			int digit = value % 10;
			value /= 10;
			view[len].setValue(digit);
		}
	}
}
