/**
 * https://algospot.com/judge/problem/read/MORSE
 * DP(k번째 답 구하기), 종만북, 어렵다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static int[][] signCount;

    private static int MAX_SIGN_COUNT = 1_000_000_001;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        signCount = new int[101][101];
        for (int i = 0; i <= 100; i++) {
            Arrays.fill(signCount[i], -1);
        }

        for (int t = Integer.parseInt(br.readLine()); t > 0; t--) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int longCount = Integer.parseInt(st.nextToken());
            int shortCount = Integer.parseInt(st.nextToken());
            int skipCount = Integer.parseInt(st.nextToken()) - 1;
            sb.append(getTargetSign(longCount, shortCount, skipCount));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    // 입력 부호 개수로 만들 수 있는 모든 신호의 개수 반환, 이항 계수 원리와 같음
    private static int getSignCount(int longCount, int shortCount) {
        if (longCount == 0 || shortCount == 0) {
            return 1;
        }
        if (signCount[longCount][shortCount] != -1) {
            return signCount[longCount][shortCount];
        }

        signCount[longCount][shortCount] =
                getSignCount(longCount - 1, shortCount) + getSignCount(longCount, shortCount - 1);
        // 이항 계수 계산 중 오버플로우가 나지 않도록 최대 값을 K + 1(MAX_SIGN_COUNT)로 제한한다.
        // 아래 재귀 함수에서 부호를 하나 선택할 때마다 건너뛸지 말지 결정하는데,
        // 기준은 그 부호로 만들 수 있는 신호의 개수가 skipCount보다 큰지 여부이다. (skipCount의 최대 값은 K)
        signCount[longCount][shortCount] = Math.min(MAX_SIGN_COUNT, signCount[longCount][shortCount]);
        return signCount[longCount][shortCount];
    }

    private static String getTargetSign(int longCount, int shortCount, int skipCount) {
        if (longCount == 0) {
            StringBuilder sb =  new StringBuilder();
            for (int i = 0; i < shortCount; i++) {
                sb.append('o');
            }
            return sb.toString();
        }

        int signCountStartingLong = getSignCount(longCount - 1, shortCount);
        if (skipCount < signCountStartingLong) {
            return "-" + getTargetSign(longCount - 1, shortCount, skipCount);
        }
        return "o" + getTargetSign(longCount, shortCount - 1, skipCount - signCountStartingLong);
    }
}