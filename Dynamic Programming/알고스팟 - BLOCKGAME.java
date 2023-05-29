/**
 * https://www.algospot.com/judge/problem/read/BLOCKGAME
 * 종만북, DP(조합 게임, 비트마스킹) + 구현, 난이도: 극그그극그그극상
 * ***도형을 놓을 수 있는지 여부를 비트 연산으로 확인할 수 있다***
 * --> IF (현재 보드 상태) & (도형을 표현하는 비트 마스크) is 0: 현재 보드에서 도형을 놓으려는 위치가 모두 비어있다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BlockGame {

    static final int BOARD_SIZE = 5;
    static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    static byte[] resultCache = new byte[1 << (BOARD_SIZE * BOARD_SIZE)];

    static List<Integer> figureMasks = new ArrayList<>();

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        preAddFigureMasks();
        for (int i = 0; i < 1 << (BOARD_SIZE * BOARD_SIZE); i++) {
            resultCache[i] = -1;
        }

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                board[i] = br.readLine().toCharArray();
            }

            int hasWinCase = canWin(binaryToDecimal(board));
            if (hasWinCase == 0) {
                sb.append("LOSING\n");
            } else {
                sb.append("WINNING\n");
            }
        }

        System.out.print(sb);
    }

    private static void preAddFigureMasks() {
        int[] dots = new int[4];

        // 3개짜리 도형
        for (int row = 0; row < BOARD_SIZE - 1; row++) {
            for (int col = 0; col < BOARD_SIZE - 1; col++) {
                for (int dRow = 0; dRow < 2; dRow++) {
                    for (int dCol = 0; dCol < 2; dCol++) {
                        int dot = calculateBitMask(row +dRow, col +dCol);
                        dots[2 * dRow + dCol] = dot;
                    }
                }

                int square = dots[0] + dots[1] + dots[2] +dots[3];
                for (int i = 0; i < 4; i++) {
                    figureMasks.add(square - dots[i]);
                }
            }
        }

        // 2개짜리 도형
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE - 1; j++) {
                figureMasks.add(calculateBitMask(i, j) + calculateBitMask(i, j + 1));
                figureMasks.add(calculateBitMask(j, i) + calculateBitMask(j + 1, i));
            }
        }
    }

    private static int calculateBitMask(int row, int col) {
        return 1 << BOARD_SIZE * row + col;
    }

    private static int binaryToDecimal(char[][] board) {
        int two = 1;
        int state = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '#') state += two;
                two *= 2;
            }
        }

        return state;
    }

    private static int canWin(int state) {
        if (resultCache[state] != -1) {
            return resultCache[state];
        }

        byte hasWinCase = 0;

        for (int mask : figureMasks) {
            // mask가 가리키는 도형의 위치가 비어있는 경우라면
            if ((state & mask) == 0) {
                if (canWin(state | mask) == 0) {
                    hasWinCase = 1;
                    break;
                }
            }
        }

        resultCache[state] = hasWinCase;
        return hasWinCase;
    }
}
