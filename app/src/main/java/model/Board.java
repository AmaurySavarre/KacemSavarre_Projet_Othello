package model;

/**
 * Created by Utilisateur on 2/23/2016.
 */
public class Board
{
    private Case[][] _board;

    public Board()
    {
        _board = new Case[8][8];
        changeXY(1, 4, 4);
        changeXY(1, 5, 5);
        changeXY(2, 5, 4);
        changeXY(2, 4, 5);
    }

    public void changeXY(int player, int X, int Y)
    {
        if(X >= 0 && X < 8 && Y >= 0 && Y < 8)
        {
            _board[X][Y].reverse(player);
        }
    }

    public Case getXY(int X, int Y)
    {
        if(X >= 0 && X < 8 && Y >= 0 && Y < 8)
        {
            return _board[X][Y];
        }

        return null;
    }
}
