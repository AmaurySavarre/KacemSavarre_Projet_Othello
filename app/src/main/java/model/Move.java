package model;

/**
 * Created by Amaury Savarre on 05/03/2016.
 *
 * Class to create and manage a Move.
 */
public class Move
{
    private int _x; // The x coordinate of the move.
    private int _y; // The y coordinate of the move.

    /**
     * Move constructor.
     *
     * @param x The x coordinate of the move.
     * @param y The y coordinate of the move.
     */
    public Move(int x, int y)
    {
        _x = x;
        _y = y;
    }

    /**
     * Give the x coordinate of the move.
     *
     * @return The x coordinate of the move.
     */
    public int getX()
    {
        return _x;
    }

    /**
     * Give the y coordinate of the move.
     *
     * @return The y coordinate of the move.
     */
    public int getY()
    {
        return _y;
    }

    public String toString()
    {
        return "(" + _x + ", " + _y + ")";
    }
}
