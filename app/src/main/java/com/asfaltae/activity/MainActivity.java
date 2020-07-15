package com.asfaltae.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asfaltae.R;
import com.asfaltae.activity.LoginActivity;
import com.asfaltae.activity.RegisterActivity;
import com.asfaltae.config.FirebaseConfiguration;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_orange_dark)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_orange_dark)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_orange_dark)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_register)
//                .canGoForward(false)
                .build());

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLoggedUser();
    }

    public void bntLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void bntRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void checkLoggedUser() {
        authentication = FirebaseConfiguration.getFirebaseAuthentication();
        authentication.signOut();
        if (authentication.getCurrentUser() != null) {
            openMainScreen();
        }
    }

    public void openMainScreen() {
        startActivity(new Intent(this, MapsActivity.class));

    }

}