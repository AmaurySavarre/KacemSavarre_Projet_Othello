package model;

import android.util.Log;

import java.util.Iterator;

/**
 * Created by Amaury on 2/23/2016.
 */
public class Othello
{
    private Board _board;
    private Player _actual_player;
    private Player _player1;
    private Player _player2;

    public Othello()
    {
        _board = new Board();
        _player1 = new PlayerHuman(1);
        _player2 = new PlayerHuman(2);
        _actual_player = _player1;
    }

    public boolean isPlayable(int X, int Y)
    {
        Log.d("isPlayable (" + X + "," + Y + ")", "IN");
        if(_board.caseEmpty(X, Y) && catchPossible(X, Y))
        {
            Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> true");
            return true;
        }

        Log.d("isPlayable (" + X + "," + Y + ")", "OUT -> false");
        return false;
    }

    public boolean catchPossible(int X, int Y)
    {
        Log.d("catchPossible (" + X + "," + Y + ")", "IN");
        // Get an iterator on the list of directions.
        Iterator<Direction> ite = Direction.getListDirections().iterator();

        // For each directions
        while(ite.hasNext())
        {
            Direction dir = ite.next();
            Log.d("catchPossible (" + X + "," + Y + ")", "dir -> " + dir);

            // We move the X and Y
            int Xtmp = X + dir.getDeltaX();
            int Ytmp = Y + dir.getDeltaY();
            Log.d("catchPossible (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

            // We check that the immediate neighbor is a disk of the adversary
            if(Xtmp >= 0 && Xtmp < 8 && Ytmp >= 0 && Ytmp  < 8 && !_board.caseEmpty(Xtmp, Ytmp) && _board.getXY(Xtmp, Ytmp).getState() != _actual_player.getNumber())
            {
                // Then we check if we can find a disk of the player that surrounds the adversary ones.
                while(dir.getDeltaX() + X >= 0 && dir.getDeltaX() + X < 8 && dir.getDeltaY() + Y >= 0 && dir.getDeltaY() + Y < 8 && !_board.caseEmpty(Xtmp, Ytmp))
                {
                    // We found a player's disk.
                    if(_board.getXY(Xtmp, Ytmp).getState() == _actual_player.getNumber())
                    {
                        Log.d("catchPossible (" + X + "," + Y + ")", "OUT -> true");
                        return true;
                    }

                    Xtmp = Xtmp + dir.getDeltaX();
                    Ytmp = Ytmp + dir.getDeltaY();
                    Log.d("catchPossible (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                }
            }
        }

        Log.d("catchPossible (" + X + "," + Y + ")", "OUT -> false");
        return false;
    }

    public void playAt(int X, int Y)
    {
        Log.d("playAt (" + X + "," + Y + ")", "IN");

        if(isPlayable(X, Y))
        {
            _board.changeXY(_actual_player.getNumber(), X, Y);

            // Get an iterator on the list of directions.
            Iterator<Direction> ite = Direction.getListDirections().iterator();

            // For each directions
            while(ite.hasNext())
            {
                Direction dir = ite.next();
                Log.d("playAt (" + X + "," + Y + ")", "dir -> " + dir);

                // We move the X and Y
                int Xtmp = X + dir.getDeltaX();
                int Ytmp = Y + dir.getDeltaY();
                Log.d("playAt (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);

                // We check that the immediate neighbor is a disk of the adversary
                while(Xtmp >= 0 && Xtmp < 8 && Ytmp >= 0 && Ytmp < 8 && !_board.caseEmpty(Xtmp, Ytmp) && _board.getXY(Xtmp, Ytmp).getState() != _actual_player.getNumber())
                {
                    _board.changeXY(_actual_player.getNumber(),Xtmp, Ytmp);

                    Xtmp = Xtmp + dir.getDeltaX();
                    Ytmp = Ytmp + dir.getDeltaY();
                    Log.d("playAt (" + X + "," + Y + ")", "Xtmp -> " + Xtmp + " and Ytmp -> " + Ytmp);
                }
            }
        }

        Log.d("playAt (" + X + "," + Y + ")", "OUT");
    }

    public String toString()
    {
        return _board.toString();
    }
}
