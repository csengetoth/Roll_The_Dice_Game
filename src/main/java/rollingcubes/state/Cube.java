package rollingcubes.state;

/**
 * Class for representing the states of the dices and the empty place.
 */
public enum  Cube {

    EMPTY,
    CUBE1,
    CUBE2,
    CUBE3,
    CUBE4,
    CUBE5,
    CUBE6;

    /**
     * The array for telling which state conversions could be between the dices:
     * whether is a given direction where the dice will be rolled to,
     * which state it will get.
     */
    private static final int[][] T = {
            {0, 0, 0, 0},
            {3, 4, 2, 5},
            {1, 2, 6, 2},
            {6, 3, 1, 3},
            {4, 6, 4, 1},
            {5, 1, 5, 6},
            {2, 5, 3, 4}
    };

    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value, which is a possible instance
     * @return the value's instance
     * @throws IllegalArgumentException if the value is not
     * a possible instance
     */
    public static Cube of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the value as an integer.
     *
     * @return the integer of the instance
     */
    public int getValue() {
        return ordinal();
    }

    /**
     * Rolls the dice to the given direction.
     *
     * @param direction the direction where the dice will be rolled to.
     * @return the dice, which we said to roll to.
     * @throws UnsupportedOperationException if the method used on the
     * {@link #EMPTY} instance
     */
    public Cube rollTo(Direction direction) {
        if (this == EMPTY) {
            throw new UnsupportedOperationException();
        }
        return values()[T[ordinal()][direction.ordinal()]];
    }

    public String toString() {
        return Integer.toString(ordinal());
    }

}
