package models;

public class FootballClub extends SportsClub{ //Basic Football Club Class that Extends Sports Club

    private int wins;
    private int draws;
    private int defeats;

    private int goalsFor;
    private int goalsAgainst;
    private int goalsDifference;

    private int pointsThisSeason;
    private int numberOfMatchesThisSeason;

    public FootballClub(String clubName, String Location) {

        super(clubName,Location);

    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalsDifference() {
        return goalsDifference;
    }

    public void setGoalsDifference(int goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    public void setGoalsThisSeason(int goalsThisSeason) {
        this.goalsFor = goalsThisSeason;
    }

    public void setGoalsReceivedThisSeason(int goalsReceivedThisSeason){
        this.goalsAgainst = goalsReceivedThisSeason;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public void setPointsThisSeason(int pointsThisSeason) {
        this.pointsThisSeason = pointsThisSeason;
    }

    public void setNumberOfMatchesThisSeason(int numberOfMatchesThisSeason) {
        this.numberOfMatchesThisSeason = numberOfMatchesThisSeason;
    }

    public int getGoalsThisSeason() {
        return goalsFor;
    }
    public int getGoalsReceivedThisSeason(){
        return goalsAgainst;
    }
    public int getWins() {
        return wins;
    }
    public int getDraws() {
        return draws;
    }
    public int getDefeats() {
        return defeats;
    }
    public int getPointsThisSeason() {
        return pointsThisSeason;
    }
    public int getNumberOfMatchesThisSeason() {
        return numberOfMatchesThisSeason;
    }

    @Override
    public String toString() {
        return "FootballClub{" + super.toString() +
                "goalsThisSeason=" + goalsFor +
                ", goalsReceivedThisSeason=" + goalsAgainst +
                ", wins=" + wins +
                ", draws=" + draws +
                ", defeats=" + defeats +
                ", pointsThisSeason=" + pointsThisSeason +
                ", numberOfMatchesThisSeason=" + numberOfMatchesThisSeason +"}";
    }
}