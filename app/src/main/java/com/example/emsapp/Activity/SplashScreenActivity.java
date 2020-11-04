package com.example.emsapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.emsapp.MainActivity;
import com.example.emsapp.R;
import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private GifImageView splashView;
    public static final String TAG = "splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ///for .glf splash screen///
        splashView = findViewById(R.id.splashView);
        runSplash();
    }

    private void runSplash() {
        Thread timer  = new Thread(){
            public void run(){
                try {
                    sleep(3500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally{
                    SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    String userName  = preferences.getString("loginState",null);
                    Log.d(TAG, "run: "+ userName);
                    if(userName == null){
                        Intent intent = new Intent(SplashScreenActivity.this, UserSignInActivity.class);
                        startActivity(intent);
                    }else if(userName!= null && userName.equals("Admin") ) {
                        Intent intent = new Intent(SplashScreenActivity.this, AdminControllerActivity.class);
                        intent.putExtra("pgId", userName);
                        startActivity(intent);
                    }else if(userName!= null) {
                        Intent intent = new Intent(SplashScreenActivity.this, UserHomePageActivity.class);
                        intent.putExtra("pgId", userName);
                        startActivity(intent);
                    }
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}