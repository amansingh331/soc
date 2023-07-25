package com.example.soc2.messagecomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.soc2.R;
import com.example.soc2.home;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class messagelist extends AppCompatActivity {

    public class Chats{
        public String user_name;
        public String last_msg;
        public String last_msg_time;
        public String profile_img;
    }

    List<Chats> msg_p = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        for (int i = 0; i < 20; i++) {

            Chats inp = new Chats();
            inp.user_name = "aadf";
            inp.last_msg ="sss";
            inp.last_msg_time = "8888";
            inp.profile_img = "proimg";
            msg_p.add(inp);
        }




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelist);

        recyclerView = findViewById(R.id.messagerecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageadapter m = new messageadapter(msg_p);
        recyclerView.setAdapter(m);

        ImageView myImage = findViewById(R.id.backButton);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // go back to the previous activity
            }
        });
    }
}