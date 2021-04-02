package com.likeview.bulkwhatsappmsg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.likeview.bulkwhatsappmsg.adapter.ViewPageAdapter;
import com.likeview.bulkwhatsappmsg.fragment.DemoFragment;
import com.likeview.bulkwhatsappmsg.fragment.FragmentContectList;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;


//class MainActivityService extends Service {
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;
//    public Toolbar toolbar;
private static String TAG = "CheckUtil";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById( R.id.tablayout_id );
        viewPager = findViewById( R.id.viewpager_id );


//        this.getToolbar().setTitleTextColor(color);
// Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable( Color.parseColor("#0F9D58"));

        // Set BackgroundDrawable
//        actionBar.setBackgroundDrawable(colorDrawable);



//        setActionBar(  );
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Main Page");
//        }
//        toolbar.setSubtitle("Test Subtitle");
//        toolbar.inflateMenu(R.menu.main_menu);


//        if (!isAccessibilitySettingsOn(getApplicationContext())) {
//            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//            return;
//        }
        initializeReference();


    }
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;

        final String service = "Services.WhatAppAccessibilityService";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt( mContext
                            .getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED );
            Log.v( TAG, "accessibilityEnabled = " + accessibilityEnabled );
        } catch (Settings.SettingNotFoundException e) {
            Log.e( TAG,
                    "Error finding setting, default accessibility to not found: "
                            + e.getMessage() );
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(
                ':' );

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString( mContext
                            .getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES );
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString( settingValue );
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    if (accessabilityService.equalsIgnoreCase( service )) {
                        return true;
                    }
                }
            }
        }

        return accessibilityFound;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu,menu);
//        MenuItem menu_main_setting = menu.findItem(R.id.menu_main_setting);
////        menu_main_setting.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(MenuItem item) {
////                ContectListAdapter contectListAdapter ;
////                contectListAdapter
////                return false;
////            }
////        } );
//
//        return super.onCreateOptionsMenu(menu);
//    }

    void initializeReference() {

//        Settings.Secure.putString(getContentResolver(),
//                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, "bulkwhatsappmsg/WhatAppAccessibilityService");
//        Settings.Secure.putString(getContentResolver(),
//                Settings.Secure.ACCESSIBILITY_ENABLED, "1");

        ViewPageAdapter adapter = new ViewPageAdapter( getSupportFragmentManager() );
        adapter.AddFragment(new FragmentContectList(), "Contact" );
        adapter.AddFragment(new DemoFragment(), "Filter" );
        adapter.AddFragment(new DemoFragment(), "Sorted" );

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(MainActivity.this,
                READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions( new String[]{READ_CONTACTS} ,1);
            requestPermissions( new String[]{READ_CALL_LOG} ,1);
        }
        else {
//            getContect();
        }


        //adapter setup
        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );


//        isAccessibilitySettingsOn(MainActivity.this);

    }



//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }


//    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        try {
//            startActivityForResult(
//                    Intent.createChooser(intent, "Select a File to Upload"),
//                    FILE_SELECT_CODE);
//        } catch (android.content.ActivityNotFoundException ex) {
//            // Potentially direct the user to the Market with a Dialog
//            Toast.makeText(this, "Please install a File Manager.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case FILE_SELECT_CODE:
//                if (resultCode == RESULT_OK) {
//                    // Get the Uri of the selected file
//                    Uri uri = data.getData();
//                    Log.d("TAG", "File Uri: " + uri.toString());
//                    // Get the path
//
//                    String path = FileUtils.getPath(uri);
//
////                    String path = Environment
////                            .getExternalStorageDirectory()
////                            + File.separator
////                            + "Phoenix" + File.separator + "default";
//
//                    Log.d("TAG", "File Path: " + path);
//                    // Get the file instance
//                    // File file = new File(path);
//                    // Initiate the upload
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public static String getPath(Context context, Uri uri) throws URISyntaxException {
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = { "_data" };
//            Cursor cursor = null;
//
//            try {
//                cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                // Eat it
//            }
//        }
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//
//        return null;
//    }
//
//
////    public void buttonShareFile(View view){
////        File file = new File(stringFile);
////        if (!file.exists()){
////            Toast.makeText(MainActivity.this, "File doesn't exists", Toast.LENGTH_LONG).show();
////            Toast.makeText(MainActivity.this, "" + file, Toast.LENGTH_LONG).show();
////            return;
////        }
////        Intent intentShare = new Intent(Intent.ACTION_SEND);
////        intentShare.setType("application/pdf");
////        intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
////        startActivity(Intent.createChooser(intentShare, "Share the file ..."));
////    }
//
//
//
//
////    public void buttonShareText(){
////
//////                        String mobileNumber = editText_mobile.getText().toString();
//////                String message = editText_msg.getText().toString();
////                boolean installed = appInstalledOrNot("com.whatsapp");
////                if (installed){
////                    Intent intent = new Intent(Intent.ACTION_VIEW);
////                    intent.setData( Uri.parse("http://api.whatsapp.com/send?phone="+"+91 "+"9510202224" + "&text="+"message"));
////                    startActivity(intent);
////
//////                    Intent intentShare = new Intent(Intent.ACTION_SEND);
//////                    intentShare.setType("application/pdf");
//////                    intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
//////                    startActivity(Intent.createChooser(intentShare, "Share the file ..."));
////
////
//////                    Uri imgUri = Uri.parse(pictureFile.getAbsolutePath());
//////                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//////                    whatsappIntent.setType("text/plain");
//////                    whatsappIntent.setPackage("com.whatsapp");
//////                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//////                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
//////                    whatsappIntent.setType("image/jpeg");
//////                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//////
//////                    try {
//////                        startActivity(whatsappIntent);
//////                    } catch (android.content.ActivityNotFoundException ex) {
//////                        ToastHelper.MakeShortText("Whatsapp have not been installed.");
//////                    }
//////                }else {
////                    Toast.makeText(MainActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
////                }
////
////            }
//
//
////                            Intent intent = new Intent(Intent.ACTION_VIEW);
////                    intent.setData( Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+"9510202224" + "&text="+"Hello"));
////                    startActivity(intent);
////        Intent intentShare = new Intent(Intent.ACTION_SEND);
////        intentShare.setType("text/plain");
////        intentShare.putExtra(Intent.EXTRA_SUBJECT,"My Subject Here ... ");
////        intentShare.putExtra(Intent.EXTRA_TEXT,"My Text of the message goes here ... write anything what you want");
////
////        startActivity(Intent.createChooser(intentShare, "Shared the text ..."));
////    }
//    //Create method appInstalledOrNot
//    private boolean appInstalledOrNot(String url){
//        PackageManager packageManager =getPackageManager();
//        boolean app_installed;
//        try {
//            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
//            app_installed = true;
//        }catch (PackageManager.NameNotFoundException e){
//            app_installed = false;
//        }
//        return app_installed;
//    }


//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case android.R.id.home:
//                finish();
//                return super.onOptionsItemSelected( item );
//            default:
//                return super.onOptionsItemSelected( item );
//        }
//    }


}
