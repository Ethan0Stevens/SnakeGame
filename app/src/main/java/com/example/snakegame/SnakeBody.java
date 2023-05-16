package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SnakeBody {
    ImageView image;
    Rect rect;
    ConstraintLayout constraintLayout;
    Snake snake;
    float positionX;
    float positionY;

    public SnakeBody(Activity activity, Snake newSnake) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.snake_body);
        image.setScaleX(0.5F);
        image.setScaleY(0.5f);

        snake = newSnake;

        // Placer l'image sur le layout
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        getPosition();

        // Place le fruit a une position aléatoire sur un des carré de jeu
        image.setX(positionX);
        image.setY(positionY);

        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    public void getPosition() {
        if (snake.image.getRotation() == 180) {
            positionX = snake.image.getX() - 32;
            positionY = snake.image.getY() - 32 - 64;
        }
        if (snake.image.getRotation() == 0) {
            positionX = snake.image.getX() - 32;
            positionY = snake.image.getY() - 32 + 64;
        }
        if (snake.image.getRotation() == 90) {
            positionX = snake.image.getX() - 32 - 64;
            positionY = snake.image.getY() - 32;
        }
        if (snake.image.getRotation() == 270) {
            positionX = snake.image.getX() - 32 + 64;
            positionY = snake.image.getY() - 32;
        }
    }
}
