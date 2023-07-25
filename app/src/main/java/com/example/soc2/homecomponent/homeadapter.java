package com.example.soc2.homecomponent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soc2.R;
import com.example.soc2.home;
import com.example.soc2.profilecomponent.otheruserprofile;

import java.util.List;

public class homeadapter extends RecyclerView.Adapter<homeadapter.ViewHolder> {
    private List<home.Post> localDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView date;
        private final ImageView profileimg;
        private final ImageView mainphoto;
        private final TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.postAuthorName);
            date = view.findViewById(R.id.postDate);
            profileimg = view.findViewById(R.id.postAuthorImage);
            mainphoto = view.findViewById(R.id.postImage);
            descriptionTextView = view.findViewById(R.id.postContent);
        }

        public TextView getNameView() {
            return name;
        }

        public TextView getDateView() {
            return date;
        }

        public ImageView getProfileImageView() {
            return profileimg;
        }

        public ImageView getMainPhotoImageView() {
            return mainphoto;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }
    }

    public homeadapter(List<home.Post> localDataSet, Context context) {
        this.localDataSet = localDataSet;
        this.context = context;
    }

    @Override
    public homeadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_posts, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getNameView().setText(localDataSet.get(position).name);
        viewHolder.getDescriptionTextView().setText(localDataSet.get(position).des);
        viewHolder.getDateView().setText(localDataSet.get(position).date);

        String imageName = localDataSet.get(position).profile_pic;
        if (imageName == null) {
            imageName = "profile";
        }
        int imageResId = viewHolder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", viewHolder.itemView.getContext().getPackageName());
        viewHolder.getProfileImageView().setImageResource(imageResId);

        String imageName2 = localDataSet.get(position).mainphoto;
        if (imageName2 == null) {
            imageName2 = "profile";
        }
        int imageResId2 = viewHolder.itemView.getContext().getResources().getIdentifier(imageName2, "drawable", viewHolder.itemView.getContext().getPackageName());
        viewHolder.getMainPhotoImageView().setImageResource(imageResId2);

        // Handle click on name
        viewHolder.getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity or perform desired action
                int clickedPosition = viewHolder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, otheruserprofile.class);
                    intent.putExtra("id", localDataSet.get(clickedPosition).id);
                    context.startActivity(intent);
                }
            }
        });

        // Handle click on profile image
        viewHolder.getProfileImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity or perform desired action
                int clickedPosition = viewHolder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, otheruserprofile.class);
                    intent.putExtra("id", localDataSet.get(clickedPosition).id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
