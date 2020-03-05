package dp;

/*
Questions: 221
    Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Solution:
    Algorithm: DP
        definitions:
            P(x, y): point with the rowIndex x, columnIndex y
            f(x, y): the edge length of the max square area that has the P(x, y) as the right-bottom point.
        DP equation: f(x, y) = if P(x, y) == 1 then min(f(x - 1, y), f(x, y - 1), f(x - 1, y - 1)) + 1 else 0;

        The original problem's result:
            max(f(x, y) * f(x, y)) for x: 0 -> rowLen, for y: 0 -> columnLen

    Complexity:
        Time: O(rowLen * columnLen)
        Space: O(rowLen * columnLen)

 */
public class MaximalSquare {
    public static void main(String[] args) {
        char[][] input = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}};
        System.out.println(maximalSquare(input));
    }

    public static int maximalSquare(char[][] matrix) {
        if(matrix.length == 0) {
            return 0;
        }
        int rowLen = matrix.length;
        int columnLen = matrix[0].length;
        int maxEdge = 0;
        int[][] maxSquareEdge = new int[rowLen][columnLen];

        for(int i = 0; i < rowLen; i ++) {
            for(int j = 0; j < columnLen; j ++) {
                if(matrix[i][j] == '0') {
                    continue;
                }
                if(i == 0 || j == 0) {
                    maxSquareEdge[i][j] = 1;
                } else {
                    maxSquareEdge[i][j] = Math.min(maxSquareEdge[i - 1][j], Math.min(maxSquareEdge[i][j - 1], maxSquareEdge[i - 1][j - 1])) + 1;
                }
                maxEdge = Math.max(maxEdge, maxSquareEdge[i][j]);
            }
        }
        return maxEdge * maxEdge;
    }
}
