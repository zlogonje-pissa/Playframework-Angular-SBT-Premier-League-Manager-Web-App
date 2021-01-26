package models;

import java.io.Serializable;

public abstract class SportsClub implements Serializable { //Abstract Sports Club Class

    private int id;
    private String clubName;
    private String location;

    public SportsClub(String clubName, String location) {
        this.clubName = clubName;
        this.location = location;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "clubName='" + clubName + '\'' +
                ", location='" + location + '\'';
    }

}