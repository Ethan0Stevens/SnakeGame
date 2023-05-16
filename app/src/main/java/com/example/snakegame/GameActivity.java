package com.example.snakegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView starImg;

    SensorManager sensorManager;
    Sensor gravity;

    /**
     * Code éxectué a la creation de l'application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialisation des varibles
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        starImg = findViewById(R.id.starImg);
    }

    /**
     * Code éxecuté régulierement lors du fonctionement de l'application
     */
    public void onResume() {
        super.onResume();

        // Appeler l'ecoute du capteur de gravité
        sensorManager.registerListener(sensorListener, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void moveSnake(float x, float y) {
        boolean moveRight = false;
        boolean moveLeft = false;
        boolean moveUp = false;
        boolean moveDown = false;

        moveUp = x > 0;
        moveDown = x < 0;
        moveRight = y < 0;
        moveLeft = y > 0;

        if (moveUp) {
            starImg.setY(starImg.getY() + 5);
        } else if (moveDown) {
            starImg.setY(starImg.getY() - 5);
        }
    }

    /**
     * Ecoute les evenement du capteur donné en parametre
     */
    public SensorEventListener sensorListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Récuperer les valeurs du capteur de gravité
            float x = event.values[0];
            float y = event.values[1];

            // Récupérer les dimensions de l'écran
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            // Mise a jour de la position de l'étoile
            // starImg.setY(Math.max(Math.min(x * screenHeight/2f/10f + screenHeight/2f - starImg.getHeight()/2f, screenHeight - starImg.getHeight()), 0));
            // starImg.setX(Math.max(Math.min(y * screenWidth/2f/10f + screenWidth/2f - starImg.getWidth()/2f, screenWidth - starImg.getWidth()), 0));

            moveSnake(x, y);
        }
    };

}

