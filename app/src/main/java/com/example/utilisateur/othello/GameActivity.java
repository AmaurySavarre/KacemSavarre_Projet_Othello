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
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import controller.OthelloController;
import model.Board;
import model.Move;
import model.Othello;
import model.Player;

public class GameActivity extends AppCompatActivity
{
    private TableLayout _table;
    private CaseButton[][] btns;

    private TextView _scorePlayer1;
    private TextView _scorePlayer2;

    private ImageView _turn;

    private Drawable _emptyCase;
    private Drawable _player1Case;
    private Drawable _player2Case;
    private Drawable _player1Disk;
    private Drawable _player2Disk;
    private Drawable _possibleCase;

    private OthelloController _controller;

    private boolean _scoresUpdated;
    private boolean _turnUpdated;
    private boolean _boardUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        _scorePlayer1 = (TextView) findViewById(R.id.Game_TextView_s1);
        _scorePlayer2 = (TextView) findViewById(R.id.Game_TextView_s2);

        _turn = (ImageView) findViewById(R.id.Game_ImageView_turn);

        _table = (TableLayout) findViewById(R.id.Game_TableLayout_board);

        try
        {
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
            String dir = getFilesDir().getAbsolutePath();
            File file = new File(dir, "save.data");

            if (file.exists())
            {
                FileInputStream fis = new FileInputStream(file);

                _controller = OthelloController.fromXML(fis, this);

                Log.d("OnCreate", "Xml loaded");
            }
            else
            {
                _controller = new OthelloController(this, 8);
                Log.d("OnCreate", "No xml new game");
            }
        }
        catch (Exception e)
        {
            Log.e("onCreate", e.getMessage());
        }

        createButtons();

        _controller.initializeGame();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        _controller.stop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        _controller.start();
    }



    @Override
    protected void onStop()
    {
        super.onStop();

        try
        {

            String data = _controller.toXML();

            if(data != null)
            {
                FileOutputStream fos = openFileOutput("save.data", Context.MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
            }
            else
            {
                String dir = getFilesDir().getAbsolutePath();
                File file = new File(dir, "save.data");
                file.delete();
            }

        }
        catch (FileNotFoundException e)
        {
            Log.e("onDestroy", e.getMessage());
        }
        catch (IOException e)
        {
            Log.e("onDestroy", e.getMessage());
        }
    }


    private void createButtons()
    {
        int n = _controller.getBoardSize();

        _table.removeAllViews();
        _table.setStretchAllColumns(true);

        btns = new CaseButton[n][n];
        for(int y=0; y<n; y++){

            TableRow row = new TableRow(this);
            TableRow.LayoutParams paramsBtn = new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT);

            for (int x=0; x<n; x++)
            {
                CaseButton btn = _controller.createButton(this, x, y);//new CaseButton(this, _controller.getCase(x, y));
                btns[y][x] = btn;
                btn.setPadding(0,0,0,0);
                btn.setId(y * n + x);
                btn.setLayoutParams(paramsBtn);
                row.addView(btn);
                btn.setOnClickListener(_controller.getListener());

            }

            _table.addView(row);
            _table.setColumnShrinkable(y, true);
        }
        setButtons(); //initialiser les images
    }

    //initialiser les images avec l'image vide
    public void setButtons()
    {
        int n = _controller.getBoardSize();

        for (int y=0; y<n; y++)
        {
            for (int x=0; x<n; x++)
            {
                (btns[y][x]).setBackground(_emptyCase);
            }
        }
    }

    public void updateScores(int scorePlayer1, int scorePlayer2)
    {
        _scoresUpdated = false;

        _scorePlayer1.setText(String.valueOf(scorePlayer1));
        _scorePlayer2.setText(String.valueOf(scorePlayer2));

        _scoresUpdated = true;
    }

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

    public void updateButton(final Board board)
    {
        _boardUpdated = false;

        for (int x = 0 ; x < board.getSize() ; ++x)
        {
            for (int y = 0 ; y < board.getSize() ; ++y)
            {
                switch (board.getXY(x, y).getState())
                {
                    case PLAYER1:
                        (btns[y][x]).setBackground(_player1Case);
                        break;
                    case PLAYER2:
                        (btns[y][x]).setBackground(_player2Case);
                        break;
                    default:
                        (btns[y][x]).setBackground(_emptyCase);
                        break;
                }
            }
        }

        _boardUpdated = true;
    }

    public void showPlayerMoves(List<Move> moves)
    {
        for(Move move : moves)
        {
            btns[move.getY()][move.getX()].setBackground(_possibleCase);
        }
    }

    public boolean updated()
    {
        return (_boardUpdated && _scoresUpdated && _turnUpdated);
    }

    public void onSettings(View v)
    {
        Toast.makeText(getApplicationContext(), "onSettings()", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(GameActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onHome(View v)
    {
        finish();
    }

}
