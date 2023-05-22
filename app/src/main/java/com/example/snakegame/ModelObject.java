package com.example.snakegame;

public enum ModelObject {

    EASY(R.string.red, R.layout.view_level_easy),
    MEDIUM(R.string.blue, R.layout.view_level_medium),
    HARD(R.string.green, R.layout.view_level_hard);

    private final int mTitleResId;
    private final int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
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
