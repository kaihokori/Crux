package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GuardTest {

    private Guard guard;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        guard = new Guard(7, 7, true); // Guard is placed in the middle of the board
        board.setPiece(7, 7, guard);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving like a King
        assertTrue(guard.isMoveValid(8, 7, board)); // Move 1 step down
        // Test moving like a Knight
        assertTrue(guard.isMoveValid(5, 6, board)); // Move 2 steps up, 1 step left
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(8, 7, new Pawn(8, 7, true));
        assertFalse(guard.isMoveValid(8, 7, board));
        // Test moving to a square that is not valid for the Guard
        assertFalse(guard.isMoveValid(7, 5, board)); // Move 2 steps left
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = guard.getValidMoves(7, 7, board);
        // Guard should have 16 valid moves from the middle of the board (8 like a King and 8 like a Knight)
        assertEquals(16, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(5.0, guard.getPieceValue());
    }
}
