package com.example.julius.rpglayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    /* Unique IDs for the different menu options */
    private static final int MENU_OPTION_SET_USER   = 0;
    private static final int MENU_OPTION_SET_SERVER = 1;

    private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private ArrayAdapter<ChatMessage> mAdapter = null;
    private SyncMessagesTask mSyncMsgTask = null;
    private SendMessageTask mSendMsgTask  = null;
    private String mUsername = "Anonymouse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Link the empty messages arraylist to the adapter */
        mAdapter = new ArrayAdapter<ChatMessage>(this,
                android.R.layout.simple_list_item_1,
                mMessages);

        /* Link the adapter to the ListView */
        ListView lstMessages = (ListView) findViewById(R.id.lst_messages);
        lstMessages.setAdapter(mAdapter);

        /* Add a default message to the list */
        mMessages.add(new ChatMessage("WelcomeBot", "No messages here yet!"));
        mAdapter.notifyDataSetChanged();

        /* Get a reference to the message text field */
        final EditText edtMessage = (EditText) findViewById(R.id.edt_message);

        /* Add functionality to the send button */
        ImageButton btnSend = (ImageButton) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* Check if we're already sending a message,
                         *  if so, then temporarily block sending new
                         *  ones until that's done */
                        if (mSendMsgTask != null) {
                            Toast.makeText(MainActivity.this,
                                    "Already sending a message",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        /* Get the current message string from the UI */
                        String msg = edtMessage.getText().toString();

                        /* Create a new SendMessageTask, an AsyncTask
                         *  object that we customized for our application.
                         *
                         *  We need to do this because we CAN'T run web
                         *  accessing code in our app's UI thread
                         *  (i.e. this one) */
                        mSendMsgTask = new SendMessageTask();

                        /* Execute the SendMessageTask with the
                         *  username and message as its parameters */
                        mSendMsgTask.execute(mUsername, msg);
                    }
                }
        );

        /* Hack to turn the Action Bar (i.e. bar at the top of
         *  the screen) red */
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.RED));

        return;
    }

    /* Override this function to add a menu to your app. This can
     *  or should be accessible at the top right of your screen */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        /* The following lines add items to the menu. The only
         *  important things here are:
         *      the 2nd argument (item id),
         *      the 3rd argument (order in menu), and
         *      the last argument (menu item text) */
        menu.add(0, MENU_OPTION_SET_USER, 0, "Set Username");
        menu.add(0, MENU_OPTION_SET_SERVER, 1, "Set Chat Server");
        return true;
    }

    /* Override this function as well to add functionality to
     *  your menu items */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_OPTION_SET_USER:
                displaySetUserDialog();
                return true;
            case MENU_OPTION_SET_SERVER:
                displaySetServerDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Builds a dialog box for setting the username from scratch and
     *  then displays it to the user. The result is saved as a member
     *  variable for the MainActivity */
    private void displaySetUserDialog() {
        /* Prepare an Editable Text Field from scratch. This text
         *  field will contain the user's choice of chat username */
        final EditText edtUsername = new EditText(this);
        edtUsername.setText(mUsername); // Displays the existing username

        /* Instantiate an Alert Dialog Box builder. This is how most
         *  Android Dialog Boxes are created on-the-fly */
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(this);

        /* Use the builder to set the different parameters of your dialog
         *  box. What we're using here are:
         *      Title   - the text at the top of the dialog box
         *      Message - text inside the dialog box
         *      View    - a "View" object inside the dialog box; in this
          *               case, we set it to the Editable Text Field we
          *               created earlier
          *     Pos Btn - Just a button really; you can "listen" for when
          *               the user clicks it so that the app can react
          *               appropriately
          *     Neg Btn - Same as Pos Btn
          */
        dlgBuilder.setTitle("Set Username")
                .setMessage("Enter your chat username:")
                .setView(edtUsername)
                .setPositiveButton("OK",
                        /* Dialog Buttons can have click listeners attached like this one */
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String username = edtUsername.getText().toString();
                                if (username.equals("") == false) {
                                    mUsername = username;
                                }

                                TextView txvUser = (TextView) findViewById(R.id.txv_username);
                                txvUser.setText("chatting as " + mUsername);

                                dialog.cancel(); // Closes the dialog box
                                return;
                            }
                        })
                .setNegativeButton("Cancel",
                        /* Dialog Buttons can have click listeners attached like this one */
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); // Closes the dialog box
                                return;
                            }
                        });

        /* Call the show() function on the builder to display the dialog
         *  box to the user. THIS IS VERY IMPORTANT. */
        dlgBuilder.show();
        return;
    }

    /* Builds a dialog box for setting the chat server from scratch and
     *  then displays it to the user. The result is saved in the CrossTalkApp
     *  object as the new chat server URL */
    private void displaySetServerDialog() {
        /* Prepare an Editable Text Field from scratch. This text
         *  field will contain the user's choice of chat server URL */
        final CrossTalkApp app = (CrossTalkApp) MainActivity.this.getApplication();
        final EditText edtServer = new EditText(this);
        edtServer.setText(app.getServerUrl()); // Displays the existing chat server

        /* Instantiate an Alert Dialog Box builder. This is how most
         *  Android Dialog Boxes are created on-the-fly */
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(this);

        /* Use the builder to set the different parameters of your dialog
         *  box. What we're using here are:
         *      Title   - the text at the top of the dialog box
         *      Message - text inside the dialog box
         *      View    - a "View" object inside the dialog box; in this
          *               case, we set it to the Editable Text Field we
          *               created earlier
          *     Pos Btn - Just a button really; you can "listen" for when
          *               the user clicks it so that the app can react
          *               appropriately
          *     Neg Btn - Same as Pos Btn
          */
        dlgBuilder.setTitle("Set Server")
                .setMessage("Enter your chat server URL:")
                .setView(edtServer)
                .setPositiveButton("OK",
                        /* Dialog Buttons can have click listeners attached like this one */
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String server = edtServer.getText().toString();
                                if (server.equals("") == false) {
                                    /* Propagate the new server URL to the
                                     *  CrossTalkApp object */
                                    app.setServer(server);
                                }

                                dialog.cancel(); // Closes the dialog box
                                return;
                            }
                        })
                .setNegativeButton("Cancel",
                        /* Dialog Buttons can have click listeners attached like this one */
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); // Closes the dialog box
                                return;
                            }
                        });

        /* Call the show() function on the builder to display the dialog
         *  box to the user. THIS IS VERY IMPORTANT. */
        dlgBuilder.show();
        return;
    }

    /* Custom AsyncTask for sending messages over HTTP
     *
     *  This task must be provided with a USERNAME and
     *  a MESSAGE string as the arguments when executed.
     */
    private class SendMessageTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            /* Get a reference to the CrossTalkApp */
            CrossTalkApp app = (CrossTalkApp) MainActivity.this.getApplication();

            /* Call the sendChatMessage() function on CrossTalkApp
             *  to perform the actual sending over Http for us */
            return app.sendChatMessage(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mSendMsgTask = null;

            /* Check if sending was successful */
            if (result == true) {
                /* Continue on to syncing the latest
                 *  chat messages from the chat server*/
                if (mSyncMsgTask != null) {
                    Toast.makeText(MainActivity.this,
                            "Already syncing messages",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                /* Create a new SyncMessagesTask, an AsyncTask
                 *  object that we customized for our application,
                 *  and execute it immediately without args/params */
                mSyncMsgTask = new SyncMessagesTask();
                mSyncMsgTask.execute();
            } else {
                /* Display a Toast if sending was unsuccessful */
                Toast.makeText(MainActivity.this,
                        "Sending failed",
                        Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(result);
            return;
        }
    }

    /* Custom AsyncTask for syncing chat messages over HTTP */
    private class SyncMessagesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            /* Get a reference to the CrossTalkApp */
            ChatApp app = (ChatApp) MainActivity.this.getApplication();

            /* Call the syncChatMessages() function to request the latest
             *  list of chat messages from our chat server */
            ArrayList<ChatMessage> syncedMessages = app.syncChatMessages();

            /* Since syncChatMessages() returns an arraylist of ChatMessage
             *  objects, we can check if it is not empty before attempting
             *  to update our message list in the MainActivity */
            if (syncedMessages.size() > 0) {
                /* Clear our old message list */
                mMessages.clear();

                /* Then copy over the contents of the new list */
                mMessages.addAll(syncedMessages);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /* Notify the adapter since we changed the contents of
             *  mMessages and it needs to update the list UI */
            mAdapter.notifyDataSetChanged();
            mSyncMsgTask = null;

            super.onPostExecute(aVoid);
            return;
        }
    }
}
}