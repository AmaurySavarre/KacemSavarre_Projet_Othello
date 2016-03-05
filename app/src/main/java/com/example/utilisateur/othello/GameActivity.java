package com.example.utilisateur.othello;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import controller.OthelloController;

public class GameActivity extends AppCompatActivity {
    private LinearLayout grid_layout;
    private TableLayout table;
    private int n=8;
    private ImageButton[][] btns;

    private OthelloController _controller;

    @Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);}*/

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        _controller = new OthelloController(this, 8);

        table = (TableLayout) findViewById(R.id.Game_TableLayout_board);
        createButtons();

    }


    private void createButtons()
    {
        table.removeAllViews();
        table.setStretchAllColumns(true);

        btns = new ImageButton[n][n];
        for(int i=0; i<n; i++){

            TableRow row = new TableRow(this);
            TableRow.LayoutParams paramsBtn = new TableRow.LayoutParams(1, TableRow.LayoutParams.WRAP_CONTENT);

        for (int j=0; j<n; j++){
                ImageButton btn = new ImageButton(this){
                    @Override
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
                    {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

                        final int height = getMeasuredHeight();
                        final int width = getMeasuredWidth();

                        setMeasuredDimension(width, width);
                    }
                };
                btns[i][j] = btn;
                btn.setPadding(2,2,2,2);
                btn.setId(i * n + j);
                btn.setLayoutParams(paramsBtn);
                row.addView(btn);
                btn.setOnClickListener(_controller.getListener());

            }
            table.addView(row);
            table.setColumnShrinkable(i, true);
        }
        setButtons(); //initialiser les images
    }

    //initialiser les images avec l'image vide
    public void setButtons(){
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                (btns[i][j]).setBackgroundResource(R.drawable.black_disk);
            }
        }
    }
}

// TODO: 3/2/2016 Changer les ImageButton en Button ou trouver comment ajouter l'image du disk du joueur.
// TODO: 3/2/2016 Corriger l'affichage des boutons pour qu'ils aient tous la même taille. 
