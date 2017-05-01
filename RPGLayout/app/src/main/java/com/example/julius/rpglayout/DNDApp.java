package com.example.julius.rpglayout; /**
 * Created by Rizzah on 5/1/2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.julius.rpglayout.Chat;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DNDApp extends Application {
    private ArrayList<Character> mCharacterList = new ArrayList<>();
    private ArrayList<Chat> mMessageList = new ArrayList<>();

    public ArrayList<Character> getCharList() { return mCharacterList; }
    public ArrayList<Chat> getMessageList() { return mMessageList; }

    public void saveUserData(String username, String password,
                             String charname, String desc) {
        SharedPreferences prefs =
                getSharedPreferences("com.example.julius.rpglayout;", Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = prefs.edit();

        edt.putString("USERNAME", username);
        edt.putString("PASSWORD", password);
        edt.putString("CHARNAME", charname);
        edt.commit();
        return;
    }

    /* Methods for reloading data from the app's SharedPrefs */
    public String getAppUser() {
        SharedPreferences prefs =
                getSharedPreferences("com.example.julius.rpglayout;", Context.MODE_PRIVATE);

        return prefs.getString("USERNAME", "");
    }

    public String getAppUserPassword() {
        SharedPreferences prefs =
                getSharedPreferences("com.example.julius.rpglayout;", Context.MODE_PRIVATE);

        return prefs.getString("PASSWORD", "");
    }

    /* ************************************************************** */
    /* Public Methods for saving user data to files on the filesystem */
    /* ************************************************************** */
    public boolean saveAppUserFriendList(String username) {
        String filename = username + "_characterlist.txt";

        String fileContents = "";
        for (int i = 0; i < mCharacterList.size(); i++) {
            Character fr = mCharacterList.get(i);

            fileContents += fr.getUsername() + "," +
                    fr.getCharname()  + "," +
                    fr.getStrength() + "," +
                    fr.getIntelligence() + "," +
                    fr.getDexterity() + "," +
                    fr.getConstitution() + "," +
                    fr.getWisdom() + "," +
                    fr.getCharisma() + "\n";
        }

        return writeFile(filename, fileContents);
    }

    /* ***************************************************************** */
    /* Public Methods for loading user data from files on the filesystem */
    /* ***************************************************************** */
    public boolean loadAppUserFriendList(String username) {
        /* Get storage path */
        String loadPath = getStoragePath();

        /* Check if file exists */
        File loadFile = new File(loadPath, username + "_characterist.txt");
        if (loadFile.exists() == false) {
            Log.e("DNDApp", "File not loaded because it does not exist");
            return false;
        }

        String contents = "";
        try {
            /* Get file input stream */
            FileInputStream fis = new FileInputStream( loadFile );

            /* Read from file input stream */
            while (fis.available() > 0) {
                byte buf[] = new byte[32];
                int bytesRead = fis.read(buf, 0, 32);
                contents += new String(buf, 0, bytesRead);
            }

            /* close file input stream */
            fis.close();
        } catch (Exception e) {
            Log.e("DNDApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        /* Display the contents in Android Monitor */
        Log.d("DNDApp", "File Read Done:");
        Log.d("DNDApp", "    " + contents );

        /* Parse the file contents */
        boolean result = parseFriendsToList(contents);

        return result;
    }


    /* ******************************************************** */
    /* Private Methods for parsing data files on the filesystem */
    /* ******************************************************** */
    private boolean parseFriendsToList(String contentsFriendFile) {
        /* Clear the existing friend list */
        mCharacterList.clear();

        /* Divide the contents of the friend list file by newlines (\n)
         *  to obtain each individual line in the file */
        String lines[] = contentsFriendFile.split("\n");

        /* Cycle through each line */
        for (int i = 0; i < lines.length; i++) {
            /* Split the current line by commas (, ) to separate the saved pieces of info */
            String characterInfo[] = lines[i].split(",");

            /* Store each piece of information in a temporary variable */
            String username = characterInfo[0];
            String charactername = characterInfo[1];
            int strength = Integer.parseInt(characterInfo[2]);
            int intelligence = Integer.parseInt(characterInfo[3]);
            int dexterity = Integer.parseInt(characterInfo[4]);
            int constitution = Integer.parseInt(characterInfo[5]);
            int wisdom = Integer.parseInt(characterInfo[6]);
            int charisma = Integer.parseInt(characterInfo[7]);

            /* Create a Friend object from the info */
            Character newCharacter = new Character( username, charactername, strength, intelligence, dexterity, constitution, wisdom, charisma );

            /* Add the new Friend object to the Friend list */
            mCharacterList.add(newCharacter);
        }

        return true;
    }


    private String getStoragePath() {
        String storagePath = getFilesDir().toString();
        return storagePath;
    }

    private boolean writeFile(String fileName, String data) {
        /* Get storage path */
        String savePath = getStoragePath();

        /* Check if path exists */
        File pathStorage = new File(savePath);
        if (pathStorage.exists() == false) {
            /* Create the directory */
            pathStorage.mkdirs();
        }

        /* Check if the file exists */
        File saveFile = new File(savePath, fileName);
        if (saveFile.exists() == false) {
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                Log.e("DNDApp", "Exception occurred: " + e.getMessage());
                return false;
            }
        }

        try {
            /* Get file output stream */
            FileOutputStream fos = new FileOutputStream(saveFile, false);

            /* Write to file */
            fos.write(data.getBytes());

            /* close the output stream */
            fos.close();
        } catch (Exception e) {
            Log.e("DNDApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        return true;
    }



























    private HttpClient mHttpClient = new DefaultHttpClient();
    private ArrayList<Chat> mChatMessages = new ArrayList<>();

    private String mServer = "http://192.168.1.14:8000/"; /* TODO Set default server URL here */

    /* Returns the current chat server URL */
    public String getServerUrl() {
        return mServer;
    }

    /* Sets the chat server URL to something else */
    public boolean setServer(String serverUrl) {
        mServer = serverUrl;
        return true;
    }

    /* Sends a chat message through HTTP to the chat server */
    public boolean sendChatMessage(String usr, String msg) {
        /* To use HTTP Post to deliver parameters (e.g. a username and
         *  a message in our case), we have to create an arraylist of
         *  NameValuePair objects like so */
        ArrayList<NameValuePair> postParams = new ArrayList<>();
        postParams.add(new BasicNameValuePair("usr", usr));
        postParams.add(new BasicNameValuePair("msg", msg));

        /* Create the base HTTP POST request with the URL of the chat
         *  server plus the resource path where we can send messages */
        HttpPost httpPost = new HttpPost(mServer + "send_message");
        try {
            /* Store our POST parameters as an Entity in our HttpPost */
            httpPost.setEntity( new UrlEncodedFormEntity(postParams) );

            /* Execute our HTTP POST request */
            HttpResponse response = mHttpClient.execute(httpPost);

            /* We can interpret the response here, but chose not to since
             *  it isn't that important to use now */
        } catch (Exception e) {
            Log.e("CrossTalkApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        return true;
    }

    /* Syncs chat messages through HTTP with the chat server */
    public ArrayList<ChatMessage> syncChatMessages() {
        String contents = "";

        /* Create the base HTTP GET request with the URL of the chat
         *  server plus the resource path where we can retrieve messages */
        HttpGet request = new HttpGet(mServer + "get_messages");

        try {
            /* Execute our HTTP GET request */
            HttpResponse response = mHttpClient.execute(request);

            /* Use the convenience class EntityUtils to turn the contents
             *  of the chat server's response to our HTTP GET request into
             *  a string */
            contents = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            Log.e("CrossTalkApp", "Exception occurred: " + e.getMessage());
            contents = "";
        }

        /* Pass the chat server's HTTP response contents to another function
         *  for further processing/parsing */
        return parseChatMessages(contents);
    }

    /* Parses chat messages given the contents of the HTTP response string of
     *  the chat server */
    private ArrayList<ChatMessage> parseChatMessages(String respContents) {
        Log.i("CrossTalkApp", respContents);

        /* Clear the current list of Chat Messages */
        mChatMessages.clear();

        /* The structure of the data returned by the chat server is as follows:
         *  <user>|<message>;<user>|<message>;...
         *
         *  So to get each message piece, we split by the (;) first */
        String messages[] = respContents.split(";");

        /* Cycle through each message piece */
        for (int i = 0; i < messages.length; i++) {
            /* Each message has a 'user' and a 'message' part, separated
             *  by (|). We need to split this as well. */
            String messagePart[] = messages[i].split("\\|");

            /* Check if we have exactly two parts in the message
             *  (one for the username and one for the actual msg);
             *  if not then this message is malformed and we must
             *  skip it */
            if (messagePart.length != 2) {
                continue;
            }

            /* Get each part of the message */
            String user = messagePart[0];
            String msg = messagePart[1];

            /* Then add it to our chat messages list */
            mChatMessages.add(new ChatMessage(user, msg));
        }

        /* Debug: Check if the messages are being properly parsed via
         *  the Android Monitor on your IDE */
        for (ChatMessage msg : mChatMessages) {
            Log.d("CrossTalkApp", "Messages --> " + msg.toString());
        }

        /* Finally return the chat messages list we built up */
        return mChatMessages;
    }
}
