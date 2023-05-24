package com.example.snakegame;

public enum ModelObjectLeaderBoard {

    EASY(R.string.red, R.layout.view_leaderboard_easy),
    MEDIUM(R.string.blue, R.layout.view_leaderboard_medium),
    HARD(R.string.green, R.layout.view_leaderboard_hard);

    private final int mTitleResId;
    private final int mLayoutResId;

    ModelObjectLeaderBoard(int titleResId, int layoutResId) {
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
