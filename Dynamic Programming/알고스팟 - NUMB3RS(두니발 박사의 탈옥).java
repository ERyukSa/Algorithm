/**
 * https://www.algospot.com/judge/problem/read/NUMB3RS
 * DP(확률, 마르코프 연쇄), 종만북
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static int[][] isLinked; // [city1][city2], 1: 연결, 0: 연결x
    private static double[][] stayPercent; // [day][city], 박사가 day일에 city에 있을 확률
    private static int[] degree; // degree[city]: city로 들어오는 엣지 개수
    private static int jail = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cityCount = Integer.parseInt(st.nextToken());
            int lastDay = Integer.parseInt(st.nextToken());
            jail = Integer.parseInt(st.nextToken());

            isLinked = new int[cityCount][cityCount];
            stayPercent = new double[lastDay + 1][cityCount];
            degree = new int[cityCount];
            for (int city1 = 0; city1 < cityCount; city1++) {
                degree[city1] = 0;
                st = new StringTokenizer(br.readLine());
                for (int city2 = 0; city2 < cityCount; city2++) {
                    int linked = Integer.parseInt(st.nextToken());
                    isLinked[city1][city2] = linked;
                    degree[city1] += linked;
                }
            }
            for (int day = 1; day <= lastDay; day++) {
                for (int city = 0; city < cityCount; city++) {
                    stayPercent[day][city] = -1.0;
                }
            }

            int checkCityCount = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            while (checkCityCount-- > 0) {
                int city = Integer.parseInt(st.nextToken());
                sb.append(getStayPercent(lastDay, city));
                sb.append(' ');
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    // 박사가 [day]일에 [city]에 있을 확률
    private static double getStayPercent(int day, int city) {
        if (day == 0) {
            return (city == jail) ? 1.0 : 0.0;
        }
        if (stayPercent[day][city] != -1.0) {
            return stayPercent[day][city];
        }

        double stayHerePercent = 0.0;
        for (int prevCity = 0; prevCity < isLinked.length; prevCity++) {
            if (isLinked[city][prevCity] == 1) {
                stayHerePercent += getStayPercent(day - 1, prevCity) / degree[prevCity];
            }
        }

        stayPercent[day][city] = stayHerePercent;
        return stayHerePercent;
    }
}