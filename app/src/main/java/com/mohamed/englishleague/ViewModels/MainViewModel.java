package com.mohamed.englishleague.ViewModels;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.Repositories.MainRepository;
import com.mohamed.englishleague.Utils.Constants;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    MainRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository();
    }

    public LiveData<List<Team>> getAllTeams(){

        return repository.getAllTeams(Constants.Token,Constants.LeagueId);
    }
}
