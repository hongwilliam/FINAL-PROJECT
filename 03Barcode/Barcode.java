import java.util.*;

public class BarCode implements Comparable<BarCode>{
   
	// instance variables
   	private String _zip;
	private int _checkDigit;

	//this returns zip code + check digit at end
  	private int checkSum(){
		String everything = _zip;
		int i = 0, sumDigs = 0, answer = 0;
		while (i < _zip.length() ){
			sumDigs += Character.getNumericValue(_zip.charAt(i) );
			i++; }
		sumDigs %= 10;
		everything += Integer.toString(sumDigs);
		answer = Integer.parseInt(everything);
		return answer; }
		
		
	//method: does the zip have only digits?
	private boolean hasDigitsOnly(String zip){
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
			_checkDigit = checkSum() % 10;  }
		else{
			throw new RuntimeException(); }
		}
	
	
	//int to bar code method
	private String convert(int digit){
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
			throw new RuntimeException();
		}
		
	}
		
	
 	public BarCode clone(){
		BarCode x = new BarCode(_zip);
		return x;
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
			
	public int compareTo(BarCode other){
		int a = checkSum();
		int b = other.checkSum();
		if (a < b){
			return -1; }
		else{
			if (a == b){
				return 0; }
			else{
				return 1; }
		}	
	}
		
	//testing
	public static void main (String[] args){
		BarCode x = new BarCode("08451");
		BarCode y = new BarCode("11375");
		
		System.out.println(x.toString() );
		// "084518  |||:::|::|::|::|:|:|::::|||::|:|"   
		System.out.println(x.compareTo(y) ); // -1
		
		BarCode z = new BarCode("abc12"); //RuntimeException
		BarCode a = new BarCode("123"); //RuntimeException
		BarCode b = new BarCode("123456"); //RuntimeException
	}
	
}
		
		
		
		
		
		