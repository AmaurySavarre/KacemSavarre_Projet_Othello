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


}
