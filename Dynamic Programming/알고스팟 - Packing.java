/**
 * https://algospot.com/judge/problem/read/PACKING
 * DP(배낭 문제, DP 최적해 추적하기), 종만북
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {

    private static String[] names;
    private static int[] volumes;
    private static int[] desperations;
    private static int[][] maxDesperation; // [index] 이후의 아이템으로 [capacity]을 채울 수 있는 최대 절박함

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int itemCount = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());

            names = new String[itemCount];
            volumes = new int[itemCount];
            desperations = new int[itemCount];
            for (int i = 0; i < itemCount; i++) {
                st = new StringTokenizer(br.readLine());
                names[i] = st.nextToken();
                volumes[i] = Integer.parseInt(st.nextToken());
                desperations[i] = Integer.parseInt(st.nextToken());
            }

            maxDesperation = new int[capacity + 1][itemCount];
            for (int[] maxDesperationPerCapacity : maxDesperation) {
                Arrays.fill(maxDesperationPerCapacity, -1);
            }

            int thisMaxDesperation = getMaxDesperation(capacity, 0);
            ArrayList<String> packedItems = new ArrayList<>(); // 최대 절박함을 만드는 아이템 추가
            addPackedItems(capacity, 0, packedItems);
            int thisPackedItemsCount = packedItems.size();

            sb.append(String.format("%d %d\n", thisMaxDesperation, thisPackedItemsCount));
            for (String item : packedItems) {
                sb.append(item);
                sb.append("\n");
            }
        }

        System.out.print(sb);
    }

    private static int getMaxDesperation(int capacity, int index) {
        if (index == volumes.length || capacity == 0) {
            return 0;
        }

        if (maxDesperation[capacity][index] != -1) {
            return maxDesperation[capacity][index];
        }

        int myMaxDesperation = getMaxDesperation(capacity, index + 1); // 아이템 추가 안하는 경우
        if (capacity >= volumes[index]) { // 아이템을 배낭에 추가하는 경우
            myMaxDesperation = Math.max(
                    myMaxDesperation,
                    getMaxDesperation(capacity - volumes[index], index + 1) + desperations[index]
            );
        }

        maxDesperation[capacity][index] = myMaxDesperation;
        return myMaxDesperation;
    }

    // 최대 절박함을 만드는 아이템을 추적해서 리스트에 추가
    private static void addPackedItems(int capacity, int index, final ArrayList<String> packedItems) {
        if (index == names.length) {
            return;
        }

        if (capacity >= volumes[index] && getMaxDesperation(capacity, index + 1) >
                getMaxDesperation(capacity - volumes[index], index + 1) + desperations[index]) {
            packedItems.add(names[index]);
            addPackedItems(capacity - volumes[index], index + 1, packedItems);
        } else {
            addPackedItems(capacity, index + 1, packedItems);
        }
    }
}