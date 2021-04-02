package com.likeview.bulkwhatsappmsg.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.likeview.bulkwhatsappmsg.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemoFragment extends Fragment {


    Chip chip;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_demo, container, false );

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        chip = view.findViewById( R.id.chip );

//        chip.setOnCloseIconClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText( getActivity(), "Close is Clicked", Toast.LENGTH_SHORT ).show();
//            }
//        } );

    }
}