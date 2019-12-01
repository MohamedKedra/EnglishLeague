package com.mohamed.englishleague.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamed.englishleague.Models.Player;
import com.mohamed.englishleague.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerHolder> {

    Context context;
    List<Player> players;

    public PlayerAdapter(Context context,List<Player> players){
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerHolder(LayoutInflater.from(context).inflate(R.layout.item_player,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {

        holder.name.setText(players.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {

        TextView name;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

}
