package controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.utilisateur.othello.CaseButton;
import com.example.utilisateur.othello.GameActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import model.Move;
import model.Othello;
import model.Player;
import model.PlayerAI;
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

    private GameThread thread;

    public OthelloController(GameActivity view, int size)
    {
        _othello = new Othello(this, size);
        _view = view;

        _player1 = new PlayerHuman(_othello, 1);

        Intent intent = view.getIntent();
        if (intent.getBooleanExtra("AI", false))
        {
            _player2 = new PlayerAI(_othello, 2, 3);
        }
        else
        {
            _player2 = new PlayerHuman(_othello, 2);
        }
        _actual_player = _player1;

        _waiting = true;

        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                int y = id/_othello.getBoardSize();
                int x = id - (_othello.getBoardSize())*y;

                if(compareAndSetWaitin(true, false))
                {
                    getActualPlayer().playAt(x, y);
                    compareAndSetWaitin(false, true);
                }
            }
        };

        _othello.initializeBoard();
    }

    private OthelloController(GameActivity view)
    {
        _view = view;

        _waiting = true;

        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                int y = id/_othello.getBoardSize();
                int x = id - (_othello.getBoardSize())*y;

                if(compareAndSetWaitin(true, false))
                {
                    getActualPlayer().playAt(x, y);
                    compareAndSetWaitin(false, true);
                }
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

    public Player getOpponent(Player player)
    {
        if(player == _player1)
        {
            return _player2;
        }
        else if(player == _player2)
        {
            return _player1;
        }
        else
        {
            return null;
        }
    }

    public int getBoardSize()
    {
        return _othello.getBoardSize();
    }

    public CaseButton createButton(Context context, int x, int y)
    {
        CaseButton btn = new CaseButton(context, _othello.getCase(x, y));
        //_othello.getCase(x, y).addObserver(btn);

        return btn;
    }

    public void initializeGame()
    {
        changeScores();
        updateBoard();
        _view.updateTurn(_actual_player);
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
                _view.updateTurn(_actual_player);
            }
        });
    }

    public void changeScores()
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateScores(_othello.getScore(_player1), _othello.getScore(_player2));
            }
        });
    }

    public void updateBoard()
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateButton(_othello.getBoard());
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

    public void showPlayerMoves(final List<Move> moves)
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.showPlayerMoves(moves);
            }
        });
    }

    private class GameThread extends Thread
    {
        private boolean _isRunning = true;

        @Override
        public void run()
        {
            _isRunning = true;
            while(!_othello.gameOver() && _isRunning)
            {
                Log.d("GameThread.run()", "new turn");
                try
                {
                    while(!_view.updated())
                    {
                        Thread.sleep(200);
                    }
                }
                catch (InterruptedException e)
                {
                    Log.e("run", e.getMessage());
                }

                List<Move> listMoves = _othello.getListMoves(_actual_player);
                if(!listMoves.isEmpty())
                {
                    if (!_actual_player.isAI())
                    {
                        showPlayerMoves(listMoves);
                    }
                    _actual_player.play();

                    if(_isRunning)
                    {
                        updateBoard();
                        changeScores();
                    }
                }
                else
                {
                    // Advert player that he can't play.
                    launchToast("Ne peut pas jouer");
                    // TODO: 11/03/2016 Prévenir le joueur qu'il ne peut pas jouer.
                }

                if(_isRunning)
                    changePlayer();

                Log.d("GameThread.run()", "end turn");
            }

            if(_isRunning)
            {
                // TODO: 11/03/2016 Gérer la fin de partie.
                launchToast("Partie finie !");
            }
            else
            {
                Log.d("GameThread.run()", "stop running");
            }

            _isRunning = false;
        }

        public void stopGame()
        {
            _isRunning = false;
        }
    }


    public void stop()
    {
        thread.stopGame();
        _actual_player.stopPlayer();
        try {
            thread.join();
        }
        catch (Exception e)
        {
            Log.e("stop()", e.getMessage());
        }
    }

    public void start()
    {
        thread = new GameThread();
        thread.start();
    }

    public String toXML() throws IOException
    {
        if(!_othello.gameOver())
        {
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            serializer.startTag("", "Othello");
            serializer.startTag("", "Board");
            serializer.text(_othello.getBoard().toString());
            serializer.endTag("", "Board");
            serializer.startTag("", "Size");
            serializer.text(String.valueOf(_othello.getBoardSize()));
            serializer.endTag("", "Size");
            serializer.startTag("", "PlayerActual");
            serializer.text(String.valueOf(_actual_player.getNumber()));
            serializer.endTag("", "PlayerActual");
            if (_player2.isAI())
            {
                serializer.startTag("", "AI");
                serializer.text(String.valueOf(_actual_player.getNumber()));
                serializer.endTag("", "AI");
            }
            serializer.endTag("", "Othello");

            serializer.endDocument();

            return writer.toString();
        }

        return null;
    }

    public static OthelloController fromXML(InputStream input, GameActivity view) throws XmlPullParserException, IOException
    {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(input, "UTF-8");

        int eventType = parser.getEventType();

        OthelloController oc = new OthelloController(view);

        int boardSize = 2;
        String data = "1221";
        int actualPlayer = 1;
        boolean AI = false;

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            String tag;

            switch (eventType)
            {
                case XmlPullParser.START_TAG :
                    tag = parser.getName();
                    if (tag.equals("Board"))
                    {
                        //Toast.makeText(_view.getApplicationContext(), parser.nextText(), Toast.LENGTH_SHORT).show();
                        data = parser.nextText();
                    }
                    else if (tag.equals("Size"))
                    {
                        boardSize = Integer.parseInt(parser.nextText());
                    }
                    else if (tag.equals("PlayerActual"))
                    {
                        actualPlayer = Integer.parseInt(parser.nextText());
                    }
                    else if (tag.equals("AI"))
                    {
                        AI = true;
                    }
                    break;
            }

            eventType = parser.next();
        }

        oc._othello = Othello.fromString(data, boardSize, oc);

        oc._player1 = new PlayerHuman(oc._othello, 1);
        if (AI)
        {
            oc._player2 = new PlayerAI(oc._othello, 2, 3);
        }
        else
        {
            oc._player2 = new PlayerHuman(oc._othello, 2);
        }

        if(actualPlayer == 2)
        {
            oc._actual_player = oc._player2;
        }
        else
        {
            oc._actual_player = oc._player1;
        }

        return oc;
    }
}
