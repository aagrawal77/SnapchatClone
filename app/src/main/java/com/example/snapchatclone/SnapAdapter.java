package com.example.snapchatclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.CustomViewHolder> {
    Context context;
    ArrayList<Snap> snaps;

    public SnapAdapter(Context context, ArrayList<Snap> snaps) {
        this.context = context;
        this.snaps = snaps;

    }

    @NonNull
    @Override
    public SnapAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snap_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnapAdapter.CustomViewHolder holder, int position) {
        Snap snap = snaps.get(position);
        holder.caption.setText(snap.caption);
        holder.email.setText(snap.user.getEmail());
        Glide.with(context).load(snap.photoURL).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return snaps.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView caption;
        TextView email;

        public CustomViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            caption = itemView.findViewById(R.id.caption);
            email = itemView.findViewById(R.id.email);

        }
    }
}
