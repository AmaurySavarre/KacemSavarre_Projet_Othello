package model;

import android.util.Log;

/**
 * Created by Amaury on 2/23/2016.
 */
public class Board
{
    private Case[][] _board;

    public Board()
    {
        _board = new Case[8][8];

        for(int i = 0 ; i < 8 ; ++i)
        {
            for(int j = 0 ; j < 8 ; ++j)
            {
                _board[i][j] = new Case();
            }
        }

        changeXY(1, 3, 3);
        changeXY(1, 4, 4);
        changeXY(2, 4, 3);
        changeXY(2, 3, 4);

        /*for(int i = 0 ; i < 8 ; ++i)
        {
            for(int j = 0 ; j < 8 ; ++j)
            {
                if(j == 0 || i == 0)
                {
                    changeXY(1, i, j);
                }
                else
                {
                    changeXY(2, i, j);
                }
            }
        }
        changeXY(0, 3, 3);*/
    }

    public void changeXY(int player, int X, int Y)
    {
        Log.d("changeXY (" + X + "," + Y + ") player" + player, "IN");

        if(X >= 0 && X < 8 && Y >= 0 && Y < 8)
        {
            _board[Y][X].reverse(player);
        }

        Log.d("changeXY (" + X + "," + Y + ") player" + player, "OUT");
    }

    public Case getXY(int X, int Y)
    {
        Log.d("getXY (" + X + "," + Y + ")", "IN");

        if(X >= 0 && X < 8 && Y >= 0 && Y < 8)
        {
            Log.d("getXY (" + X + "," + Y + ")", "OUT -> " + _board[X][Y]);
            return _board[Y][X];
        }

        Log.d("getXY (" + X + "," + Y + ")", "OUT -> null");
        return null;
    }

    public boolean caseEmpty(int X, int Y) {
        Log.d("caseEmpty (" + X + "," + Y + ")", "OUT -> " + _board[X][Y].isEmpty());
        return _board[Y][X].isEmpty();
    }

    public String toString()
    {
        String res = new String();

        res += "\n";
        for(int i = 0 ; i < 8 ; ++i)
        {
            for(int j = 0 ; j < 8 ; ++j)
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
