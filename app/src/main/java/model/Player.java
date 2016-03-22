package model;

/**
 * Created by Amaury Savarre on 27/02/2016.
 *
 * Abstract class that define a player. User must define the play() and playAt() methods.
 */
abstract public class Player
{
    protected int _number;              // The player's number.
    protected Othello _othello;         // The game on which the player plays.
    protected boolean _hasPlayed;       // A boolean saying if the play has played.
    protected boolean _mustEnd;         // A boolean to force ce player to stop playing.
    protected State _associatedState;   // The associated state of the player.

    /**
     * Gives the number of the player.
     *
     * @return The number of the player.
     */
    public int getNumber()
    {
        return _number;
    }

    /**
     * Gives the associated state of the player.
     *
     * @return The associated state of the player.
     */
    public State getAssociatedState()
    {
        return _associatedState;
    }

    /**
     * Indicates to the player that he must stop to play.     *
     */
    public void stopPlayer()
    {
        _mustEnd = true;
    }

    /**
     * Player Constructor.
     *
     * @param othello The game on which the player plays.
     * @param number The number of the player.
     */
    public Player(Othello othello,int number)
    {
        _othello = othello;

        // Associate the correct state to the player depending on his number.
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

    /**
     * Checks if the player is an AI.
     *
     * @return True if the player is an AI, else false.
     */
    public boolean isAI()
    {
        // By default the player is not an AI;
        return false;
    }

    /**
     * Tells the player to play.
     */
    abstract public void  play();

    /**
     * Tells the player to play at a specific location.
     *
     * @param x The x coordinate on the game.
     * @param y The y coordinate on the game.
     */
    abstract public void playAt(int x, int y);

    public String toString()
    {
        return "Player " + String.valueOf(_number);
    }
}
