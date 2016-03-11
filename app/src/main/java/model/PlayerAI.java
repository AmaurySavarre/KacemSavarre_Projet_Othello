package model;

import android.util.Log;

/**
 * Created by Amaury on 11/03/2016.
 */
public class PlayerAI implements Player
{
    private int _number;
    private Othello _othello;
    private boolean _hasPlayed;

    public int getNumber()
    {
        return _number;
    }

    public PlayerAI(Othello othello,int number)
    {
        _othello = othello;
        _number = number;
        _hasPlayed = false;
    }

    public void play()
    {
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
        if(_othello.isPlayable(_number, x, y))
        {
            _othello.playAt(_number, x, y);
            _hasPlayed = true;
        }
    }
}

// TODO: 11/03/2016 Faire l'IA.
