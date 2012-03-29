package ru.nsu.ccfit.kuznetsov.minesweeper.resources;

import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class MineSweeperResources {
	static HashMap<String, ImageIcon> icons = null;

	public static ImageIcon getIcon(String name) {
		if (null == icons) {
			icons = new HashMap<String, ImageIcon>();
		}
		if (icons.containsKey(name)) {
			return icons.get(name);
		} else {
			ImageIcon i = new ImageIcon(getURL(name));
			icons.put(name, i);
			return i;
		}
	}

	public static URL getURL(String name) {
		return MineSweeperResources.class.getResource(name);
	}
}
