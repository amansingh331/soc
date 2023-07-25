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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.soc2.auth.login;
import com.example.soc2.homecomponent.homeadapter;
import com.example.soc2.messagecomponent.messagelist;
import com.example.soc2.profilecomponent.edit_profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */


public class home extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    ImageView logout;


    RecyclerView recyclerView;
    ProgressBar loadingIndicator;


    public class Post {
        public int id;
        public String name;
        public String date;
        public String profile_pic;
        public String mainphoto;
        public int likecount;
        public String des;
    }


    List<Post> posts = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        loadingIndicator = root.findViewById(R.id.loadingIndicator);

//      message page button function
        ImageView myImage = root.findViewById(R.id.message_btn);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), messagelist.class);
                startActivity(intent);
            }
        });


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logout = root.findViewById(R.id.logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < 5; i++) {
                        JSONObject obj = response.getJSONObject(i);

                        String na = obj.getString("id");
                        String de = obj.getString("title");

                        Post inp = new Post();
                        inp.id = i+1;
                        inp.name = na;
                        inp.des = de;
                        inp.date = "May 1, 2022";
                        inp.profile_pic = "proimg";
                        inp.mainphoto = "postimg";
                        inp.likecount = 10;
                        posts.add(inp);
                    }

                    for (int i = 0; i < posts.size(); i++) {
                        Log.d("checkingthevalue", "name - " + posts.get(i).name + " des " + posts.get(i).des);
                    }



                    recyclerView = root.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    homeadapter ad = new homeadapter(posts, getActivity());
                    recyclerView.setAdapter(ad);

                    loadingIndicator.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                loadingIndicator.setVisibility(View.GONE);
            }
        });
        loadingIndicator.setVisibility(View.VISIBLE);

        requestQueue.add(jsonArrayRequest);




        return root;
    }
}