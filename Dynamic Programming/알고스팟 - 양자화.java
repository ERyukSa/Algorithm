/**
 * https://algospot.com/judge/problem/read/QUANTIZE
 * 종만북, DP
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {

    private static int[] numbers;
    private static int[][] minQuantumSum; // [시작인덱스][압축으로 사용할 숫자의 남은 개수]

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());

        while(t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numberCount = Integer.parseInt(st.nextToken());
            int partCount = Integer.parseInt(st.nextToken());
            numbers = Arrays.stream(br.readLine().split(" "))
                    .sequential()
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .toArray();
            minQuantumSum = new int[numberCount][partCount + 1];

            for (int[] array: minQuantumSum) {
                Arrays.fill(array, -1);
            }

            sb.append(quantize(0, partCount));
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int quantize(int start, int partCount) {
        if (start == numbers.length) {
            return 0;
        }
        if (partCount == 0) {
            return 987654321;
        }

        if (minQuantumSum[start][partCount] != -1) {
            return minQuantumSum[start][partCount];
        }

        int minSum = 987654321;
        for (int partLength = 1; partLength <= numbers.length - start; partLength++) {
            minSum = Math.min(
                    minSum,
                    getMinQuantumSum(start, partLength) + quantize(start + partLength, partCount - 1)
            );
        }

        minQuantumSum[start][partCount] = minSum;
        return minSum;
    }

    private static int getMinQuantumSum(int start, int length) {
        int average = 0;
        for (int i = start; i < start + length; i++) {
            average += numbers[i];
        }
        average = (int)(0.5 + (double)average / length);

        int quantumSum = 0;
        for (int i = start; i < start + length; i++) {
            quantumSum += (average - numbers[i]) * (average - numbers[i]);
        }

        return quantumSum;
    }
}
