package com.example.soc2.profilecomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.soc2.R;

public class otheruserprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruserprofile);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Toast.makeText(this, "id :"+ id, Toast.LENGTH_SHORT).show();
    }
}