package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest extends App {

    private App app;

    @BeforeEach
    public void setUp() {
        app = new App();
    }

    public void simulateMouseClick(int x, int y) {
        mouseX = x;
        mouseY = y;
        mousePressed();
    }

    private void setCustomBoard(Piece[][] pieces) {
        Board customBoard = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                customBoard.setPiece(row, col, pieces[row][col]);
            }
        }
        app.board = customBoard;
    }    

    @Test
    public void testStartButton() {
        simulateMouseClick(startButtonX + 10, startButtonY + 10);
        assertTrue(gameStarted, "Game should start when clicking the start button");
    }
    
    @Test
    public void testDepthButton() {
        simulateMouseClick(depthButtonX + 10, depthButtonY + 10);
        assertFalse(depth == 3, "Depth should be changed from 3 when clicking the depth button");
        assertTrue(depth == 4, "Depth should be changed from 4 when clicking the depth button");

        simulateMouseClick(depthButtonX + 10, depthButtonY + 10);
        assertFalse(depth == 4, "Depth should be changed from 4 when clicking the depth button");
        assertTrue(depth == 5, "Depth should be changed from 5 when clicking the depth button");

        simulateMouseClick(depthButtonX + 10, depthButtonY + 10);
        assertFalse(depth == 5, "Depth should be changed from 5 when clicking the depth button");
        assertTrue(depth == 1, "Depth should be changed from 1 when clicking the depth button");

        simulateMouseClick(depthButtonX + 10, depthButtonY + 10);
        assertFalse(depth == 1, "Depth should be changed from 1 when clicking the depth button");
        assertTrue(depth == 2, "Depth should be changed from 2 when clicking the depth button");

        simulateMouseClick(depthButtonX + 10, depthButtonY + 10);
        assertFalse(depth == 2, "Depth should be changed from 2 when clicking the depth button");
        assertTrue(depth == 3, "Depth should be changed from 3 when clicking the depth button");
    }

    @Test
    public void testModeButton() {
        simulateMouseClick(modeButtonX + 10, modeButtonY + 10);
        assertFalse(ai, "AI should be enabled when clicking the mode button");

        simulateMouseClick(modeButtonX + 10, modeButtonY + 10);
        assertTrue(ai, "AI should be disabled when clicking the mode button");
    }

    @Test
    public void testPieceSelectionAndMovement() {
        board = new Board();
        gameStarted = true;
        whiteTurn = true;

            // Place two white pieces on the board
        int pieceRow1 = 12;
        int pieceCol1 = 6;
        Piece piece1 = new Pawn(pieceRow1, pieceCol1, true);
        board.setPiece(pieceRow1, pieceCol1, piece1);

        int pieceRow2 = 11;
        int pieceCol2 = 5;
        Piece piece2 = new Pawn(pieceRow2, pieceCol2, true);
        board.setPiece(pieceRow2, pieceCol2, piece2);

        assertNotNull(piece1, "A piece should be present at this position");

        int mouseX = pieceCol1 * CELL_SIZE + CELL_SIZE / 2;
        int mouseY = pieceRow1 * CELL_SIZE + CELL_SIZE / 2;
        simulateMouseClick(mouseX, mouseY);

        assertEquals(piece1, selectedPiece, "The clicked piece should be selected");
        assertEquals(pieceRow1, selectedRow, "Selected row should be set");
        assertEquals(pieceCol1, selectedCol, "Selected col should be set");

        // Select the second white piece
        mouseX = pieceCol2 * CELL_SIZE + CELL_SIZE / 2;
        mouseY = pieceRow2 * CELL_SIZE + CELL_SIZE / 2;
        simulateMouseClick(mouseX, mouseY);

        assertEquals(piece2, selectedPiece, "The clicked piece should change to the second white piece");
        assertEquals(pieceRow2, selectedRow, "Selected row should change");
        assertEquals(pieceCol2, selectedCol, "Selected col should change");

        // Move the selected piece to a new position
        int newRow = pieceRow2 + 1;
        int newCol = pieceCol2;
        mouseX = newCol * CELL_SIZE + CELL_SIZE / 2;
        mouseY = newRow * CELL_SIZE + CELL_SIZE / 2;
        simulateMouseClick(mouseX, mouseY);

        assertNull(selectedPiece, "The selected piece should be reset after a valid move");
        assertFalse(whiteTurn, "It should be black's turn after white makes a valid move");

        Piece movedPiece = board.getPiece(newRow, newCol);
        assertEquals(piece2, movedPiece, "The moved piece should be at the new position");

        // Test invalid move attempt
        whiteTurn = true;
        mouseX = newCol * CELL_SIZE + CELL_SIZE / 2;
        mouseY = newRow * CELL_SIZE + CELL_SIZE / 2;
        simulateMouseClick(mouseX, mouseY);

        // Try to move the piece to an invalid position
        newRow = newRow - 1;
        newCol = newCol + 1;
        mouseX = newCol * CELL_SIZE + CELL_SIZE / 2;
        mouseY = newRow * CELL_SIZE + CELL_SIZE / 2;
        simulateMouseClick(mouseX, mouseY);

        assertNull(selectedPiece, "The selected piece should be reset after an invalid move");
    }

    @Test
    public void testGetPieceIndex() {
        App app = new App();

        // Test each piece type
        assertEquals(0, app.getPieceIndex(new Pawn(0, 0, true)));
        assertEquals(1, app.getPieceIndex(new Rook(0, 0, true)));
        assertEquals(2, app.getPieceIndex(new Knight(0, 0, true)));
        assertEquals(3, app.getPieceIndex(new Bishop(0, 0, true)));
        assertEquals(4, app.getPieceIndex(new Archbishop(0, 0, true)));
        assertEquals(5, app.getPieceIndex(new Camel(0, 0, true)));
        assertEquals(6, app.getPieceIndex(new Guard(0, 0, true)));
        assertEquals(7, app.getPieceIndex(new Amazon(0, 0, true)));
        assertEquals(8, app.getPieceIndex(new King(0, 0, true)));
        assertEquals(9, app.getPieceIndex(new Chancellor(0, 0, true)));
        assertEquals(10, app.getPieceIndex(new Queen(0, 0, true)));

        // Test null input
        assertEquals(-1, app.getPieceIndex(null));
    }

    @Test
    public void checkTimers_gameNotEnded() {
        app.whiteTimeLeft = 1000;
        app.blackTimeLeft = 1000;

        app.checkTimers();

        assertFalse(app.gameEnded, "The game should not be ended when both timers have time left.");
    }

    @Test
    public void checkTimers_whiteTimeExpired() {
        app.whiteTimeLeft = 0;
        app.blackTimeLeft = 1000;

        app.checkTimers();

        assertTrue(app.gameEnded, "The game should be ended when the white timer has expired.");
    }

    @Test
    public void checkTimers_blackTimeExpired() {
        app.whiteTimeLeft = 1000;
        app.blackTimeLeft = 0;

        app.checkTimers();

        assertTrue(app.gameEnded, "The game should be ended when the black timer has expired.");
    }

    @Test
    public void testIsKingCaptured() {
        // Both kings are on the board
        Piece[][] pieces = new Piece[Board.SIZE][Board.SIZE];
        pieces[4][4] = new King(4, 4, true); // White king
        pieces[4][6] = new King(4, 6, false); // Black king
        setCustomBoard(pieces);

        assertFalse(app.isKingCaptured(), "Both kings are on the board, no king should be captured");

        // Only the white king is on the board
        pieces[4][6] = null; // Remove black king
        setCustomBoard(pieces);

        assertTrue(app.isKingCaptured(), "Only the white king is on the board, the black king should be captured");

        // Only the black king is on the board
        pieces[4][4] = null; // Remove white king
        pieces[4][6] = new King(4, 6, false); // Add black king
        setCustomBoard(pieces);

        assertTrue(app.isKingCaptured(), "Only the black king is on the board, the white king should be captured");

        // No kings are on the board
        pieces[4][6] = null; // Remove black king
        setCustomBoard(pieces);

        assertTrue(app.isKingCaptured(), "No kings are on the board, one of the kings should be captured");
    }

    @Test
    public void testRunAI() {
        Board testBoard = new Board();
        testBoard.clearBoard();

        // Set up a simple board state
        testBoard.setPiece(0, 4, new King(0, 4, false));
        testBoard.setPiece(1, 4, new Pawn(1, 4, false));
        testBoard.setPiece(6, 4, new Pawn(6, 4, true));
        testBoard.setPiece(7, 4, new King(7, 4, true));

        app.board = testBoard;
        app.whiteTurn = false;
        app.depth = 3;

        // Call the runAI method
        app.runAI();

        // Verify if the AI played a valid move
        boolean isValidMove = false;
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Piece piece = testBoard.getPiece(row, col);
                if (piece != null && !piece.isWhite) {
                    if (piece instanceof Pawn && row == 2 && col == 4) {
                        isValidMove = true;
                    } else if (piece instanceof King && row == 0 && (col == 3 || col == 5)) {
                        isValidMove = true;
                    }
                }
            }
        }

        assertTrue(isValidMove, "AI should make a valid move");
    }
}
