package com.example.apiapplication.util;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiapplication.R;
import com.example.apiapplication.model.Charact;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private Context context;
    private List<Charact> characts;

    public CharacterAdapter(Context context, List<Charact> characts) {
        this.context = context;
        this.characts = characts;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Charact charact = characts.get(position);

        holder.nameTextView.setText(charact.getName());
        holder.houseTextView.setText(charact.getHouse());

        // Используйте Picasso для загрузки изображений из URL
        Picasso.get().load(charact.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return characts.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView houseTextView;
        public ImageView imageView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            houseTextView = itemView.findViewById(R.id.houseTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
