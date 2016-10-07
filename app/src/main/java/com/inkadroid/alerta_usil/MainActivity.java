package com.inkadroid.alerta_usil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MainActivityFragment mainActivityFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_main);



        //Visualiza el Fragment MainActivityFragment
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainActivityFragment = new MainActivityFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainActivityFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainActivityFragment = (MainActivityFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }



    }
}
