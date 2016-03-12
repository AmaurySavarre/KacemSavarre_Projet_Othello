package model;

import java.util.Observable;

/**
 * Created by Amaury Savarre on 2/23/2016.
 */
public class Case// extends Observable
{


    private State _state;     // State of the case.

    /**
     * Returns the actual state of the case.
     *
     * @return the actual state of the case.
     */
    public State getState()
    {
        /*switch (_state)
        {
            case EMPTY:
                return 0;
            case PLAYER1:
                return 1;
            case PLAYER2:
                return 2;
            default:
                return 0;
        }*/
        return _state;
    }

    /**
     * Case Constructor.
     */
    public Case()
    {
        //_state = StateEmpty.getInstance();
        _state = State.EMPTY;
    }

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
        /*switch (player)
        {
            case 1:
                _state = State.PLAYER1;
                break;
            case 2:
                _state = State.PLAYER2;
                break;
            default:
                _state = State.EMPTY;
                break;
        }*/
        _state = player.getAssociatedState();
        /*setChanged();
        notifyObservers(_state);*/

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