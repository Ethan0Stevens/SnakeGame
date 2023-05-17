package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class Snake {
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

    public Snake(ImageView snakeImg, Activity newActivity) {
        activity = newActivity;
        image = snakeImg;
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    public Rect getRect() {
        return rect;
    }

    public void addBody() {
        SnakeBody body = new SnakeBody(activity, this, snakeBodies.size(), snakeBodies);
        snakeBodies.add(body);

    }

    public void getCurrentMove(float x, float y) {
        if (moveCouldown <= 0) {

            if (Math.abs(x) > Math.abs(y)) {
                if (x > 3)
                    currentMove = "up";
                else if (x < -3)
                    currentMove = "down";
                else
                    currentMove = lastMove;
            } else {
                if (y < -3)
                    currentMove = "right";
                else if (y > 3)
                    currentMove = "left";
                else
                    currentMove = lastMove;
            }
            updateMovement(currentMove);
            updateRotation();
            lastMove = currentMove;
            for (SnakeBody body : snakeBodies) {
                body.update();
            }
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
        lastPositionX = image.getX();
        lastPositionY = image.getY();
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

    public void die() {
        dead = true;
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
            die();
        }
    }

}
