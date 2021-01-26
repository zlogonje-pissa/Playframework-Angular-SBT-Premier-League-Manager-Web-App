package models;

import java.io.Serializable;
import java.util.Date;

public class FootballMatch implements Serializable {

    FootballClub homeTeam; //Home Team
    FootballClub awayTeam; //Away Team

    int homeTeamGoals;
    int awayTeamGoals;

    Date matchDate;

    public FootballMatch() {
    }

    public FootballMatch(FootballClub homeTeam, FootballClub awayTeam, int homeTeamGoals, int awayTeamGoals, Date matchDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.matchDate = matchDate;
    }

    public FootballClub getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(FootballClub team1) {
        this.homeTeam = team1;
    }

    public FootballClub getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(FootballClub team2) {
        this.awayTeam = team2;
    }

    public int getTeam1_goals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public int getTeam2_goals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "FootballMatch{" +
                "team1=" + homeTeam +
                ", team2=" + awayTeam +
                ", team1_goals=" + homeTeamGoals +
                ", team2_goals=" + awayTeamGoals +
                ", matchDate=" + matchDate +
                '}';
    }

}
