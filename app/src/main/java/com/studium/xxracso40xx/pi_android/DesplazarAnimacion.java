package com.studium.xxracso40xx.pi_android;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.studium.xxracso40xx.pi_android.R;

public class DesplazarAnimacion
{

    public static void MoverDesdeIzquierda(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    public static void MoverParaIzquierda(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }
    public static void moverDesdeDerecha(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }
    public static void MoverParaDerecha(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_right);
    }
    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
        ));
    }
}
