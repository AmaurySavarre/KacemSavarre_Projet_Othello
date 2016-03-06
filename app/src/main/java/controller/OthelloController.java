package controller;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import model.Othello;
import model.Player;
import model.PlayerHuman;

/**
 * Created by Amaury Savarre on 29/02/2016.
 */
public class OthelloController
{
    private Player _player1;
    private Player _player2;
    private Player _actual_player;

    private Othello _othello;
    private Activity _view;

    private View.OnClickListener _listener;

    public OthelloController(Activity view, int size)
    {
        _othello = new Othello(this, size);
        _view = view;

        _player1 = new PlayerHuman(_othello, 1);
        _player2 = new PlayerHuman(_othello, 2);
        _actual_player = _player1;

        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                int x = id/_othello.getBoardSize();
                int y = id - (_othello.getBoardSize())*x;

                //Prévenir les clicks quand le joueur adverse joue
                /*if(enAttente.compareAndSet(false, true)){
                    turn(i,j);
                }*/

                getActualPlayer().play(x, y);
                //_othello.playAt(_actual_player.getNumber(), x, y);

                Toast.makeText(_view.getApplicationContext(), "Case " + x + "x" + y, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener getListener()
    {
        return _listener;
    }

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
