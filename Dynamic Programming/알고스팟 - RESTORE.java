/**
 * https://algospot.com/judge/problem/read/ZIMBABWE
 * 종만북, DP(비트마스크, 역추적), 난이도: 극극극극상
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static int[][] maxOverlapSize;
    private static int[][] suffixOverlapSize;
    private static List<String> subStrings;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int substringCount = Integer.parseInt(br.readLine());
            subStrings = new ArrayList<>();
            List<String> tempStringList = new ArrayList<>();

            for (int i = 0; i < substringCount; i++) {
                tempStringList.add(br.readLine());
            }
            for (int i = 0; i < substringCount; i++) {
                boolean isContained = false;
                for (int j = 0; j < substringCount; j++) {
                    if (tempStringList.get(i) != tempStringList.get(j) && tempStringList.get(j).contains(tempStringList.get(i))) {
                        isContained = true;
                        break;
                    }
                }
                if (!isContained) {
                    subStrings.add(tempStringList.get(i));
                }
            }

            maxOverlapSize = new int[subStrings.size()][1<<subStrings.size()];
            suffixOverlapSize = new int[subStrings.size()][subStrings.size()];

            for (int i = 0; i < subStrings.size(); i++) {
                for (int j = 0; j < maxOverlapSize[0].length; j++) {
                    maxOverlapSize[i][j] = -1;
                }
            }
            for (int i = 0; i < subStrings.size(); i++) {
                for (int j = 0; j < subStrings.size(); j++) {
                    suffixOverlapSize[i][j] = -1;
                }
                suffixOverlapSize[i][i] = subStrings.get(i).length();
            }

            int first = 0;
            int realMaxOverlapSize = 0;
            for (int i = 0; i < subStrings.size(); i++) {
                int tempMaxOverlapSize = getMaxOverlapSize(i, 1<<i);
                if (tempMaxOverlapSize > realMaxOverlapSize) {
                    first = i;
                    realMaxOverlapSize = tempMaxOverlapSize;
                }
            }

            sb.append(subStrings.get(first)).append(buildShortestString(first, 1 << first));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static String buildShortestString(int last, int usedBitMask) {
        if (usedBitMask == (1<<subStrings.size()) - 1) {
            return "";
        }

        for (int next = 0; next < subStrings.size(); next++) {
            if ((usedBitMask & (1<<next)) == 0) {
                int realMaxOverlapSize = getMaxOverlapSize(last, usedBitMask);
                int nextBitMask = usedBitMask + (1<<next);
                int tempMaxOverlapSize = getSuffixOverlapSize(last, next) + getMaxOverlapSize(next, nextBitMask);
                if (realMaxOverlapSize == tempMaxOverlapSize) {
                    return subStrings.get(next).substring(getSuffixOverlapSize(last, next)) + buildShortestString(next, nextBitMask);
                }
            }
        }

        return "*****DEBUG REQUIRED*****";
    }

    private static int getMaxOverlapSize(int last, int usedBitMask) {
        if (usedBitMask == (1 << subStrings.size()) - 1) {
            return 0;
        }
        if (maxOverlapSize[last][usedBitMask] != -1) {
            return maxOverlapSize[last][usedBitMask];
        }

        int myMaxOverlapSize = 0;
        for (int next = 0; next < subStrings.size(); next++) {
            if ((usedBitMask & (1 << next)) == 0) {
                myMaxOverlapSize = Math.max(
                        myMaxOverlapSize,
                        getSuffixOverlapSize(last, next) + getMaxOverlapSize(next, usedBitMask + (1 << next))
                );
            }
        }

        maxOverlapSize[last][usedBitMask] = myMaxOverlapSize;
        return myMaxOverlapSize;
    }

    private static int getSuffixOverlapSize(int prevIndex, int nextIndex) {
        if (suffixOverlapSize[prevIndex][nextIndex] != -1) {
            return suffixOverlapSize[prevIndex][nextIndex];
        }

        if (subStrings.get(prevIndex).contains(subStrings.get(nextIndex))) {
            return subStrings.get(nextIndex).length();
        }

        int myMaxSize = 0;
        for (int size = Math.min(subStrings.get(prevIndex).length(), subStrings.get(nextIndex).length()); size > 0; size--) {
            if (subStrings.get(prevIndex).substring(subStrings.get(prevIndex).length() - size)
                    .equals(subStrings.get(nextIndex).substring(0, size))
            ) {
                myMaxSize = size;
                break;
            }
        }

        suffixOverlapSize[prevIndex][nextIndex] = myMaxSize;
        return myMaxSize;
    }
}