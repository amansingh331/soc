package com.example.soc2.profilecomponent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soc2.R;
import com.example.soc2.home;
import com.example.soc2.profile;

import java.util.List;

public class profile_posts_addapter extends RecyclerView.Adapter<com.example.soc2.profilecomponent.profile_posts_addapter.ViewHolder>{
    private List<profile.Post> localDataSet;
    //    private HomeFragment.Post[] localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView descriptionTextView;


        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.postAuthorName);
            descriptionTextView = (TextView) view.findViewById(R.id.postContent);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }
    }

    public profile_posts_addapter(List<profile.Post> localDataSet) {this.localDataSet = localDataSet;}


    @Override
    public com.example.soc2.profilecomponent.profile_posts_addapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_posts, viewGroup, false);

        return new com.example.soc2.profilecomponent.profile_posts_addapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.soc2.profilecomponent.profile_posts_addapter.ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet.get(position).name);
        viewHolder.getDescriptionTextView().setText(localDataSet.get(position).des);

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

