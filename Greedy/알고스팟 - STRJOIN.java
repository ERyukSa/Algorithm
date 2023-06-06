/**
 * https://algospot.com/judge/problem/read/STRJOIN
 * 알고리즘 문제해결전략, 유형: 그리디, 난이도: 하
 * 1. 하나씩 합치기만 하지 않고 따로 합친 문자열을 합칠 수도 있음을 파악해야 함
 * 2. 풀이를 떠올리는 것은 어렵지 않았지만 증명은 그렇지 않았다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int stringCount = Integer.parseInt(br.readLine());
            int[] lengthArray = new int[stringCount];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < stringCount; i++) {
                lengthArray[i] = Integer.parseInt(st.nextToken());
            }

            int minCost = findMinCost(lengthArray);
            sb.append(minCost).append("\n");
        }

        System.out.print(sb.deleteCharAt(sb.length() - 1));
    }

    private static int findMinCost(int[] lengthArray) {
        int costSum = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int length : lengthArray) {
            pq.offer(length);
        }

        while (pq.size() > 1) {
            int minLength1 = pq.poll();
            int minLength2 = pq.poll();
            int cost = minLength1 + minLength2;
            costSum += cost;
            pq.offer(cost);
        }

        return costSum;
    }
}