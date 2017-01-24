public class Book{

	private String author;
	private String title;
	private String ISBN;

	public Book(){
		author = "";
		title = "";
		ISBN = ""; }

	public Book(String writer, String name, String ID){
		author = writer;
		title = name;
		ISBN = ID; }
		
	public void setAuthor(String s){
		author = s; }
		
	public void setTitle(String s){
		title = s; }
		
	public void setISBN(String s){
		ISBN = s; }
		
	public String getAuthor(){
		return author; }
		
	public String getTitle(){
		return title; }
		
	public String getISBN(){
		return ISBN; }
		
	public String toString(){
		String s = title + ", " + author + ", " + ISBN;
		return s; }
		
	}