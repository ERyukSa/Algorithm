/**
 * https://algospot.com/judge/problem/read/SUSHI
 * 종만북, DP(반복적 DP)
 * 방법1) 메모리를 최적화하기 위해 가격에 100을 나눈다.
 * **방법2)** DP 테이블에 슬라이딩 윈도우를 활용한다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Sushi {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sushiCount = Integer.parseInt(st.nextToken());
            int budget = Integer.parseInt(st.nextToken()) / 100;

            int[] prices = new int[sushiCount];
            int[] priorities = new int[sushiCount];

            for (int i = 0; i < sushiCount; i++) {
                st = new StringTokenizer(br.readLine());
                prices[i] = Integer.parseInt(st.nextToken()) / 100;
                priorities[i] = Integer.parseInt(st.nextToken());
            }

            int[] maxPriorityCache = new int[budget + 1]; // 예산 [k]로 얻을 수 있는 최대 선호도 합
            // 1 <= k <= budget
            Arrays.fill(maxPriorityCache, 0);
            // 왜 0으로 초기화하는가?
            // [k]원으로 살 수 있는 초밥이 없으면 선호도는 0이다
            // P[k] = max(priority[i] + P[k - price[i]]), 0 <= i < sushiCount
            // ==> P[0] = 0이어야 한다. 예산에 딱 맞게 초밥을 샀을 경우 P[0]이 더해지기 때문이다.
            // 내가 궁금한 것은? 초밥을 선택한다 => k' = k - price[i], P[k']가 계산되지 않았을 경우?? 말이 안된다.
            // bottom-up 방식으로 최소 범위부터 값을 계산하기 때문이다. k'가 min(price[i])보다 작다면? 초기 값인 0이 계산된다.
            // k원으로는 초밥을 살 수 없기 때문에 선호도 총합도0이 된다. minPrice보다 작으면 0이고, 크거나 같으면 반드시 값이 계산되어 있다

            for (int tempBudget = 1; tempBudget <= budget; tempBudget++) {
                for (int i = 0; i < sushiCount; i++) {
                    if (tempBudget - prices[i] >= 0) {
                        maxPriorityCache[tempBudget] = Math.max(
                                maxPriorityCache[tempBudget],
                                priorities[i] + maxPriorityCache[tempBudget - prices[i]]
                        );
                    }
                }
            }

            sb.append(maxPriorityCache[budget]).append("\n");
        }

        System.out.print(sb);
    }
}
