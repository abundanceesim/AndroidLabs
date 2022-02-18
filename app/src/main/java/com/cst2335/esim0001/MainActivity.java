package com.cst2335.esim0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "StateChange";
    public final static String PREFERENCES_FILE = "MyData_Lab3";
    SharedPreferences sharedPrefs;
    Button loginButton;
    EditText editEmail;
    String emailString;
    String previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        Log.i(TAG, "onCreate");

        editEmail = findViewById(R.id.emailEditText);
        sharedPrefs = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        previous = sharedPrefs.getString("Email", "");
        editEmail.setText(previous);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

        editEmail = findViewById(R.id.emailEditText);
        loginButton = findViewById(R.id.loginButton);
        emailString = editEmail.getText().toString();

        sharedPrefs = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);

        loginButton.setOnClickListener(click -> {

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("Email", emailString);

            editor.apply();
            //editor.commit();  //was advised by compiler to use apply() instead.
            Toast.makeText(MainActivity.this, "information saved", Toast.LENGTH_LONG).show();//saves the edit

            //Go to Profile page when button is clicked.
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

            goToProfile.putExtra("Email", emailString);

            startActivity( goToProfile );
        });


    }

}