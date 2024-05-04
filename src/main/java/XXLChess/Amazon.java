package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Amazon chess piece.
 */
public class Amazon extends Piece {
    
    /**
     * Constructor for an Amazon piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Amazon(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Amazon piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        // Amazon moves are valid if they follow the moves of a Bishop, Rook, or Knight piece
        return new Bishop(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new Rook(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new Knight(row, col, isWhite).isMoveValid(destRow, destCol, board);
    }

    /**
     * Gets all valid moves for the Amazon piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        // Initialize an empty list to store valid moves
        List<int[]> validMoves = new ArrayList<>();

        // Get all valid moves for a Bishop, Rook, or Knight piece and add them to the validMoves list
        validMoves.addAll(new Bishop(row, col, isWhite).getValidMoves(row, col, board));
        validMoves.addAll(new Rook(row, col, isWhite).getValidMoves(row, col, board));
        validMoves.addAll(new Knight(row, col, isWhite).getValidMoves(row, col, board));

        // Return the list of valid moves
        return validMoves;
    }

    /**
     * Gets the point value of the Amazon piece.
     *
     * @return the point value of the piece (12.0)
     */
    public double getPieceValue() {
        return 12.0;
    }    
}
