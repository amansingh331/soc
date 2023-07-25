package com.example.soc2.messagecomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soc2.R;

import java.util.List;

public class singlechat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlechat);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        String username = intent.getStringExtra("username");
        String profile_link = intent.getStringExtra("profile_link");


        if(position == -1){
            finish(); // go back to the previous activity
        }else{
            TextView usernameset = findViewById(R.id.username);
            usernameset.setText(username);

            ImageView circularImage = findViewById(R.id.circularImage);
            if (profile_link == null) {
                profile_link = "profile";
            }
            int imageResId = getResources().getIdentifier(profile_link, "drawable", getPackageName());
            circularImage.setImageResource(imageResId);
        }

        ImageView myImage = findViewById(R.id.backButton);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // go back to the previous activity
            }
        });
    }
}