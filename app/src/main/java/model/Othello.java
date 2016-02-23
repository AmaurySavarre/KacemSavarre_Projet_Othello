package model;

/**
 * Created by Utilisateur on 2/23/2016.
 */
public class Othello
{
    private Board _board;

    public Othello()
    {
        _board = new Board();
    }

    public boolean isPlayable(int player, int X, int Y)
    {
        Case target = _board.getXY(X, Y);

        if(target.isEmpty())
        {
        }

        return false;
    }
}
