package com.mohamed.englishleague.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohamed.englishleague.Adapters.TeamAdapter;
import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.R;
import com.mohamed.englishleague.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    TeamAdapter adapter;
    @BindView(R.id.pb_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.rv_teams)
    RecyclerView teamsRecycler;
    @BindView(R.id.tv_no_fav)
    TextView noFav;
    RecyclerView.LayoutManager layoutManager;
    MainViewModel mainViewModel;
    FrameLayout loading;
    List<Team> teamsList;
    static int page = 1;
    final static String StateKey = "state";
    final static String PosKey = "pos";
    final static String IndexKey = "index";
    Parcelable stateData;
    int index = 0;
    List<Team> favoriteTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        displayAllTeams();
        paginating();
    }

    public void initViews() {
        getSupportActionBar().setTitle("Home");
        teamsRecycler = findViewById(R.id.rv_teams);
        progressBar = findViewById(R.id.pb_progressbar);
        loading = findViewById(R.id.lay_loading);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(this, 3);
        }else {
            layoutManager = new GridLayoutManager(this, 2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        stateData = layoutManager.onSaveInstanceState();
        outState.putParcelable(StateKey,stateData);
        int pos = ((GridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition();
        if (pos == -1){
            pos = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
        }
        outState.putInt(PosKey,pos);
        outState.putInt(IndexKey,index);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            stateData = savedInstanceState.getParcelable(StateKey);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stateData != null){
            layoutManager.onRestoreInstanceState(stateData);
        }
    }

    public void displayAllTeams() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllTeams().observe(this, observer);
    }

    Observer<List<Team>> observer = new Observer<List<Team>>() {
        @Override
        public void onChanged(List<Team> teams) {
            progressBar.setVisibility(View.GONE);
            if (teams != null) {
                teamsList = teams;
                adapter = new TeamAdapter(getListFromPage(teamsList,page), MainActivity.this);
                page++;
                teamsRecycler.setAdapter(adapter);
                teamsRecycler.setLayoutManager(layoutManager);
            }
        }
    };

    public boolean isLastItemDisplayed() {

        if (teamsRecycler.getAdapter().getItemCount() != 0) {
            int lastItemPosition = ((LinearLayoutManager) teamsRecycler.getLayoutManager()).findLastCompletelyVisibleItemPosition();
             if (lastItemPosition != RecyclerView.NO_POSITION && lastItemPosition == teamsRecycler.getAdapter().getItemCount() - 1) {

                return true;
            }
        }
        return false;
    }

    public void paginating() {
        teamsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(favoriteTeam == null || favoriteTeam.isEmpty()){
                    if (isLastItemDisplayed()){
                        loading.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loading.setVisibility(View.GONE);
                                if (page < 5){
                                    adapter = new TeamAdapter(getListFromPage(teamsList,page),MainActivity.this);
                                    teamsRecycler.setAdapter(adapter);
                                    teamsRecycler.setLayoutManager(layoutManager);
                                    teamsRecycler.scrollToPosition((page - 1) * 6 );
                                    page++;
                                }
                            }
                        },1000);
                    }
                }
            }
        });
    }

    public List<Team> getListFromPage(List<Team> teams ,int page){

        List<Team> newList = new ArrayList<>();
        int index = teams.size() > page * 6 ? page * 6: teams.size();
        for (int i = 0; i < index; i++ ){
            newList.add(teams.get(i));
        }
        return newList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.all_teams:
                getSupportActionBar().setTitle("Home");
                displayAllTeams();
                return true;

            case R.id.favorite:

                getSupportActionBar().setTitle("Favorite");
                mainViewModel.getFavoriteTeams().observe(this, new Observer<List<Team>>() {
                    @Override
                    public void onChanged(List<Team> teams) {
                        favoriteTeam = teams;
                        adapter = new TeamAdapter(getListFromPage(favoriteTeam,page), MainActivity.this);
                        page++;
                        teamsRecycler.setAdapter(adapter);
                        teamsRecycler.setLayoutManager(layoutManager);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}