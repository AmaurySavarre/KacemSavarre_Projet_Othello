package controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.utilisateur.othello.CaseButton;
import com.example.utilisateur.othello.GameActivity;
import com.example.utilisateur.othello.R;

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
 *
 * Class to create and manage a controller for Othello.
 */
public class OthelloController
{
    private Player _player1;                // Player 1
    private Player _player2;                // Player 2
    private Player _actual_player;          // The actual player who plays
    private boolean _waiting;               // Variable that says if the controller is waiting or not.

    private Othello _othello;               // The game to play on.
    private GameActivity _view;             // The view to show the game.

    private View.OnClickListener _listener; // The listener to manage the action on the view.

    private GameThread thread;              // The thread of the game.

    /**
     * OthelloController Constructor.
     *
     * @param view The view on which the controller operate.
     * @param size The size of the board.
     */
    public OthelloController(GameActivity view, int size)
    {
        // Create a new game.
        _othello = new Othello(this, size);
        _view = view;

        // Create the player 1
        _player1 = new PlayerHuman(_othello, 1);

        // Get the AI option from the view.
        Intent intent = view.getIntent();

        // If the player asked an AI.
        if (intent.getBooleanExtra("AI", false))
        {
            //Create an AI.
            _player2 = new PlayerAI(_othello, 2, 3);
        }
        else
        {
            // Create a second human player.
            _player2 = new PlayerHuman(_othello, 2);
        }

        // Set the actual player to player 1.
        _actual_player = _player1;

        _waiting = true;

        // Create a listener for the button.
        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the x and y coordinates depending on the ID of the Button.
                int id = view.getId();
                int y = id/_othello.getBoardSize();
                int x = id - (_othello.getBoardSize())*y;

                // If the controller is in waiting mode.
                if(compareAndSetWaiting(true, false))
                {
                    // Ask the player to play on the corresponding Case to the Button.
                    getActualPlayer().playAt(x, y);
                    compareAndSetWaiting(false, true);
                }
            }
        };

        // Initialize the board.
        _othello.initializeBoard();
    }

    /**
     * Create an empty controller.
     *
     * @param view The view on which the controller operate.
     */
    private OthelloController(GameActivity view)
    {
        _view = view;

        _waiting = true;

        // Create a listener for the button.
        _listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the x and y coordinates depending on the ID of the Button.
                int id = view.getId();
                int y = id/_othello.getBoardSize();
                int x = id - (_othello.getBoardSize())*y;

                // If the controller is in waiting mode.
                if(compareAndSetWaiting(true, false))
                {
                    // Ask the player to play on the corresponding Case to the Button.
                    getActualPlayer().playAt(x, y);
                    compareAndSetWaiting(false, true);
                }
            }
        };
    }

    /**
     * Gives the Button listener.
     *
     * @return The Button listener.
     */
    public View.OnClickListener getListener()
    {
        return _listener;
    }

    /**
     * Gives the Player 1.
     *
     * @return The player 1.
     */
    public Player getPlayer1()
    {
        return _player1;
    }

    /**
     * Gives the Player 2.
     *
     * @return The player 2.
     */
    public Player getPlayer2()
    {
        return _player2;
    }

    /**
     * Gives the actual player.
     *
     * @return The actual player.
     */
    public Player getActualPlayer()
    {
        return _actual_player;
    }

    /**
     * Gives the opponent of the player.
     *
     * @param player The player to whom we wants to get the opponent.
     * @return The opponent of the player.
     */
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

    /**
     * Gives the board size.
     *
     * @return The size of the board.
     */
    public int getBoardSize()
    {
        return _othello.getBoardSize();
    }

    public Player getWinner()
    {
        if(_othello.getScore(_player1) > _othello.getScore(_player2))
        {
            return _player1;
        }
        else if (_othello.getScore(_player1) < _othello.getScore(_player2))
        {
            return _player2;
        }

        return null;
    }

    /**
     * Create a button for the board.
     *
     * @param context The context in which the button is created.
     * @param x The x coordinate of the button.
     * @param y The y coordinate of the button.
     * @return A new button.
     */
    public CaseButton createButton(Context context, int x, int y)
    {
        return (new CaseButton(context, _othello.getCase(x, y)));
    }

    /**
     * Initializes the game.
     */
    public void initializeGame()
    {
        changeScores();                     // Update the scores.
        updateBoard();                      // Update the buttons.
        _view.updateTurn(_actual_player);   // Set the turn to the actual player.
    }

    /**
     * Resets the game.
     */
    public void resetGame()
    {
        _actual_player = _player1;

        _othello.initializeBoard();
        initializeGame();

        _waiting = true;

        start();
    }

    /**
     * Compares and sets the waiting variable.
     *
     * @param compare The value to compare.
     * @param set The value to set if the compare is ok.
     * @return If the value has changed.
     */
    public boolean compareAndSetWaiting(boolean compare, boolean set)
    {
        if(_waiting == compare)
        {
            _waiting = set;
            return true;
        }

        return false;
    }

    /**
     * Change the actual player to its opponent.
     */
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

        // Update the view.
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateTurn(_actual_player);
            }
        });
    }

    /**
     * Change the scores on the view.
     */
    public void changeScores()
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateScores(_othello.getScore(_player1), _othello.getScore(_player2));
            }
        });
    }

    /**
     * Update the board on the view.
     */
    public void updateBoard()
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.updateButton(_othello.getBoard());
            }
        });
    }


    public void showGameOver()
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.showGameOver();
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

    /**
     * Show the player's available moves.
     *
     * @param moves The moves available to the player.
     */
    public void showPlayerMoves(final List<Move> moves)
    {
        _view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.showPlayerMoves(moves);
            }
        });
    }

    /**
     * Inner class to represent a game thread.
     */
    private class GameThread extends Thread
    {
        private boolean _isRunning = true;  // Indicate if the thread is running.

        /**
         * The run function of the thread.
         */
        @Override
        public void run()
        {
            _isRunning = true;  // The thread is running.

            Resources res = _view.getResources();

            // Loop while the game is not over and the thread is running.
            while(!_othello.gameOver() && _isRunning)
            {

                // Wait for the view to be updated.
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

                // Get the list of move of the actual player.
                List<Move> listMoves = _othello.getListMoves(_actual_player);

                // If he have at least one move available.
                if(!listMoves.isEmpty())
                {
                    // If it's not an IA.
                    if (!_actual_player.isAI())
                    {
                        // Show the available moves.
                        showPlayerMoves(listMoves);
                    }

                    // Ask the player to play.
                    _actual_player.play();

                    // If the thread is running.
                    if(_isRunning)
                    {
                        // Update the view.
                        updateBoard();
                        changeScores();
                    }
                }
                else
                {
                    // Advert player that he can't play.
                    launchToast(res.getString(R.string.Game_cannot_play, _actual_player.toString()));
                }

                // If the thread is running.
                if(_isRunning)
                {
                    // Change the actual player.
                    changePlayer();
                }
            }

            if(_isRunning)
            {
                showGameOver();
            }

            _isRunning = false;
        }

        /**
         * Asks the thread to stop.
         */
        public void stopGame()
        {
            _isRunning = false;
        }
    }


    /**
     * Stop the controller.
     */
    public void stop()
    {
        // Stop the game.
        thread.stopGame();

        // Ask the actual player to stop.
        _actual_player.stopPlayer();
        try {
            thread.join();
        }
        catch (Exception e)
        {
            Log.e("stop()", e.getMessage());
        }
    }

    /**
     * Start the controller.
     */
    public void start()
    {
        thread = new GameThread();
        thread.start();
    }

    /**
     * Creates a string corresponding of the XML version of the controller.
     *
     * @return A string corresponding of the XML version of the controller.
     * @throws IOException
     */
    public String toXML() throws IOException
    {
        // If the game is not over.
        if(!_othello.gameOver())
        {

            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            serializer.startTag("", "Othello");

            // Save the state of the board.
            serializer.startTag("", "Board");
            serializer.text(_othello.getBoard().toString());
            serializer.endTag("", "Board");

            // Save the size of the board.
            serializer.startTag("", "Size");
            serializer.text(String.valueOf(_othello.getBoardSize()));
            serializer.endTag("", "Size");

            // Save the number of the actual player.
            serializer.startTag("", "PlayerActual");
            serializer.text(String.valueOf(_actual_player.getNumber()));
            serializer.endTag("", "PlayerActual");

            // If there is an AI
            if (_player2.isAI())
            {
                // Save that there is an AI.
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

    /**
     * Create a controller from the XML file.
     *
     * @param input The xml file containing all the data to create the controller.
     * @param view The view on which the controller operate.
     * @return A nex OthelloController created with the data given.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static OthelloController fromXML(InputStream input, GameActivity view) throws XmlPullParserException, IOException
    {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(input, "UTF-8");

        int eventType = parser.getEventType();

        OthelloController oc = new OthelloController(view);

        // Default value in case there is a problem.
        int boardSize = 2;
        String data = "1221";
        int actualPlayer = 1;
        boolean AI = false;

        // While we are not at the end of the XML.
        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            String tag;

            switch (eventType)
            {
                // Beginning of a tag.
                case XmlPullParser.START_TAG :
                    tag = parser.getName();
                    if (tag.equals("Board"))
                    {
                        // Read the data for the board.
                        data = parser.nextText();
                    }
                    else if (tag.equals("Size"))
                    {
                        // Read the data for the size of the board.
                        boardSize = Integer.parseInt(parser.nextText());
                    }
                    else if (tag.equals("PlayerActual"))
                    {
                        // Read the data for the actual player.
                        actualPlayer = Integer.parseInt(parser.nextText());
                    }
                    else if (tag.equals("AI"))
                    {
                        // Read the data for the AI.
                        AI = true;
                    }
                    break;
            }

            eventType = parser.next();
        }

        // Create a new Othello from the data read
        oc._othello = Othello.fromString(data, boardSize, oc);

        // Create the human player 1.
        oc._player1 = new PlayerHuman(oc._othello, 1);

        // If there is an AI.
        if (AI)
        {
            //Create the player 2 as an AI.
            oc._player2 = new PlayerAI(oc._othello, 2, 3);
        }
        else
        {
            // Create the player 2 as a human.
            oc._player2 = new PlayerHuman(oc._othello, 2);
        }

        // Set the actual player.
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
