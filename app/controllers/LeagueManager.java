package controllers;

import java.io.IOException;
import java.text.ParseException;

public interface LeagueManager{

    void displayMenu() throws IOException, ParseException;
    void displayStatisticsOfClub();
    void displayPremierLeagueTable();
    void enterNewMatch();
    void displayTeams();

}