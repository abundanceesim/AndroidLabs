package com.cst2335.esim0001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        TextView myText = findViewById(R.id.text);//brings the textView from XML to Java
        myText.setText(R.string.welcome_message);

        //EditText editText = findViewById(R.id.edit);
        //editText.setInputType(61);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_SHORT).show();
        });

        Switch mySwitch = findViewById(R.id.switchB);


        mySwitch.setOnCheckedChangeListener( (cb, b) ->{  //b is the boolean, for on or off
            String t = "";

            if (b == true)
                t = getString(R.string.switchOn);
            if (b == false)
                t = getString(R.string.switchOff);

            Snackbar snack = Snackbar.make(mySwitch, "The switch is now " + t, Snackbar.LENGTH_SHORT);
            snack.setAction("Undo", click->cb.setChecked(!b));
            snack.show();

        });

    }
}