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
        _hasPlayed = false;

        while (!_hasPlayed && !_mustEnd)
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

        if (_mustEnd)
            _mustEnd = false;
    }

    public void playAt(int x, int y)
    {
        if(!_hasPlayed && _othello.isPlayable(this, x, y))
        {
            _othello.playAt(this, x, y);
            _hasPlayed = true;
        }
    }
}
