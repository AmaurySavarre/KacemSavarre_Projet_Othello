package com.example.utilisateur.othello;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinner;
    String[] languages_string = { "English",
                                  "Français" };

    int languages_icon[] = { R.drawable.us,
                             R.drawable.fr };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = (Spinner) findViewById(R.id.Settings_Spinner_language);
        spinner.setAdapter(new LanguagesArrayAdapter(SettingsActivity.this, R.layout.row, languages_string));
    }

    public class LanguagesArrayAdapter extends ArrayAdapter<String> {

        public LanguagesArrayAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.Row_TextView_name);
            ImageView icon = (ImageView) row.findViewById(R.id.Row_ImageView_icon);


            label.setText(languages_string[position]);
            icon.setImageResource(languages_icon[position]);

            return row;
        }
    }
}
