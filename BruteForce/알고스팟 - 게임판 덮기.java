/**
 * https://algospot.com/judge/problem/read/STRJOIN
 * 알고리즘 문제해결전략, 유형: 완전탐색 + 가지치기 (구한 답보다 더 좋은 답을 못구하는 상황이면 바로 리턴), 난이도: 최상
 * 가지 치기: 구한 답보다 더 좋은 답을 못구하는 상황이면 바로 리턴
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Point {
    public int row;
    public int col;

    public Point(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}

class Main {

    private static char[][] board;
    private static char[][] block;
    private static int blockSize;
    private static List<Point>[] blocks;
    private static int maxCount;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int boardRowSize = Integer.parseInt(st.nextToken());
            int boardColSize = Integer.parseInt(st.nextToken());
            int blockRowSize = Integer.parseInt(st.nextToken());
            int blockColSize = Integer.parseInt(st.nextToken());
            int spaceCount = 0;

            board = new char[boardRowSize][boardColSize];
            block = new char[blockRowSize][blockColSize];
            blockSize = 0;
            blocks = new ArrayList[4];
            maxCount = 0;

            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = new ArrayList<>();
            }

            for (int i = 0; i < boardRowSize; i++) {
                board[i] = br.readLine().toCharArray();
                for (char ch : board[i]) {
                    if (ch == '.') spaceCount += 1;
                }
            }

            for (int i = 0; i < blockRowSize; i++) {
                block[i] = br.readLine().toCharArray();
                for (char ch : block[i]) {
                    if (ch == '#') blockSize += 1;
                }
            }
            setUpBlocks(block);

            findMaxCount(0, 0, 0, spaceCount);
            sb.append(maxCount).append("\n");
        }

        System.out.print(sb.deleteCharAt(sb.length() - 1));
    }

    private static void findMaxCount(int row, int col, int putCount, int restSpaceCount) {
        if (row == board.length) {
            maxCount = Math.max(maxCount, putCount);
            return;
        }
        // 더 좋은 답을 구할 수 없는 경우 가지치기
        if (putCount + restSpaceCount / blockSize <= maxCount) {
            return;
        }

        int nextRow = col == board.length - 1 ? row + 1 : row;
        int nextCol = col == board.length - 1 ? 0 : col + 1;

        if (board[row][col] == '#') {
            findMaxCount(nextRow, nextCol, putCount, restSpaceCount);
            return;
        }

        for (List<Point> block : blocks) {
            if (put(row, col, block)) {
                findMaxCount(nextRow, nextCol, putCount + 1, restSpaceCount -= blockSize);
            }
            setBack(row, col, block);
        }

        findMaxCount(nextRow, nextCol, putCount, restSpaceCount);
    }

    private static boolean put(int startRow, int startCol, List<Point> block) {
        for (Point point : block) {
            int row = startRow + point.row;
            int col = startCol + point.col;

            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != '.') {
                return false;
            }

            board[row][col] = '*';
        }

        return true;
    }

    private static void setBack(int startRow, int startCol, List<Point> block) {
        for (Point point : block) {
            int row = startRow + point.row;
            int col = startCol + point.col;

            if (row < 0 || row >= board.length || col < 0 || col >= board.length || board[row][col] == '#') {
                return;
            }

            board[row][col] = '.';
        }
    }

    public static char[][] rotate(char[][] block) {
        char[][] rotatedBlock = new char[block[0].length][block.length];

        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[0].length; col++) {
                rotatedBlock[col][block.length - row - 1] = block[row][col];
            }
        }

        return rotatedBlock;
    }

    private static void setUpBlocks(char[][] block) {
        for (int rotate = 0; rotate < 4; rotate++) {
            int pivotRow = -1;
            int pivotCol = -1;

            for (int row = 0; row < block.length; row++) {
                for (int col = 0; col < block[0].length; col++) {
                    if (block[row][col] == '#') {
                        if (pivotRow == -1) {
                            pivotRow = row;
                            pivotCol = col;
                        }

                        blocks[rotate].add(new Point(pivotRow - row, pivotCol - col));
                    }
                }
            }

            block = rotate(block);
        }
    }
}