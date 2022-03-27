package com.cst2335.esim0001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String TAG = "ChatRoomActivity";
    EditText message;
    Button sendButton;
    Button receiveButton;
    ChatAdapter myAdapter;
    ArrayList<Message> messages = new ArrayList<Message>();  //array list that would contain messages.
    //Create an OpenHelper to store data:
    MyOpenHelper myOpener;
    SQLiteDatabase theDatabase;
    //lab 6
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Lab 6
        isTablet = true;

        FrameLayout frame1 = findViewById(R.id.frame1);
        if(frame1 == null){
            isTablet = false;
        }

        //initialize it in onCreate
        myOpener = new MyOpenHelper( this );
        //open the database:
        theDatabase = myOpener.getWritableDatabase();

        //load from the database:
        Cursor results = theDatabase.rawQuery( "Select * from " + MyOpenHelper.TABLE_NAME + ";", null );//no arguments to the query
        printCursor(results, theDatabase.getVersion());

        //list view created for holding messages.
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
                //insert into database:
                ContentValues newRow = new ContentValues();// like intent or Bundle

                //Message column.
                newRow.put(MyOpenHelper.COL_MESSAGE, typedMessage);

                //Send or receive column.
                //0 represents false in this column, this indicates that a message was received.
                newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 1);

                //Insert columns into the database.
                long id = theDatabase.insert(MyOpenHelper.TABLE_NAME, null, newRow); //returns the id

                messages.add(new Message(typedMessage, true, id));

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
                //insert into database:
                ContentValues newRow = new ContentValues();// like intent or Bundle

                //Message column.
                newRow.put(MyOpenHelper.COL_MESSAGE, typedMessage);

                //Send or receive column.
                //0 represents false in this column, this indicates that a message was received.
                newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 0);

                //Insert columns into the database.
                long id = theDatabase.insert(MyOpenHelper.TABLE_NAME, null, newRow); //returns the id

                messages.add(new Message(typedMessage, false, id));

                message.setText("");   //clear any text from the EditText.

                //notify adapter that new data was added at a row:
                myAdapter.notifyDataSetChanged();

            }
        });

        //lab 6
        myList.setOnItemClickListener( (list, view, position, id) -> {
            Message clickedMessage = messages.get(position);
            id = clickedMessage.getId();
            String typedMessage = clickedMessage.messageTyped;
            boolean isSend = clickedMessage.getIsSent();

            //CheckBox checkBox = findViewById(R.id.checkbox_lab6);
            DetailsFragment detailsFragment = new DetailsFragment();
            if(isTablet == true){ //If device is tablet
                //DetailsFragment detailsFragment = new DetailsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame1, detailsFragment);    // add Fragment
                ft.commit();
            }
            if(isTablet == false){ //If device is phone
                Intent goToEmptyActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                goToEmptyActivity.putExtra("message", typedMessage);
                goToEmptyActivity.putExtra("id", id);
                goToEmptyActivity.putExtra("isSent", isSend); //send data to EmptyActivity.

                startActivity(goToEmptyActivity);
            }
            //Send data to the Fragment
            Bundle args = new Bundle();
            args.putString("message", typedMessage);
            args.putLong("id", id);
            args.putBoolean("isSent", isSend);
            detailsFragment.setArguments(args);

        } );


        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            Message clickedMessage = messages.get(pos);
            id = clickedMessage.getId();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    .setMessage("The selected row is: " + pos + "\nThe database id is: " + id)
                    //Remove row from list and also delete it from the database.
                    .setPositiveButton("Yes", (click, arg) -> {

                        if(isTablet == true){
                            //DetailsFragment detailsFragment = new DetailsFragment();
                            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame1);
                            if(fragment != null)
                                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        //delete row from the database.
                        theDatabase.delete(MyOpenHelper.TABLE_NAME,
                                MyOpenHelper.COL_ID +" = ?", new String[] { Long.toString(clickedMessage.getId())  });

                        messages.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //if the user clicks No, no change should occur.
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

        public long getItemId(int position) { return (long) position; }  //public long getItemId(int position) { return (long) position; }

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
            if (thisRow.isSent)
                return sendView;


            return receiveView;
        }
    }

    /*Message class would be used for creating new messages and adding them to the Array List.*/
    public class Message{
        String messageTyped;
        boolean isSent; //boolean to show whether message is being sent, or received.
        long id;

        public Message(String messageTyped, boolean isSent, long _id) {
            this.messageTyped = messageTyped;
            this.isSent = isSent;
            this.id = _id;
        }

        public String getMessageTyped() {
            return messageTyped;
        }

        public boolean getIsSent() {
            return isSent;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    /*
    This function prints the cursor and also logs the cursor information for debugging purposes.
     */
    public Cursor printCursor( Cursor c, int version){

         //Convert column names to indices which can be used in displaying the items.
        int idIndex = c.getColumnIndex( MyOpenHelper.COL_ID );
        int  messageIndex = c.getColumnIndex( MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = c.getColumnIndex( MyOpenHelper.COL_SEND_RECEIVE);

        int rowCount = c.getCount();
        int columnCount = c.getColumnCount();

        //Logs
        Log.i(TAG, "Database version: " + version);
        Log.i(TAG, "Row count: " + rowCount);
        Log.i(TAG, "Column count: " + columnCount);

        Log.i(TAG, "Column Names: ");

        //for loop to print column names
        for(int i = 1; i < columnCount; i++){   //for(int i = 0; i < columnCount; i++) would include the _id column, which is not needed in this case.
            String columnName = c.getColumnName(i);
            //String columnNumber = Integer.toString(i);
            Log.i(TAG, "Column " + (i) + ": " + columnName); //Log.i(TAG, "Column " + (i + 1) + ": " + columnName);
        }

        while( c.moveToNext() ) //returns false if no more data
        {
            int id = c.getInt(idIndex);
            String message = c.getString( messageIndex );
            int isSent = c.getInt(sOrRIndex);

            //integers 1 and 0 are used to represent the boolean values "true" and "false".
            switch(isSent){
                case 1:
                    //populate array list with messages already stored in the database.
                    messages.add( new Message( message, true, id ));
                    break;
                case 0:
                    //populate array list with messages already stored in the database.
                    messages.add( new Message( message, false, id ));
                    break;
            }
        }
        return c;  //return the Cursor.
    }

}