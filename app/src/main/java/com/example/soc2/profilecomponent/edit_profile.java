package com.example.soc2.profilecomponent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.soc2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONException;
import org.json.JSONObject;

public class edit_profile extends AppCompatActivity {

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText fullname, collegename, bio, place, headline;
        Button btn_sub;

        fullname = findViewById(R.id.full_name);
        collegename = findViewById(R.id.collegeEditText);
        bio = findViewById(R.id.bioEditText);
        place = findViewById(R.id.placeEditText);
        headline = findViewById(R.id.placeHeadlineEditText);
        btn_sub = findViewById(R.id.btn_save);
        progressbar = findViewById(R.id.progressbar_save);


        Intent intent = getIntent();
        String fname = intent.getStringExtra("fullname");
        String cname = intent.getStringExtra("collegename");
        String bi = intent.getStringExtra("bio");
        String pl = intent.getStringExtra("place");
        String headl = intent.getStringExtra("headline");

        fullname.setText(fname);
        collegename.setText(cname);
        bio.setText(bi);
        place.setText(pl);
        headline.setText(headl);


        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                String sfullname, scollegename, sbio, splace, sheadline;
                sfullname = String.valueOf(fullname.getText());
                scollegename = String.valueOf(collegename.getText());
                sbio = String.valueOf(bio.getText());
                splace = String.valueOf(place.getText());
                sheadline = String.valueOf(headline.getText());

                RequestQueue requestQueue = Volley.newRequestQueue(edit_profile.this);

                // Specify the API endpoint URL
                String url = "https://heraclitean-apparat.000webhostapp.com/api/postprofiledata.php";

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                // Create the JSON data to send
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uid", user.getUid());
                    jsonObject.put("fullname", sfullname);
                    jsonObject.put("collegename", scollegename);
                    jsonObject.put("bio", sbio);
                    jsonObject.put("place", splace);
                    jsonObject.put("headline", sheadline);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create the request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(edit_profile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(edit_profile.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the request queue
                requestQueue.add(jsonObjectRequest);
            }
        });

    }
}