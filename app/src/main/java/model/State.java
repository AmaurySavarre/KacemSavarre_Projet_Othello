package model;

/**
 * Created by Amaury Savarre on 12/03/2016.
 *
 * Enumeration Class that hold the different states of a case.
 */
public enum State {
    EMPTY   (0),    // Empty state : the case is empty.
    PLAYER1 (1),    // Player 1 state : the case is occupied by the player 1.
    PLAYER2 (2);    // Player 2 state : the case is occupied by the player 2.

    private final int _state;

    /**
     * State Constructor.
     *
     * @param state The value of the state.
     */
    State(int state)
    {
        _state = state;
    }

    /**
     * Gives the state associated to the value.
     *
     * @param state The value of the desired state.
     * @return The state associated to the value.
     */
    public static State getState(int state)
    {
        switch (state)
        {
            case 0:
                return EMPTY;
            case 1:
                return PLAYER1;
            case 2:
                return PLAYER2;
            default:
                return null;
        }
    }
}
