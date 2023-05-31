package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.view.ViewGroup;
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
    boolean turn = false;
    boolean turned = false;


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

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(64, 64);
        image.setLayoutParams(layoutParams);

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
            positionX = snake.lastPositionX;
            positionY = snake.lastPositionY;
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
            x = snake.image.getX();
            y = snake.image.getY();
            rotation = (int) snake.image.getRotation();
        } else {
            setFrontBody();
            x  = frontBody.positionX;
            y  = frontBody.positionY;
            rotation = (int) frontBody.image.getRotation();
        }

        int squareSize = snake.image.getWidth();

        // Placer la partie du corps en fonction de la rotation de la partie qui se trouve devant
        switch (rotation) {
            case 180:
                positionX = x;
                positionY = y - squareSize;
                break;
            case 0:
                positionX = x;
                positionY = y + squareSize;
                break;
            case 90:
                positionX = x - squareSize;
                positionY = y;
                break;
            case 270:
                positionX = x + squareSize;
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
            turned = turn;
            if ((!snake.currentMove.equals(snake.lastMove))) {
                turn = true;
                image.setImageResource(R.drawable.snake_body_turn_right);

                if (turned) {
                    if ((image.getRotation() == 0 && snake.currentMove.equals("up")) ||
                            (image.getRotation() == 180 && snake.currentMove.equals("right"))  ||
                            (image.getRotation() == 270 && snake.currentMove.equals("up")) ||
                            (image.getRotation() == 270 && snake.currentMove.equals("right"))) {
                        image.setRotation(90);
                    } else if ((image.getRotation() == 0 && snake.currentMove.equals("down")) ||
                            (image.getRotation() == 270 && snake.currentMove.equals("down")) ||
                            (image.getRotation() == 0 && snake.currentMove.equals("right")) ||
                            (image.getRotation() == 90 && snake.currentMove.equals("right"))) {
                        image.setRotation(180);
                    } else if((image.getRotation() == 90 && snake.currentMove.equals("left")) ||
                            (image.getRotation() == 180 && snake.currentMove.equals("down")) ||
                            (image.getRotation() == 0 && snake.currentMove.equals("left")) ||
                            (image.getRotation() == 90 && snake.currentMove.equals("down"))) {
                        image.setRotation(270);
                    } else if ((image.getRotation() == 180 && snake.currentMove.equals("up")) ||
                            (image.getRotation() == 180 && snake.currentMove.equals("left")) ||
                            (image.getRotation() == 90 && snake.currentMove.equals("up")) ||
                            (image.getRotation() == 270 && snake.currentMove.equals("left"))){
                        image.setRotation(0);
                    }
                } else {
                    if ((image.getRotation() == 0 && snake.currentMove.equals("right")) ||
                            (image.getRotation() == 90 && snake.currentMove.equals("up"))) {
                        image.setRotation(90);
                    } else if ((image.getRotation() == 0 && snake.currentMove.equals("left")) ||
                            (image.getRotation() == 270 && snake.currentMove.equals("up"))){
                        image.setRotation(0);
                    } else if ((image.getRotation() == 270 && snake.currentMove.equals("down")) ||
                            (image.getRotation() == 180 && snake.currentMove.equals("left"))) {
                        image.setRotation(270);
                    } else if ((image.getRotation() == 90 && snake.currentMove.equals("down")) ||
                            (image.getRotation() == 180 && snake.currentMove.equals("right"))) {
                        image.setRotation(180);
                    }
                }
            } else {
                image.setImageResource(R.drawable.snake_body);
                image.setRotation(snake.image.getRotation());
                turn = false;
            }
        } else {
            turned = turn;
            if (frontBody.turned) {
                image.setImageResource(R.drawable.snake_body_turn_right);
                turn = true;
            } else {
                image.setImageResource(R.drawable.snake_body);
                turn = false;
            }
            image.setRotation(frontBody.lastRotation);
        }
    }
}
