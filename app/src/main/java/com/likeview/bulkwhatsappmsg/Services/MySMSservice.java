package com.likeview.bulkwhatsappmsg.Services;

import android.app.Activity;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.likeview.bulkwhatsappmsg.BuildConfig;
import com.likeview.bulkwhatsappmsg.model.ContactModel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import javax.security.auth.callback.PasswordCallback;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MySMSservice extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.geniobits.autosmssender.action.SMS";
    private static final String ACTION_WHATSAPP = "com.geniobits.autosmssender.action.WHATSAPP";

    // TODO: Rename parameters
    private static final String MESSAGE = "com.geniobits.autosmssender.extra.PARAM1";
    private static final String COUNT = "com.geniobits.autosmssender.extra.PARAM2";
    private static final String MOBILE_NUMBER = "com.geniobits.autosmssender.extra.PARAM3";
    private static final String IS_EACH_WORD = "com.geniobits.autosmssender.extra.PARAM4";
    private static final String URIIMAGE = "com.geniobits.autosmssender.extra.PARAM5";
    private static final String FILEPATH = "com.geniobits.autosmssender.extra.PARAM6";
//    Uri uri;
    public MySMSservice() {
        super("MySMSservice");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, String message, String count, List<ContactModel> mobile_numbers) {

//        List<String> numbers =new ArrayList<String>();
//        for(int i = 0;i<mobile_numbers.size();i++){
//            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
//        }
//        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,"9510202224");
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method


    public static void startActionWHATSAPP(Activity context, String message,String uriImage, String count, List<ContactModel> mobile_numbers, Boolean isEachWord) {

        List<String> numbers =new ArrayList<String>();

        for(int i = 0;i<mobile_numbers.size();i++){
                numbers.add("91"+mobile_numbers.get(i).getNumber().replace( " ","" ).replace( "-","" ).substring( mobile_numbers.get(i).getNumber().length()-10,mobile_numbers.get(i).getNumber().trim().replace( " ","" ).replace( "-","" ).length() ));
                Log.d( "check::91",""+ mobile_numbers.get(i).getNumber().substring( mobile_numbers.get(i).getNumber().length()-10,mobile_numbers.get(i).getNumber().length() ));

        }

        String[] numbersArray = numbers.toArray(new String[0]);
        Log.d( "check::1",""+ numbersArray.length);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(MESSAGE, message);
        intent.putExtra("FILEPATH", uriImage);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        intent.putExtra(IS_EACH_WORD,isEachWord);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                handleActionSMS(message, count,mobile_number);
            } else if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String filepath = intent.getStringExtra("FILEPATH");
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                final boolean isEachWord = intent.getBooleanExtra(IS_EACH_WORD,false);
                try {
                    handleActionWHATSAPP(message, count,mobile_number,filepath,isEachWord);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(String message, String count, String[] mobile_number) {
        // TODO: Handle action Foo

        try {
            if(mobile_number.length!=0) {
                for(int j=0;j<mobile_number.length;j++) {

                    for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mobile_number[j], null, message, null, null);
                        sendBroadcastMessage("Result:"+ (i+1) + " "+ mobile_number[j]);
                    }

                }
            }
        }catch(Exception e){

        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String message, String count, String[] mobile_number,String filepath, boolean isEachWord) throws InterruptedException {

    if (filepath != null){
        Log.d( "Test::1",""+filepath );
        if (mobile_number.length != 0){
            PackageManager packageManager = getApplicationContext().getPackageManager();
            for (int j = 0; j < mobile_number.length; j++){
                for (int i = 0; i < Integer.parseInt( count.toString() ) ; i++ ) {
                    String number = mobile_number[j];
                    Intent sendIntent = new Intent(  );
//                    if (filepath != null) {
//                        File f = new File(filepath);
//                        uri = Uri.fromFile(f);
//                    }
                    Uri uri1 = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", new File( filepath ) );

//                    Uri uri1 = Uri.fromFile( new File( filepath ) );
//                    if (XBuild.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                        uri = FileProvider.getUriForFile( getApplicationContext(), getApplicationContext().getPackageName()+".provider",new File( filepath  ));
//                    }
//                    Log.d( "Test::2",""+number );

//                    Uri fileData = FileProvider.getUriForFile( getApplicationContext(), BuildConfig.APPLICATION_ID,getApplicationContext().getPackageName()+".provider",new File( filepath ) )
//                                        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.ContactPicker"));
//                    sendIntent.setType("text/plain");
                    sendIntent.putExtra( "jid", number + "@s.whatsapp.net" );
                    sendIntent.putExtra( Intent.EXTRA_TEXT, message );
                    sendIntent.setDataAndType(uri1, "text/*");

                    sendIntent.putExtra( Intent.EXTRA_STREAM,uri1 );
                    sendIntent.setAction( Intent.ACTION_SEND );
                    sendIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    sendIntent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                    sendIntent.setPackage( "com.whatsapp" );
//                    sendIntent.setType( "image/jpg" );
//                    sendIntent.setType( "text/plain" );
                    if (sendIntent.resolveActivity( packageManager ) != null) {
                        getApplicationContext().startActivity( sendIntent );
                        Thread.sleep( 10000 );
                        sendBroadcastMessage( "Result: " + number );
                    } else {
                        sendBroadcastMessage( "Result: WhatsApp Not installed" );
                    }
                }
            }
        }


        }
    else {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        if (mobile_number.length != 0) {
            Log.d( "Test::3",""+"Hello::3" );

            for (int j = 0; j < mobile_number.length; j++) {
                for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                    String number = mobile_number[j];
                    String url = null;
                    try {
                        url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message + "   ", "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
                    whatappIntent.setPackage("com.whatsapp");
                    whatappIntent.setData(Uri.parse(url));
                    whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (whatappIntent.resolveActivity(packageManager) != null) {
                        getApplicationContext().startActivity(whatappIntent);
                        Thread.sleep(10000);
                        sendBroadcastMessage("Result: " + number);
                    } else {
                        sendBroadcastMessage("Result: WhatsApp Not installed");
                    }
                }

            }
        }
    }




//        if(isEachWord){
//            try {
//                PackageManager packageManager = getApplicationContext().getPackageManager();
//                if (mobile_number.length != 0) {
//                    for (int j = 0; j < mobile_number.length; j++) {
//
//                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
//                            String[] words = message.split(" ");
//                            String number = mobile_number[j];
//                            for(int k=0;k<words.length;k++) {
//                                String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(words[k] + "   ", "UTF-8");
//                                Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
//                                whatappIntent.setPackage("com.whatsapp");
//                                whatappIntent.setData(Uri.parse(url));
//                                whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                if (whatappIntent.resolveActivity(packageManager) != null) {
//                                    getApplicationContext().startActivity(whatappIntent);
//                                    Thread.sleep(10000);
//                                    sendBroadcastMessage("Result: " + k);
//                                } else {
//                                    sendBroadcastMessage("Result: WhatsApp Not installed");
//                                }
//                            }
//                        }
//
//                    }
//                }
//            } catch (Exception e) {
//                sendBroadcastMessage("Result: " + e.toString());
//            }
//        }
//        else {
//            try {
//                PackageManager packageManager = getApplicationContext().getPackageManager();
//                if (mobile_number.length != 0) {
//                    for (int j = 0; j < mobile_number.length; j++) {
//
//                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
//                            String number = mobile_number[j];
//                            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message + "   ", "UTF-8");
//                            Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
//                            whatappIntent.setPackage("com.whatsapp");
//                            whatappIntent.setData(Uri.parse(url));
//                            whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            if (whatappIntent.resolveActivity(packageManager) != null) {
//                                getApplicationContext().startActivity(whatappIntent);
//                                Thread.sleep(10000);
//                                sendBroadcastMessage("Result: " + number);
//                            } else {
//                                sendBroadcastMessage("Result: WhatsApp Not installed");
//                            }
//                        }
//
//                    }
//                }
//            } catch (Exception e) {
//                sendBroadcastMessage("Result: " + e.toString());
//            }
//        }


    }



    private void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
