package com.cst2335.esim0001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;


public class EmptyActivity extends AppCompatActivity {
    String message;
    long id;
    boolean isSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty);

        DetailsFragment detailsFragment = new DetailsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame1, detailsFragment);    // add Fragment
        ft.commit();

        //getting data from ChatRoomActivity.
        Intent fromChatRoomActivity = getIntent();
        message = fromChatRoomActivity.getStringExtra("message");
        id = fromChatRoomActivity.getLongExtra("id", 0);
        isSend = fromChatRoomActivity.getBooleanExtra("isSent", false);

        //Send data to the Fragment using a Bundle object.
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putLong("id", id);
        args.putBoolean("isSent", isSend);
        detailsFragment.setArguments(args);

    }

}