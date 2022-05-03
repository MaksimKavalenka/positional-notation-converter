package com.MaksimKavalenka.PositionalNotationConverter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

public class AboutActivity extends Activity {

    private String[] string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        string = getIntent().getStringArrayExtra("strings");

        TextView about = (TextView)findViewById(R.id.textAbout);
        TextView input = (TextView)findViewById(R.id.textInput);
        TextView system = (TextView)findViewById(R.id.textSystem);
        TextView mode = (TextView)findViewById(R.id.textMode);
        TextView language = (TextView)findViewById(R.id.textLanguage);

        about.setText(string[0]);
        input.setText(string[1]);
        system.setText(string[2]);
        mode.setText(string[3]);
        language.setText(string[4]);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArray("string", string);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle restoredInstanceState) {
        super.onRestoreInstanceState(restoredInstanceState);
        string = restoredInstanceState.getStringArray("string");
    }
}