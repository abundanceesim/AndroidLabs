package com.cst2335.esim0001;


import android.app.Activity;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "PROFILE_ACTIVITY";
    ImageButton imageButton;
    ImageView imgView;

    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.e(TAG, "In function: onActivityResult()");

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                        imgView.setImageBitmap(imgbitmap);
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.i(TAG, "User refused to capture a picture.");
                }
            } );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e(TAG, "In function: onCreate()");

        imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(click -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                myPictureTakerLauncher.launch(takePictureIntent);
            }
        });

        imgView = findViewById( R.id.imageButton);

        Intent fromMain = getIntent();      //latest edit
        String input = fromMain.getStringExtra("Email");      //latest edit
        EditText editEmail = findViewById(R.id.profile_email_editText);     //--last modification
        editEmail.setText(input);     //latest edit
        if ( !input.isEmpty()) {
            editEmail.setText(input);
        }

    }

   @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "In function: onStart()");
    }

    @Override //screen is visible but not responding
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In function: onResume()");
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "In function: onPause()");
    }

    @Override //not visible
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "In function: onStop()");
    }

    @Override  //garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In function: onDestroy()");
    }
}