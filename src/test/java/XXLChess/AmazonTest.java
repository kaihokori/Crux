package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AmazonTest {

    private Amazon amazon;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        amazon = new Amazon(7, 7, true); // Amazon is placed in the middle of the board
        board.setPiece(7, 7, amazon);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving like a Bishop
        assertTrue(amazon.isMoveValid(5, 5, board));
        // Test moving like a Rook
        assertTrue(amazon.isMoveValid(7, 4, board));
        // Test moving like a Knight
        assertTrue(amazon.isMoveValid(5, 6, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(5, 5, new Pawn(5, 5, true));
        assertFalse(amazon.isMoveValid(5, 5, board));
        // Test moving to a square that is not valid for the Amazon
        assertFalse(amazon.isMoveValid(11, 6, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = amazon.getValidMoves(7, 7, board);
        // Amazon should have 59 valid moves from the middle of the board
        assertEquals(59, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(12.0, amazon.getPieceValue());
    }
}
