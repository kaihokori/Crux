package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Rook chess piece.
 */
public class Rook extends Piece {

    /**
     * Constructor for an Rook piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Rook piece is valid.
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

        // Rook can move only in a straight line (row or column)
        if (rowDiff == 0 || colDiff == 0) {
            // Check for pieces in the path
            int rowStep = (destRow - row) == 0 ? 0 : (destRow - row) / rowDiff;
            int colStep = (destCol - col) == 0 ? 0 : (destCol - col) / colDiff;

            for (int i = 1; i < Math.max(rowDiff, colDiff); i++) {
                if (board.getPiece(row + i * rowStep, col + i * colStep) != null) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Gets all valid moves for the Rook piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate all possible moves in horizontal and vertical directions
        for (int[] direction : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            while (board.isInsideBoard(newRow, newCol)) {
                Piece targetPiece = board.getPiece(newRow, newCol);
                // If the current square is empty, it is a valid move
                if (targetPiece == null) {
                    validMoves.add(new int[]{newRow, newCol});
                } else {
                    if (targetPiece.isWhite != isWhite) {
                        validMoves.add(new int[]{newRow, newCol});
                    }
                    break;
                }

                // Move to the next square in the current direction
                newRow += direction[0];
                newCol += direction[1];
            }
        }

        return validMoves;
    }

    /**
     * Gets the point value of the Rook piece.
     *
     * @return the point value of the piece (5.25)
     */
    public double getPieceValue() {
        return 5.25;
    }    
}