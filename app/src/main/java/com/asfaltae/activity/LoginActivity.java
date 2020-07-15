package com.asfaltae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asfaltae.R;
import com.asfaltae.config.FirebaseConfiguration;
import com.asfaltae.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText fieldEmail, fieldPassword;
    private Button fieldLogin;
    private FirebaseAuth authentication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldEmail = findViewById(R.id.editEmail);
        fieldPassword = findViewById(R.id.editPassword);
        fieldLogin = findViewById(R.id.buttonLogin);

        fieldLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textEmail = fieldEmail.getText().toString();
                String textPassword = fieldPassword.getText().toString();

                // Validating that the fields were filled out correctly

                if (!textEmail.isEmpty()) {
                    if (!textPassword.isEmpty()) {

                        user = new User();
                        user.setEmail(textEmail);
                        user.setPassword(textPassword);
                        validateLogin();


                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Digite sua senha",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Digite seu email",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validateLogin() {

        authentication = FirebaseConfiguration.getFirebaseAuthentication();
        authentication.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,
                            "Login realizado com sucesso",
                            Toast.LENGTH_LONG).show();
                    openMainScreen();

                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Usuário não está cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Email e senha não correspondem a um usuário cadastrado";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void openMainScreen() {
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }

}