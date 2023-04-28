/**
 * https://www.algospot.com/judge/problem/read/TILING2
 * DP(경우의 수), 종만북
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {

    private static int MOD = 1_000_000_007;
    private static int[] tilingCount; // 2x[width]인 타일 채우는 방법의 수

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            int width = Integer.parseInt(br.readLine());
            tilingCount = new int[width + 1];
            Arrays.fill(tilingCount, -1);
            sb.append(getTilingCount(width));
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

        // MOD + MOD는 2^32 - 1 보다 작아서 오버플로우가 발생하지 않는다
        tilingCount[width] = (getTilingCount(width - 1) + getTilingCount(width - 2)) % MOD;
        return tilingCount[width];
    }
}
