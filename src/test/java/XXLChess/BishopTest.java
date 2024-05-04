package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    private Bishop bishop;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        bishop = new Bishop(7, 7, true); // Bishop is placed in the middle of the board
        board.setPiece(7, 7, bishop);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving diagonally
        assertTrue(bishop.isMoveValid(10, 10, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(10, 10, new Pawn(10, 10, true));
        assertFalse(bishop.isMoveValid(10, 10, board));
        // Test moving to a square that is not valid for the Bishop
        assertFalse(bishop.isMoveValid(9, 10, board));
        // Test moving to a square with a piece blocking the path
        board.setPiece(9, 9, new Pawn(9, 9, false));
        assertFalse(bishop.isMoveValid(10, 10, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMoves = bishop.getValidMoves(7, 7, board);
        // Bishop should have 26 valid moves from the middle of the board on a 14x14 grid
        assertEquals(25, validMoves.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(3.625, bishop.getPieceValue());
    }
}
