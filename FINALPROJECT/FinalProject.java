import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.List;

public class FinalProject{

	//round -> 0 = wild card, 1 = divisional, 2 = conference championships, 3 = super bowl
	private static int round = 0;
	private static List<Integer> AFCseed1, AFCseed2, AFCseed3, AFCseed4, AFCseed5, AFCseed6;
	private static List<Integer> NFCseed1, NFCseed2, NFCseed3, NFCseed4, NFCseed5, NFCseed6;
	private static List<Integer> opponentAFCseed1, opponentAFCseed2, opponentNFCseed1, opponentNFCseed2;
	private static List<Integer> AFCchampionship1, AFCchampionship2, NFCchampionship1, NFCchampionship2;
	private static List<Integer> AFCchampion, NFCchampion, SuperBowlChampion;
		
	private static List<String> AFCseed1info, AFCseed2info, AFCseed3info, AFCseed4info, AFCseed5info, AFCseed6info;
	private static List<String> NFCseed1info, NFCseed2info, NFCseed3info, NFCseed4info, NFCseed5info, NFCseed6info;
	private static List<String> opponentAFCseed1info, opponentAFCseed2info, opponentNFCseed1info, opponentNFCseed2info;
	private static List<String> AFCchampionship1info, AFCchampionship2info, NFCchampionship1info, NFCchampionship2info;
	private static List<String> AFCchampionInfo, NFCchampionInfo, SuperBowlChampionInfo;
	
	private static List<List<Integer>> AFCseed1Stats, AFCseed2Stats, AFCseed3Stats, AFCseed4Stats, AFCseed5Stats, AFCseed6Stats;
	private static List<List<Integer>> NFCseed1Stats, NFCseed2Stats, NFCseed3Stats, NFCseed4Stats, NFCseed5Stats, NFCseed6Stats;
	private static List<List<Integer>> opponentAFCseed1Stats, opponentAFCseed2Stats, opponentNFCseed1Stats, opponentNFCseed2Stats;
	private static List<List<Integer>> AFCchampionship1Stats, AFCchampionship2Stats, NFCchampionship1Stats, NFCchampionship2Stats;
	private static List<List<Integer>> AFCchampionStats, NFCchampionStats, SuperBowlChampionStats;
		
	private static List<String> AFCseed1infoStats, AFCseed2infoStats, AFCseed3infoStats, AFCseed4infoStats, AFCseed5infoStats, AFCseed6infoStats;
	private static List<String> NFCseed1infoStats, NFCseed2infoStats, NFCseed3infoStats, NFCseed4infoStats, NFCseed5infoStats, NFCseed6infoStats;
	private static List<String> opponentAFCseed1infoStats, opponentAFCseed2infoStats, opponentNFCseed1infoStats, opponentNFCseed2infoStats;
	private static List<String> AFCchampionship1infoStats, AFCchampionship2infoStats, NFCchampionship1infoStats, NFCchampionship2infoStats;
	private static List<String> AFCchampionInfoStats, NFCchampionInfoStats, SuperBowlChampionInfoStats;

	
	/** Indexing format for the List<Integer> of stats
	0: Turnover Differential
	1: Point Differential
	2: Passer Rating Against
	3: Overall Efficiency
	4: Passing Yards Per Attempt Against
	5: Passer Rating
	6: Scoring Offense
	7: Scoring Defense
	8: Red Zone TD%
	9: Passing Yards Per Attempt
	10: Overall Offensive Efficiency
	11: Pass Offense Efficiency
	12: Rush Offense Efficiency
	13: O-Line Pass Protection
	14: O-Line Run Blocking
	15: Overall Defensive Efficiency
	16: Pass Defense Efficiency
	17: Rush Defense Efficiency
	18: D-Line Pass Rushing
	19: D-Line Run Stuffing 
	20: Final Stat Grade */

	/** Indexing format for the List<String> of info
	0: Team name
	1: Regular season record
	2: Offensive ranking (regular season)
	3: Defensive ranking (regular season)
	4: Seed number */
	
	/** Point distrubtion for key stats (sum rounded to nearest hundredth)
	20 points: Turnover Differential
	15 points: Point Differential
	15 points: Pass Rating Against
	10 points: Overall Efficiency
	10 points: Passing Yards Per Attempt Against
	10 points: Passer Rating
	5 points: Scoring Offense
	5 points: Scoring Defense
	5 points: Red Zone TD%
	5 points: Passing Yards Per Attempt	*/
	
	
	public static double convertRankToRaw(int r){
		return (32 - (r-1) ) / 32.0; }
		
	public static double convertRawToScore(List<Integer> team){
		double turnoverDiff = 20 * convertRankToRaw( team.get(0) );
		double pointDiff = 15 * convertRankToRaw( team.get(1) );
		double PRagainst = 15 * convertRankToRaw( team.get(2) );
		double overallEff = 10 * convertRankToRaw( team.get(3) );
		double PYPAagainst = 10 * convertRankToRaw( team.get(4) );
		double PR = 10 * convertRankToRaw( team.get(5) );
		double scoringOff = 5 * convertRankToRaw( team.get(6) );
		double scoringDef = 5 * convertRankToRaw( team.get(7) );
		double redZone = 5 * convertRankToRaw( team.get(8) );
		double PYPA = 5 * convertRankToRaw( team.get(9) );
		double total = turnoverDiff + pointDiff + PRagainst + overallEff + PYPAagainst + PR + scoringOff + scoringDef + redZone + PYPA;
		double totalRounded = Math.round(total * 100.0) / 100.0; 
		return totalRounded;
	}
	
	public static double matchupAdvantage(List<Integer> teamA, List<Integer> teamB){
		double advantageA = 0.0, advantageB = 0.0;
		
		//TeamA's offensive stats
		int AOeff = teamA.get(10);
		int AOpe = teamA.get(11);
		int AOre = teamA.get(12);
		int AOolp = teamA.get(13);
		int AOolr = teamA.get(14);
		
		//TeamA's defensive stats
		int ADeff = teamA.get(15);
		int ADpe = teamA.get(16);
		int ADre = teamA.get(17);
		int ADdlp = teamA.get(18);
		int ADdlr = teamA.get(19);
		
		//TeamB's offensive stats
		int BOeff = teamB.get(10);
		int BOpe = teamB.get(11);
		int BOre = teamB.get(12);
		int BOolp = teamB.get(13);
		int BOolr = teamB.get(14);
		
		//TeamB's defensive stats
		int BDeff = teamB.get(15);
		int BDpe = teamB.get(16);
		int BDre = teamB.get(17);
		int BDdlp = teamB.get(18);
		int BDdlr = teamB.get(19);
	
		double marginAeff = ((BDeff - AOeff)/ 31.0) * 50;
		double marginApe = ((BDpe - AOpe)/ 31.0) * 25;
		double marginAre = ((BDre - AOre)/ 31.0) * 15;
		double marginAolp = ((BDdlp - AOolp)/ 31.0) * 5;
		double marginAolr = ((BDdlr - AOolr)/ 31.0) * 5;
		double finalAOvsBD = marginAeff + marginApe+ marginAre + marginAolp + marginAolr;
		
		//TeamB's offense vs TeamA's defense
		double marginBeff = ((ADeff - BOeff)/ 31.0) * 50;
		double marginBpe = ((ADpe - BOpe)/ 31.0) * 25;
		double marginBre = ((ADre - BOre)/ 31.0) * 15;
		double marginBolp = ((ADdlp - BOolp)/ 31.0) * 5;
		double marginBolr = ((ADdlr - BOolr)/ 31.0) * 5;
		double finalBOvsAD = marginBeff + marginBpe+ marginBre + marginBolp + marginBolr;
		
		if (finalAOvsBD < 0){
			advantageB += -1 *finalAOvsBD; }
		else{
			advantageA += finalAOvsBD; }
		
		if (finalBOvsAD < 0){
			advantageA += -1 * finalBOvsAD; }
		else{
			advantageB += finalBOvsAD; }

		//if teamA has the matchup advantage, the return value will be pos
		//if teamB has the matchup advantage, the return value will be neg
		double advantage = advantageA - advantageB;
		double advantageRounded = Math.round(advantage * 100.0) / 100.0; 
		return advantageRounded;
	}
	
	public static double statAdvantage(List<Integer> teamA, List<Integer> teamB){
		//if teamA has the stat advantage, the return value will be pos
		//if teamB has the stat advantage, the return value will be neg
		double advantage = teamA.get(20) - teamB.get(20);
		double advantageRounded = Math.round(advantage * 100.0) / 100.0; 
		return advantageRounded;
	}
	
	public static int probabilityGame(List<Integer> teamA, List<Integer> teamB){
		//the return value will be teamA's probability of winning against teamB
		int A = 0; 
		
		//statAdvantage accounts for 75% of the probability points
		double stat = statAdvantage(teamA, teamB);
		int sign = 0;
		if (stat < 0){
			sign = 1; } //if teamB has the advantage, then the sign will be 1 to signify so
			
		//very evenly matched 50-50
		if ( Math.abs(stat) >= 0 && Math.abs(stat) <= 5){
			A += 0.5 * 75; }
			
		//close game 60-40
		if ( Math.abs(stat) > 5 && Math.abs(stat) <= 10){
			if (sign == 0){
				A += 0.6 * 75; }
			else{
				A += 0.4 * 75; }
		}	
		
		//slight favorite 65-35
		if ( Math.abs(stat) > 10 && Math.abs(stat) <= 15){
			if (sign == 0){
				A += 0.65 * 75; }
			else{
				A += 0.35 * 75; }
		}	
		
		//strong favorite 70-30
		if ( Math.abs(stat) > 15 && Math.abs(stat) <= 20){
			if (sign == 0){
				A += 0.7 * 75; }
			else{
				A += 0.3 * 75; }
		}
		
		//clear favorite 75-25
		if ( Math.abs(stat) > 20 && Math.abs(stat) <= 25){
			if (sign == 0){
				A += 0.75 * 75; }
			else{
				A += 0.25 * 75; }
		}
		
		//one-sided affair 80-20
		if ( Math.abs(stat) > 25 && Math.abs(stat) <= 30){
			if (sign == 0){
				A += 0.8 * 75; }
			else{
				A += 0.2 * 75; }
		}
		
		//utter humiliation 90-10
		if ( Math.abs(stat) > 30 && Math.abs(stat) <= 40){
			if (sign == 0){
				A += 0.9 * 75; }
			else{
				A += 0.1 * 75; }
		}
		
		//oh dear 95-5
		if ( Math.abs(stat) > 40){
			if (sign == 0){
				A += 0.95 * 75; }
			else{
				A += 0.05 * 75; }
		}
		
		//matchupAdvantage accounts for 25% of the probability points
		double matchup = matchupAdvantage(teamA, teamB);
		int adv = 0;
		if (matchup < 0){
			adv = 1; } 
			
		//very evenly matched 50-50
		if ( Math.abs(matchup) >= 0 && Math.abs(matchup) <= 10){
			A += 0.5 * 25; }
			
		//close matchup 60-40
		if ( Math.abs(matchup) > 10 && Math.abs(matchup) <= 15){
			if (adv == 0){
				A += 0.6 * 25; }
				
			else{
				A += 0.4 * 25; }
		}

		//good matchup 65-35
		if ( Math.abs(matchup) > 15 && Math.abs(matchup) <= 20){
			if (adv == 0){
				A += 0.65 * 25; }
				
			else{
				A += 0.35 * 25; }
		}
		
		//heavy favorite 75-25
		if ( Math.abs(matchup) > 20 && Math.abs(matchup) <= 30){
			if (adv == 0){
				A += 0.75 * 25; }
				
			else{
				A += 0.25 * 25; }
		}
		
		//change the channel 85-15
		if ( Math.abs(matchup) > 30 && Math.abs(matchup) <= 40){
			if (adv == 0){
				A += 0.85 * 25; }
				
			else{
				A += 0.15 * 25; }
		}
		
		//jv vs. varsity 90-10
		if ( Math.abs(matchup) > 40 && Math.abs(matchup) <= 50){
			if (adv == 0){
				A += 0.9 * 25; }
				
			else{
				A += 0.1 * 25; }
		}
		
		//do you believe in miracles? 95-15
		if ( Math.abs(matchup) > 50){
			if (adv == 0){
				A += 0.95 * 25; }
				
			else{
				A += 0.05 * 25; }
		}
		
		Math.round(A);
		return A;	
			
	}
	
	public static int determineWinner (List<Integer> teamA, List<Integer> teamB){
		//the function will return 0 if team A wins, 1 if team B wins
		int probabilityA = probabilityGame(teamA, teamB);
		double chance = Math.random() * 100;
		if (chance < probabilityA){
			return 0; }
		else{
			return 1; }
	}
	
	public static String probabilityPreview (List<Integer> teamA, List<Integer> teamB, List<String> A, List<String> B){
		String answer = "Probability: ";
		int probabilityA = probabilityGame(teamA, teamB);
		answer += A.get(0) + " " + probabilityA + "%, " + B.get(0) + " " + (100 - probabilityA) + "%";
		return answer;
	}
	
	public static void simWildCardRound (){
		int did3winAFC = 0; //will be 0 if 3 wins, 1 if 6 wins
		if (determineWinner(AFCseed3, AFCseed6) == 0){
			opponentAFCseed2 = AFCseed3; 
			opponentAFCseed2info = AFCseed3info;
			opponentAFCseed2Stats = AFCseed3Stats; 
			opponentAFCseed2infoStats = AFCseed3infoStats; }
		else{
			opponentAFCseed1 = AFCseed6; 
			opponentAFCseed1info = AFCseed6info;
			opponentAFCseed1Stats = AFCseed6Stats; 
			opponentAFCseed1infoStats = AFCseed6infoStats;
			did3winAFC = 1;} 
			
		if (determineWinner(AFCseed4, AFCseed5) == 0){
			if (did3winAFC == 0){
				opponentAFCseed1 = AFCseed4; 
				opponentAFCseed1info = AFCseed4info;
				opponentAFCseed1Stats = AFCseed4Stats; 
				opponentAFCseed1infoStats = AFCseed4infoStats;}
			else{
				opponentAFCseed2 = AFCseed4; 
				opponentAFCseed2info = AFCseed4info;
				opponentAFCseed2Stats = AFCseed4Stats; 
				opponentAFCseed2infoStats = AFCseed4infoStats; } }	
		else{
			if (did3winAFC == 0){
				opponentAFCseed1 = AFCseed5; 
				opponentAFCseed1info = AFCseed5info; 
				opponentAFCseed1Stats = AFCseed5Stats; 
				opponentAFCseed1infoStats = AFCseed5infoStats; }
			else{
				opponentAFCseed2 = AFCseed5; 
				opponentAFCseed2info = AFCseed5info;
				opponentAFCseed2Stats = AFCseed5Stats; 
				opponentAFCseed2infoStats = AFCseed5infoStats; } }
		
		int did3winNFC = 0; //will be 0 if 3 wins, 1 if 6 wins
		if (determineWinner(NFCseed3, NFCseed6) == 0){
			opponentNFCseed2 = NFCseed3; 
			opponentNFCseed2info = NFCseed3info; 
			opponentNFCseed2Stats = NFCseed3Stats; 
			opponentNFCseed2infoStats = NFCseed3infoStats; }
		else{
			opponentNFCseed1 = NFCseed6; 
			opponentNFCseed1info = NFCseed6info;
			opponentNFCseed1Stats = NFCseed6Stats; 
			opponentNFCseed1infoStats = NFCseed6infoStats;
			did3winNFC = 1;} 
			
		if (determineWinner(NFCseed4, NFCseed5) == 0){
			if (did3winNFC == 0){
				opponentNFCseed1 = NFCseed4; 
				opponentNFCseed1info = NFCseed4info;
				opponentNFCseed1Stats = NFCseed4Stats; 
				opponentNFCseed1infoStats = NFCseed4infoStats; }
			else{
				opponentNFCseed2 = NFCseed4;
				opponentNFCseed2info = NFCseed4info;
				opponentNFCseed2Stats = NFCseed4Stats;
				opponentNFCseed2infoStats = NFCseed4infoStats; } }	
		else{
			if (did3winNFC == 0){
				opponentNFCseed1 = NFCseed5;
				opponentNFCseed1info = NFCseed5info; 
				opponentNFCseed1Stats = NFCseed5Stats;
				opponentNFCseed1infoStats = NFCseed5infoStats; }
			else{
				opponentNFCseed2 = NFCseed5; 
				opponentNFCseed2info = NFCseed5info; 
				opponentNFCseed2Stats = NFCseed5Stats; 
				opponentNFCseed2infoStats = NFCseed5infoStats; } }
				
		round += 1;
	}
	
	public static void simDivisionalRound() {
		if (determineWinner(AFCseed1, opponentAFCseed1) == 0){
			AFCchampionship1 = AFCseed1; 
			AFCchampionship1info = AFCseed1info;
			AFCchampionship1Stats = AFCseed1Stats; 
			AFCchampionship1infoStats = AFCseed1infoStats; }
		else{
			AFCchampionship1 = opponentAFCseed1;
			AFCchampionship1info = opponentAFCseed1info;
			AFCchampionship1Stats = opponentAFCseed1Stats;
			AFCchampionship1infoStats = opponentAFCseed1infoStats; } 
			
		if (determineWinner(AFCseed2, opponentAFCseed2) == 0){
			AFCchampionship2 = AFCseed2; 
			AFCchampionship2info = AFCseed2info; 
			AFCchampionship2Stats = AFCseed2Stats; 
			AFCchampionship2infoStats = AFCseed2infoStats; }
		else{
			AFCchampionship2 = opponentAFCseed2; 
			AFCchampionship2info = opponentAFCseed2info; 
			AFCchampionship2Stats = opponentAFCseed2Stats; 
			AFCchampionship2infoStats = opponentAFCseed2infoStats; } 
			
		if (determineWinner(NFCseed1, opponentNFCseed1) == 0){
			NFCchampionship1 = NFCseed1;
			NFCchampionship1info = NFCseed1info;
			NFCchampionship1Stats = NFCseed1Stats;
			NFCchampionship1infoStats = NFCseed1infoStats; }
		else{
			NFCchampionship1 = opponentNFCseed1;
			NFCchampionship1info = opponentNFCseed1info;
			NFCchampionship1Stats = opponentNFCseed1Stats;
			NFCchampionship1infoStats = opponentNFCseed1infoStats; } 
			
		if (determineWinner(NFCseed2, opponentNFCseed2) == 0){
			NFCchampionship2 = NFCseed2;
			NFCchampionship2info = NFCseed2info;
			NFCchampionship2Stats = NFCseed2Stats;
			NFCchampionship2infoStats = NFCseed2infoStats; }
		else{
			NFCchampionship2 = opponentNFCseed2; 
			NFCchampionship2info = opponentNFCseed2info; 
			NFCchampionship2Stats = opponentNFCseed2Stats; 
			NFCchampionship2infoStats = opponentNFCseed2infoStats; } 
		
		round += 1;
	}
	
	public static void simConferenceChampionships(){
		if (determineWinner(AFCchampionship1, AFCchampionship2) == 0){
			AFCchampion = AFCchampionship1;
			AFCchampionInfo = AFCchampionship1info; 
			AFCchampionStats = AFCchampionship1Stats;
			AFCchampionInfoStats = AFCchampionship1infoStats; }
		else{
			AFCchampion = AFCchampionship2; 
			AFCchampionInfo = AFCchampionship2info;
			AFCchampionStats = AFCchampionship2Stats; 
			AFCchampionInfoStats = AFCchampionship2infoStats; }
			
		if (determineWinner(NFCchampionship1, NFCchampionship2) == 0){
			NFCchampion = NFCchampionship1;
			NFCchampionInfo = NFCchampionship1info;
			NFCchampionStats = NFCchampionship1Stats;
			NFCchampionInfoStats = NFCchampionship1infoStats; }
		else{
			NFCchampion = NFCchampionship2;
			NFCchampionInfo = NFCchampionship2info; 
			NFCchampionStats = NFCchampionship2Stats;
			NFCchampionInfoStats = NFCchampionship2infoStats; }

		round += 1;
	}
	
	public static void simSuperBowl(){
		if (determineWinner(AFCchampion, NFCchampion) == 0){
			SuperBowlChampion = AFCchampion; 
			SuperBowlChampionInfo = AFCchampionInfo;
			SuperBowlChampionStats = AFCchampionStats; 
			SuperBowlChampionInfoStats = AFCchampionInfoStats; }
		else{
			SuperBowlChampion = NFCchampion; 
			SuperBowlChampionInfo = NFCchampionInfo; 
			SuperBowlChampionStats = NFCchampionStats; 
			SuperBowlChampionInfoStats = NFCchampionInfoStats; }
		
		round = 0;
	}

	//0 - yds, 1 - tds, 2 - interceptions
	public static List<Integer> qbStats(List<Integer> stats, List<Integer> opp){

		List<Integer> results = new ArrayList<Integer>();
		//base stats
		double yds = stats.get(3) / (stats.get(2) + 0.0);
		double td = stats.get(4) / (stats.get(2) + 0.0);
		double inter = stats.get(7) / (stats.get(2) + 0.0);
		int rating = stats.get(8);
		int finalInt = 0;
	
		//adjustment from madden rating 
		if (rating >= 87){
			yds *= 1.25;
			td *= 1.25;
			inter *= 0.75; }
		
		if (rating >= 79 && rating <= 86){
			yds *= 1.1;
			td *= 1.1;
			inter *= 0.9; }
	
		if (rating <= 75){
			yds *= 0.75;
			td *= 0.75;
			inter *= 1.25; }
	
		//vs opposing def
		int def = opp.get(7);
		if (def >= 1 && def <= 8){
			yds *= 0.75;
			td *= 0.75;
			inter *= 1.25; }
		
		if (def >= 17 && def <= 24){
			yds *= 1.1;
			td *= 1.1;
			inter *= 0.9; }
		
		if (def >= 25 && def <= 32){
			yds *= 1.25;
			td *= 1.25;
			inter *= 0.75; }
		
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			yds *= 0.8; 
			td *= 0.8;
			inter *= 1.2; }
		
		if (chance < 40 && chance > 20){
			yds *= 0.9; 
			td *= 0.9;
			inter *= 1.1; }
		
		if (chance < 60 && chance > 40){
			yds *= 1.1; 
			td *= 1.1;
			inter *= 0.9; }
		
		if (chance < 80 && chance > 60){
			yds *= 1.2; 
			td *= 1.2;
			inter *= 0.8; }
		
		//adjusting interceptions
		double turnover = Math.random() * 100;
		if (inter <= 1){
			if (turnover < (inter * 100) ){
				finalInt = 1; }
		}
	
		if (inter > 1){
			double base = Math.floor(inter);
			finalInt += base; 
			double rem = inter - base; 
			if (turnover < (rem * 100) ){
				finalInt += 1; }
		}

		int finalYds = (int) Math.round(yds);
		int finalTd = (int) Math.round(td);
	
		//some final adjustments
		double luck1 = Math.random() * 100; 
		double luck2 = Math.random() * 100; 
		
		if (def >= 32 && def <= 17){
			if (rating >= 87){
				finalYds += 75;
				finalTd += 2; }
			if (rating >= 79 && rating <= 86){
				finalYds += 50;
				finalTd += 1; }
		}
		
		if (luck1 < 20){
			finalInt += 1; }
		
		if (luck2 < 50){
			if (rating <= 86){
				finalInt += 1; }
		}
		
		results.add(finalYds);
		results.add(finalTd);
		results.add(finalInt);
		
		return results; 
		
	}
		
	//0 - yds, 1 - tds, 2 - carries, 3 - fumbles
	public static List<Integer> rbStats(List<Integer> stats, List<Integer> opp){
	
		List<Integer> results = new ArrayList<Integer>();
	
		//base stats
		double yds = stats.get(3) / (stats.get(2) + 0.0);
		double td = stats.get(4) / (stats.get(2) + 0.0);
		double car = stats.get(5) / stats.get(2);
		double fum = stats.get(6) / (stats.get(2) + 0.0);
		int rating = stats.get(7);
		int finalFum = 0;
		
		//adjustment from madden rating 
		if (rating >= 87){
			yds *= 1.25;
			td *= 1.25;
			fum *= 0.75; }
		
		if (rating >= 82 && rating <= 86){
			yds *= 1.1;
			td *= 1.1;
			fum *= 0.9; }
	
		if (rating <= 79){
			yds *= 0.75;
			td *= 0.75;
			fum *= 1.25; }
			
		//vs opposing def
		int def = opp.get(7);
		if (def >= 1 && def <= 8){
			yds *= 0.75;
			td *= 0.75;
			fum *= 1.25; }
		
		if (def >= 17 && def <= 24){
			yds *= 1.1;
			td *= 1.1;
			fum *= 0.9; }
		
		if (def >= 25 && def <= 32){
			yds *= 1.25;
			td *= 1.25;
			fum *= 0.75; }
			
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			yds *= 0.8; 
			td *= 0.8;
			fum *= 1.2; }
		
		if (chance < 40 && chance > 20){
			yds *= 0.9; 
			td *= 0.9;
			fum *= 1.1; }
		
		if (chance < 60 && chance > 40){
			yds *= 1.1; 
			td *= 1.1;
			fum *= 0.9; }
		
		if (chance < 80 && chance > 60){
			yds *= 1.2; 
			td *= 1.2;
			fum *= 0.8; }
			
		//adjusting fumbles
		double turnover = Math.random() * 100;
		if (fum <= 1){
			if (turnover < (fum * 100) ){
				finalFum = 1; }
		}
	
		if (fum > 1){
			double base = Math.floor(fum);
			finalFum += base; 
			double rem = fum - base; 
			if (turnover < (rem * 100) ){
				finalFum += 1; }
		}
		
		int finalYds = (int) Math.round(yds);
		int finalTd = (int) Math.round(td);
		int finalCar = (int) Math.round(car);
		
		//some final adjustments
		double luck1 = Math.random() * 100; 
		
		if (def >= 32 && def <= 17){
			if (rating >= 87){
				finalYds += 50;
				finalTd += 1; }
			if (rating >= 79 && rating <= 86){
				finalYds += 50; }
		}
		
		if (luck1 < 20){
			finalFum += 1; }
		
			
		results.add(finalYds);
		results.add(finalTd);
		results.add(finalCar);
		results.add(finalFum);
		
		return results;
		
	}
	
	//0 - yds, 1 - tds, 2 - receptions
	public static List<Integer> wrteStats(List<Integer> stats, List<Integer> opp){

		List<Integer> results = new ArrayList<Integer>();
	
		//base stats
		double yds = stats.get(3) / (stats.get(2) + 0.0);
		double td = stats.get(4) / (stats.get(2) + 0.0);
		double rec = stats.get(5) / (stats.get(2) + 0.0);
		int rating = stats.get(6);
		
		//adjustment from madden rating 
		if (rating >= 90){
			yds *= 1.25;
			td *= 1.25;
			rec *= 1.25; }
		
		if (rating >= 83 && rating <= 89){
			yds *= 1.1;
			td *= 1.1;
			rec *= 1.1; }
	
		if (rating <= 77){
			yds *= 0.75;
			td *= 0.75;
			rec *= 0.75; }
			
		//vs opposing def
		int def = opp.get(7);
		if (def >= 1 && def <= 8){
			yds *= 0.75;
			td *= 0.75;
			rec *= 0.75; }
		
		if (def >= 17 && def <= 24){
			yds *= 1.1;
			td *= 1.1;
			rec *= 1.1; }
		
		if (def >= 25 && def <= 32){
			yds *= 1.25;
			td *= 1.25;
			rec *= 1.25; }
			
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			yds *= 0.8; 
			td *= 0.8;
			rec *= 1.2; }
		
		if (chance < 40 && chance > 20){
			yds *= 0.9; 
			td *= 0.9;
			rec *= 0.9; }
		
		if (chance < 60 && chance > 40){
			yds *= 1.1; 
			td *= 1.1;
			rec *= 1.1; }
		
		if (chance < 80 && chance > 60){
			yds *= 1.2; 
			td *= 1.2;
			rec *= 1.1; }
			
		
		int finalYds = (int) Math.round(yds);
		int finalTd = (int) Math.round(td);
		int finalRec = (int) Math.round(rec);
	
		//some final adjustments
		
		if (def >= 32 && def <= 17){
			if (rating >= 87){
				finalYds += 75;
				finalTd += 1; }
			if (rating >= 79 && rating <= 86){
				finalYds += 50;
				finalTd += 1; }
		}
		
		results.add(finalYds);
		results.add(finalTd);
		results.add(finalRec);
		return results;
		
	}
	
	//0 - tackles, 1 - sacks, 2 - forced fum, 3 - picks, 4 - PD
	public static List<Integer> lbStats(List<Integer> stats, List<Integer> opp){

		List<Integer> results = new ArrayList<Integer>();

		double tak = stats.get(3) / (stats.get(2) + 0.0);
		double sak = stats.get(4) / (stats.get(2) + 0.0);
		double ff = stats.get(5) / (stats.get(2) + 0.0);
		double pick = stats.get(6) / (stats.get(2) + 0.0);
		double pd = stats.get(7) / (stats.get(2) + 0.0);
		int rating = stats.get(8);
		
		double finalSak = 0, finalFF= 0, finalPick = 0, finalPD = 0;
	
		//adjustment from madden rating 
		if (rating >= 88){
			tak *= 1.25;
			sak *= 1.25;
			ff *= 1.25;
			pick *= 1.25;
			pd *= 1.25; }
		
		if (rating >= 82 && rating <= 87){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1;
			pick *= 1.1;
			pd *= 1.1; }
	
		if (rating <= 76){
			tak *= 0.75;
			sak *= 0.75;
			ff *= 0.75;
			pick *= 0.75;
			pd *= 0.75; }
			
		//vs opposing off
		int off = opp.get(6);
		if (off >= 1 && off <= 8){
			tak *= 0.75;
			sak *= 0.75;
			ff *= 0.75;
			pick *= 0.75;
			pd *= 0.75; }
		
		if (off >= 17 && off <= 24){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1;
			pick *= 1.1;
			pd *= 1.1; }
		
		if (off >= 25 && off <= 32){
			tak *= 1.25;
			sak *= 1.25;
			ff *= 1.25;
			pick *= 1.25;
			pd *= 1.25; }
			
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			tak *= 0.8;
			sak *= 0.8;
			ff *= 0.8;
			pick *= 0.8;
			pd *= 0.8; }
		
		if (chance < 40 && chance > 20){
			tak *= 0.9;
			sak *= 0.9;
			ff *= 0.9;
			pick *= 0.9;
			pd *= 0.9; }
		
		if (chance < 60 && chance > 40){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1;
			pick *= 1.1;
			pd *= 1.1; }
		
		if (chance < 80 && chance > 60){
			tak *= 1.2;
			sak *= 1.2;
			ff *= 1.2;
			pick *= 1.2;
			pd *= 1.2; }
		
		//adjusting sacks
		if (sak <= 1){
			if (chance < (sak * 100) ){
				finalSak = 1; }
		}
	
		if (sak > 1){
			double base1 = Math.floor(sak);
			finalSak += base1; 
			double rem1 = sak - base1; 
			if (chance < (rem1 * 100) ){
				finalSak += 1; }
		}
		
		//adjusting forced fumbles
		if (ff <= 1){
			if (chance < (ff * 100) ){
				finalFF = 1; }
		}
	
		if (ff > 1){
			double base2 = Math.floor(ff);
			finalFF += base2; 
			double rem2 = ff - base2; 
			if (chance < (rem2 * 100) ){
				finalFF += 1; }
		}
		
		//adjusting interceptions
		if (pick <= 1){
			if (chance < (pick * 100) ){
				finalPick = 1; }
		}
	
		if (pick > 1){
			double base3 = Math.floor(pick);
			finalFF += base3; 
			double rem3 = pick - base3; 
			if (chance < (rem3 * 100) ){
				finalPick += 1; }
		}
		
		//adjusting passes defended
		if (pd <= 1){
			if (chance < (pd * 100) ){
				finalPD= 1; }
		}
	
		if (pd > 1){
			double base4 = Math.floor(pd);
			finalPD += base4; 
			double rem4 = pd - base4; 
			if (chance < (rem4 * 100) ){
				finalPD += 1; }
		}
	
		int adjTak = (int) Math.round(tak);
		int adjSak = (int) Math.round(finalSak);
		int adjFF = (int) Math.round(finalFF);
		int adjPick = (int) Math.round(finalPick);
		int adjPD = (int) Math.round(finalPD);
		
		results.add(adjTak);
		results.add(adjSak);
		results.add(adjFF);
		results.add(adjPick);
		results.add(adjPD);
		
		return results;
	}
	
	//0 - tackles, 1 - sacks, 2 - forced fum
	public static List<Integer> dlStats(List<Integer> stats, List<Integer> opp){
		
		List<Integer> results = new ArrayList<Integer>();
		
		double tak = stats.get(3) / (stats.get(2) + 0.0);
		double sak = stats.get(4) / (stats.get(2) + 0.0);
		double ff = stats.get(5) / (stats.get(2) + 0.0);
		int rating = stats.get(6);
		
		double finalSak = 0, finalFF= 0; 
		//adjustment from madden rating 
		if (rating >= 88){
			tak *= 1.25;
			sak *= 1.25;
			ff *= 1.25; }
		
		if (rating >= 82 && rating <= 87){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1; }
	
		if (rating <= 77){
			tak *= 0.75;
			sak *= 0.75;
			ff *= 0.75; }
			
		//vs opposing off
		int off = opp.get(6);
		if (off >= 1 && off <= 8){
			tak *= 0.75;
			sak *= 0.75;
			ff *= 0.75; }
		
		if (off >= 17 && off <= 24){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1; }
		
		if (off >= 25 && off <= 32){
			tak *= 1.25;
			sak *= 1.25;
			ff *= 1.25; }
			
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			tak *= 0.8;
			sak *= 0.8;
			ff *= 0.8; }
		
		if (chance < 40 && chance > 20){
			tak *= 0.9;
			sak *= 0.9;
			ff *= 0.9; }
		
		if (chance < 60 && chance > 40){
			tak *= 1.1;
			sak *= 1.1;
			ff *= 1.1; }
		
		if (chance < 80 && chance > 60){
			tak *= 1.2;
			sak *= 1.2;
			ff *= 1.2; }
		
		//adjusting sacks
		if (sak <= 1){
			if (chance < (sak * 100) ){
				finalSak = 1; }
		}
	
		if (sak > 1){
			double base1 = Math.floor(sak);
			finalSak += base1; 
			double rem1 = sak - base1; 
			if (chance < (rem1 * 100) ){
				finalSak += 1; }
		}
		
		//adjusting forced fumbles
		if (ff <= 1){
			if (chance < (ff * 100) ){
				finalFF = 1; }
		}
	
		if (ff > 1){
			double base2 = Math.floor(ff);
			finalFF += base2; 
			double rem2 = ff - base2; 
			if (chance < (rem2 * 100) ){
				finalFF += 1; }
		}
		
		int adjTak = (int) Math.round(tak);
		int adjSak = (int) Math.round(finalSak);
		int adjFF = (int) Math.round(finalFF);
		
		results.add(adjTak);
		results.add(adjSak);
		results.add(adjFF);
	
		return results;
	}
	
	//0 - tackles, 1 - picks, 2 - PD, 3 - forced fum
	public static List<Integer> dbStats(List<Integer> stats, List<Integer> opp){
		List<Integer> results = new ArrayList<Integer>();
		
		double tak = stats.get(3) / (stats.get(2) + 0.0);
		double pick = stats.get(4) / (stats.get(2) + 0.0);
		double pd = stats.get(5) / (stats.get(2) + 0.0);
		double ff = stats.get(6) / (stats.get(2) + 0.0);
		int rating = stats.get(6);
		
		double finalPick = 0, finalPD = 0, finalFF= 0; 
		//adjustment from madden rating 
		if (rating >= 89){
			tak *= 1.25;
			pick *= 1.25;
			pd *= 1.25;
			ff *= 1.25; }
		
		if (rating >= 83 && rating <= 88){
			tak *= 1.1;
			pick *= 1.1;
			pd *= 1.1;
			ff *= 1.1; }
	
		if (rating <= 79){
			tak *= 0.75;
			pick *= 0.75;
			pd *= 0.75;
			ff *= 0.75;}
			
		//vs opposing off
		int off = opp.get(6);
		if (off >= 1 && off <= 8){
			tak *= 0.75;
			pick *= 0.75;
			pd *= 0.75;
			ff *= 0.75;}
		
		if (off >= 17 && off <= 24){
			tak *= 1.1;
			pick *= 1.1;
			pd *= 1.1;
			ff *= 1.1; }
		
		if (off >= 25 && off <= 32){
			tak *= 1.25;
			pick *= 1.25;
			pd *= 1.25;
			ff *= 1.25; }
			
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			tak *= 0.8;
			pick *= 0.8;
			pd *= 0.8;
			ff *= 0.8; }
		
		if (chance < 40 && chance > 20){
			tak *= 0.9;
			pick *= 0.9;
			pd *= 0.9;
			ff *= 0.9; }
		
		if (chance < 60 && chance > 40){
			tak *= 1.1;
			pick *= 1.1;
			pd *= 1.1;
			ff *= 1.1; }
		
		if (chance < 80 && chance > 60){
			tak *= 1.25;
			pick *= 1.25;
			pd *= 1.25;
			ff *= 1.25; }
		
		//adjusting interceptions
		if (pick <= 1){
			if (chance < (pick * 100) ){
				finalPick = 1; }
		}
	
		if (pick > 1){
			double base1 = Math.floor(pick);
			finalPick += base1; 
			double rem1 = pick - base1; 
			if (chance < (rem1 * 100) ){
				finalPick += 1; }
		}
		
		//adjusting passes defended
		if (pd <= 1){
			if (chance < (pd * 100) ){
				finalPD = 1; }
		}
	
		if (pd > 1){
			double base2 = Math.floor(pd);
			finalPD += base2; 
			double rem2 = pd - base2; 
			if (chance < (rem2 * 100) ){
				finalPD += 1; }
		}
		
		//adjusting forced fumbles
		if (ff <= 1){
			if (chance < (ff * 100) ){
				finalFF = 1; }
		}
	
		if (ff > 1){
			double base3 = Math.floor(ff);
			finalFF += base3; 
			double rem3 = ff - base3; 
			if (chance < (rem3 * 100) ){
				finalFF += 1; }
		}
		
		int adjTak = (int) Math.round(tak);
		int adjPick = (int) Math.round(finalPick);
		int adjPD = (int) Math.round(finalPD);
		int adjFF = (int) Math.round(finalFF);
		
		results.add(adjTak);
		results.add(adjPick);
		results.add(adjPD);
		results.add(adjFF);
		return results;
	}
	
	public static String SSS(List<List<Integer>> aStats, List<List<Integer>> bStats, List<String> aRoster, List<String> bRoster,
		List<Integer> aTeam, List<Integer> bTeam, List<String> aTeamInfo, List<String> bTeamInfo, int win){
			
		String answer = "";	
		//win = 0 when A wins, 1 when B wins
		
		List<Integer> aQBstat = aStats.get(0), aRBstat = aStats.get(1), aWRstat = aStats.get(2),
			aFLEXstat = aStats.get(3), aD1stat = aStats.get(4), aD2stat = aStats.get(5), 
			aD3stat = aStats.get(6), aD4stat = aStats.get(7);
		
		List<Integer> bQBstat = bStats.get(0), bRBstat = bStats.get(1), bWRstat = bStats.get(2), 
			bFLEXstat = bStats.get(3), bD1stat = bStats.get(4), bD2stat = bStats.get(5), 
			bD3stat = bStats.get(6), bD4stat = bStats.get(7);
		
		//setting up team A
		List<Integer> aQB = qbStats(aQBstat, bTeam);
		List<Integer> aRB = rbStats(aRBstat, bTeam);
		List<Integer> aWR = wrteStats(aWRstat, bTeam);
		List<Integer> aFLEX = new ArrayList<Integer>();
		if (aFLEXstat.get(1) == 1){
			aFLEX = rbStats(aFLEXstat, bTeam); }
		else{
			aFLEX = wrteStats(aFLEXstat, bTeam); }
			
		List<Integer> aD1 = new ArrayList<Integer>();
		if (aD1stat.get(1) == 3){
			aD1 = lbStats(aD1stat, bTeam); }
		else{
			if (aD1stat.get(1) == 4){
				aD1 = dlStats(aD1stat, bTeam); }
			else{
				aD1 = dbStats(aD1stat, bTeam); }
		}
		
		List<Integer> aD2 = new ArrayList<Integer>();
		if (aD2stat.get(1) == 3){
			aD2 = lbStats(aD2stat, bTeam); }
		else{
			if (aD2stat.get(1) == 4){
				aD2 = dlStats(aD2stat, bTeam); }
			else{
				aD2 = dbStats(aD2stat, bTeam); }
		}
		
		List<Integer> aD3 = new ArrayList<Integer>();
		if (aD3stat.get(1) == 3){
			aD3 = lbStats(aD3stat, bTeam); }
		else{
			if (aD3stat.get(1) == 4){
				aD3 = dlStats(aD3stat, bTeam); }
			else{
				aD3 = dbStats(aD3stat, bTeam); }
		}
		
		List<Integer> aD4 = new ArrayList<Integer>();
		if (aD4stat.get(1) == 3){
			aD4 = lbStats(aD4stat, bTeam); }
		else{
			if (aD1stat.get(1) == 4){
				aD4 = dlStats(aD4stat, bTeam); }
			else{
				aD4 = dbStats(aD4stat, bTeam); }
		}
		
		int ApassingTD = aQB.get(1);
		int ArushingTD = aRB.get(1);
		int ApickThrown = aQB.get(2);
		int Afumb = aRB.get(3);
		int ArecTD = aWR.get(1);
		int AflexTD = aFLEX.get(1);
		int AD1int = 0, AD1FF = 0, AD2int = 0, AD2FF = 0, 
			AD3int = 0, AD3FF = 0, AD4int = 0, AD4FF = 0;
		if (aD1stat.get(1) == 3){
			AD1int = aD1.get(3);
			AD1FF = aD1.get(2); }
		else{
			if (aD1stat.get(1) == 4){
				AD1FF = aD1.get(2);}
			else{
				AD1int = aD1.get(1);
				AD1FF = aD1.get(3); }
		}
		
		if (aD2stat.get(1) == 3){
			AD2int = aD2.get(3);
			AD2FF = aD2.get(2); }
		else{
			if (aD2stat.get(1) == 4){
				AD2FF = aD2.get(2);}
			else{
				AD2int = aD2.get(1);
				AD2FF = aD2.get(3); }
		}
		
		if (aD3stat.get(1) == 3){
			AD3int = aD3.get(3);
			AD3FF = aD3.get(2); }
		else{
			if (aD3stat.get(1) == 4){
				AD3FF = aD3.get(2);}
			else{
				AD3int = aD3.get(1);
				AD3FF = aD3.get(3); }
		}
		
		if (aD4stat.get(1) == 3){
			AD4int = aD4.get(3);
			AD4FF = aD4.get(2); }
		else{
			if (aD4stat.get(1) == 4){
				AD4FF = aD4.get(2);}
			else{
				AD4int = aD4.get(1);
				AD4FF = aD4.get(3); }
		}
		
		//setting up team B
		List<Integer> bQB = qbStats(bQBstat, aTeam);
		List<Integer> bRB = rbStats(bRBstat, aTeam);
		List<Integer> bWR = wrteStats(bWRstat, aTeam);
		List<Integer> bFLEX = new ArrayList<Integer>();
		if (bFLEXstat.get(1) == 1){
			bFLEX = rbStats(bFLEXstat, aTeam); }
		else{
			bFLEX = wrteStats(bFLEXstat, aTeam); }
	
		List<Integer> bD1 = new ArrayList<Integer>();
		if (bD1stat.get(1) == 3){
			bD1 = lbStats(bD1stat, aTeam); }
		else{
			if (bD1stat.get(1) == 4){
				bD1 = dlStats(bD1stat, aTeam); }
			else{
				bD1 = dbStats(bD1stat, aTeam); }
		}
		
		List<Integer> bD2 = new ArrayList<Integer>();
		if (bD2stat.get(1) == 3){
			bD2 = lbStats(bD2stat, aTeam); }
		else{
			if (bD2stat.get(1) == 4){
				bD2 = dlStats(bD2stat, aTeam); }
			else{
				bD2 = dbStats(bD2stat, aTeam); }
		}
		
		List<Integer> bD3 = new ArrayList<Integer>();
		if (bD3stat.get(1) == 3){
			bD3 = lbStats(bD3stat, aTeam); }
		else{
			if (bD3stat.get(1) == 4){
				bD3 = dlStats(bD3stat, aTeam); }
			else{
				bD3 = dbStats(bD3stat, aTeam); }
		}
		
		List<Integer> bD4 = new ArrayList<Integer>();
		if (bD4stat.get(1) == 3){
			bD4 = lbStats(bD4stat, aTeam); }
		else{
			if (bD4stat.get(1) == 4){
				bD4 = dlStats(bD4stat, aTeam); }
			else{
				bD4 = dbStats(bD4stat, aTeam); }
		}
		
		int BpassingTD = bQB.get(1);
		int BrushingTD = bRB.get(1);
		int BpickThrown = bQB.get(2);
		int Bfumb = bRB.get(3);
		int BrecTD = bWR.get(1);
		int BflexTD = bFLEX.get(1);
		int BD1int = 0, BD1FF = 0, BD2int = 0, BD2FF = 0, 
			BD3int = 0, BD3FF = 0, BD4int = 0, BD4FF = 0;
		
		if (bD1stat.get(1) == 3){
			BD1int = bD1.get(3);
			BD1FF = bD1.get(2); }
		else{
			if (bD1stat.get(1) == 4){
				BD1FF = bD1.get(2);}
			else{
				BD1int = bD1.get(1);
				BD1FF = bD1.get(3); }
		}
		
		if (bD2stat.get(1) == 3){
			BD2int = bD2.get(3);
			BD2FF = bD2.get(2); }
		else{
			if (bD2stat.get(1) == 4){
				BD2FF = bD2.get(2);}
			else{
				BD2int = bD2.get(1);
				BD2FF = bD2.get(3); }
		}
		
		if (bD3stat.get(1) == 3){
			BD3int = bD3.get(3);
			BD3FF = bD3.get(2); }
		else{
			if (bD3stat.get(1) == 4){
				BD3FF = bD3.get(2);}
			else{
				BD3int = bD3.get(1);
				BD3FF = bD3.get(3); }
		}
		
		if (bD4stat.get(1) == 3){
			BD4int = bD4.get(3);
			BD4FF = bD4.get(2); }
		else{
			if (bD4stat.get(1) == 4){
				BD4FF = bD4.get(2);}
			else{
				BD4int = bD4.get(1);
				BD4FF = bD4.get(3); }
		}
		
		//ADJUSTING STATS SO THEY MAKE SENSE
		//EX: if a qb threw 3 picks, the opposing defensive players should
		//have a total of 3 picks as well
		
		//adjustment for A offense players, B defense players
		if (ArecTD + AflexTD > ApassingTD){
			ApassingTD = ArecTD + AflexTD; }
		
		if (ApassingTD > ArecTD + AflexTD){
			ArecTD += ApassingTD - (ArecTD + AflexTD); }
		
		int BDEFtotalPicks = BD1int + BD2int + BD3int + BD4int;
		int BDEFtotalFF = BD1FF + BD2FF + BD3FF + BD4FF;
			
		if (ApickThrown > BDEFtotalPicks){
			int j = 0;
			while (j < (ApickThrown - BDEFtotalPicks) ){
				double chance = Math.random() * 100;
				if (chance < 50){
					BD1int += 1; }
				else{
					if (chance < 75){
						BD2int += 1; }
					else{
						if (chance < 90){
							BD3int += 1; }
						else{
							BD4int += 1; }
					}
				}
				
				j += 1; }
		}
		
		if (ApickThrown < BDEFtotalPicks){
			ApickThrown += (BDEFtotalPicks - ApickThrown); }
			
		
		if (Afumb > BDEFtotalFF){
			int k = 0;
			while (k < (Afumb - BDEFtotalFF) ){
				double chance = Math.random() * 100;
				if (chance < 50){
					BD1FF += 1; }
				else{
					if (chance < 75){
						BD2FF += 1; }
					else{
						if (chance < 90){
							BD3FF += 1; }
						else{
							BD4FF += 1; }
					}
				}
				
				k += 1; }
		}
		
		if (Afumb < BDEFtotalFF){
			Afumb += (BDEFtotalFF - Afumb); }
			
		
		//adjustment for B offense players, A defense players
		if (BrecTD + BflexTD > BpassingTD){
			BpassingTD = BrecTD + BflexTD; }
		
		if (BpassingTD > BrecTD + BflexTD){
			BrecTD += BpassingTD - (BrecTD + BflexTD); }
		
		int ADEFtotalPicks = AD1int + AD2int + AD3int + AD4int;
		int ADEFtotalFF = AD1FF + AD2FF + AD3FF + AD4FF;
			
		if (BpickThrown > ADEFtotalPicks){
			int l = 0;
			while (l < (BpickThrown - ADEFtotalPicks) ){
				double chance = Math.random() * 100;
				if (chance < 50){
					AD1int += 1; }
				else{
					if (chance < 75){
						AD2int += 1; }
					else{
						if (chance < 90){
							AD3int += 1; }
						else{
							AD4int += 1; }
					}
				}
				
				l += 1; }
		}
		
		if (BpickThrown < ADEFtotalPicks){
			BpickThrown += (ADEFtotalPicks - BpickThrown); }
			
		
		if (Bfumb > ADEFtotalFF){
			int m = 0;
			while (m < (Bfumb - ADEFtotalFF) ){
				double chance = Math.random() * 100;
				if (chance < 50){
					AD1FF += 1; }
				else{
					if (chance < 75){
						AD2FF += 1; }
					else{
						if (chance < 90){
							AD3FF += 1; }
						else{
							AD4FF += 1; }
					}
				}
				
				m += 1; }
		}
		
		if (Bfumb < ADEFtotalFF){
			Bfumb += (ADEFtotalFF - Bfumb); }
		
		String teamASTATS = "", qbAS = "", rbAS = "", wrAS = "", flexAS = "", d1AS = "", d2AS = "", d3AS = "", d4AS = "";
		String teamBSTATS = "", qbBS = "", rbBS = "", wrBS = "", flexBS = "", d1BS = "", d2BS = "", d3BS = "", d4BS = "";
		
		int ASCORE = ApassingTD + ArushingTD;
		int BSCORE = BpassingTD + BrushingTD;
		
		if (win == 0){
			if (ASCORE < BSCORE){
				int diffA = BSCORE - ASCORE;
				if (diffA == 1){
					ApassingTD += 1;
					ArecTD += 1; }
				if (diffA == 2){
					ApassingTD += 1;
					ArecTD += 1;
					ArushingTD += 1; }
				if (diffA == 3){
					ApassingTD += 2;
					ArecTD += 2;
					ArushingTD += 1; }
				if (diffA == 4){
					ApassingTD += 2;
					ArecTD += 1;
					AflexTD += 1;
					ArushingTD += 2; }
			}
		}
		
		if (win == 1){
			if (BSCORE < ASCORE){
				int diffB = ASCORE - BSCORE;
				if (diffB == 1){
					BpassingTD += 1;
					BrecTD += 1; }
				if (diffB == 2){
					BpassingTD += 1;
					BrecTD += 1;
					BrushingTD += 1; }
				if (diffB == 3){
					BpassingTD += 2;
					BrecTD += 2;
					BrushingTD += 1; }
				if (diffB == 4){
					BpassingTD += 2;
					BrecTD += 1;
					BflexTD += 1;
					BrushingTD += 2; }
			}
		}
		
		ASCORE *= 7;
		BSCORE *= 7;
		if (ASCORE == BSCORE){
				ASCORE += 3; }
		
		//all offensive players
		qbAS += aRoster.get(0) + ": " + aQB.get(0) + " Yards, " + ApassingTD + 
			" Touchdowns, " + ApickThrown + " Interceptions \n";
		
		rbAS += aRoster.get(1) + ": " + aRB.get(0) + " Yards, ";
		if (ArushingTD > 0){
			rbAS += ArushingTD + " Touchdowns, "; }
		rbAS += aRB.get(2) + " Carries";
		if (Afumb > 0){
			rbAS += ", " + Afumb + " Fumbles"; }
		rbAS += "\n";
		
		
		wrAS += aRoster.get(2) + ": " + aWR.get(0) + " Yards, ";
		if (ArecTD > 0){
			wrAS += ArecTD + " Touchdowns, "; }
		wrAS += aWR.get(2) + " Receptions \n";
			
		flexAS += aRoster.get(3) + ": " + aFLEX.get(0) + " Yards, ";
		if (aFLEXstat.get(1) == 1){
			if (AflexTD > 0){
				flexAS += AflexTD + " Touchdowns, "; }
			flexAS += aFLEX.get(2) + " Carries \n\n"; 
		}
		else{
			if (AflexTD > 0){
				flexAS += AflexTD + " Touchdowns, "; }
			flexAS += aFLEX.get(2) + " Receptions \n\n";
			
		}
		
		qbBS += bRoster.get(0) + ": " + bQB.get(0) + " Yards, " + BpassingTD + 
			" Touchdowns, " + BpickThrown + " Interceptions \n";
		
		rbBS += bRoster.get(1) + ": " + bRB.get(0) + " Yards, ";
		if (BrushingTD > 0){
			rbBS += BrushingTD + " Touchdowns, "; }
		rbBS += bRB.get(2) + " Carries";
		if (Bfumb > 0){
			rbBS += ", " + Bfumb + " Fumbles"; }
		rbBS += "\n";
	
		wrBS += bRoster.get(2) + ": " + bWR.get(0) + " Yards, ";
		if (BrecTD > 0){
			wrBS += BrecTD + " Touchdowns, "; }
		wrBS += bWR.get(2) + " Receptions \n";
			
		flexBS += bRoster.get(3) + ": " + bFLEX.get(0) + " Yards, ";
		if (bFLEXstat.get(1) == 1){
			if (BflexTD > 0){
				flexBS += BflexTD + " Touchdowns, "; }
			flexBS += bFLEX.get(2) + " Carries \n\n";
		}
		else{
			if (BflexTD > 0){
				flexBS += BflexTD + " Touchdowns, "; }
			flexBS += bFLEX.get(2) + " Receptions \n\n";
		}
		
		//all defensive players
		d1AS += aRoster.get(4) + ": ";
		if (aD1stat.get(1) == 3){
			d1AS += aD1.get(0) + " Tackles";
			if (aD1.get(1) > 0){
				d1AS += ", " + aD1.get(1) + " Sacks"; }
			if (AD1FF > 0){
				d1AS += ", " + AD1FF + " Forced Fumbles"; }
			if (AD1int > 0){
				d1AS += ", " + AD1int + " Interceptions"; }
			if (aD1.get(4) > 0){
				d1AS += ", " + aD1.get(4) + " Passes Defended"; }
			d1AS += "\n"; }
		else{
			if (aD1stat.get(1) == 4){
				d1AS += aD1.get(0) + " Tackles";
				if (aD1.get(1) > 0){
					d1AS += ", " + aD1.get(1) + " Sacks"; }
				if (AD1FF > 0){
					d1AS += ", " + AD1FF + " Forced Fumbles"; }
				d1AS += "\n"; }
			else{
				d1AS += aD1.get(0) + " Tackles";
				if (AD1int > 0){
					d1AS += ", " + AD1int + " Interceptions"; }
				if (aD1.get(2) > 0){
					d1AS += ", " + aD1.get(2) + " Passes Defended"; }
				if (AD1FF > 0){
					d1AS += ", " + AD1FF + " Forced Fumbles"; }
				d1AS += "\n"; }
			}
		
		d2AS += aRoster.get(5) + ": ";
		if (aD2stat.get(1) == 3){
			d2AS += aD2.get(0) + " Tackles";
			if (aD2.get(1) > 0){
				d2AS += ", " + aD2.get(1) + " Sacks"; }
			if (AD2FF > 0){
				d2AS += ", " + AD2FF + " Forced Fumbles"; }
			if (AD2int > 0){
				d2AS += ", " + AD2int + " Interceptions"; }
			if (aD2.get(4) > 0){
				d2AS += ", " + aD2.get(4) + " Passes Defended"; }
			d2AS += "\n"; }
		else{
			if (aD2stat.get(1) == 4){
				d2AS += aD2.get(0) + " Tackles";
				if (aD2.get(1) > 0){
					d2AS += ", " + aD2.get(1) + " Sacks"; }
				if (AD2FF > 0){
					d2AS += ", " + AD2FF + " Forced Fumbles"; }
				d2AS += "\n"; }
			else{
				d2AS += aD2.get(0) + " Tackles";
				if (AD2int > 0){
					d2AS += ", " + AD2int + " Interceptions"; }
				if (aD2.get(2) > 0){
					d2AS += ", " + aD2.get(2) + " Passes Defended"; }
				if (AD2FF > 0){
					d2AS += ", " + AD2FF + " Forced Fumbles"; }
				d2AS += "\n"; }
			}
			
		d3AS += aRoster.get(6) + ": ";
		if (aD3stat.get(1) == 3){
			d3AS += aD3.get(0) + " Tackles";
			if (aD3.get(1) > 0){
				d3AS += ", " + aD3.get(1) + " Sacks"; }
			if (AD3FF > 0){
				d3AS += ", " + AD3FF + " Forced Fumbles"; }
			if (AD3int > 0){
				d3AS += ", " + AD3int + " Interceptions"; }
			if (aD3.get(4) > 0){
				d3AS += ", " + aD3.get(4) + " Passes Defended"; }
			d3AS += "\n"; }
		else{
			if (aD3stat.get(1) == 4){
				d3AS += aD3.get(0) + " Tackles";
				if (aD3.get(1) > 0){
					d3AS += ", " + aD3.get(1) + " Sacks"; }
				if (AD3FF > 0){
					d3AS += ", " + AD3FF + " Forced Fumbles"; }
				d3AS += "\n"; }
			else{
				d3AS += aD3.get(0) + " Tackles";
				if (AD3int > 0){
					d3AS += ", " + AD3int + " Interceptions"; }
				if (aD3.get(2) > 0){
					d3AS += ", " + aD3.get(2) + " Passes Defended"; }
				if (AD3FF > 0){
					d3AS += ", " + AD3FF + " Forced Fumbles"; }
				d3AS += "\n"; }
			}
		
		d4AS += aRoster.get(7) + ": ";
		if (aD4stat.get(1) == 3){
			d4AS += aD4.get(0) + " Tackles";
			if (aD4.get(1) > 0){
				d4AS += ", " + aD4.get(1) + " Sacks"; }
			if (AD4FF > 0){
				d4AS += ", " + AD4FF + " Forced Fumbles"; }
			if (AD4int > 0){
				d4AS += ", " + AD4int + " Interceptions"; }
			if (aD4.get(4) > 0){
				d4AS += ", " + aD4.get(4) + " Passes Defended"; }
			d4AS += "\n"; }
		else{
			if (aD4stat.get(1) == 4){
				d4AS += aD4.get(0) + " Tackles";
				if (aD4.get(1) > 0){
					d4AS += ", " + aD4.get(1) + " Sacks"; }
				if (AD4FF > 0){
					d4AS += ", " + AD4FF + " Forced Fumbles"; }
				d4AS += "\n"; }
			else{
				d4AS += aD4.get(0) + " Tackles";
				if (AD4int > 0){
					d4AS += ", " + AD4int + " Interceptions"; }
				if (aD4.get(2) > 0){
					d4AS += ", " + aD4.get(2) + " Passes Defended"; }
				if (AD4FF > 0){
					d4AS += ", " + AD4FF + " Forced Fumbles"; }
				d4AS += "\n"; }
		}
		
		d1BS += bRoster.get(4) + ": ";
		if (bD1stat.get(1) == 3){
			d1BS += bD1.get(0) + " Tackles";
			if (bD1.get(1) > 0){
				d1BS += ", " + bD1.get(1) + " Sacks"; }
			if (BD1FF > 0){
				d1BS += ", " + BD1FF + " Forced Fumbles"; }
			if (BD1int > 0){
				d1BS += ", " + BD1int + " Interceptions"; }
			if (bD1.get(4) > 0){
				d1BS += ", " + bD1.get(4) + " Passes Defended"; }
			d1BS += "\n"; }
		else{
			if (bD1stat.get(1) == 4){
				d1BS += bD1.get(0) + " Tackles";
				if (bD1.get(1) > 0){
					d1BS += ", " + bD1.get(1) + " Sacks"; }
				if (BD1FF > 0){
					d1BS += ", " + BD1FF + " Forced Fumbles"; }
				d1BS += "\n"; }
			else{
				d1BS += bD1.get(0) + " Tackles";
				if (BD1int > 0){
					d1BS += ", " + BD1int + " Interceptions"; }
				if (bD1.get(2) > 0){
					d1BS += ", " + bD1.get(2) + " Passes Defended"; }
				if (BD1FF > 0){
					d1BS += ", " + BD1FF + " Forced Fumbles"; }
				d1BS += "\n"; }
			}
		
		d2BS += bRoster.get(5) + ": ";
		if (bD2stat.get(1) == 3){
			d2BS += bD2.get(0) + " Tackles";
			if (bD2.get(1) > 0){
				d2BS += ", " + bD2.get(1) + " Sacks"; }
			if (BD2FF > 0){
				d2BS += ", " + BD2FF + " Forced Fumbles"; }
			if (BD2int > 0){
				d2BS += ", " + BD2int + " Interceptions"; }
			if (bD2.get(4) > 0){
				d2BS += ", " + bD2.get(4) + " Passes Defended"; }
			d2BS += "\n"; }
		else{
			if (bD2stat.get(1) == 4){
				d2BS += bD2.get(0) + " Tackles";
				if (bD2.get(1) > 0){
					d2BS += ", " + bD2.get(1) + " Sacks"; }
				if (BD2FF > 0){
					d2BS += ", " + BD2FF + " Forced Fumbles"; }
				d2BS += "\n"; }
			else{
				d2BS += bD2.get(0) + " Tackles";
				if (BD2int > 0){
					d2BS += ", " + BD2int + " Interceptions"; }
				if (bD2.get(2) > 0){
					d2BS += ", " + bD2.get(2) + " Passes Defended"; }
				if (BD2FF > 0){
					d2BS += ", " + BD2FF + " Forced Fumbles"; }
				d2BS += "\n"; }
			}
			
		d3BS += bRoster.get(6) + ": ";
		if (bD3stat.get(1) == 3){
			d3BS += bD3.get(0) + " Tackles";
			if (bD3.get(1) > 0){
				d3BS += ", " + bD3.get(1) + " Sacks"; }
			if (BD3FF > 0){
				d3BS += ", " + BD3FF + " Forced Fumbles"; }
			if (BD3int > 0){
				d3BS += ", " + BD3int + " Interceptions"; }
			if (bD3.get(4) > 0){
				d3BS += ", " + bD3.get(4) + " Passes Defended"; }
			d3BS += "\n"; }
		else{
			if (bD3stat.get(1) == 4){
				d3BS += bD3.get(0) + " Tackles";
				if (bD3.get(1) > 0){
					d3BS += ", " + bD3.get(1) + " Sacks"; }
				if (BD3FF > 0){
					d3BS += ", " + BD3FF + " Forced Fumbles"; }
				d3BS += "\n"; }
			else{
				d3BS += bD3.get(0) + " Tackles";
				if (BD3int > 0){
					d3BS += ", " + BD3int + " Interceptions"; }
				if (bD3.get(2) > 0){
					d3BS += ", " + bD3.get(2) + " Passes Defended"; }
				if (BD3FF > 0){
					d3BS += ", " + BD3FF + " Forced Fumbles"; }
				d3BS += "\n"; }
			}
			
		d4BS += bRoster.get(7) + ": ";
		if (bD4stat.get(1) == 3){
			d4BS += bD4.get(0) + " Tackles";
			if (bD4.get(1) > 0){
				d4BS += ", " + bD4.get(1) + " Sacks"; }
			if (BD4FF > 0){
				d4BS += ", " + BD4FF + " Forced Fumbles"; }
			if (BD4int > 0){
				d4BS += ", " + BD4int + " Interceptions"; }
			if (bD4.get(4) > 0){
				d4BS += ", " + bD4.get(4) + " Passes Defended"; }
			d4BS += "\n"; }
		else{
			if (bD4stat.get(1) == 4){
				d4BS += bD4.get(0) + " Tackles";
				if (bD4.get(1) > 0){
					d4BS += ", " + bD4.get(1) + " Sacks"; }
				if (BD4FF > 0){
					d4BS += ", " + BD4FF + " Forced Fumbles"; }
				d4BS += "\n"; }
			else{
				d4BS += bD4.get(0) + " Tackles";
				if (BD4int > 0){
					d4BS += ", " + BD4int + " Interceptions"; }
				if (bD4.get(2) > 0){
					d4BS += ", " + bD4.get(2) + " Passes Defended"; }
				if (BD4FF > 0){
					d4BS += ", " + BD4FF + " Forced Fumbles"; }
				d4BS += "\n"; }
			}
			
		teamASTATS += aTeamInfo.get(0) + ": \n" + qbAS + rbAS + wrAS + flexAS + d1AS + d2AS + d3AS + d4AS + "\n";
		teamBSTATS += bTeamInfo.get(0) + ": \n" + qbBS + rbBS + wrBS + flexBS + d1BS + d2BS + d3BS + d4BS + "\n";
			
		answer += teamASTATS + teamBSTATS; 
		if (win == 0){
			answer += "Winner: " + aTeamInfo.get(0) + " " + ASCORE + "-" + BSCORE; }
		else{
			answer += "Winner: " + bTeamInfo.get(0) + " " + BSCORE + "-" + ASCORE; }
		
		
	
		return answer; 
	}
	
	public static String playoffPicture(){
		String answer = "", currentRound = "\nROUND: ", AFC = "AFC \n", NFC = "NFC \n", upcoming = "Upcoming games: \n";
		if (round == 0){
			currentRound += "Wild Card Round \n\n";
			upcoming += AFCseed3info.get(0) + AFCseed3info.get(4) +
				" vs. " + AFCseed6info.get(0) + AFCseed6info.get(4) + "\n" 
				+ probabilityPreview(AFCseed3, AFCseed6, AFCseed3info, AFCseed6info) + "\n\n";
			upcoming += AFCseed4info.get(0) + AFCseed4info.get(4) +
				" vs. " + AFCseed5info.get(0) + AFCseed5info.get(4) + "\n"
				+ probabilityPreview(AFCseed4, AFCseed5, AFCseed4info, AFCseed5info) + "\n\n";
			upcoming += NFCseed3info.get(0) + NFCseed3info.get(4) + 
				" vs. " + NFCseed6info.get(0) + NFCseed6info.get(4) + "\n"
				+ probabilityPreview(NFCseed3, NFCseed6, NFCseed3info, NFCseed6info) + "\n\n";
			upcoming += NFCseed4info.get(0) + NFCseed4info.get(4) + 
				" vs. " + NFCseed5info.get(0) + NFCseed5info.get(4) + "\n"
				+ probabilityPreview(NFCseed4, NFCseed5, NFCseed4info, NFCseed5info) + "\n\n"; }
		
		if (round == 1){
			currentRound += "Divisional Round \n\n";
			upcoming += AFCseed1info.get(0) + AFCseed1info.get(4) + " vs. " 
				+ opponentAFCseed1info.get(0) + opponentAFCseed1info.get(4) + "\n"
				+ probabilityPreview(AFCseed1, opponentAFCseed1, AFCseed1info, opponentAFCseed1info) + "\n\n";
			upcoming += AFCseed2info.get(0) + AFCseed2info.get(4) + " vs. " 
				+ opponentAFCseed2info.get(0) + opponentAFCseed2info.get(4) + "\n"
				+ probabilityPreview(AFCseed2, opponentAFCseed2, AFCseed2info, opponentAFCseed2info) + "\n\n";
			upcoming += NFCseed1info.get(0) + NFCseed1info.get(4) + " vs. " 
				+ opponentNFCseed1info.get(0) + opponentNFCseed1info.get(4) + "\n"
				+ probabilityPreview(NFCseed1, opponentNFCseed1, NFCseed1info, opponentNFCseed1info) + "\n\n";
			upcoming += NFCseed2info.get(0) + NFCseed2info.get(4) + " vs. " 
				+ opponentNFCseed2info.get(0) + opponentNFCseed2info.get(4) + "\n"
				+ probabilityPreview(NFCseed2, opponentNFCseed2, NFCseed2info, opponentNFCseed2info) + "\n\n"; }
		
		if (round == 2){
			currentRound += "Conference Championships \n\n"; 
			upcoming += AFCchampionship1info.get(0) + AFCchampionship1info.get(4) + 
				" vs. " + AFCchampionship2info.get(0) + AFCchampionship2info.get(4) +  "\n"
				+ probabilityPreview(AFCchampionship1, AFCchampionship2, AFCchampionship1info, AFCchampionship2info) + "\n\n";
			upcoming += NFCchampionship1info.get(0) + NFCchampionship1info.get(4) +
				" vs. " + NFCchampionship2info.get(0) + NFCchampionship2info.get(4) + 	"\n"
				+ probabilityPreview(NFCchampionship1, NFCchampionship2, NFCchampionship1info, NFCchampionship2info) + "\n\n"; }
		
		if (round == 3){
			currentRound += "SUPER BOWL \n\n"; 
			upcoming += AFCchampionInfo.get(0) + AFCchampionInfo.get(4) +
				" vs. " + NFCchampionInfo.get(0) + NFCchampionInfo.get(4) + "\n"
				+ probabilityPreview(AFCchampion, NFCchampion, AFCchampionInfo, NFCchampionInfo) + "\n\n"; }
		
		AFC += "1) " + AFCseed1info.get(0) + AFCseed1info.get(1) + "\n" 
			+ "Offense: " + AFCseed1info.get(2) + "     Defense: " + AFCseed1info.get(3) + "\n\n";
		AFC += "2) " + AFCseed2info.get(0) + AFCseed2info.get(1) + "\n" 
			+ "Offense: " + AFCseed2info.get(2) + "     Defense: " + AFCseed2info.get(3) + "\n\n";
		AFC += "3) " + AFCseed3info.get(0) + AFCseed3info.get(1) + "\n" 
			+ "Offense: " + AFCseed3info.get(2) + "     Defense: " + AFCseed3info.get(3) + "\n\n";
		AFC += "4) " + AFCseed4info.get(0) + AFCseed4info.get(1) + "\n" 
			+ "Offense: " + AFCseed4info.get(2) + "     Defense: " + AFCseed4info.get(3) + "\n\n";
		AFC += "5) " + AFCseed5info.get(0) + AFCseed5info.get(1) + "\n"
			+ "Offense: " + AFCseed5info.get(2) + "     Defense: " + AFCseed5info.get(3) + "\n\n";
		AFC += "6) " + AFCseed6info.get(0) + AFCseed6info.get(1) + "\n"
			+ "Offense: " + AFCseed6info.get(2) + "     Defense: " + AFCseed6info.get(3) + "\n\n";
		
		NFC += "1) " + NFCseed1info.get(0) + NFCseed1info.get(1) + "\n"
			+ "Offense: " + NFCseed1info.get(2) + "     Defense: " + NFCseed1info.get(3) + "\n\n";
		NFC += "2) " + NFCseed2info.get(0) + NFCseed2info.get(1) + "\n"
			+ "Offense: " + NFCseed2info.get(2) + "     Defense: " + NFCseed2info.get(3) + "\n\n";
		NFC += "3) " + NFCseed3info.get(0) + NFCseed3info.get(1) + "\n"
			+ "Offense: " + NFCseed3info.get(2) + "     Defense: " + NFCseed3info.get(3) + "\n\n";
		NFC += "4) " + NFCseed4info.get(0) + NFCseed4info.get(1) + "\n"
			+ "Offense: " + NFCseed4info.get(2) + "     Defense: " + NFCseed4info.get(3) + "\n\n";
		NFC += "5) " + NFCseed5info.get(0) + NFCseed5info.get(1) + "\n"
			+ "Offense: " + NFCseed5info.get(2) + "     Defense: " + NFCseed5info.get(3) + "\n\n";
		NFC += "6) " + NFCseed6info.get(0) + NFCseed6info.get(1) + "\n"
			+ "Offense: " + NFCseed6info.get(2) + "     Defense: " + NFCseed6info.get(3) + "\n\n";
		
		if (round == 0){
			answer += currentRound + AFC + NFC + upcoming; }
		else{
			answer += currentRound + upcoming; }
		
		return answer;
	}
	
	public static String simResults(int round){
		String answer = "Results: \n\n";
		if (round == 0){
			simWildCardRound();
			
			String AFC3vs6 = AFCseed3info.get(0) + AFCseed3info.get(4) + 
				" vs. " + AFCseed6info.get(0) + AFCseed6info.get(4) + "\n\n";
			if (opponentAFCseed2.equals(AFCseed3)){
				AFC3vs6 += SSS(AFCseed3Stats, AFCseed6Stats, AFCseed3infoStats, AFCseed6infoStats, AFCseed3, AFCseed6,
					AFCseed3info, AFCseed6info, 0) + "\n\n"; }
			else{
				AFC3vs6 += SSS(AFCseed3Stats, AFCseed6Stats, AFCseed3infoStats, AFCseed6infoStats, AFCseed3, AFCseed6,
					AFCseed3info, AFCseed6info, 1) + "\n\n"; }
		
				
			String AFC4vs5 =  AFCseed4info.get(0) + AFCseed4info.get(4) +
				" vs. " + AFCseed5info.get(0) + AFCseed5info.get(4) + "\n";	
			if (opponentAFCseed1.equals(AFCseed4) || opponentAFCseed2.equals(AFCseed4)){
				AFC4vs5 += SSS(AFCseed4Stats, AFCseed5Stats, AFCseed4infoStats, AFCseed5infoStats, AFCseed4, AFCseed5,
					AFCseed4info, AFCseed5info, 0) + "\n\n"; }
			else{
				AFC4vs5 += SSS(AFCseed4Stats, AFCseed5Stats, AFCseed4infoStats, AFCseed5infoStats, AFCseed4, AFCseed5,
					AFCseed4info, AFCseed5info, 1) + "\n\n"; }
				
			String NFC3vs6 = NFCseed3info.get(0) + NFCseed3info.get(4) + 
				" vs. " + NFCseed6info.get(0) + NFCseed6info.get(4) + "\n";
			if (opponentNFCseed2.equals(NFCseed3)){
				NFC3vs6 += SSS(NFCseed3Stats, NFCseed6Stats, NFCseed3infoStats, NFCseed6infoStats, NFCseed3, NFCseed6,
					NFCseed3info, NFCseed6info, 0) + "\n\n"; }
			else{
				NFC3vs6 += SSS(NFCseed3Stats, NFCseed6Stats, NFCseed3infoStats, NFCseed6infoStats, NFCseed3, NFCseed6,
					NFCseed3info, NFCseed6info, 1) + "\n\n"; }
			
			String NFC4vs5 = NFCseed4info.get(0) + NFCseed4info.get(4) +
				" vs. " + NFCseed5info.get(0) + NFCseed5info.get(4) + "\n";	
			if (opponentNFCseed1.equals(NFCseed4) || opponentNFCseed2.equals(NFCseed4)){
				NFC4vs5 += SSS(NFCseed4Stats, NFCseed5Stats, NFCseed4infoStats, NFCseed5infoStats, NFCseed4, NFCseed5,
					NFCseed4info, NFCseed5info, 0) + "\n\n"; }
			else{
				NFC4vs5 += SSS(NFCseed4Stats, NFCseed5Stats, NFCseed4infoStats, NFCseed5infoStats, NFCseed4, NFCseed5,
					NFCseed4info, NFCseed5info, 1) + "\n\n"; }
				
			answer += AFC3vs6 + AFC4vs5 + NFC3vs6 + NFC4vs5;
		}
		
		if (round == 1){
			simDivisionalRound();
			
			String AFC1 = AFCseed1info.get(0) + AFCseed1info.get(4) +
				" vs. " + opponentAFCseed1info.get(0) + opponentAFCseed1info.get(4) + "\n";
			if (AFCchampionship1.equals(AFCseed1)){
				AFC1 += SSS(AFCseed1Stats, opponentAFCseed1Stats, AFCseed1infoStats, opponentAFCseed1infoStats, 
					AFCseed1, opponentAFCseed1, AFCseed1info, opponentAFCseed1info, 0) + "\n\n"; }
			else{
				AFC1 += SSS(AFCseed1Stats, opponentAFCseed1Stats, AFCseed1infoStats, opponentAFCseed1infoStats, 
					AFCseed1, opponentAFCseed1, AFCseed1info, opponentAFCseed1info, 1) + "\n\n";}
				
			String AFC2 = AFCseed2info.get(0) + AFCseed2info.get(4) +
				" vs. " + opponentAFCseed2info.get(0) + opponentAFCseed2info.get(4) + "\n";
			if (AFCchampionship2.equals(AFCseed2)){
				AFC2 += SSS(AFCseed2Stats, opponentAFCseed2Stats, AFCseed2infoStats, opponentAFCseed2infoStats, 
					AFCseed2, opponentAFCseed2, AFCseed2info, opponentAFCseed2info, 0) + "\n\n";  }
			else{
				AFC2 += SSS(AFCseed2Stats, opponentAFCseed2Stats, AFCseed2infoStats, opponentAFCseed2infoStats, 
					AFCseed2, opponentAFCseed2, AFCseed2info, opponentAFCseed2info, 1) + "\n\n"; }
				
			String NFC1 = NFCseed1info.get(0) + NFCseed1info.get(4) +
				" vs. " + opponentNFCseed1info.get(0) + opponentNFCseed1info.get(4) + "\n";
			if (NFCchampionship1.equals(NFCseed1)){
				NFC1 += SSS(NFCseed1Stats, opponentNFCseed1Stats, NFCseed1infoStats, opponentNFCseed1infoStats, 
					NFCseed1, opponentNFCseed1, NFCseed1info, opponentNFCseed1info, 0) + "\n\n";  }
			else{
				NFC1 += SSS(NFCseed1Stats, opponentNFCseed1Stats, NFCseed1infoStats, opponentNFCseed1infoStats, 
					NFCseed1, opponentNFCseed1, NFCseed1info, opponentNFCseed1info, 1) + "\n\n"; }
			
			String NFC2 = NFCseed2info.get(0) + NFCseed2info.get(4) +
				" vs. " + opponentNFCseed2info.get(0) + opponentNFCseed2info.get(4) + "\n";
			if (NFCchampionship2.equals(NFCseed2)){
				NFC2 += SSS(NFCseed2Stats, opponentNFCseed2Stats, NFCseed2infoStats, opponentNFCseed2infoStats, 
					NFCseed2, opponentNFCseed2, NFCseed2info, opponentNFCseed2info, 0) + "\n\n"; }
			else{
				NFC2 += SSS(NFCseed2Stats, opponentNFCseed2Stats, NFCseed2infoStats, opponentNFCseed2infoStats, 
					NFCseed2, opponentNFCseed2, NFCseed2info, opponentNFCseed2info, 1) + "\n\n"; }
				
			answer += AFC1 + AFC2 + NFC1 + NFC2;
		}
		
		
		if (round == 2){
			simConferenceChampionships();
			
			String AFCchampionship = AFCchampionship1info.get(0) + AFCchampionship1info.get(4) +
				" vs. " + AFCchampionship2info.get(0) + AFCchampionship2info.get(4) + "\n";
			if (AFCchampion.equals(AFCchampionship1)){
				AFCchampionship += SSS(AFCchampionship1Stats, AFCchampionship2Stats, AFCchampionship1infoStats, 
					AFCchampionship2infoStats, AFCchampionship1, AFCchampionship2, AFCchampionship1info, 
					AFCchampionship2info, 0) + "\n\n"; }
			else{
				AFCchampionship += SSS(AFCchampionship1Stats, AFCchampionship2Stats, AFCchampionship1infoStats, 
					AFCchampionship2infoStats, AFCchampionship1, AFCchampionship2, AFCchampionship1info, 
					AFCchampionship2info, 1) + "\n\n"; }

				
			String NFCchampionship = NFCchampionship1info.get(0) + NFCchampionship1info.get(4) +
				" vs. " + NFCchampionship2info.get(0) + NFCchampionship2info.get(4) + "\n";
			if (NFCchampion.equals(NFCchampionship1)){
				NFCchampionship += SSS(NFCchampionship1Stats, NFCchampionship2Stats, NFCchampionship1infoStats, 
					NFCchampionship2infoStats, NFCchampionship1, NFCchampionship2, NFCchampionship1info, 
					NFCchampionship2info, 0) + "\n\n"; }
			else{
				NFCchampionship += SSS(NFCchampionship1Stats, NFCchampionship2Stats, NFCchampionship1infoStats, 
					NFCchampionship2infoStats, NFCchampionship1, NFCchampionship2, NFCchampionship1info, 
					NFCchampionship2info, 1) + "\n\n";}
			
			answer += AFCchampionship + NFCchampionship;
		}
		
		if (round == 3){
			simSuperBowl();
			
			String superBowl = AFCchampionInfo.get(0) + AFCchampionInfo.get(4) +
				" vs. " + NFCchampionInfo.get(0) + NFCchampionInfo.get(4) + "\n";
			if (AFCchampion.equals(SuperBowlChampion)){
				superBowl += SSS(AFCchampionStats, NFCchampionStats, AFCchampionInfoStats, NFCchampionInfoStats, 
					AFCchampion, NFCchampion, AFCchampionInfo, NFCchampionInfo, 0) + "\n\n";
				superBowl += "SUPER BOWL CHAMPS: The " + AFCchampionInfo.get(0) + "!"; }
			else{
				superBowl += SSS(AFCchampionStats, NFCchampionStats, AFCchampionInfoStats, NFCchampionInfoStats, 
					AFCchampion, NFCchampion, AFCchampionInfo, NFCchampionInfo, 1) + "\n\n";
				superBowl += "SUPER BOWL CHAMPS: The " + NFCchampionInfo.get(0) + "!"; }
				
			answer += superBowl;
		}
			
		return answer;
	}

	//this is only for improving the text file of stats and rosters
	public static String improve(String fileName){
		String answer = "";
		try{
			Scanner scan = new Scanner(new File (fileName));
			while(scan.hasNextLine() ){
					String stats = scan.nextLine();
					String player = stats.replaceAll("\\s", "");
					answer += player + "\n"; 
			}
			
			
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!");
		}
	return answer; 
	}
	
	public static void setCustomDivisional(List<Integer> aT1, List<String> aI1, List<List<Integer>> aS1, List<String> aR1, 
		List<Integer> aT2, List<String> aI2, List<List<Integer>> aS2, List<String> aR2, 
		List<Integer> nT1, List<String> nI1, List<List<Integer>> nS1, List<String> nR1, 
		List<Integer> nT2, List<String> nI2, List<List<Integer>> nS2, List<String> nR2){
		
		round = 1;
		opponentAFCseed1 = aT1; 
		opponentAFCseed1info = aI1;
		opponentAFCseed1Stats = aS1;
		opponentAFCseed1infoStats = aR1;

		opponentAFCseed2 = aT2; 
		opponentAFCseed2info = aI2;
		opponentAFCseed2Stats = aS2;
		opponentAFCseed2infoStats = aR2;

		opponentNFCseed1 = nT1; 
		opponentNFCseed1info = nI1;
		opponentNFCseed1Stats = nS1;
		opponentNFCseed1infoStats = nR1;

		opponentNFCseed2 = nT2; 
		opponentNFCseed2info = nI2;
		opponentNFCseed2Stats = nS2;
		opponentNFCseed2infoStats = nR2; }
		
	
	
	public static void main (String[] args){
		List<Integer> NewEnglandPatriots = Arrays.asList(3, 1, 8, 2, 7, 2, 3, 1, 8, 3, 3, 2, 19, 8, 8, 16, 22, 5, 23, 12, 101);
		List<Integer> KansasCityChiefs = Arrays.asList(2, 4, 3, 7, 15, 15, 13, 7, 30, 15, 13, 11, 20, 16, 17, 12, 4, 23, 24, 28, 67); 
		List<Integer> PittsburghSteelers = Arrays.asList(9, 5, 14, 3, 13, 11, 11, 10, 12, 11, 9, 8, 4, 3, 2, 9, 13, 9, 17, 13, 63); 
		List<Integer> HoustonTexans = Arrays.asList(26, 26, 7, 29, 4, 30, 29, 11, 31, 32, 30, 30, 27, 9, 15, 11, 9, 17, 25, 16, 36);
		List<Integer> OaklandRaiders = Arrays.asList(1, 11, 19, 8, 32, 9, 7, 20, 14, 16, 7, 4, 12, 1, 10, 22, 23, 21, 27, 27, 24);
		List<Integer> MiamiDolphins = Arrays.asList(13, 24, 17, 15, 12, 8, 17, 18, 15, 6, 14, 18, 15, 23, 21, 17, 14, 19, 31, 20, 26);
		
		List<String> NewEnglandPatriotsInfo = Arrays.asList("New England Patriots", " (14-2)", "3rd", "1st", " (1)");
		List<String> KansasCityChiefsInfo = Arrays.asList("Kansas City Chiefs", " (12-4)", "13th", "7th", " (2)");
		List<String> PittsburghSteelersInfo = Arrays.asList("Pittsburgh Steelers", " (11-5)", "11th", "10th", " (3)");
		List<String> HoustonTexansInfo = Arrays.asList("Houston Texans", " (9-7)", "29th", "11th", " (4)");
		List<String> OaklandRaidersInfo = Arrays.asList("Oakland Raiders", " (12-4)", "7th", "20th", " (5)");
		List<String> MiamiDolphinsInfo = Arrays.asList("Miami Dolphins", " (10-6)", "17th", "18th", " (6)");
		
		List<Integer> DallasCowboys = Arrays.asList(10, 3, 24, 1, 14, 3, 5, 5, 3, 5, 2, 3, 2, 11, 3, 18, 19, 8, 10, 11, 66);
		List<Integer> AtlantaFalcons = Arrays.asList(4, 2, 22, 4, 11, 1, 1, 27, 9, 1, 1, 1, 8, 22, 9, 27, 24, 28, 22, 24, 68);
		List<Integer> SeattleSeahawks = Arrays.asList(16, 6, 9, 10, 16, 13, 18, 3, 27, 7, 18, 16, 21, 25, 26, 4, 11, 2, 13, 10, 75);
		List<Integer> GreenBayPackers = Arrays.asList(6, 8, 26, 6, 31, 5, 4, 21, 10, 18, 6, 9, 9, 14, 22, 19, 20, 12, 4, 9, 72);
		List<Integer> NewYorkGiants = Arrays.asList(22, 12, 2, 12, 6, 20, 26, 2, 19, 21, 21, 21, 24, 4, 25, 2, 3, 4, 29, 17, 70); 
		List<Integer> DetroitLions = Arrays.asList(20, 21, 32, 27, 26, 14, 20, 13, 17, 14, 17, 13, 26, 18, 31, 32, 32, 22, 21, 19, 35); 
		
		List<String> DallasCowboysInfo = Arrays.asList("Dallas Cowboys", " (13-3)", "5th", "5th", " (1)");
		List<String> AtlantaFalconsInfo = Arrays.asList("Atlanta Falcons", " (11-5)", "1st", "27th", " (2)");
		List<String> SeattleSeahawksInfo = Arrays.asList("Seattle Seahawks", " (10-5-1)", "18th", "3rd", " (3)");
		List<String> GreenBayPackersInfo = Arrays.asList("Green Bay Packers", " (10-6)", "4th", "21st", " (4)");
		List<String> NewYorkGiantsInfo = Arrays.asList("New York Giants", " (11-5)", "26th", "2nd", " (5)");
		List<String> DetroitLionsInfo = Arrays.asList("Detroit Lions", " (9-7)", "20th", "13th", " (6)");
	
		List<List<Integer>> stats = new ArrayList<List<Integer>>();
		List<List<Integer>> NewEnglandPatriotsStats = new ArrayList<List<Integer>>();
		List<List<Integer>> KansasCityChiefsStats = new ArrayList<List<Integer>>();
		List<List<Integer>> PittsburghSteelersStats = new ArrayList<List<Integer>>();
		List<List<Integer>> HoustonTexansStats = new ArrayList<List<Integer>>();
		List<List<Integer>> OaklandRaidersStats = new ArrayList<List<Integer>>();
		List<List<Integer>> MiamiDolphinsStats = new ArrayList<List<Integer>>();
		
		List<List<Integer>> DallasCowboysStats = new ArrayList<List<Integer>>();
		List<List<Integer>> AtlantaFalconsStats = new ArrayList<List<Integer>>();
		List<List<Integer>> SeattleSeahawksStats = new ArrayList<List<Integer>>();
		List<List<Integer>> GreenBayPackersStats = new ArrayList<List<Integer>>();
		List<List<Integer>> NewYorkGiantsStats = new ArrayList<List<Integer>>();
		List<List<Integer>> DetroitLionsStats = new ArrayList<List<Integer>>();
      
		try{
			Scanner scan = new Scanner(new File ("Stats.txt"));
			while(scan.hasNextLine() ){
					String player = scan.nextLine();
					List<String> playerStats = new ArrayList<String>(Arrays.asList(player.split(",")));
					List<Integer> intStats = new ArrayList<Integer>();
					int elements = playerStats.size(), i = 0;
					while(i < elements){
						intStats.add(Integer.valueOf(playerStats.get(i)));
						i += 1; }
					stats.add(intStats); }
			
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!"); }
		
		int x = 0;
		while (x < stats.size() ){
			List<Integer> player = stats.get(x);
			if (x <= 7){
				NewEnglandPatriotsStats.add(player); }
			if (x >= 8 && x <= 15){
				KansasCityChiefsStats.add(player); }
			if (x >= 16 && x <= 23){
				PittsburghSteelersStats.add(player); }
			if (x >= 24 && x <= 31){
				HoustonTexansStats.add(player); }
			if (x >= 32 && x <= 39){
				OaklandRaidersStats.add(player); }
			if (x >= 40 && x <= 47){
				MiamiDolphinsStats.add(player); }
			
			
			if (x >= 48 && x <= 55){
				DallasCowboysStats.add(player); }
			if (x >= 56 && x <= 63){
				AtlantaFalconsStats.add(player); }
			if (x >= 64 && x <= 71){
				SeattleSeahawksStats.add(player); }
			if (x >= 72 && x <= 79){
				GreenBayPackersStats.add(player); }
			if (x >= 80 && x <= 87){
				NewYorkGiantsStats.add(player); }
			if (x >= 88 && x <= 95){
				DetroitLionsStats.add(player); }
			
			x += 1; }
		
		List<String> players = new ArrayList<String>();
		List<String> NewEnglandPatriotsRoster = new ArrayList<String>();
		List<String> KansasCityChiefsRoster = new ArrayList<String>();
		List<String> PittsburghSteelersRoster = new ArrayList<String>();
		List<String> HoustonTexansRoster = new ArrayList<String>();
		List<String> OaklandRaidersRoster = new ArrayList<String>();
		List<String> MiamiDolphinsRoster = new ArrayList<String>();
		
		List<String> DallasCowboysRoster = new ArrayList<String>();
		List<String> AtlantaFalconsRoster = new ArrayList<String>();
		List<String> SeattleSeahawksRoster = new ArrayList<String>();
		List<String> GreenBayPackersRoster = new ArrayList<String>();
		List<String> NewYorkGiantsRoster = new ArrayList<String>();
		List<String> DetroitLionsRoster = new ArrayList<String>(); 
      
		try{
			Scanner scan = new Scanner(new File ("Rosters.txt"));
			while(scan.hasNextLine() ){
					String playerInfo = scan.nextLine();
					players.add(playerInfo); }
			
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!"); }
		
		int y = 0;
		while (y < players.size() ){
			String playerName = players.get(y);
			if (y <= 7){
				NewEnglandPatriotsRoster.add(playerName); }
			if (y >= 8 && y <= 15){
				KansasCityChiefsRoster.add(playerName); }
			if (y >= 16 && y <= 23){
				PittsburghSteelersRoster.add(playerName); }
			if (y >= 24 && y <= 31){
				HoustonTexansRoster.add(playerName); }
			if (y >= 32 && y <= 39){
				OaklandRaidersRoster.add(playerName); }
			if (y >= 40 && y <= 47){
				MiamiDolphinsRoster.add(playerName); }
			
			
			if (y >= 48 && y <= 55){
				DallasCowboysRoster.add(playerName); }
			if (y >= 56 && y <= 63){
				AtlantaFalconsRoster.add(playerName); }
			if (y >= 64 && y <= 71){
				SeattleSeahawksRoster.add(playerName); }
			if (y >= 72 && y <= 79){
				GreenBayPackersRoster.add(playerName); }
			if (y >= 80 && y <= 87){
				NewYorkGiantsRoster.add(playerName); }
			if (y >= 88 && y <= 95){
				DetroitLionsRoster.add(playerName); }
				
			y += 1;
		}
		
		//these stat grades are only for reference
		//adjustments were made for injuries, current win streak, and experience in postseason
		/** System.out.println(convertRawToScore(NewEnglandPatriots)); //91.25 -> 101.25 (+10)
		System.out.println(convertRawToScore(KansasCityChiefs)); //76.88 -> 66.88 (-10)
		System.out.println(convertRawToScore(PittsburghSteelers)); //73.28 -> 63.28 (-10)
		System.out.println(convertRawToScore(HoustonTexans)); //35.63 
		System.out.println(convertRawToScore(OaklandRaiders)); //64.22 -> 24.22 (-40)
		System.out.println(convertRawToScore(MiamiDolphins)); //56.09 -> 26.09 (-30)
		
		System.out.println(convertRawToScore(DallasCowboys)); //75.78 -> 65.78 (-10)
		System.out.println(convertRawToScore(AtlantaFalcons)); //78.44 -> 68.44 (-10)
		System.out.println(convertRawToScore(SeattleSeahawks)); //65.31 -> 75.31 (+10)
		System.out.println(convertRawToScore(GreenBayPackers)); //62.03 -> 72.03 (+10)
		System.out.println(convertRawToScore(NewYorkGiants)); //60.31 
		System.out.println(convertRawToScore(DetroitLions)); //34.84 */
	
		/** this is using the default playoff tournament; the feature to customize will come later
			AFC 				NFC
		1) Patriots				Cowboys
		2) Chiefs				Falcons
		3) Steelers				Seahawks
		4) Texans				Packers
		5) Raiders				Giants
		6) Dolphins				Lions */
		
		AFCseed1 = NewEnglandPatriots;
		AFCseed1info = NewEnglandPatriotsInfo;
		AFCseed2 = KansasCityChiefs;
		AFCseed2info = KansasCityChiefsInfo;
		AFCseed3 = PittsburghSteelers;
		AFCseed3info = PittsburghSteelersInfo;
		AFCseed4 = HoustonTexans;
		AFCseed4info = HoustonTexansInfo;
		AFCseed5 = OaklandRaiders;
		AFCseed5info = OaklandRaidersInfo;
		AFCseed6 = MiamiDolphins;
		AFCseed6info = MiamiDolphinsInfo;
		
		
		NFCseed1 = DallasCowboys;
		NFCseed1info = DallasCowboysInfo;
		NFCseed2 = AtlantaFalcons;
		NFCseed2info = AtlantaFalconsInfo;
		NFCseed3 = SeattleSeahawks;
		NFCseed3info = SeattleSeahawksInfo;
		NFCseed4 = GreenBayPackers;
		NFCseed4info = GreenBayPackersInfo;
		NFCseed5 = NewYorkGiants;
		NFCseed5info = NewYorkGiantsInfo;
		NFCseed6 = DetroitLions;
		NFCseed6info = DetroitLionsInfo;
	
		
		
		AFCseed1Stats = NewEnglandPatriotsStats;
		AFCseed1infoStats= NewEnglandPatriotsRoster;
		AFCseed2Stats = KansasCityChiefsStats;
		AFCseed2infoStats = KansasCityChiefsRoster;
		AFCseed3Stats = PittsburghSteelersStats;
		AFCseed3infoStats = PittsburghSteelersRoster;
		AFCseed4Stats = HoustonTexansStats;
		AFCseed4infoStats = HoustonTexansRoster;
		AFCseed5Stats = OaklandRaidersStats;
		AFCseed5infoStats = OaklandRaidersRoster;
		AFCseed6Stats = MiamiDolphinsStats;
		AFCseed6infoStats = MiamiDolphinsRoster;
		
		
		NFCseed1Stats = DallasCowboysStats;
		NFCseed1infoStats = DallasCowboysRoster;
		NFCseed2Stats = AtlantaFalconsStats;
		NFCseed2infoStats = AtlantaFalconsRoster;
		NFCseed3Stats = SeattleSeahawksStats;
		NFCseed3infoStats = SeattleSeahawksRoster;
		NFCseed4Stats = GreenBayPackersStats;
		NFCseed4infoStats = GreenBayPackersRoster;
		NFCseed5Stats = NewYorkGiantsStats;
		NFCseed5infoStats = NewYorkGiantsRoster;
		NFCseed6Stats = DetroitLionsStats;
		NFCseed6infoStats = DetroitLionsRoster;
		
		/** 
		System.out.println(NewEnglandPatriotsRoster.toString());
		System.out.println(KansasCityChiefsRoster.toString());
		System.out.println(PittsburghSteelersRoster.toString());
		System.out.println(HoustonTexansRoster.toString());
		System.out.println(OaklandRaidersRoster.toString());
		System.out.println(MiamiDolphinsRoster.toString());
		
		System.out.println(DallasCowboysRoster.toString());
		System.out.println(AtlantaFalconsRoster.toString());
		System.out.println(SeattleSeahawksRoster.toString());
		System.out.println(GreenBayPackersRoster.toString());
		System.out.println(NewYorkGiantsRoster.toString());
		System.out.println(DetroitLionsRoster.toString());
		
		System.out.println(NewEnglandPatriotsStats.toString());
		System.out.println(KansasCityChiefsStats.toString());
		System.out.println(PittsburghSteelersStats.toString());
		System.out.println(HoustonTexansStats.toString());
		System.out.println(OaklandRaidersStats.toString());
		System.out.println(MiamiDolphinsStats.toString());
		
		System.out.println(DallasCowboysStats.toString());
		System.out.println(AtlantaFalconsStats.toString());
		System.out.println(SeattleSeahawksStats.toString());
		System.out.println(GreenBayPackersStats.toString());
		System.out.println(NewYorkGiantsStats.toString());
		System.out.println(DetroitLionsStats.toString());  */
		
		/** setCustomDivisional(HoustonTexans, HoustonTexansInfo, HoustonTexansStats, HoustonTexansRoster, 
			PittsburghSteelers, PittsburghSteelersInfo, PittsburghSteelersStats, PittsburghSteelersRoster,
			GreenBayPackers, GreenBayPackersInfo, GreenBayPackersStats, GreenBayPackersRoster,
			SeattleSeahawks, SeattleSeahawksInfo, SeattleSeahawksStats, SeattleSeahawksRoster); */
			
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		//System.out.println(playoffPicture());
		//System.out.println(simResults(round));
		//System.out.println(playoffPicture());
		//System.out.println(simResults(round));
		//System.out.println(playoffPicture());
		//System.out.println(simResults(round));
		
			
	} 
}
	
