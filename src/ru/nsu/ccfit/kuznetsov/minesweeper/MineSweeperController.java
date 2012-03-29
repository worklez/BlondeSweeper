package ru.nsu.ccfit.kuznetsov.minesweeper;

import java.util.Timer;
import java.util.TimerTask;

public class MineSweeperController {
	private MineSweeperModel model;
	private MineSweeperActionListener listener;
	private Timer timer;
	private boolean timerStarted = false;

	public MineSweeperController(MineSweeperModel model,
			MineSweeperActionListener listener) {
		this.model = model;
		this.listener = listener;
		if (null == listener) {
			this.listener = new MineSweeperActionListener() {
				public void cellBombed(int i, int j) {
				}

				public void cellOpened(int i, int j) {
				}

				public void userWon() {
				}

				public void timerAction() {
				}
			};
		}
	}

	public void shot(int i, int j) {
		if (model.isMined(i, j)) {
			stopTimer();
			model.open(i, j);
			listener.cellBombed(i, j);
		} else {
			check(i, j);
			if (model.onlyMinesLeaved()) {
				stopTimer();
				listener.userWon();
			}
		}
	}

	private void check(int i, int j) {
		if (i < 0 || i >= model.getHeight() || j < 0 || j >= model.getWidth())
			return;
		if (model.isOpened(i, j))
			return;

		model.open(i, j);
		listener.cellOpened(i, j);

		if (0 != model.getCell(i, j))
			return;

		for (int x : new int[] { i - 1, i, i + 1 }) {
			for (int y : new int[] { j - 1, j, j + 1 }) {
				if (x != i || y != j)
					check(x, y);
			}
		}
	}

	public void flag(int i, int j) {
		model.setFlagged(i, j, true);
		System.out.println("controller: flagged");
	}

	public void setListener(MineSweeperActionListener listener) {
		this.listener = listener;
	}

	public void startTimer() {
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				listener.timerAction();
			}
		}, 0, 1000);
		timerStarted = true;
	}

	public void stopTimer() {
		timer.cancel();
		timerStarted = false;
	}

	public boolean isTimerStarted() {
		return timerStarted;
	}

	public void setModel(MineSweeperModel model) {
		this.model = model;
	}
}