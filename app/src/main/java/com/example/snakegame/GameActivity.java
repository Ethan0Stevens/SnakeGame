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

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    // Déclaration des variables
    SensorManager sensorManager;
    Sensor gravity;
    Snake snake;
    Activity activity;
    ArrayList<Fruit> fruits = new ArrayList<>();
    TextView scoreText;
    TextInputEditText pseudoTextEdit;
    ConstraintLayout gameOverLayout;
    ConstraintLayout askPseudoLayout;
    int totalFruitsSpawn;
    float snakeSpeed;
    String difficulty = "";

    /**
     * Code éxectué a la creation de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setFullScreen();
        getParameters();

        // ---------------- Initialisation des varibles ---------------

        activity = this;

        // Initialisation du capteur de gravité
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // Récuperer le champ qui contient le pseudo du joueur
        pseudoTextEdit = findViewById(R.id.pseudoTextEdit);

        setScoreTextView();
        setGameOverLayout();
        setAskPseudoLayout();

        // Creation du serpent
        snake = new Snake(findViewById(R.id.snakeImg), this, 300f, snakeSpeed);
    }

    /**
     * Déclaration du text view du score
     */
    private void setScoreTextView() {
        scoreText = findViewById(R.id.scoreText);
        scoreText.setZ(9);
    }

    /**
     * Déclaration du layout qui demande le pseudo du joueur
     */
    private void setAskPseudoLayout() {
        askPseudoLayout = findViewById(R.id.askPseudo);
        askPseudoLayout.setZ(10);
    }

    /**
     * Déclaration du layout de game over
     */
    private void setGameOverLayout() {
        gameOverLayout = findViewById(R.id.gameOver);
        gameOverLayout.setZ(10);
        gameOverLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Fixe l'écran en pleine écran
     */
    private void setFullScreen() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * Récuperation des parametres données a l'activité
     */
    private void getParameters() {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            if (extras.getString("difficulty") != null) {
                difficulty = extras.getString("difficulty");
            }
            totalFruitsSpawn = extras.getInt("totalFruitsSpawn");
            snakeSpeed = extras.getInt("snakeSpeed");
        }
        setDifficultyParams();
    }

    /**
     * Définir les parametre du serpent en fonction du niveau de difficulté
     */
    public void setDifficultyParams() {
        switch (difficulty) {
            case "easy":
                totalFruitsSpawn = 4;
                snakeSpeed = 70;
                break;
            case "medium":
                totalFruitsSpawn = 3;
                snakeSpeed = 50;
                break;
            case "hard":
                totalFruitsSpawn = 2;
                snakeSpeed = 30;
                break;
        }
    }

    /**
     * Relancer le jeu
     */
    public void reloadGame(View view) {
        recreate();
    }

    /**
     * Met fin au jeu
     */
    public void endGame() {
        gameOverLayout.setVisibility(View.VISIBLE);

        verifyPseudo();
        saveScore();
    }

    /**
     * Vérifie que le pseudo ne soit pas vide, sinon le remplire par 'user'
     */
    @SuppressLint("SetTextI18n")
    private void verifyPseudo() {
        // Si le champs est vide alors le remplacer par 'user'
        if (String.valueOf(pseudoTextEdit.getText()).equals(""))
            pseudoTextEdit.setText("user");
    }

    /**
     * Ajouter le score de la partie a la tables des scores corréspondante au mode de jeu
     */
    private void saveScore() {
        if (!difficulty.equals("")) {
            DataBaseHandler table = new DataBaseHandler(this, difficulty);
            table.insertScore(String.valueOf(pseudoTextEdit.getText()), String.valueOf(snake.snakeBodies.size()));
        }
    }

    /**
     * Mettre fin a l'activité
     */
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
        if (askPseudoLayout.getVisibility() == View.INVISIBLE) {
            snake.moveSnake(x, y, activity);
            checkCollisionWithFruit();
            checkCollisionWithBodies();
            verifiyIsSnakeAlive();
        }
    }


    /**
     * Vérifie les collision entre le serpent et le fruit
     * Si il y a collision, alors supprimer le fruit touché, ajouter un nouveau fruit
     * et ajouter une partie du corps au serpent
     */
    public void checkCollisionWithFruit() {
        ArrayList<Fruit> fruitsToRemove = new ArrayList<>();

        // Detecter si le serpent touche un fruit
        for (Fruit fruit : fruits) {
            if (snake.getRect().intersect(fruit.getRect())) {
                eatFruit(fruit, fruitsToRemove);
            }
        }

        // Supprimer les fruits a supprimer
        for (Fruit fruit : fruitsToRemove) {
            fruits.remove(fruit);

            // ajouter le nombre de fruit nécessaire pour arriver au total de fruits
            if (fruits.size() <= 0) {
                for (int i = totalFruitsSpawn-1; i > 0; i--) {
                    addFruit();
                }
            }
            addFruit();
        }
    }

    /**
     * Manger un fruit
     * @param fruit fruit a supprimer
     * @param fruitsToRemove liste de fruits a supprimer
     */
    public void eatFruit(Fruit fruit, ArrayList<Fruit> fruitsToRemove) {
        fruitsToRemove.add(fruit); // ajouter le fruit a une liste de fruits a supprimer
        fruit.delete(); // supprimer l'image du fruit
        snake.addBody(); // Ajouter une partie du corps au serpent
        incrementScore();
    }

    /**
     * Ajouter un fruit a la liste de fruits
     */
    private void addFruit() {
        fruits.add(new Fruit(activity, fruits, snake.snakeBodies));
    }

    /**
     * Incrementation de l'affichage du score
     */
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

    /**
     * Cacher le layout qui demande un pseudo avant la partie
     */
    public void hideAskPseudo(View view) {
        askPseudoLayout.setVisibility(View.INVISIBLE);
        addFruit();
    }
}

