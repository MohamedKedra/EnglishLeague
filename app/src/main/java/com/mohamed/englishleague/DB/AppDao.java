package com.mohamed.englishleague.DB;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mohamed.englishleague.Models.Team;

import java.util.List;

@Dao
public interface AppDao {

    @Insert
    void addTeam(Team team);

    @Delete
    void deleteTeam(Team team);

    @Query("SELECT * FROM teams_table ORDER BY id DESC")
    LiveData<List<Team>> getFavoriteTeams();
}
