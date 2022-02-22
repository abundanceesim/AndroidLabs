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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "PROFILE_ACTIVITY";
    ImageButton imageButton;
    ImageView imgView;
    public static final String ON_ACTIVITY_RESULT = "onActivityResult()";
    public static final String ON_CREATE = "onCreate()";
    public static final String ON_START = "onStart()";
    public static final String ON_PAUSE = "onPause()";
    public static final String ON_RESUME = "onResume()";
    public static final String ON_DESTROY = "onDestroy()";
    public static final String ON_STOP = "onStop()";


    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.e(TAG, "In function: " + ON_ACTIVITY_RESULT);

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

        Log.e(TAG, "In function: " + ON_CREATE);

        imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(click -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                myPictureTakerLauncher.launch(takePictureIntent);
            }
        });

        imgView = findViewById( R.id.imageButton);

        Intent fromMain = getIntent();
        String input = fromMain.getStringExtra("Email");
        EditText editEmail = findViewById(R.id.profile_email_editText);
        editEmail.setText(input);
        if ( !input.isEmpty()) {
            editEmail.setText(input);
        }

        //Lab 4 update
        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener( (click) -> {
            Intent chatPage = new Intent(ProfileActivity.this,   ChatRoomActivity.class  );
            startActivity(chatPage);
        });
    }

   @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "In function: " + ON_START);
    }

    @Override //screen is visible but not responding
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In function: " + ON_RESUME);
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "In function: " + ON_PAUSE);
    }

    @Override //not visible
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "In function: " + ON_STOP);
    }

    @Override  //garbage collected
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In function: " + ON_DESTROY);
    }
}