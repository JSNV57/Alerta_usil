package com.inkadroid.alerta_usil;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Login extends AppCompatActivity {
    private  LoginFragment mainActivityFragment; //DECLARAMOS UNA VARIABLE TIPO EL FRAGMENTO CREADO
    //private MapsFragment mapsFragment;
    PackageInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        try {
            info = getPackageManager().getPackageInfo("com.inkadroid.alerta_usil", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        /*
        //Visualiza el Fragment MainActivityFragment
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mapsFragment = new MapsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mapsFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mapsFragment = (MapsFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
        */

        //Visualiza el Fragment MainActivityFragment
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainActivityFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainActivityFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainActivityFragment = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }






    }
}
