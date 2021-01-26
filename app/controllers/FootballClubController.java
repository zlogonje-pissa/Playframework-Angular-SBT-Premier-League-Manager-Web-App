package controllers;

import com.typesafe.config.Config;
import models.FootballClub;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import models.FootballMatch;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.*;

import services.FootballClubComparator;

import javax.inject.Inject;

public class FootballClubController extends Controller {

    static ArrayList<FootballClub> clubsPlaying = new ArrayList<>();
    static ArrayList<FootballMatch> matches = new ArrayList<>();

    final static int numberOfClubs = 12; //Max Number of Clubs

    private final Config config;

    @Inject
    public FootballClubController(Config config) {
        this.config = config;
    }

    private static FootballClubController instance;




    public static void createFootballClub() { //Method Name Explains Function

        if (clubsPlaying.size() == numberOfClubs) {
            System.out.println("Can't add more clubs to league");
            return;
        }

        System.out.print("Enter the name of the Club you want to Create : ");
        Scanner clubName = new Scanner(System.in);
        String n = clubName.nextLine();

        System.out.print("Enter the location of the Club you want to Create : ");
        Scanner location = new Scanner(System.in);
        String l = location.nextLine();

        FootballClub newFootballClub = new FootballClub(n, l);

        clubsPlaying.add(newFootballClub);

    }


    public static void deleteExistingClub() {  //Method Name Explains Function

        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert club name: ");
        String line = scanner.nextLine();
        for (FootballClub club: clubsPlaying) {
            if (club.getClubName().equals(line)) {
                clubsPlaying.remove(club);
                System.out.println("Club " + club.getClubName() + " removed");
                return;
            }
        }
        System.out.println("No such club in league");
    }




    public Result listClubs(){

        gettingTheData:
        try {
            loadClubData(clubsPlaying, "clubData.txt");
            loadMatchData(matches, "matchData.txt");
        }
        catch (FileNotFoundException e){
            break gettingTheData;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(clubsPlaying, new FootballClubComparator());
        return ok(Json.toJson(clubsPlaying));

    }

    public static void loadClubData(ArrayList<FootballClub> arrayName, String fileName) throws IOException, ClassNotFoundException {

        arrayName.clear();

        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        for(;;){
            try {
                FootballClub fc = (FootballClub) ois.readObject();
                arrayName.add(fc);
            }
            catch (EOFException e){
                break;
            }
        }

        ois.close();
        fis.close();

    }

    public static void loadMatchData(ArrayList<FootballMatch> arrayName, String fileName) throws IOException {

        arrayName.clear();

        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        for(;;){
            try {
                FootballMatch fm = (FootballMatch) ois.readObject();
                arrayName.add(fm);
            }
            catch (EOFException | ClassNotFoundException e){
                break;
            }

        }

        ois.close();
        fis.close();

    }

    public Result saveData() throws IOException {
        saveClubData(clubsPlaying, "clubData.txt");
        saveMatchData(matches, "matchData.txt");
        System.out.println("Thank You for using Premier League Manager. Have a Nice Day!");
        return ok(Json.toJson(matches));
    }

    public static void saveClubData(ArrayList<FootballClub> arrayName, String fileName) throws IOException {

        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (FootballClub v : arrayName) {
            oos.writeObject(v);
        }
        oos.flush();
        fos.close();
        oos.close();
       System.out.println("Data has been Successfully Saved to the File");

    }

    public static void saveMatchData(ArrayList<FootballMatch> arrayName, String fileName) throws IOException {

        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (FootballMatch v : arrayName) {
            oos.writeObject(v);
        }

        oos.flush();
        fos.close();
        oos.close();

      System.out.println("Data has been Successfully Saved to the File");

    }

    public Date randomMatchDate(){ //Produces a Random Match Date

        ZoneId defaultZoneId = ZoneId.systemDefault();
        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        Date date = Date.from(randomDate.atStartOfDay(defaultZoneId).toInstant());

        return date;

    }

    public Result randomMatch() throws ParseException {

        //Shuffles Teams and Selects The First 2 in the Array
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i< clubsPlaying.size(); i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        FootballMatch match = new FootballMatch( clubsPlaying.get(list.get(0)),clubsPlaying.get(list.get(1)),(int) Math.round(Math.random()*10),(int) Math.round(Math.random()*10),randomMatchDate());
        matches.add(match);
        //Updates the Stats of Both Teams
        updateTeamStats(match);

        return ok(Json.toJson(matches));

    }

    public static void updateTeamStats(FootballMatch match){ //Updates the Stats of Both Teams

        FootballClub home = match.getHomeTeam();
        FootballClub away = match.getAwayTeam();

        int homeGoals = match.getTeam1_goals();
        int awayGoals = match.getTeam2_goals();

        home.setGoalsThisSeason(home.getGoalsThisSeason()+homeGoals);
        away.setGoalsReceivedThisSeason(away.getGoalsReceivedThisSeason()+awayGoals);

        home.setGoalsThisSeason(home.getGoalsThisSeason()+awayGoals);
        away.setGoalsReceivedThisSeason(away.getGoalsReceivedThisSeason()+homeGoals);

        home.setNumberOfMatchesThisSeason(home.getNumberOfMatchesThisSeason()+1);
        away.setNumberOfMatchesThisSeason(away.getNumberOfMatchesThisSeason()+1);


        if (homeGoals > awayGoals) {
            home.setPointsThisSeason(home.getPointsThisSeason()+3);
            home.setWins(home.getWins()+1);
            away.setDefeats(away.getDefeats()+1);
        }

        else if (homeGoals < awayGoals) {
            away.setPointsThisSeason(away.getPointsThisSeason()+3);
            away.setWins(away.getWins()+1);
            home.setDefeats(home.getDefeats()+1);
        }
        else {
            home.setPointsThisSeason(home.getPointsThisSeason()+1);
            away.setPointsThisSeason(away.getPointsThisSeason()+1);
            home.setDraws(home.getDraws()+1);
            away.setDraws(away.getDraws()+1);
        }

        home.setGoalsDifference(home.getGoalsDifference()+homeGoals-awayGoals);
        away.setGoalsDifference(away.getGoalsDifference()-homeGoals+awayGoals);

    }

    public Result listMatches(){



        return ok(Json.toJson(matches));

    }

}