/**
 * https://algospot.com/judge/problem/read/TRIPATHCNT
 * DP(경우의 수), DP 2번 적용하기, 종만북
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static int[][] triangle;
    private static int[][] maxWeightSum;
    private static int[][] maxPathCount;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int triLength = Integer.parseInt(br.readLine());
            triangle = new int[triLength][triLength];
            for (int i = 0; i < triLength; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j <= i; j++) {
                    triangle[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            maxWeightSum = new int[triLength][triLength];
            maxPathCount = new int[triLength][triLength];
            for (int row = 0; row < triLength; row++) {
                for (int col = 0; col <= row; col ++) {
                    maxWeightSum[row][col] = -1;
                    maxPathCount[row][col] = -1;
                }
            }

            sb.append(getMaxPathCount(0, 0));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int getMaxPathCount(int row, int col) {
        if (row == triangle.length - 1) {
            return 1;
        }
        if (maxPathCount[row][col] != -1) {
            return maxPathCount[row][col];
        }

        int myMaxPathCount = 0;
        if (getMaxWeightSum(row + 1, col) >= getMaxWeightSum(row + 1, col + 1)) {
            myMaxPathCount += getMaxPathCount(row + 1, col);
        }
        if (getMaxWeightSum(row + 1, col) <= getMaxWeightSum(row + 1, col + 1)) {
            myMaxPathCount += getMaxPathCount(row + 1, col + 1);
        }

        maxPathCount[row][col] = myMaxPathCount;
        return myMaxPathCount;
    }

    private static int getMaxWeightSum(int row, int col) {
        if (row == triangle.length - 1) {
            return triangle[row][col];
        }
        if (maxWeightSum[row][col] != -1) {
            return maxWeightSum[row][col];
        }

        maxWeightSum[row][col] = triangle[row][col] +
                Math.max(getMaxWeightSum(row + 1, col), getMaxWeightSum(row + 1, col + 1));
        return maxWeightSum[row][col];
    }
}