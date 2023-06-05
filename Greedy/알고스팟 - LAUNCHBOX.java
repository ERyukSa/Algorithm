/**
 * https://algospot.com/judge/problem/read/LUNCHBOX
 * 알고리즘 문제해결전략, 유형: 그리디, 난이도: 중하
 * 1. 순서를 바꿔도 데우는 총 시간은 변하지 않는다
 * 2. 먹는 시간이 오래 걸리는 것부터 데운다 (그리디)
 * 3. 그리디한 최적해가 있음을 귀납적으로 증명해본다
 *    => 1) 그리디 하지 않은 최적해가 있다고 가정하고, 맨 앞 조각을 그리디하게 바꿔도 최적해가 성립함(손해가 없음)을 보인다
 *    => 2) 남은 부분 문제에 대해서도 그리디한 최적해가 있음을 보인다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int boxCount = Integer.parseInt(br.readLine());
            LaunchBox[] boxes = new LaunchBox[boxCount];

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            for (int i = 0; i < boxCount; i++) {
                boxes[i] = new LaunchBox(Integer.parseInt(st1.nextToken()), Integer.parseInt(st2.nextToken()));
            }
            Arrays.sort(boxes);

            int heatedTime = 0;
            int finishTime = 0;
            for (LaunchBox box : boxes) {
                heatedTime += box.heatTime;
                finishTime = Math.max(finishTime, heatedTime + box.eatTime);
            }

            sb.append(finishTime).append("\n");
        }

        System.out.print(sb.deleteCharAt(sb.length() - 1));
    }
}

class LaunchBox implements Comparable<LaunchBox> {
    public int heatTime;
    public int eatTime;

    public LaunchBox(int heatTime, int eatTime) {
        this.heatTime = heatTime;
        this.eatTime = eatTime;
    }

    public int getHeatTime() {
        return this.heatTime;
    }

    public int getEatTime() {
        return this.eatTime;
    }

    @Override
    public int compareTo(LaunchBox other) {
        return other.eatTime - this.eatTime;
    }
}