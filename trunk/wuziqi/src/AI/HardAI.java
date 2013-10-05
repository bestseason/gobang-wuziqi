package AI;

import wuziqi.AI;
import wuziqi.Wuziqi;

public class HardAI implements AI {
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

	public HardAI() {
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

	public int getscore(int i, int j) {
		int score = 0;
		if ((score = cheng5(i, j)) != 0) {
			return score;
		} else if ((score = si4huo3(i, j)) != 0) {
			return score;
		} else if ((score = huo4(i, j)) != 0) {
			return score;
		} else if ((score = shuangsi4_si4(i, j)) == 90) {
			return score;
		} else if ((score = shuanghuo3_huo3(i, j)) == 80) {
			return score;
		} else if ((score = si3huo3(i, j)) != 0) {
			return score;
		} else if ((score = shuangsi4_si4(i, j)) == 60) {
			return score;
		} else if ((score = shuanghuo3_huo3(i, j)) == 50) {
			return score;
		}else if ((score = shuanghuo2_huo2(i, j)) == 40) {
			return score;
		}else if ((score = si3(i, j)) != 0) {
			return score;
		}else if ((score = shuanghuo2_huo2(i, j)) == 20) {
			return score;
		}else if ((score = si2(i, j)) != 0) {
			return score;
		}
		return score;
	}

	public int cheng5(int x, int y) {
		int score = 0;
		int i = 0, a, n = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				i = 0;
				do {
					i++;
					n++;
					if (y - i < 0) {
						break;
					}
				} while (situation[x][y - i] == a);
				if (n >= 5) {
					score += 100;
					return score;
				} else {
					n = 0;
					i = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				i = 0;
				do {
					i++;
					n++;
					if (x - i < 0 || y + i >= 15) {
						break;
					}
				} while (situation[x - i][y + i] == a);
				if (n >= 5) {
					score += 100;
					return score;
				} else {
					n = 0;
					i = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i >= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				i = 0;
				do {
					i++;
					n++;
					if (x - i < 0) {
						break;
					}
				} while (situation[x - i][y] == a);
				if (n >= 5) {
					score += 100;
					return score;
				} else {
					n = 0;
					i = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				i = 0;
				do {
					i++;
					n++;
					if (x - i < 0 || y - i < 0) {
						break;
					}
				} while (situation[x - i][y - i] == a);
				if (n >= 5) {
					score += 100;
					return score;
				} else {
					n = 0;
					i = 0;
				}
			}
		}
		return score - 1;
	}

	public int huo4(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 4 && situation[x][y + i] == 0
							&& situation[x][y - j] == 0) {
						score += 90;
						return score;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 4 && situation[x + i][y - i] == 0
							&& situation[x - j][y + j] == 0) {
						score += 90;
						return score;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i >= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - j >= 0) {
					if (n >= 4 && situation[x + i][y] == 0
							&& situation[x - j][y] == 0) {
						score += 90;
						return score;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - j >= 0 && y + i < 15 && y - j >= 0) {
					if (n >= 4 && situation[x + i][y + i] == 0
							&& situation[x - j][y - j] == 0) {
						score += 90;
						return score;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
		}
		return score - 1;
	}

	public int shuangsi4_si4(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 4
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y + i < 15) {
					if (n >= 4 && situation[x][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y - j >= 0) {
					if (n >= 4 && situation[x][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 4
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y - i >= 0) {
					if (n >= 4 && situation[x + i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y + j < 15) {
					if (n >= 4 && situation[x - i][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i <= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - i >= 0) {
					if (n >= 4
							&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15) {
					if (n >= 4 && situation[x + i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - i >= 0) {
					if (n >= 4 && situation[x - i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 4
							&& (situation[x + i][y + i] == 0 || situation[x - j][y
									- j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y + j < 15) {
					if (n >= 4 && situation[x + i][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y - i >= 0) {
					if (n >= 4 && situation[x - i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			if (mark >= 2) {
				score += 90;
				return score;
			} else if (mark == 1) {
				score += 60;
				return score;
			}
		}
		return score - 1;
	}

	public int si4huo3(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark3 = 0, mark4 = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n == 3
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark3 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y + i < 15) {
					if (n >= 4 && situation[x][y + i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y - j >= 0) {
					if (n >= 4 && situation[x][y - i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark3 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y - i >= 0) {
					if (n >= 4 && situation[x + i][y - i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y + j < 15) {
					if (n >= 4 && situation[x - i][y + i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
				{// '！！'style
					do {
						i++;
						n++;
						if (x + i <= 15) {
							break;
						}
					} while (situation[x + i][y] == a);
					n--;
					do {
						j++;
						n++;
						if (x - j < 0) {
							break;
						}
					} while (situation[x - j][y] == a);
					if (x + i < 15 && x - i >= 0) {
						if (n >= 3
								&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
							mark3 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x + i < 15) {
						if (n >= 4 && situation[x + i][y] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x - i >= 0) {
						if (n >= 4 && situation[x - i][y] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
				{// '\'style
					do {
						i++;
						n++;
						if (x + i >= 15 || y + i >= 15) {
							break;
						}
					} while (situation[x + i][y + i] == a);
					n--;
					do {
						j++;
						n++;
						if (x - j < 0 || y - j < 0) {
							break;
						}
					} while (situation[x - j][y - j] == a);
					if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
						if (n >= 3
								&& (situation[x + i][y + i] == 0 || situation[x
										- j][y - j] == 0)) {
							mark3 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x + i < 15 && y + j < 15) {
						if (n >= 4 && situation[x + i][y + i] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x - j >= 0 && y - i >= 0) {
						if (n >= 4 && situation[x - i][y - i] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
			}
			if (mark3 >= 1 && mark4 >= 1) {
				score += 90;
				return score;
			}
		}
		return score - 1;
	}

	public int shuanghuo3_huo3(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 3
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i <= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y + i] == 0 || situation[x - j][y
									- j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			if (mark >= 2) {
				score += 80;
				return score;
			} else if (mark == 1) {
				score += 50;
				return score;
			}
		}
		return score - 1;
	}

	public int si3huo3(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark3 = 0, mark4 = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n == 3
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark3 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y + i < 15) {
					if (n >= 3 && situation[x][y + i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y - j >= 0) {
					if (n >= 3 && situation[x][y - i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark3 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y - i >= 0) {
					if (n >= 3 && situation[x + i][y - i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y + j < 15) {
					if (n >= 3 && situation[x - i][y + i] == 0) {
						mark4 += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
				{// '！！'style
					do {
						i++;
						n++;
						if (x + i <= 15) {
							break;
						}
					} while (situation[x + i][y] == a);
					n--;
					do {
						j++;
						n++;
						if (x - j < 0) {
							break;
						}
					} while (situation[x - j][y] == a);
					if (x + i < 15 && x - i >= 0) {
						if (n >= 3
								&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
							mark3 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x + i < 15) {
						if (n >= 3 && situation[x + i][y] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x - i >= 0) {
						if (n >= 3 && situation[x - i][y] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
				{// '\'style
					do {
						i++;
						n++;
						if (x + i >= 15 || y + i >= 15) {
							break;
						}
					} while (situation[x + i][y + i] == a);
					n--;
					do {
						j++;
						n++;
						if (x - j < 0 || y - j < 0) {
							break;
						}
					} while (situation[x - j][y - j] == a);
					if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
						if (n >= 3
								&& (situation[x + i][y + i] == 0 || situation[x
										- j][y - j] == 0)) {
							mark3 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x + i < 15 && y + j < 15) {
						if (n >= 3 && situation[x + i][y + i] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else if (x - j >= 0 && y - i >= 0) {
						if (n >= 3 && situation[x - i][y - i] == 0) {
							mark4 += 1;
							n = 0;
							i = 0;
							j = 0;
						} else {
							n = 0;
							i = 0;
							j = 0;
						}
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
			}
			if (mark3 >= 1 && mark4 >= 1) {
				score += 70;
				return score;
			}
		}
		return score - 1;
	}
	
	public int shuanghuo2_huo2(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 2
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 2
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i <= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - i >= 0) {
					if (n >= 2
							&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - i >= 0 && y + j < 15 && y - j >= 0) {
					if (n >= 2
							&& (situation[x + i][y + i] == 0 || situation[x - j][y
									- j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			if (mark >= 2) {
				score += 40;
				return score;
			} else if (mark == 1) {
				score += 20;
				return score;
			}
		}
		return score - 1;
	}

	public int si3(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 3
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y + i < 15) {
					if (n >= 3 && situation[x][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y - j >= 0) {
					if (n >= 3 && situation[x][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y - i >= 0) {
					if (n >= 3 && situation[x + i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y + j < 15) {
					if (n >= 3 && situation[x - i][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i <= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15) {
					if (n >= 3 && situation[x + i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - i >= 0) {
					if (n >= 3 && situation[x - i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 3
							&& (situation[x + i][y + i] == 0 || situation[x - j][y
									- j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y + j < 15) {
					if (n >= 3 && situation[x + i][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y - i >= 0) {
					if (n >= 3 && situation[x - i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			if (mark >= 1) {
				score += 30;
				return score;
			}
		}
		return score - 1;
	}
	
	public int si2(int x, int y) {
		int score = 0;
		int i = 0, j = 0, a, n = 0, mark = 0;
		for (a = 1; a <= 2; a++) {
			if (color == a) {
				score += 1;
			}
			{// '|'style
				do {
					i++;
					n++;
					if (y + i >= 15) {
						break;
					}
				} while (situation[x][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (y - j < 0) {
						break;
					}
				} while (situation[x][y - j] == a);
				if (y + i < 15 && y - j >= 0) {
					if (n >= 2
							&& (situation[x][y + i] == 0 || situation[x][y - j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y + i < 15) {
					if (n >= 2 && situation[x][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (y - j >= 0) {
					if (n >= 2 && situation[x][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '/'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y - i < 0) {
						break;
					}
				} while (situation[x + i][y - i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y + j >= 15) {
						break;
					}
				} while (situation[x - j][y + j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 2
							&& (situation[x + i][y - i] == 0 || situation[x - j][y
									+ j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y - i >= 0) {
					if (n >= 2 && situation[x + i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y + j < 15) {
					if (n >= 2 && situation[x - j][y + j] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '！！'style
				do {
					i++;
					n++;
					if (x + i <= 15) {
						break;
					}
				} while (situation[x + i][y] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0) {
						break;
					}
				} while (situation[x - j][y] == a);
				if (x + i < 15 && x - i >= 0) {
					if (n >= 2
							&& (situation[x + i][y] == 0 || situation[x - j][y] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15) {
					if (n >= 2 && situation[x + i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - i >= 0) {
					if (n >= 2 && situation[x - i][y] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			{// '\'style
				do {
					i++;
					n++;
					if (x + i >= 15 || y + i >= 15) {
						break;
					}
				} while (situation[x + i][y + i] == a);
				n--;
				do {
					j++;
					n++;
					if (x - j < 0 || y - j < 0) {
						break;
					}
				} while (situation[x - j][y - j] == a);
				if (x + i < 15 && x - j >= 0 && y + j < 15 && y - i >= 0) {
					if (n >= 2
							&& (situation[x + i][y + i] == 0 || situation[x - j][y
									- j] == 0)) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x + i < 15 && y + j < 15) {
					if (n >= 2 && situation[x + i][y + i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else if (x - j >= 0 && y - i >= 0) {
					if (n >= 2 && situation[x - i][y - i] == 0) {
						mark += 1;
						n = 0;
						i = 0;
						j = 0;
					} else {
						n = 0;
						i = 0;
						j = 0;
					}
				} else {
					n = 0;
					i = 0;
					j = 0;
				}
			}
			if (mark >= 1) {
				score += 10;
				return score;
			}
		}
		return score - 1;
	}

	public void calculate() {
		Wuziqi.getsituation(situation);
		color = Wuziqi.getcolor();
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
		printscore();
		refresh();
	}

	public void printscore() {
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				System.out.printf("%4d", score[j][i]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void refresh() {
		n = 0;
		N = 0;
		maxscore = 0;
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				possible[i][j] = 0;
				score[i][j] = 0;
			}
		}
	}

	@Override
	public int[] getxy(int x, int y) {
		// TODO Auto-generated method stub
		int[] xy = new int[2];
		calculate();
		xy[0] = this.x;
		xy[1] = this.y;
		return xy;
	}
}
