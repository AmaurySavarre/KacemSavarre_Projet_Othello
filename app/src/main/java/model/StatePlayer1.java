package model;

/**
 * Created by Amaury on 2/23/2016.
 */
public class StatePlayer1 implements State
{
    private static StatePlayer1 _instance = new StatePlayer1();

    public static StatePlayer1 getInstance()
    {
        return _instance;
    }

    private StatePlayer1() {
    }

    public State changeState(int player)
    {
        return StatePlayer2.getInstance();
    }
}
