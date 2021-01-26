package services;

import java.util.Comparator;
import models.FootballClub;

public class FootballClubComparator implements Comparator<FootballClub> {

    @Override
    public int compare(FootballClub t1, FootballClub t2){

        if (t1.getPointsThisSeason() > t2.getPointsThisSeason()){
            return -1;
        }
        else{
            if (t1.getPointsThisSeason() < t2.getPointsThisSeason()){
                return 1;
            }
            else{
                int goalDif1 = t1.getGoalsThisSeason() - t1.getGoalsReceivedThisSeason();
                int goalDif2 = t2.getGoalsThisSeason() - t2.getGoalsReceivedThisSeason();

                return Integer.compare(goalDif2, goalDif1);

            }
        }
    }

}
