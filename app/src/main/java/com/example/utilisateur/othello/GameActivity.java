package com.example.utilisateur.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameActivity extends AppCompatActivity {
    private LinearLayout grid_layout;
    private TableLayout table;
    private int n=8;
    private ImageButton[][] btns;

    @Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);}*/

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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
            TableRow.LayoutParams paramsBtn = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1);

            for (int j=0; j<n; j++){
                ImageButton btn = new ImageButton(this);
                btns[i][j] = btn;
                btn.setPadding(0,0,0,0);
                btn.setId(i * n + j);
                btn.setLayoutParams(paramsBtn);
                row.addView(btn);
                /*btn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        int id = view.getId();
                        int i = id/n;
                        int j = id - n*i;

                        //Prévenir les clicks quand le joueur adverse joue
                        if(enAttente.compareAndSet(false, true)){
                           turn(i,j);
                        }
                    }
                });*/

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
