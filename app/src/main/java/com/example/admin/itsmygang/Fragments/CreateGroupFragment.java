package com.example.admin.itsmygang.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

public class CreateGroupFragment extends DialogFragment {

    EditText Etusername;
    ImageButton Btnsubmit;
    ImageButton Btncancel;
    DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef=rootref.child("groups");
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_group, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Etusername=(EditText)rootView.findViewById(R.id.username);
        Btnsubmit=(ImageButton)rootView.findViewById(R.id.btn_submit);
        Btncancel=(ImageButton)rootView.findViewById(R.id.btn_cancel);

        Btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=Etusername.getText().toString();
                if(username.length()<5)
                {
                    Toast.makeText(getContext(), "Group name should be more than 5 char", Toast.LENGTH_SHORT).show();


                }
                else
                {

                   DatabaseReference groupname=groupRef.child(username);
                   DatabaseReference usergroup= rootref.child("Users").child(auth.getCurrentUser().getUid()).child("Groups");
                //    usergroup.child(username).setValue(username);
                    DatabaseReference child=usergroup.push();
                    child.setValue(username);
                    groupname.child("Creator").setValue(auth.getCurrentUser().getDisplayName());
                   dismiss();




            }
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
