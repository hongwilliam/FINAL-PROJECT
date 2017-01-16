public class ReferenceBook extends LibraryBook{

	private String collection;
	
	public ReferenceBook(String writer, String name, String ID, String call, String col){
		super(writer, name, ID, call);
		collection = col; }
	
	public void setCollection(String s){
		collection = s; }
		
	public String getCollection(){
		return collection; }
		
	public void checkout(String patron, String due){
		System.out.println(new IllegalStateException("cannot check out a reference book"));  }

	public void returned(){
		System.out.println(new IllegalStateException("reference book could not have been checked out - return impossible"));  }
		
	public String circulationStatus(){
		return "non-circulating reference book"; }
		
	public String toString(){
		String s = super.toString() + getCollection(); 
		return s; }
		
	}
