import java.util.*;

public class FinalProject{

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
	19: D-Line Run Stuffing */

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
		double advantage = convertRawToScore(teamA) - convertRawToScore(teamB);
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
	
	
	public static void main (String[] args){
		List<Integer> NewEnglandPatriots = Arrays.asList(4, 1, 7, 2, 8, 2, 5, 1, 8, 3, 3, 2, 19, 8, 8, 16, 22, 5, 23, 12);
		List<Integer> OaklandRaiders = Arrays.asList(1, 7, 18, 8, 32, 8, 3, 19, 13, 14, 7, 4, 12, 1, 10, 22, 23, 21, 27, 27);
		List<Integer> PittsburghSteelers = Arrays.asList(13, 4, 13, 3, 16, 12, 12, 9, 14, 12, 9, 8, 4, 3, 2, 9, 13, 9, 17, 13); 
		List<Integer> HoustonTexans = Arrays.asList(7, 26, 9, 29, 4, 30, 29, 10, 31, 32, 30, 30, 27, 9, 15, 11, 9, 17, 25, 16);
		List<Integer> KansasCityChiefs = Arrays.asList(2, 5, 3, 7, 13, 15, 15, 5, 27, 18, 13, 11, 20, 16, 17, 12,  4, 23, 24, 28); 
		List<Integer> MiamiDolphins = Arrays.asList(11, 18, 12, 15, 10, 9, 16, 14, 15, 6, 14, 18, 15, 23, 21, 17, 14, 19, 31, 20);
	
		List<Integer> DallasCowboys = Arrays.asList(7, 2, 24, 1, 15, 3, 4, 4, 2, 4, 2, 3, 2, 11, 3, 18, 19, 8, 10, 11);
		List<Integer> AtlantaFalcons = Arrays.asList(3, 3, 22, 4, 11, 1, 1, 25, 10, 1, 1, 1, 8, 22, 9, 27, 24, 28, 22, 24);
		List<Integer> SeattleSeahawks = Arrays.asList(20, 6, 5, 10, 14, 13, 20, 2, 26, 7, 18, 16, 21, 25, 26, 4, 11, 2, 13, 10);
		List<Integer> GreenBayPackers = Arrays.asList(8, 10, 26, 6, 31, 5, 6, 22, 12, 17, 6, 9, 9, 14, 22, 19, 20, 12, 4, 9); 
		List<Integer> NewYorkGiants = Arrays.asList(25, 16, 2, 12, 5, 20, 25, 3, 17, 20, 21, 21, 24, 4, 25, 2,  3, 4, 29, 17); 
		List<Integer> DetroitLions = Arrays.asList(17, 21, 32, 27, 25, 14, 21, 13, 19, 16, 17, 13, 26, 18, 31, 32, 32, 22, 21, 19); 
		
		/** System.out.println(convertRawToScore(NewEnglandPatriots)); //90.47
		System.out.println(convertRawToScore(OaklandRaiders)); //68.13
		System.out.println(convertRawToScore(PittsburghSteelers)); //70.0
		System.out.println(convertRawToScore(HoustonTexans)); //46.72
		System.out.println(convertRawToScore(KansasCityChiefs)); //77.03
		System.out.println(convertRawToScore(MiamiDolphins)); //63.59
		
		System.out.println(convertRawToScore(DallasCowboys)); //78.44
		System.out.println(convertRawToScore(AtlantaFalcons)); //78.75
		System.out.println(convertRawToScore(SeattleSeahawks)); //65.31
		System.out.println(convertRawToScore(GreenBayPackers)); //59.38
		System.out.println(convertRawToScore(NewYorkGiants)); //57.34
		System.out.println(convertRawToScore(DetroitLions)); //36.25 */
	
		//sample playoffs based on un-adjusted probability
		//wild card round
		System.out.println(determineWinner(PittsburghSteelers, MiamiDolphins)); // 0 -> Pittsburgh 
		System.out.println(determineWinner(HoustonTexans, KansasCityChiefs)); // 1 -> Kansas City 
		System.out.println(determineWinner(SeattleSeahawks, DetroitLions)); // 0 -> Seattle 
		System.out.println(determineWinner(GreenBayPackers, NewYorkGiants)); // 1 -> Green Bay
		
		//divisional round
		System.out.println(determineWinner(NewEnglandPatriots, KansasCityChiefs)); // 0 -> New England
		System.out.println(determineWinner(OaklandRaiders, PittsburghSteelers)); // 1 -> Pittsburgh
		System.out.println(determineWinner(DallasCowboys, GreenBayPackers)); // 1 -> Green Bay
		System.out.println(determineWinner(AtlantaFalcons, SeattleSeahawks)); // 1 -> Seattle
		
		//conference championships
		System.out.println(determineWinner(NewEnglandPatriots, PittsburghSteelers)); // 0 -> New England
		System.out.println(determineWinner(SeattleSeahawks, GreenBayPackers)); // 1 -> Green Bay
		
		//super bowl LI
		System.out.println(determineWinner(NewEnglandPatriots, GreenBayPackers)); //0 -> New England
		
		//super bowl champion: New England Patriots!
		
		/** 
		//playoffs if the favored team wins every time
		//wild card round
		System.out.println(probabilityGame(PittsburghSteelers, MiamiDolphins)); // Pittsburgh 67%
		System.out.println(probabilityGame(HoustonTexans, KansasCityChiefs)); // Kansas City 91%
		System.out.println(probabilityGame(SeattleSeahawks, DetroitLions)); // Seattle 83%
		System.out.println(probabilityGame(GreenBayPackers, NewYorkGiants)); // New York 51%
		
		//divisional round
		System.out.println(probabilityGame(NewEnglandPatriots, KansasCityChiefs)); // New England 64%
		System.out.println(probabilityGame(OaklandRaiders, PittsburghSteelers)); // Pittsburgh 60%
		System.out.println(probabilityGame(DallasCowboys, NewYorkGiants)); // Dallas 74%
		System.out.println(probabilityGame(AtlantaFalcons, SeattleSeahawks)); // Atlanta 58% 
	
		//conference championships
		System.out.println(probabilityGame(NewEnglandPatriots, PittsburghSteelers)); // New England 66%
		System.out.println(probabilityGame(DallasCowboys, AtlantaFalcons)); // Dallas 58%
		
		//super bowl LI
		System.out.println(probabilityGame(NewEnglandPatriots, DallasCowboys)); // New England 60% (super bowl champion: New England)
		*/
	
		/** 
		//Playoffs based solely on stat grades
		//wild card round
		System.out.println(statAdvantage(PittsburghSteelers, MiamiDolphins)); //6.41 (winner: Pittsburgh)
		System.out.println(statAdvantage(HoustonTexans, KansasCityChiefs)); //-30.31 (winner: Kansas City)
		System.out.println(statAdvantage(SeattleSeahawks, DetroitLions)); //29.06 (winner: Seattle)
		System.out.println(statAdvantage(GreenBayPackers, NewYorkGiants)); //1.88 (winner: Green Bay)
		
		//divisional round
		System.out.println(statAdvantage(NewEnglandPatriots, KansasCityChiefs)); //13.44 (winner: New England)
		System.out.println(statAdvantage(OaklandRaiders, PittsburghSteelers)); //-1.87 (winner: Pittsburgh)
		System.out.println(statAdvantage(DallasCowboys, GreenBayPackers)); //19.22 (winner: Dallas)
		System.out.println(statAdvantage(AtlantaFalcons, SeattleSeahawks)); //13.44 (winner: Atlanta)
		
		//conference championships
		System.out.println(statAdvantage(NewEnglandPatriots, PittsburghSteelers)); //20.47 (winner: New England)
		System.out.println(statAdvantage(DallasCowboys, AtlantaFalcons)); //-0.31 (winner: Atlanta)
		
		//suoer bowl LI
		System.out.println(statAdvantage(NewEnglandPatriots, AtlantaFalcons)); //11.72 (super bowl champion: New England!)
		*/

		/**
		//Playoffs based solely on matchup grades
		//wild card round
		System.out.println(matchupAdvantage(PittsburghSteelers, MiamiDolphins)); //49.68 (winner: Pittsburgh)
		System.out.println(matchupAdvantage(HoustonTexans, KansasCityChiefs)); //-42.42 (winner: Kansas City)
		System.out.println(matchupAdvantage(SeattleSeahawks, DetroitLions)); //72.58 (winner: Seattle)
		System.out.println(matchupAdvantage(GreenBayPackers, NewYorkGiants)); //0.32 (winner: Green Bay)
		
		//divisional round
		System.out.println(matchupAdvantage(NewEnglandPatriots, KansasCityChiefs)); //17.1 (winner: New England)
		System.out.println(matchupAdvantage(OaklandRaiders, PittsburghSteelers)); //-37.1 (winner: Pittsburgh)
		System.out.println(matchupAdvantage(DallasCowboys, GreenBayPackers)); //21.29 (winner: Dallas)
		System.out.println(matchupAdvantage(AtlantaFalcons, SeattleSeahawks)); //-14.84 (winner: Seattle)
		
		//conference championships
		System.out.println(matchupAdvantage(NewEnglandPatriots, PittsburghSteelers)); //-11.94 (winner: Pitsburgh)
		System.out.println(matchupAdvantage(DallasCowboys, SeattleSeahawks)); //19.84 (winner: Dallas)
		
		//super bowl LI
		System.out.println(matchupAdvantage(PittsburghSteelers, DallasCowboys)); //2.58 (super bowl champion: Pittsburgh)
		*/
		
	}
	
}