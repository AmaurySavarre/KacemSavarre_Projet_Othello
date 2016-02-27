package model;

/**
 * Created by Amaury on 2/23/2016.
 */
public class StatePlayer2 implements State
{
    private static StatePlayer2 _instance = new StatePlayer2();

    public static StatePlayer2 getInstance()
    {
        return _instance;
    }

    private StatePlayer2()
    {
    }

    public State changeState(int player)
    {
        return StatePlayer1.getInstance();
    }
}
