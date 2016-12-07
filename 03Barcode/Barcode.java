public class BarCode implements Comparable<Barcode>{
   
	// instance variables
   	private String _zip;
	private int _checkDigit;

	// postcondition: computes and returns the check sum for _zip
  	private int checkSum(String zip){
		int i = 0, answer = 0;
		while (i < zip.length() ){
			answer += zip.charAt(i) - '0';
			i++; }
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
			_checkDigit = checkSum(zip); }
		else{
			throw new RunTimeException(); }
		}
	
	//get methods
	public String getZip(){
		return _zip; }

	public int getCheckDigit(){
		return _checkDigit; }	


	// postcondition: Creates a copy of a bar code.
 	public Barcode clone(){
		Barcode answer = "||"; 
		int i  }
		

	//postcondition: format zip + check digit + barcode 
	//ex. "084518  |||:::|::|::|::|:|:|::::|||::|:|"      
  	public String toString(){
		String answer = "||";
		int i = 0;
		while (i <  ){
			


	// postcondition: compares the zip + checkdigit, in numerical order. 
  	public int compareTo(Barcode other){}
    
}
