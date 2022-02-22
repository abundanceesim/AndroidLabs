package com.cst2335.esim0001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String TAG = "ChatRoomActivity";
    public static String messageType;
    EditText message;
    Button sendButton;
    Button receiveButton;
    boolean sendClicked;
    boolean receiveClicked;
    MyListAdapter myAdapter;   //<<cannot be anonymous<<
    ArrayList<Message> messages = new ArrayList<Message>(); // was formerly of type Message, could be changed back soon.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.listView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        sendClicked = false;
        receiveClicked = false;

        //if send button is clicked, message is being sent, use the layout for sending a message
        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);
        message = findViewById(R.id.messageText);


        sendButton.setOnClickListener( (click) -> {
            String typedMessage = message.getText().toString();
            Log.e(TAG, "Send button clicked");
            if ( !typedMessage.isEmpty()) {
                messages.add(new Message(typedMessage));

                message.setText(typedMessage);//clear the text

                //notify that new data was added at a row:
                myAdapter.notifyDataSetChanged(); //at the end of ArrayList,

            }
        });

        receiveButton.setOnClickListener( (click) -> {
            Log.e(TAG, "Receive button clicked");
            receiveClicked = true;
        });

    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return messages.size();}

        public Object getItem(int position) { return message.getText().toString(); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View sendView = inflater.inflate(R.layout.sent_message, parent, false);

            //set what the text should be for this row:
            TextView sentMessage = sendView.findViewById(R.id.sendText);
            sentMessage.setText( getItem(position).toString() );

            //Button b =  newView.findViewById(R.id.textGoesHere);
            //b.setText( getItem(position).toString() );

            //return it to be put in the table
            return sentMessage;
        }
    }


    public class Message{
        String messageTyped;

        public Message(String messageTyped) {
            this.messageTyped = messageTyped;
        }

        public String getMessageTyped() {
            return messageTyped;
        }
    }

}