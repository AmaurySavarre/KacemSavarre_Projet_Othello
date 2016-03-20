package model;

/**
 * Created by Amaury Savarre on 27/02/2016.
 */
abstract public class Player
{
    protected int _number;
    protected Othello _othello;
    protected boolean _hasPlayed;
    protected boolean _mustEnd;
    protected State _associatedState;

    public int getNumber()
    {
        return _number;
    }

    public State getAssociatedState()
    {
        return _associatedState;
    }

    public void stopPlayer()
    {
        _mustEnd = true;
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
        _mustEnd = false;
    }

    public boolean isAI()
    {
        return false;
    }

    abstract public void  play();
    abstract public void playAt(int x, int y);
}
