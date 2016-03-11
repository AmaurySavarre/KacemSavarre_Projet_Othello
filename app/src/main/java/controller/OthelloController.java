package controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
public class OthelloController extends Thread
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

        //_view.setImagePlayer(_player1, _player2);

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

                if(compareAndSetWaitin(true, false))
                {
                    getActualPlayer().playAt(x, y);
                    compareAndSetWaitin(false, true);
                }
                //_actual_player.playAt(x, y);
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

    public Player getActualPlayer()
    {
        return _actual_player;
    }

    public int getBoardSize()
    {
        return _othello.getBoardSize();
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
        _view.updateScores(_othello.getScore(1), _othello.getScore(2));
        _view.setTurn(_actual_player);
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


        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.setTurn(_actual_player);
            }
        });
    }

    public void changeScores(int scorePlayer1, int scorePlayer2)
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateScores(_othello.getScore(_player1.getNumber()), _othello.getScore(_player2.getNumber()));
            }
        });
    }

    public void launchToast(final String toast)
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(_view.getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void run()
    {
        while(!_othello.gameOver())
        {
            if(!_othello.getListMoves(_actual_player.getNumber()).isEmpty())
            {
                Log.e("run", "1");
                _actual_player.play();
                Log.e("run", "2");
                //compareAndSetWaitin(false, true);
                Log.e("run", "3");
            }
            else
            {
                // Advert player that he can't play.
                Log.e("run", "4");
                launchToast("Ne peut pas jouer");
                // TODO: 11/03/2016 Prévenir le joueur qu'il ne peut pas jouer.
            }

            Log.e("run", "Pre");
            changeScores(_othello.getScore(1), _othello.getScore(2));
            changePlayer();
            Log.e("run", "Post");
        }

        // TODO: 11/03/2016 Gérer la fin de partie.
        Log.e("run", "Finish");
        launchToast("Partie finie !");
    }

    public void setCaseObserver(int x, int y, Observer o)
    {
        _othello.getCase(x, y).addObserver(o);
    }

}
