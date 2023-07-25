package com.example.soc2;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.soc2.profilecomponent.edit_profile;
import com.example.soc2.profilecomponent.profile_posts_addapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class profile extends Fragment {

//    this is used for the main purpose

    public TextView profilename;
    public TextView profileHeadline;
    public TextView place;
    public TextView bio;


    private ProgressBar progressBar;
    private RecyclerView recyclerView;


    public class Post {
        public String name;
        public String des;
    }


    List<Post> posts = new ArrayList<>();


    // end of the main purpose



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        // Initialize views
        profilename = root.findViewById(R.id.profileName);
        profileHeadline = root.findViewById(R.id.profileHeadline);
        place = root.findViewById(R.id.place);
        bio = root.findViewById(R.id.bio);
        recyclerView = root.findViewById(R.id.recyclerViewprofile);
        progressBar = root.findViewById(R.id.progressBar);
        ScrollView scrollView = root.findViewById(R.id.scrollview);

        // Hide the RecyclerView initially
        recyclerView.setVisibility(View.GONE);

        // Show the loading indicator
        progressBar.setVisibility(View.VISIBLE);

        // Handle edit profile button click


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());

        FirebaseAuth auth;
        FirebaseUser user;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String url =  "https://heraclitean-apparat.000webhostapp.com/api/profile.php?uid="+user.getUid();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("id");
                    String name = response.getString("fullname");
                    String prohead = response.getString("headline");
                    String pl = response.getString("place");
                    String bi = response.getString("bio");
                    String col = response.getString("college");

                    if(id.equals("-1")){
                        Toast.makeText(getActivity(), "something went wrong ", Toast.LENGTH_SHORT).show();
                    }else{
                        profilename.setText(name);
                        profileHeadline.setText(prohead);
                        place.setText(pl);
                        bio.setText(bi);
                    }

                    ImageView editProfileButton = root.findViewById(R.id.editProfileButton);
                    editProfileButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), edit_profile.class);
                            intent.putExtra("fullname", name);
                            intent.putExtra("collegename", col);
                            intent.putExtra("bio", bi);
                            intent.putExtra("place", pl);
                            intent.putExtra("headline", prohead);
                            startActivity(intent);
                        }
                    });


                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    // Show the RecyclerView
                    recyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(jsonObjectRequest);

//        here is the end of the profile main data




//        here is the start of the profile post data

        RequestQueue requestQueue2;
        requestQueue2 = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < 5; i++) {
                        JSONObject obj = response.getJSONObject(i);

                        String na = obj.getString("id");
                        String de = obj.getString("title");

                        Post inp = new Post();
                        inp.name = na;
                        inp.des = de;
                        posts.add(inp);
                    }

                    for (int i = 0; i < posts.size(); i++) {
                        Log.d("checkingthevalue", "name - " + posts.get(i).name + " des " + posts.get(i).des);
                    }



                    recyclerView = root.findViewById(R.id.recyclerViewprofile);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    profile_posts_addapter ad = new profile_posts_addapter(posts);
                    recyclerView.setAdapter(ad);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue2.add(jsonArrayRequest2);
//        here is the end of the profile posts data end





        return root;
    }
}