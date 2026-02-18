# Conway's Game of Life - Java Implementation

A simple, terminal-based implementation of Conway's Game of Life that demonstrates cellular automaton behavior through clear, educational code.

> **Note:** This documentation was created with the assistance of Claude (Anthropic AI) to help explain the implementation and programming concepts in detail.
>
> Edited and reviewed by Titus Ward.

---

## Table of Contents
1. [What is Conway's Game of Life?](#what-is-conways-game-of-life)
2. [The Rules](#the-rules)
3. [Code Structure Overview](#code-structure-overview)
4. [Static Variables](#static-variables)
5. [The Eight Directions](#the-eight-directions)
6. [Helper Methods](#helper-methods)
7. [The Core Logic: nextGen](#the-core-logic-nextgen)
8. [Grid Management](#grid-management)
9. [Running the Simulation](#running-the-simulation)
10. [Common Patterns](#common-patterns)

---

## What is Conway's Game of Life?

Conway's Game of Life is a cellular automaton created by mathematician John Conway in 1970. It's a zero-player game, meaning its evolution is determined by its initial state with no further input required.

The "game" takes place on a grid where each cell can be either **alive** or **dead**. With each generation (time step), every cell simultaneously updates based on its eight surrounding neighbors.

Despite having only four simple rules, the Game of Life produces incredibly complex and beautiful patterns, including stable structures, oscillators, and even patterns that move across the grid.

---

## The Rules

Every cell in the grid follows these four rules each generation:

**Rule 1 - Underpopulation:** Any live cell with fewer than 2 live neighbors dies (as if by loneliness)

**Rule 2 - Survival:** Any live cell with 2 or 3 live neighbors lives on to the next generation

**Rule 3 - Overpopulation:** Any live cell with more than 3 live neighbors dies (as if by overcrowding)

**Rule 4 - Reproduction:** Any dead cell with exactly 3 live neighbors becomes a live cell (as if by reproduction)

**Critical concept:** All cells update *simultaneously*. We must evaluate every cell based on the current generation before updating any of them.

---

## Code Structure Overview

This implementation uses a 2D String array to represent the grid, where each cell is either `"."` (dead) or `"■"` (alive). The main loop repeatedly:
1. Prints the current grid state
2. Calculates the next generation based on the rules
3. Updates the grid
4. Repeats

---

## Static Variables

These variables are accessible throughout the entire `GameOfLife` class:

```java
static String DEAD = ".";
static String ALIVE = "■";
static String[][] grid = new String[30][35];
```

**Explanation:**
- `DEAD` and `ALIVE`: Constants representing cell states. Using constants makes the code more readable and easier to modify.
- `grid`: A 2D array with 30 rows and 35 columns. Think of it like a spreadsheet where each cell holds a String.
- `static`: These belong to the class itself, not to instances of the class. Since we're not creating objects, everything is static.

---

## The Eight Directions

Every cell has exactly 8 neighbors: up, down, left, right, and the four diagonals.

```java
private static final int[][] DIRS_8 = {
    {-1, 0},  // top
    {1, 0},   // bottom
    {0, -1},  // left
    {0, 1},   // right
    {-1, -1}, // top-left
    {-1, 1},  // top-right
    {1, -1},  // bottom-left
    {1, 1}    // bottom-right
};
```

**How it works:**
- Each inner array `{row_offset, col_offset}` represents a direction
- To check a neighbor: add the offset to the current cell's position
- Example: If we're at position (5, 10), the top neighbor is at (5 + (-1), 10 + 0) = (4, 10)

**Visual representation:**

<img width="349" height="353" alt="DIR8" src="https://github.com/user-attachments/assets/2374fdd3-7f83-48a2-a59f-2f8da968d267" />

---

## Helper Methods

### isFilled Method

This method checks if a specific cell is alive and handles edge cases.

```java
public static boolean isFilled(String[][] grid, int r, int c) {
    if(grid == null || grid.length == 0) return false;
    if (r < 0 || r >= grid.length) return false;
    if (c < 0 || c >= grid[r].length) return false;
    String cell = grid[r][c];
    return cell != null && !cell.equals(DEAD);
}
```

**Why all the checks?**
1. **`if(grid == null || grid.length == 0)`**: Ensures the grid exists
2. **`if (r < 0 || r >= grid.length)`**: Prevents array out-of-bounds errors for row
3. **`if (c < 0 || c >= grid[r].length)`**: Prevents array out-of-bounds errors for column
4. **`return cell != null && !cell.equals(DEAD)`**: Returns true only if the cell exists and is alive

**Why is boundary checking important?**
Cells on the edges of the grid have neighbors that would be outside the grid. Without these checks, the program would crash with an `ArrayIndexOutOfBoundsException`.

---

### countFilledNeighbors Method

Counts how many living neighbors surround a given cell.

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

**Step-by-step:**
1. Start with `count = 0`
2. Loop through each of the 8 directions in `DIRS_8`
3. Calculate the neighbor's position: `nr = r + d[0]` and `nc = c + d[1]`
4. Check if that neighbor is alive using `isFilled`
5. If alive, increment the count
6. Return the total count

**Enhanced for loop syntax:**
- `for (int[] d : DIRS_8)` means "for each array `d` in `DIRS_8`"
- This is shorthand for looping through all elements without needing an index variable
- Equivalent to: `for (int i = 0; i < DIRS_8.length; i++) { int[] d = DIRS_8[i]; ... }`

**Example:**
If a cell at position (5, 5) has neighbors at (4,4), (4,5), and (6,5), this method returns `3`.

---

## The Core Logic: nextGen

This is the heart of the Game of Life. It applies all four rules to every cell and returns the next generation.

```java
public static String[][] nextGen() {
    String[][] newgen = new String[30][35];
    
    for(int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[r].length; c++) {
            int neighbors = countFilledNeighbors(grid, r, c);
            String cell = grid[r][c];
            
            if (cell != null && !cell.equals(DEAD)) {
                // Cell is currently ALIVE
                if (neighbors == 2 || neighbors == 3) {
                    newgen[r][c] = ALIVE;  // Survives
                } else {
                    newgen[r][c] = DEAD;   // Dies
                }
            } else {
                // Cell is currently DEAD
                if (neighbors == 3) {
                    newgen[r][c] = ALIVE;  // Becomes alive
                } else {
                    newgen[r][c] = DEAD;   // Stays dead
                }
            }
        }
    }
    
    return newgen;
}
```

### Why Create a New Grid?

```java
String[][] newgen = new String[30][35];
```

**This is crucial!** We create a completely separate grid for the next generation. If we modified cells in the original grid as we checked them, later cells would see the updated values instead of the original state, breaking the simulation.

**Example of what goes wrong without a new grid:**
```
Current state: [■][■][.]
                ↑
If we kill this cell immediately when checking it,
the next cell will see [.][■][.] instead of [■][■][.]
```

### The Decision Tree

For each cell, we follow this logic:

#### Branch 1: Cell is Currently ALIVE
```java
if (cell != null && !cell.equals(DEAD)) {
    if (neighbors == 2 || neighbors == 3) {
        newgen[r][c] = ALIVE;  // Rule 2: Survival
    } else {
        newgen[r][c] = DEAD;   // Rule 1 or 3: Dies
    }
}
```

- **2-3 neighbors**: Survives (perfect conditions)
- **0-1 neighbors**: Dies from underpopulation
- **4+ neighbors**: Dies from overpopulation

#### Branch 2: Cell is Currently DEAD
```java
else {
    if (neighbors == 3) {
        newgen[r][c] = ALIVE;  // Rule 4: Reproduction
    } else {
        newgen[r][c] = DEAD;   // Stays dead
    }
}
```

- **Exactly 3 neighbors**: Comes to life (reproduction)
- **Any other count**: Remains dead

### Visual Example

Let's trace a specific cell through one generation:

```
Generation N:        Generation N+1:
. . ■                . ■ .
. ■ .       →        . ■ .
. . ■                . ■ .

Center cell (■):
- Has 3 living neighbors
- Is currently alive
- 3 neighbors → survives
- Result: Still alive in next gen
```

---

## Grid Management

These utility methods handle creating and displaying the grid.

### createGrid Method

```java
public static void createGrid() {
    for (String[] strings : grid) {
        Arrays.fill(strings, ".");
    }
}
```

**What it does:**
- Loops through each row in the grid
- Fills every cell with `"."` (dead)
- This initializes the grid to a blank state

**Enhanced for loop:**
- `for (String[] strings : grid)` means "for each row in grid"
- `Arrays.fill(strings, ".")` fills that entire row with dead cells

---

### printGrid Method

```java
public static void printGrid() {
    for (String[] row : grid) {
        for (String cell : row) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}
```

**How it works:**
1. Outer loop: iterate through each row
2. Inner loop: iterate through each cell in that row
3. Print each cell followed by a space
4. After finishing a row, print a newline to move to the next line

**Output example:**
```
. . . ■ . . .
. . ■ . ■ . .
. . . ■ . . .
```

---

## Running the Simulation

The main method sets up the initial state and runs the simulation loop.

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

**Step-by-step execution:**

1. **`createGrid()`**: Initialize all cells to dead
2. **`glider()`**: Set up a specific starting pattern (the glider)
3. **Loop 100 times:**
   - Print the current generation number
   - Print the grid
   - Calculate the next generation with `nextGen()`
   - **Update the grid**: `grid = nextGen()` replaces the old grid with the new one
   - **Wait 500ms**: `Thread.sleep(500)` pauses for half a second so we can watch the evolution

**Why try/catch?**
- `Thread.sleep()` can throw an `InterruptedException` if the thread is interrupted while sleeping
- Java requires us to handle this potential exception
- If interrupted, we `break` out of the loop

---

## Common Patterns

Conway's Game of Life has many well-known patterns. Here are a few:

### Glider
A pattern that moves diagonally across the grid.

```
Generation 0:    Generation 1:    Generation 2:
. ■ .            . . .            . . .
. . ■            ■ . ■            . . ■
■ ■ ■            . ■ ■            ■ . ■
                 . ■ .            . ■ ■
```

**Implementation:**
```java
public static void glider() {
    grid[2][4] = "■";
    grid[3][5] = "■";
    grid[4][5] = "■";
    grid[4][4] = "■";
    grid[4][3] = "■";
}
```

### Blinker (Oscillator)
Alternates between horizontal and vertical.

```
Generation 0:    Generation 1:
. . .            . ■ .
■ ■ ■            . ■ .
. . .            . ■ .
```

### Block (Still Life)
Stays the same forever.

```
■ ■
■ ■
```

---

## Compiling and Running

To run this program:

1. Save the code as `GameOfLife.java`
2. Open a terminal in the same directory
3. Compile: `javac GameOfLife.java`
4. Run: `java GameOfLife`
5. Watch the simulation! Press `Ctrl+C` to stop.

---

## Acknowledgments

This documentation was created with the assistance of Claude (Anthropic AI) to provide clear explanations of both the Game of Life logic and the Java programming concepts used in this implementation.

Edited and reviewed by Titus Ward.
