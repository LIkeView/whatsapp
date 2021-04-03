package com.likeview.bulkwhatsappmsg.fragment;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.likeview.bulkwhatsappmsg.BuildConfig;
import com.likeview.bulkwhatsappmsg.R;
import com.likeview.bulkwhatsappmsg.Services.MySMSservice;
import com.likeview.bulkwhatsappmsg.adapter.ContectListAdapter;
import com.likeview.bulkwhatsappmsg.model.ContactModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class FragmentContectList extends Fragment {

    private static final int PICK_IMAGE = 1;
    Uri uri;
    String path = null;
    Context context;

    private String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator+"Download/"+"Test.pdf";
    private String stringImage = Environment.getExternalStorageDirectory().getPath() + File.separator+"Download/"+"test.jpg";
    private RecyclerView recyclerView;
    private View v;
    SearchView searchView;
    SearchManager searchManager;
    Button SandButton,OpenButton;
    ContectListAdapter contectListAdapter;
    TextView tv_phonebookName,tv_phonebookContact;
    public static List<ContactModel> arrayList = new ArrayList<>(  );

    EditText editTextEnterText;
//    BoundService boundService;
//    boolean mServiceBound = false;

    Timer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_contect_list, container, false );


    }

    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        arrayList = new ArrayList<>(  );
        recyclerView = view.findViewById( R.id.recyclerView );
        editTextEnterText = view.findViewById( R.id.editTextEnterText );
        SandButton = view.findViewById( R.id.SandButton );
        OpenButton = view.findViewById( R.id.OpenButton );
        searchView = view.findViewById(R.id.searchView);


        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager( getContext() );
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager( layoutManager );





        OpenButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu( getActivity(),v );
                popupMenu.getMenuInflater().inflate( R.menu.menu_popup,popupMenu.getMenu() );
                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.itme1:
                                selectImage();
//                                Toast.makeText(getActivity(), "Image", Toast.LENGTH_SHORT).show();
                            break;
                            case R.id.itme2:
//                                String abc = "     -  951 02 022 24";
//                                Log.d( "abc::1::",""+abc );
//                                Log.d( "abc::2::","abc"+abc. );
                                Toast.makeText(getActivity(), "PDF", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return false;
                    }
                } );
                popupMenu.show();
            }
        } );

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions( new String[]{READ_CONTACTS} ,1);
//            requestPermissions( new String[]{READ_CALL_LOG} ,1);


        }
        else {
//            getContect();
//            notifyAll();
            contectListAdapter = new ContectListAdapter( getActivity(), getContect() );
            recyclerView.setAdapter( contectListAdapter );
        }




        SandButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File(stringFile);
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
//                    if (!file.exists()){
//                        Toast.makeText(getActivity(), "File doesn't exists", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    Uri uri = Uri.fromFile( file );
//                    Uri uri = Uri.fromFile( new File( stringFile ) );

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                        uri = FileProvider.getUriForFile( getActivity(), BuildConfig.APPLICATION_ID+".provider",file );
//                    }

//                    Uri uriImage = Uri.fromFile( new File( stringImage ) );


//                    Intent intent = new Intent( Settings.ACTION_ACCESSIBILITY_SETTINGS);
////                    Intent intent = new Intent( "android.settings.ACCESSIBILITY_SETTINGS");
////                    startActivityForResult(intent, 1);
//                    startActivity(intent);
                    Log.d( "URI::1::","Uri 1 :" +path) ;
                    Log.d( "URI::2::","Uri 2 :" +stringImage) ;
                    isAccessibilitySettingsOn(getContext(),MySMSservice.class);
//
//                    if (!isAccessibilitySettingsOn(getContext(),MySMSservice.class)) {
//                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//                    }
//                    else{
//                        MySMSservice.startActionWHATSAPP(getActivity(),editTextEnterText.getText().toString().trim(),path,
//                                "1",arrayList,false);
//                    }


                }else {
                    Toast.makeText(getActivity(), "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        } );


        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if(subfilesWithUserDetailHistories.contains(s)){
                contectListAdapter.getFilter().filter(s);
//                }else{
//                    Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    contectListAdapter.getFilter().filter(s);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });



//        for (int i = 0; i>arrayList.size();i++){
//            File file = new File(stringFile);
//            boolean installed = appInstalledOrNot("com.whatsapp");
//            if (installed){
//
//
//                if (!file.exists()){
//                    Toast.makeText(getActivity(), "File doesn't exists", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Uri uri = Uri.fromFile( new File( stringFile ) );
//                Uri uriImage = Uri.fromFile( new File( stringImage ) );
//
//
//                Intent sendIntent = new Intent("android.intent.action.SEND");
//                sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.ContactPicker"));
//                sendIntent.setType("application/pdf/plaintext");
//                sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
////                sendIntent.putExtra(Intent.EXTRA_STREAM,uriImage);
//                sendIntent.putExtra(Intent.EXTRA_TEXT,"Sample text");
//                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+arrayList.get( i ).getNumber())+"@s.whatsapp.net");
//                sendIntent.setAction(Intent.ACTION_SENDTO);
//                startActivity(sendIntent);
//
//            }else {
//                Toast.makeText(getActivity(), "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//            }
//        }


//
//        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());




//                tv_phonebook.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File file = new File(stringFile);
//
//
//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed){
//
//
//                    if (!file.exists()){
//                        Toast.makeText(getActivity(), "File doesn't exists", Toast.LENGTH_LONG).show();
//                        return;
//                    }
////                Intent intentShare = new Intent(Intent.ACTION_SEND);
////                intentShare.setType("application/pdf");
////                intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
////                startActivity(Intent.createChooser(intentShare, "Share the file ..."));
//
//
//
//
////                File outputFile = new File(Environment.getExternalStoragePublicDirectory
////                        (Environment.DIRECTORY_DOWNLOADS), "example.pdf");
//                    Uri uri = Uri.fromFile( new File( stringFile ) );
//                    Uri uriImage = Uri.fromFile( new File( stringImage ) );
//
//
//                    Intent sendIntent = new Intent("android.intent.action.SEND");
////                File f=new File("Your file path");
////                Uri uri = Uri.fromFile(f);
//                    sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.ContactPicker"));
//                    sendIntent.setType("application/pdf/plaintext");
//                    sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
////                sendIntent.putExtra(Intent.EXTRA_STREAM,uriImage);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Sample text");
//                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("919510202224")+"@s.whatsapp.net");
//                    sendIntent.setAction(Intent.ACTION_SENDTO);
//
//                    startActivity(sendIntent);
//
//
////                Intent share = new Intent(Intent.ACTION_VIEW);
////                share.setAction(Intent.ACTION_SEND);
////                share.setType("application/pdf");
////                share.putExtra(Intent.EXTRA_STREAM, uri);
////                share.putExtra("jid", PhoneNumberUtils.stripSeparators("919510202224")+"@s.whatsapp.net");
////                share.putExtra(Intent.EXTRA_TEXT,"Sample text");
////                share.setPackage("com.whatsapp");
////                share.setData( Uri.parse("http://api.whatsapp.com/send?phone="+"+91 "+"9510202224"));
////                share.setData( Uri.parse("http://api.whatsapp.com/send?phone="+"+91 "+"9510202224" + "&text="+"message2"));
//
////                startActivity(share);
//                }else {
//                    Toast.makeText(getActivity(), "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//                }
////
//
//
//
//            }
//        } );


    }

//    public void getData(View V){
//        List<ContactModel> selected;
//        selected = contectListAdapter.listofSelectedItem();
//        Log.d( "selcted",""+selected );
//    }

    private void selectImage() {
        if (ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE );
        } else {
            Intent galleryIntent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            startActivityForResult( galleryIntent, PICK_IMAGE );
        }

//        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
//        gallery.setType( "image/*" );
//        startActivityForResult( Intent.createChooser( gallery, "sellect picture" ), PICK_IMAGE );
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        switch (requestCode) {
            case PICK_IMAGE:

                try {
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    path = getPathFromURI(uri);
                    if (path != null) {
                        File f = new File(path);
                        uri = Uri.fromFile(f);
                    }

//                    this.moveimage = uri;
                    String a = Environment.getExternalStorageDirectory().getPath() + File.separator+"Download/"+"test.jpg";
//                    String path = uri.getPath();
//                    Toast.makeText(getActivity(), "Image : "+path +" : " + a, Toast.LENGTH_SHORT).show();
                    Log.d( "Image::1::","Image : 1 "+uri );
                                        Uri uri1 = Uri.fromFile( new File( a ) );

                    Log.d( "Image::Uri","Image : Uri "+uri1 );
                    Log.d( "Image::2::","Image : 2 "+a );
//                    this.sub_file_path = path;
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), uri );
//
//                        view_image.setImageBitmap( bitmap );
//                        String imagepath = data.getData().getPath();
////                       view_Dwg_File.setText( main_file_path );
////                       this.sub_file_path=imagepath;
////                       File file=new File(imagepath);
////                       view_Dwg_File.setText(file.getName());
////                       Uri yourUri = Uri.fromFile(file);
//
////                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file_path);
//                        btnUplode.setEnabled( true );
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
                } catch (Exception e) {
                    Log.e("FileSelectorActivity", "File select error", e);
                }
                break;

        }

    }

    public static String removeLeadingSpaces(String param)
    {
        if (param == null) {
            return null;
        }

        if(param.isEmpty()) {
            return "";
        }

        int arrayIndex = 0;
        while(true)
        {
            if (!Character.isWhitespace(param.charAt(arrayIndex++))) {
                break;
            }
        }
        return param.substring(arrayIndex-1);
    }

    private List<ContactModel> getContect() {
        final Cursor cursor =getActivity().getApplicationContext().getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC" );
        final int name = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME );
        final int mobile = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER  );
        while (cursor.moveToNext()){
            final ContactModel contactModels = new ContactModel( cursor.getString( name ),cursor.getString( mobile )  );
            arrayList.add(contactModels);
        }
        return arrayList;
    }




    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getActivity().getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

    private boolean isAccessibilitySettingsOn(Context mContext, Class<MySMSservice> clazz) {
        int accessibilityEnabled = 0;
        final String service = getContext().getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (getContext().getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            MySMSservice.startActionWHATSAPP(getActivity(), editTextEnterText.getText().toString().trim(), path,
                    "1", arrayList, false);
//            String settingValue = Settings.Secure.getString (getContext().getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
//            if (settingValue != null) {
//                colonSplitter.setString (settingValue);
//                while (colonSplitter.hasNext ()) {
//                    String accessibilityService = colonSplitter.next ();
//
//                    if (accessibilityService.equalsIgnoreCase (service)) {
//                        return true;
//                    }
//                }
//            }
        }
        else{
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }

        return false;
    }


//    @Override
//    public void onEventClicked(ContactModel info) {
//        Log.d( "arrayyyyyyy",""+info.getNumber() );
//    }
}