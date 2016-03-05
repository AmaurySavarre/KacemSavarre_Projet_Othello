package com.example.utilisateur.othello;

import android.content.Context;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Amaury Savarre on 3/5/2016.
 */
public class CaseButton extends Button implements Observer
{
    public CaseButton(Context context)
    {
        super(context);
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

    }
}
