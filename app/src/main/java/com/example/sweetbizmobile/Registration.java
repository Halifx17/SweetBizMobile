package com.example.sweetbizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputLayout editTextUsername, editTextEmail, editTextPassword;
    EditText editUsername, editEmail, editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editEmail = findViewById(R.id.Edit_Email);
        editUsername = findViewById(R.id.Edit_Username);
        editPassword = findViewById(R.id.Edit_Password);

        editTextEmail = findViewById(R.id.EditText_Email);
        editTextUsername = findViewById(R.id.EditText_Username);
        editTextPassword = findViewById(R.id.EditText_Password);

        mAuth = FirebaseAuth.getInstance();


    }

    public void registerFirebase(View view) {

        String username, email, password;

        username = editUsername.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();


        if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
            Toast.makeText(Registration.this, "Fields are Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError("Username is Required");
            editTextEmail.setError("Email is Required");
            editTextPassword.setError("Password is Required\nMinimum of 6 characters");
        }  else if (username.isEmpty()) {
            Toast.makeText(Registration.this, "Username is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError("Username is Required");
            editTextEmail.setError(null);
            editTextPassword.setError(null);

        }  else if (password.isEmpty()) {
            Toast.makeText(Registration.this, "Password is Required", Toast.LENGTH_LONG).show();

            editTextUsername.setError(null);
            editTextEmail.setError(null);
            editTextPassword.setError("Password is Required\nMinimum of 6 characters");

        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        User user = new User(username, email, password,"user");

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            openDialog();
                                        } else {
                                            Toast.makeText(Registration.this, "Failed Registration", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });
                    } else {
                        Toast.makeText(Registration.this, "Failed to register", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    public void openDialog(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Success");
        builder.setMessage("Registration Successful");
        builder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);

            }
        });

        builder.show();

    }
}