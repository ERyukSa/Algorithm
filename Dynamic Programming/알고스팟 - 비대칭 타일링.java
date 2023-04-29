/**
 * https://www.algospot.com/judge/problem/read/ASYMTILING
 * DP(경우의 수), 종만북
 * 풀이1) 전체 타일링 수 - 대칭 타일링 수
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Solution1 {

    private static int[] tilingCount;
    private static int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            int width = Integer.parseInt(br.readLine());
            if (width <= 2) {
                sb.append(0);
                sb.append("\n");
                continue;
            }
            tilingCount = new int[width + 1];
            Arrays.fill(tilingCount, -1);

            int answer = (getTilingCount(width) - getTilingCount(width / 2) + MOD) % MOD;
            if (width % 2 == 0) {
                answer = (answer - getTilingCount(width / 2 - 1) + MOD) % MOD;
            }
            sb.append(answer);
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int getTilingCount(int width) {
        if (width <= 2) {
            return width;
        }
        if (tilingCount[width] != -1) {
            return tilingCount[width];
        }

        tilingCount[width] = (getTilingCount(width - 2) + getTilingCount(width - 1)) % MOD;
        return tilingCount[width];
    }
}

/**
 * 풀이2) 비대칭 타일링 방법 수 직접 세기
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Solution2 {

    private static int[] tilingCount;
    private static int[] asymTilingCount;
    private static int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            int width = Integer.parseInt(br.readLine());
            if (width <= 2) {
                sb.append(0);
                sb.append("\n");
                continue;
            }

            tilingCount = new int[width + 1];
            asymTilingCount = new int[width + 1];
            Arrays.fill(tilingCount, -1);
            Arrays.fill(asymTilingCount, -1);

            sb.append(getAsymTilingCount(width));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    /**
     * @param width: 2 x width 직사각형
     * @return: 비대칭 타일링 방법 수
     */
    private static int getAsymTilingCount(int width) {
        if (width <= 2) {
            return 0;
        }
        if (asymTilingCount[width] != -1) {
            return asymTilingCount[width];
        }

        int myAsymTilingCount = 0;
        myAsymTilingCount = (myAsymTilingCount + getAsymTilingCount(width - 2)) % MOD; // 양쪽 세로 막대기 세우기
        myAsymTilingCount = (myAsymTilingCount + getAsymTilingCount(width - 4)) % MOD; // 양쪽 가로 막대기 2개씩 세우기
        // 양쪽 비대칭(세로1, 가로2) 세우고, 나머진 어떻게 타일링해도 비대칭이므로 나머지 길이에 대해 타일링 방법 수 세기
        myAsymTilingCount = (myAsymTilingCount + getTilingCount(width - 3)) % MOD;
        myAsymTilingCount = (myAsymTilingCount + getTilingCount(width - 3)) % MOD; // 양쪽 비대칭(가로2, 세로1) 세우기

        asymTilingCount[width] = myAsymTilingCount;
        return myAsymTilingCount;
    }

    private static int getTilingCount(int width) {
        if (width <= 2) {
            return width;
        }
        if (tilingCount[width] != -1) {
            return tilingCount[width];
        }

        tilingCount[width] = (getTilingCount(width - 2) + getTilingCount(width - 1)) % MOD;
        return tilingCount[width];
    }
}