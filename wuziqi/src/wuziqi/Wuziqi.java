package wuziqi;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import AI.EasyAI;
import AI.HardAI;

public class Wuziqi {
	private Display display;
	private Shell shell;
	Mydialog mydialog;
	private Menu menu, gameMenu, PVCMenu, helpMenu;
	private MenuItem gameMenuItem, PVPMenuItem, PVCMenuItem, useBlackMenuItem,
			useWhiteMenuItem, cancelItem, selectAiItem1, selectAiItem2,
			giveUpMenuItem, exitMenuItem, HelpMenuItem, helpMenuItem,
			aboutMenuItem;
	Image image, imageH, imageB, imageStart, imageStop;
	private Button startbutton;
	private static int i;
	private static int j;
	private int x;
	private int y;
	private GC gc;
	private static boolean color;
	private boolean game;
	private boolean PVP;
	private boolean video = false;
	private static int[][] situation;
	private boolean easySelected;
	java.sql.ResultSet rs = null;
	java.sql.Statement smt = null;
	java.sql.Connection con = null;
	String fileName;
	AI ai;
	int[] xy;
	int ID = 0;
	int mouseX;
	int mouseY;
	static MyLabel[][] label;

	public Wuziqi() {
		Access();
		create("situation");
		xy = new int[2];
		easySelected = true;
		game = false;
		color = true;
		PVP = true;
		situation = new int[15][15];

		// ai.Access();
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				situation[i][j] = 0;
			}
		}
		display = new Display();
		image = new Image(display, "Icon.gif");
		shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.NO_REDRAW_RESIZE);
		shell.setImage(image);
		jiemian();
		luxiang();
		mydialog = new Mydialog(shell);
		gc = new GC(shell);
		menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		gameMenuItem = new MenuItem(menu, SWT.CASCADE);
		gameMenuItem.setText("游戏(&G)");
		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuItem.setMenu(gameMenu);
		{
			PVCMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			PVCMenuItem.setText("以计算机为对手的新游戏(&N)	F2");
			PVCMenuItem.setAccelerator(SWT.F2);
			{
				PVCMenu = new Menu(shell, SWT.DROP_DOWN);
				PVCMenuItem.setMenu(PVCMenu);
				useBlackMenuItem = new MenuItem(PVCMenu, SWT.PUSH);
				useBlackMenuItem.setText("使用黑子");
				useBlackMenuItem.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						// TODO Auto-generated AI stub
						selectAI();
						if (game) {
							giveUp();
						}
						close("LastReplay");
						color = true;
						game = true;
						PVP = false;
						giveUpMenuItem.setEnabled(true);
						cancelItem.setEnabled(true);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated AI stub

					}
				});
				useWhiteMenuItem = new MenuItem(PVCMenu, SWT.PUSH);
				useWhiteMenuItem.setText("使用白子");
				useWhiteMenuItem.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						// TODO Auto-generated AI stub
						selectAI();
						if (game) {
							giveUp();
						}
						close("LastReplay");
						color = false;
						game = true;
						PVP = false;
						giveUpMenuItem.setEnabled(true);
						cancelItem.setEnabled(true);
						{
							if (color) {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							} else {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_BLACK));
							}
							xy = ai.getxy(x, y);
							x = xy[0];
							y = xy[1];
							System.out.println();
							// ai.refresh();
							if (!color) {
								situation[x][y] = 1;
								ID = insert(ID, 1, x, y);
							} else {
								situation[x][y] = 2;
								ID = insert(ID, 2, x, y);
							}

							/*
							 * for (i = 0; i < 15; i++) { for (j = 0; j < 15;
							 * j++) { System.out.print(situation[j][i] + " "); }
							 * System.out.println(); } System.out.println();
							 */

							gc.fillOval(x * 20, y * 20, 20, 20);
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated AI stub

					}
				});
			}
			PVPMenuItem = new MenuItem(gameMenu, SWT.PUSH);
			PVPMenuItem.setText("以人为对手的新游戏(&M)	F3");
			PVPMenuItem.setAccelerator(SWT.F3);
			PVPMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub
					if (game) {
						giveUp();
					}
					close("LastReplay");
					game = true;
					PVP = true;
					giveUpMenuItem.setEnabled(true);
					cancelItem.setEnabled(true);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub

				}
			});
			new MenuItem(gameMenu, SWT.SEPARATOR);
			cancelItem = new MenuItem(gameMenu, SWT.CASCADE);
			cancelItem.setText("撤销(&U)");
			cancelItem.setEnabled(false);
			cancelItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					String sql = "select * from situation where ID=" + (ID - 1);
					try {
						rs = smt.executeQuery(sql);
						while (rs.next()) {
							x = rs.getInt("x");
							y = rs.getInt("y");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					situation[x][y] = 0;
					label[x][y].redraw();
					ID -= 1;
					String sql1 = "select * from situation where ID="
							+ (ID - 1);
					try {
						rs = smt.executeQuery(sql1);
						while (rs.next()) {
							x = rs.getInt("x");
							y = rs.getInt("y");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					situation[x][y] = 0;
					label[x][y].redraw();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			new MenuItem(gameMenu, SWT.SEPARATOR);
			selectAiItem1 = new MenuItem(gameMenu, SWT.RADIO);
			selectAiItem1.setText("初级");
			selectAiItem1.setSelection(true);
			selectAiItem1.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub
					/*
					 * FileDialog dialog = new FileDialog(shell, SWT.OPEN);
					 * dialog.setFilterPath("");// 设置默认的路径
					 * dialog.setText("选择AI");// 设置对话框的标题
					 * dialog.setFileName("");// 设置默认的文件名
					 * dialog.setFilterNames(new String[] { "(*.jar)", "(*.*)"
					 * });// 设置扩展名 dialog.setFilterExtensions(new String[] {
					 * "*.jar", "*.*" });// 设置文件扩展名 if ((fileName =
					 * dialog.open()) != null) { fileName =
					 * fileName.replaceAll("\\\\", "\\\\\\\\"); }
					 */
					easySelected = true;
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub

				}
			});
			selectAiItem2 = new MenuItem(gameMenu, SWT.RADIO);
			selectAiItem2.setText("高级");
			selectAiItem2.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					easySelected = false;
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			new MenuItem(gameMenu, SWT.SEPARATOR);
			giveUpMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			giveUpMenuItem.setText("放弃(&R)");
			giveUpMenuItem.setEnabled(false);
			giveUpMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub
					giveUp();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub

				}
			});
			exitMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			exitMenuItem.setText("退出(&X)");
			exitMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub
					close("situation");
					System.exit(0);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated AI stub

				}
			});
		}
		HelpMenuItem = new MenuItem(menu, SWT.CASCADE);
		HelpMenuItem.setText("帮助(&H)");
		helpMenu = new Menu(shell, SWT.DROP_DOWN);
		HelpMenuItem.setMenu(helpMenu);
		{
			helpMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
			helpMenuItem.setText("查看帮助(&V)	F1");
			helpMenuItem.setAccelerator(SWT.F1);
			helpMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						Runtime.getRuntime().exec(
								"cmd /c start " + "wuziqi.chm");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
			aboutMenuItem.setText("关于\"五子棋\"(&A)");
			aboutMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					mydialog.open();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		shell.setBackgroundImage(new Image(display, "background.jpg"));
		// shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		shell.addShellListener(new ShellListener() {

			@Override
			public void shellIconified(ShellEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellDeiconified(ShellEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellDeactivated(ShellEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellClosed(ShellEvent arg0) {
				// TODO Auto-generated method stub
				if (!ifexist()) {
					create("LastReplay");
					copy();
				}
				close("situation");
			}

			@Override
			public void shellActivated(ShellEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setLayout(null);
		shell.setSize(650, 650);
		center(shell);
		shell.setVisible(true);
		shell.setText("五子棋");
		shell.open();
		setdown();
		while (!shell.isDisposed()) { // 如果主窗体没有关闭则一直循环
			if (!display.readAndDispatch()) { // 如果display不忙
				display.sleep(); // 休眠
			}
		}
		display.dispose(); // 销毁display
	}

	private void setdown() {
		shell.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated AI stub

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated AI stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated AI stub

			}
		});
	}

	private void refresh() {
		game = false;
		color = true;
		PVP = true;
		ID = 0;
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				situation[i][j] = 0;
				label[i][j].redraw();
			}
		}
		create("LastReplay");
		copy();
		close("situation");
		create("situation");
		giveUpMenuItem.setEnabled(false);
		cancelItem.setEnabled(false);
	}

	private int win() {
		int a;
		for (a = 1; a <= 2; a++) {
			for (i = 0; i <= 10; i++) {
				for (j = 0; j <= 10; j++) {
					// "\"形
					if (situation[i][j] == a && situation[i + 1][j + 1] == a
							&& situation[i + 2][j + 2] == a
							&& situation[i + 3][j + 3] == a
							&& situation[i + 4][j + 4] == a) {
						return a;
					}
				}
			}
			for (i = 14; i >= 4; i--) {
				for (j = 0; j <= 10; j++) {
					// "/"形
					if (situation[i][j] == a && situation[i - 1][j + 1] == a
							&& situation[i - 2][j + 2] == a
							&& situation[i - 3][j + 3] == a
							&& situation[i - 4][j + 4] == a) {
						return a;
					}
				}
			}
			for (i = 0; i <= 10; i++) {
				for (j = 0; j <= 14; j++) {
					// "——"形
					if (situation[i][j] == a && situation[i + 1][j] == a
							&& situation[i + 2][j] == a
							&& situation[i + 3][j] == a
							&& situation[i + 4][j] == a) {
						return a;
					}
				}
			}
			for (i = 0; i <= 14; i++) {
				for (j = 0; j <= 10; j++) {
					// "|"形
					if (situation[i][j] == a && situation[i][j + 1] == a
							&& situation[i][j + 2] == a
							&& situation[i][j + 3] == a
							&& situation[i][j + 4] == a) {
						return a;
					}
				}
			}
		}
		return 0;
	}

	public static void getsituation(int array[][]) {
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				array[i][j] = situation[i][j];
			}
		}
	}

	public static int getcolor() {
		if (color) {
			return 2;// AI为白色返回2
		}
		return 1;// AI为黑色返回1
	}

	private void giveUp() {
		MessageBox box1 = new MessageBox(shell, SWT.YES | SWT.NO);
		box1.setText("放弃游戏");
		box1.setMessage("是否确实想放弃本局游戏？");
		int choice = box1.open();
		if (choice == SWT.YES) {
			if (color) {
				MessageBox box2 = new MessageBox(shell);
				box2.setText("游戏结束");
				box2.setMessage("黑棋放弃，白棋获胜！");
				box2.open();
			} else {
				MessageBox box3 = new MessageBox(shell);
				box3.setText("游戏结束");
				box3.setMessage("白棋放弃，黑棋获胜！");
				box3.open();
			}
			refresh();
		}
	}

	private void selectAI() {
		if (easySelected) {
			try {
				ai = (EasyAI) Class.forName("AI.EasyAI").newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			try {
				ai = (HardAI) Class.forName("AI.HardAI").newInstance();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void center(Shell shell) {
		// 获取屏幕高度和宽度
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
		// 获取对象窗口高度和宽度
		int shellH = shell.getBounds().height;
		int shellW = shell.getBounds().width;

		// 如果对象窗口高度超出屏幕高度，则强制其与屏幕等高
		if (shellH > screenH)
			shellH = screenH;

		// 如果对象窗口宽度超出屏幕宽度，则强制其与屏幕等宽
		if (shellW > screenW)
			shellW = screenW;

		// 定位对象窗口坐标
		shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));
	}

	public void jiemian() {
		label = new MyLabel[15][15];
		imageH = new Image(display, "黑棋.gif");
		imageB = new Image(display, "白棋.gif");
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				label[i][j] = new MyLabel(shell, SWT.NULL);
				label[i][j].x = i;
				label[i][j].y = j;
			}
		}
		for (i = 1; i < 14; i++) {
			for (j = 1; j < 14; j++) {
				label[i][j].addPaintListener(new PaintListener() {

					@Override
					public void paintControl(PaintEvent e) {
						// TODO Auto-generated method stub
						gc = new GC((Control) e.getSource());
						Rectangle clientArea = ((Control) e.getSource())
								.getBounds();
						gc.setBackground(Display.getCurrent().getSystemColor(
								SWT.COLOR_BLACK));
						gc.drawLine(clientArea.width / 2, 0,
								clientArea.width / 2, clientArea.height);
						gc.drawLine(0, clientArea.height / 2, clientArea.width,
								clientArea.height / 2);
						MyLabel label = (MyLabel) (e.getSource());
						if (situation[label.x][label.y] == 1) {
							final Rectangle bounds = imageH.getBounds();
							int picwidth = bounds.width;// 图片宽
							int picheight = bounds.height;// 图片高
							gc.drawImage(imageH, 0, 0, picwidth, picheight,
									clientArea.width / 8,
									clientArea.height / 8,
									clientArea.width * 3 / 4,
									clientArea.height * 3 / 4);
						} else if (situation[label.x][label.y] == 2) {
							final Rectangle bounds = imageB.getBounds();
							int picwidth = bounds.width;// 图片宽
							int picheight = bounds.height;// 图片高
							gc.drawImage(imageB, 0, 0, picwidth, picheight,
									clientArea.width / 8,
									clientArea.height / 8,
									clientArea.width * 3 / 4,
									clientArea.height * 3 / 4);
						}
						gc.dispose();
					}
				});
			}
		}

		for (i = 1; i < 14; i++) {
			j = 0;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, clientArea.height);
					gc.drawLine(0, clientArea.height / 2, clientArea.width,
							clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}
		for (i = 1; i < 14; i++) {
			j = 14;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, 0);
					gc.drawLine(0, clientArea.height / 2, clientArea.width,
							clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		for (j = 1; j < 14; j++) {
			i = 0;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, 0, clientArea.width / 2,
							clientArea.height);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width, clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}
		for (j = 1; j < 14; j++) {
			i = 14;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, 0, clientArea.width / 2,
							clientArea.height);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2, 0,
							clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		{
			i = 0;
			j = 0;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, clientArea.height);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width, clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		{
			i = 0;
			j = 14;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, 0);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width, clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		{
			i = 14;
			j = 0;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, clientArea.height);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2, 0,
							clientArea.height / 2);
					MyLabel label = (MyLabel) (e.getSource());
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		{
			i = 14;
			j = 14;
			label[i][j].addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					MyLabel label = (MyLabel) (e.getSource());
					gc = new GC((Control) e.getSource());
					Rectangle clientArea = ((Control) e.getSource())
							.getBounds();
					gc.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_BLACK));
					gc.drawLine(clientArea.width / 2, clientArea.height / 2,
							clientArea.width / 2, 0);
					gc.drawLine(clientArea.width / 2, clientArea.height / 2, 0,
							clientArea.height / 2);
					if (situation[label.x][label.y] == 1) {
						final Rectangle bounds = imageH.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageH, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					} else if (situation[label.x][label.y] == 2) {
						final Rectangle bounds = imageB.getBounds();
						int picwidth = bounds.width;// 图片宽
						int picheight = bounds.height;// 图片高
						gc.drawImage(imageB, 0, 0, picwidth, picheight,
								clientArea.width / 8, clientArea.height / 8,
								clientArea.width * 3 / 4,
								clientArea.height * 3 / 4);
					}
					gc.dispose();
				}
			});
		}

		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				label[i][j].setBounds(40 * i + 10, 40 * j + 10, 40, 40);
				label[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseUp(MouseEvent e) {
						// TODO Auto-generated method stub
						MyLabel label = (MyLabel) (e.getSource());
						if (game) {
							if (PVP) {
								if (situation[label.x][label.y] != 1
										&& situation[label.x][label.y] != 2) {
									gc = new GC(((Control) e.getSource()));
									Rectangle clientArea = (((Control) e
											.getSource())).getBounds();
									if (color) {
										situation[label.x][label.y] = 1;
										ID = insert(ID, 1, label.x, label.y);
										final Rectangle bounds = imageH
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageH, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
										gc.dispose();
									} else {
										situation[label.x][label.y] = 2;
										ID = insert(ID, 2, label.x, label.y);
										final Rectangle bounds = imageB
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageB, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
										gc.dispose();
									}
									color = !color;
									switch (win()) {
									case 0:
										break;
									case 1:
										MessageBox box4 = new MessageBox(shell);
										box4.setText("游戏结束");
										box4.setMessage("黑棋获胜！");
										box4.open();
										refresh();
										break;
									case 2:
										MessageBox box5 = new MessageBox(shell);
										box5.setText("游戏结束");
										box5.setMessage("白棋获胜！");
										box5.open();
										refresh();
										break;
									}

								}
							}// 与计算机对战
							else {
								if (situation[label.x][label.y] != 1
										&& situation[label.x][label.y] != 2) {
									// AI取白棋
									gc = new GC(((Control) e.getSource()));
									Rectangle clientArea = (((Control) e
											.getSource())).getBounds();
									if (color) {
										situation[label.x][label.y] = 1;
										ID = insert(ID, 1, label.x, label.y);
										final Rectangle bounds = imageH
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageH, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
									} else {
										situation[label.x][label.y] = 2;
										ID = insert(ID, 2, label.x, label.y);
										final Rectangle bounds = imageB
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageB, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
									}
									gc.dispose();
									xy = ai.getxy(x, y);
									x = xy[0];
									y = xy[1];
									gc = new GC(Wuziqi.label[x][y]);
									clientArea = Wuziqi.label[x][y].getBounds();
									if (!color) {
										situation[x][y] = 1;
										ID = insert(ID, 1, x, y);
										final Rectangle bounds = imageH
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageH, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
									} else {
										situation[x][y] = 2;
										ID = insert(ID, 2, x, y);
										final Rectangle bounds = imageB
												.getBounds();
										int picwidth = bounds.width;// 图片宽
										int picheight = bounds.height;// 图片高
										gc.drawImage(imageB, 0, 0, picwidth,
												picheight,
												clientArea.width / 8,
												clientArea.height / 8,
												clientArea.width * 3 / 4,
												clientArea.height * 3 / 4);
									}
									gc.dispose();
								}
								switch (win()) {
								case 0:
									break;
								case 1:
									MessageBox box4 = new MessageBox(shell);
									box4.setText("游戏结束");
									box4.setMessage("黑棋获胜！");
									box4.open();
									refresh();
									break;
								case 2:
									MessageBox box5 = new MessageBox(shell);
									box5.setText("游戏结束");
									box5.setMessage("白棋获胜！");
									box5.open();
									refresh();
									break;
								}
							}
						}
					}

					@Override
					public void mouseDown(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				label[i][j].addMouseTrackListener(new MouseTrackListener() {

					@Override
					public void mouseHover(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						((Control) e.getSource()).redraw();
					}

					@Override
					public void mouseEnter(MouseEvent e) {
						if (game) {
							// TODO Auto-generated method stub
							gc = new GC((Control) e.getSource());
							Rectangle clientArea = ((Control) e.getSource())
									.getBounds();
							gc.drawLine(0, 0, clientArea.width / 3, 0);
							gc.drawLine(clientArea.width * 2 / 3, 0,
									clientArea.width, 0);
							gc.drawLine(0, 0, 0, clientArea.height / 3);
							gc.drawLine(0, clientArea.height * 2 / 3, 0,
									clientArea.height);
							gc.drawLine(clientArea.width - 1, 0,
									clientArea.width - 1, clientArea.height / 3);
							gc.drawLine(clientArea.width - 1,
									clientArea.height * 2 / 3,
									clientArea.width - 1, clientArea.height);
							gc.drawLine(0, clientArea.height - 1,
									clientArea.width / 3, clientArea.height - 1);
							gc.drawLine(clientArea.width * 2 / 3,
									clientArea.height - 1, clientArea.width,
									clientArea.height - 1);
							gc.dispose();
						}
					}
				});
			}
		}
	}

	public void luxiang() {
		startbutton = new Button(shell, SWT.CENTER);
		startbutton.setVisible(true);
		startbutton.setBounds(650, 100, 50, 30);
		ImageLoader loader = new ImageLoader();
		ImageData[] imageData = loader.load("start.jpg");
		imageStart = new Image(display, imageData[0].scaledTo(50, 30));
		loader = new ImageLoader();
		imageData = loader.load("stop.gif");
		imageStop = new Image(display, imageData[0].scaledTo(50, 30));
		startbutton.setImage(imageStart);
		startbutton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (!video) {
					startbutton.setImage(imageStop);
					video = !video;
				} else {
					startbutton.setImage(imageStart);
					video=!video;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void main(String[] args) {
		new Wuziqi();
	}

	public void Access() {
		String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=.\\gobang.accdb";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			System.out.println("forName方法运行成功！");
		} catch (ClassNotFoundException e) {
			System.out.println("Error1:" + e);
		}
		try {
			con = DriverManager.getConnection(url);
			System.out.println("getConnection方法运行成功！");
			smt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			System.out.println("createStatement方法运行成功!");
		} catch (SQLException e) {
			System.out.print("Error2:" + e);
		}
	}

	public void create(String s) {
		String sql = "create table " + s + "(" + "ID integer,"
				+ "color integer," + "x integer," + "y integer" + ")";
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insert(int ID, int color, int x, int y) {
		String sql = "insert into situation (ID,color,x,y) values (" + ID + ","
				+ color + "," + x + "," + y + ")";
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ID + 1;
	}

	public void update(int ID, int color, int x, int y) {
		String sql = "update situation set " + ",color=" + color + ",x=" + x
				+ ",y=" + y + " where ID=" + ID;
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(String s) {

		String sql = "drop table " + s;
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void copy() {
		String sql = "insert into LastReplay select  * from situation";
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean ifexist() {
		String sql = "select * from LastReplay";
		try {
			rs = smt.executeQuery(sql);
			rs.afterLast();
			rs.getRow();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}
