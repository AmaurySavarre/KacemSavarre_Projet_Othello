package model;

/**
 * Created by Amaury Savarre on 05/03/2016.
 */
public class Move
{
    private int _x;
    private int _y;

    public Move(int x, int y)
    {
        _x = x;
        _y = y;
    }

    public int getX()
    {
        return _x;
    }

    public int getY()
    {
        return _y;
    }
}
