/**
 * https://algospot.com/judge/problem/read/SNAIL
 * DP(확률), 종만북
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static double[][] reachPercent; // [남은 높이][남은 날짜]

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int height = Integer.parseInt(st.nextToken());
            int rainPeriod = Integer.parseInt(st.nextToken());

            reachPercent = new double[height + 1][rainPeriod + 1];
            for (int h = 1; h <= height; h++) {
                for (int p = 1; p <= rainPeriod; p++) {
                    reachPercentAtHeight[h][p] = -1.0;
                }
            }

            sb.append(getReachPercent(height, rainPeriod));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static double getReachPercent(int height, int period) {
        if (height <= 0) { // 꼭대기에 도착했으면 확률 1 반환
            return 1;
        }
        if (period == 0) { // 도착 전에 기간 종료 됐으면 0 반환
            return 0;
        }

        if(reachPercent[height][period] != -1.0) { // 메모이제이션 활용
            return reachPercent[height][period];
        }

        reachPercent[height][period] = 0.75 * getReachPercent(height - 2, period - 1) +
                0.25 * getReachPercent(height - 1, period - 1);
        return reachPercent[height][period];
    }
}
