package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    private Rook rook;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        rook = new Rook(7, 7, true); // Rook is placed in the middle of the board
        board.setPiece(7, 7, rook);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving vertically
        assertTrue(rook.isMoveValid(13, 7, board));
        // Test moving horizontally
        assertTrue(rook.isMoveValid(7, 0, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(13, 7, new Pawn(13, 7, true));
        assertFalse(rook.isMoveValid(13, 7, board));
        // Test moving to a square that is not valid for the Rook
        assertFalse(rook.isMoveValid(13, 8, board));
        // Test moving with a piece in the way
        board.setPiece(10, 7, new Pawn(10, 7, false));
        assertFalse(rook.isMoveValid(13, 7, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = rook.getValidMoves(7, 7, board);
        // Rook should have 26 valid moves from the middle of the board (7 moves in each direction)
        assertEquals(26, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(5.25, rook.getPieceValue());
    }
}
