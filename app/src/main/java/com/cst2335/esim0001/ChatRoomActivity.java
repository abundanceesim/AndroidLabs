package com.cst2335.esim0001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String TAG = "ChatRoomActivity";
    EditText message;
    Button sendButton;
    Button receiveButton;
    ChatAdapter myAdapter;   //<<cannot be anonymous<<
    ArrayList<Message> messages = new ArrayList<Message>();  //array list that would contain messages.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.listView);
        myList.setAdapter( myAdapter = new ChatAdapter());


        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);
        message = findViewById(R.id.messageText);

        //if send button is clicked use the layout for sending a message
        sendButton.setOnClickListener( (click) -> {
            String typedMessage = message.getText().toString();
            Log.e(TAG, "Send button clicked");

            //Message thisRow = messages.get(position);
            if ( !typedMessage.isEmpty()) {
                messages.add(new Message(typedMessage, "send"));

                message.setText(""); //clear any text from the EditText.

                //notify adapter that new data was added at a row:
                myAdapter.notifyDataSetChanged();

            }
        });

        //if receive button is clicked use the layout for receiving a message
        receiveButton.setOnClickListener( (click) -> {
            Log.e(TAG, "Receive button clicked");
            String typedMessage = message.getText().toString();

            //Message thisRow = messages.get(position);
            if ( !typedMessage.isEmpty()) {
                messages.add(new Message(typedMessage, "receive"));

                message.setText("");   //clear any text from the EditText.

                //notify adapter that new data was added at a row:
                myAdapter.notifyDataSetChanged();

            }
        });

        /*Listener for listview entries.*/
        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    .setMessage("The selected row is: " + pos + "\nThe database id is: " + id)

                    .setPositiveButton("Yes", (click, arg) -> {
                        messages.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })

                    .setNegativeButton("No", (click, arg) -> { })

                    //Display the Alert Dialog.
                    .create().show();
            return true;
        });


    }

    private class ChatAdapter extends BaseAdapter {

        public int getCount() { return messages.size();}

        public Object getItem(int position) {
            Message thisRow = messages.get(position);
            return thisRow.messageTyped;
        }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //New row for the Send Layout
            View sendView = inflater.inflate(R.layout.sent_message, parent, false);
            TextView sentMessage = sendView.findViewById(R.id.sendText);
            sentMessage.setText( getItem(position).toString() );         //set text to be displayed for this row.
            ImageView sendImage = sendView.findViewById(R.id.sendImage); //image to be displayed for sent messages

            //New row for the Receive Layout
            View receiveView = inflater.inflate(R.layout.received_message, parent, false);
            TextView receivedMessage = receiveView.findViewById(R.id.receiveText);
            receivedMessage.setText( getItem(position).toString() );              //set text to be displayed for this row.
            ImageView receiveImage = receiveView.findViewById(R.id.receiveImage); //image to be displayed for received messages


            Message thisRow = messages.get(position);
            //if the value for the type of message is "send", return the send view, otherwise return the receive view.
            if (thisRow.sendOrReceive.equals("send"))
                return sendView;


            return receiveView;
        }
    }

    /*Message class would be used for creating new messages and adding them to the Array List.*/
    public class Message{
        String messageTyped;
        String sendOrReceive; //String to show whether message is being sent, or received.

        public Message(String messageTyped, String sendOrReceive) {
            this.messageTyped = messageTyped;
            this.sendOrReceive = sendOrReceive;
        }

        public String getMessageTyped() {
            return messageTyped;
        }

        public String getSendOrReceive() {
            return sendOrReceive;
        }
    }

}