package model;

/**
 * Created by Amaury on 27/02/2016.
 */
public class PlayerHuman implements Player
{
    private int _number;
    private Othello _othello;

    public int getNumber()
    {
        return _number;
    }

    public PlayerHuman(Othello othello,int number)
    {
        _othello = othello;
        _number = number;
    }

    public void play(int X, int Y)
    {

    }
}
