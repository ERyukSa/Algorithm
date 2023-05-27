/**
 * https://algospot.com/judge/problem/read/NUMBERGAME
 * DP(조합 게임), 난이도: 하
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static final int MIN_DIFFERENCE = -50_001;

    private static int[] numbers;
    private static int[][] maxDifferenceTable;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int arraySize = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            numbers = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            maxDifferenceTable = new int[arraySize][arraySize];
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    maxDifferenceTable[i][j] = MIN_DIFFERENCE;
                }
            }

            int result = simulate(0, arraySize - 1, 0);
            sb.append(result).append("\n");
        }

        System.out.print(sb);
    }

    private static int simulate(int start, int end, int turn) {
        if (start > end) {
            return 0;
        }

        if (maxDifferenceTable[start][end] > MIN_DIFFERENCE) {
            return maxDifferenceTable[start][end];
        }

        int nextTurn = turn^1;
        int myMaxDifference = MIN_DIFFERENCE;

        myMaxDifference = Math.max(myMaxDifference, numbers[start] - simulate(start + 1, end, nextTurn));
        myMaxDifference = Math.max(myMaxDifference, numbers[end] - simulate(start, end - 1, nextTurn));

        if (end - start >= 1) {
            myMaxDifference = Math.max(myMaxDifference, -simulate(start + 2, end, nextTurn));
            myMaxDifference = Math.max(myMaxDifference, -simulate(start, end - 2, nextTurn));
        }

        maxDifferenceTable[start][end] = myMaxDifference;
        return myMaxDifference;
    }
}