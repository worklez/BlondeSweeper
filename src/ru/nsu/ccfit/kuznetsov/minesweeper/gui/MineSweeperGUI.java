package ru.nsu.ccfit.kuznetsov.minesweeper.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperActionListener;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperCell;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperController;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperLevel;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperModel;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperScore;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperScoreEntry;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperSettings;
import ru.nsu.ccfit.kuznetsov.minesweeper.MineSweeperWorldAction;
import ru.nsu.ccfit.kuznetsov.minesweeper.NoSuchLevelException;
import ru.nsu.ccfit.kuznetsov.minesweeper.resources.MineSweeperResources;

public class MineSweeperGUI extends JFrame {
	private static final long serialVersionUID = -6902064073445587235L;

	private JPanel mainPanel;
	private JButton face;
	private MineSweeperDigitView[] flagsView;
	private MineSweeperDigitView[] timeView;
	private int width;
	private int height;
	private int buttonSize;
	private boolean firstClick = true;
	private int currentTime;
	private int flagsAvail;

	private MineSweeperModel model;
	private MineSweeperController controller;
	private MineSweeperCellButton[][] buttons;

	private void adaptView() {
		currentTime = 0;
		MineSweeperDigitView.setValue(timeView, 0);
		flagsAvail = model.getMines();
		MineSweeperDigitView.setValue(flagsView, flagsAvail);

		width = model.getWidth();
		height = model.getHeight();

		mainPanel.removeAll();
		mainPanel.setLayout(new GridLayout(height, width));
		mainPanel.setSize(width * (buttonSize + 1), height * (buttonSize + 1));
		mainPanel.setPreferredSize(mainPanel.getSize());

		buttons = new MineSweeperCellButton[height][width];

		controller.setListener(new MineSweeperActionListener() {

			public void cellBombed(int i, int j) {
				final ImageIcon img = MineSweeperResources.getIcon("mine.png");
				buttons[i][j].setIcon(img);
				model.applyToWorld(new MineSweeperWorldAction() {
					public void actionPerfromed(int i, int j,
							MineSweeperCell cell) {
						buttons[i][j].setClickable(false);
						if (cell.isMined()) {
							buttons[i][j].setIcon(img);
						}
					}
				});
				face.setIcon(MineSweeperResources.getIcon("face1.png"));
				JOptionPane.showMessageDialog(MineSweeperGUI.this, "Oops. You died. Have a nice day.");
				//\n\nDon't you want to save your score?");
//				if (null != user) {
//					saveScore(user);
//				}
			}

			public void cellOpened(int i, int j) {
				int val = model.getCell(i, j);
				Color c;

				if (model.isFlagged(i, j))
					return;

				buttons[i][j].setClickable(false);
				buttons[i][j].setBackground(Color.WHITE);

				if (0 == val)
					return;

				buttons[i][j].setText("" + val);
				switch (val) {
				case 1:
					c = Color.BLUE;
					break;
				case 2:
					c = Color.GREEN;
					break;
				case 3:
					c = Color.ORANGE;
					break;
				default:
					c = Color.RED;
					break;
				}
				buttons[i][j].setForeground(c);
			}

			public void userWon() {
				for (int i = 0; i < height; ++i) {
					for (int j = 0; j < width; ++j) {
						buttons[i][j].setClickable(false);
					}
				}
				String user = JOptionPane
						.showInputDialog("Congrats! You won.\nEnter your name if you want to save the score:");
				if (null != user) {
					saveScore(user);
				}
			}

			public void timerAction() {
				synchronized (this) {
					currentTime++;
				}
				currentTime %= 100;
				MineSweeperDigitView.setValue(timeView, currentTime);
			}

		});
		setupButtons();
		pack();
	}

	private void setupButtons() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				MineSweeperCellButton button = new MineSweeperCellButton(i, j);
				button.setSize(buttonSize, buttonSize);
				button.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						MineSweeperCellButton b = (MineSweeperCellButton) e
								.getSource();
						int i = b.getI();
						int j = b.getJ();

						if (firstClick) {
							firstClick = false;
							model.fillWorld(i, j);
						}

						if (!b.isClickable())
							return;

						if (!controller.isTimerStarted()) {
							controller.startTimer();
						}

						if (SwingUtilities.isRightMouseButton(e)) {
							if (model.isFlagged(i, j)) {
								model.setFlagged(i, j, false);
								buttons[i][j].setIcon(null);
								flagsAvail++;
							} else {
								if (flagsAvail < 0)
									return;
								model.setFlagged(i, j, true);
								buttons[i][j].setIcon(MineSweeperResources
										.getIcon("flag.png"));
								flagsAvail--;
							}
							MineSweeperDigitView
									.setValue(flagsView, flagsAvail);
						} else if (SwingUtilities.isLeftMouseButton(e)) {
							if (model.isFlagged(i, j))
								return;
							controller.shot(i, j);
						}
					}
				});

				buttons[i][j] = button;
				mainPanel.add(button);
			}
		}
	}

	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuGame = new JMenu("Game");
		JMenuItem newItem = new JMenuItem("New");
		newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				globalRestart();	
			}
		});
		menuGame.add(newItem);

		JMenuItem editItem = new JMenuItem("Select level");
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String level = (String) JOptionPane.showInputDialog(
						MineSweeperGUI.this, "Select level", "Level selection",
						JOptionPane.PLAIN_MESSAGE, null,
						MineSweeperSettings.getInstance().getLevels(),
						MineSweeperSettings.getInstance().getLevel().getName());
				if (null == level)
					return;
				try {
					MineSweeperSettings.getInstance().setLevel(level);
					globalRestart();
				} catch (NoSuchLevelException ex) {
					JOptionPane.showMessageDialog(MineSweeperGUI.this,
							"No such level", "Internal error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		menuGame.add(editItem);

		JMenuItem scoresItem = new JMenuItem("Scores");
		scoresItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final LinkedList<MineSweeperScoreEntry> scores = MineSweeperScore.getScores();

				if (null == scores) {
					JOptionPane.showMessageDialog(MineSweeperGUI.this, "There are no scores yet.");
				} else {
					final String[] columns = {"Name", "Width", "Height", "Mines", "Flags set", "Time"};
					JTable table = new JTable(new AbstractTableModel() {
						
						public int getColumnCount() {
							return columns.length;
						}

						public int getRowCount() {
							return scores.size()+1;
						}

						public Object getValueAt(int rowIndex, int columnIndex) {
							if (0 == rowIndex) return getColumnName(columnIndex);
							rowIndex--;
							MineSweeperScoreEntry entry = scores.get(rowIndex);
							MineSweeperLevel level = entry.getLevel();
							switch (columnIndex) {
								case 0: return entry.getName();
								case 1: return level.getWidth();
								case 2: return level.getHeight();
								case 3: return level.getMines();
								case 4: return entry.getFlagsSet();
								case 5: return entry.getTime();
								default: return "???";
							}
						}

						public String getColumnName(int column) {
							return columns[column];
						}
					});
					table.setAutoCreateRowSorter(true);
					JOptionPane.showMessageDialog(MineSweeperGUI.this, table);
				}	
			}
		});
		menuGame.add(scoresItem);

		JMenu menuHelp = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								MineSweeperGUI.this,
								"BlondeSweeper 0.01\n2009 all wrongs reserved\nIlya Kuznetsov <ikuznecov@ccfit.nsu.ru>");
			}
		});
		menuHelp.add(aboutItem);

		menuBar.add(menuGame);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}
	
	private void saveScore(String name) {
		LinkedList<MineSweeperScoreEntry> scores = MineSweeperScore.getScores();
		if (null == scores) {
			scores = new LinkedList<MineSweeperScoreEntry>();
		}
		MineSweeperLevel level = MineSweeperSettings.getInstance().getLevel();
		scores.add(new MineSweeperScoreEntry(name, level, currentTime, level.getMines() - flagsAvail));
		MineSweeperScore.saveScores(scores);
	}

	private void globalRestart() {
		controller.stopTimer();
		face.setIcon(MineSweeperResources.getIcon("face0.png"));
		model = new MineSweeperModel();
		controller.setModel(model);
		adaptView();
		firstClick = true;
	}

	
	public MineSweeperGUI(String str, MineSweeperController controller,
			MineSweeperModel model) {
		super(str);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);

		this.controller = controller;
		this.model = model;

		buttonSize = 20; // pixels
		mainPanel = new JPanel();
		flagsView = new MineSweeperDigitView[2];
		timeView = new MineSweeperDigitView[2];
		for (int i = 0; i < 2; ++i) {
			flagsView[i] = new MineSweeperDigitView();
			timeView[i] = new MineSweeperDigitView();
		}

		adaptView();

		face = new JButton();
		face.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				globalRestart();
			}
		});

		face.setBorder(null);
		face.setIcon(MineSweeperResources.getIcon("face0.png"));
		face.setSize(face.getPreferredSize());

		constraints.weightx = 0;
		constraints.weighty = 0;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		getContentPane().add(flagsView[0], constraints);
		constraints.gridx = 1;
		getContentPane().add(flagsView[1], constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.weightx = 1;
		constraints.weighty = 1;
		getContentPane().add(face, constraints);

		constraints.weightx = 0;
		constraints.weighty = 0;

		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		getContentPane().add(timeView[0], constraints);
		constraints.gridx = 4;
		getContentPane().add(timeView[1], constraints);

		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 5;
		getContentPane().add(mainPanel, constraints);
		makeMenu();
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		MineSweeperModel model = new MineSweeperModel();
		// model.debug();
		new MineSweeperGUI("BlondeSweeper", new MineSweeperController(model,
				null), model);
	}
}
