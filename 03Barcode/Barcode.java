import java.util.*;

public class BarCode implements Comparable<BarCode>{
   
	// instance variables
   	private String _zip;
	private int _checkDigit;

	//IMPORTANT: know the difference between checkDigit and checkSum methods!!

	//returns the check digit or the sum of the digits % 10
	private static int checkDigit(String zip){
		int i = 0, sumDigs = 0;
		while (i < zip.length() ){
			sumDigs += Character.getNumericValue(zip.charAt(i) );
			i++; }
		sumDigs %= 10;
		return sumDigs;
	}
	
	//returns zip code + check digit at end as one big int
  	private static int checkSum(String zip){
		String everything = zip;
		int i = 0, sumDigs = 0, answer = 0;
		while (i < zip.length() ){
			sumDigs += Character.getNumericValue(zip.charAt(i) );
			i++; }
		sumDigs %= 10;
		everything += Integer.toString(sumDigs);
		answer = Integer.parseInt(everything);
		return answer; }
		
		
	//method: does the zip have only digits and a valid length? 
	private static boolean hasDigitsOnly(String zip){
		int i = 0;
		if (zip.length() != 5 || ! zip.matches("[0-9]+")){
			return false; }
		else{
			return true; }
		}
	
	//constructor
 	public BarCode(String zip) {
		if ( hasDigitsOnly(zip) == true){
			_zip = zip;
			_checkDigit = checkSum(zip) % 10;  }
		else{
			throw new IllegalArgumentException("invalid input!"); }
		}
	
	
	//digit to bar code sequence method
	private static String convert(int digit){
		if (digit == 1){
			return ":::||"; }
			
		else if (digit == 2){
			return "::|:|"; }
			
		else if (digit == 3){
			return "::||:";}
			
		else if (digit == 4){
			return ":|::|"; }
			
		else if (digit == 5){
			return ":|:|:"; }
			
		else if (digit == 6){
			return ":||::"; }
		
		else if (digit == 7){
			return "|:::|"; }
			
		else if (digit == 8){
			return "|::|:"; }
			
		else if (digit == 9){
			return "|:|::"; }
			
		else if (digit == 0){
			return "||:::"; }
			
		else {
			throw new IllegalArgumentException("not a digit!"); 
		}
		
	}
	
	//bar code sequence to digit method
	private static int dig(String sequence){
		if (sequence.equals(":::||") ){
			return 1; }
			
		else if (sequence.equals("::|:|") ){
			return 2; }
		
		else if (sequence.equals("::||:") ){
			return 3; }
			
		else if (sequence.equals(":|::|") ){
			return 4; }
			
		else if (sequence.equals(":|:|:") ){
			return 5; }
			
		else if (sequence.equals(":||::") ){
			return 6; }
			
		else if (sequence.equals("|:::|") ){
			return 7; }
			
		else if (sequence.equals("|::|:") ){
			return 8; }
		
		else if (sequence.equals("|:|::") ){
			return 9; }
			
		else if (sequence.equals("||:::") ){
			return 0; }
			
		else{
			throw new IllegalArgumentException("invalid sequence"); }
			
	}
	
	//zip code to bar code sequence method
  	public static String toCode(String zip){
		if (hasDigitsOnly(zip) == false){
			throw new IllegalArgumentException("must be digits!"); }
		
		else{
			String answer = "|";
			int i = 0, compareDig = 0;
			while (i < zip.length() ) {
				compareDig = Character.getNumericValue(zip.charAt(i) );
				answer += convert(compareDig);
				i++; }
			answer += convert(checkDigit(zip) );
			answer += "|"; 
			return answer; }
	}
	
	//bar code sequence to zip code method	
	public static String toZip(String code){
		if (code.length() != 32){
			throw new IllegalArgumentException("invalid length!"); }
		else{
			if (code.charAt(0) != '|' || code.charAt(code.length()-1)!='|'){
				throw new IllegalArgumentException("start and end should be a bar!"); }

			else{
				String answer = "";
				int i = 1;
				while (i < 26){
					String seq = code.substring(i, i+5);
					int digit = dig(seq);
					answer += digit;
					i += 5; }
				String check = code.substring(26,31);
				int checkConverted = dig(check);
				if (checkConverted == checkDigit(answer)){
					return answer; }
				else{
					throw new IllegalArgumentException("invalid check digit!"); }
			}
		}
	}
	
	public String toString(){
		String answer = _zip + _checkDigit;
		answer += "		|";
		int i = 0, compareDig = 0;
		while (i < _zip.length() ) {
			compareDig = Character.getNumericValue(_zip.charAt(i) );
			answer += convert(compareDig);
			i++; }
		answer += convert(_checkDigit);
		answer += "|"; 
		return answer;
	}
	
	public String get_zip(){
		return _zip;
	}
	
	public int compareTo(BarCode other){
		int a = checkSum(get_zip() );
		int b = other.checkSum(other.get_zip() );
		if (a < b){
			return -1; }
		else{
			if (a == b){
				return 0; }
			else{
				return 1; }
		}	
	}
		
	//basic testing
	public static void main (String[] args){
		BarCode x = new BarCode("08451");
		
		System.out.println(x.toString() );
		// "084518  |||:::|::|::|::|:|:|::::|||::|:|"   
		String a = toZip("|||:::|::|::|::|:|:|::::|||::|:|");
		String b = toCode("08451");
		System.out.println(a); //08451
		System.out.println(b); //|||:::|::|::|::|:|:|::::|||::|:|

		//
	}
	
}