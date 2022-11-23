package com.example.sweetbizmobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText editEmail, editPassword;
    TextInputLayout editTextEmail, editTextPassword;
    CheckBox remember;
    String ifChecked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.LogIn_Edit_Email);
        editPassword = findViewById(R.id.LogIn_Edit_Password);
        editTextEmail =  findViewById(R.id.EditText_Email);
        editTextPassword =  findViewById(R.id.EditText_Password);

        remember = findViewById(R.id.checkBox);

        SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = sharedPreferences.getString("remember","");
        if(checkbox.equals("true")){
            Toast.makeText(MainActivity.this,"Log In Successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);

        }else if(checkbox.equals("false")){
            Toast.makeText(MainActivity.this,"Please Sign In",Toast.LENGTH_SHORT).show();
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked()){
                    ifChecked = "true";

                }else if(!compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember","false");
                    editor.apply();


                }

            }
        });

    }




    public void LogIn(View view) {

        String email, password;
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if(email.isEmpty()&&password.isEmpty()){
            Toast.makeText(MainActivity.this, "Fields cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Email is required");
            editTextPassword.setError("Password is required");
        }
        else if(email.isEmpty()){
            Toast.makeText(MainActivity.this, "Email cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Password is required");
            editTextPassword.setError(null);
        }

        else if(password.isEmpty()){
            Toast.makeText(MainActivity.this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Password is required");
            editTextEmail.setError(null);
        }

        else{
            editTextEmail.setError(null);
            editTextPassword.setError(null);

            Log.d(TAG, email+password);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        if(ifChecked.equals("true")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("remember", "true");
                            editor.apply();
                        }
                        Toast.makeText(MainActivity.this,"Log In Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    public void registerButton(View view) {
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
    }
}