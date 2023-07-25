package com.example.soc2.messagecomponent;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.soc2.R;
import com.example.soc2.home;

import java.util.List;

public class messageadapter extends RecyclerView.Adapter<messageadapter.ViewHolder>{

    public List<messagelist.Chats> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView username;
        private final TextView lastmsg;
        private final TextView lastmsgtime;

        private final ImageView profileimg;

        private List<messagelist.Chats> localDataSet;

        public ViewHolder(View view, List<messagelist.Chats> dataSet) {
            super(view);
            localDataSet = dataSet;
            // Define click listener for the ViewHolder's View
            username = (TextView) view.findViewById(R.id.chat_name);
            lastmsg = (TextView) view.findViewById(R.id.chat_message);
            lastmsgtime = (TextView) view.findViewById(R.id.chat_timestamp);
            profileimg = (ImageView) view.findViewById(R.id.chat_avatar);
            view.setOnClickListener(this);
        }

        public TextView getusername() {
            return username;
        }
        public TextView getLastmsg() {
            return lastmsg;
        }
        public TextView getLastmsgtime() {
            return lastmsgtime;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), singlechat.class);

            if (position != RecyclerView.NO_POSITION) {
                intent.putExtra("position", position);
                intent.putExtra("username", localDataSet.get(position).user_name);
                intent.putExtra("profile_link", localDataSet.get(position).profile_img);
                v.getContext().startActivity(intent);
            }else{
                Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public messageadapter(List<messagelist.Chats> dataSet) {
        localDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chatlist, viewGroup, false);
        return new ViewHolder(view, localDataSet);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getusername().setText(localDataSet.get(position).user_name);
        viewHolder.getLastmsg().setText(localDataSet.get(position).last_msg);
        viewHolder.getLastmsgtime().setText(localDataSet.get(position).last_msg_time);

        String imageName = localDataSet.get(position).profile_img;
        if (imageName == null) {
            imageName = "profile";
        }
        int imageResId = viewHolder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", viewHolder.itemView.getContext().getPackageName());
        viewHolder.profileimg.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

