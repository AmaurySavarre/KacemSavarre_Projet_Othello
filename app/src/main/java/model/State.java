package model;

/**
 * Created by Amaury on 12/03/2016.
 */
public enum State {
    EMPTY   (0),
    PLAYER1 (1),
    PLAYER2 (2);

    private final int _state;

    State(int state)
    {
        _state = state;
    }

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
