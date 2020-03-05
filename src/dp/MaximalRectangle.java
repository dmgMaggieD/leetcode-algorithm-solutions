package dp;

import java.util.Arrays;
import java.util.Stack;

public class MaximalRectangle {
    public static void main(String[] args) {
        char[][] input = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}};
        System.out.println(maximalRectangleDpSolution(input));
        System.out.println(maximalRectangleStackSolution(input));
    }

    // Solution with dynamic programing + memorization
    public static int maximalRectangleDpSolution(char[][] matrix) {
        if(matrix.length == 0) return 0;
        int columnLen = matrix[0].length;
        int[] heights = new int[columnLen];
        int[] lefts = new int[columnLen];
        int[] rights = new int[columnLen];
        Arrays.fill(rights, columnLen);
        int maxRectangle = 0;
        for (char[] chars : matrix) {
            int currentLeft = 0;
            for (int j = 0; j < columnLen; j++) {

                //Update heights and lefts
                if (chars[j] == '1') {
                    heights[j]++;
                    lefts[j] = Math.max(lefts[j], currentLeft);
                } else {
                    heights[j] = 0;
                    lefts[j] = 0;
                    currentLeft = j + 1;
                }
            }
            int currentRight = columnLen;
            for (int j = columnLen - 1; j >= 0; j--) {
                if (chars[j] == '1') {
                    rights[j] = Math.min(rights[j], currentRight);
                } else {
                    rights[j] = columnLen;
                    currentRight = j;
                }
            }
            for (int j = 0; j < columnLen; j++) {
                maxRectangle = Math.max(maxRectangle, heights[j] * (rights[j] - lefts[j]));
            }
        }
        return maxRectangle;
    }

    // Solution using stack:
    public static int maximalRectangleStackSolution(char[][] matrix) {
        if(matrix.length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] preHeight = new int[matrix[0].length];
        for (char[] chars : matrix) {
            int[] heights = calculateHeights(chars, preHeight);
            maxArea = Math.max(maxArea, maxHistgramArea(heights));
            preHeight = heights;
        }
        return maxArea;
    }

    private static int[] calculateHeights(char[] row, int[] preHeight) {
        int[] heights = new int[row.length];
        for(int i = 0; i < row.length; i ++  ) {
            if(row[i] != '0') {
                heights[i] = preHeight[i] + 1;
            }
        }
        return heights;
    }

    private static int maxHistgramArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        for(int i = 0; i < heights.length; i ++) {
            while(!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                int popIndex = stack.pop();
                int lastIndex = stack.isEmpty() ? 0 : stack.peek() + 1;
                max = Math.max(max, heights[popIndex] * (i - lastIndex));
            }
            stack.push(i);
        }

        while(!stack.isEmpty()) {
            int popIndex = stack.pop();
            int lastIndex = stack.isEmpty() ? 0 : stack.peek() + 1;
            max = Math.max(max, heights[popIndex] * (heights.length - lastIndex));
        }
        return max;
    }

}
