package com.asfaltae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private EditText fieldName, fieldSurname, fieldEmail, fieldPassword;
    private Button fieldRegister;
    private FirebaseAuth authentication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fieldName = findViewById(R.id.editName);
        fieldSurname = findViewById(R.id.editSurname);
        fieldEmail = findViewById(R.id.editEmail);
        fieldPassword = findViewById(R.id.editPassword);
        fieldRegister = findViewById(R.id.buttonRegister);

        fieldRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = fieldName.getText().toString();
                String textSurname = fieldSurname.getText().toString();
                String textEmail = fieldEmail.getText().toString();
                String textPassword = fieldPassword.getText().toString();

                // Validating that the fields were filled out correctly
                if (!textName.isEmpty()) {
                    if (!textSurname.isEmpty()) {
                        if (!textEmail.isEmpty()) {
                            if (!textPassword.isEmpty()) {

                                user = new User();
                                user.setName(textName);
                                user.setSurname(textSurname);
                                user.setEmail(textEmail);
                                user.setPassword(textPassword);
                                registerUser();


                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        "Digite uma senha",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    "Escolha um endereço de email",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this,
                                "Digite o sobrenome",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Digite o nome",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void registerUser() {

        authentication = FirebaseConfiguration.getFirebaseAuthentication();
        authentication.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,
                            "Sucesso ao cadastrar o usuário",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "Esta conta já foi cadastrada";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(RegisterActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}