package XXLChess;

import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    public App() {
        
    }
    public static final int CELL_SIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;
    public static final int FPS = 60;

    public boolean gameStarted = false;
    public boolean gameEnded = false;
    public boolean resigned = false;
	
    // Board variables
    Board board;
    PImage[][] pieceImages;
    boolean whiteTurn;

    // Selected piece variables
    public Piece selectedPiece;
    public int selectedRow;
    public int selectedCol;

    // Timer variables
    private static final int TIME_LIMIT = 3 * 60 * 1000; // 3 minutes in milliseconds
    public long whiteTimeLeft = TIME_LIMIT;
    public long blackTimeLeft = TIME_LIMIT;
    private long lastTimeMillis;

    // Start Button variables
    public int startButtonX = BOARD_WIDTH * CELL_SIZE + 10;
    public int startButtonY = 10;
    private int startButtonWidth = SIDEBAR - 20;
    private int startButtonHeight = 30;

    // Depth Button variables
    public int depthButtonX = BOARD_WIDTH * CELL_SIZE + 10;
    public int depthButtonY = BOARD_WIDTH * CELL_SIZE - 80;
    private int depthButtonWidth = SIDEBAR - 20;
    private int depthButtonHeight = 30;

    // Mode Button variables
    public int modeButtonX = BOARD_WIDTH * CELL_SIZE + 10;
    public int modeButtonY = BOARD_WIDTH * CELL_SIZE - 40;
    private int modeButtonWidth = SIDEBAR - 20;
    private int modeButtonHeight = 30;

    // Valid moves
    private List<int[]> validMoves = null;

    // AI variables
    public boolean ai = true;
    public int depth = 3;

    /**
     * Overrides the settings function to set the size of the game window to the
     * product of BOARD_WIDTH and CELL_SIZE plus SIDEBAR. Also sets lastTimeMillis
     * to the current system time in milliseconds.
     */
    @Override
    public void settings() {
        // Decalring window size
        size(BOARD_WIDTH * CELL_SIZE + SIDEBAR, BOARD_WIDTH * CELL_SIZE);
        lastTimeMillis = System.currentTimeMillis();
    }
    /**
     * Initializes the game board, sets the frame rate, and loads the board and
     * piece images from files.
     */
    public void setup() {
        frameRate(FPS);
        
        // Initializing board
        board = new Board();
        whiteTurn = true;

        // Loading board from text file
        board.loadBoardFromTextFile("src/main/level.txt");

        // Loading piece images
        loadPieceImages();
    }

    /**
     * Loads piece images for each color and piece in the XXLChess game.
     * Uses the file path "src/main/resources/XXLChess/{color}-{piece}.png".
     */
    public void loadPieceImages() {
        String[] pieceNames = {"pawn", "rook", "knight", "bishop", "archbishop", "camel", "guard", "amazon", "king", "chancellor", "queen"};
        String[] colors = {"w", "b"};
        pieceImages = new PImage[colors.length][pieceNames.length];

        // Loading piece images for each color and piece
        for (int colorIndex = 0; colorIndex < colors.length; colorIndex++) {
            for (int pieceIndex = 0; pieceIndex < pieceNames.length; pieceIndex++) {
                pieceImages[colorIndex][pieceIndex] = loadImage("src/main/resources/XXLChess/" + colors[colorIndex] + "-" + pieceNames[pieceIndex] + ".png");
            }
        }
    }

    /**
     * Handles key presses for game controls.
     * 
     * @param  None
     * @return None
     */
    public void keyPressed(){
        if (key == 'r' || key == 'R') {
            // Restart game and resetting necessary variables
            System.out.println("Restarting Game (R Key pressed)");
            background(255);
            board = new Board();
            whiteTurn = true;
            board.loadBoardFromTextFile("src/main/level.txt");
            loadPieceImages();
            text("", BOARD_WIDTH * CELL_SIZE + SIDEBAR / 2, BOARD_WIDTH * CELL_SIZE / 2);
            whiteTimeLeft = TIME_LIMIT;
            blackTimeLeft = TIME_LIMIT;
            gameStarted = false;
            gameEnded = false;
            resigned = false;
            ai = true;
            depth = 3;
            redraw();
        } else if (key == 'e' || key == 'E') {
            // Resigning game and displaying resignation text
            System.out.println("Resigning Game (E Key pressed)");
            if (gameStarted) {
                drawResignation();
            }
        }
    }

    /**
     * This function handles mouse press events. It checks if the game has ended and if not, handles
     * clicks on the start button, depth button, and mode button. If the game has started, it checks
     * if the click is valid and makes the move if it is. It also updates the valid moves for the
     * selected piece if applicable.
     *
     * @return void
     */
    @Override
    public void mousePressed() {
        if (!gameEnded) {
            if (!gameStarted) {
                // Mouse clicked on start button
                if (mouseX > startButtonX && mouseX < startButtonX + startButtonWidth &&
                    mouseY > startButtonY && mouseY < startButtonY + startButtonHeight) {
                    
                    // Starting game and timers
                    gameStarted = true;
                    lastTimeMillis = System.currentTimeMillis();
                }

                // Mouse clicked on depth button
                if (mouseX > depthButtonX && mouseX < depthButtonX + depthButtonWidth &&
                    mouseY > depthButtonY && mouseY < depthButtonY + depthButtonHeight) {
                    // Changing depth from 1 to 5 (step 1)
                    if (depth == 3) {
                        depth = 4;
                    } else if (depth == 4) {
                        depth = 5;
                    } else if (depth == 5) {
                        depth = 1;
                    } else if (depth == 1) {
                        depth = 2;
                    } else {
                        depth = 3;
                    }
                }

                // Mouse clicked on mode button
                if (mouseX > modeButtonX && mouseX < modeButtonX + modeButtonWidth &&
                    mouseY > modeButtonY && mouseY < modeButtonY + modeButtonHeight) {
                    // Toggling AI
                    ai = !ai;
                }
            } else {
                // Checking if mouse clicked on board
                int row = mouseY / CELL_SIZE;
                int col = mouseX / CELL_SIZE;
                if (row >= 0 && row < Board.SIZE && col >= 0 && col < Board.SIZE) {
                    // Retrieving piece at mouse position
                    Piece piece = board.getPiece(row, col);

                    if (selectedPiece == null) {
                        // No piece is selected yet
                        if (piece != null && piece.isWhite == whiteTurn) {
                            selectedPiece = piece;
                            selectedRow = row;
                            selectedCol = col;
                        }
                    } else {
                        // A piece is already selected
                        if (selectedPiece.isMoveValid(row, col, board)) {
                            System.out.println("Valid move: " + selectedPiece.getClass().getSimpleName() + " from (" + selectedRow + ", " + selectedCol + ") to (" + row + ", " + col + ")");
                            board.movePiece(selectedRow, selectedCol, row, col);
                            whiteTurn = !whiteTurn; // Switch turns
                            selectedPiece = null; // Reset selected piece
                        } else if (piece != null && piece.isWhite == whiteTurn) {
                            // The user clicked on another piece of their color; change the selection
                            System.out.println("Changing selection to: " + piece.getClass().getSimpleName() + " at (" + row + ", " + col + ")");
                            selectedPiece = piece;
                            selectedRow = row;
                            selectedCol = col;
                        } else {
                            // Invalidate the selected piece to return it to its original position
                            System.out.println("Invalid move for " + selectedPiece.getClass().getSimpleName() + " from (" + selectedRow + ", " + selectedCol + ") to (" + row + ", " + col + ")");
                            selectedPiece = null;
                        }
                    }
                    
                    // Updating valid moves
                    if (selectedPiece != null) {
                        validMoves = selectedPiece.getValidMoves(selectedRow, selectedCol, board);
                    } else {
                        validMoves = null;
                    }
                }
            }
        }    
    }

    /**
     * Draws the game board and pieces, controls timers, and runs the AI.
     */
    @Override
    public void draw() {
        if (!resigned) {
            // Drawing grid and pieces
            background(255);
            drawGrid();
            drawPieces();

            // Checking if timer has reached 0
            checkTimers();
            
            // Controlling timer logic
            if (gameStarted) {
                if (!gameEnded) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (whiteTurn) {
                        whiteTimeLeft -= currentTimeMillis - lastTimeMillis;
                    } else {
                        blackTimeLeft -= currentTimeMillis - lastTimeMillis;
                    }
                    lastTimeMillis = currentTimeMillis;
                }
            }

            // Drawing timers, start, depth, and mode buttons
            drawTimers();
            drawStartButton();
            drawDepthButton();
            drawModeButton();

            // Drawing winner if game has ended or if the king is captured
            if (gameEnded || isKingCaptured()) {
                drawWinner();
            }

            // Drawing highlights for when moving pieces
            drawHighlights();

            // Running AI if enabled (from button) and if it's not white's turn
            if (ai && !whiteTurn) {
                runAI();
            }
        }
    }

    /**
     * Draws a grid on the screen.
     * The grid has a width of BOARD_WIDTH and each cell has a size of CELL_SIZE.
     * The colors of the cells alternate between two shades.
     */
    private void drawGrid() {
        noStroke();
        // Looping through each row and column
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                // Alternating color
                if ((i + j) % 2 == 0) {
                    fill(181, 136, 99);
                } else {
                    fill(240, 217, 181);
                }
                rect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Draws all the pieces on the board and the selected piece at the mouse position if there is one.
     */
    private void drawPieces() {
        // Looping through each row and column
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Piece piece = board.getPiece(row, col);
                // If a piece is selected and is not captured
                if (piece != null && piece != selectedPiece) {
                    int colorIndex = piece.isWhite ? 0 : 1;
                    int pieceIndex = getPieceIndex(piece);
                    if (pieceIndex >= 0) {
                        // Loading piece image
                        image(pieceImages[colorIndex][pieceIndex], col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
        
        // Drawing selected piece at mouse position
        if (selectedPiece != null) {
            int colorIndex = selectedPiece.isWhite ? 0 : 1;
            int pieceIndex = getPieceIndex(selectedPiece);
            if (pieceIndex >= 0) {
                // Loading selected piece image
                image(pieceImages[colorIndex][pieceIndex], mouseX - CELL_SIZE / 2, mouseY - CELL_SIZE / 2, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Draws highlights for valid moves and the selected piece on the board.
     */
    private void drawHighlights() {
        // Checking if piece isn't captured
        if (selectedPiece != null) {
            for (int[] move : validMoves) {
                int row = move[0];
                int col = move[1];
    
                Piece targetPiece = board.getPiece(row, col);
                // Drawing highlights for potential movement, king, or capture
                if (targetPiece == null) {
                    fill(100, 100, 255, 100); // Blue for empty square
                } else if (targetPiece instanceof King) {
                    fill(255, 0, 0, 100); // Red for king
                } else {
                    fill(255, 100, 100, 100); // Light red for other pieces
                }
    
                rect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
    
            // Drawing highlight for the selected piece
            fill(100, 255, 100, 100);
            rect(selectedCol * CELL_SIZE, selectedRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    /**
     * Draws the timers for the game.
     */
    private void drawTimers() {
        // Only draw the timers if the game has started.
        if (gameStarted) {
            // Set the text alignment and size.
            textAlign(CENTER, CENTER);
            textSize(20);

            // Set the text fill color to black.
            fill(0);

            // Format the white player's time left as a string in the format "MM:SS".
            String whiteTimeText = String.format("%02d:%02d", whiteTimeLeft / 60000, (whiteTimeLeft % 60000) / 1000);

            // Draw the white player's time left text in the center of the top half of the sidebar.
            text(whiteTimeText, BOARD_WIDTH * CELL_SIZE + SIDEBAR / 2, 3 * BOARD_WIDTH * CELL_SIZE / 4);

            // Only draw the black player's time left if the AI is not enabled.
            if (!ai) {
                // Format the black player's time left as a string in the format "MM:SS".
                String blackTimeText = String.format("%02d:%02d", blackTimeLeft / 60000, (blackTimeLeft % 60000) / 1000);

                // Draw the black player's time left text in the center of the bottom half of the sidebar.
                text(blackTimeText, BOARD_WIDTH * CELL_SIZE + SIDEBAR / 2, BOARD_WIDTH * CELL_SIZE / 4);
            }
        }
    }

    /**
     * Draws the start button if the game has not yet started.
     */
    private void drawStartButton() {
        if (!gameStarted) { 
            fill(0);
            rect(startButtonX, startButtonY, startButtonWidth, startButtonHeight);
            fill(255);
            textSize(16);
            textAlign(CENTER, CENTER);
            text("Start", startButtonX + startButtonWidth / 2, startButtonY + startButtonHeight / 2);
        }
    }

    /**
     * Draws the depth button if the game has not yet started
     */
    private void drawDepthButton() {
        if (!gameStarted) {
            fill(0); 
            rect(depthButtonX, depthButtonY, depthButtonWidth, depthButtonHeight); 
            fill(255); 
            textSize(16); 
            textAlign(CENTER, CENTER); 
            text("Depth: " + depth, depthButtonX + depthButtonWidth / 2, depthButtonY + depthButtonHeight / 2);
        }
    }

    /**
     * Draws the mode button on the screen, indicating whether the game is in AI mode or not.
     * If the game has not started yet, the button will be displayed.
     */
    private void drawModeButton() {
        if (!gameStarted) {
            fill(0);
            rect(modeButtonX, modeButtonY, modeButtonWidth, modeButtonHeight);
            fill(255);
            textSize(16);
            textAlign(CENTER, CENTER);
            if (ai) {
                text("Mode: AI", modeButtonX + modeButtonWidth / 2, modeButtonY + modeButtonHeight / 2);
            } else {
                text("Mode: No AI", modeButtonX + modeButtonWidth / 2, modeButtonY + modeButtonHeight / 2);
            }
        }
    }

    /**
     * Draws the winner text on the screen.
     * The winner text is displayed at the center of the board and
     * indicates who has won the game or if the game ended in a tie.
     */
    private void drawWinner() {
        textAlign(CENTER, CENTER);
        textSize(20);
        fill(0);
    
        String winnerText;

        // Figuring out winner
        if (isKingCaptured()) {
            if (whiteTurn) {
                winnerText = "Black wins!";
            } else {
                winnerText = "White wins!";
            }
        } else if (whiteTimeLeft > 0) {
            winnerText = "White wins!";
        } else {
            winnerText = "Black wins!";
        }
    
        text(winnerText, BOARD_WIDTH * CELL_SIZE + SIDEBAR / 2, BOARD_WIDTH * CELL_SIZE / 2);

        gameEnded = true;
    }

    /**
     * Draws the "resignation" message on the screen and sets the gameEnded flag to true.
     * This method should be called when a player resigns the game.
     */
    private void drawResignation() {
        textAlign(CENTER, CENTER);
        textSize(20);
        fill(0);

        // Figuring out winner
        if (gameStarted && !gameEnded) {
            text("You\nresigned", BOARD_WIDTH * CELL_SIZE + SIDEBAR / 2, BOARD_WIDTH * CELL_SIZE / 2);
            gameEnded = true;
        }

        resigned = true;
    }

    /**
     * Returns the index of the given piece.
     *
     * @param piece The chess piece to get the index of.
     * @return The index of the piece, or -1 if the piece is not recognized.
     */
    public int getPieceIndex(Piece piece) {
        if (piece instanceof XXLChess.Pawn) {
            return 0;
        } else if (piece instanceof XXLChess.Rook) {
            return 1;
        } else if (piece instanceof XXLChess.Knight) {
            return 2;
        } else if (piece instanceof XXLChess.Bishop) {
            return 3;
        } else if (piece instanceof XXLChess.Archbishop) {
            return 4;
        } else if (piece instanceof XXLChess.Camel) {
            return 5;
        } else if (piece instanceof XXLChess.Guard) {
            return 6;
        } else if (piece instanceof XXLChess.Amazon) {
            return 7;
        } else if (piece instanceof XXLChess.King) {
            return 8;
        } else if (piece instanceof XXLChess.Chancellor) {
            return 9;
        } else if (piece instanceof XXLChess.Queen) {
            return 10;
        } else {
            return -1;
        }
    }

    /**
     * Checks if the game timer has run out for either player and ends the game if so.
     */
    public void checkTimers() {
        if (!gameEnded && (whiteTimeLeft <= 0 || blackTimeLeft <= 0)) {
            gameEnded = true;
        }
    }

    /**
     * Checks whether the white or black king has been captured.
     * 
     * @return true if the white king has been captured or if the black king has been captured, false otherwise
     */
    public boolean isKingCaptured() {
        boolean whiteKingCaptured = true;
        boolean blackKingCaptured = true;
    
        // Running through all rows and columns
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King) {
                    if (piece.isWhite) {
                        whiteKingCaptured = false;
                    } else {
                        blackKingCaptured = false;
                    }
    
                    if (!whiteKingCaptured && !blackKingCaptured) {
                        return false;
                    }
                }
            }
        }
    
        return whiteKingCaptured || blackKingCaptured;
    }

    /**
     * Runs the AI algorithm to determine the best move for the current player and updates the board accordingly.
     * Uses the MinMaxAB algorithm to find the best move.
     */
    public void runAI() {
        // Calling MinMaxAB algorithm
        MinMaxAI aiCheck = new MinMaxAI(board, depth, false);
        int[] bestMove = aiCheck.getBestMove();
        if (bestMove != null) {
            board.movePiece(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
            whiteTurn = !whiteTurn;
        }
    }

    /**
     * The main function that starts the application.
     */
    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
