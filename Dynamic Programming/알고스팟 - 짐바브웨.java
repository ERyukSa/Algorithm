/**
 * https://algospot.com/judge/problem/read/ZIMBABWE
 * DP(비트마스크 + 수학), 난이도: 극극극극극상
 * - 비트마스크 => 이때까지 선택한 정보의 순서는 상관하지 않겠다, 조합이 같은지만 확인하겠다
 * - 같은 숫자가 여러 개 있을 때 하나만 선택하는 기법: 정렬 후 같은 수의 맨 앞의 것만 선택하도록 함
 * - 나머지: (앞에 선택한 수의 나머지 * 10 + 다음 숫자) % MOD <=> 지금까지 선택한 숫자 % MOD
 * - 선택한 숫자의 범위 판별: 이전에 선택한 수가 이미 범위 안에 포함되어서 나머지는 아무거나 선택 가능한지(less),
 *                        같은 자리수에 같은 숫자를 선택해서 앞으로 선택할 때도 범위 내에서 선택하도록 조심해야 하는지(not less)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private final static int MOD = 1_000_000_007;

    private static String originalPrice; // 원래 가격 => less 판별에 사용
    private static int CANDY_SHARE;
    private static char[] digits; // orignalPrice를 정렬한 문자 배열
    private static boolean[] used; // 앞에서 사용된 digit인지?
    private static int[][][] possibleCount; // 캐시 [2^가격문자열길이][직전에 선택된 수 % M][less(originalPrice보다 작은지 같은지)]

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            originalPrice = st.nextToken();
            CANDY_SHARE = Integer.parseInt(st.nextToken());

            digits = originalPrice.toCharArray();
            Arrays.sort(digits);

            used = new boolean[digits.length];
            Arrays.fill(used, false);

            possibleCount = new int[1<<digits.length][CANDY_SHARE][2];
            for (int i = 0; i < 1<<digits.length; i++) {
                for (int j = 0; j < CANDY_SHARE; j++) {
                    for (int k = 0; k < 2; k++) {
                        possibleCount[i][j][k] = -1;
                    }
                }
            }

            // 만드는중인 인덱스, 비트마스크, 나머지, less
            int count = getPossibleCount(0, 0, 0, 0);
            sb.append(count);
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int getPossibleCount(int index, int selectedMask, int remain, int less) {
        if (index == originalPrice.length()) {
            return (remain == 0 && less == 1) ? 1 : 0;
        }

        if (possibleCount[selectedMask][remain][less] != -1) {
            return possibleCount[selectedMask][remain][less];
        }
// 123, 132, 2
        int myPossibleCount = 0;
        for (int i = 0; i < digits.length; i++) {
            if (!used[i] && (i == 0 || digits[i - 1] != digits[i] || used[i - 1])) {
                if (less == 0 && digits[i] > originalPrice.charAt(index)) {
                    continue;
                }

                used[i] = true;
                // [숫자|문자] % MOD =>
                int nextRemain = (remain * 10 + (digits[i] - '0')) % CANDY_SHARE;
                int nextLess = (less == 0 && digits[i] == originalPrice.charAt(index)) ? 0 : 1;
                myPossibleCount += getPossibleCount(index + 1, selectedMask | 1 << i, nextRemain, nextLess);
                myPossibleCount %= MOD;
                used[i] = false;
            }
        }

        possibleCount[selectedMask][remain][less] = myPossibleCount;
        return myPossibleCount;
    }
}