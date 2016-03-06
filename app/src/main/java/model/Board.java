package model;

import android.util.Log;

/**
 * Created by Amaury Savarre on 2/23/2016.
 */
public class Board
{
    private Case[][] _board;    // Matrix containing all the cases of the board.
    private int _size;          // Integer representing the size of the board.

    /**
     * Board Constructor.
     *
     * @param size The size of the board.
     */
    public Board(int size)
    {
        _size = size;
        _board = new Case[_size][_size];

        // Initializing the matrix of cases.
        for(int i = 0 ; i < _size ; ++i)
        {
            for(int j = 0 ; j < _size ; ++j)
            {
                _board[i][j] = new Case();
            }
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize()
    {
        return _size;
    }

    public void initializeCases()
    {
        // Placing the 4 first disks.
        changeXY(1, 3, 3);
        changeXY(1, 4, 4);
        changeXY(2, 4, 3);
        changeXY(2, 3, 4);

        /*for(int y = 0 ; y < _size ; ++y)
        {
            for(int x = 0 ; x < _size ; ++x)
            {
                if(x == 0 || y == 0)
                {
                    changeXY(1, x, y);
                }
                else
                {
                    changeXY(2, x, y);
                }
            }
        }
        changeXY(0, 5, 3);*/
    }

    /**
     * Changes the state of the case at the specified coordinates.
     *
     * @param player The player that change the state of the case.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     */
    public void changeXY(int player, int X, int Y)
    {
        Log.d("changeXY (" + X + "," + Y + ") player" + player, "IN");

        if(X >= 0 && X < _size && Y >= 0 && Y < _size)
        {
            _board[Y][X].changeState(player);
        }

        Log.d("changeXY (" + X + "," + Y + ") player" + player, "OUT");
    }

    /**
     * Returns the case at the specified coordinates.
     *
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return The case at the specified coordinates.
     */
    public Case getXY(int X, int Y)
    {
        Log.d("getXY (" + X + "," + Y + ")", "IN");

        if(X >= 0 && X < _size && Y >= 0 && Y < _size)
        {
            Log.d("getXY (" + X + "," + Y + ")", "OUT -> " + _board[X][Y]);
            return _board[Y][X];
        }

        Log.d("getXY (" + X + "," + Y + ")", "OUT -> null");
        return null;
    }

    /**
     * Checks if the case is empty.
     *
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return A boolean saying if the case is empty (true) or not (false).
     */
    public boolean caseEmpty(int X, int Y)
    {
        Log.d("caseEmpty (" + X + "," + Y + ")", "IN");
        Log.d("caseEmpty (" + X + "," + Y + ")", "OUT -> " + _board[X][Y].isEmpty());
        return _board[Y][X].isEmpty();
    }

    public String toString()
    {
        String res = new String();

        res += "\n";
        for(int i = 0 ; i < _size ; ++i)
        {
            for(int j = 0 ; j < _size ; ++j)
            {
                res += _board[i][j];
                res += " ";
            }
            res += "\n";
        }

        res += "\n";

        return res;
    }
}
