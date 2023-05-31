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
    String direction = "";
    String lastDirection = "";


    /**
     * Constructeur de la classe SnakeBody
     * @param activity L'activité de la classe SnakeBody
     * @param snake La tete du serpent
     * @param bodyPosition La position de la partie du corps dans la liste des parties du corps
     * @param bodies la liste des parties du corps
     */
    public SnakeBody(Activity activity, Snake snake, int bodyPosition, ArrayList<SnakeBody> bodies) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.snake_body);

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(64, 64);
        image.setLayoutParams(layoutParams);

        this.bodyPosition = bodyPosition;
        this.snakeBodies = bodies;
        this.snake = snake;

        // Placer l'image sur le layout
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        getInitialPosition();

        image.setX(positionX);
        image.setY(positionY);


        updateRotation();

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
     * Méthode qui fait appele au fonction utile au bon fonctionnement d'une partie du corps
     */
    public void update() {
        updatePosition();
        updateRotation();
    }

    /**
     * Met à jour en temps réel la position de la partie du corps
     */
    public void updatePosition() {
        updateLastPosition();
        lastDirection = direction;

        // Si la partie du corps est la premiere de la liste,
        // Alors la position sera l'ancienne position de la tete du serpent
        // Sinon la position sera l'ancienne position de la partie qui se trouve devant
        if (bodyPosition == 0){
            direction = snake.lastMove;
            positionX = snake.lastPositionX;
            positionY = snake.lastPositionY;
        } else {
            direction = frontBody.lastDirection;
            positionX = frontBody.lastPositionX;
            positionY = frontBody.lastPositionY;
        }

        // Définit la position x et y de l'image
        image.setX(positionX);
        image.setY(positionY);

        setRect();
    }

    /**
     * Stoque la derniere position de la partie du corps
     */
    public void updateLastPosition() {
        lastPositionX = positionX;
        lastPositionY = positionY;
    }

    /**
     * Récupere la position et la rotation initial de la partie du coprs lors de sa création
     */
    public void getInitialPosition() {
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

        // Taille d'une tuile de jeu
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
    public void setFrontBody() {
        frontBody = snakeBodies.get(bodyPosition - 1);
    }

    /**
     * Met a jour la rotation de la partie du corps
     */
    public void updateRotation() {
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

                // En fonction de la direction du serpent, changer de sprite et changer sa rotation
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
