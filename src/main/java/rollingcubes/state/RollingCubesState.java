package rollingcubes.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for the dices state representation.
 */
@Data
@Slf4j
public class RollingCubesState implements Cloneable {

    /**
     * Array for representing the original state of the board.
     */
    public static final int[][] INITIAL = {
            {1, 0, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
    };

    /**
     * The array representing an instance of the possible goal state.
     */
    public static final int[][] NEAR_GOAL = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 0, 1, 1}
    };

    /**
     * The array stores the actual state of the board.
     */
    @Setter(AccessLevel.NONE)
    private Cube[][] tray;

    /**
     * The empty place's row.
     */
    @Setter(AccessLevel.NONE)
    private int emptyRow;

    /**
     * The empty place's column.
     */
    @Setter(AccessLevel.NONE)
    private int emptyCol;

    /**
     * Creates a {@code RollingCubesState} object representing the original state
     * of the board.
     */
    public RollingCubesState() {
        this(INITIAL);
    }

    /**
     * Creates a {@code RollingCubesState} object that is given by the
     * starter array.
     *
     * @param a an array in the size 3&#xd7;3 which is the
     * starter array.
     * @throws IllegalArgumentException if the array is not
     * a valid instance of the board.
     */
    public RollingCubesState(int[][] a) {
        if (!isValidTray(a)) {
            throw new IllegalArgumentException();
        }
        initTray(a);
    }

    private boolean isValidTray(int[][] a) {
        if (a == null || a.length != 4) {
            return false;
        }
        boolean foundEmpty = false;
        for (int[] row : a) {
            if (row == null || row.length != 4) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Cube.values().length) {
                    return false;
                }
                if (space == Cube.EMPTY.getValue()) {
                    if (foundEmpty) {
                        return false;
                    }
                    foundEmpty = true;
                }
            }
        }
        return foundEmpty;
    }

    private void initTray(int[][] a) {
        this.tray = new Cube[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if ((this.tray[i][j] = Cube.of(a[i][j])) == Cube.EMPTY) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    /**
     * Checks if the game is solved or not.
     *
     * @return {@code true} if the game is solved, {@code false} otherwise
     */
    public boolean isSolved() {
        for (Cube[] row : tray) {
            for (Cube cube : row) {
                if (cube != Cube.CUBE2 && cube != Cube.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns, if a dice in it's current position is able to roll to the
     * empty place.
     *
     * @param row the rolled cube's row
     * @param col the rolled cube's column
     * @return {@code true} if the cube is able to roll
     * to the empty place, {@code false} otherwise
     */
    public boolean canRollToEmptySpace(int row, int col) {
        return 0 <= row && row <= 3 && 0 <= col && col <= 3 &&
                Math.abs(emptyRow - row) + Math.abs(emptyCol - col) == 1;
    }

    /**
     * Returns the direction where to cube is able to
     * roll to; where the empty place is.
     *
     * @param row the rolled cube's row
     * @param col the rolled cube's column
     * @return Returns the direction where to cube is able to
     * roll to; where the empty place is.
     * @throws IllegalArgumentException if the cube at its current position
     * can not be rolled to
     */
    public Direction getRollDirection(int row, int col) {
        if (! canRollToEmptySpace(row, col)) {
            throw new IllegalArgumentException();
        }
        return Direction.of(emptyRow - row, emptyCol - col);
    }

    /**
     * Rolls the cube to the given direction, where the empty place is.
     *
     * @param row the rolled cube's row
     * @param col the rolled cube's column
     * @throws IllegalArgumentException if the cube at its current position
     * can not be rolled to
     */
    public void rollToEmptySpace(int row, int col) {
        Direction direction = getRollDirection(row, col);
        log.info("Dice at ({},{}) is rolled to {}", row, col, direction);
        tray[emptyRow][emptyCol] = tray[row][col].rollTo(direction);
        tray[row][col] = Cube.EMPTY;
        emptyRow = row;
        emptyCol = col;
    }

    public RollingCubesState clone() {
        RollingCubesState copy = null;
        try {
            copy = (RollingCubesState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.tray = new Cube[tray.length][];
        for (int i = 0; i < tray.length; ++i) {
            copy.tray[i] = tray[i].clone();
        }
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cube[] row : tray) {
            for (Cube cube : row) {
                sb.append(cube).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RollingCubesState state = new RollingCubesState();
        System.out.println(state);
        state.rollToEmptySpace(0, 1);
        System.out.println(state);
    }

}
