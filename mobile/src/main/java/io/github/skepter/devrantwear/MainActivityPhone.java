package io.github.skepter.devrantwear;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import io.github.skepter.devrantwear.io.github.skepter.devrantwear.devrant.DevRantAccessor;

public class MainActivityPhone extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static GoogleApiClient googleApiClient;
    public static Context myContext;

    public final static String TAG = "MainActivityPhone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);

        myContext = this.getApplicationContext();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .addApi(AppIndex.API).build();

        googleApiClient.connect();

        Log.d(TAG, "Application started!");

        Log.d(TAG, "Preparing test for comments:");

        new Thread(new Runnable() {

            @Override
            public void run() {
                JsonObject json = new DevRantAccessor().getComments(18023);
                JsonArray comments = json.get("comments").getAsJsonArray();
                for (JsonElement ob : comments) {
                    JsonObject comment = ob.getAsJsonObject();
                    Log.d(TAG, comment.get("user_username").getAsString() + ": " + comment.get("body").getAsString());
                }
            }
        }).start();


    }

    private void check(final GoogleApiClient client) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Node> connectedNodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes();
                if(connectedNodes.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView t = (TextView) findViewById(R.id.textView);
                            t.setText("No devices found D:");
                        }
                    });
                    return;
                }
                for(final Node node : connectedNodes) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView t = (TextView) findViewById(R.id.textView);
                            t.setText("Connected to device: " + node.getDisplayName());
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        check(googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MainActivityPhone Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient.connect();
        AppIndex.AppIndexApi.start(googleApiClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(googleApiClient, getIndexApiAction());
        googleApiClient.disconnect();
    }
}
