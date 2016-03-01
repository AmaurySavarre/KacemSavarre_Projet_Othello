package controller;

import model.Player;

/**
 * Created by Amaury on 29/02/2016.
 */
public class OthelloController
{
    public Player _player1;
    public Player _player2;
    public Player _actual_player;

    // TODO: 29/02/2016 getActualPlayer()
    public Player getActualPlayer()
    {
        return _actual_player;
    }

    // TODO: 29/02/2016 changePlayer()
    public void changePlayer()
    {
        if(_actual_player == _player1)
        {
            _actual_player = _player2;
        }
        else if(_actual_player == _player2)
        {
            _actual_player = _player1;
        }
    }

    // TODO: 29/02/2016 Listener to treat onClick action from GameActivity buttons.
}
