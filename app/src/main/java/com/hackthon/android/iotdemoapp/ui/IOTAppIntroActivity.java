package com.hackthon.android.iotdemoapp.ui;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.hackthon.android.iotdemoapp.R;
import com.hackthon.android.iotdemoapp.fragments.FirstIntroFragment;
import com.hackthon.android.iotdemoapp.fragments.SecIntroFragment;
import com.hackthon.android.iotdemoapp.fragments.ThirdIntroFragment;

public class IOTAppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirstIntroFragment firstIntroFragment = new FirstIntroFragment();
        SecIntroFragment secIntroFragment = new SecIntroFragment();
        ThirdIntroFragment thirdIntroFragment = new ThirdIntroFragment();

        addSlide(firstIntroFragment);
        addSlide(secIntroFragment);
        addSlide(thirdIntroFragment);

        setBarColor(Color.parseColor("#0bb961"));
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(android.support.v4.app.Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(android.support.v4.app.Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(IOTAppIntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable android.support.v4.app.Fragment oldFragment, @Nullable android.support.v4.app.Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
