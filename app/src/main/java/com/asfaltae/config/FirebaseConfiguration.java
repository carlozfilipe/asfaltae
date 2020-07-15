package com.asfaltae.config;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfiguration {

    private static FirebaseAuth authentication;

    // Returns the firebase auth instance
    public static FirebaseAuth getFirebaseAuthentication() {

        if (authentication == null) {
            authentication = FirebaseAuth.getInstance();
        }
        return authentication;
    }

}
