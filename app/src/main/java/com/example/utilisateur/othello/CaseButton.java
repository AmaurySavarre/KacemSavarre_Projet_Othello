package com.example.utilisateur.othello;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import model.Case;

/**
 * Created by Amaury Savarre on 3/5/2016.
 */
public class CaseButton extends Button implements Observer
{
    private Case _case;

    public CaseButton(Context context, Case c)
    {
        super(context);
        _case = c;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //final int height = getMeasuredHeight();
        final int width = getMeasuredWidth();

        setMeasuredDimension(width, width);
    }

    @Override
    public void update(Observable observable, Object data)
    {
        Log.d("update", "IN");
        if(observable == _case)
        {
            Log.d("update", "IN2");
            try
            {
                Resources res = getResources();
                Drawable player1 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape1));
                Drawable player2 = Drawable.createFromXml(res, res.getXml(R.xml.case_full_shape2));
                player2.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

                switch ((Case.State) data)
                {
                    case PLAYER1:
                        setBackground(player1);
                        break;
                    case PLAYER2:
                        setBackground(player2);
                        break;
                    default:
                        setBackground(player1);
                        break;
                }
            }
            catch (Exception e)
            {
                Log.e("update", e.getMessage());
            }
        }
    }
}
