package model;

/**
 * Created by Amaury Savarre on 27/02/2016.
 */
abstract public class Player
{
    protected int _number;
    protected Othello _othello;
    protected boolean _hasPlayed;
    protected State _associatedState;

    public int getNumber()
    {
        return _number;
    }

    public State getAssociatedState()
    {
        return _associatedState;
    }

    public Player(Othello othello,int number)
    {
        _othello = othello;
        switch (number)
        {
            case 1:
                _associatedState = State.PLAYER1;
                break;
            case 2:
                _associatedState = State.PLAYER2;
                break;
            default:
                _associatedState = State.EMPTY;
                break;
        }
        _number = number;
        _hasPlayed = false;
    }

    abstract public void  play();
    abstract public void playAt(int x, int y);
}
