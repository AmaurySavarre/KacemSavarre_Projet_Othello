package model;

import java.util.ArrayList;
import java.util.List;

import controller.OthelloController;

/**
 * Created by Amaury Savarre on 2/23/2016.
 *
 * Class to create and manage an Othello and that implements the game engine.
 */
public class Othello
{
    private Board _board;

    private OthelloController _controller;

    /**
     * Othello's Constructor.
     *
     * @param controller The controller used to control the game.
     * @param size the size of the board.
     */
    public Othello(OthelloController controller, int size)
    {
        _controller = controller;
        _board = new Board(size);
    }

    /**
     * A special Constructor that only create an empty Othello.
     *
     * @param controller The controller used to control the game.
     */
    private Othello(OthelloController controller)
    {
        _controller = controller;
    }

    /**
     * Copy Constructor.
     *
     * @param o The Othello to copy.
     */
    public Othello(Othello o)
    {
        _board = new Board(o._board);
        _controller = o._controller;
    }

    /**
     * Gets the board size.
     *
     * @return The size of the board.
     */
    public int getBoardSize()
    {
        return _board.getSize();
    }

    /**
     * Gets the case at the specified coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The case at the specified coordinates.
     */
    public Case getCase(int x, int y)
    {
        return _board.getXY(x, y);
    }

    /**
     * Gets the board of the game
     *
     * @return The board of the game.
     */
    public Board getBoard()
    {
        return _board;
    }

    /**
     * Sets the board.
     *
     * @param board The board to set.
     */
    public void setBoard(Board board)
    {
        // Copy the board the the game board.
        _board.copy(board);
    }

    /**
     * Initializes the board.
     */
    public void initializeBoard()
    {
        _board.initializeCases(_controller.getPlayer1(), _controller.getPlayer2());
    }

    /**
     * Says if a player moves is playable i.e. the case is empty with at least one immediate one neighbor case occupied by the adversary which can be catch.
     *
     * @param player The player who wants to play.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return Returns true if the case is empty with at least one immediate one neighbour case occupied by the adversary which can be catch, else false.
     */
    public boolean isPlayable(Player player, int X, int Y)
    {
        // Check if the case is empty and the player can make a catch at this coordinates.
        return (_board.caseEmpty(X, Y) && this.catchPossible(player, X, Y));
    }

    /**
     * Checks if there is a catch possible for the player i.e. for any direction, the new player's move surround at least one adversary disk with one the player disk.
     *
     * @param player The player who plays.
     * @param x The X coordinate on the board.
     * @param y The Y coordinate on the board.
     * @return Return true if for any direction, the new player's move surround at least one adversary disk with one the player disk.
     */
    public boolean catchPossible(Player player, int x, int y)
    {
        // Get the list of all directions.
        List<Direction> directions = Direction.getListDirections();

        // For each direction.
        for(Direction direction : directions)
        {

            if(catchInDirection(player, x, y, direction))
            {
                return true;
            }
        }

        // No catch possible.
        return false;
    }

    /**
     * Verifies if there is a catch possible in a specified direction.
     *
     * @param player The player who plays.
     * @param x The x coordinate where the catch begin.
     * @param y The y coordinate where the catch begin.
     * @param direction The direction of the catch.
     * @return True if there is a catch possible in the specified direction, else false.
     */
    public boolean catchInDirection(Player player, int x, int y, Direction direction)
    {
        // We temporarily move the X and Y.
        int Xtmp = x + direction.getDeltaX();
        int Ytmp = y + direction.getDeltaY();

        // We check that the immediate neighbor is a disk of the adversary.
        if(Xtmp >= 0 && Xtmp < _board.getSize() &&
                Ytmp >= 0 && Ytmp  < _board.getSize() &&
                !_board.caseEmpty(Xtmp, Ytmp) &&
                _board.getXY(Xtmp, Ytmp).getState() != player.getAssociatedState())
        {
            // Then we check if we can find a disk of the player that surrounds the adversary ones.
            while(Xtmp >= 0 &&
                    Xtmp < _board.getSize() &&
                    Ytmp >= 0 &&
                    Ytmp < _board.getSize() &&
                    !_board.caseEmpty(Xtmp, Ytmp))
            {
                // We found a player's disk.
                if(_board.getXY(Xtmp, Ytmp).getState() == player.getAssociatedState())
                {
                    return true;
                }

                // We temporarily move the X and Y to continue in the same direction.
                Xtmp = Xtmp + direction.getDeltaX();
                Ytmp = Ytmp + direction.getDeltaY();
            }
        }

        return false;
    }

    /**
     * Plays a player's move at the specified location.
     *
     * @param player The player who plays.
     * @param x The X coordinate on the board.
     * @param y The Y coordinate on the board.
     */
    public boolean playAt(Player player, int x, int y)
    {
        //Log.d("playAt (" + x + "," + y + ")", "IN");

        // Check if the player is able to play here.
        if(this.isPlayable(player, x, y))
        {
            // Place the player's disk at the location.
            _board.changeXY(player, x, y);

            // Get the list of all directions.
            List<Direction> directions = Direction.getListDirections();

            // For each direction.
            for(Direction direction : directions)
            {
                if(catchInDirection(player, x, y, direction))
                {
                    // Move temporarily the X and Y.
                    int Xtmp = x + direction.getDeltaX();
                    int Ytmp = y + direction.getDeltaY();

                    // Check that it's still on the board, the case is empty and the case is not occupied by a player's disk.
                    while(Xtmp >= 0 && Xtmp < _board.getSize() &&
                            Ytmp >= 0 && Ytmp < _board.getSize() &&
                            !_board.caseEmpty(Xtmp, Ytmp) &&
                            _board.getXY(Xtmp, Ytmp).getState() != player.getAssociatedState())
                    {

                        // Apply the change.
                        _board.changeXY(player, Xtmp, Ytmp);

                        Xtmp = Xtmp + direction.getDeltaX();
                        Ytmp = Ytmp + direction.getDeltaY();
                    }
                }
            }
            return true;
        }

        return false;
    }


    /**
     * Gives a list of moves available for the player.
     *
     * @param player The player who wants a list of moves available.
     * @return A list of moves available for the player.
     */
    public List<Move> getListMoves(Player player)
    {
        ArrayList<Move> list =  new ArrayList<>();

        // Scan all the board.
        for(int y = 0 ; y < _board.getSize() ; y++)
        {
            for(int x = 0 ; x < _board.getSize() ; x++)
            {
                // If the actual case is playable for the player.
                if(isPlayable(player, x, y))
                {
                    // Add the move to the list of moves.
                    list.add(new Move(x, y));
                }
            }
        }

        return list;
    }

    /**
     * Gives the score of the specified player.
     *
     * @param player The player of whom we want to get the score.
     * @return The score of the given player.
     */
    public int getScore(Player player)
    {
        int score = 0;

        // Scan the board.
        for(int y = 0 ; y < _board.getSize() ; y++)
        {
            for(int x = 0 ; x < _board.getSize() ; x++)
            {
                // If the case is actually occupied by the player.
                if(_board.getXY(x, y).getState() == player.getAssociatedState())
                {
                    // Add one to the score.
                    score++;
                }
            }
        }

        return score;
    }

    /**
     * Gives the opponent of the player.
     *
     * @param player The player to whom we wants to get the opponent.
     * @return The opponent of the player.
     */
    public Player getOpponent(Player player)
    {
        return _controller.getOpponent(player);
    }

    /**
     * Evaluates the board for the specified player.
     *
     * @param player The player who wants to evaluate the board.
     * @return The value of the board for the player.
     */
    public int evaluateBoard(Player player)
    {
        int eval = 0;

        // If we are on a game over.
        if (gameOver())
        {
            // If the opponent win the game or it is a draw.
            if (getScore(player) <= getScore(getOpponent(player)))
            {
                // Gives the minimum value.
                return Integer.MIN_VALUE;
            }
            // If it is the player who wins.
            else
            {
                // Gives the maximum value.
                return Integer.MAX_VALUE;
            }
        }

        // If the player can't play in this state of the game.
        if (getListMoves(player).isEmpty())
        {
            // Initializes the value of the board to half the minimum.
            eval = Integer.MIN_VALUE/2;
        }
        // If the opponent can't play in this state of the game.
        else if (getListMoves(getOpponent(player)).isEmpty())
        {
            // Initializes the value of the board to half the maximum.
            eval = Integer.MAX_VALUE/2;
        }

        // Scan the board.
        for (int x = 0; x < getBoardSize(); ++x)
        {
            for (int y = 0; y < getBoardSize(); ++y)
            {
                Case c = _board.getXY(x, y);

                // If the case is not empty.
                if (!c.isEmpty())
                {
                    // The default weight of a disk is one.
                    int weight = 1;

                    // If the disk is in a corner.
                    if ((x==0 && y==0) || (x==0 && y==getBoardSize()-1) || (x==getBoardSize()-1 && y==0) || (x==getBoardSize()-1 && y==getBoardSize()-1))
                    {
                        // Add a thousand to the weight.
                        weight += 1000;
                    }
                    // If the disk is on a side.
                    else if (x == 0 || y == 0)
                    {
                        // Add ten to the weight.
                        weight += 10;
                    }

                    // If the disk is one of the opponent.
                    if (c.getState() != player.getAssociatedState())
                    {
                        // Negate the weight.
                        weight = -weight;
                    }

                    // Add the weight to the current value of the board.
                    eval += weight;
                }
            }
        }

        return eval;
    }

    /**
     * Says if the game is over.
     *
     * @return True if the game is over, else false.
     */
    public boolean gameOver()
    {
        // If none of the player have any moves lefts, the game is over.
        return (getListMoves(_controller.getPlayer1()).isEmpty() && getListMoves(_controller.getPlayer2()).isEmpty());
    }

    public String toString()
    {
        return _board.toString();
    }

    /**
     * Create a game from a string.
     *
     * @param data String that contains the data for the game.
     * @param boardSize The size of the board.
     * @param controller The controller that control the game.
     * @return A new Othello using the information given.
     */
    public static Othello fromString(String data, int boardSize, OthelloController controller)
    {
        // create a new empty Othello.
        Othello othello = new Othello(controller);

        // Create and initialise the board from the string and the board size.
        othello._board = Board.fromString(data, boardSize);

        return othello;
    }
}
