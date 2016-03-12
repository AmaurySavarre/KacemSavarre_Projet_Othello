package model;

import android.util.Log;

/**
 * Created by Amaury on 27/02/2016.
 */
public class PlayerHuman extends Player
{
    private boolean _isPlaying;

    public PlayerHuman(Othello othello,int number)
    {
        super(othello, number);

        _isPlaying = false;
    }

    public void play()
    {
        _isPlaying = true;

        while (!_hasPlayed)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.e("play()", e.toString());
            }
        }

        _hasPlayed = false;
    }

    public void playAt(int x, int y)
    {
        if(_isPlaying && _othello.isPlayable(this, x, y))
        {
            _othello.playAt(this, x, y);
            _hasPlayed = true;
            _isPlaying = false;
        }
    }
}
