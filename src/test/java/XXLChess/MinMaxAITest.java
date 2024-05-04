package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinMaxAITest {
    public Board board;
    public MinMaxAI whiteAI;
    public MinMaxAI blackAI;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testGetBestMove_initialSetup() {
        MinMaxAI whiteAI = new MinMaxAI(board, 3, true);
        MinMaxAI blackAI = new MinMaxAI(board, 3, false);
        int[] bestMove = whiteAI.getBestMove();
        assertNotNull(bestMove);
        // assertTrue(board.isMoveValid(bestMove[0], bestMove[1], bestMove[2], bestMove[3]));

        bestMove = blackAI.getBestMove();
        assertNotNull(bestMove);
        // assertTrue(board.isMoveValid(bestMove[0], bestMove[1], bestMove[2], bestMove[3]));
    }

    @Test
    void testGetBestMove_specificPosition() {
        // Clear the board
        board.clearBoard();

        // Set up a specific position
        board.setPiece(0, 0, new King(0, 0, true));
        board.setPiece(1, 1, new Pawn(1, 1, false));
        board.setPiece(6, 6, new King(6, 6, false));

        MinMaxAI specificWhiteAI = new MinMaxAI(board, 3, true);
        MinMaxAI specificBlackAI = new MinMaxAI(board, 3, false);

        // Test the best move for white
        int[] bestMove = specificWhiteAI.getBestMove();
        assertNotNull(bestMove);
        assertTrue(board.isMoveValid(bestMove[0], bestMove[1], bestMove[2], bestMove[3]));

        // Test the best move for black
        bestMove = specificBlackAI.getBestMove();
        assertNotNull(bestMove);
        assertTrue(board.isMoveValid(bestMove[0], bestMove[1], bestMove[2], bestMove[3]));
    }

    @Test
    public void testMaxValueWithDepthZero() {
        board.clearBoard();
        // Set up a simple board position
        board.setPiece(0, 0, new King(0, 0, true));
        board.setPiece(1, 0, new Pawn(1, 0, true));
        board.setPiece(2, 0, new Pawn(2, 0, false));
        board.setPiece(1, 1, new King(1, 1, false));
        MinMaxAI minMaxAI = new MinMaxAI(board, 0, true);
    
        // Act
        double maxValue = minMaxAI.maxValue(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
    
        // Assert
        assertEquals(0.0, maxValue, 0.01);
    }    
}
