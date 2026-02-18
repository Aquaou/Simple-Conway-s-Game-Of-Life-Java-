# Simple-Conway-s-Game-Of-Life-Java-
An intuitive demonstration of Conway’s Game of Life logic. Manually set a starting pattern to observe how future generations evolve.

# Static Variables:
Available within the GameOfLife outer scope.
```java
static String DEAD = ".";
static String ALIVE = "■";
static String[][] grid = new String[30][35];
```
# Directions:
Is the 8 directions that we are required to check.
```java
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
```
<img width="349" height="353" alt="DIR8" src="https://github.com/user-attachments/assets/2374fdd3-7f83-48a2-a59f-2f8da968d267" />

# isFilled method:
Checks the grid for filled or alive cells.
```java
public static boolean isFilled(String[][] grid, int r, int c) {
        if(grid == null || grid.length == 0) return false;
        if (r < 0 || r >= grid.length) return false;
        if (c < 0 || c >= grid[r].length) return false;

        String cell = grid[r][c];
        return cell != null && !cell.equals(DEAD);
    }
```
# countFilledNeighbors method:
Counts how many of the surrounding cells are alive or not.
```java
public static int countFilledNeighbors(String[][] grid, int r, int c) {
        int count = 0;
        for (int[] d : DIRS_8) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (isFilled(grid, nr, nc)) count++;
        }
        return count;
    }
```
# nextGen method:
Calculates the next generation.
```java
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
```
# Create and Print Grid methods:
```java
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
```
# Main method:
```java
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
```
