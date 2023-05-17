package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class SnakeBody {
    ImageView image;
    Rect rect;
    ConstraintLayout constraintLayout;
    Snake snake;
    float positionX;
    float positionY;
    float lastPositionX;
    float lastPositionY;
    int bodyPosition;
    ArrayList<SnakeBody> snakeBodies;
    SnakeBody frontBody;
    float lastRotation;



    public SnakeBody(Activity activity, Snake newSnake, int newBodyPotition, ArrayList<SnakeBody> newSnakeBodyList) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.snake_body);
        image.setScaleX(0.5F);
        image.setScaleY(0.5f);

        bodyPosition = newBodyPotition;
        snakeBodies = newSnakeBodyList;
        snake = newSnake;

        // Placer l'image sur le layout
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        getInitialPosition();

        image.setX(positionX);
        image.setY(positionY);

        updateRotation();

        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    public void update() {
        updatePosition();
        updateRotation();
    }

    private void updatePosition() {
        updateLastPosition();
        if (bodyPosition == 0){
            positionX = snake.lastPositionX - 32;
            positionY = snake.lastPositionY - 32;
        } else {
            positionX = frontBody.lastPositionX;
            positionY = frontBody.lastPositionY;
        }

        image.setX(positionX);
        image.setY(positionY);
    }

    private void updateLastPosition() {
        lastPositionX = positionX;
        lastPositionY = positionY;
    }

    private void getInitialPosition() {
        float x;
        float y;
        int rotation;

        if (bodyPosition == 0) {
            x = snake.image.getX() - 32;
            y = snake.image.getX() - 32;
            rotation = (int) snake.image.getRotation();
        } else {
            setFrontBody();
            x  = frontBody.positionX;
            y  = frontBody.positionY;
            rotation = (int) frontBody.image.getRotation();
        }

        switch (rotation) {
            case 180:
                positionX = x;
                positionY = y - 64;
                break;
            case 0:
                positionX = x;
                positionY = y + 64;
                break;
            case 90:
                positionX = x - 64;
                positionY = y;
                break;
            case 270:
                positionX = x + 64;
                positionY = y;
                break;
        }

        updateLastPosition();
    }

    /**
     * Récupère la partie du corps qui se trouve juste avant celle la
     */
    private void setFrontBody() {
        frontBody = snakeBodies.get(bodyPosition - 1);
    }

    private void updateRotation() {
        lastRotation = image.getRotation();
        if (bodyPosition == 0) {
            image.setRotation(snake.image.getRotation());
        } else {
            image.setRotation(frontBody.lastRotation);
        }
    }
}
