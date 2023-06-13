package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class Fruit {

    final int TILE_SIZE = 64;

    // Déclaration des variables de la class Fruit
    ImageView image;
    Rect rect;
    ConstraintLayout constraintLayout;
    Activity activity;
    ArrayList<Fruit> fruits;
    ArrayList<SnakeBody> bodies;

    /**
     * Constructeur de la class Fruit
     * @param newActivity l'activité du fruit
     */
    public Fruit(Activity newActivity, ArrayList<Fruit> fruits, ArrayList<SnakeBody> bodies) {
        activity = newActivity;
        this.fruits = fruits;
        this.bodies = bodies;

        setImage();
        updateFruit();
        placeImage();
    }

    /**
     * Création de l'image et tous ses parametres
     */
    private void setImage() {
        // Creation d'une ImageView
        image = new ImageView(activity);

        // Définit le sprite de l'image
        image.setImageResource(R.drawable.devil_fruit);

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(TILE_SIZE, TILE_SIZE);
        image.setLayoutParams(layoutParams);
    }

    /**
     * Place l'image sur la vue
     */
    private void placeImage() {
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);
    }

    /**
     * Place le fruit sur le jeu,
     * si il apparait sur un autre fruit, alors le replacer et ainsi de suite,
     * si il apparait sur une partie du corps du serpent, alors le replacer et ainsi de suite
     */
    public void updateFruit() {
        setRandomPosition();

        // Detection des collision avec les autres fruits
        for (Fruit fruit : fruits) {
            if (rect.intersect(fruit.getRect())) {
                updateFruit();
            }
        }

        // Detection des collision avec le serpent
        for (SnakeBody body : bodies) {
            if (rect.intersect(body.getRect())) {
                updateFruit();
            }
        }
    }

    /**
     * Place l'image a une position aléatoire de l'ecrant en prenant en compte un quadrillage imaginaire
     */
    private void setRandomPosition() {
        // Récupérer les dimensions la bar de navigation virtuelle
        Rect activityRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(activityRect);

        Random random = new Random();
        int randomx = random.nextInt(activityRect.right/TILE_SIZE - 1);
        int randomy = random.nextInt(activityRect.bottom/TILE_SIZE - 1);

        image.setX(randomx * TILE_SIZE);
        image.setY(randomy * TILE_SIZE);

        setRect();
    }

    /**
     * Définit la taille du rectangle de collision du fruit
     */
    private void setRect() {
        rect = new Rect((int) image.getX() + 2,
                        (int) image.getY() + 2,
                            (int) (image.getX() + TILE_SIZE-4),
                            (int) (image.getY() + TILE_SIZE-4));
    }

    /**
     * Retourne le rectangle de collision
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Enleve l'image du fruit de la vue
     */
    public void delete() {
        constraintLayout.removeView(image);
    }
}
