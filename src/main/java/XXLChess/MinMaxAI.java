package XXLChess;

import java.util.List;

public class MinMaxAI {
    private Board board;
    private int depth;
    private boolean isWhite;

    /**
     * Creates a new MinMaxAI instance with the specified board, search depth, and player color.
     *
     * @param board the game board to use for evaluating moves
     * @param depth the maximum search depth to use when evaluating moves
     * @param isWhite true if this AI is playing as the white player, false if playing as black
     */
    public MinMaxAI(Board board, int depth, boolean isWhite) {
        this.board = board;
        this.depth = depth;
        this.isWhite = isWhite;
    }

    /**
     * Determines the best move for the current player by simulating all possible moves and evaluating them using the
     * min-max algorithm with alpha-beta pruning. The best move is returned as an array of size 4, where the first two
     * elements represent the starting position of the piece to move, and the last two elements represent the ending
     * position of the piece to move.
     *
     * @return the best move for the current player
     */
    public int[] getBestMove() {
        int[] bestMove = new int[4];
        double bestScore = Double.NEGATIVE_INFINITY;
        List<int[]> allMoves = board.getAllMovesForColor(isWhite);

        for (int[] move : allMoves) {
            // Simulate move
            Piece capturedPiece = board.movePiece(move[0], move[1], move[2], move[3]);

            // Calculate move score using min-max
            double moveScore = minValue(depth - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !isWhite);

            // Revert the move
            board.movePiece(move[2], move[3], move[0], move[1]);
            if (capturedPiece != null) {
                board.setPiece(move[2], move[3], capturedPiece);
            }

            // Update best move
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * Computes the maximum possible score of a given game state using the Min-Max algorithm.
     *
     * @param depth the maximum depth of the search tree to explore.
     * @param alpha the current best score for the maximizing player.
     * @param beta the current best score for the minimizing player.
     * @param isWhite {@code true} if the player to move is white; {@code false} otherwise.
     * @return the maximum score that can be achieved by the player to move.
     */
    public double maxValue(int depth, double alpha, double beta, boolean isWhite) {
        // Base case - end of game
        if (depth == 0 || board.isCheckmate(isWhite)) {
            return board.evaluateBoard(isWhite);
        }

        double maxScore = Double.NEGATIVE_INFINITY;
        List<int[]> allMoves = board.getAllMovesForColor(isWhite);

        for (int[] move : allMoves) {
            // Simulate move
            Piece capturedPiece = board.movePiece(move[0], move[1], move[2], move[3]);

            // Calculate move score using min-max
            double moveScore = minValue(depth - 1, alpha, beta, !isWhite);

            // Revert the move
            board.movePiece(move[2], move[3], move[0], move[1]);
            if (capturedPiece != null) {
                board.setPiece(move[2], move[3], capturedPiece);
            }

            maxScore = Math.max(maxScore, moveScore);
            alpha = Math.max(alpha, maxScore);

            if (alpha >= beta) {
                break;
            }
        }

        return maxScore;
    }

    /**
     * Finds the minimum score for the current board state using a min-max algorithm with alpha-beta pruning.
     * 
     * @param depth the current depth of the search tree
     * @param alpha the alpha value for alpha-beta pruning
     * @param beta the beta value for alpha-beta pruning
     * @param isWhite a boolean indicating whether it is white's turn to move
     * @return the minimum score for the current board state
     */
    private double minValue(int depth, double alpha, double beta, boolean isWhite) {
        // Base case - end of game
        if (depth == 0 || board.isCheckmate(isWhite)) {
            return board.evaluateBoard(!isWhite);
        }

        double minScore = Double.POSITIVE_INFINITY;
        List<int[]> allMoves = board.getAllMovesForColor(isWhite);

        for (int[] move : allMoves) {
            // Simulate move
            Piece capturedPiece = board.movePiece(move[0], move[1], move[2], move[3]);

            // Calculate move score using min-max
            double moveScore = maxValue(depth - 1, alpha, beta, !isWhite);

            // Revert the move
            board.movePiece(move[2], move[3], move[0], move[1]);
            if (capturedPiece != null) {
                board.setPiece(move[2], move[3], capturedPiece);
            }

            minScore = Math.min(minScore, moveScore);
            beta = Math.min(beta, minScore);

            if (alpha >= beta) {
                break;
            }
        }

        return minScore;
    }
}

