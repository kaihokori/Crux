package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Knight chess piece.
 */
public class Knight extends Piece {

    /**
     * Constructor for an Knight piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Knight piece is valid.
     *
     * @param destRow the row position of the destination square
     * @param destCol the column position of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isMoveValid(int destRow, int destCol, Board board) {
        int rowDiff = Math.abs(destRow - row);
        int colDiff = Math.abs(destCol - col);

        // Check if the destination square is occupied by a piece of the same color
        Piece destPiece = board.getPiece(destRow, destCol);
        if (destPiece != null && destPiece.isWhite == isWhite) {
            return false;
        }

        // Knight moves in an L-shape (two squares in one direction and one square in the other)
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return true;
        }

        return false;
    }

    /**
     * Gets all valid moves for the Knight piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate all possible L-shaped moves for the knight
        int[][] offsets = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (board.isInsideBoard(newRow, newCol)) {
                Piece targetPiece = board.getPiece(newRow, newCol);
                if (targetPiece == null || targetPiece.isWhite != isWhite) {
                    validMoves.add(new int[]{newRow, newCol});
                }
            }
        }

        return validMoves;
    }

    /**
     * Gets the point value of the Knight piece.
     *
     * @return the point value of the piece (2.0)
     */
    public double getPieceValue() {
        return 2.0;
    }    
}