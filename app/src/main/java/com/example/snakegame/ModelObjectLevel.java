package com.example.snakegame;

public enum ModelObjectLevel {

    EASY(R.string.red, R.layout.view_level_easy),
    MEDIUM(R.string.blue, R.layout.view_level_medium),
    HARD(R.string.green, R.layout.view_level_hard),
    CUSTOM(R.string.green, R.layout.view_level_custom);

    private final int mTitleResId;
    private final int mLayoutResId;

    ModelObjectLevel(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
