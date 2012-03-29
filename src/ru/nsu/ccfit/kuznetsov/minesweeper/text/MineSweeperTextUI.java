package ru.nsu.ccfit.kuznetsov.minesweeper.text;

import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperActionListener;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperCell;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperController;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperModel;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperWorldAction;

public class MineSweeperTextUI {
	private static class InternalState {
		public boolean stopped = false;
	}
	private static boolean firstShot = true;
	public static void main(String[] args) {
		final MineSweeperModel m = new MineSweeperModel();
		final InternalState state = new InternalState();

		MineSweeperController c = new MineSweeperController(m,
				new MineSweeperActionListener() {
					public void cellBombed(int i, int j) {
						System.out.println("Oops. You died. Have a nice day.");
						state.stopped = true;
					}

					public void cellOpened(int i, int j) {
					}

					public void userWon() {
						System.out.println("Congrats! You won.");
						state.stopped = true;
					}

					public void timerAction() {
						// TODO Auto-generated method stub
						
					}
				});
		MineSweeperWorldAction act = new MineSweeperWorldAction() {
			public void actionPerfromed(int i, int j, MineSweeperCell cell) {
				if (cell.isFlagged()) {
					System.out.print("F ");
				} else {
					if (!cell.isOpened()) {
						System.out.print("X ");
					} else {
						if (cell.isMined()) {
							System.out.print("x ");
						} else {
							System.out.print(cell.getValue() + " ");
						}
					}
				}
				if (j == m.getWidth() - 1) {
					System.out.println();
				}
			}
		};
		Scanner in = new Scanner(System.in);
		while (true) {
			try {
				String cmd = in.nextLine();
				if ("quit".equals(cmd)) {
					return;
				} else {
					Scanner s = new Scanner(cmd);
					cmd = s.next();
					int i = s.nextInt();
					int j = s.nextInt();
					if (firstShot) {
						m.fillWorld(i, j);
						firstShot = false;
					}
					if ("point".equals(cmd)) {
						c.shot(i, j);
					} else if ("flag".equals(cmd)) {
						c.flag(i, j);
					}
				}
				if (state.stopped) {
					break;
				}
				if (!firstShot) {
					m.applyToWorld(act);
				}
			} catch (NoSuchElementException e) {
				
			}
		}
	}
}