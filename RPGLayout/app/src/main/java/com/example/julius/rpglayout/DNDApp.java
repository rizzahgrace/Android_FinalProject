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
                             String charname) {
        SharedPreferences prefs =
                getSharedPreferences("com.example.julius.rpglayout;", Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = prefs.edit();

        edt.putString("USERNAME", username);
        edt.putString("PASSWORD", password);
        edt.putString("CHARNAME", charname);
        edt.commit();
        return;
    }

    public void saveCharacterData(String charname, int str, int inte, int dex, int con, int wis, int cha) {
        SharedPreferences prefs =
                getSharedPreferences("com.example.julius.rpglayout;", Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = prefs.edit();

        edt.putString("CHARNAE", charname);
        edt.putString("STRENGTH", Integer.toString(str));
        edt.putString("INTELLIGENXE", Integer.toString(inte));
        edt.putString("DEXTERITY", Integer.toString(dex));
        edt.putString("CONSTITUTION", Integer.toString(con));
        edt.putString("WISDOM", Integer.toString(wis));
        edt.putString("CHARISMA", Integer.toString(cha));
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
    public boolean saveAppUserCharacterList(String charname) {
        String filename = charname + "_characterlist.txt";

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
}
