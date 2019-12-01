package com.mohamed.englishleague.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamed.englishleague.Screens.DetailsActivity;
import com.mohamed.englishleague.Utils.Constants;
import com.mohamed.englishleague.Utils.ImageGenerator;
import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamHolder> {

    List<Team> teams;
    Context context;

    public TeamAdapter(List<Team> teams, Context context) {
        this.context = context;
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {

        final Team team = teams.get(position);
        if (team != null) {
            //  Picasso.with(context).load(team.getCrestUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.avatar);
           // ImageGenerator.fetchImage(context, team.getCrestUrl(), holder.avatar);
            holder.name.setText(team.getName());
            holder.venue.setText(team.getVenue());
            holder.color.setText(team.getClubColors());
            holder.website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(team.getWebsite()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    class TeamHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name, venue, color;
        Button website;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avatar);
            name = itemView.findViewById(R.id.tv_name);
            venue = itemView.findViewById(R.id.tv_venue);
            color = itemView.findViewById(R.id.tv_color);
            website = itemView.findViewById(R.id.btn_website);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Constants.TeamKey,teams.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
