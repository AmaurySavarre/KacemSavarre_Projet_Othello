package model;

/**
 * Created by Amaury on 20/03/2016.
 */
public class Language
{
    private String _code;
    private String _text;
    private int _flag;

    public Language(String code, String name, int flag)
    {
        _code = code;
        _text = name;
        _flag = flag;
    }

    public String getCode()
    {
        return _code;
    }

    public String getText()
    {
        return _text;
    }

    public int getFlag()
    {
        return _flag;
    }
}
