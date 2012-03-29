package ru.nsu.ccfit.kuznetsov.minesweeper;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import ru.nsu.ccfit.kuznetsov.minesweeper.resources.MineSweeperResources;

public class MineSweeperSettings {

	private HashMap<String, MineSweeperLevel> levels;
	private String currentLevel;

	private static MineSweeperSettings instance = null;

	private MineSweeperSettings() {
		currentLevel = "Beginner";
		loadLevels();
	}

	public static MineSweeperSettings getInstance() {
		if (null == instance)
			instance = new MineSweeperSettings();
		return instance;
	}

	public void loadLevels() {
		try {
			InputStream in;
			in = MineSweeperResources.getURL("levels.xml")
					.openStream();
			XMLDecoder dec = new XMLDecoder(in);
			levels = (HashMap<String, MineSweeperLevel>) dec.readObject();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void setLevel(String name) throws NoSuchLevelException {
		if (levels.containsKey(name)) {
			currentLevel = name;
		} else {
			loadLevels();
			if (levels.containsKey(name)) {
				currentLevel = name;
			} else {
				throw new NoSuchLevelException();
			}
		}
	}

	public MineSweeperLevel getLevel() {
		return levels.get(currentLevel);
	}

	public Object[] getLevels() {
		return levels.keySet().toArray();
	}
}
