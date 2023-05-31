package com.example.snakegame;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class SnakeTail {
    // Déclaration des variables
    ImageView image;
    ArrayList<SnakeBody> bodies;
    Snake snake;

    public SnakeTail(Activity activity, Snake snake, ArrayList<SnakeBody> bodies) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.snake_tail);

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(64, 64);
        image.setLayoutParams(layoutParams);

        // Placer l'image sur le layout
        ConstraintLayout constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        this.bodies = bodies;
        this.snake = snake;

        image.setX(0);
        image.setY(0);
    }

    public void update() {
        updatePosition();
        updateRotation();
    }

    public void updatePosition() {
        // Si la partie du corps est la premiere de la liste,
        // Alors la position sera l'ancienne position de la tete du serpent
        // Sinon la position sera l'ancienne position de la partie qui se trouve devant
        if (bodies.size() == 0){
            image.setX(snake.lastPositionX);
            image.setY(snake.lastPositionY);
        } else {
            SnakeBody body = bodies.get(bodies.size()-1);

            image.setX(body.lastPositionX);
            image.setY(body.lastPositionY);
        }
    }

    public void updateRotation() {
        if (bodies.size() == 0){
            image.setRotation(snake.image.getRotation());
        } else {
            if (bodies.get(bodies.size()-1).direction.equals("up")) {
                image.setRotation(180);
            } else if (bodies.get(bodies.size()-1).direction.equals("down")) {
                image.setRotation(0);
            } else if (bodies.get(bodies.size()-1).direction.equals("right")) {
                image.setRotation(270);
            } else if (bodies.get(bodies.size()-1).direction.equals("left")) {
                image.setRotation(90);
            }
        }
    }
}