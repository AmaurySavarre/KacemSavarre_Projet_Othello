package com.example.utilisateur.othello;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import controller.OthelloController;
import model.Board;
import model.Move;
import model.Player;

public class GameActivity extends AppCompatActivity
{
    private TableLayout _table;             // TableLayout containing the button of the board.
    private CaseButton[][] _btns;           // Matrix containing the CaseButtons.

    private TextView _scorePlayer1;         // TextView for the score of the Player 1.
    private TextView _scorePlayer2;         // TextView for the score of the Player 2.

    private ImageView _turn;                // ImageView to show which player is currently playing.

    private Drawable _emptyCase;            // Drawable of an empty case.
    private Drawable _player1Case;          // Drawable of a case occupied by the player 1.
    private Drawable _player2Case;          // Drawable of a case occupied by the player 2.
    private Drawable _player1Disk;          // Drawable of the player's 1 disk.
    private Drawable _player2Disk;          // Drawable of the player's 2 disk.
    private Drawable _possibleCase;         // Drawable of an available move.

    private OthelloController _controller;  // The controller of the game.

    private boolean _scoresUpdated;         // Boolean to say if the scores have been updated.
    private boolean _turnUpdated;           // Boolean to say if the turn has been updated.
    private boolean _boardUpdated;          // Boolean to say if the board has been updated.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try
        {
            // Get the different Drawable.
            Resources res = getResources();
            _player1Disk = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));
            _player1Disk.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
            _player2Disk = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));

            _player1Case = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            _player2Case = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            _player1Case.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

            _emptyCase = Drawable.createFromXml(res, res.getXml(R.xml.case_empty_shape));

            _possibleCase = Drawable.createFromXml(res, res.getXml(R.xml.case_possible_move));

        }
        catch (Exception e)
        {
            Log.e("onCreate", e.getMessage());
        }

        _scoresUpdated = true;
        _turnUpdated = true;
        _boardUpdated = true;

        try
        {
            // Load a save or create a new game.
            String dir = getFilesDir().getAbsolutePath();
            File file = new File(dir, "save.data");

            // If the file exists.
            if (file.exists())
            {
                // Get an input stream.
                FileInputStream fis = new FileInputStream(file);

                // Create a new controller from the XML loaded.
                _controller = OthelloController.fromXML(fis, this);
            }
            // The file does not exist.
            else
            {
                // Create a new game.
                _controller = new OthelloController(this, 8);
            }
        }
        catch (Exception e)
        {
            Log.e("onCreate", e.getMessage());
        }

        initView();

        // Ask the controller to initialize the game and the view.
        _controller.initializeGame();
    }

    private void initView()
    {
        _scorePlayer1 = (TextView) findViewById(R.id.Game_TextView_s1);
        _scorePlayer2 = (TextView) findViewById(R.id.Game_TextView_s2);

        _turn = (ImageView) findViewById(R.id.Game_ImageView_turn);

        _table = (TableLayout) findViewById(R.id.Game_TableLayout_board);

        // Create the buttons.
        createButtons();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Stop the controller.
        _controller.stop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Start the controller.
        _controller.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        try
        {
            // Convert the controller in a XML file.
            String data = _controller.toXML();

            // If it can be done.
            if(data != null)
            {
                // Save the XML in a file.
                FileOutputStream fos = openFileOutput("save.data", Context.MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
            }
            else
            {
                // The game is over so we delete the save.
                String dir = getFilesDir().getAbsolutePath();
                File file = new File(dir, "save.data");
                file.delete();
            }

        }
        catch (Exception e)
        {
            Log.e("onDestroy", e.getMessage());
        }
    }


    /**
     * Create the buttons to compose the board.
     */
    private void createButtons()
    {
        int n = _controller.getBoardSize();

        _table.removeAllViews();
        _table.setStretchAllColumns(true);

        // Create the matrix.
        _btns = new CaseButton[n][n];

        for(int y=0; y<n; y++)
        {
            // Create a new TableRow.
            TableRow row = new TableRow(this);
            TableRow.LayoutParams paramsBtn = new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT);

            for (int x=0; x<n; x++)
            {
                // Create a new CaseButton.
                CaseButton btn = _controller.createButton(this, x, y);
                _btns[y][x] = btn;
                btn.setPadding(0,0,0,0);        // Set no padding to the button.
                btn.setId(y * n + x);           // Set the id of the button.
                btn.setLayoutParams(paramsBtn); // Set the parameters of the button.
                row.addView(btn);               // Add the button to the row.

                // Set the button listener.
                btn.setOnClickListener(_controller.getListener());

            }

            // Add the row to the table.
            _table.addView(row);
            _table.setColumnShrinkable(y, true);
        }

        // Initialize the images of the buttons.
        setButtons();
    }

    /**
     * Initializes the images of the buttons.
     */
    public void setButtons()
    {
        int n = _controller.getBoardSize();

        for (int y=0; y<n; y++)
        {
            for (int x=0; x<n; x++)
            {
                // Set all the buttons with the empty case drawable.
                (_btns[y][x]).setBackground(_emptyCase);
            }
        }
    }

    /**
     * Updates the score of the players.
     *
     * @param scorePlayer1 Score of the player 1.
     * @param scorePlayer2 Score of the player 2.
     */
    public void updateScores(int scorePlayer1, int scorePlayer2)
    {
        _scoresUpdated = false;

        _scorePlayer1.setText(String.valueOf(scorePlayer1));
        _scorePlayer2.setText(String.valueOf(scorePlayer2));

        _scoresUpdated = true;
    }

    /**
     * Updates the the turn of the actual player.
     *
     * @param player The player turn.
     */
    public void updateTurn(Player player)
    {
        _turnUpdated = false;

        switch (player.getNumber())
        {
            case 1 :
                _turn.setImageDrawable(_player1Disk);
                break;
            case 2:
                _turn.setImageDrawable(_player2Disk);
                break;
            default:
                break;
        }

        _turnUpdated = true;
    }

    /**
     * Updates the buttons with the board.
     *
     * @param board Board on which the interface is based on.
     */
    public void updateButton(final Board board)
    {
        _boardUpdated = false;

        // Scan the board.
        for (int x = 0 ; x < board.getSize() ; ++x)
        {
            for (int y = 0 ; y < board.getSize() ; ++y)
            {
                // Set the appropriate background depending on the state of the case.
                switch (board.getXY(x, y).getState())
                {
                    case PLAYER1:
                        (_btns[y][x]).setBackground(_player1Case);
                        break;
                    case PLAYER2:
                        (_btns[y][x]).setBackground(_player2Case);
                        break;
                    default:
                        (_btns[y][x]).setBackground(_emptyCase);
                        break;
                }
            }
        }

        _boardUpdated = true;
    }

    /**
     * Show the available moves to the player on the board.
     *
     * @param moves List of moves available to the player.
     */
    public void showPlayerMoves(List<Move> moves)
    {
        for(Move move : moves)
        {
            _btns[move.getY()][move.getX()].setBackground(_possibleCase);
        }
    }

    /**
     * Show the game over screen.
     */
    public void showGameOver()
    {
        setContentView(R.layout.activity_gameover);

        Resources res = getResources();

        TextView winner = (TextView) findViewById(R.id.Game_TextView_winner);
        TextView scorePlayer1 = (TextView) findViewById(R.id.Game_TextView_s1);
        TextView scorePlayer2 = (TextView) findViewById(R.id.Game_TextView_s2);

        Player player = _controller.getWinner();

        if (player != null)
        {
            winner.setText(res.getString(R.string.Game_winner, player.toString()));
        }
        else
        {
            winner.setText(res.getString(R.string.Game_draw));
        }

        scorePlayer1.setText(_scorePlayer1.getText());
        scorePlayer2.setText(_scorePlayer2.getText());
    }

    /**
     * Says if the interface is updated.
     *
     * @return True if the game is updated, else false.
     */
    public boolean updated()
    {
        return (_boardUpdated && _scoresUpdated && _turnUpdated);
    }

    /**
     * Action on the settings button.
     *
     * @param v View of the button.
     */
    public void onSettings(View v)
    {
        Toast.makeText(getApplicationContext(), "onSettings()", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(GameActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Action on the home button.
     *
     * @param v View of the button.
     */
    public void onHome(View v)
    {
        finish();
    }

    public void onRestart(View v) {
        setContentView(R.layout.activity_game);
        initView();
        _controller.resetGame();
    }

    public void onQuit(View v)
    {
        finish();
    }
}
