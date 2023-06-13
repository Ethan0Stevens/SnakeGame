package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class SnakeTail {
    final int TILE_SIZE = 64;

    // Déclaration des variables
    ImageView image;
    ArrayList<SnakeBody> bodies;
    Snake snake;
    private Rect rect;

    public SnakeTail(Activity activity, Snake snake, ArrayList<SnakeBody> bodies) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.snake_tail);

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(TILE_SIZE, TILE_SIZE);
        image.setLayoutParams(layoutParams);

        // Placer l'image sur le layout
        ConstraintLayout constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        this.bodies = bodies;
        this.snake = snake;

        image.setX(snake.image.getX());
        image.setY(snake.image.getY());

        setRect();
    }

    /**
     * Retourne le rectangle de collision
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Définit la taille du rectangle de collision du fruit
     */
    public void setRect() {
        rect = new Rect((int) image.getX(), (int) image.getY(), (int) (image.getX() + image.getWidth()), (int) (image.getY() + image.getHeight()));
    }

    /**
     * Met à jour tout ce qui concerne le fonctionnement de la queue du serpent
     */
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

        setRect();
    }

    /**
     * Met a jour la rotation de la queue du serpent
     */
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
