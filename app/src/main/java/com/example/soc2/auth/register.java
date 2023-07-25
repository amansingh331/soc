package com.example.soc2.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.soc2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {

    EditText editname, editTextemail, editTextpassword, editrepass;
    Button btnreg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView myImage = findViewById(R.id.swipeLeftbutton);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // go back to the previous activity
            }
        });


        editname = findViewById(R.id.et_name);
        editTextemail = findViewById(R.id.et_email);
        editTextpassword = findViewById(R.id.et_password);
        editrepass = findViewById(R.id.et_repassword);
        btnreg = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar_reg);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name, email, password, re_pass;
                name = String.valueOf(editname.getText());
                email = String.valueOf(editTextemail.getText());
                password = String.valueOf(editTextpassword.getText());
                re_pass = String.valueOf(editrepass.getText());

                if(TextUtils.isEmpty(name)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Enter the Full Name", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(TextUtils.isEmpty(re_pass)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Enter the Re-password", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(!password.equals(re_pass)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this, "Your Re-password is not matching", Toast.LENGTH_SHORT).show();
                    return ;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();

//                                    this is the start of api request
                                    RequestQueue requestQueue = Volley.newRequestQueue(register.this);

                                    // Specify the API endpoint URL
                                    String url = "https://heraclitean-apparat.000webhostapp.com/api/postdata.php";

                                    // Create the JSON data to send
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("uid", user.getUid());
                                        jsonObject.put("name", name);
                                        jsonObject.put("email", email);
                                        jsonObject.put("password", password);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    // Create the request
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
//                                                    Toast.makeText(register.this, "message: "+ response.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Handle the error response
                                            Log.e("TAG", "Error: " + error.toString());
                                        }
                                    });

                                    // Add the request to the request queue
                                    requestQueue.add(jsonObjectRequest);

//                                this is the end
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(register.this, login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(register.this, "Failed to create the account. Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}