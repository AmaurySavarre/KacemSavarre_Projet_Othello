package com.example.utilisateur.othello;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import model.Case;
import model.State;

/**
 * Created by Amaury Savarre on 3/5/2016.
 *
 * Create and manage CaseButton.
 */
public class CaseButton extends Button
{
    private Case _case;

    /**
     * CaseButton Constructor.
     *
     * @param context The context in which the button is created.
     * @param c The case corresponding to the button on the interface.
     */
    public CaseButton(Context context, Case c)
    {
        super(context);
        _case = c;
    }

    /**
     * Measure the view and its content to determine the measured width and the measured height.
     *
     * @param widthMeasureSpec Horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec Vertical space requirements as imposed by the parent.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //final int height = getMeasuredHeight();
        final int width = getMeasuredWidth();

        // Set the width to the two dimension so that the button is square.
        setMeasuredDimension(width, width);
    }
}
