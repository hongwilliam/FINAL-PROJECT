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

	/** Point distrubtion for key stats (sum rounded to hundredth)
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
	
	
	//formulas go here
	
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
		
		System.out.println(convertRawToScore(NewEnglandPatriots)); //90.47
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
		System.out.println(convertRawToScore(DetroitLions)); //36.25 
	
	}
	
}