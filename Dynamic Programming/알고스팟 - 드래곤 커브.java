/**
 * https://algospot.com/judge/problem/read/DRAGON
 * DP(k번째 정답 찾기), 종만북, 오래 걸렸다
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static final int MAX_STRING_LENGTH = 1_000_000_000;
    private static final int MAX_XY_COUNT= 300_000_000;

    private static int[] xyLengthAfter;
    private static int[] xyCountAfter;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int generation = Integer.parseInt(st.nextToken());
            int startIndex = Integer.parseInt(st.nextToken()) - 1;
            int answerLength = Integer.parseInt(st.nextToken());

            xyLengthAfter = new int[generation + 1];
            xyCountAfter = new int[generation + 1];
            Arrays.fill(xyLengthAfter, -1);
            Arrays.fill(xyCountAfter, -1);

            for (int skipCount = startIndex; skipCount < startIndex + answerLength; skipCount++) {
                sb.append(getChar("FX", skipCount, generation));
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static char getChar(String str, int skipCount, int generation) {
        if (generation == 0) {
            return str.charAt(skipCount);
        }

        char answer = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch == 'X' || ch == 'Y') {
                int lengthAfterGeneration = getXyLengthAfter(generation);
                if (lengthAfterGeneration > skipCount) {
                    answer = getChar(ch == 'X' ? "X+YF" : "FX-Y", skipCount, generation - 1);
                    break;
                } else {
                    skipCount -= lengthAfterGeneration;
                }
            } else {
                if (skipCount == 0) {
                    answer = str.charAt(i);
                    break;
                }
                skipCount -= 1;
            }
        }

        return answer;
    }

    private static int getXyLengthAfter(int generation) {
        if (generation == 0) {
            return 1;
        }
        if (xyLengthAfter[generation] != -1) {
            return xyLengthAfter[generation];
        }

        xyLengthAfter[generation] = getXyLengthAfter(generation - 1) + getXyCountAfter(generation - 1) * 3;
        xyLengthAfter[generation] = Math.min(MAX_STRING_LENGTH, xyLengthAfter[generation]);
        return xyLengthAfter[generation];
    }

    private static int getXyCountAfter(int generation) {
        if (generation == 0) {
            return 1;
        }
        if (xyCountAfter[generation] != -1) {
            return xyCountAfter[generation];
        }

        xyCountAfter[generation] = Math.min(MAX_XY_COUNT, getXyCountAfter(generation - 1) * 2);
        return xyCountAfter[generation];
    }
}