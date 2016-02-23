package model;

/**
 * Created by Utilisateur on 2/23/2016.
 */
public class StateEmpty implements State
{
    private static StateEmpty _instance = new StateEmpty();

    public static StateEmpty getInstance()
    {
        return _instance;
    }

    private StateEmpty() {
    }

    public State changeState(int player)
    {
        switch (player)
        {
            case 1 :
                return StatePlayer1.getInstance();
            case 2 :
                return StatePlayer2.getInstance();
            default:
                return getInstance();
        }
    }
}
