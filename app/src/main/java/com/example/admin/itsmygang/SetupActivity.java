package com.example.admin.itsmygang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SetupActivity extends AppCompatActivity {


    FirebaseAuth auth;
    EditText etUsername;
    FloatingActionButton btnContinue;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        auth=FirebaseAuth.getInstance();
        etUsername= (EditText) findViewById(R.id.et_username);
        btnContinue= (FloatingActionButton) findViewById(R.id.btn_continue);
       pbar= (ProgressBar) findViewById(R.id.p_bar);


        if(auth==null)
        {
            Toast.makeText(SetupActivity.this, "Please Sign In Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
             finish();
        }




        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbar.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.GONE);
                String username=etUsername.getText().toString();
                if(username.length()<5)
                {
                    etUsername.setError("Min 5 characters");
                    pbar.setVisibility(View.GONE);
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
                                    pbar.setVisibility(View.GONE);
                                    btnContinue.setVisibility(View.VISIBLE);
                                }
                            }
                        });



            }
        });




    }
}
