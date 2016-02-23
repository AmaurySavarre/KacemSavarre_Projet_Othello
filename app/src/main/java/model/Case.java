package model;

/**
 * Created by Utilisateur on 2/23/2016.
 */
public class Case
{
    private State _state;

    public Case()
    {
        _state = StateEmpty.getInstance();
    }

    public void reverse(int player)
    {
        _state.changeState(player);
    }

    public boolean isEmpty()
    {
        if(_state == StateEmpty.getInstance())
        {
            return true;
        }

        return false;
    }
}
