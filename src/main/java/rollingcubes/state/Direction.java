package rollingcubes.state;

/**
 * Enum for the possible four directions, where the dices can be rolled to.
 */
public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private int dx;
    private int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the changed direction's x-coordinate.
     *
     * @return Returns the changed direction's x-coordinate.
     */
    public int getDx() {
        return dx;
    }

    /**
     * Returns the changed direction's y-coordinate.
     *
     * @return Returns the changed direction's y-coordinate.
     */
    public int getDy() {
        return dy;
    }

    /**
     * Returns the possible direction depending on the x-coordinate and the y-coordinate.
     *
     * @param dx dx the changed x-coordinate
     * @param dy the changed y-coordinate
     * @return Returns the possible direction depending on
     * the x-coordinate and the y-coordinate.
     */
    public static Direction of(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns the opposite direction of this direction.
     *
     * @return the opposite direction of this direction
     */
    public Direction opposite() {
        switch (this) {
            case UP: return DOWN;
            case RIGHT: return LEFT;
            case DOWN:return UP;
            case LEFT: return RIGHT;
        }
        throw new AssertionError();
    }

}
