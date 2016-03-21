package model;


/**
 * Created by Amaury Savarre on 2/23/2016.
 *
 * Class to create and manage Case.
 */
public class Case
{
    private State _state;     // State of the case.

    /**
     * Returns the actual state of the case.
     *
     * @return the actual state of the case.
     */
    public State getState()
    {
        return _state;
    }

    /**
     * Case Constructor.
     */
    public Case()
    {
        _state = State.EMPTY;
    }

    /**
     * Case Constructor.
     *
     * @param state The initial state of the Case.
     */
    public Case(State state)
    {
        _state = state;
    }

    /**
     * Case copy Constructor.
     *
     * @param c The case to copy.
     */
    public Case(Case c)
    {
        _state = c._state;
    }

    /**
     * Changes the state of the case depending one the player who plays.
     *
     * @param player The player who plays.
     */
    public void changeState(Player player)
    {
        _state = player.getAssociatedState();
    }

    /**
     * Checks if the case is empty.
     *
     * @return A boolean which validate if the case is empty (true) or not (false).
     */
    public boolean isEmpty()
    {
        return (_state == State.EMPTY);
    }

    /**
     * Copies the state of a Case.
     *
     * @param c The Case to copy.
     */
    public void copy(Case c)
    {
        this._state = c.getState();
    }

    public String toString()
    {
        switch (_state)
        {
            case EMPTY:
                return "0";
            case PLAYER1:
                return "1";
            case PLAYER2:
                return  "2";
            default:
                return "?";
        }
    }
}