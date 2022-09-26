package com.example.nutechwallet.screen;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


import nutechwallet.R;
import com.example.nutechwallet.base.BaseActivity;
import com.example.nutechwallet.utils.database.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    public static final int SPLASH_TIME = 3000;
    private int count = 0;
    private AnimatorSet set;
    @BindView(R.id.logo) ImageView imageView;
    protected UserData userData;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        ButterKnife.bind(this);
        userData = new UserData(this);
        initViews();

    }


    private void initViews(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                set = (AnimatorSet) AnimatorInflater
                        .loadAnimator(SplashActivity.this, R.animator.animation_splash);
                set.setTarget(imageView);
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (userData.count() == 1)
                            startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                        else
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                set.start();
            }
        }, SPLASH_TIME);
    }

}
