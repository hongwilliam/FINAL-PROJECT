public abstract class LibraryBook extends Book implements Comparable<LibraryBook> {

	private String callNumber;
	
	public LibraryBook(String writer, String name, String ID, String call){
		super(writer, name, ID);
		callNumber = call; }
		
	public void setCallNumber(String s){
		callNumber = s; }
		
	public String getCallNumber(){
		return callNumber; }
		
	abstract void checkout(String patron, String due);
	
	abstract void returned();
	
	abstract String circulationStatus();
	
	public String toString(){
		String s = super.toString() + circulationStatus() + getCallNumber();
		return s; }
	
	public int compareTo(LibraryBook other){
		return getCallNumber().compareTo(other.getCallNumber() ); }
		
}

	
	
	