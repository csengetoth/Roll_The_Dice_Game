package rollingcubes.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RollingCubesStateTest {

    private void assertEmptySpace(int expectedEmptyRow, int expectedEmptyCol, RollingCubesState state) {
        assertAll(
                () -> assertEquals(expectedEmptyRow, state.getEmptyRow()),
                () -> assertEquals(expectedEmptyCol, state.getEmptyCol())
        );
    }

    @Test
    void testOneArgConstructor_InvalidArg() {
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(null));
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {1, 1},
                {1, 0},
                {1, 1}})
        );
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {0},
                {1, 2},
                {3, 4, 5},
                {6, 7, 8}})
        );
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}})
        );
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}})
        );
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {0, 1, 1},
                {1, 1, 1},
                {1, 1, 0},
                {0, 0, 0}})
        );
        assertThrows(IllegalArgumentException.class, () -> new RollingCubesState(new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}})
        );
    }

    @Test
    void testOneArgConstructor_ValidArg() {
        int[][] a = new int[][] {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 0, 1, 1}
        };
        RollingCubesState state = new RollingCubesState(a);
        assertArrayEquals(new Cube[][] {
                {Cube.CUBE1, Cube.CUBE1, Cube.CUBE1, Cube.CUBE1},
                {Cube.CUBE1, Cube.CUBE1, Cube.CUBE1, Cube.CUBE1},
                {Cube.CUBE1, Cube.CUBE1, Cube.CUBE1, Cube.CUBE1},
                {Cube.CUBE1, Cube.EMPTY, Cube.CUBE1, Cube.CUBE1}
        }, state.getTray());
        assertEmptySpace(3, 1, state);
    }

    @Test
    void testIsSolved() {
        assertFalse(new RollingCubesState().isSolved());
        assertTrue(new RollingCubesState(new int[][] {
                {2, 2, 2, 2},
                {2, 0, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2}}).isSolved());
        assertTrue(new RollingCubesState(new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 0, 2},
                {2, 2, 2, 2}}).isSolved());
    }

    @Test
    void testCanRollToEmptySpace() {
        RollingCubesState state = new RollingCubesState();
        assertEmptySpace(0, 1, state);
        assertTrue(state.canRollToEmptySpace(0, 0));
        assertFalse(state.canRollToEmptySpace(3, 1));
        assertTrue(state.canRollToEmptySpace(0, 2));
        assertFalse(state.canRollToEmptySpace(1, 0));
        assertTrue(state.canRollToEmptySpace(1, 1));
        assertFalse(state.canRollToEmptySpace(1, 2));
        assertFalse(state.canRollToEmptySpace(2, 0));
        assertFalse(state.canRollToEmptySpace(2, 1));
        assertFalse(state.canRollToEmptySpace(2, 2));
    }

    @Test
    void testGetRollDirection() {
        RollingCubesState state = new RollingCubesState();
        assertEmptySpace(0, 1, state);
        assertEquals(Direction.RIGHT, state.getRollDirection(0, 0));
        assertEquals(Direction.UP, state.getRollDirection(1, 1));
        assertEquals(Direction.LEFT, state.getRollDirection(0, 2));
        assertThrows(IllegalArgumentException.class, () -> state.getRollDirection(3, 3));
        assertThrows(IllegalArgumentException.class, () -> state.getRollDirection(3, 2));
        assertThrows(IllegalArgumentException.class, () -> state.getRollDirection(3, 1));
        assertThrows(IllegalArgumentException.class, () -> state.getRollDirection(3, 0));
        assertThrows(IllegalArgumentException.class, () -> state.getRollDirection(2, 2));
    }

    @Test
    void testRollToEmptySpace() {
        RollingCubesState state = new RollingCubesState();
        assertEmptySpace(0, 1, state); //just to make sure that the empty space is in (0, 1) of the tray
        Cube cube = state.getTray()[0][1]; // the cube at (0, 1) will be rolled to the empty space
        state.rollToEmptySpace(0, 0); // let's roll the cube at (0, 0) to the empty space
        assertEmptySpace(0, 0, state); // the empty space now should be at (0, 0)
        //assertEquals(cube.rollTo(Direction.), state.getTray()[0][1]); // the cube rolled now should be at (0, 1) and its orientation should have been changed accordingly
        state.rollToEmptySpace(0, 1); // let's roll the cube to the empty space again
        assertEmptySpace(0, 1, state); // the empty space now should be at (0, 1) again
        assertEquals(cube, state.getTray()[0][1]); // the cube rolled now should be at (0, 1) again in its original orientation
    }

    @Test
    void testToString() {
        RollingCubesState state = new RollingCubesState();
        assertEquals("1 0 1 1 \n"
                + "1 1 1 1 \n"
                + "1 1 1 1 \n"
                + "1 1 1 1 \n", state.toString());
    }

}
