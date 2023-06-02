/**
 * https://algospot.com/judge/problem/read/GENIUS
 * 알고리즘 문제해결전략, DP(행렬 거듭제곱 최적화), 난이도: 그그극극극극극극상
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Main {

    private static int songCount;
    private static int[] playLength;
    private static double[][] runPercent;
    private static double[][] W;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            songCount = Integer.parseInt(st.nextToken());
            int targetTime = Integer.parseInt(st.nextToken());
            int favoriteCount = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            playLength = new int[songCount];
            runPercent = new double[songCount][songCount];

            for (int i = 0; i < songCount; i++) {
                playLength[i] = Integer.parseInt(st.nextToken());
            }
            for (int prev = 0; prev < songCount; prev++) {
                st = new StringTokenizer(br.readLine());
                for (int next = 0; next < songCount; next++) {
                    runPercent[prev][next] = Double.parseDouble(st.nextToken());
                }
            }

            W = new double[4 * songCount][4 * songCount];
            setUpWeightMatrix();

            double[] C_0 = new double[4 * songCount];
            setUpC_O(C_0);

            st = new StringTokenizer(br.readLine());
            double[] C_targetTime =  multiply(pow(W, targetTime), C_0);

            for (int i = 0; i < favoriteCount; i++) {
                int song = Integer.parseInt(st.nextToken());
                double percent = 0.0;
                for (int time = 0; time < playLength[song]; time++) {
                    percent += C_targetTime[(3 - time) * songCount + song];
                }
                sb.append(percent).append(" ");
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static void setUpWeightMatrix() {
        for (int i = 0; i < songCount; i++) {
            Arrays.fill(W[i], 0.0);
        }

        for (int i = 0; i < songCount; i++) {
            W[i][songCount + i] = 1;
            W[songCount + i][2 * songCount + i] = 1;
            W[2 * songCount + i][3 * songCount + i] = 1;
        }

        for (int next = 0; next < songCount; next++) {
            for (int prev = 0; prev < songCount; prev++) {
                W[3 * songCount + next][(4 - playLength[prev]) * songCount + prev] = runPercent[prev][next];
            }
        }
    }

    private static void setUpC_O(double[] C_0) {
        Arrays.fill(C_0, 0);
        C_0[3 * songCount] = 1;
    }

    private static double[][] pow(double[][] matrix, int n) {
        if (n == 1) {
            return matrix;
        }

        if (n % 2 == 1) return multiply(pow(matrix, n - 1), matrix);
        double[][] half = pow(matrix, n / 2);
        return multiply(half, half);
    }

    private static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        double[][] newMatrix = new double[matrix1.length][matrix2[0].length];
        for (double[] matrix : newMatrix) {
            Arrays.fill(matrix, 0.0);
        }

        for (int row = 0; row < matrix1.length; row++) {
            for (int col = 0; col < matrix2[0].length; col++) {
                for (int k = 0; k < matrix2.length; k++)
                    newMatrix[row][col] += matrix1[row][k] * matrix2[k][col];
            }
        }

        return newMatrix;
    }

    private static double[] multiply(double[][] matrix1, double[] matrix2) {
        double[] newMatrix = new double[matrix1.length];

        for (int row = 0; row < matrix1.length; row++) {
            newMatrix[row] = 0;
            for (int col = 0; col < matrix1[0].length; col++) {
                newMatrix[row] += matrix1[row][col] * matrix2[col];
            }
        }

        return newMatrix;
    }
}