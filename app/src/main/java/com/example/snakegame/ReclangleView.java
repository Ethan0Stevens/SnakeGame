package com.example.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class ReclangleView extends View {
    Paint paint;
    Rect rect;

    public ReclangleView(Context context, Rect newRect) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED); // Couleur des contours du rectangle
        paint.setStyle(Paint.Style.STROKE); // Style des contours
        paint.setStrokeWidth(5); // Épaisseur des contours

        // Définir les coordonnées du rectangle (gauche, haut, droite, bas)
        rect = newRect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dessiner le rectangle avec les contours
        canvas.drawRect(rect, paint);
    }
}
