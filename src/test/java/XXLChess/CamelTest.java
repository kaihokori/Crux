package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CamelTest {

    private Camel camel;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        camel = new Camel(7, 7, true); // Camel is placed in the middle of the board
        board.setPiece(7, 7, camel);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving 3 squares vertically and 1 square horizontally
        assertTrue(camel.isMoveValid(4, 8, board));
        // Test moving 3 squares horizontally and 1 square vertically
        assertTrue(camel.isMoveValid(6, 4, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(4, 8, new Pawn(4, 8, true));
        assertFalse(camel.isMoveValid(4, 8, board));
        // Test moving to a square that is not valid for the Camel
        assertFalse(camel.isMoveValid(6, 6, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = camel.getValidMoves(7, 7, board);
        // Camel should have 8 valid moves from the middle of the board
        assertEquals(8, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(2.0, camel.getPieceValue());
    }
}
