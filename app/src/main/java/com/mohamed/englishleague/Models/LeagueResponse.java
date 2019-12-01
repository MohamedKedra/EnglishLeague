package com.mohamed.englishleague.Models;

import java.util.List;

public class LeagueResponse {

    private int count;
    private List<Team> teams;

    public LeagueResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
