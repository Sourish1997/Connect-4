import java.util.*;

public class GameState {
    int board[][] = new int[6][7];
    boolean maxPlayer;
    public GameState(int brd[][], boolean mp) {
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 7; b++) {
                board[a][b] = brd[a][b];
            }
        }
        maxPlayer = mp;
    }
    public ArrayList < GameState > generateChildren() {
        ArrayList < GameState > children = new ArrayList < GameState > ();
        for (int a = 0; a <= 6; a++) {
            if (isFilled(0, a) == false) {
                for (int b = 5; b >= 0; b--) {
                    if (isFilled(b, a) == false) {
                        int childBoard[][] = new int[6][7];
                        for (int c = 0; c < 6; c++) {
                            for (int d = 0; d < 7; d++) {
                                childBoard[c][d] = board[c][d];
                            }
                        }
                        if (maxPlayer) {
                            childBoard[b][a] = 1;
                            children.add(new GameState(childBoard, false));
                            break;
                        } else {
                            childBoard[b][a] = 2;
                            children.add(new GameState(childBoard, true));
                            break;
                        }
                    }
                }
            }
        }
        return children;
    }
    public int addDisc(int col) {
        if (isFilled(0, col))
            return -1;
        for (int a = 5; a >= 0; a--) {
            if (isFilled(a, col) == false) {
                if (maxPlayer) {
                    board[a][col] = 1;
                    maxPlayer = false;
                } else {
                    board[a][col] = 2;
                    maxPlayer = true;
                }
                return 0;
            }
        }
        return 0;
    }

    public boolean isFilled(int a, int b) {
        if (board[a][b] == 1 || board[a][b] == 2)
            return true;
        else
            return false;
    }
}