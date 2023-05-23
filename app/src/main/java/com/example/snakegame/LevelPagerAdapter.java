package com.example.snakegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class LevelPagerAdapter extends PagerAdapter {
    private final Context mContext;

    public LevelPagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        ModelObjectLevel modelObject = ModelObjectLevel.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return ModelObjectLevel.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ModelObjectLevel customPagerEnum = ModelObjectLevel.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

    @SuppressLint("InflateParams")
    public View getView() {
        // Récupérez la vue de la page à l'aide de son index
        return LayoutInflater.from(mContext).inflate(R.layout.view_level_custom, null);
    }
}
