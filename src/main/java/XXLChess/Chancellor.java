package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Chancellor chess piece.
 */
public class Chancellor extends Piece {
    
    /**
     * Constructor for an Archbishop piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Chancellor(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Chancellor piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        // A chancellor can move like a knight or a rook
        return new Knight(row, col, isWhite).isMoveValid(destRow, destCol, board) ||
               new Rook(row, col, isWhite).isMoveValid(destRow, destCol, board);
    }

    /**
     * Gets all valid moves for the Chancellor piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // A chancellor can move like a knight or a rook
        validMoves.addAll(new Knight(row, col, isWhite).getValidMoves(row, col, board));
        validMoves.addAll(new Rook(row, col, isWhite).getValidMoves(row, col, board));

        return validMoves;
    }

    /**
     * Gets the point value of the Chancellor piece.
     *
     * @return the point value of the piece (8.5)
     */
    public double getPieceValue() {
        return 8.5;
    }    
}