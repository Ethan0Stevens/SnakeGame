package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class Snake {
    final int TILE_SIZE = 64;

    // Déclaration des variables
    public ImageView image;
    public String currentMove = "up";
    public String lastMove = currentMove;
    private float moveCouldown = 0;
    private boolean dead = false;
    private Rect rect;
    public ArrayList<SnakeBody> snakeBodies = new ArrayList<>();
    public Activity activity;
    public float lastPositionX;
    public float lastPositionY;
    private final float acceleration;
    private final float defaultSpeed;
    SnakeTail tail;

    /**
     * Constructeur de la class Snake
     * @param snakeImg image du serpent
     * @param newActivity activité du serpent
     */
    public Snake(ImageView snakeImg, Activity newActivity, float acceleration, float defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
        this.acceleration = acceleration;
        activity = newActivity;
        image = snakeImg;

        // Position de départ du serpent
        image.setX((TILE_SIZE * 11) + TILE_SIZE/2f);
        image.setY((TILE_SIZE * 5) + TILE_SIZE/2f);

        setRect();

        tail = new SnakeTail(activity, this, snakeBodies);
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
     * Met a jour le rectangle de collision de la tete du serpent
     */
    private void setRect() {
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    /**
     * Récupere le mouvement a appliquer en fonction de l'inclinaison du téléphone
     */
    public void getCurrentMove(float x, float y) {
        float sensibility = 1.5F;

        if (Math.abs(x) > Math.abs(y)) {

            if (x > sensibility + 0.5)
                currentMove = "up";
            else if (x < -sensibility)
                currentMove = "down";;
        } else {
            if (y < -sensibility)
                currentMove = "right";
            else if (y > sensibility)
                currentMove = "left";
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
        updateMovement(currentMove);

        if (moveCouldown <= ((snakeBodies.size()-1) / acceleration)) {

            // Met a jour la position de chaque partie du corps
            for (SnakeBody body : snakeBodies) {
                body.update();
            }

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
            tail.update();
            checkCollisions(activity);
        }


        // Enregistre l'ancienne position du serpent
        lastPositionX = image.getX();
        lastPositionY = image.getY();

        moveCouldown -= 0.1f; // diminue le couldown
    }

    /**
     * Réinitialise le mouvement du serpent et son couldown
     */
    public void resetMove() {
        updateRotation();
        currentMove = lastMove;
        moveCouldown = defaultSpeed/100f;
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
        // Récupérer les dimensions la bar de navigation virtuelle
        Rect activityRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(activityRect);

        setRect();

        // Si le serpent touche un murs alors il meurt
        if (image.getX() < 0 || image.getX() > activityRect.right - image.getWidth() || image.getY() < 0 || image.getY() > activityRect.bottom - image.getHeight()) {
            die();
        }
    }

}
