package controllers;

import play.mvc.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import models.FootballClub;
import models.FootballMatch;

import services.*;

public class PremierLeagueManager extends Controller implements LeagueManager {

    ArrayList < FootballClub > clubsPlaying = FootballClubController.clubsPlaying; //List of Clubs



    public PremierLeagueManager() throws IOException, ClassNotFoundException, ParseException { //Constructor Tries to Load data and then Displays the Menu



        gettingTheData: try {
            FootballClubController.loadClubData(FootballClubController.clubsPlaying, "clubData.txt");
            FootballClubController.loadMatchData(FootballClubController.matches, "matchData.txt");
        }
        catch (Exception e) {
            break gettingTheData;
        }
        displayMenu();
    }

    @Override
    public void displayMenu() throws IOException, ParseException { //Basic CLI Menu Using Switch,Case

        Scanner scanner = new Scanner(System.in);
        menuLoop:
        while (true) {
            System.out.println("\nWelcome to Premier League Manager, Please Select what you Would like to do: ");
            System.out.println("1: Create a Football Club");
            System.out.println("2: Delete an Existing Club");
            System.out.println("3: Display Statistics of a Club");
            System.out.println("4: Display Premier League Table");
            System.out.println("5: Add a Played Match");
            System.out.println("6: Exit Application");
            String input = scanner.nextLine();
            int inputNum = 0;
            try {
                inputNum = Integer.parseInt(input);
            } catch (Exception e) {

            }

            switch (inputNum) {
                case 1:
                    FootballClubController.createFootballClub();
                    break;
                case 2:
                    FootballClubController.deleteExistingClub();
                    break;
                case 3:
                    displayStatisticsOfClub();
                    break;
                case 4:
                    displayPremierLeagueTable();
                    break;
                case 5:
                    enterNewMatch();
                    break;
                case 6:
                    FootballClubController.saveClubData(FootballClubController.clubsPlaying, "clubData.txt");
                    FootballClubController.saveMatchData(FootballClubController.matches, "matchData.txt");
                    System.out.println("Thank You for using Premier League Manager. Have a Nice Day!");
                    break menuLoop;
                default:
                    System.out.println("Your Input was Invalid, Please Choose a Valid input");
            }


        }

        scanner.close();


    }



    @Override
    public void displayStatisticsOfClub() { //Method Name Explains Function
        System.out.print("Display Statistics of a Club");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert club name: ");
        String line = scanner.nextLine();
        for (FootballClub club: clubsPlaying) {
            if (club.getClubName().equals(line)) {
                System.out.println("Club " + club.getClubName() + " matches won: " + club.getWins());
                System.out.println("Club " + club.getClubName() + " matches lost: " + club.getDefeats());
                System.out.println("Club " + club.getClubName() + " matches draw: " + club.getDraws());
                System.out.println("Club " + club.getClubName() + " scored goals: " + club.getGoalsThisSeason());
                System.out.println("Club " + club.getClubName() + " received goals: " + club.getGoalsReceivedThisSeason());
                System.out.println("Club " + club.getClubName() + " points: " + club.getPointsThisSeason());
                System.out.println("Club " + club.getClubName() + " matches played: " + club.getNumberOfMatchesThisSeason());
                return;
            }
        }
        System.out.println("No such club in league");

    }

    @Override
    public void displayPremierLeagueTable() {  //Method Name Explains Function

        System.out.print("\n-----Premier League Table-----\n");

        Collections.sort(clubsPlaying, new FootballClubComparator()); //Custom Comparator Was Created to Sort Football Clubs
        int i = 0;
        for (FootballClub club: clubsPlaying) {
            i++;
            System.out.println(i + ") Club: " + club.getClubName() + " | Points: " + club.getPointsThisSeason() + " | Goal Difference: " + (club.getGoalsThisSeason() - club.getGoalsReceivedThisSeason()));
        }

    }

    @Override
    public void enterNewMatch() {

        //Starts Getting Data From the User
        Date matchDate = new Date();
        System.out.print("When was the Match Held?: ");
        boolean checkError = true;
        do {
            Scanner md = new Scanner(System.in);
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Please enter a date (dd/MM/yyyy)");
            String uDate = md.nextLine();
            try {
                matchDate = date.parse(uDate);
                System.out.println(uDate + " is a valid Date");
                checkError = false;
            } catch (Exception e) {
                System.out.println(uDate + " is not a valid Date");
            }

        } while (checkError);

        System.out.print("Enter the 2 Football Clubs that Played the Match\n\n");

        displayTeams();   //Displays the Team Names so User can Check Spellings

        System.out.println("Enter Home Team: ");

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        FootballClub home = null;
        for (FootballClub club: clubsPlaying) {
            if (club.getClubName().equals(line))
                home = club;
        }
        if (home == null) {
            System.out.println("No such club in league");
            return;
        }
        System.out.println("Enter Away Team: ");
        line = scanner.nextLine();
        FootballClub away = null;
        for (FootballClub club: clubsPlaying) {
            if (club.getClubName().equals(line))
                away = club;
        }
        if (away == null) {
            System.out.println("No such club in league");
            return;
        }

        System.out.println("Enter home team goals: ");
        line = scanner.nextLine();
        int homeGoals = -1;
        try {
            homeGoals = Integer.parseInt(line);
        } catch (Exception e) {

        }
        if (homeGoals == -1) {
            System.out.println("You have to enter number of goals");
            return;
        }

        System.out.println("Enter away team goals: ");
        line = scanner.nextLine();
        int awayGoals = -1;
        try {
            awayGoals = Integer.parseInt(line);
        } catch (Exception e) {}
        if (awayGoals == -1) {
            System.out.println("You have to enter number of goals");
            return;
        }

        //Initializes the Constructor
        FootballMatch match = new FootballMatch( home,away,homeGoals,awayGoals,matchDate);
        FootballClubController.matches.add(match);
        //Updates the Stats of Both Teams
        FootballClubController.updateTeamStats(match);



    }

    @Override
    public void displayTeams() { //Display all the Team Names for User to Check names
        System.out.print("Print List of Teams");
        for (FootballClub club: clubsPlaying) {
            System.out.println(club.getClubName());
        }
    }


}