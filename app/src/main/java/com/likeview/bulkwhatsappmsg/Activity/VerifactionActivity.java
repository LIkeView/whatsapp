package com.likeview.bulkwhatsappmsg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.likeview.bulkwhatsappmsg.MainActivity;
import com.likeview.bulkwhatsappmsg.R;
import com.likeview.bulkwhatsappmsg.api.Api;
import com.likeview.bulkwhatsappmsg.api.RetrofitClient;
import com.likeview.bulkwhatsappmsg.models.ApiResponse;
import com.likeview.bulkwhatsappmsg.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifactionActivity extends AppCompatActivity {

    EditText etMobile,etPass;
    Button btnSubmit;
    private SharedPrefManager sfm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verifaction );
        etMobile = findViewById( R.id.etMobile );
        etPass = findViewById( R.id.etPass );
        btnSubmit = findViewById( R.id.btnSubmit );
        btnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        } );

    }
    private void userLogin() {

        String email = etMobile.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (email.isEmpty()) {
            etMobile.setError( "Email is required" );
            etMobile.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            etPass.setError( "Password required" );
            etPass.requestFocus();
            return;
        }

        if (password.length() < 8) {
            etPass.setError( "Password should be atleast 8 character long" );
            etPass.requestFocus();
            return;
        }

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponse> call = api.login( email, password );


        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.body().getResCode() == 1) {
                    Log.d( "Dk", "" + apiResponse.getResMessage() );
                    Toast.makeText( VerifactionActivity.this, apiResponse.getResMessage(), Toast.LENGTH_LONG ).show();
                    sfm = SharedPrefManager.getInstance( getApplicationContext() );
                    sfm.saveUser( apiResponse.getResData().getProfileDetails().get( 0 ) );

                    Intent intent = new Intent( VerifactionActivity.this, MainActivity.class );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );
                } else {
                    Toast.makeText( VerifactionActivity.this, apiResponse.getResMessage(), Toast.LENGTH_LONG ).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d( "Login", "" + t.getLocalizedMessage() );

                Toast.makeText( VerifactionActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG ).show();

            }
        } );

    }

}