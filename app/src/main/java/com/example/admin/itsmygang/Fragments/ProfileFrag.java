package com.example.admin.itsmygang.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.itsmygang.Activity.MainActivity;
import com.example.admin.itsmygang.Activity.SignupActivity;
import com.example.admin.itsmygang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView dp;
    private TextView un;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FloatingActionButton btn;
    StorageReference userdispref;
    StorageReference ref;
    Uri imgUri;

    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {

            imgUri = data.getData();

            //Picasso.with(getApplicationContext()).load(uri).into(IvDisp);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                // Log.d(TAG, String.valueOf(bitmap));


                dp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_profile, container, false);
        dp= (ImageView) rootview.findViewById(R.id.civ_disp_update);
        un=(TextView)rootview.findViewById(R.id.tv_user_name);
        btn=(FloatingActionButton)rootview.findViewById(R.id.btn_con);
        ref = FirebaseStorage.getInstance().getReference();
        userdispref = ref.child("Users").child(auth.getCurrentUser().getUid()).child("DisplayPicture");
        if(auth.getCurrentUser()==null)
        {
            startActivity(new Intent(getContext(), SignupActivity.class));


        }

        un.setText(auth.getCurrentUser().getDisplayName());
        if(auth.getCurrentUser().getPhotoUrl()==null)
        {
            dp.setBackgroundResource(R.drawable.ic_account);
        }
        else
        {
            Picasso.with(getContext()).load(auth.getCurrentUser().getPhotoUrl()).fit().into(dp);
        }

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btn.setVisibility(View.INVISIBLE);
                final FirebaseUser user = auth.getCurrentUser();

                final UploadTask uploadDp = userdispref.putFile(imgUri);
                uploadDp.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(taskSnapshot.getDownloadUrl())
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Update Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getContext(), MainActivity.class));

                                        } else {
                                            Toast.makeText(getContext(), "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                                            //   pbar.setVisibility(View.GONE);
                                            btn.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(), "Some error occured", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });






        return rootview;
    }

}
