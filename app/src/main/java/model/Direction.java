package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amaury on 27/02/2016.
 *
 * Class containing all the available and usable directions to play.
 */
public class Direction
{
    // Create all the possible directions.
    public static Direction NORTH = new Direction(0, -1);       // North direction
    public static Direction NORTH_EAST = new Direction(1, -1);  // North-East direction
    public static Direction NORTH_WEST = new Direction(-1, -1); // North-West direction
    public static Direction WEST = new Direction(-1, 0);        // West direction
    public static Direction EAST = new Direction(1, 0);         // East direction
    public static Direction SOUTH = new Direction(0, 1);        // South direction
    public static Direction SOUTH_EAST = new Direction(1, 1);   // South-East
    public static Direction SOUTH_WEST = new Direction(-1, 1);  // South-West

    /**
     * Returns a List of all the directions available.
     *
     * @return a List of all directions available.
     */
    public static List<Direction> getListDirections()
    {
        ArrayList<Direction> _list = new ArrayList<>();

        _list.add(NORTH);
        _list.add(NORTH_EAST);
        _list.add(EAST);
        _list.add(SOUTH_EAST);
        _list.add(SOUTH);
        _list.add(SOUTH_WEST);
        _list.add(WEST);
        _list.add(NORTH_WEST);

        return _list;
    }

    private int deltaX;
    private int deltaY;

    /**
     * Direction Constructor.
     *
     * @param deltaX the X difference for this direction.
     * @param deltaY the Y difference for this direction.
     */
    private Direction(int deltaX, int deltaY)
    {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Returns the X difference associated with the direction.
     *
     * @return the X difference associated with the direction.
     */
    public int getDeltaX()
    {
        return this.deltaX;
    }

    /**
     * Returns the Y difference associated with the direction.
     *
     * @return the Y difference associated with the direction.
     */
    public int getDeltaY()
    {
        return this.deltaY;
    }

    public String toString()
    {
        String res = new String();

        switch(this.deltaY)
        {
            case(-1) :
                res += "North";
                break;
            case(1) :
                res += "South";
                break;
            default :
                break;
        }

        switch(this.deltaX)
        {
            case(-1) :
                res += "West";
                break;
            case(1) :
                res += "East";
                break;
            default :
                break;
        }

        res += " (" + this.deltaX + "," + this.deltaY + ")";

        return res;
    }
}
