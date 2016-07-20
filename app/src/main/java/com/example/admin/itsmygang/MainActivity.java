package com.example.admin.itsmygang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user= (TextView) findViewById(R.id.tvuser);
        auth=FirebaseAuth.getInstance();

        user.setText(auth.getCurrentUser().getDisplayName());


    }


}
