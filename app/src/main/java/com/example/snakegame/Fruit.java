package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Fruit {
    // Déclaration des variables de la class Fruit
    ImageView image;
    Rect rect;
    ConstraintLayout constraintLayout;
    Activity activity;

    /**
     * Constructeur de la class Fruit
     * @param newActivity l'activité du fruit
     */
    public Fruit(Activity newActivity) {
        activity = newActivity;

        setImage();
        setRandomPosition();
        placeImage();
        setRect();
    }

    /**
     * Création de l'image et tous ses parametres
     */
    private void setImage() {
        // Creation d'une ImageView
        image = new ImageView(activity);

        // Définit le sprite de l'image
        image.setImageResource(R.drawable.devil_fruit);

        // Diminue la taille de l'image par 2
        image.setScaleX(0.5F);
        image.setScaleY(0.5f);
    }

    /**
     * Place l'image sur la vue
     */
    private void placeImage() {
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);
    }

    /**
     * Place l'image a une position aléatoire de l'ecrant en prenant en compte un quadrillage imaginaire
     */
    private void setRandomPosition() {
        // Récupérer la taille de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        image.setX(((int)(Math.random() * (screenWidth)/64) * 64));
        image.setY(((int)(Math.random() * (screenHeight)/64) * 64));
    }

    /**
     * Définit la taille du rectangle de collision du fruit
     */
    private void setRect() {
        rect = new Rect((int) image.getX() + 40, (int) image.getY() + 40, (int) (image.getX() + 90), (int) (image.getY() + 90));
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
