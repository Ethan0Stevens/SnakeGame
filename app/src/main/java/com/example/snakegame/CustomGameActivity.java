package com.example.snakegame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomGameActivity  extends AppCompatActivity {
    SeekBar fruitsSeekerBar;
    static TextView nbFruitsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


        fruitsSeekerBar = findViewById(R.id.maxFruitBar);
        nbFruitsText = findViewById(R.id.nbFruitsText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fruitsSeekerBar.setOnSeekBarChangeListener(new SeekerListner());
    }

    private static class SeekerListner implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            nbFruitsText.setText(String.valueOf(progress));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}
