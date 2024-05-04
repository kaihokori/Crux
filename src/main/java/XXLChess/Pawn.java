package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Pawn chess piece.
 */
public class Pawn extends Piece {

    /**
     * Constructor for an Pawn piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the Pawn piece is valid.
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

        // Handle the first move of the pawn, allowing it to move two squares forward
        int initialRow = isWhite ? 12 : 1;
        boolean isFirstMove = (row == initialRow);
        int forwardDirection = isWhite ? -1 : 1;

        // Check if the pawn is moving forward
        if (col == destCol) {
            if (rowDiff == 1 && board.getPiece(destRow, destCol) == null) {
                return true;
            }
            if (isFirstMove && rowDiff == 2 && board.getPiece(destRow, destCol) == null &&
                    board.getPiece(row + forwardDirection, col) == null) {
                return true;
            }
        }

        // Check if the pawn is capturing a piece diagonally
        if (rowDiff == 1 && colDiff == 1 && destPiece != null && destPiece.isWhite != isWhite) {
            return true;
        }

        // Prevent moving forward diagonally without capturing a piece
        if (rowDiff == 1 && colDiff == 1 && destPiece == null) {
            return false;
        }

        return false;
    }

    /**
     * Gets all valid moves for the Pawn piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        int direction = isWhite ? -1 : 1;
        int newRow = row + direction;

        // Regular move
        if (board.isInsideBoard(newRow, col) && board.getPiece(newRow, col) == null) {
            validMoves.add(new int[]{newRow, col});
        }

        // Capture move
        for (int newCol : new int[]{col - 1, col + 1}) {
            if (board.isInsideBoard(newRow, newCol)) {
                Piece targetPiece = board.getPiece(newRow, newCol);
                if (targetPiece != null && targetPiece.isWhite != isWhite) {
                    validMoves.add(new int[]{newRow, newCol});
                }
            }
        }

        // First move: two squares forward
        int initialRow = isWhite ? 12 : 1;
        if (row == initialRow && board.getPiece(newRow, col) == null) {
            newRow += direction;
            if (board.isInsideBoard(newRow, col) && board.getPiece(newRow, col) == null) {
                validMoves.add(new int[]{newRow, col});
            }
        }

        return validMoves;
    }

    /**
     * Gets the point value of the Pawn piece.
     *
     * @return the point value of the piece (1.0)
     */
    public double getPieceValue() {
        return 1.0;
    }    
}
