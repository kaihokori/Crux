package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Archbishop chess piece.
 */
public class Archbishop extends Piece {

    /**
     * Constructor for an Archbishop piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Archbishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Archbishop piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        return new Bishop(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new Knight(row, col, isWhite).isMoveValid(destRow, destCol, board);
    }

    /**
     * Gets all valid moves for the Archbishop piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        validMoves.addAll(new Bishop(row, col, isWhite).getValidMoves(row, col, board));
        validMoves.addAll(new Knight(row, col, isWhite).getValidMoves(row, col, board));

        return validMoves;
    }

    /**
     * Gets the point value of the Archbishop piece.
     *
     * @return the point value of the piece (7.5)
     */
    public double getPieceValue() {
        return 7.5;
    }    
}
