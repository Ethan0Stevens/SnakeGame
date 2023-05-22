package com.example.snakegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor gravity;
    Snake snake;
    Activity activity;
    ArrayList<Fruit> fruits = new ArrayList<>();
    TextView scoreText;
    ConstraintLayout constraintLayout;
    int totalFruitsSpawn;
    float snakeSpeed;

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


        Bundle extras = getIntent().getExtras();
        String difficulty = "";
        if(extras != null) {
            if (extras.getString("difficulty") != null) {
                difficulty = extras.getString("difficulty");
            }
            totalFruitsSpawn = extras.getInt("totalFruitsSpawn");
            snakeSpeed = extras.getInt("snakeSpeed");
        }
        setDifficultyParams(difficulty);

        // Initialisation des varibles
        activity = this;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        scoreText = findViewById(R.id.scoreText);
        constraintLayout = findViewById(R.id.gameOver);

        snake = new Snake(findViewById(R.id.snakeImg), this, 10, snakeSpeed);
        fruits.add(new Fruit(this));
        constraintLayout.setVisibility(View.INVISIBLE);

        snake.image.setX((64 * 11) + 32);
        snake.image.setY((64 * 5) + 32);
    }

    public void setDifficultyParams(String difficulty) {
        switch (difficulty) {
            case "easy":
                totalFruitsSpawn = 4;
                snakeSpeed = 10;
                break;
            case "medium":
                totalFruitsSpawn = 3;
                snakeSpeed = 7;
                break;
            case "hard":
                totalFruitsSpawn = 2;
                snakeSpeed = 5;
                break;
        }
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
                for (int i = totalFruitsSpawn-1; i > 0; i--) {
                    fruits.add(new Fruit(activity));
                }
            }
            fruits.add(new Fruit(activity));
        }
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

