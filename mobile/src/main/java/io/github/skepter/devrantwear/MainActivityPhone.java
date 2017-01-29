package io.github.skepter.devrantwear;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import io.github.skepter.devrantwear.io.github.skepter.devrantwear.devrant.DevRantAccessor;
import io.github.skepter.devrantwear.io.github.skepter.devrantwear.devrant.RawRant;

public class MainActivityPhone extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static GoogleApiClient googleApiClient;
    public static Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);

        myContext = this.getApplicationContext();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        googleApiClient.connect();

        Log.d("MainActivityPhone", "Application started!");

        new DevRantAccessor().getRant();


        //TODO Add some kind of feature to let the user login
        //(Used to ++ and -- rants)

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        TextView t = (TextView) findViewById(R.id.textView);
        t.setText("We are connected :D");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
