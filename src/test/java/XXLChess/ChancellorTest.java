package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChancellorTest {

    private Chancellor chancellor;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        chancellor = new Chancellor(7, 7, true); // Chancellor is placed in the middle of the board
        board.setPiece(7, 7, chancellor);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving like a knight
        assertTrue(chancellor.isMoveValid(5, 6, board));
        // Test moving like a rook
        assertTrue(chancellor.isMoveValid(7, 4, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(5, 6, new Pawn(5, 6, true));
        assertFalse(chancellor.isMoveValid(5, 6, board));
        // Test moving to a square that is not valid for the chancellor
        assertFalse(chancellor.isMoveValid(5, 5, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = chancellor.getValidMoves(7, 7, board);
        assertEquals(34, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(8.5, chancellor.getPieceValue());
    }
}
