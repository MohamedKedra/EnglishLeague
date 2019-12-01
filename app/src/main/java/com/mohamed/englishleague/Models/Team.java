package com.mohamed.englishleague.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {

    private int id;
    private String name;
    private String crestUrl;
    private String clubColors;
    private String website;
    private String venue;

    public Team() {
    }

    protected Team(Parcel in) {
        id = in.readInt();
        name = in.readString();
        crestUrl = in.readString();
        clubColors = in.readString();
        website = in.readString();
        venue = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

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

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public String getClubColors() {
        return clubColors;
    }

    public void setClubColors(String clubColors) {
        this.clubColors = clubColors;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(crestUrl);
        parcel.writeString(clubColors);
        parcel.writeString(website);
        parcel.writeString(venue);
    }
}
