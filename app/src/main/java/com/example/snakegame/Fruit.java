package com.example.snakegame;

import android.app.Activity;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Fruit {
    public Fruit(Activity activity) {
        ImageView imageView = new ImageView(activity);
        imageView.setImageResource(R.drawable.devil_fruit);
        imageView.setScaleX(0.5F);
        imageView.setScaleY(0.5F);

        ConstraintLayout constraintLayout = activity.findViewById(R.id.gameLayout);
        constraintLayout.addView(imageView);
    }
}
