package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    private King king;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        king = new King(7, 7, true); // King is placed in the middle of the board
        board.setPiece(7, 7, king);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving one square in any direction
        assertTrue(king.isMoveValid(6, 7, board));
        assertTrue(king.isMoveValid(8, 7, board));
        assertTrue(king.isMoveValid(7, 6, board));
        assertTrue(king.isMoveValid(7, 8, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(8, 7, new Pawn(8, 7, true));
        assertFalse(king.isMoveValid(8, 7, board));
        // Test moving to a square that is not valid for the King
        assertFalse(king.isMoveValid(5, 7, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = king.getValidMoves(7, 7, board);
        // King should have 8 valid moves from the middle of the board
        assertEquals(8, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(99999.0, king.getPieceValue());
    }
}
