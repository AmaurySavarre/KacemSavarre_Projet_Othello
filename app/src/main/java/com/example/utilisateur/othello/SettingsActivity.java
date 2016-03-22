package com.example.utilisateur.othello;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import model.Language;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinner;
    String[] languages_string = { "English",
                                  "Français" };

    int languages_icon[] = { R.drawable.us,
                             R.drawable.fr };

    Language[] _languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        _languages = new Language[2];

        _languages[0] = new Language(Locale.ENGLISH, "English", R.drawable.us);
        _languages[1] = new Language(Locale.FRANCE, "Français", R.drawable.fr);

        spinner = (Spinner) findViewById(R.id.Settings_Spinner_language);
        spinner.setAdapter(new LanguagesArrayAdapter(SettingsActivity.this, R.layout.row, languages_string));
    }

    public class LanguagesArrayAdapter extends ArrayAdapter<String> {

        public LanguagesArrayAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.Row_TextView_name);
            ImageView icon = (ImageView) row.findViewById(R.id.Row_ImageView_icon);


            label.setText(_languages[position].getText());
            icon.setImageResource(_languages[position].getFlag());

            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            row.setBackground(getDrawable(R.drawable.spinner_shape));
            TextView label = (TextView) row.findViewById(R.id.Row_TextView_name);
            ImageView icon = (ImageView) row.findViewById(R.id.Row_ImageView_icon);


            label.setText(_languages[position].getText());
            icon.setImageResource(_languages[position].getFlag());

            return row;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.Row_TextView_name);
            ImageView icon = (ImageView) row.findViewById(R.id.Row_ImageView_icon);


            label.setText(_languages[position].getText());
            icon.setImageResource(_languages[position].getFlag());

            return row;
        }
    }

    public void onBack(View v)
    {
        finish();
    }

    public void setLocale(Locale locale)
    {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SettingsActivity.this.getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void onSave(View v)
    {
        Log.e("onSave()", _languages[spinner.getSelectedItemPosition()].getText());

        setLocale(_languages[spinner.getSelectedItemPosition()].getLocale());
        recreate();
    }
}
