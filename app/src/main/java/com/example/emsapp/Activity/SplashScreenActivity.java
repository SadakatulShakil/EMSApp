package com.example.emsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.emsapp.MainActivity;
import com.example.emsapp.R;
import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private GifImageView splashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ///for .glf splash screen///
        splashView = findViewById(R.id.splashView);
       Thread timer  = new Thread(){
           public void run(){
               try {
                   sleep(3500);
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
               finally{
                   Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                   startActivity(intent);
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