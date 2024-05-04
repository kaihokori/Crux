package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchbishopTest {

    private Archbishop archbishop;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        archbishop = new Archbishop(7, 7, true); // Archbishop is placed in the middle of the board
        board.setPiece(7, 7, archbishop);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving like a bishop
        assertTrue(archbishop.isMoveValid(9, 9, board));
        // Test moving like a knight
        assertTrue(archbishop.isMoveValid(6, 9, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(9, 9, new Pawn(9, 9, true));
        assertFalse(archbishop.isMoveValid(9, 9, board));
        // Test moving to a square that is not valid for the Archbishop
        assertFalse(archbishop.isMoveValid(7, 6, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = archbishop.getValidMoves(7, 7, board);
        // Archbishop should have 33 valid moves from the middle of the board (8 like a knight + 25 like a bishop)
        assertEquals(33, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(7.5, archbishop.getPieceValue());
    }
}
