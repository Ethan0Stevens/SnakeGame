package com.example.snakegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomGameActivity  extends AppCompatActivity {
    SeekBar fruitsSeekerBar;
    SeekBar accelerationSeekerBar;
    TextView nbFruitsText;
    TextView accelerationValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        fruitsSeekerBar = findViewById(R.id.maxFruitBar);
        accelerationSeekerBar = findViewById(R.id.accelerationBar);
        nbFruitsText = findViewById(R.id.nbFruitsText);
        accelerationValueText = findViewById(R.id.accelerationValueText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fruitsSeekerBar.setOnSeekBarChangeListener(new FruitSeekerListner());
        accelerationSeekerBar.setOnSeekBarChangeListener(new AccelerationSeekerListner());
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("totalFruitsSpawn", fruitsSeekerBar.getProgress());
        intent.putExtra("snakeSpeed", accelerationSeekerBar.getProgress());
        startActivity(intent);
    }

    private class FruitSeekerListner implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            nbFruitsText.setText(String.valueOf(progress));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }

    private class AccelerationSeekerListner implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text

            if (progress > seekBar.getMax()/2 + 2) {
                if (progress == seekBar.getMax())
                    accelerationValueText.setText("Très Faible");
                accelerationValueText.setText("Faible");
            } else if (progress < seekBar.getMax()/2 - 2) {
                if (progress == 0)
                    accelerationValueText.setText("Très Forte");
                accelerationValueText.setText("Forte");
            } else {
                accelerationValueText.setText("Moyenne");
            }

        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}
