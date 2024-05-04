package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    private Pawn whitePawn;
    private Pawn blackPawn;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        whitePawn = new Pawn(12, 7, true);
        blackPawn = new Pawn(1, 7, false);
        board.setPiece(12, 7, whitePawn);
        board.setPiece(1, 7, blackPawn);
    }

    @Test
    public void testIsMoveValid() {
        // Test moving forward one square
        assertTrue(whitePawn.isMoveValid(11, 7, board));
        assertTrue(blackPawn.isMoveValid(2, 7, board));
        // Test moving forward two squares on first move
        assertTrue(whitePawn.isMoveValid(10, 7, board));
        assertTrue(blackPawn.isMoveValid(3, 7, board));
        // Test capturing a piece diagonally
        board.setPiece(11, 6, new Rook(11, 6, false));
        assertTrue(whitePawn.isMoveValid(11, 6, board));
        // Test moving to a square occupied by a piece of the same color
        board.setPiece(11, 7, new Rook(11, 7, true));
        assertFalse(whitePawn.isMoveValid(11, 7, board));
        // Test moving to a square that is not valid for the Pawn
        assertFalse(whitePawn.isMoveValid(10, 6, board));
    }

    @Test
    public void testGetValidMoves() {
        List<int[]> validMovesWhite = whitePawn.getValidMoves(12, 7, board);
        List<int[]> validMovesBlack = blackPawn.getValidMoves(1, 7, board);
        // Pawn should have 2 valid moves from the initial position
        assertEquals(2, validMovesWhite.size());
        assertEquals(2, validMovesBlack.size());
    }

    @Test
    public void testGetPieceValue() {
        assertEquals(1.0, whitePawn.getPieceValue());
        assertEquals(1.0, blackPawn.getPieceValue());
    }
}
