package com.mohamed.englishleague.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohamed.englishleague.Adapters.PlayerAdapter;
import com.mohamed.englishleague.Models.Player;
import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.R;
import com.mohamed.englishleague.Utils.AppNetwork;
import com.mohamed.englishleague.Utils.Constants;
import com.mohamed.englishleague.ViewModels.DetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    ImageView avatar, arrow;
    TextView title, venue, color;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    CoordinatorLayout layoutList;
    FloatingActionButton favorite;
    Button website;
    LinearLayout expanded;
    RecyclerView.LayoutManager layoutManager;
    Team team;
    DetailsViewModel detailsViewModel;
    PlayerAdapter adapter;
    List<Team> teamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        initViews();

        drawDetails();

        expandPlayers();

        addOrRemoveFavorite();
    }

    private void initViews() {
        avatar = findViewById(R.id.iv_avatar);
        arrow = findViewById(R.id.iv_arrow);
        expanded = findViewById(R.id.lay_expanded);
        title = findViewById(R.id.tv_title);
        venue = findViewById(R.id.tv_venue);
        color = findViewById(R.id.tv_color);
        recyclerView = findViewById(R.id.rv_players);
        website = findViewById(R.id.btn_website);
        progressBar = findViewById(R.id.pb_progressbar);
        layoutManager = new LinearLayoutManager(this);
        layoutList = findViewById(R.id.lay_players);
        favorite = findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
    }

    public void addOrRemoveFavorite() {

        detailsViewModel.getFavoriteTeams().observe(DetailsActivity.this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if (teams != null){
                    for (Team t : teams) {
                        if (team.getId() == t.getId()) {
                            favorite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                        }
                    }
                }else {
                    favorite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                }
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favorite.getBackgroundTintList().getDefaultColor() == getResources().getColor(R.color.colorRed)) {
                    detailsViewModel.DeleteTeam(team);
                    Toast.makeText(DetailsActivity.this, team.getName()+" is deleted", Toast.LENGTH_SHORT).show();
                    favorite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                } else {
                    detailsViewModel.addTeam(team);
                    Toast.makeText(DetailsActivity.this, team.getName()+" is added", Toast.LENGTH_SHORT).show();
                    favorite.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                }
            }
        });
    }

    private boolean isFavorite(int id) {
        detailsViewModel.getFavoriteTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                teamsList = teams;
            }
        });

        if (teamsList == null){
            return false;
        }else{
            for (Team team : teamsList) {
                if (id == team.getId()) {

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void drawDetails() {

        team = getIntent().getExtras().getParcelable(Constants.TeamKey);
        Glide.with(this).load(team.getCrestUrl())
                .placeholder(R.drawable.team_holder)
                .error(R.drawable.team_holder)
                .into(avatar);
        title.setText(team.getName());
        venue.setText(team.getVenue());
        color.setText(team.getClubColors());
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(team.getWebsite()));
                startActivity(intent);
            }
        });
    }

    private void expandPlayers() {

        expanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppNetwork.hasNetwork()) {
                    if (layoutList.getVisibility() == View.GONE) {
                        layoutList.setVisibility(View.VISIBLE);
                        arrow.setImageDrawable(getDrawable(R.drawable.ic_arrow_opened));
                        progressBar.setVisibility(View.VISIBLE);
                        displayPlayers();
                    } else {
                        layoutList.setVisibility(View.GONE);
                        arrow.setImageDrawable(getDrawable(R.drawable.ic_arrow_closed));
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void displayPlayers() {
        detailsViewModel.getTeamPlayers(team.getId()).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                progressBar.setVisibility(View.GONE);
                if (players != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new PlayerAdapter(DetailsActivity.this, players);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }
}
