package XXLChess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    // Board variables
    private final Piece[][] board;
    public static final int SIZE = 14;

    /**
     * Constructs a new Board object with an empty grid of size SIZE by SIZE.
     */
    public Board() {
        board = new Piece[SIZE][SIZE];
    }

    /**
     * Clears the board by setting all pieces to null.
     */
    public void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                setPiece(row, col, null);
            }
        }
    }    

    /**
     * Returns the piece at the specified row and column.
     *
     * @param row the row of the piece to retrieve (0-indexed)
     * @param col the column of the piece to retrieve (0-indexed)
     * @return the piece at the specified row and column, or null if the coordinates are out of bounds
     */
    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return null;
        }
        return board[row][col];
    }

    /**
     * Sets a piece on the board at the specified row and column.
     * 
     * @param row the row where the piece will be set
     * @param col the column where the piece will be set
     * @param piece the piece to set on the board
     */
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    /**
     * Loads a game board from a text file.
     *
     * @param fileName the name of the file to load the board from
     * @throws IOException if there is an error reading from the file
     */
    public void loadBoardFromTextFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Loop through each row on the board
            for (int row = 0; row < Board.SIZE; row++) {
                // Read the next line from the file
                String line = br.readLine();
                // If there's no more lines, break out of the loop
                if (line == null) {
                    break;
                }
                // Loop through each column in the current row
                for (int col = 0; col < Board.SIZE && col < line.length(); col++) {
                    // Get the character representing the piece at this position
                    char pieceChar = line.charAt(col);
                    // Create a Piece object based on the character and its position
                    Piece piece = createPiece(pieceChar, row, col);
                    // Set the Piece object at this position on the board
                    setPiece(row, col, piece);
                }
            }
        } catch (IOException e) {
            // If there's an error reading from the file, print the stack trace
            e.printStackTrace();
        }
    }

    /**
     * Creates a Piece object based on the given piece character, row, and column.
     *
     * @param pieceChar the character representing the type and color of the piece (e.g. 'P' for white pawn, 'p' for black pawn)
     * @param row the row of the piece on the board (0-indexed)
     * @param col the column of the piece on the board (0-indexed)
     * @return a Piece object representing the specified piece, or null if the piece character is not recognized
     */
    public Piece createPiece(char pieceChar, int row, int col) {
        // Determine whether the piece is white or black based on the piece character
        boolean isWhite = Character.isLowerCase(pieceChar);

        // Convert the piece character to uppercase to simplify the switch statement
        char pieceType = Character.toUpperCase(pieceChar);

        // Create the appropriate Piece object based on the piece type
        switch (pieceType) {
            case 'P':
                return new Pawn(row, col, isWhite);
            case 'R':
                return new Rook(row, col, isWhite);
            case 'N':
                return new Knight(row, col, isWhite);
            case 'B':
                return new Bishop(row, col, isWhite);
            case 'H':
                return new Archbishop(row, col, isWhite);
            case 'C':
                return new Camel(row, col, isWhite);
            case 'G':
                return new Guard(row, col, isWhite);
            case 'A':
                return new Amazon(row, col, isWhite);
            case 'K':
                return new King(row, col, isWhite);
            case 'E':
                return new Chancellor(row, col, isWhite);
            case 'Q':
                return new Queen(row, col, isWhite);
            default:
                // If the piece character is not recognized, return null
                return null;
        }
    }

    /**
     * Moves a piece from the source square to the destination square, updating the game board and returning any captured piece.
     *
     * @param srcRow the row index of the source square
     * @param srcCol the column index of the source square
     * @param destRow the row index of the destination square
     * @param destCol the column index of the destination square
     * @return the captured piece (if any)
     */
    public Piece movePiece(int srcRow, int srcCol, int destRow, int destCol) {
        Piece piece = getPiece(srcRow, srcCol);
    
        // Remove the piece from the source square
        setPiece(srcRow, srcCol, null);
    
        // Store the captured piece if any
        Piece capturedPiece = getPiece(destRow, destCol);
    
        // Check for pawn promotion
        if (piece instanceof Pawn && (destRow == 0 || destRow == Board.SIZE / 2)) {
            piece = new Queen(destRow, destCol, piece.isWhite);
        }
    
        // Place the piece on the destination square
        setPiece(destRow, destCol, piece);
    
        // Update the piece's internal position and set the hasMoved property
        piece.updatePiecePosition(destRow, destCol);
        piece.setHasMoved(true);
    
        // Return the captured piece (if any)
        return capturedPiece;
    }    

    /**
     * Determines whether a given row and column are within the bounds of the game board.
     *
     * @param row the row to check
     * @param col the column to check
     * @return {@code true} if the given row and column are inside the board, {@code false} otherwise
     */
    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }    

    /**
     * Determines if a move is valid based on the source and destination coordinates and the current board state.
     * @param srcRow the row of the source coordinate
     * @param srcCol the column of the source coordinate
     * @param destRow the row of the destination coordinate
     * @param destCol the column of the destination coordinate
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(int srcRow, int srcCol, int destRow, int destCol) {
        Piece piece = getPiece(srcRow, srcCol);
        if (piece == null) {
            return false;
        }
        return piece.isMoveValid(destRow, destCol, this);
    }    

    /**
     * Determines if a king is in check.
     *
     * @param kingRow the row of the king on the board
     * @param kingCol the column of the king on the board
     * @param isWhite the color of the king
     * @return true if the king is in check, false otherwise
     */
    public boolean isKingInCheck(int kingRow, int kingCol, boolean isWhite) {
        // Iterate over all pieces on the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                // If the piece is not null and is of the opposite color of the king
                if (piece != null && piece.isWhite != isWhite) {
                    // Check if the piece is capable of capturing the king
                    if (piece.isMoveValid(kingRow, kingCol, this)) {
                        return true;
                    }
                }
            }
        }
        // If no piece is capable of capturing the king, return false
        return false;
    }

    /**
     * Checks if the player of the given color is in checkmate.
     *
     * @param isWhite the color of the player to check
     * @return true if the player is in checkmate, false otherwise
     */
    public boolean isCheckmate(boolean isWhite) {
        // Find the king's position
        int kingRow = -1;
        int kingCol = -1;
        // Iterate over all pieces on the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // Check if there is a piece at this position and if it matches the king's color
                Piece piece = getPiece(row, col);
                if (piece != null && piece.isWhite == isWhite && piece instanceof King) {
                    // If the piece is a king of the correct color, record its position and exit the loop
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
            // If king's position is found, exit the outer loop too
            if (kingRow != -1) {
                break;
            }
        }
    
        // Check if the king is in check
        if (!isKingInCheck(kingRow, kingCol, isWhite)) {
            return false;
        }
    
        // Check if there are any valid moves for the player that would get the king out of check
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null && piece.isWhite == isWhite) {
                    List<int[]> validMoves = piece.getValidMoves(row, col, this);
                    for (int[] move : validMoves) {
                        // Temporarily move the piece
                        Piece capturedPiece = movePiece(row, col, move[0], move[1]);
    
                        // Check if the king is still in check
                        boolean stillInCheck = isKingInCheck(kingRow, kingCol, isWhite);
    
                        // Undo the move
                        movePiece(move[0], move[1], row, col);
                        setPiece(move[0], move[1], capturedPiece);
    
                        // If the king is not in check after the move, it's not checkmate
                        if (!stillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }
    
        // If no valid moves are found that get the king out of check, it's checkmate
        return true;
    }     

    /**
     * Calculates the score of the current game board for a given player.
     *
     * @param isWhite true if the player is white; false if the player is black
     * @return the score of the game board for the given player
     */
    public double evaluateBoard(boolean isWhite) {
        double score = 0.0;
    
        // Iterate over all pieces on the board
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null) {
                    // Get the value of the piece according to its type
                    double pieceValue = piece.getPieceValue();
                    // If the piece belongs to the player we're evaluating for, add its value to the score; otherwise, subtract it
                    if (piece.isWhite == isWhite) {
                        score += pieceValue;
                    } else {
                        score -= pieceValue;
                    }
                }
            }
        }
    
        return score;
    }    

    /**
     * Retrieves a list of all valid moves for a given color on the current board.
     * 
     * @param isWhite a boolean indicating which color's moves to retrieve
     * @return a List of int arrays representing each valid move, in the format [startRow, startCol, endRow, endCol]
     */
    public List<int[]> getAllMovesForColor(boolean isWhite) {
        List<int[]> allMoves = new ArrayList<>();
    
        // Iterate over all pieces on the board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                // if the piece is not null and matches the desired color, get its valid moves
                if (piece != null && piece.isWhite == isWhite) {
                    List<int[]> validMoves = piece.getValidMoves(row, col, this);
                    // add each valid move to the list of all moves
                    for (int[] move : validMoves) {
                        allMoves.add(new int[]{row, col, move[0], move[1]});
                    }
                }
            }
        }
    
        return allMoves;
    }
}