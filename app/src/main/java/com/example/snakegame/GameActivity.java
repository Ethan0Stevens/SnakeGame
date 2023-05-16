package com.example.snakegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
    String currentMove = "up";
    String lastMove = null;
    int moveCouldown = 0;

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
        if (moveCouldown <= 0) {

            if (Math.abs(x) > Math.abs(y)) {
                if (x > 0)
                    currentMove = "up";
                if (x < 0)
                    currentMove = "down";
            } else {
                if (y < 0)
                    currentMove = "right";
                if (y > 0)
                    currentMove = "left";
            }
            updateMovement(currentMove);
            lastMove = currentMove;
        }


        switch (currentMove) {
            case "up":
                starImg.setY(starImg.getY() + starImg.getWidth()/2f);
                currentMove = "";
                moveCouldown = 15;
                break;
            case "down":
                starImg.setY(starImg.getY() - starImg.getWidth()/2f);
                currentMove = "";
                moveCouldown = 15;
                break;
            case "right":
                starImg.setX(starImg.getX() - starImg.getWidth()/2f);
                currentMove = "";
                moveCouldown = 15;
                break;
            case "left":
                starImg.setX(starImg.getX() + starImg.getWidth()/2f);
                currentMove = "";
                moveCouldown = 15;
                break;
            default:
                break;
        }

        checkCollisions();

        moveCouldown--;
    }

    public void updateMovement(String move) {
        if (!(lastMove == null)) {
            if (move.equals("up") && lastMove.equals("down")) {
                currentMove = "down";
            }
            if (move.equals("down") && lastMove.equals("up")) {
                currentMove = "up";
            }
            if (move.equals("right") && lastMove.equals("left")) {
                currentMove = "left";
            }
            if (move.equals("left") && lastMove.equals("right")) {
                currentMove = "right";
            }
        }
    }

    public void checkCollisions() {
        // Récupérer les dimensions de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        if (starImg.getX() <= 0 || starImg.getX() >= screenWidth - starImg.getWidth() || starImg.getY() <= 0 || starImg.getY() >= screenHeight - starImg.getHeight())
            finish();
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

            moveSnake(x, y);
        }
    };

}

