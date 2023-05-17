package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class Snake {
    // Déclaration des variables
    public ImageView image;
    private String currentMove = "up";
    private String lastMove = currentMove;
    private int moveCouldown = 0;
    private boolean dead = false;
    private Rect rect;
    public ArrayList<SnakeBody> snakeBodies = new ArrayList<>();
    public Activity activity;
    public float lastPositionX;
    public float lastPositionY;

    /**
     * Constructeur de la class Snake
     * @param snakeImg image du serpent
     * @param newActivity activité du serpent
     */
    public Snake(ImageView snakeImg, Activity newActivity) {
        activity = newActivity;
        image = snakeImg;
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    /**
     * Retourne le rectangle de collision
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Ajouter une partie du corps au serpent
     */
    public void addBody() {
        SnakeBody body = new SnakeBody(activity, this, snakeBodies.size(), snakeBodies);
        snakeBodies.add(body);

    }

    /**
     * Récupere le mouvement a appliquer en fonction de l'inclinaison du téléphone
     */
    public void getCurrentMove(float x, float y) {
        float sensibility = 2F;

        if (Math.abs(x) > Math.abs(y)) {
            if (x > sensibility)
                currentMove = "up";
            else if (x < -sensibility)
                currentMove = "down";
            else
                currentMove = lastMove;
        } else {
            if (y < -sensibility)
                currentMove = "right";
            else if (y > sensibility)
                currentMove = "left";
            else
                currentMove = lastMove;
        }
    }

    /**
     * Bouge le serpent en fonction de la dirrection
     * @param x position x du capteur de gravité
     * @param y position y du capteur de gravité
     * @param activity l'activité du serpent
     */
    public void moveSnake(float x, float y, Activity activity) {
        getCurrentMove(x, y);
        if (moveCouldown <= 0) {
            updateMovement(currentMove);
            lastMove = currentMove;
            switch (currentMove) {
                case "up":
                    image.setY(image.getY() + image.getWidth());
                    resetMove();
                    break;
                case "down":
                    image.setY(image.getY() - image.getWidth());
                    resetMove();
                    break;
                case "right":
                    image.setX(image.getX() - image.getWidth());
                    resetMove();
                    break;
                case "left":
                    image.setX(image.getX() + image.getWidth());
                    resetMove();
                    break;
                default:
                    break;
            }
            checkCollisions(activity);
            for (SnakeBody body : snakeBodies) {
                body.update();
            }
        }
        // Enregistre l'ancienne position du serpent
        lastPositionX = image.getX();
        lastPositionY = image.getY();

        moveCouldown--; // diminue le couldown de 1
    }

    /**
     * Réinitialise le mouvement du serpent et son couldown
     */
    public void resetMove() {
        updateRotation();
        currentMove = "";
        moveCouldown = 7 - (snakeBodies.size()-1) / 5;
    }

    /**
     * Met a jour la rotation en fonction de la direction
     */
    public void updateRotation() {
        switch (currentMove) {
            case "up":
                image.setRotation(180);
                break;
            case "down":
                image.setRotation(0);
                break;
            case "left":
                image.setRotation(90);
                break;
            case "right":
                image.setRotation(270);
                break;
        }
    }

    /**
     * Empeche le serpent de partir dans la direction opposé
     * @param move le mouvements à tester
     */
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

    /**
     * Fait mourir le serpent
     */
    public void die() {
        dead = true;
    }

    /**
     * Retourne si le serpent est mort ou non
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Verifie les collision avec les bords de l'écran
     */
    public void checkCollisions(Activity activity) {
        // Récupérer les dimensions de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Met a jour le rectangle de collision de la tete du serpent
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));

        // Si le serpent touche un murs alors il meurt
        if (image.getX() <= 0 || image.getX() >= screenWidth - image.getWidth() || image.getY() <= 0 || image.getY() >= screenHeight - image.getHeight()) {
            die();
        }
    }

}
