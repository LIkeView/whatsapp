package com.likeview.bulkwhatsappmsg.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.likeview.bulkwhatsappmsg.MainActivity;
import com.likeview.bulkwhatsappmsg.R;
import com.likeview.bulkwhatsappmsg.fragment.DemoFragment;
import com.likeview.bulkwhatsappmsg.fragment.FragmentContectList;
import com.likeview.bulkwhatsappmsg.model.ContactModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ContectListAdapter extends RecyclerView.Adapter<ContectListAdapter.UsersViewHolder> implements Filterable{
    private static final int REQUEST_PHONE_CALL = 1;
//    public ContectListAdapterEvente contectListAdapter;
    List<ContactModel> NewArray = new ArrayList<>(  );
    boolean  isEnable = false;
    boolean  isSelectAll = false;

    private String stringFile = Environment.getExternalStorageDirectory().getPath() + File.separator+"Download/"+"Test.pdf";
    private String stringImage = Environment.getExternalStorageDirectory().getPath() + File.separator+"Download/"+"test.jpg";
    ValueFilter valueFilter;
    private Activity context;
    private List<ContactModel> contactModels ;
    private List<ContactModel> contactModelsFilter ;

    public ContectListAdapter(Activity context, List<ContactModel> contactModels) {
        this.context = context;
//        this.contectListAdapter = contectListAdapter;
        this.contactModels = contactModels;
    }

    //    ValueFilter valueFilter;
//    private ArrayList<ListClientModel> listEvents;
//    ArrayList<ListClientModel> listEventsFiltered;
//
//
//    public ContectListAdapter(Activity context, ArrayList<ListClientModel> listEvents) {
//        this.context = context;
//        this.listEvents = listEvents;
//        this.listEventsFiltered = listEvents;
//    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.item_contact, parent, false);
        return new UsersViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {
        holder.tv_phonebookContact.setText( contactModels.get( position ).getNumber() );
        holder.tv_phonebookName.setText( contactModels.get( position ).getName().replace( " ","" ) );
//        holder.checkboxselect.setText( contactModels.get( position ).getName()  );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable){
                    ClickItem( holder );
                }
                else {
                    Toast.makeText( context,"You Clicked"+ contactModels.get( holder.getAdapterPosition() ),Toast.LENGTH_LONG ).show();
                }
            }
        } );
        if(isSelectAll){
            holder.checkboxselect.setVisibility( View.VISIBLE );
            holder.itemView.setBackgroundColor( Color.LTGRAY );
        }
        else {
            holder.checkboxselect.setVisibility( View.GONE );
            holder.itemView.setBackgroundColor( Color.TRANSPARENT );
        }
        holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isEnable)
                {
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate( R.menu.main_menu,menu );

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            isEnable = true;
                            ClickItem(holder);
//                            holder.checkboxselect.setText( contactModels.get( 0 ).getNumber() );

                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            int id = item.getItemId();

                            switch (id){
                                case R.id.menu_main_setting:
                                    if(NewArray.size() == contactModels.size() ){
                                        isSelectAll = false;
                                        NewArray.clear();
                                    }
                                    else {
                                        isSelectAll = true;
                                        NewArray.clear();
                                        NewArray.addAll( contactModels );
                                    }
                                    notifyDataSetChanged();
                                   break;
                                case R.id.menu_main_setting2:
//                                    int newarraysize = NewArray.size();
//                                    contactModels ;
                                    contactModels.clear();
                                    for(ContactModel s:NewArray){
                                        Log.d( "abc",""+s.getNumber() );
                                        contactModels.add( s );
                                    }
                                    notifyDataSetChanged();

//                                    Intent i = new Intent(context, DemoFragment.class);
//                                    context.startActivity(i);
                                    mode.finish();
                                    break;


                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {

                            isSelectAll = false;
                            isEnable = false;
                            NewArray.clear();
                            notifyDataSetChanged();
                        }
                    };

                    ((AppCompatActivity) v.getContext()).startActionMode( callback );
                }
                else {
                    ClickItem( holder );
                }
                return true;
            }
        } );

//        holder.checkboxselect.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    NewArray.add( contactModels.get( position ) );
//
//                }
//                else {
//                    NewArray.remove( contactModels.get( position ) );
//                }
//            }
//        } );
//        holder.checkboxselect.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    contectListAdapter.onEventClicked( contactModels.get(position) );
//                }
//                else {
//                    contactModels.remove( contactModels.get( position ) );
//                }
//            }
//        } );
//        holder.itemView.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File file = new File(stringFile);
//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed){
//
//
//                    if (!file.exists()){
//                        Toast.makeText(context, "File doesn't exists", Toast.LENGTH_LONG).show();
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
//                    sendIntent.setType("*/*");
//                    sendIntent.putExtra(Intent.EXTRA_STREAM,uriImage);
////                sendIntent.putExtra(Intent.EXTRA_STREAM,uriImage);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Sample text");
//                    Log.d( "abcd",""+contactModels.get( position ).getNumber().length() );
//
////                    if (contactModels.get( position ).getNumber().length() == 10){
////                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+contactModels.get( position ).getNumber())+"@s.whatsapp.net");
////                    }
////                    if (contactModels.get( position ).getNumber().length() == 11){
//                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+contactModels.get( position ).getNumber().substring( contactModels.get( position ).getNumber().length()-10,contactModels.get( position ).getNumber().length() ))+"@s.whatsapp.net");
////                    }
////                    if (contactModels.get( position ).getNumber().length() == 12){
////                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+contactModels.get( position ).getNumber().substring( 2,12 ))+"@s.whatsapp.net");
////                        Log.d( "91",""+contactModels.get( position ).getNumber().substring( 2,12 ) );
////                    }
////                    if (contactModels.get( position ).getNumber().length() == 13){
////                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+contactModels.get( position ).getNumber().substring( 2,12 ))+"@s.whatsapp.net");
////                        Log.d( "91",""+contactModels.get( position ).getNumber().substring( 3,13 ) );
////                    }
//                    sendIntent.setAction(Intent.ACTION_SENDTO);
//
//                    context.startActivity(sendIntent);
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
//                    Toast.makeText(context, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } );

    }

    private void ClickItem(UsersViewHolder holder) {
        ContactModel s = contactModels.get(holder.getAdapterPosition());
        if(holder.checkboxselect.getVisibility() == View.GONE){
            holder.checkboxselect.setVisibility( View.VISIBLE );
            holder.itemView.setBackgroundColor( Color.LTGRAY );

            NewArray.add( s );
        }
        else{
            holder.checkboxselect.setVisibility( View.GONE );

            holder.itemView.setBackgroundColor( Color.TRANSPARENT );

            NewArray.remove( s );
        }



    }


    @Override
    public int getItemCount() {
//                                            notifyDataSetChanged();

        int size;
        Log.d( "size",""+contactModels.size() );
        size = contactModels.size();
        return size;
    }

    @Override
    public final Filter getFilter() {
            valueFilter = new ContectListAdapter.ValueFilter();
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            if (charString.isEmpty()) {
                contactModelsFilter = contactModels;
            } else {
                List<ContactModel> filteredList = new ArrayList<>();
                for (ContactModel row : contactModels) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                        Toast.makeText( context, "andar"+filteredList.size(), Toast.LENGTH_SHORT ).show();
                    }
                    Toast.makeText( context, "bare"+filteredList.size(), Toast.LENGTH_SHORT ).show();

                }

                contactModelsFilter = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = contactModelsFilter;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            contactModelsFilter = (ArrayList<ContactModel>) results.values;
            notifyDataSetChanged();
        }

    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView tv_phonebookName,tv_phonebookContact;
        ImageView checkboxselect;

        public UsersViewHolder(View itemView) {
            super(itemView);
        tv_phonebookName = itemView.findViewById( R.id.tv_phonebookName );
        tv_phonebookContact = itemView.findViewById( R.id.tv_phonebookContact );
            checkboxselect = itemView.findViewById( R.id.checkboxselect );
        }

    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = context.getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

//    public interface ContectListAdapterEvente{
//        void onEventClicked(ContactModel info);
//    }
}
