package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class Snake {
    ImageView image;
    String currentMove = "up";
    String lastMove = currentMove;
    int moveCouldown = 0;
    boolean dead = false;
    Rect rect;

    public Snake(ImageView snakeImg) {
        image = snakeImg;
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    public Rect getRect() {
        return rect;
    }

    public void getCurrentMove(float x, float y) {
        if (moveCouldown <= 0) {

            if (Math.abs(x) > Math.abs(y)) {
                if (x > 1)
                    currentMove = "up";
                else if (x < -1)
                    currentMove = "down";
                else
                    currentMove = lastMove;
            } else {
                if (y < -1)
                    currentMove = "right";
                else if (y > 1)
                    currentMove = "left";
                else
                    currentMove = lastMove;
            }
            updateMovement(currentMove);
            lastMove = currentMove;
            updateRotation();
        }
    }

    public void moveSnake(float x, float y, Activity activity) {
        getCurrentMove(x, y);
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

        moveCouldown--;
    }
    public void resetMove() {
        currentMove = "";
        moveCouldown = 7;
    }

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

        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));

        // Si le serpent touche un murs alors il meurt
        if (image.getX() <= 0 || image.getX() >= screenWidth - image.getWidth() || image.getY() <= 0 || image.getY() >= screenHeight - image.getHeight()) {
            dead = true;
        }
    }

}
