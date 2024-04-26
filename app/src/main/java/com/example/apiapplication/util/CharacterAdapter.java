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

    public void setFilteredList(List<Charact> filteredList){
        this.characts = filteredList;
        notifyDataSetChanged();
    }

    public interface OnCharactClickListener {
        void onCharactClick(String characterId);
    }
    private OnCharactClickListener listener;

    public void setOnCharacterClickListener(OnCharactClickListener listener) {
        this.listener = listener;
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
        Picasso.get().load(charact.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onCharactClick(charact.getId());
                }
            }
        });
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
