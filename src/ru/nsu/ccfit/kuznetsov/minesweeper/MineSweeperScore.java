package ru.nsu.ccfit.kuznetsov.minesweeper;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import ru.nsu.ccfit.kuznetsov.minesweeper.resources.MineSweeperResources;

public class MineSweeperScore {
	public static LinkedList<MineSweeperScoreEntry> getScores() {
		LinkedList<MineSweeperScoreEntry> scores = null;
		try {
			InputStream in;
			in = MineSweeperResources.getURL("scores.xml").openStream();
			XMLDecoder dec = new XMLDecoder(in);
			scores = (LinkedList<MineSweeperScoreEntry>) dec.readObject();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		} catch (NoSuchElementException e) {
			// System.out.println(e);
		}

		return scores;
	}

	public static void saveScores(LinkedList<MineSweeperScoreEntry> scores) {
		try {
			FileOutputStream out;
			out = new FileOutputStream(MineSweeperResources.getURL("scores.xml").getFile());
			XMLEncoder xenc = new XMLEncoder(out);
			xenc.writeObject(scores);
			xenc.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
