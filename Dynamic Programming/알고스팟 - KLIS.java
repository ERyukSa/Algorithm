/**
 * https://algospot.com/judge/problem/read/KLIS
 * DP(k번째 최적해 구하기), 종만북, 연쇄 DP마가 나타났다. DP가 3번 등장한다. 어렵다
 * Top-Down으로 구현할 때, 다른 DP의 정답을 배열로 접근하지 않고 함수 호출로 가져오는 방식이 더 편하다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static final int MAX_LIS_COUNT = Integer.MAX_VALUE;
    private static int[] numbers;
    private static int[] lisLength;
    private static long[] lisCount;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int t = Integer.parseInt(br.readLine()); t > 0; t--) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numberCount = Integer.parseInt(st.nextToken());
            int skipCount = Integer.parseInt(st.nextToken()) - 1;
            numbers = new int[numberCount + 1];
            lisLength = new int[numberCount + 1];
            lisCount = new long[numberCount + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= numberCount; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }
            numbers[0] = -1;
            Arrays.fill(lisLength, -1);
            Arrays.fill(lisCount, -1);

            sb.append(getLisLength(0) - 1);
            sb.append("\n");
            sb.append(getKthLis(0, skipCount));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int getLisLength(int start) {
        if (lisLength[start] != -1) {
            return lisLength[start];
        }

        int nextLisLength = 0;
        for (int next = start + 1; next < lisLength.length; next++) {
            if (numbers[start] < numbers[next]) {
                nextLisLength = Math.max(nextLisLength, getLisLength(next));
            }
        }

        lisLength[start] = nextLisLength + 1;
        return lisLength[start];
    }

    private static long getLisCount(int start) {
        if (getLisLength(start) == 1) {
            return 1;
        }
        if (lisCount[start] != -1) {
            return lisCount[start];
        }

        long myLisCount = 0;
        for (int next = start + 1; next < numbers.length; next++) {
            if (numbers[start] < numbers[next] && getLisLength(start) == getLisLength(next) + 1) {
                myLisCount = Math.min(MAX_LIS_COUNT, myLisCount + getLisCount(next));
            }
        }

        lisCount[start] = myLisCount;
        return myLisCount;
    }

    private static String getKthLis(int start, int skipCount) {
        PriorityQueue<int[]> lisCountPQ = new PriorityQueue<>((int[] i1, int[] i2) -> { return i1[0] - i2[0]; });

        // "lis(start) = [number[start], number[next], ...]"의 next 선택 즉, lis를 만드는 다음 숫자와 인덱스 선택
        for (int next = start + 1; next < numbers.length; next++) {
            if (numbers[start] < numbers[next] && getLisLength(start) == getLisLength(next) + 1) {
                lisCountPQ.offer(new int[]{numbers[next], next});
            }
        }

        // if skipCount와 >= lisCount(next): 해당 범위 lis는 건너 뛴다
        // else: lis(next)에 [skipCount + 1]번째 lis가 포함되어 있으므로 next로 재귀 호출
        while (!lisCountPQ.isEmpty()) {
            int next = lisCountPQ.peek()[1];
            int number = lisCountPQ.peek()[0];
            lisCountPQ.poll();

            if (getLisCount(next) <= skipCount) {
                skipCount -= getLisCount(next);
            } else {
                return String.format("%d %s", number, getKthLis(next, skipCount));
            }
        }

        return "";
    }
}