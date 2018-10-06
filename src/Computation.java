import java.util.*;

public class Computation {
    int diag1init[] = {3, 0, 4, 0, 5, 0, 5, 1, 5, 2, 5, 3};
    int diag2init[] = {2, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0, 3};
    int diagSize[] = {4, 5, 6, 6, 5, 4};
    boolean firstMinimaxCall = true;
    ArrayList < Integer > childrenVals = new ArrayList < Integer > ();

    public void refresh() {
        firstMinimaxCall = true;
        childrenVals.clear();
    }

    public int[] minimax(GameState game, int depth, int alpha, int beta) {
        ArrayList<GameState> children = game.generateChildren();

        int score;
        int bestCol = -1;

        if (children.isEmpty() || depth == 0 || winCheck(game) != 0) {
            score = evaluate(game);
            return new int[] {score, bestCol};
        } else {
            for (GameState child: children) {
                if (game.maxPlayer) {
                    score = minimax(child, depth - 1, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestCol = getDiff(game, child);
                    }
                } else {
                    score = minimax(child, depth - 1, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestCol = getDiff(game,child);
                    }
                }
                if (alpha >= beta) break;
            }
            return new int[] {game.maxPlayer ? alpha : beta, bestCol};
        }
    }

    public int getDiff(GameState parent, GameState child) {
        int[][] parentBoard = parent.board;
        int[][] childBoard = child.board;

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                if(parentBoard[i][j] != childBoard[i][j])
                    return j;
            }
        }

        return -1;
    }

    public int winCheck(GameState game) {
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 4; b++) {
                if (game.board[a][b] == 1 && game.board[a][b + 1] == 1 && game.board[a][b + 2] == 1 && game.board[a][b + 3] == 1)
                    return 1;
                if (game.board[a][b] == 2 && game.board[a][b + 1] == 2 && game.board[a][b + 2] == 2 && game.board[a][b + 3] == 2)
                    return 2;
            }
        }
        for (int a = 0; a < 7; a++) {
            for (int b = 0; b < 3; b++) {
                if (game.board[b][a] == 1 && game.board[b + 1][a] == 1 && game.board[b + 2][a] == 1 && game.board[b + 3][a] == 1)
                    return 1;
                if (game.board[b][a] == 2 && game.board[b + 1][a] == 2 && game.board[b + 2][a] == 2 && game.board[b + 3][a] == 2)
                    return 2;
            }
        }
        for (int a = 0, init = 0; a < 6; a++, init += 2) {
            int diag[] = returnDiag1(game, init, diagSize[a]);
            for (int b = 0; b < diagSize[a] - 3; b++) {
                if (diag[b] == 1 && diag[b + 1] == 1 && diag[b + 2] == 1 && diag[b + 3] == 1)
                    return 1;
                if (diag[b] == 2 && diag[b + 1] == 2 && diag[b + 2] == 2 && diag[b + 3] == 2)
                    return 2;
            }
        }
        for (int a = 0, init = 0; a < 6; a++, init += 2) {
            int diag[] = returnDiag2(game, init, diagSize[a]);
            for (int b = 0; b < diagSize[a] - 3; b++) {
                if (diag[b] == 1 && diag[b + 1] == 1 && diag[b + 2] == 1 && diag[b + 3] == 1)
                    return 1;
                if (diag[b] == 2 && diag[b + 1] == 2 && diag[b + 2] == 2 && diag[b + 3] == 2)
                    return 2;
            }
        }
        return 0;
    }

    public int[] returnDiag1(GameState game, int init, int diagSize) {
        int diag[] = new int[diagSize];
        for (int b = 0, c = diag1init[init], d = diag1init[init + 1]; b < diagSize; b++, c--, d++) {
            diag[b] = game.board[c][d];
        }
        return diag;
    }

    public int[] returnDiag2(GameState game, int init, int diagSize) {
        int diag[] = new int[diagSize];
        for (int b = 0, c = diag2init[init], d = diag2init[init + 1]; b < diagSize; b++, c++, d++) {
            diag[b] = game.board[c][d];
        }
        return diag;
    }

    public boolean boardFilled(GameState game) {
        boolean decider = true;
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 7; b++) {
                if (game.board[a][b] == 0)
                    decider = false;
            }
        }
        return decider;
    }

    public boolean canMove(int row, int col) {
        if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
            return false;
        }
        return true;
    }

    public int check3InARow(int player, GameState game) {

        int times = 0;

        // Check for 3 consecutive checkers in a row, horizontally.
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 2)) {
                    if (game.board[i][j] == game.board[i][j + 1]
                            && game.board[i][j] == game.board[i][j + 2]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, vertically.
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j)) {
                    if (game.board[i][j] == game.board[i - 1][j]
                            && game.board[i][j] == game.board[i - 2][j]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, in descending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 2, j + 2)) {
                    if (game.board[i][j] == game.board[i + 1][j + 1]
                            && game.board[i][j] == game.board[i + 2][j + 2]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, in ascending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j + 2)) {
                    if (game.board[i][j] == game.board[i - 1][j + 1]
                            && game.board[i][j] == game.board[i - 2][j + 2]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        return times;

    }

    public int check2InARow(int player, GameState game) {

        int times = 0;

        // Check for 2 consecutive checkers in a row, horizontally.
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 1)) {
                    if (game.board[i][j] == game.board[i][j + 1]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, vertically.
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j)) {
                    if (game.board[i][j] == game.board[i - 1][j]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, in descending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 1, j + 1)) {
                    if (game.board[i][j] == game.board[i + 1][j + 1]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, in ascending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j + 1)) {
                    if (game.board[i][j] == game.board[i - 1][j + 1]
                            && game.board[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        return times;

    }

    public int evaluate(GameState game) {
        // +100 'X' wins, -100 'O' wins,
        // +10 for each three 'X' in a row, -10 for each three 'O' in a row,
        // +1 for each two 'X' in a row, -1 for each two 'O' in a row
        int Xlines = 0;
        int Olines = 0;

        int winState = winCheck(game);
        if (winState != 0) {
            if(winState == 1) {
                Xlines = Xlines + 100;
            } else if(winState == 2) {
                Olines = Olines + 100;
            }
        }

        Xlines  = Xlines + check3InARow(1, game)*10 + check2InARow(1, game);
        Olines  = Olines + check3InARow(2, game)*10 + check2InARow(2, game);

        // if the result is 0, then it'a a draw
        return Xlines - Olines;
    }
}