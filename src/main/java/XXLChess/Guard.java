package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Guard chess piece.
 */
public class Guard extends Piece {
    
    /**
     * Constructor for an Guard piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Guard(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Guard piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        // A guard can move like a knight or a king
        return new Knight(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new King(row, col, isWhite).isMoveValid(destRow, destCol, board);
    }

    /**
     * Gets all valid moves for the Guard piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // A guard can move like a knight or a king
        validMoves.addAll(new Knight(row, col, isWhite).getValidMoves(row, col, board));
        validMoves.addAll(new King(row, col, isWhite).getValidMoves(row, col, board));

        return validMoves;
    }

    /**
     * Gets the point value of the Guard piece.
     *
     * @return the point value of the piece (5.0)
     */
    public double getPieceValue() {
        return 5.0;
    }    
}
