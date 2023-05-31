package com.example.snakegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomGameActivity  extends AppCompatActivity {
    // Déclaration des variables
    SeekBar fruitsSeekerBar;
    SeekBar accelerationSeekerBar;
    TextView nbFruitsText;
    TextView accelerationValueText;

    /**
     * Code executé a la cration de l'activité
     */
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

    /**
     * Change d'activité pour la GameActivity
     * en donnant en parametre le nombre de fruit max et la vitesse du serpent
     */
    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("totalFruitsSpawn", fruitsSeekerBar.getProgress());
        intent.putExtra("snakeSpeed", accelerationSeekerBar.getProgress());
        startActivity(intent);
    }

    /**
     * Listener de la bar du nombre de fruit max
     */
    private class FruitSeekerListner implements SeekBar.OnSeekBarChangeListener {

        /**
         * A chaque fois que la valeur de la bar change
         */
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
            nbFruitsText.setText(String.valueOf(progress));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }

    /**
     * Listener de la bar de la vitesse du serpent
     */
    private class AccelerationSeekerListner implements SeekBar.OnSeekBarChangeListener {

        /**
         * A chaque fois que la valeur de la bar change
         */
        @SuppressLint("SetTextI18n")
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text

            if (progress > seekBar.getMax()/2 + 2) {
                accelerationValueText.setText("Faible");
            } else if (progress < seekBar.getMax()/2 - 2) {
                accelerationValueText.setText("Forte");
            } else {
                accelerationValueText.setText("Moyenne");
            }

            if (progress == seekBar.getMax()) {
                accelerationValueText.setText("Tres Faible");
            } else if (progress == 0) {
                accelerationValueText.setText("Très Forte");
            }

        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}
