import java.util.*;
import java.io.*;


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

	public static int poss(int margin){
		double chance = Math.random() * 100;
		int answer = 0;
		
		if (margin >= 29 && margin <= 31){
			if (chance < 10){
				answer = 14; }
			else {
				if (chance < 25){
					answer = 10; }
				else{
					answer = 7; }
			}
		}
		
		if (margin >= 25 && margin <= 28){
			if (chance < 5){
				answer = 14; }
			else {
				if (chance < 20){
					answer = 10; }
				else{
					answer = 7; }
			}
		}
		
		if (margin >= 21 && margin <= 24){
			if (chance < 1){
				answer = 14; }
			else {
				if (chance < 10){
					answer = 10; }
				else{
					answer = 7; }
			}
		}
		
		if (margin >= 17 && margin <= 20){
			if (chance < 5){
				answer = 10; }
			else {
				if (chance < 95){
					answer = 7; }
				else{
					answer = 3; }
			}
		}
		
		if (margin >= 13 && margin <= 16){
			if (chance < 1){
				answer = 10; }
				
			else{
				if (chance < 76){
					answer = 7; }
				else{
					if (chance < 96){
						answer = 3; }
					else{
						answer = 0; }
				}
			}
		}
		
		if (margin >= 9 && margin <= 12){
			if (chance < 50){
				answer = 7; }
			else {
				if (chance < 90){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= 5 && margin <= 8){
			if (chance < 40){
				answer = 7; }
			else {
				if (chance < 80){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= 1 && margin <= 4){
			if (chance < 34){
				answer = 7; }
			else {
				if (chance < 67){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= -4 && margin <= -1){
			if (chance < 33){
				answer = 7; }
			else {
				if (chance < 66){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= -8 && margin <= -5){
			if (chance < 20){
				answer = 7; }
			else {
				if (chance < 60){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= -12 && margin <= -9){
			if (chance < 10){
				answer = 7; }
			else {
				if (chance < 50){
					answer = 3; }
				else{
					answer = 0; }
			}
		}
		
		if (margin >= -16 && margin <= -13){
			if (chance < 4){
				answer = 7; }
			else {
				if (chance < 24){
					answer = 3; }
				else{
					if (chance < 99){
						answer = 0; }
					else{
						answer = -3; }
				}
			}
		}
		
		if (margin >= -20 && margin <= -17){
			if (chance < 5){
				answer = 3; }
			else {
				if (chance < 95){
					answer = 0; }
				else{
					answer = -3; }
			}
		}
		
		if (margin >= -24 && margin <= -21){
			if (chance < 90){
				answer = 0; }
			else {
				if (chance < 99){
					answer = -3; }
				else{
					answer = -7; }
			}
		}
		
		if (margin >= -28 && margin <= -25){
			if (chance < 80){
				answer = 0; }
			else {
				if (chance < 95){
					answer = -3; }
				else{
					answer = -7; }
			}
		}
		
		if (margin >= -31 && margin <= -29){
			if (chance < 75){
				answer = 0; }
			else {
				if (chance < 90){
					answer = -3; }
				else{
					answer = -7; }
			}
		}
		
		return answer;
		
	}
	
	public static String simScore(List<Integer> teamA, List<Integer> teamB, int win){
		int aFinal = 0, bFinal = 0, winner, loser;
		String answer = "";
		
		int aScoreMar = teamB.get(7) - teamA.get(6);
		int aPointDiffMar = teamB.get(1) - teamA.get(1);
		int aTurnDiffMar = teamB.get(0) - teamA.get(0);
		
		int bEffMar = teamA.get(15) - teamB.get(10);
		int bPassMar = teamA.get(16) - teamB.get(11);
		int bRushMar = teamA.get(17) - teamB.get(12);
		int bScoreMar = teamA.get(7) - teamB.get(6);
		int bPointDiffMar = teamA.get(1) - teamB.get(1);
		int bTurnDiffMar = teamA.get(0) - teamB.get(0);
		
		aFinal += poss(aScoreMar) + poss(aPointDiffMar) + poss(aTurnDiffMar);
		bFinal += poss(bRushMar) + poss(bScoreMar) + poss(bPointDiffMar) + poss(bTurnDiffMar);
		
		if (win == 0){
			aFinal += 7;
			if (aFinal == bFinal){
				aFinal += 3; }
		}
		
		else{
			bFinal += 7; 
			if (bFinal == aFinal){
				bFinal += 3; }
		}
		
		if (aFinal < 3){
			aFinal = 0; }
			
		if (bFinal < 3){
			bFinal = 0; }
			
		if (aFinal > bFinal){
			winner = aFinal;
			loser = bFinal; }
		else{
			winner = bFinal;
			loser = aFinal; }
		
		answer += winner + "-" + loser;
		return answer;
		
	}

	public static String qbStats(List<Integer> stats, List<Integer> opp){

		String answer = ""; 
	
		//base stats
		double yds = stats.get(3) / (stats.get(2) + 0.0);
		double td = stats.get(4) / (stats.get(2) + 0.0);
		double comp = stats.get(5) / (stats.get(2) + 0.0);
		double att = stats.get(6) / (stats.get(2) + 0.0);
		double inter = stats.get(7) / (stats.get(2) + 0.0);
		int rating = stats.get(8);
		int finalInt = 0;
	
		//adjustment from madden rating 
		if (rating >= 87){
			yds *= 1.25;
			td *= 1.25;
			comp *= 1.25;
			att *= 1.25;
			inter *= 0.75; }
		
		if (rating >= 79 && rating <= 86){
			yds *= 1.1;
			td *= 1.1;
			comp *= 1.1;
			att *= 1.1;
			inter *= 0.9; }
	
		if (rating <= 75){
			yds *= 0.75;
			td *= 0.75;
			comp *= 0.75;
			att *= 0.75;
			inter *= 1.25; }
	
		//vs opposing def
		int def = opp.get(7);
		if (def >= 1 && def <= 8){
			yds *= 0.75;
			td *= 0.75;
			comp *= 0.75;
			att *= 0.75;
			inter *= 1.25; }
		
		if (def >= 17 && def <= 24){
			yds *= 1.1;
			td *= 1.1;
			comp *= 1.1;
			att *= 1.1;
			inter *= 0.9; }
		
		if (def >= 25 && def <= 32){
			yds *= 1.25;
			td *= 1.25;
			comp *= 1.25;
			att *= 1.25;
			inter *= 0.75; }
		
		//randomizer
		double chance = Math.random() * 100; 
		if (chance < 20){
			yds *= 0.8; 
			td *= 0.8;
			comp *= 0.8;
			att *= 0.8;
			inter *= 1.2; }
		
		if (chance < 40 && chance > 20){
			yds *= 0.9; 
			td *= 0.9;
			comp *= 0.9;
			att *= 0.9;
			inter *= 1.1; }
		
		if (chance < 60 && chance > 40){
			yds *= 1.1; 
			td *= 1.1;
			comp *= 1.1;
			att *= 1.1;
			inter *= 0.9; }
		
		if (chance < 80 && chance > 60){
			yds *= 1.2; 
			td *= 1.2;
			comp *= 1.2;
			att *= 1.2;
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
		int finalComp = (int) Math.round(comp);
		int finalAtt = (int) Math.round(att);
	
		
		answer += finalYds + " Yards, " + finalTd + " Touchdowns, " + finalComp + " Completions, " + finalAtt + " Attempts, " + 
			finalInt + " Interceptions";
		
		return answer;
		
		}
		
	public static String rbStats(List<Integer> stats, List<Integer> opp){

		String answer = ""; 
	
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
	
		
		answer += finalYds + " Total Yards, " + finalTd + " Total Touchdowns, " + finalCar + " Carries, " + 
			finalFum + " Fumbles";
		
		return answer;
		
	}
	
	public static String wrteStats(List<Integer> stats, List<Integer> opp){

		String answer = ""; 
	
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
	
		
		answer += finalYds + " Total Yards, " + finalTd + " Total Touchdowns, " + finalRec + " Receptions";
		
		return answer;
		
	}
	
	public static String lbStats(List<Integer> stats, List<Integer> opp){

		String answer = "";
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
		
		answer += adjTak + " Tackles";
		if (adjSak > 0){
			answer += ", " + adjSak + " Sacks"; }
			
		if (adjFF > 0){
			answer += ", " + adjFF + " Forced Fumbles"; }
			
		if (adjPick > 0){
			answer += ", " + adjPick + " Interceptions"; }
			
		if (adjPD > 0){
			answer += ", " + adjPD + " Passes Defended"; }
		
		return answer;
	}
	
	public static String dlStats(List<Integer> stats, List<Integer> opp){
		String answer = "";
		
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
		
		answer += adjTak + " Tackles";
		if (adjSak > 0){
			answer += ", " + adjSak + " Sacks"; }
			
		if (adjFF > 0){
			answer += ", " + adjFF + " Forced Fumbles"; }
		
		return answer;
	}
	
	public static String dbStats(List<Integer> stats, List<Integer> opp){
		String answer = "";
		
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
		
		answer += adjTak + " Tackles";
		if (adjPick > 0){
			answer += ", " + adjPick + " Interceptions"; }
		
		if (adjPD > 0){
			answer += ", " + adjPD + " Passes Defended"; }
			
		if (adjFF > 0){
			answer += ", " + adjFF + " Forced Fumbles"; }
		
		return answer;
	}
	
	public static String teamStats(List<List<Integer>> teamStats, List<String> teamPlayers, List<Integer> opp){
		String answer = "", qb = "", rb = "", wr = "", flex = "", d1 = "", d2 = "", d3 = "", d4 = "";
		List<Integer> QB = teamStats.get(0), RB = teamStats.get(1), WR = teamStats.get(2), FLEX = teamStats.get(3), 
			D1 = teamStats.get(4), D2 = teamStats.get(5), D3 = teamStats.get(6), D4 = teamStats.get(7);
			
		qb += teamPlayers.get(0) + ": " + qbStats(QB, opp) + "\n";
		rb += teamPlayers.get(1) + ": " + rbStats(RB, opp) + "\n";
		wr += teamPlayers.get(2) + ": " + wrteStats(WR, opp) + "\n";
		flex += teamPlayers.get(3) + ": "; 
		if (FLEX.get(1) == 1){
			flex += rbStats(FLEX, opp) + "\n"; }
		else{
			flex += wrteStats(FLEX, opp) + "\n"; }
			
		d1 += teamPlayers.get(4) + ": ";
		d2 += teamPlayers.get(5) + ": ";
		d3 += teamPlayers.get(6) + ": ";
		d4 += teamPlayers.get(7) + ": ";
		
		if (D1.get(1) == 3){
			d1 += lbStats(D1, opp) + "\n"; }
		else{
			if (D1.get(1) == 4){
				d1 += dlStats(D1, opp) + "\n"; }
			else{
				d1 += dbStats(D1, opp) + "\n"; }
			
		}
		
		if (D2.get(1) == 3){
			d1 += lbStats(D2, opp) + "\n"; }
		else{
			if (D2.get(1) == 4){
				d2 += dlStats(D2, opp) + "\n"; }
			else{
				d2 += dbStats(D2, opp) + "\n"; }
			
		}
		
		if (D3.get(1) == 3){
			d3 += lbStats(D3, opp) + "\n"; }
		else{
			if (D1.get(1) == 4){
				d3 += dlStats(D3, opp) + "\n"; }
			else{
				d3 += dbStats(D3, opp) + "\n"; }
			
		}
		
		if (D4.get(1) == 3){
			d4 += lbStats(D4, opp) + "\n"; }
		else{
			if (D4.get(1) == 4){
				d4 += dlStats(D4, opp) + "\n"; }
			else{
				d4 += dbStats(D4, opp) + "\n"; }
			
		}
		
		answer += qb + rb + wr + flex + d1 + d2 + d3 + d4 + "\n\n";
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
		String answer = "Results: \n";
		if (round == 0){
			simWildCardRound();
			
			String AFC3vs6 = AFCseed3info.get(0) + AFCseed3info.get(4) + 
				" vs. " + AFCseed6info.get(0) + AFCseed6info.get(4) + "\n Winner: ";
			if (opponentAFCseed2.equals(AFCseed3)){
				AFC3vs6 += AFCseed3info.get(0) + " ";
				AFC3vs6 += simScore(AFCseed3, AFCseed6, 0) + "\n\n"; }
			else{
				AFC3vs6 += AFCseed6info.get(0) + " "; 
				AFC3vs6 += simScore(AFCseed3, AFCseed6, 1) + "\n\n"; }
			AFC3vs6 += AFCseed3info.get(0) + ": \n" + teamStats(AFCseed3Stats, AFCseed3infoStats, AFCseed6);
			AFC3vs6 += AFCseed6info.get(0) + ": \n" + teamStats(AFCseed6Stats, AFCseed6infoStats, AFCseed3);
				
			String AFC4vs5 =  AFCseed4info.get(0) + AFCseed4info.get(4) +
				" vs. " + AFCseed5info.get(0) + AFCseed5info.get(4) + "\n Winner: ";	
			if (opponentAFCseed1.equals(AFCseed4) || opponentAFCseed2.equals(AFCseed4)){
				AFC4vs5 += AFCseed4info.get(0) + " "; 
				AFC4vs5 += simScore(AFCseed4, AFCseed5, 0) + "\n\n"; }
			else{
				AFC4vs5 += AFCseed5info.get(0) + " "; 
				AFC4vs5 += simScore(AFCseed4, AFCseed5, 1) + "\n\n"; }
			AFC4vs5 += AFCseed4info.get(0) + ": \n" + teamStats(AFCseed4Stats, AFCseed4infoStats, AFCseed5);
			AFC4vs5 += AFCseed5info.get(0) + ": \n" + teamStats(AFCseed5Stats, AFCseed5infoStats, AFCseed4);
				
			String NFC3vs6 = NFCseed3info.get(0) + NFCseed3info.get(4) + 
				" vs. " + NFCseed6info.get(0) + NFCseed6info.get(4) + "\n Winner: ";
			if (opponentNFCseed2.equals(NFCseed3)){
				NFC3vs6 += NFCseed3info.get(0) + " ";
				NFC3vs6 += simScore(NFCseed3, NFCseed6, 0) + "\n\n"; }
			else{
				NFC3vs6 += NFCseed6info.get(0) + " ";
				NFC3vs6 += simScore(NFCseed3, NFCseed6, 1) + "\n\n"; }
			NFC3vs6 += NFCseed3info.get(0) + ": \n" + teamStats(NFCseed3Stats, NFCseed3infoStats, NFCseed6);
			NFC3vs6 += NFCseed6info.get(0) + ": \n" + teamStats(NFCseed6Stats, NFCseed6infoStats, NFCseed3);
			
			String NFC4vs5 = NFCseed4info.get(0) + NFCseed4info.get(4) +
				" vs. " + NFCseed5info.get(0) + NFCseed5info.get(4) + "\n Winner: ";	
			if (opponentNFCseed1.equals(NFCseed4) || opponentNFCseed2.equals(NFCseed4)){
				NFC4vs5 += NFCseed4info.get(0) + " "; 
				NFC4vs5 += simScore(NFCseed4, NFCseed5, 0) + "\n\n"; }
			else{
				NFC4vs5 += NFCseed5info.get(0) + " "; 
				NFC4vs5 += simScore(NFCseed4, NFCseed5, 1) + "\n\n"; }
			NFC4vs5 += NFCseed4info.get(0) + ": \n" + teamStats(NFCseed4Stats, NFCseed4infoStats, NFCseed5);
			NFC4vs5 += NFCseed5info.get(0) + ": \n" + teamStats(NFCseed5Stats, NFCseed5infoStats, NFCseed4);
				
			answer += AFC3vs6 + AFC4vs5 + NFC3vs6 + NFC4vs5;
		}
		
		if (round == 1){
			simDivisionalRound();
			
			String AFC1 = AFCseed1info.get(0) + AFCseed1info.get(4) +
				" vs. " + opponentAFCseed1info.get(0) + opponentAFCseed1info.get(4) + "\n Winner: ";
			if (AFCchampionship1.equals(AFCseed1)){
				AFC1 += AFCseed1info.get(0) + " "; 
				AFC1 += simScore(AFCseed1, opponentAFCseed1, 0) + "\n\n"; }
			else{
				AFC1 += opponentAFCseed1info.get(0) + " "; 
				AFC1 += simScore(AFCseed1, opponentAFCseed1, 1) + "\n\n"; }
			AFC1 += AFCseed1info.get(0) + ": \n" + teamStats(AFCseed1Stats, AFCseed1infoStats, opponentAFCseed1);
			AFC1 += opponentAFCseed1info.get(0) + ": \n" + teamStats(opponentAFCseed1Stats, opponentAFCseed1infoStats, AFCseed1);
				
			String AFC2 = AFCseed2info.get(0) + AFCseed2info.get(4) +
				" vs. " + opponentAFCseed2info.get(0) + opponentAFCseed2info.get(4) + "\n Winner: ";
			if (AFCchampionship2.equals(AFCseed2)){
				AFC2 += AFCseed2info.get(0) + " "; 
				AFC2 += simScore(AFCseed2, opponentAFCseed2, 0) + "\n\n"; }
			else{
				AFC2 += opponentAFCseed2info.get(0) + " "; 
				AFC2 += simScore(AFCseed2, opponentAFCseed2, 1) + "\n\n"; }
			AFC2 += AFCseed2info.get(0) + ": \n" + teamStats(AFCseed2Stats, AFCseed2infoStats, opponentAFCseed2);
			AFC2 += opponentAFCseed2info.get(0) + ": \n" + teamStats(opponentAFCseed2Stats, opponentAFCseed2infoStats, AFCseed2);
				
			String NFC1 = NFCseed1info.get(0) + NFCseed1info.get(4) +
				" vs. " + opponentNFCseed1info.get(0) + opponentNFCseed1info.get(4) + "\n Winner: ";
			if (NFCchampionship1.equals(NFCseed1)){
				NFC1 += NFCseed1info.get(0) + " "; 
				NFC1 += simScore(NFCseed1, opponentNFCseed1, 0) + "\n\n"; }
			else{
				NFC1 += opponentNFCseed1info.get(0) + " ";
				NFC1 += simScore(NFCseed1, opponentNFCseed1, 1) + "\n\n"; }
			NFC1 += NFCseed1info.get(0) + ": \n" + teamStats(NFCseed1Stats, NFCseed1infoStats, opponentNFCseed1);
			NFC1 += opponentNFCseed1info.get(0) + ": \n" + teamStats(opponentNFCseed1Stats, opponentNFCseed1infoStats, NFCseed1);	
			
			String NFC2 = NFCseed2info.get(0) + NFCseed2info.get(4) +
				" vs. " + opponentNFCseed2info.get(0) + opponentNFCseed2info.get(4) + "\n Winner: ";
			if (NFCchampionship2.equals(NFCseed2)){
				NFC2 += NFCseed2info.get(0) + " "; 
				NFC2 += simScore(NFCseed2, opponentNFCseed2, 0) + "\n\n"; }
			else{
				NFC2 += opponentNFCseed2info.get(0) + " ";
				NFC2 += simScore(NFCseed2, opponentNFCseed2, 1) + "\n\n"; }
			NFC2 += NFCseed2info.get(0) + ": \n" + teamStats(NFCseed2Stats, NFCseed2infoStats, opponentNFCseed2);
			NFC2 += opponentNFCseed2info.get(0) + ": \n" + teamStats(opponentNFCseed2Stats, opponentNFCseed2infoStats, NFCseed2);
				
			answer += AFC1 + AFC2 + NFC1 + NFC2;
		}
		
		
		if (round == 2){
			simConferenceChampionships();
			
			String AFCchampionship = AFCchampionship1info.get(0) + AFCchampionship1info.get(4) +
				" vs. " + AFCchampionship2info.get(0) + AFCchampionship2info.get(4) + "\n Winner: ";
			if (AFCchampion.equals(AFCchampionship1)){
				AFCchampionship += AFCchampionship1info.get(0) + " "; 
				AFCchampionship += simScore(AFCchampionship1, AFCchampionship2, 0) + "\n\n"; }
			else{
				AFCchampionship += AFCchampionship2info.get(0) + " ";
				AFCchampionship += simScore(AFCchampionship1, AFCchampionship2, 1) + "\n\n"; }
			AFCchampionship += AFCchampionship1info.get(0) + ": \n" + teamStats(AFCchampionship1Stats, AFCchampionship1infoStats, AFCchampionship2);
			AFCchampionship += AFCchampionship2info.get(0) + ": \n" + teamStats(AFCchampionship2Stats, AFCchampionship2infoStats, AFCchampionship1);
				
			String NFCchampionship = NFCchampionship1info.get(0) + NFCchampionship1info.get(4) +
				" vs. " + NFCchampionship2info.get(0) + NFCchampionship2info.get(4) + "\n Winner: ";
			if (NFCchampion.equals(NFCchampionship1)){
				NFCchampionship += NFCchampionship1info.get(0) + " ";
				NFCchampionship += simScore(NFCchampionship1, NFCchampionship2, 0) + "\n\n"; }
			else{
				NFCchampionship += NFCchampionship2info.get(0) + " "; 
				NFCchampionship += simScore(NFCchampionship1, NFCchampionship2, 1) + "\n\n";}
			NFCchampionship += NFCchampionship1info.get(0) + ": \n" + teamStats(NFCchampionship1Stats, NFCchampionship1infoStats, NFCchampionship2);
			NFCchampionship += NFCchampionship2info.get(0) + ": \n" + teamStats(NFCchampionship2Stats, NFCchampionship2infoStats, NFCchampionship1);
			
			answer += AFCchampionship + NFCchampionship;
		}
		
		if (round == 3){
			simSuperBowl();
			
			String superBowl = AFCchampionInfo.get(0) + AFCchampionInfo.get(4) +
				" vs. " + NFCchampionInfo.get(0) + NFCchampionInfo.get(4) + "\n Winner: ";
			if (AFCchampion.equals(SuperBowlChampion)){
				superBowl += AFCchampionInfo.get(0) + " "; 
				superBowl += simScore(AFCchampion, NFCchampion, 0) + "\n\n";
				superBowl += AFCchampionInfo.get(0) + ": \n" + teamStats(AFCchampionStats, AFCchampionInfoStats, NFCchampion);
				superBowl += NFCchampionInfo.get(0) + ": \n" + teamStats(NFCchampionStats, NFCchampionInfoStats, AFCchampion);
				superBowl += "SUPER BOWL CHAMPS: The " + AFCchampionInfo.get(0) + "!";}
			else{
				superBowl += NFCchampionInfo.get(0) + " ";
				superBowl += simScore(AFCchampion, NFCchampion, 1) + "\n\n";
				superBowl += AFCchampionInfo.get(0) + ": \n" + teamStats(AFCchampionStats, AFCchampionInfoStats, NFCchampion);
				superBowl += NFCchampionInfo.get(0) + ": \n" + teamStats(NFCchampionStats, NFCchampionInfoStats, AFCchampion);
				superBowl += "SUPER BOWL CHAMPS: The " + NFCchampionInfo.get(0) + "!";}
				
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
			
			x += 1;
		}
		
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
		
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
			
	} 
}
	
