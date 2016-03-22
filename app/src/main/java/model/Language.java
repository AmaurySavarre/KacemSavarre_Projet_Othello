package model;

import java.util.Locale;

/**
 * Created by Amaury on 20/03/2016.
 */
public class Language
{
    private Locale _locale;
    private String _text;
    private int _flag;

    public Language(Locale code, String name, int flag)
    {
        _locale = code;
        _text = name;
        _flag = flag;
    }

    public Locale getLocale()
    {
        return _locale;
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
