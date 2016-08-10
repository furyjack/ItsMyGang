package com.example.admin.itsmygang.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.itsmygang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class SetupActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 145;
    FirebaseAuth auth;
    EditText etUsername;
    FloatingActionButton btnContinue;
    ProgressBar pbar;
    ImageView IvDisp;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            //Picasso.with(getApplicationContext()).load(uri).into(IvDisp);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                IvDisp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        auth=FirebaseAuth.getInstance();
        etUsername= (EditText) findViewById(R.id.et_username);
        btnContinue= (FloatingActionButton) findViewById(R.id.btn_continue);
        //pbar= (ProgressBar) findViewById(R.id.p_bar);
        IvDisp=(ImageView)findViewById(R.id.img_disp);


        if(auth==null)
        {
            Toast.makeText(SetupActivity.this, "Please Sign In Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
             finish();
        }


   IvDisp.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           Intent intent = new Intent();
// Show only images, no videos or anything else
           intent.setType("image/*");
           intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
           startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


       }
   });





        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  pbar.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.GONE);
                String username=etUsername.getText().toString();
                if(username.length()<5)
                {
                    etUsername.setError("Min 5 characters");
                   // pbar.setVisibility(View.GONE);
                    btnContinue.setVisibility(View.VISIBLE);
                    return;
                }

                FirebaseUser user =auth.getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SetupActivity.this, "Update Complete", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SetupActivity.this,MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SetupActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                                 //   pbar.setVisibility(View.GONE);
                                    btnContinue.setVisibility(View.VISIBLE);
                                }
                            }
                        });



            }
        });




    }
}
