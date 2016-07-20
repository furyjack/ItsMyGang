package com.example.admin.itsmygang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tvUsername;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUsername=(TextView)findViewById(R.id.username);
        auth=FirebaseAuth.getInstance();
        if(auth==null)
        {
            Toast.makeText(MainActivity.this, "Please Sign In Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
             finish();
        }

        String username=auth.getCurrentUser().getEmail();
        tvUsername.setText(username);



    }
}
