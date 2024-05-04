package XXLChess;

import java.util.List;

public abstract class Piece {
    protected int row;
    protected int col;
    protected boolean isWhite;
    protected boolean hasMoved;

    /**
     * Constructs a new Piece object with the given row, column, and color.
     *
     * @param row the row of the piece on the game board
     * @param col the column of the piece on the game board
     * @param isWhite the color of the piece, true if white, false if black
     */
    public Piece(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    /**
     * Checks if a move to the specified destination row and column is valid for the piece on the specified row and column.
     *
     * @param destRow the row of the destination square
     * @param destCol the column of the destination square
     * @param board the game board
     * @return true if the move is valid, false otherwise
     */
    public abstract boolean isMoveValid(int destRow, int destCol, Board board);

    /**
     * Returns a list of valid moves for the piece on the specified row and column.
     *
     * @param row the row of the piece
     * @param col the column of the piece
     * @param board the game board
     * @return a list of int arrays representing valid moves, where each int array contains the destination row and column
     */
    public abstract List<int[]> getValidMoves(int row, int col, Board board);

    /**
     * Updates the position of the game piece to the specified row and column.
     * @param newRow The new row for the game piece.
     * @param newCol The new column for the game piece.
     */
    public void updatePiecePosition(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    /**
     * Returns whether the game piece has moved.
     * @return true if the game piece has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets whether the game piece has moved.
     * @param hasMoved true if the game piece has moved, false otherwise.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Returns the value of the game piece.
     * @return The value of the game piece.
     */
    public abstract double getPieceValue();
}