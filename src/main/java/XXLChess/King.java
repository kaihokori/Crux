package XXLChess;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an King chess piece.
 */
public class King extends Piece {

    /**
     * Constructor for an King piece.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param isWhite true if the piece is white, false if it is black
     */
    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    /**
     * Checks if a move for the King piece is valid.
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

        // King can move only one square in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            return true;
        }

        // Castling move
        if (rowDiff == 0 && colDiff == 2 && !hasMoved) {
            int rookCol = destCol > col ? (Board.SIZE - 1) : 0;
            Piece rook = board.getPiece(row, rookCol);
            if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                int colDirection = destCol > col ? 1 : -1;
                for (int c = col + colDirection; c != rookCol; c += colDirection) {
                    if (board.getPiece(row, c) != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Gets all valid moves for the King piece on the game board.
     *
     * @param row the row position of the piece on the board
     * @param col the column position of the piece on the board
     * @param board the game board
     * @return a list of int arrays representing the valid moves
     */
    @Override
    public List<int[]> getValidMoves(int row, int col, Board board) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate all possible moves in every direction (up to one square away)
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // Skip the current square
                }

                int newRow = row + rowOffset;
                int newCol = col + colOffset;

                if (board.isInsideBoard(newRow, newCol)) {
                    Piece targetPiece = board.getPiece(newRow, newCol);
                    if (targetPiece == null || targetPiece.isWhite != isWhite) {
                        validMoves.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        // Castling moves
        if (!hasMoved) {
            for (int rookCol : new int[]{0, Board.SIZE - 1}) {
                Piece rook = board.getPiece(row, rookCol);
                if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                    int colDirection = rookCol > col ? 1 : -1;
                    boolean canCastle = true;
                    for (int c = col + colDirection; c != rookCol; c += colDirection) {
                        if (board.getPiece(row, c) != null) {
                            canCastle = false;
                            break;
                        }
                    }
                    if (canCastle) {
                        validMoves.add(new int[]{row, col + 2 * colDirection});
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Gets the point value of the King piece.
     *
     * @return the point value of the piece (99999.0)
     */
    public double getPieceValue() {
        return 99999.0;
    }    
}
