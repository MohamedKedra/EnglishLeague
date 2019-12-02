package com.mohamed.englishleague.ViewModels;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohamed.englishleague.Models.Player;
import com.mohamed.englishleague.Repositories.DetailsRepository;
import com.mohamed.englishleague.Utils.Constants;

import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    DetailsRepository repository;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new DetailsRepository(application.getApplicationContext());
    }

    public LiveData<List<Player>> getTeamPlayers(int teamId){

        return repository.getTeamPlayers(Constants.Token,teamId);
    }
}
