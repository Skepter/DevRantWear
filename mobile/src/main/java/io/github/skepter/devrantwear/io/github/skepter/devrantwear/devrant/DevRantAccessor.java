package io.github.skepter.devrantwear.io.github.skepter.devrantwear.devrant;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jorel on 28/01/2017.
 */

/*
Some kind of class to access devRant

JavaRant is available, but I would rather learn how to use
GSON by myself now and then be able to apply it in the future.

Also, JavaRant uses Java 8 with lambda expressions which are
not compatible with Android (unfortunately).
 */
public class DevRantAccessor {


    public Rant getRant() {

        HttpURLConnection connection;
        InputStream inputStream;
        try {
            Log.d("DevRantAccessor", "Retrieving rant...");
            connection = (HttpURLConnection) new URL("https://www.devrant.io/api/devrant/rants/surprise?app=3").openConnection();
            inputStream = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
            JsonObject json = (new JsonParser().parse(new InputStreamReader(inputStream))).getAsJsonObject();
            inputStream.close();
            connection.disconnect();
            return new Rant(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public JsonObject getComments(int rantID) {
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            connection = (HttpURLConnection) new URL("https://www.devrant.io/api/devrant/rants/" + rantID + "?app=3").openConnection();
            inputStream = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
            JsonObject json = (new JsonParser().parse(new InputStreamReader(inputStream))).getAsJsonObject();
            inputStream.close();
            connection.disconnect();
            return json;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
