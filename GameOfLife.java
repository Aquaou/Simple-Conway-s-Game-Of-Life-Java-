import java.util.Arrays;

public class GameOfLife {

    static String DEAD = ".";
    static String ALIVE = "■";

    static String[][] grid = new String[30][35];

    private static final int[][] DIRS_8 = {
            {-1, 0}, // top
            {1, 0}, // bottom
            {0, -1}, // left
            {0, 1}, // right
            {-1, -1}, // top-left
            {-1, 1}, // top-right
            {1, -1}, // bottom-left
            {1, 1} // bottom-right
    };

    public static boolean isFilled(String[][] grid, int r, int c) {
        if(grid == null || grid.length == 0) return false;
        if (r < 0 || r >= grid.length) return false;
        if (c < 0 || c >= grid[r].length) return false;

        String cell = grid[r][c];
        return cell != null && !cell.equals(DEAD);
    }

    public static int countFilledNeighbors(String[][] grid, int r, int c) {
        int count = 0;
        for (int[] d : DIRS_8) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (isFilled(grid, nr, nc)) count++;
        }
        return count;
    }

    public static String[][] nextGen() {
        String[][] newgen = new String[30][35];

        for(int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int neighbors = countFilledNeighbors(grid, r, c);
                String cell = grid[r][c];

                if (cell != null && !cell.equals(DEAD)) {
                    if (neighbors == 2 || neighbors == 3) {
                        newgen[r][c] = ALIVE;
                    } else {
                        newgen[r][c] = DEAD;
                    }
                } else {
                    if (neighbors == 3) {
                        newgen[r][c] = ALIVE;
                    } else {
                        newgen[r][c] = DEAD;
                    }
                }
            }
        }
        return newgen;
    }

    public static void createGrid() {
        for (String[] strings : grid) {
            Arrays.fill(strings, ".");
        }
    }

    public static void printGrid() {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void glider() {
        grid[2][4] = "■";
        grid[3][5] = "■";
        grid[4][5] = "■";
        grid[4][4] = "■";
        grid[4][3] = "■";
    }

    public static void main(String[] args) {
        createGrid();
        glider();

        for (int generation = 0; generation < 100; generation++) {
            System.out.println("Generation: " + generation);
            System.out.println();

            printGrid();

            grid = nextGen();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
