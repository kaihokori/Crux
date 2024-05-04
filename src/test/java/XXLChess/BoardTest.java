package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }
    
    @Test
    public void testSetPiece() {
        // Set a piece at a specific position on the board
        Piece knight = new Knight(2, 3, true);
        board.setPiece(2, 3, knight);

        // Verify that the piece is set correctly
        assertEquals(board.getPiece(2, 3), knight);

        // Remove the piece from the board
        board.setPiece(2, 3, null);

        // Verify that the piece is removed correctly
        assertNull(board.getPiece(2, 3));
    }

    @Test
    public void testLoadBoardFromTextFile(@TempDir Path tempDir) throws IOException {
        // Create a sample board configuration as a string
        String boardConfig =
                "RNBHCGAKGCEBNR" + System.lineSeparator() +
                "PPPPPPPPPPPPPP" + System.lineSeparator() +
                "       l" + System.lineSeparator() +
                "       Q" + System.lineSeparator() +
                "       q" + System.lineSeparator() +
                "       w" + System.lineSeparator() +
                "pppppppppppppp" + System.lineSeparator() +
                "rnbhcgakgcebnr";

        // Create a temporary file with the board configuration
        Path boardFile = tempDir.resolve("sample_board.txt");
        Files.write(boardFile, boardConfig.getBytes());

        // Load the board from the temporary file and assert that no exception is thrown
        assertDoesNotThrow(() -> board.loadBoardFromTextFile(boardFile.toString()));
    }

    @Test
    public void testLoadBoardFromTextFileException() {
        Board board = new Board();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent)); // Redirects stderr to outContent
        String nonExistentFileName = "nonexistentfile.txt";

        board.loadBoardFromTextFile(nonExistentFileName);

        String expectedOutput = "java.io.FileNotFoundException: " + nonExistentFileName + " (No such file or directory)\n";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testMovePiece() {
        // Set up the board with custom pieces and positions for testing
        board.setPiece(0, 0, new Rook(0, 0, true));
        board.setPiece(0, 1, new Pawn(0, 1, true));
        board.setPiece(1, 1, new Pawn(1, 1, false));
        board.setPiece(1, 2, new Knight(1, 2, false));

        // Move a piece and check if the source square is empty
        Piece capturedPiece = board.movePiece(0, 0, 2, 0);
        assertNull(board.getPiece(0, 0));

        // Check if the destination square has the correct piece
        Piece destPiece = board.getPiece(2, 0);
        assertTrue(destPiece instanceof Rook);
        assertTrue(destPiece.isWhite);
        assertEquals(2, destPiece.row);
        assertEquals(0, destPiece.col);

        // Check if the hasMoved property is set to true
        assertTrue(destPiece.hasMoved());

        // Check if a piece was captured (there should be no captured piece in this case)
        assertNull(capturedPiece);

        // Move a piece to a square with an enemy piece and check if the enemy piece is captured
        capturedPiece = board.movePiece(1, 1, 0, 1);
        assertNotNull(capturedPiece);
        assertTrue(capturedPiece instanceof Pawn);
        assertTrue(capturedPiece.isWhite);
    }

    @Test
    public void testGetPieceInsideBoard() {
        // Set up the board with a custom piece for testing
        Rook testRook = new Rook(1, 1, true);
        board.setPiece(1, 1, testRook);

        // Verify that the getPiece method returns the correct piece
        Piece piece = board.getPiece(1, 1);
        assertSame(piece, testRook);
    }

    @Test
    public void testGetPieceOutsideBoard() {
        // Verify that the getPiece method returns null for coordinates outside the board
        assertNull(board.getPiece(-1, 1));
        assertNull(board.getPiece(1, -1));
        assertNull(board.getPiece(Board.SIZE, 1));
        assertNull(board.getPiece(1, Board.SIZE));
    }

    @Test
    public void testIsKingInCheck() {
        // Set up the board with a custom configuration for testing
        board.setPiece(0, 0, new King(0, 0, true));
        board.setPiece(2, 2, new Rook(2, 2, false));
        board.setPiece(0, 1, new Pawn(0, 1, true));
        board.setPiece(1, 1, new Pawn(1, 1, false));
        board.setPiece(1, 2, new Knight(1, 2, false));

        boolean whiteKingInCheck = board.isKingInCheck(0, 0, true);
        boolean blackKingInCheck = board.isKingInCheck(5, 7, false);

        // Verify that the white king is in check and the black king is not
        assertTrue(whiteKingInCheck);
        assertFalse(blackKingInCheck);
    }

    @Test
    public void testIsCheckmate_notCheckmate() {
        // Set up a simple board where the white king is not in checkmate
        board.setPiece(0, 0, new King(0, 0, true));
        board.setPiece(0, 1, new Rook(0, 1, false));

        boolean isWhiteCheckmate = board.isCheckmate(true);

        assertFalse(isWhiteCheckmate, "The white king should not be in checkmate");
    }

    @Test
    public void testIsCheckmate_checkmate() {
        // Set up a custom board with checkmate position
        // Black king is in checkmate
        board.setPiece(0, 0, new King(0, 0, false));
        board.setPiece(0, 2, new Queen(0, 2, true));
        board.setPiece(1, 1, new Queen(1, 1, true));
        board.setPiece(1, 2, new Queen(1, 2, true));
        board.setPiece(2, 0, new Queen(2, 0, true));
        board.setPiece(2, 1, new Queen(2, 1, true));
        board.setPiece(2, 2, new Queen(2, 2, true));

        assertTrue(board.isCheckmate(false), "The black king should be in checkmate");

        // Set up a custom board where black king is not in checkmate
        board.setPiece(0, 0, new King(0, 0, false));
        board.setPiece(0, 1, new Queen(0, 1, true));

        assertFalse(board.isCheckmate(true), "The black king should not be in checkmate");
    }

    @Test
    public void testEvaluateBoard() {
        // Set up the board with custom pieces and positions for testing
        board.setPiece(0, 0, new Rook(0, 0, true));
        board.setPiece(0, 1, new Pawn(0, 1, true));
        board.setPiece(1, 1, new Pawn(1, 1, false));
        board.setPiece(1, 2, new Knight(1, 2, false));

        // Calculate the board evaluation score for white and black
        double whiteScore = board.evaluateBoard(true);
        double blackScore = board.evaluateBoard(false);

        // Verify the board evaluation score for each color
        assertEquals(whiteScore, 3.25, 0.001); // Rook (5.25) + Pawn (1)
        assertEquals(blackScore, -3.25, 0.001); // -1 * (Pawn (1) + Knight (3))
    }

    @Test
    public void testGetAllMovesForColor() {
        // Set up the board with custom pieces and positions for testing
        board.setPiece(0, 0, new Rook(0, 0, true));
        board.setPiece(0, 1, new Pawn(0, 1, true));
        board.setPiece(1, 1, new Pawn(1, 1, false));
        board.setPiece(1, 2, new Knight(1, 2, false));

        List<int[]> whiteMoves = board.getAllMovesForColor(true);
        List<int[]> blackMoves = board.getAllMovesForColor(false);

        // Verify the total number of moves for each color
        assertEquals(whiteMoves.size(), 13);
        assertEquals(blackMoves.size(), 8);
    }
}
