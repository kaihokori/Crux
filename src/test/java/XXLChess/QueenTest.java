package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    private Queen queen;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        queen = new Queen(7, 7, true); // Queen is placed in the middle of the board
        board.setPiece(7, 7, queen);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving vertically
        assertTrue(queen.isMoveValid(9, 7, board));
        // Test moving horizontally
        assertTrue(queen.isMoveValid(7, 9, board));
        // Test moving diagonally
        assertTrue(queen.isMoveValid(9, 9, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(9, 9, new Pawn(9, 9, true));
        assertFalse(queen.isMoveValid(9, 9, board));
        // Test moving to a square that is not valid for the Queen
        assertFalse(queen.isMoveValid(10, 9, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = queen.getValidMoves(7, 7, board);
        // Queen should have 26 valid moves from the middle of a 14x14 board
        assertEquals(51, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(9.5, queen.getPieceValue());
    }
}
