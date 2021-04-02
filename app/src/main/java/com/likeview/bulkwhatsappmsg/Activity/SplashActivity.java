package com.likeview.bulkwhatsappmsg.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.likeview.bulkwhatsappmsg.MainActivity;
import com.likeview.bulkwhatsappmsg.R;
import com.likeview.bulkwhatsappmsg.storage.SharedPrefManager;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                SharedPrefManager sfm = SharedPrefManager.getInstance(getApplicationContext());
                if(sfm.isLoggedIn())
                {
                    startActivity( new Intent( SplashActivity.this, MainActivity.class) );
                    finish();
                }else
                {
                    startActivity( new Intent( SplashActivity.this, VerifactionActivity.class) );
                    finish();
                }
                // This method will be executed once the timer is over

//                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(SplashActivity.this,
//                        READ_CONTACTS)
//                        != PackageManager.PERMISSION_GRANTED){
//
//                    requestPermissions( new String[]{READ_CONTACTS} ,1);
//                    requestPermissions( new String[]{READ_CALL_LOG} ,1);
////                    startActivity( new Intent( SplashActivity.this, MainActivity.class) );
////                    finish();
//                }
//                else {
//                    startActivity( new Intent( SplashActivity.this, MainActivity.class) );
//                    finish();
//                }
            }
        }, 2000);
    }

}