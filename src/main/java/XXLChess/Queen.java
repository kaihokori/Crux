package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Queen chess piece.
 */
public class Queen extends Piece {

    /**
     * Constructor for an Queen piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Queen(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Queen piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        // A queen's move is valid if it is a valid move for either a bishop or a rook
        return new Bishop(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new Rook(row, col, isWhite).isMoveValid(destRow, destCol, board);
    }

    /**
     * Gets all valid moves for the Queen piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Get valid moves for a bishop
        validMoves.addAll(new Bishop(row, col, isWhite).getValidMoves(row, col, board));
        // Get valid moves for a rook
        validMoves.addAll(new Rook(row, col, isWhite).getValidMoves(row, col, board));

        return validMoves;
    }

    /**
     * Gets the point value of the Queen piece.
     *
     * @return the point value of the piece (9.5)
     */
    public double getPieceValue() {
        return 9.5;
    }   
}
