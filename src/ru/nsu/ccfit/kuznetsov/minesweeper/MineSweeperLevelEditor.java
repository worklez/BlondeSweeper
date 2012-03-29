package ru.nsu.ccfit.kuznetsov.minesweeper;

import java.beans.XMLEncoder;
import java.util.HashMap;

public class MineSweeperLevelEditor {

	public static void main(String[] args) {
		HashMap<String, MineSweeperLevel> levels = new HashMap<String, MineSweeperLevel>();
		levels.put("Beginner", new MineSweeperLevel(10, 10, 9, "Beginner"));
		levels.put("Intermediate", new MineSweeperLevel(16, 16, 40, "Intermediate"));
		levels.put("Expert", new MineSweeperLevel(16, 30, 99, "Expert"));

		// FileOutputStream fos = new FileOutputStream("1.xml");

		XMLEncoder xenc = new XMLEncoder(System.out);
		xenc.writeObject(levels);
		xenc.close();
	}

}
