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
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor gravity;
    Snake snake;
    Activity activity;
    ArrayList<Fruit> fruits = new ArrayList<>();

    TextView scoreText;
    ConstraintLayout constraintLayout;

    /**
     * Code éxectué a la creation de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        // Initialisation des varibles
        activity = this;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        scoreText = findViewById(R.id.scoreText);
        constraintLayout = findViewById(R.id.gameOver);

        snake = new Snake(findViewById(R.id.snakeImg), this);
        fruits.add(new Fruit(this));
        constraintLayout.setVisibility(View.INVISIBLE);
    }

    public void reloadGame(View view) {
        recreate();
    }

    /**
     * Met fin à l'activité
     */
    public void endGame() {
        constraintLayout.setZ(10);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    public void exitActivity(View view) {
        finish();
    }

    /**
     * onResume de GameActivity
     */
    public void onResume() {
        super.onResume();

        // Appeler l'ecoute du capteur de gravité
        sensorManager.registerListener(sensorListener, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Met à jour tout ce qui concerne le fonctionnement du serpent
     * @param x la nouvelle posision x du serpent
     * @param y la nouvelle posision Y du serpent
     */
    public void updateSnake(float x, float y) {
        snake.moveSnake(x, y, activity);
        checkCollisionWithFruit();
        checkCollisionWithBodies();
        verifiyIsSnakeAlive();
    }


    /**
     * Vérifie les collision entre le serpent et le fruit
     * Si il y a collision, alors supprimer le fruit touché, ajouter un nouveau fruit
     * et ajouter une partie du corps au serpent
     */
    public void checkCollisionWithFruit() {
        ArrayList<Fruit> fruitsToRemove = new ArrayList<>();
        for (Fruit fruit : fruits) {
            if (snake.getRect().intersect(fruit.getRect())) {
                fruit.delete();
                fruitsToRemove.add(fruit);
                snake.addBody(); // Ajouter une partie du corps au serpent
            }
        }
        for (Fruit fruit : fruitsToRemove) {
            incrementScore();
            fruits.remove(fruit);

            // Ajouter un nouveau fruit a la liste des fruits
            if (fruits.size() <= 0) {
                fruits.add(new Fruit(activity));
            }
            fruits.add(new Fruit(activity));
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        Rect activityRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(activityRect);

        Rect rect = new Rect(0, 0, activityRect.right, screenHeight);

        ConstraintLayout layout = findViewById(R.id.gameLayout); // Remplacez "layout" par l'ID de votre layout
        RectangleView rectangleView = new RectangleView(activity, rect);
        layout.addView(rectangleView);
    }

    public void incrementScore() {
        int score = Integer.parseInt((String) scoreText.getText());
        score++;
        scoreText.setText(String.valueOf(score));
    }

    /**
     * Verifie les collisions entre la tete du serpent et les partie du corp
     * Si il y a une collision, alors tuer le serpent
     */
    public void checkCollisionWithBodies() {
        for (SnakeBody body : snake.snakeBodies) {
            if (snake.getRect().intersect(body.getRect()))
                snake.die();
        }
    }

    /**
     * Vérifie si le serpent est mort ou non, si oui alors mettre fin a l'activité
     */
    public void verifiyIsSnakeAlive() {
        if (snake.isDead())
            endGame();
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

            if (!snake.isDead())
                updateSnake(x, y);
        }
    };
}

