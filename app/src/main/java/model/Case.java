package model;

/**
 * Created by Amaury on 2/23/2016.
 */
public class Case
{
    //private State _state;
    private int _state;

    public int getState()
    {
        return _state;
    }

    public Case()
    {
        //_state = StateEmpty.getInstance();
        _state = 0;
    }

    public void reverse(int player)
    {
        //_state.changeState(player);
        _state = player;
    }

    public boolean isEmpty()
    {
        return (_state == 0);//(_state == StateEmpty.getInstance());
    }

    public String toString()
    {
        return Integer.toString(_state);
    }
}
