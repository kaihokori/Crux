package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    private Knight knight;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        knight = new Knight(7, 7, true); // Knight is placed in the middle of the board
        board.setPiece(7, 7, knight);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving 2 squares vertically and 1 square horizontally
        assertTrue(knight.isMoveValid(5, 8, board));
        // Test moving 2 squares horizontally and 1 square vertically
        assertTrue(knight.isMoveValid(6, 5, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(5, 8, new Pawn(5, 8, true));
        assertFalse(knight.isMoveValid(5, 8, board));
        // Test moving to a square that is not valid for the Knight
        assertFalse(knight.isMoveValid(6, 6, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = knight.getValidMoves(7, 7, board);
        // Knight should have 8 valid moves from the middle of the board
        assertEquals(8, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(2.0, knight.getPieceValue());
    }
}
