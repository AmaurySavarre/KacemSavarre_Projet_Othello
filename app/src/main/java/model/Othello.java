package model;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Amaury on 2/23/2016.
 */
public class Othello
{
    private Board _board;
    private Player _actual_player;
    private Player _player1;
    private Player _player2;

    /**
     * Othello constructor.
     */
    public Othello()
    {
        _board = new Board();
        _player1 = new PlayerHuman(1);
        _player2 = new PlayerHuman(2);
        _actual_player = _player1;
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
        Log.d("isPlayable (" + X + "," + Y + ")", "IN");
        // Check if the case is empty and the player can make a catch at this coordinates.
        if(_board.caseEmpty(X, Y) && this.catchPossible(player, X, Y))
        {
            Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> true");
            return true;
        }

        Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> false");
        return false;
    }

    /**
     * Checks if there is a catch possible for the player i.e. for any direction, the new player's move surround at least one adversary disk with one the player disk.
     *
     * @param player The player who plays.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return Return true if for any direction, the new player's move surround at least one adversary disk with one the player disk.
     */
    public boolean catchPossible(int player, int X, int Y)
    {
        Log.d("catchPossible (" + X + "," + Y + ")", "IN");
        // Get an iterator on the list of directions.
        //Iterator<Direction> ite = Direction.getListDirections().iterator();
        // Get the list of all directions.
        List<Direction> directions = Direction.getListDirections();

        // For each direction.
        //while(ite.hasNext())
        for(Direction direction : directions)
        {
            //Direction direction = ite.next();
            Log.d("catchPossible (" + X + "," + Y + ")", "dir -> " + direction);

            // We temporarily move the X and Y.
            int Xtmp = X + direction.getDeltaX();
            int Ytmp = Y + direction.getDeltaY();
            Log.d("catchPossible (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

            // We check that the immediate neighbor is a disk of the adversary.
            if(Xtmp >= 0 && Xtmp < 8 && Ytmp >= 0 && Ytmp  < 8 && !_board.caseEmpty(Xtmp, Ytmp) && _board.getXY(Xtmp, Ytmp).getState() != player)
            {
                // Then we check if we can find a disk of the player that surrounds the adversary ones.
                while(direction.getDeltaX() + X >= 0 && direction.getDeltaX() + X < 8 && direction.getDeltaY() + Y >= 0 && direction.getDeltaY() + Y < 8 && !_board.caseEmpty(Xtmp, Ytmp))
                {
                    // We found a player's disk.
                    if(_board.getXY(Xtmp, Ytmp).getState() == player)
                    {
                        Log.d("catchPossible (" + X + "," + Y + ")", "OUT -> true");
                        return true;
                    }

                    // We temporarily move the X and Y to continue in the same direction.
                    Xtmp = Xtmp + direction.getDeltaX();
                    Ytmp = Ytmp + direction.getDeltaY();
                    Log.d("catchPossible (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                }
            }
        }

        Log.d("catchPossible (" + X + "," + Y + ")", "OUT -> false");
        // No catch possible.
        return false;
    }

    /**
     * Plays a player's move at the specified location.
     *
     * @param player The player who plays.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     */
    public void playAt(int player, int X, int Y)
    {
        Log.d("playAt (" + X + "," + Y + ")", "IN");

        // Check if the player is able to play here.
        if(this.isPlayable(player, X, Y))
        {
            // Place the player's disk at the location.
            _board.changeXY(player, X, Y);

            // Get an iterator on the list of directions.
            //Iterator<Direction> ite = Direction.getListDirections().iterator();
            // Get the list of all directions.
            List<Direction> directions = Direction.getListDirections();

            // For each direction.
            //while(ite.hasNext())
            for(Direction direction : directions)
            {
                //Direction direction = ite.next();
                Log.d("playAt (" + X + "," + Y + ")", "dir -> " + direction);

                // Move temporarily the X and Y.
                int Xtmp = X + direction.getDeltaX();
                int Ytmp = Y + direction.getDeltaY();
                Log.d("playAt (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

                // Check that it's still on the board, the case is empty and the case is not occupied by a player's disk.
                while(Xtmp >= 0 && Xtmp < 8 && Ytmp >= 0 && Ytmp < 8 && !_board.caseEmpty(Xtmp, Ytmp) && _board.getXY(Xtmp, Ytmp).getState() != player)
                {
                    
                    _board.changeXY(player, Xtmp, Ytmp);

                    Xtmp = Xtmp + direction.getDeltaX();
                    Ytmp = Ytmp + direction.getDeltaY();
                    Log.d("playAt (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                }
            }
        }

        Log.d("playAt (" + X + "," + Y + ")", "OUT");
    }

    // TODO: 29/02/2016 getListMoves()

    public String toString()
    {
        return _board.toString();
    }
}
