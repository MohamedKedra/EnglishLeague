package com.mohamed.englishleague.Models;

import java.util.List;

public class TeamResponse {

    private int id;
    private String name;
    private List<Player> squad;

    public TeamResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getSquad() {
        return squad;
    }

    public void setSquad(List<Player> squad) {
        this.squad = squad;
    }
}
