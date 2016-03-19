package com.example.utilisateur.othello;

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

import java.util.List;

import controller.OthelloController;
import model.Board;
import model.Move;
import model.Player;
import model.State;

public class GameActivity extends AppCompatActivity
{
    private TableLayout table;
    private CaseButton[][] btns;

    private TextView _scorePlayer1;
    private TextView _scorePlayer2;

    private ImageView _turn;

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

        table = (TableLayout) findViewById(R.id.Game_TableLayout_board);

        _scoresUpdated = true;
        _turnUpdated = true;
        _boardUpdated = true;

        _controller = new OthelloController(this, 8);

        createButtons();

        _controller.initializeGame();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        _controller.stopGame();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        _controller.start();
    }


    private void createButtons()
    {
        int n = _controller.getBoardSize();

        table.removeAllViews();
        table.setStretchAllColumns(true);

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

            table.addView(row);
            table.setColumnShrinkable(y, true);
        }
        setButtons(); //initialiser les images
    }

    //initialiser les images avec l'image vide
    public void setButtons()
    {
        int n = _controller.getBoardSize();

        try
        {
            Resources res = getResources();

            for (int y=0; y<n; y++)
            {
                for (int x=0; x<n; x++)
                {
                    (btns[y][x]).setBackground(Drawable.createFromXml(res, res.getXml(R.xml.case_empty_shape)));
                }
            }
        }
        catch (Exception e)
        {
            Log.e("setButtons", e.getMessage());
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

        try
        {
            Resources res = getResources();
            Drawable p1 = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));
            Drawable p2 = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));
            p1.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

            switch (player.getNumber())
            {
                case 1 :
                    _turn.setImageDrawable(p1);
                    break;
                case 2:
                    _turn.setImageDrawable(p2);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e)
        {
            Log.e("update", e.getMessage());
        }

        _turnUpdated = true;
    }

    public void updateButton(final Board board)
    {
        _boardUpdated = false;

        try
        {
            Resources res = getResources();
            Drawable player1 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            Drawable player2 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            Drawable empty = Drawable.createFromXml(res, res.getXml(R.xml.case_empty_shape));
            player1.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

            for (int x = 0 ; x < board.getSize() ; ++x)
            {
                for (int y = 0 ; y < board.getSize() ; ++y)
                {
                    switch (board.getXY(x, y).getState())
                    {
                        case PLAYER1:
                            (btns[y][x]).setBackground(player1);
                            break;
                        case PLAYER2:
                            (btns[y][x]).setBackground(player2);
                            break;
                        default:
                            (btns[y][x]).setBackground(empty);
                            break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.e("update", e.getMessage());
        }

        _boardUpdated = true;
    }

    public void showPlayerMoves(List<Move> moves)
    {
        try
        {
            Resources res = getResources();
            Drawable drawable = Drawable.createFromXml(res, res.getXml(R.xml.case_possible_move));

            for(Move move : moves)
            {
                btns[move.getY()][move.getX()].setBackground(drawable);
            }

        }
        catch (Exception e)
        {
            Log.e("update", e.getMessage());
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
}
