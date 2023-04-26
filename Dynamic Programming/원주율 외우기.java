import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * https://algospot.com/judge/problem/read/PI
 * 종만북: DP & 구현
 */
class Main {

    private static String number;
    private static long[] minLevels;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            number = br.readLine();
            minLevels = new long[number.length()];
            Arrays.fill(minLevels, -1);
            bw.write(String.valueOf(getMinLevel(0)));
            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }

    private static long getMinLevel(int start) {
        if (start == number.length()) {
            return 0;
        }
        if (minLevels[start] != -1) {
            return minLevels[start];
        }

        long myMinLevel = Integer.MAX_VALUE;
        for(int length = 3; length <= 5 && start + length <= number.length(); length++) {
            myMinLevel = Math.min(
                    myMinLevel,
                    calculateLevel(start, length) + getMinLevel(start + length)
            );
        }

        minLevels[start] = myMinLevel;
        return myMinLevel;
    }

    private static int calculateLevel(int start, int length) {
        boolean isLevelOne = true;
        for (int i = start; i < start + length - 1; i++) {
            if (number.charAt(i) != number.charAt(i + 1)) {
                isLevelOne = false;
                break;
            }
        }
        if (isLevelOne) return 1;

        boolean isLevelFive = true;
        for (int i = start; i < start + length - 2; i++) {
            if (number.charAt(i + 1) - number.charAt(i) != number.charAt(i + 2) - number.charAt(i + 1)) {
                isLevelFive = false;
                break;
            }
        }
        if (isLevelFive) {
            return (Math.abs(number.charAt(start) - number.charAt(start + 1)) == 1) ? 2 : 5;
        }

        boolean isLevelFour = true;
        for (int i = start; i < start + length - 2; i += 2) {
            if (number.charAt(i) != number.charAt(i + 2)) {
                isLevelFour = false;
                break;
            }
        }
        for (int i = start + 1; i < start + length - 2; i += 2) {
            if (number.charAt(i) != number.charAt(i + 2)) {
                isLevelFour = false;
                break;
            }
        }
        if (isLevelFour) return 4;

        return 10;
    }
}