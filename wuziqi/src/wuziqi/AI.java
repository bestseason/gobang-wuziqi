package wuziqi;

public class AI {
	int situation[][], possible[][], score[][];
	int x, y, i, j, maxscore, N, n, m, color;
	static final int UP = 1;
	static final int RIGHTUP = 2;
	static final int RIGHT = 3;
	static final int RIGHTDOWN = 4;
	static final int DOWN = 5;
	static final int LEFTDOWN = 6;
	static final int LEFT = 7;
	static final int LEFTUP = 8;

	public AI() {
		situation = new int[15][15];
		possible = new int[15][15];
		score = new int[15][15];
		maxscore = 0;
		N = 0;
		n = 0;
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				possible[i][j] = 0;
				score[i][j] = 0;
				situation[i][j] = 0;
			}
		}
	}

	public void calculate() {
		Wuziqi.getsituation(situation);
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				if (situation[i][j] == 0) {
					possible[i][j] = 1;
					N++;
				}
			}
		}

		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				if (possible[i][j] == 1) {
					score[i][j] = getscore(i, j);
				}
			}
		}
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				if (score[i][j] > maxscore) {
					maxscore = score[i][j];
					x = i;
					y = j;
				}
			}
		}
		if (N == 225) {
			x = 7;
			y = 7;
		}
	}

	public int getscorex(int i, int j, int direction) {
		int score = 0;
		m = 0;
		n = 0;
		score = getscore(i, j, direction);
		n = 0;
		m = 0;
		return score;
	}

	public int getscore(int i, int j, int direction) {
		int score = 0;

		color = Wuziqi.getcolor();
		int a;
		if (color == 1) {
			a = 2;
		} else {
			a = 1;
		}
		// 上方
		if (direction == UP) {
			if (j - 1 >= 0) {
				if (situation[i][j - 1] == a && m == 0) {
					n++;
					score += getscore(i, j - 1, UP) + Math.pow(4, n);
				}
				if (situation[i][j - 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i][j - 1] == color && n == 0) {
					m++;
					score += getscore(i, j - 1, UP) + Math.pow(4, m) - 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 右上
		if (direction == RIGHTUP) {
			if (i + 1 <= 14 && j - 1 >= 0) {
				if (situation[i + 1][j - 1] == a && m == 0) {
					n++;
					score += getscore(i + 1, j - 1, RIGHTUP) + Math.pow(4, n);
				}
				if (situation[i + 1][j - 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i + 1][j - 1] == color && n == 0) {
					m++;
					score += getscore(i + 1, j - 1, RIGHTUP) + Math.pow(4, m)
							- 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 右方
		if (direction == RIGHT) {
			if (i + 1 <= 14) {
				if (situation[i + 1][j] == a && m == 0) {
					n++;
					score += getscore(i + 1, j, RIGHT) + Math.pow(4, n);
				}
				if (situation[i + 1][j] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i + 1][j] == color && n == 0) {
					m++;
					score += getscore(i + 1, j, RIGHT) + Math.pow(4, m) - 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 右下
		if (direction == RIGHTDOWN) {
			if (i + 1 <= 14 && j + 1 <= 14) {
				if (situation[i + 1][j + 1] == a && m == 0) {
					n++;
					score += getscore(i + 1, j + 1, RIGHTDOWN) + Math.pow(4, n);
				}
				if (situation[i + 1][j + 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i + 1][j + 1] == color && n == 0) {
					m++;
					score += getscore(i + 1, j + 1, RIGHTDOWN) + Math.pow(4, m)
							- 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 下方
		if (direction == DOWN) {
			if (j + 1 <= 14) {
				if (situation[i][j + 1] == a && m == 0) {
					n++;
					score += getscore(i, j + 1, DOWN) + Math.pow(4, n);
				}
				if (situation[i][j + 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i][j + 1] == color && n == 0) {
					m++;
					score += getscore(i, j + 1, DOWN) + Math.pow(4, m) - 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 左下
		if (direction == LEFTDOWN) {
			if (i - 1 >= 0 && j + 1 <= 14) {
				if (situation[i - 1][j + 1] == a && m == 0) {
					n++;
					score += getscore(i - 1, j + 1, LEFTDOWN) + Math.pow(4, n);
				}
				if (situation[i - 1][j + 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i - 1][j + 1] == color && n == 0) {
					m++;
					score += getscore(i - 1, j + 1, LEFTDOWN) + Math.pow(4, m)
							- 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 左方
		if (direction == LEFT) {
			if (i - 1 >= 0) {
				if (situation[i - 1][j] == a && m == 0) {
					n++;
					score += getscore(i - 1, j, LEFT) + Math.pow(4, n);

				}
				if (situation[i - 1][j] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i - 1][j] == color && n == 0) {
					m++;
					score += getscore(i - 1, j, LEFT) + Math.pow(4, m) - 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		// 左上
		if (direction == LEFTUP) {
			if (i - 1 >= 0 && j - 1 >= 0) {
				if (situation[i - 1][j - 1] == a && m == 0) {
					n++;
					score += getscore(i - 1, j - 1, LEFTUP) + Math.pow(4, n);
				}
				if (situation[i - 1][j - 1] == color && n > 1) {
					score -= Math.pow(4, n);
				}
				if (situation[i - 1][j - 1] == color && n == 0) {
					m++;
					score += getscore(i - 1, j - 1, LEFTUP) + Math.pow(4, m)
							- 1;
				}
				score += 1;
				return score;
			}
			return 0;
		}
		return 0;
	}

	public int getscore(int i, int j) {
		int score = 0;
		score = getscorex(i, j, UP) + getscorex(i, j, RIGHTUP)
				+ getscorex(i, j, RIGHT) + getscorex(i, j, RIGHTDOWN)
				+ getscorex(i, j, DOWN) + getscorex(i, j, LEFTDOWN)
				+ getscorex(i, j, LEFT) + getscorex(i, j, LEFTUP);
		if (getscorex(i, j, UP) + getscorex(i, j, DOWN) >= 41
				|| getscorex(i, j, RIGHTUP) + getscorex(i, j, LEFTDOWN) >= 41
				|| getscorex(i, j, RIGHT) + getscorex(i, j, LEFT) >= 41
				|| getscorex(i, j, LEFTUP) + getscorex(i, j, RIGHTDOWN) >= 41) {
			score += 100;
		}
		return score;

	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public void refresh() {
		n = 0;
		maxscore = 0;
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				possible[i][j] = 0;
				score[i][j] = 0;
				situation[i][j] = 0;
			}
		}
	}

	public void printscore() {
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				System.out.printf("%4d", score[j][i]);
			}
			System.out.println();
		}
	}
}
