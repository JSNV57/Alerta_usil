package com.inkadroid.alerta_usil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart(){
        super.onStart();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //intent para realizar un trabajo especifico : llamar a otra actividad despues de 8 segundos
                Intent intent = new Intent(Splash.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

}
