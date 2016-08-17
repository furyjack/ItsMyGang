package com.example.admin.itsmygang.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admin.itsmygang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGroupFragment extends DialogFragment {

    EditText Etusername;
    ImageButton Btnsubmit;
    ImageButton Btncancel;
    DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef=rootref.child("groups");
    FirebaseAuth auth=FirebaseAuth.getInstance();
    public static final String TAG="logs";

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public JoinGroupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_create_group, container, false);
        Log.d(TAG, "onCreateView: jg ");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Etusername=(EditText)rootView.findViewById(R.id.username);
        Btnsubmit=(ImageButton)rootView.findViewById(R.id.btn_submit);
        Btncancel=(ImageButton)rootView.findViewById(R.id.btn_cancel);

        Btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=Etusername.getText().toString();
                Query result = groupRef.equalTo(username);


//                else
//                {
//
//                    Grouppojo new_group=new Grouppojo(username,auth.getCurrentUser().getDisplayName(),1,null);
//                    DatabaseReference ng=groupRef.push();
//                    new_group.setUid(ng.getKey());
//                    ng.setValue(new_group);
//                    DatabaseReference usergroup= rootref.child("Users").child(auth.getCurrentUser().getUid()).child("Groups");
//                    DatabaseReference child=usergroup.child(ng.getKey());
//                    child.setValue(new_group);
//                    dismiss();
//
//
//
//
//                }
            }});

        Btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });





        return rootView;
    }


}

