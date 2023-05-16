package com.example.snakegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;

import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor gravity;
    Snake snake;

    Activity activity;
    Fruit fruit;

    /**
     * Code éxectué a la creation de l'application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activity = this;

        // Initialisation des varibles
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        snake = new Snake(findViewById(R.id.snakeImg));

        fruit = new Fruit(this);
    }

    public void endGame() {
        finish();
    }

    /**
     * Code éxecuté régulierement lors du fonctionement de l'application
     */
    public void onResume() {
        super.onResume();

        // Appeler l'ecoute du capteur de gravité
        sensorManager.registerListener(sensorListener, gravity, SensorManager.SENSOR_DELAY_NORMAL);
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

            snake.moveSnake(x, y, activity);

            Log.i("TAG", String.valueOf(fruit.getRect()));

            if (Rect.intersects(snake.getRect(), fruit.getRect())) {
                fruit = new Fruit(activity);
            }

            if (snake.isDead())
                endGame();
        }
    };

}

