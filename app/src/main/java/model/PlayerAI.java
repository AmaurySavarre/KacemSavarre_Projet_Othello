package model;

import java.util.List;
import java.util.Random;

/**
 * Created by Amaury Savarre on 11/03/2016.
 *
 * Class to create and manage a PlayerAI.
 */
public class PlayerAI extends Player
{
    private int _depth; // Depth of the research algorithm of the AI.

    /**
     * PlayerAI Constructor.
     *
     * @param othello The game on which the player plays.
     * @param number The number of the player.
     * @param maxDepth Max depth of the research algorithm.
     */
    public PlayerAI(Othello othello,int number, int maxDepth)
    {
        super(othello, number);
        _depth = maxDepth;
    }

    /**
     * Tells the player to play.
     */
    public void play()
    {
        int bestValue = Integer.MIN_VALUE;                      // The best value found.
        Move bestMove = new Move(0, 0);                         // The best move found.
        Board boardSave = new Board(_othello.getBoardSize());   // A save of the board.
        Othello othello = new Othello(_othello);                // A copy of the game on which the player player.
        List<Move> listMoves = _othello.getListMoves(this);     // The list of move available to the player.
        Random rand = new Random();                             // Random generator.

        // For each move in the list of moves
        for (Move move : listMoves)
        {
            // Save the state of the board.
            boardSave.copy(othello.getBoard());
            // Plays the actual move.
            othello.playAt(this, move.getX(), move.getY());
            // Evaluate the move with an alpha-beta.
            int moveValue = alphaBeta(othello, Integer.MIN_VALUE, Integer.MAX_VALUE, _depth, _othello.getOpponent(this));
            // Put back the game board at the initial state.
            othello.setBoard(boardSave);

            // If the current move is the best.
            if (moveValue >= bestValue && moveValue != Integer.MIN_VALUE)
            {
                // Update the best move.
                if(rand.nextInt(100)>50)
                {
                    bestValue = moveValue;
                    bestMove = move;
                }
            }
            else
            {
                bestMove = move;
            }
        }

        // Play the best move found on the board.
        _othello.playAt(this, bestMove.getX(), bestMove.getY());
    }

    public Move random(List<Move> listMoves)
    {
        Random rand = new Random();

        return listMoves.get(rand.nextInt(listMoves.size()));
    }

    /**
     * Alpha-Beta algorithm to find the best value of a move.
     *
     * @param othello The game on which the moves are played.
     * @param alpha The alpha value.
     * @param beta  The beta value.
     * @param maxDepth The maximum depth of the search.
     * @param player The player who plays the next move.
     * @return The value of a move.
     */
    public int alphaBeta(Othello othello, int alpha, int beta, int maxDepth, Player player)
    {
        // Save the state of the board before the player plays.
        Board boardSave = new Board(othello.getBoardSize());
        int val;    // The value the of the move.

        // If we are at the end of the search either by reaching the max depth or a game over.
        if (maxDepth == 0 || othello.gameOver())
        {
            // We evaluate the board for the player and return it.
            return othello.evaluateBoard(this);
        }

        // If it is the the AI to play (max)
        if (player == this)
        {
            // Initialise the move to the minimum.
            val = Integer.MIN_VALUE;

            // Get the list of move on the current game state.
            List<Move> listMoves = othello.getListMoves(player);
            // For each move.
            for (Move move : listMoves)
            {
                // Save the current game state.
                boardSave.copy(othello.getBoard());
                // Play the current move on the game.
                othello.playAt(player, move.getX(), move.getY());
                // Evaluate the move.
                int eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                // Put back the board to its initial state.
                othello.setBoard(boardSave);

                // Check if the current move is better than the best and update it.
                val = (val < eval)?eval:val;

                // Check if the best move is better than the alpha.
                alpha = (alpha < val)?val:alpha;

                // If the beta is lower than the alpha, the opponent will always chose the lower.
                if (beta <= alpha)
                {
                    // We cut the search.
                    break;
                }
            }
        }
        // It is to the opponent to play.
        else
        {
            // Initialise the move to the maximum value.
            val = Integer.MAX_VALUE;

            // Get the list of move on the current game state.
            List<Move> listMoves = othello.getListMoves(player);
            // For each move.
            for (Move move : listMoves)
            {
                // Save the current game state.
                boardSave.copy(othello.getBoard());
                // Play the current move on the game.
                othello.playAt(player, move.getX(), move.getY());
                // Evaluate the move.
                int eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                // Put back the board to its initial state.
                othello.setBoard(boardSave);

                // Check if the current move is better than the best and update it.
                val = (val > eval)?eval:val;

                // Check if the best move is lower than the beta.
                beta = (beta > val)?val:beta;

                // If the beta is lower than the alpha, the opponent will always chose the higher.
                if (beta <= alpha)
                {
                    // We cut the search.
                    break;
                }
            }
        }

        return val;
    }

    /**
     * Tells the player to play at a specific location.
     *
     * @param x The x coordinate on the game.
     * @param y The y coordinate on the game.
     */
    public void playAt(int x, int y)
    {
        // Do Nothing because the AI does not use the interface.
    }

    /**
     * Checks if the player is an AI.
     *
     * @return True if the player is an AI, else false.
     */
    @Override
    public boolean isAI() {
        return true;
    }
}
