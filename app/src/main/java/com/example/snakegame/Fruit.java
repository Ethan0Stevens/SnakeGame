package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
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

        // Appliquez les nouveaux paramètres de mise en page à l'ImageView
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(64, 64);
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
     * Place l'image a une position aléatoire de l'ecrant en prenant en compte un quadrillage imaginaire
     */
    private void setRandomPosition() {
        // Récupérer les dimensions la bar de navigation virtuelle
        Rect activityRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(activityRect);

        image.setX(((int)(Math.random() * (activityRect.right)/64) * 64));
        image.setY(((int)(Math.random() * ((activityRect.bottom)/64) - 1) * 64));

        ConstraintLayout layout = activity.findViewById(R.id.gameLayout); // Remplacez "layout" par l'ID de votre layout
    }

    /**
     * Définit la taille du rectangle de collision du fruit
     */
    private void setRect() {
        rect = new Rect((int) image.getX() + 2, (int) image.getY() + 2, (int) (image.getX() + 60), (int) (image.getY() + 60));
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
