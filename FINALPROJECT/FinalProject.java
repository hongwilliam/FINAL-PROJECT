import java.util.*;
import java.io.File;

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
			opponentAFCseed2info = AFCseed3info;}
		else{
			opponentAFCseed1 = AFCseed6; 
			opponentAFCseed1info = AFCseed6info;
			did3winAFC = 1;} 
			
		if (determineWinner(AFCseed4, AFCseed5) == 0){
			if (did3winAFC == 0){
				opponentAFCseed1 = AFCseed4; 
				opponentAFCseed1info = AFCseed4info; }
			else{
				opponentAFCseed2 = AFCseed4; 
				opponentAFCseed2info = AFCseed4info; } }	
		else{
			if (did3winAFC == 0){
				opponentAFCseed1 = AFCseed5; 
				opponentAFCseed1info = AFCseed5info; }
			else{
				opponentAFCseed2 = AFCseed5; 
				opponentAFCseed2info = AFCseed5info; } }
		
		int did3winNFC = 0; //will be 0 if 3 wins, 1 if 6 wins
		if (determineWinner(NFCseed3, NFCseed6) == 0){
			opponentNFCseed2 = NFCseed3; 
			opponentNFCseed2info = NFCseed3info; }
		else{
			opponentNFCseed1 = NFCseed6; 
			opponentNFCseed1info = NFCseed6info;
			did3winNFC = 1;} 
			
		if (determineWinner(NFCseed4, NFCseed5) == 0){
			if (did3winNFC == 0){
				opponentNFCseed1 = NFCseed4; 
				opponentNFCseed1info = NFCseed4info; }
			else{
				opponentNFCseed2 = NFCseed4;
				opponentNFCseed2info= NFCseed4info; } }	
		else{
			if (did3winNFC == 0){
				opponentNFCseed1 = NFCseed5;
				opponentNFCseed1info = NFCseed5info; }
			else{
				opponentNFCseed2 = NFCseed5; 
				opponentNFCseed2info = NFCseed5info; } }
				
		round += 1;
	}
	
	public static void simDivisionalRound() {
		if (determineWinner(AFCseed1, opponentAFCseed1) == 0){
			AFCchampionship1 = AFCseed1; 
			AFCchampionship1info = AFCseed1info; }
		else{
			AFCchampionship1 = opponentAFCseed1;
			AFCchampionship1info = opponentAFCseed1info; } 
			
		if (determineWinner(AFCseed2, opponentAFCseed2) == 0){
			AFCchampionship2 = AFCseed2; 
			AFCchampionship2info = AFCseed2info; }
		else{
			AFCchampionship2 = opponentAFCseed2; 
			AFCchampionship2info = opponentAFCseed2info; } 
			
		if (determineWinner(NFCseed1, opponentNFCseed1) == 0){
			NFCchampionship1 = NFCseed1;
			NFCchampionship1info = NFCseed1info; }
		else{
			NFCchampionship1 = opponentNFCseed1;
			NFCchampionship1info = opponentNFCseed1info; } 
			
		if (determineWinner(NFCseed2, opponentNFCseed2) == 0){
			NFCchampionship2 = NFCseed2;
			NFCchampionship2info = NFCseed2info; }
		else{
			NFCchampionship2 = opponentNFCseed2; 
			NFCchampionship2info = opponentNFCseed2info; } 
		
		round += 1;
	}
	
	public static void simConferenceChampionships(){
		if (determineWinner(AFCchampionship1, AFCchampionship2) == 0){
			AFCchampion = AFCchampionship1;
			AFCchampionInfo = AFCchampionship1info; }
		else{
			AFCchampion = AFCchampionship2; 
			AFCchampionInfo = AFCchampionship2info; }
			
		if (determineWinner(NFCchampionship1, NFCchampionship2) == 0){
			NFCchampion = NFCchampionship1;
			NFCchampionInfo = NFCchampionship1info;}
		else{
			NFCchampion = NFCchampionship2;
			NFCchampionInfo = NFCchampionship2info; }

		round += 1;
	}
	
	public static void simSuperBowl(){
		if (determineWinner(AFCchampion, NFCchampion) == 0){
			SuperBowlChampion = AFCchampion; 
			SuperBowlChampionInfo = AFCchampionInfo; }
		else{
			SuperBowlChampion = NFCchampion; 
			SuperBowlChampionInfo = NFCchampionInfo; }
		
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
			aFinal = 3; }
			
		if (bFinal < 3){
			bFinal = 3; }
			
		if (aFinal > bFinal){
			winner = aFinal;
			loser = bFinal; }
		else{
			winner = bFinal;
			loser = aFinal; }
		
		answer += winner + "-" + loser;
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
				
			String AFC4vs5 =  AFCseed4info.get(0) + AFCseed4info.get(4) +
				" vs. " + AFCseed5info.get(0) + AFCseed5info.get(4) + "\n Winner: ";	
			if (opponentAFCseed1.equals(AFCseed4) || opponentAFCseed2.equals(AFCseed4)){
				AFC4vs5 += AFCseed4info.get(0) + " "; 
				AFC4vs5 += simScore(AFCseed4, AFCseed5, 0) + "\n\n"; }
			else{
				AFC4vs5 += AFCseed5info.get(0) + " "; 
				AFC4vs5 += simScore(AFCseed4, AFCseed5, 1) + "\n\n"; }
				
			String NFC3vs6 = NFCseed3info.get(0) + NFCseed3info.get(4) + 
				" vs. " + NFCseed6info.get(0) + NFCseed6info.get(4) + "\n Winner: ";
			if (opponentNFCseed2.equals(NFCseed3)){
				NFC3vs6 += NFCseed3info.get(0) + " ";
				NFC3vs6 += simScore(NFCseed3, NFCseed6, 0) + "\n\n"; }
			else{
				NFC3vs6 += NFCseed6info.get(0) + " ";
				NFC3vs6 += simScore(NFCseed3, NFCseed6, 1) + "\n\n"; }
				
			String NFC4vs5 = NFCseed4info.get(0) + NFCseed4info.get(4) +
				" vs. " + NFCseed5info.get(0) + NFCseed5info.get(4) + "\n Winner: ";	
			if (opponentNFCseed1.equals(NFCseed4) || opponentNFCseed2.equals(NFCseed4)){
				NFC4vs5 += NFCseed4info.get(0) + " "; 
				NFC4vs5 += simScore(NFCseed4, NFCseed5, 0) + "\n\n"; }
			else{
				NFC4vs5 += NFCseed5info.get(0) + " "; 
				NFC4vs5 += simScore(NFCseed4, NFCseed5, 1) + "\n\n"; }
				
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
				
			String AFC2 = AFCseed2info.get(0) + AFCseed2info.get(4) +
				" vs. " + opponentAFCseed2info.get(0) + opponentAFCseed2info.get(4) + "\n Winner: ";
			if (AFCchampionship2.equals(AFCseed2)){
				AFC2 += AFCseed2info.get(0) + " "; 
				AFC2 += simScore(AFCseed2, opponentAFCseed2, 0) + "\n\n"; }
			else{
				AFC2 += opponentAFCseed2info.get(0) + " "; 
				AFC2 += simScore(AFCseed2, opponentAFCseed2, 1) + "\n\n"; }
				
			String NFC1 = NFCseed1info.get(0) + NFCseed1info.get(4) +
				" vs. " + opponentNFCseed1info.get(0) + opponentNFCseed1info.get(4) + "\n Winner: ";
			if (NFCchampionship1.equals(NFCseed1)){
				NFC1 += NFCseed1info.get(0) + " "; 
				NFC1 += simScore(NFCseed1, opponentNFCseed1, 0) + "\n\n"; }
			else{
				NFC1 += opponentNFCseed1info.get(0) + " ";
				NFC1 += simScore(NFCseed1, opponentNFCseed1, 1) + "\n\n"; }
				
			String NFC2 = NFCseed2info.get(0) + NFCseed2info.get(4) +
				" vs. " + opponentNFCseed2info.get(0) + opponentNFCseed2info.get(4) + "\n Winner: ";
			if (NFCchampionship2.equals(NFCseed2)){
				NFC2 += NFCseed2info.get(0) + " "; 
				NFC2 += simScore(NFCseed2, opponentNFCseed2, 0) + "\n\n"; }
			else{
				NFC2 += opponentNFCseed2info.get(0) + " ";
				NFC2 += simScore(NFCseed2, opponentNFCseed2, 1) + "\n\n"; }
				
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
				
			String NFCchampionship = NFCchampionship1info.get(0) + NFCchampionship1info.get(4) +
				" vs. " + NFCchampionship2info.get(0) + NFCchampionship2info.get(4) + "\n Winner: ";
			if (NFCchampion.equals(NFCchampionship1)){
				NFCchampionship += NFCchampionship1info.get(0) + " ";
				NFCchampionship += simScore(NFCchampionship1, NFCchampionship2, 0) + "\n\n"; }
			else{
				NFCchampionship += NFCchampionship2info.get(0) + " "; 
				NFCchampionship += simScore(NFCchampionship1, NFCchampionship2, 1) + "\n\n";}
				
			answer += AFCchampionship + NFCchampionship;
		}
		
		if (round == 3){
			simSuperBowl();
			
			String superBowl = AFCchampionInfo.get(0) + AFCchampionInfo.get(4) +
				" vs. " + NFCchampionInfo.get(0) + NFCchampionInfo.get(4) + "\n Winner: ";
			if (AFCchampion.equals(SuperBowlChampion)){
				superBowl += AFCchampionInfo.get(0) + " "; 
				superBowl += simScore(AFCchampion, NFCchampion, 0) + "\n\n";
				superBowl += "SUPER BOWL CHAMPS: The " + AFCchampionInfo.get(0) + "!";}
			else{
				superBowl += NFCchampionInfo.get(0) + " ";
				superBowl += simScore(AFCchampion, NFCchampion, 1) + "\n\n";
				superBowl += "SUPER BOWL CHAMPS: The " + NFCchampionInfo.get(0) + "!";}
				
			answer += superBowl;
		}
			
		return answer;
	}
	
	public static void main (String[] args){
		List<Integer> NewEnglandPatriots = Arrays.asList(3, 1, 8, 2, 7, 2, 3, 1, 8, 3, 3, 2, 19, 8, 8, 16, 22, 5, 23, 12, 97);
		List<Integer> KansasCityChiefs = Arrays.asList(2, 4, 3, 7, 15, 15, 13, 7, 30, 15, 13, 11, 20, 16, 17, 12, 4, 23, 24, 28, 72); 
		List<Integer> PittsburghSteelers = Arrays.asList(9, 5, 14, 3, 13, 11, 11, 10, 12, 11, 9, 8, 4, 3, 2, 9, 13, 9, 17, 13, 68); 
		List<Integer> HoustonTexans = Arrays.asList(26, 26, 7, 29, 4, 30, 29, 11, 31, 32, 30, 30, 27, 9, 15, 11, 9, 17, 25, 16, 36);
		List<Integer> OaklandRaiders = Arrays.asList(1, 11, 19, 8, 32, 9, 7, 20, 14, 16, 7, 4, 12, 1, 10, 22, 23, 21, 27, 27, 34);
		List<Integer> MiamiDolphins = Arrays.asList(13, 24, 17, 15, 12, 8, 17, 18, 15, 6, 14, 18, 15, 23, 21, 17, 14, 19, 31, 20, 56);
		
		List<String> NewEnglandPatriotsInfo = Arrays.asList("New England Patriots", " (14-2)", "3rd", "1st", " (1)");
		List<String> KansasCityChiefsInfo = Arrays.asList("Kansas City Chiefs", " (12-4)", "13th", "7th", " (2)");
		List<String> PittsburghSteelersInfo = Arrays.asList("Pittsburgh Steelers", " (11-5)", "11th", "10th", " (3)");
		List<String> HoustonTexansInfo = Arrays.asList("Houston Texans", " (9-7)", "29th", "11th", " (4)");
		List<String> OaklandRaidersInfo = Arrays.asList("Oakland Raiders", " (12-4)", "7th", "20th", " (5)");
		List<String> MiamiDolphinsInfo = Arrays.asList("Miami Dolphins", " (10-6)", "17th", "18th", " (6)");
		
		List<Integer> DallasCowboys = Arrays.asList(10, 3, 24, 1, 14, 3, 5, 5, 3, 5, 2, 3, 2, 11, 3, 18, 19, 8, 10, 11, 71);
		List<Integer> AtlantaFalcons = Arrays.asList(4, 2, 22, 4, 11, 1, 1, 27, 9, 1, 1, 1, 8, 22, 9, 27, 24, 28, 22, 24, 73);
		List<Integer> SeattleSeahawks = Arrays.asList(16, 6, 9, 10, 16, 13, 18, 3, 27, 7, 18, 16, 21, 25, 26, 4, 11, 2, 13, 10, 65);
		List<Integer> GreenBayPackers = Arrays.asList(6, 8, 26, 6, 31, 5, 4, 21, 10, 18, 6, 9, 9, 14, 22, 19, 20, 12, 4, 9, 62);
		List<Integer> NewYorkGiants = Arrays.asList(22, 12, 2, 12, 6, 20, 26, 2, 19, 21, 21, 21, 24, 4, 25, 2, 3, 4, 29, 17, 70); 
		List<Integer> DetroitLions = Arrays.asList(20, 21, 32, 27, 26, 14, 20, 13, 17, 14, 17, 13, 26, 18, 31, 32, 32, 22, 21, 19, 35); 
		
		List<String> DallasCowboysInfo = Arrays.asList("Dallas Cowboys", " (13-3)", "5th", "5th", " (1)");
		List<String> AtlantaFalconsInfo = Arrays.asList("Atlanta Falcons", " (11-5)", "1st", "27th", " (2)");
		List<String> SeattleSeahawksInfo = Arrays.asList("Seattle Seahawks", " (10-5-1)", "18th", "3rd", " (3)");
		List<String> GreenBayPackersInfo = Arrays.asList("Green Bay Packers", " (10-6)", "4th", "21st", " (4)");
		List<String> NewYorkGiantsInfo = Arrays.asList("New York Giants", " (11-5)", "26th", "2nd", " (5)");
		List<String> DetroitLionsInfo = Arrays.asList("Detroit Lions", " (9-7)", "20th", "13th", " (6)");
		
		/** System.out.println(convertRawToScore(NewEnglandPatriots)); //91.25 -> 96.25 (+5)
		System.out.println(convertRawToScore(KansasCityChiefs)); //76.88 -> 71.88 (-5)
		System.out.println(convertRawToScore(PittsburghSteelers)); //73.28 -> 68.28 (-5)
		System.out.println(convertRawToScore(HoustonTexans)); //35.63 
		System.out.println(convertRawToScore(OaklandRaiders)); //64.22 -> 34.22 (-30)
		System.out.println(convertRawToScore(MiamiDolphins)); //56.09
		
		System.out.println(convertRawToScore(DallasCowboys)); //75.78 -> 70.78 (-5)
		System.out.println(convertRawToScore(AtlantaFalcons)); //78.44 -> 73.44 (-5)
		System.out.println(convertRawToScore(SeattleSeahawks)); //65.31
		System.out.println(convertRawToScore(GreenBayPackers)); //62.03
		System.out.println(convertRawToScore(NewYorkGiants)); //60.31 -> 70.31 (+10)
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
		AFCseed1info= NewEnglandPatriotsInfo;
		AFCseed2 = KansasCityChiefs;
		AFCseed2info= KansasCityChiefsInfo;
		AFCseed3 = PittsburghSteelers;
		AFCseed3info= PittsburghSteelersInfo;
		AFCseed4 = HoustonTexans;
		AFCseed4info= HoustonTexansInfo;
		AFCseed5 = OaklandRaiders;
		AFCseed5info= OaklandRaidersInfo;
		AFCseed6 = MiamiDolphins;
		AFCseed6info= MiamiDolphinsInfo;
		
		
		NFCseed1 = DallasCowboys;
		NFCseed1info= DallasCowboysInfo;
		NFCseed2 = AtlantaFalcons;
		NFCseed2info= AtlantaFalconsInfo;
		NFCseed3 = SeattleSeahawks;
		NFCseed3info= SeattleSeahawksInfo;
		NFCseed4 = GreenBayPackers;
		NFCseed4info= GreenBayPackersInfo;
		NFCseed5 = NewYorkGiants;
		NFCseed5info= NewYorkGiantsInfo;
		NFCseed6 = DetroitLions;
		NFCseed6info= DetroitLionsInfo;
		
		//wild card 
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		
		//divisional
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		
		//conference championship
		System.out.println(playoffPicture());
		System.out.println(simResults(round));
		
		//super bowl
		System.out.println(playoffPicture());
		System.out.println(simResults(round)); 
		
		/** Sample playoff picture
		ROUND: Wild Card Round

		AFC
		1) New England Patriots (14-2)
		Offense: 3rd     Defense: 1st

		2) Kansas City Chiefs (12-4)
		Offense: 13th     Defense: 7th

		3) Pittsburgh Steelers (11-5)
		Offense: 11th     Defense: 10th

		4) Houston Texans (9-7)
		Offense: 29th     Defense: 11th

		5) Oakland Raiders (12-4)
		Offense: 7th     Defense: 20th

		6) Miami Dolphins (10-6)
		Offense: 17th     Defense: 18th

		NFC
		1) Dallas Cowboys (13-3)
		Offense: 5th     Defense: 5th

		2) Atlanta Falcons (11-5)
		Offense: 1st     Defense: 27th

		3) Seattle Seahawks (10-5-1)
		Offense: 18th     Defense: 3rd

		4) Green Bay Packers (10-6)
		Offense: 4th     Defense: 21st

		5) New York Giants (11-5)
		Offense: 26th     Defense: 2nd

		6) Detroit Lions (9-7)
		Offense: 20th     Defense: 13th

		Upcoming games:
		Pittsburgh Steelers (3) vs. Miami Dolphins (6)
		Probability: Pittsburgh Steelers 70%, Miami Dolphins 30%

		Houston Texans (4) vs. Oakland Raiders (5)
		Probability: Houston Texans 40%, Oakland Raiders 60%

		Seattle Seahawks (3) vs. Detroit Lions (6)
		Probability: Seattle Seahawks 83%, Detroit Lions 17%

		Green Bay Packers (4) vs. New York Giants (5)
		Probability: Houston Texans 42%, Oakland Raiders 58%


		Results:
		Pittsburgh Steelers (3) vs. Miami Dolphins (6)
		Winner: Pittsburgh Steelers 28-3

		Houston Texans (4) vs. Oakland Raiders (5)
		Winner: Houston Texans 24-3

		Seattle Seahawks (3) vs. Detroit Lions (6)
		Winner: Seattle Seahawks 16-7

		Green Bay Packers (4) vs. New York Giants (5)
		Winner: New York Giants 17-10



		ROUND: Divisional Round

		Upcoming games:
		New England Patriots (1) vs. Houston Texans (4)
		Probability: New England Patriots 94%, Houston Texans 6%

		Kansas City Chiefs (2) vs. Pittsburgh Steelers (3)
		Probability: Kansas City Chiefs 43%, Pittsburgh Steelers 57%

		Dallas Cowboys (1) vs. New York Giants (5)
		Probability: Dallas Cowboys 55%, New York Giants 45%

		Atlanta Falcons (2) vs. Seattle Seahawks (3)
		Probability: Atlanta Falcons 55%, Seattle Seahawks 45%


		Results:
		New England Patriots (1) vs. Houston Texans (4)
		Winner: New England Patriots 27-3

		Kansas City Chiefs (2) vs. Pittsburgh Steelers (3)
		Winner: Pittsburgh Steelers 20-17

		Dallas Cowboys (1) vs. New York Giants (5)
		Winner: New York Giants 21-10

		Atlanta Falcons (2) vs. Seattle Seahawks (3)
		Winner: Atlanta Falcons 17-10



		ROUND: Conference Championships

		Upcoming games:
		New England Patriots (1) vs. Pittsburgh Steelers (3)
		Probability: New England Patriots 70%, Pittsburgh Steelers 30%

		New York Giants (5) vs. Atlanta Falcons (2)
		Probability: New York Giants 52%, Atlanta Falcons 48%


		Results:
		New England Patriots (1) vs. Pittsburgh Steelers (3)
		Winner: New England Patriots 17-7

		New York Giants (5) vs. Atlanta Falcons (2)
		Winner: New York Giants 20-7



		ROUND: SUPER BOWL

		Upcoming games:
		New England Patriots (1) vs. New York Giants (5)
		Probability: New England Patriots 75%, New York Giants 25%

	
		Results:
		New England Patriots (1) vs. New York Giants (5)
		Winner: New England Patriots 17-3

		SUPER BOWL CHAMPS: The New England Patriots!
		*/
		
	}
	
}