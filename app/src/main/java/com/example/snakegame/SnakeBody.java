package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class SnakeBody {
    // Déclaration des variables de la class SnakeBody
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


    /**
     * Constructeur de la classe SnakeBody
     * @param activity L'activité de la classe SnakeBody
     * @param newSnake La tete du serpent
     * @param newBodyPotition La position de la partie du corps dans la liste des parties du corps
     * @param newSnakeBodyList la liste des parties du corps
     */
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

    /**
     * Retourne le rectangle de collision
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Méthode qui fait appele au fonction utile au bon fonctionnement d'une partie du corps
     */
    public void update() {
        updatePosition();
        updateRotation();
    }

    /**
     * Met à jour en temps réel la position de la partie du corps
     */
    private void updatePosition() {
        updateLastPosition();

        // Si la partie du corps est la premiere de la liste,
        // Alors la position sera l'ancienne position de la tete du serpent
        // Sinon la position sera l'ancienne position de la partie qui se trouve devant
        if (bodyPosition == 0){
            positionX = snake.lastPositionX - 32;
            positionY = snake.lastPositionY - 32;
        } else {
            positionX = frontBody.lastPositionX;
            positionY = frontBody.lastPositionY;
        }

        // Définit la position x et y de l'image
        image.setX(positionX);
        image.setY(positionY);

        // Met a jour la hitbox de la partie du corps (Elle est plus petite que la tete pour certains problemes)
        rect = new Rect((int) image.getX() + 40, (int) image.getY() + 40, (int) (image.getX() + image.getWidth() - 40), (int) (image.getY() + image.getHeight() - 40));
    }

    /**
     * Stoque la derniere position de la partie du corps
     */
    private void updateLastPosition() {
        lastPositionX = positionX;
        lastPositionY = positionY;
    }

    /**
     * Récupere la position et la rotation initial de la partie du coprs lors de sa création
     */
    private void getInitialPosition() {
        // Déclaration de variables
        float x;
        float y;
        int rotation;

        // Si la partie du corps est la premiere de la liste,
        // Alors récupérer la position/rotation de la tete du serpent
        // Sinon récupérer la position/rotation de la partie du corps de devant
        if (bodyPosition == 0) {
            x = snake.image.getX() - 32;
            y = snake.image.getY() - 32;
            rotation = (int) snake.image.getRotation();
        } else {
            setFrontBody();
            x  = frontBody.positionX;
            y  = frontBody.positionY;
            rotation = (int) frontBody.image.getRotation();
        }

        // Placer la partie du corps en fonction de la rotation de la partie qui se trouve devant
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

    /**
     * Met a jour la rotation de la partie du corps
     */
    private void updateRotation() {
        // Stoque l'ancienne rotation
        lastRotation = image.getRotation();

        // Si la partie du corps est la premiere de la liste,
        // Alors récupere la meme rotation que la tete
        // Sinon récupere l'ancienne rotation de la partie du corps positionné devant
        if (bodyPosition == 0) {
            image.setRotation(snake.image.getRotation());
        } else {
            image.setRotation(frontBody.lastRotation);
        }
    }
}
