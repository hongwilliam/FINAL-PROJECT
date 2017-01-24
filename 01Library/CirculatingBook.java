public class CirculatingBook extends LibraryBook{

	private String currentHolder;
	private String dueDate;
	
	public CirculatingBook(String writer, String name, String ID, String call){
		super(writer, name, ID, call);
		currentHolder = "";
		dueDate = ""; }
		
	public void setCurrentHolder(String s){
		currentHolder = s; }
		
	public void setDueDate(String s){
		dueDate = s; }
		
	public String getCurrentHolder(){
		return currentHolder; }
		
	public String getDueDate(){
		return dueDate; }
		
	public void checkout(String patron, String due){
		setCurrentHolder(patron);
		setDueDate(due); }
		
	public void returned(){
		setCurrentHolder("");
		setDueDate(""); }
		
	public String circulationStatus(){
		if (! getCurrentHolder().equals("") && getDueDate().equals("") ){
			String s = getCurrentHolder() + getDueDate(); 
			return s; }
		else{
			return "book avaiable on shelves"; }
		
	}
	
	public String toString(){
		String s = super.toString() + circulationStatus();
		return s; }
		
}