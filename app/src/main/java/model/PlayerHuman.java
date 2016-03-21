package model;

import android.util.Log;

/**
 * Created by Amaury Savarre on 27/02/2016.
 *
 * Class to create and manage a PlayerHuman.
 */
public class PlayerHuman extends Player
{
    /**
     * PlayerHuman  Constructor.
     *
     * @param othello The game on which the player plays.
     * @param number The number of the player.
     */
    public PlayerHuman(Othello othello,int number)
    {
        super(othello, number);
    }

    /**
     * Tells the player to play.
     */
    public void play()
    {
        // The player begin to play
        _hasPlayed = false;

        // As long as the player has not played or he is not told to stop.
        while (!_hasPlayed && !_mustEnd)
        {
            // The player waits an input and make the motor wait.
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.e("play()", e.toString());
            }
        }

        if (_mustEnd)
            _mustEnd = false;
    }

    /**
     * Tells the player to play at a specific location.
     *
     * @param x The x coordinate on the game.
     * @param y The y coordinate on the game.
     */
    public void playAt(int x, int y)
    {
        // If the player has not player (so he can play) and the position is valid.
        if(!_hasPlayed && _othello.isPlayable(this, x, y))
        {
            // The player plays at the x, y location on the game.
            _othello.playAt(this, x, y);
            _hasPlayed = true;
        }
    }
}
