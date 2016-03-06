package controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.utilisateur.othello.CaseButton;
import com.example.utilisateur.othello.GameActivity;

import java.util.Observer;

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
    private boolean _waiting;

    private Othello _othello;
    private GameActivity _view;

    private View.OnClickListener _listener;

    public OthelloController(GameActivity view, int size)
    {
        _othello = new Othello(this, size);
        _view = view;

        _player1 = new PlayerHuman(_othello, 1);
        _player2 = new PlayerHuman(_othello, 2);
        _actual_player = _player1;

        _waiting = true;

        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                int y = id/_othello.getBoardSize();
                int x = id - (_othello.getBoardSize())*y;

                //Prévenir les clicks quand le joueur adverse joue
                /*if(enAttente.compareAndSet(false, true)){
                    turn(i,j);
                }*/

                /*if(compareAndSetWaitin(true, false))
                {
                    getActualPlayer().playAt(x, y);
                }*/
                if(_othello.playAt(_actual_player.getNumber(), x, y))
                {
                    changePlayer();
                    _view.updateScores(_othello.getScore(1), _othello.getScore(2));
                }

                //Toast.makeText(_view.getApplicationContext(), "Case " + x + "x" + y, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener getListener()
    {
        return _listener;
    }

    public Player getPlayer1()
    {
        return _player1;
    }

    public Player getPlayer2()
    {
        return _player2;
    }

    // TODO: 29/02/2016 getActualPlayer()
    public Player getActualPlayer()
    {
        return _actual_player;
    }

    public CaseButton createButton(Context context, int x, int y)
    {
        CaseButton btn = new CaseButton(context, _othello.getCase(x, y));
        _othello.getCase(x, y).addObserver(btn);

        return btn;
    }

    public void initializeGame()
    {
        _othello.initializeBoard();
    }

    public boolean compareAndSetWaitin(boolean compare, boolean set)
    {
        if(_waiting == compare)
        {
            _waiting = set;
            return true;
        }

        return false;
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

    public void startGame()
    {
        while(!_othello.gameOver())
        {
            if(!_othello.getListMoves(_actual_player.getNumber()).isEmpty())
            {
                _actual_player.play();
                compareAndSetWaitin(false, true);
            }
            changePlayer();
        }
    }

    public void setCaseObserver(int x, int y, Observer o)
    {
        _othello.getCase(x, y).addObserver(o);
    }

    // TODO: 29/02/2016 Listener to treat onClick action from GameActivity buttons.
}
