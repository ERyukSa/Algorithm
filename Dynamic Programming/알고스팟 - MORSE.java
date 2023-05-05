/**
 * https://www.algospot.com/judge/problem/read/NUMB3RS
 * DP(k번째 답 구하기), 종만북
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static int maxBino = 1_000_000_000; // 이항계수로 계산할 최대 값
    private static int[][] bino = new int[201][201]; // 이항 계수
    private static int skipCount = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        calculateBino();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int longCount = Integer.parseInt(st.nextToken());
            int shortCount = Integer.parseInt(st.nextToken());
            skipCount = Integer.parseInt(st.nextToken()) - 1;

            makeTargetMos(longCount, shortCount, "");
        }
    }

    private static void calculateBino() {
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j <= 200; j++) {
                bino[i][j] = 0;
            }
        }

        for (int i = 0; i <= 200; i++) {
            bino[i][0] = bino[i][i] = 1;
            for (int j = 1; j < i; j++) {
                bino[i][j] = Math.min(maxBino, bino[i - 1][j - 1] + bino[i - 1][j]);
            }
        }
    }

    private static void makeTargetMos(int longCount, int shortCount, String mos) {
        if (skipCount < 0) {
            return;
        }
        if (longCount == 0 && shortCount == 0) {
            if (skipCount == 0) {
                System.out.println(mos);
            }
            skipCount--;
            return;
        }

        if (bino[longCount + shortCount][longCount] <= skipCount) {
            skipCount -= bino[longCount + shortCount][longCount];
            return;
        }
        if (longCount > 0) makeTargetMos(longCount - 1, shortCount, mos + "-");
        if (shortCount > 0) makeTargetMos(longCount, shortCount - 1, mos + "o");
    }
}