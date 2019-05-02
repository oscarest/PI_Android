package com.studium.xxracso40xx.pi_android;

public enum ModelObject
{
    FIRST(R.string.app_name, R.layout.activity_principal),
    SECOND(R.string.boton1, R.layout.activity_canciones),
    THIRD(R.string.title_activity_main2, R.layout.activity_perfil);
    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId =titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
