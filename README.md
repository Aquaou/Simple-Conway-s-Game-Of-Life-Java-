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
<img width="698" height="705" alt="DIR8" src="https://github.com/user-attachments/assets/2374fdd3-7f83-48a2-a59f-2f8da968d267" />

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
