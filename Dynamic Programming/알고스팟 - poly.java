/**
 * https://algospot.com/judge/problem/read/POLY
 * DP(경우의 수, 도형), 종만북, HARD
 * 폴리오미노는 가로 사각형이 모두 연속돼야 하므로 행의 사각형 개수로 모든 경우의 수를 나눌 수 있다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {

    private static int MOD = 10_000_000;
    private static int[][] polyCount; // [남은 사각형 수][첫 줄 사각형 수]

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int squareCount = Integer.parseInt(br.readLine());
            polyCount = new int[squareCount + 1][squareCount + 1];

            for (int i = 1; i <= squareCount; i++) {
                for (int j = 1; j <= i; j++) {
                    polyCount[i][j] = -1;
                }
            }

            int totalPolyCount = 0;
            // 첫째 줄에 사각형이 1개 ~ n개일 때 모노폴리 수를 모두 구하며 더한다
            for (int firstLineSquareCount = 1; firstLineSquareCount <= squareCount; firstLineSquareCount++) {
                totalPolyCount += getPolyCount(squareCount, firstLineSquareCount);
                totalPolyCount %= MOD;
            }
            sb.append(totalPolyCount);
            sb.append("\n");
        }

        System.out.print(sb);
    }

    /**
     *
     * @param squareCount: 남은 정사각형 수
     * @param firstCount: 첫 째줄 정사각형 수
     * @return: 모노폴리 수
     */
    private static int getPolyCount(int squareCount, int firstCount) {
        if (squareCount == firstCount) {
            return 1;
        }
        if (polyCount[squareCount][firstCount] != -1) {
            return polyCount[squareCount][firstCount];
        }

        int myPolyCount = 0;
        for (int secondCount = 1; secondCount <= squareCount - firstCount; secondCount++) {
            myPolyCount +=
                    (firstCount + secondCount - 1) * getPolyCount(squareCount - firstCount, secondCount);
            myPolyCount %= MOD;
        }

        polyCount[squareCount][firstCount] = myPolyCount;
        return myPolyCount;
    }
}