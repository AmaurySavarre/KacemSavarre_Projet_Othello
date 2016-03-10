package com.example.utilisateur.othello;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import controller.OthelloController;
import model.Case;
import model.Player;

public class GameActivity extends AppCompatActivity {
    private LinearLayout grid_layout;
    private TableLayout table;
    private int n=8;
    private CaseButton[][] btns;

    private TextView _scorePlayer1;
    private TextView _scorePlayer2;
    //private ImageView _imagePlayer1;
    //private ImageView _imagePlayer2;

    private ImageView _turn;

    private OthelloController _controller;

    @Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);}*/

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        _scorePlayer1 = (TextView) findViewById(R.id.Game_TextView_s1);
        _scorePlayer2 = (TextView) findViewById(R.id.Game_TextView_s2);
        //_imagePlayer1 = (ImageView) findViewById(R.id.Game_ImageView_player1);
        //_imagePlayer2 = (ImageView) findViewById(R.id.Game_ImageView_player2);

        _turn = (ImageView) findViewById(R.id.Game_ImageView_turn);

        table = (TableLayout) findViewById(R.id.Game_TableLayout_board);

        _controller = new OthelloController(this, 8);

        createButtons();

        _controller.initializeGame();
    }


    private void createButtons()
    {
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
    public void setButtons(){
        try
        {
            Resources res = getResources();

            for (int y=0; y<n; y++)
            {
                for (int x=0; x<n; x++)
                {
                    (btns[y][x]).setBackground(Drawable.createFromXml(res, res.getXml(R.xml.case_empty_shape)));
                    //(btns[i][j]).setBackgroundResource(R.drawable.black_disk);
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
        _scorePlayer1.setText(String.valueOf(scorePlayer1));
        _scorePlayer2.setText(String.valueOf(scorePlayer2));
    }

    public void setTurn(Player player)
    {
        try
        {
            Resources res = getResources();
            Drawable p1 = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));
            Drawable p2 = Drawable.createFromXml(res, res.getXml(R.xml.disc_shape));
            p2.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

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
    }

    public void setImagePlayer(Player player1, Player player2)
    {
        try
        {
            Resources res = getResources();
            Drawable p1 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            Drawable p2 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape));
            p2.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

            //_imagePlayer1.setImageDrawable(p1);
            //_imagePlayer2.setImageDrawable(p2);
        }
        catch (Exception e)
        {
            Log.e("update", e.getMessage());
        }
    }
}

// TODO: 3/2/2016 Changer les ImageButton en Button ou trouver comment ajouter l'image du disk du joueur.
