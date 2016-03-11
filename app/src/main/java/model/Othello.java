package model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import controller.OthelloController;

/**
 * Created by Amaury Savarre on 2/23/2016.
 */
public class Othello
{
    private Board _board;
    private Player _actual_player;
    private Player _player1;
    private Player _player2;

    private OthelloController _controller;

    /**
     * Othello constructor.
     *
     * @param controller The controller used to control the game.
     */
    public Othello(OthelloController controller, int size)
    {
        _controller = controller;
        _board = new Board(size);
    }

    public int getBoardSize()
    {
        return _board.getSize();
    }

    public Case getCase(int x, int y)
    {
        return _board.getXY(x, y);
    }

    public void initializeBoard()
    {
        _board.initializeCases();
    }

    /**
     * Says if a player moves is playable i.e. the case is empty with at least one immediate one neighbor case occupied by the adversary which can be catch.
     *
     * @param player The player who wants to play.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return Returns true if the case is empty with at least one immediate one neighbour case occupied by the adversary which can be catch, else false.
     */
    public boolean isPlayable(int player, int X, int Y)
    {
        //Log.d("isPlayable (" + X + "," + Y + ")", "IN");
        // Check if the case is empty and the player can make a catch at this coordinates.
        if(_board.caseEmpty(X, Y) && this.catchPossible(player, X, Y))
        {
            //Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> true");
            return true;
        }

        //Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> false");
        return false;
    }

    /**
     * Checks if there is a catch possible for the player i.e. for any direction, the new player's move surround at least one adversary disk with one the player disk.
     *
     * @param player The player who plays.
     * @param x The X coordinate on the board.
     * @param y The Y coordinate on the board.
     * @return Return true if for any direction, the new player's move surround at least one adversary disk with one the player disk.
     */
    public boolean catchPossible(int player, int x, int y)
    {
        //Log.d("catchPossible (" + x + "," + y + ")", "IN");
        // Get an iterator on the list of directions.
        //Iterator<Direction> ite = Direction.getListDirections().iterator();
        // Get the list of all directions.
        List<Direction> directions = Direction.getListDirections();

        // For each direction.
        //while(ite.hasNext())
        for(Direction direction : directions)
        {
            //Direction direction = ite.next();
            //Log.d("catchPossible (" + x + "," + y + ")", "dir -> " + direction);

            if(catchInDirection(player, x, y, direction))
            {
                //Log.d("catchPossible (" + x + "," + y + ")", "OUT -> true");
                return true;
            }

            /*// We temporarily move the X and Y.
            int Xtmp = x + direction.getDeltaX();
            int Ytmp = y + direction.getDeltaY();
            //Log.d("catchPossible (" + x + "," + y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

            // We check that the immediate neighbor is a disk of the adversary.
            if(Xtmp >= 0 && Xtmp < _board.getSize() &&
                    Ytmp >= 0 && Ytmp  < _board.getSize() &&
                    !_board.caseEmpty(Xtmp, Ytmp) &&
                    _board.getXY(Xtmp, Ytmp).getState() != player)
            {
                // Then we check if we can find a disk of the player that surrounds the adversary ones.
                while(direction.getDeltaX() + x >= 0 &&
                        direction.getDeltaX() + x < _board.getSize() &&
                        direction.getDeltaY() + y >= 0 &&
                        direction.getDeltaY() + y < _board.getSize() &&
                        !_board.caseEmpty(Xtmp, Ytmp))
                {
                    // We found a player's disk.
                    if(_board.getXY(Xtmp, Ytmp).getState() == player)
                    {
                        //Log.d("catchPossible (" + x + "," + y + ")", "OUT -> true");
                        return true;
                    }

                    // We temporarily move the X and Y to continue in the same direction.
                    Xtmp = Xtmp + direction.getDeltaX();
                    Ytmp = Ytmp + direction.getDeltaY();
                    //Log.d("catchPossible (" + x + "," + y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                }
            }*/
        }

        //Log.d("catchPossible (" + x + "," + y + ")", "OUT -> false");
        // No catch possible.
        return false;
    }

    public boolean catchInDirection(int player, int x, int y, Direction direction)
    {
        // We temporarily move the X and Y.
        int Xtmp = x + direction.getDeltaX();
        int Ytmp = y + direction.getDeltaY();
        //Log.d("catchInDirection (" + x + "," + y + ")", "IN : Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

        // We check that the immediate neighbor is a disk of the adversary.
        if(Xtmp >= 0 && Xtmp < _board.getSize() &&
                Ytmp >= 0 && Ytmp  < _board.getSize() &&
                !_board.caseEmpty(Xtmp, Ytmp) &&
                _board.getXY(Xtmp, Ytmp).getState() != player)
        {
            // Then we check if we can find a disk of the player that surrounds the adversary ones.
            while(Xtmp >= 0 &&
                    Xtmp < _board.getSize() &&
                    Ytmp >= 0 &&
                    Ytmp < _board.getSize() &&
                    !_board.caseEmpty(Xtmp, Ytmp))
            {
                // We found a player's disk.
                if(_board.getXY(Xtmp, Ytmp).getState() == player)
                {
                    //Log.d("catchInDirection (" + x + "," + y + ")", "OUT -> true");
                    return true;
                }

                // We temporarily move the X and Y to continue in the same direction.
                Xtmp = Xtmp + direction.getDeltaX();
                Ytmp = Ytmp + direction.getDeltaY();
                //Log.d("catchInDirection (" + x + "," + y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
            }
        }

        //Log.d("catchInDirection (" + x + "," + y + ")", "OUT -> false");
        return false;
    }

    /**
     * Plays a player's move at the specified location.
     *
     * @param player The player who plays.
     * @param x The X coordinate on the board.
     * @param y The Y coordinate on the board.
     */
    public boolean playAt(int player, int x, int y)
    {
        //Log.d("playAt (" + x + "," + y + ")", "IN");

        // Check if the player is able to play here.
        if(this.isPlayable(player, x, y))
        {
            // Place the player's disk at the location.
            _board.changeXY(player, x, y);

            // Get an iterator on the list of directions.
            //Iterator<Direction> ite = Direction.getListDirections().iterator();
            // Get the list of all directions.
            List<Direction> directions = Direction.getListDirections();

            // For each direction.
            //while(ite.hasNext())
            for(Direction direction : directions)
            {
                if(catchInDirection(player, x, y, direction))
                {
                    //Direction direction = ite.next();
                    //Log.d("playAt (" + x + "," + y + ")", "dir -> " + direction);

                    // Move temporarily the X and Y.
                    int Xtmp = x + direction.getDeltaX();
                    int Ytmp = y + direction.getDeltaY();
                    //Log.d("playAt (" + x + "," + y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

                    // Check that it's still on the board, the case is empty and the case is not occupied by a player's disk.
                    while(Xtmp >= 0 && Xtmp < _board.getSize() &&
                            Ytmp >= 0 && Ytmp < _board.getSize() &&
                            !_board.caseEmpty(Xtmp, Ytmp) &&
                            _board.getXY(Xtmp, Ytmp).getState() != player)
                    {

                        _board.changeXY(player, Xtmp, Ytmp);

                        Xtmp = Xtmp + direction.getDeltaX();
                        Ytmp = Ytmp + direction.getDeltaY();
                        //Log.d("playAt (" + x + "," + y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                    }
                }
            }
            return true;
        }

        //Log.d("playAt (" + x + "," + y + ")", "OUT");
        return false;
    }


    /**
     * Gives a list of moves available for the player.
     *
     * @param player The player who wants a list of moves available.
     * @return A list of moves available for the player.
     */
    public List<Move> getListMoves(int player)
    {
        ArrayList<Move> list =  new ArrayList<>();

        for(int y = 0 ; y < _board.getSize() ; y++)
        {
            for(int x = 0 ; x < _board.getSize() ; x++)
            {
                if(isPlayable(player, x, y))
                {
                    list.add(new Move(x, y));
                }
            }
        }

        return list;
    }

    public int getScore(int player)
    {
        int score = 0;

        for(int y = 0 ; y < _board.getSize() ; y++)
        {
            for(int x = 0 ; x < _board.getSize() ; x++)
            {
                if(_board.getXY(x, y).getState() == player)
                {
                    score++;
                }
            }
        }

        return score;
    }

    public boolean gameOver()
    {
        if(getListMoves(_controller.getPlayer1().getNumber()).isEmpty() && getListMoves(_controller.getPlayer2().getNumber()).isEmpty())
        {
            return true;
        }

        return false;
    }

    public String toString()
    {
        return _board.toString();
    }
}
