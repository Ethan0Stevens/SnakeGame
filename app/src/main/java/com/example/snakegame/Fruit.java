package com.example.snakegame;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Fruit {
    ImageView image;
    Rect rect;
    ConstraintLayout constraintLayout;
    public Fruit(Activity activity) {
        image = new ImageView(activity);
        image.setImageResource(R.drawable.devil_fruit);
        image.setScaleX(0.5F);
        image.setScaleY(0.5f);

        // Récupérer la taille de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Place le fruit a une position aléatoire sur un des carré de jeu
        image.setX(((int)(Math.random() * (screenWidth-100)/64) * 64));
        image.setY(((int)(Math.random() * (screenHeight-50)/64) * 64) + 10);

        // Placer l'image sur le layout
        constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(image);

        rect = new Rect((int) image.getX() + 40, (int) image.getY() + 40, (int) (image.getX() + 90), (int) (image.getY() + 90));
    }

    public Rect getRect() {
        return rect;
    }

    public void delete() {
        constraintLayout.removeView(image);
    }
}
