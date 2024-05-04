package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Bishop chess piece.
 */
public class Bishop extends Piece {

    /**
     * Constructor for an Bishop piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Bishop piece is valid.
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

        // Bishop can move diagonally only
        if (rowDiff == colDiff) {
            // Check if there are no pieces blocking the path
            int rowDirection = destRow > row ? 1 : -1;
            int colDirection = destCol > col ? 1 : -1;

            int currentRow = row + rowDirection;
            int currentCol = col + colDirection;

            while (currentRow != destRow && currentCol != destCol) {
                if (board.getPiece(currentRow, currentCol) != null) {
                    return false;
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }

            return true;
        }

        return false;
    }

    /**
     * Gets all valid moves for the Bishop piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate all possible diagonal moves
        int[][] directions = {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            while (board.isInsideBoard(newRow, newCol)) {
                Piece targetPiece = board.getPiece(newRow, newCol);
                if (targetPiece == null) {
                    validMoves.add(new int[]{newRow, newCol});
                } else {
                    if (targetPiece.isWhite != isWhite) {
                        validMoves.add(new int[]{newRow, newCol});
                    }
                    break; // Stop checking in this direction if there is a piece
                }

                newRow += direction[0];
                newCol += direction[1];
            }
        }

        return validMoves;
    }

    /**
     * Gets the point value of the Bishop piece.
     *
     * @return the point value of the piece (3.625)
     */
    public double getPieceValue() {
        return 3.625;
    }    
}
