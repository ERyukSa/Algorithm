/**
 * https://algospot.com/judge/problem/read/MATCHORDER
 * 알고리즘 문제해결전략, 유형: 그리디, 난이도: 하
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    static int[] koreaRatings;
    static int[] russiaRatings;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int playerCount = Integer.parseInt(br.readLine());
            koreaRatings = new int[playerCount];
            russiaRatings = new int[playerCount];

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            for (int i = 0; i < playerCount; i++) {
                russiaRatings[i] = Integer.parseInt(st1.nextToken());
                koreaRatings[i] = Integer.parseInt(st2.nextToken());
            }
            Arrays.sort(koreaRatings);
            Arrays.sort(russiaRatings);

            int koreaIndex = 0;
            int russiaIndex = 0;
            while (koreaIndex < playerCount) {
                if (koreaRatings[koreaIndex] >= russiaRatings[russiaIndex]) {
                    russiaIndex++;
                }
                koreaIndex++;
            }
            sb.append(russiaIndex).append("\n");
        }

        System.out.print(sb);
    }
}