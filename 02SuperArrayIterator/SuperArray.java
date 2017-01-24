import java.util.*;

public class SuperArray implements Iterable<String>{

    private String[] data;
    private int size;

    //default constructor
    public SuperArray(){
		data = new String[10];
		size = 0; }
		
	//constructor
    public SuperArray(int capacity){
	   if (capacity < 0){
			throw new IllegalArgumentException(); }
		data = new String[capacity];
		size = 0; }

	//constructor
	public SuperArray(String[] ary){
		size = ary.length;
		data = new String[size];
		for (int i = 0; i < ary.length; i++){
			data[i] = ary[i]; } }
	
	//accessor methods
	public int getSize(){
		return size; }
	
	public int getDataLength(){
		return data.length; }
		 
    // add(String) method
    public boolean add (String s){
		if (size < data.length){
			data[size] = s;
			size += 1; }
			
		else {
			String[] copy = new String[size+1];
			for (int i = 0; i < data.length; i++){
				copy[i] = data[i]; }
			
			copy[size] = s;
			size += 1;
			data = copy; }
			
		return true; }
	
	// get(int) method
	public String get(int index){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException(); }
		return data[index]; }
		  
	// toString() method
	public String toString(){
		String answer = "[" ;
		int i = 0;
		if (size == 0){
			return "[]"; }
		else {
			while (i < size-1){
				answer += data[i] + ", ";
				i += 1; }
			answer += data[i] + "]"; }
		return answer; }
		
	// toStringDebug() method
	public String toStringDebug(){
		String answer = "[";
		int i = 0;
		while (i < data.length - 1){
			
			while (i < size){
				answer += data[i] + ", " ;
				i += 1; }
				
			answer += "_, " ;
			i += 1; }
			
		answer += "_]"; 
		return answer; }
		
	// clear() method
	public void clear(){
		size = 0; }
	
	// isEmpty() method
	public boolean isEmpty(){
		return (size == 0); }
		
	// set(int, String) method
	public String set(int index, String element){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException(); }
		String original = data[index];
		data[index] = element;
		return original; }
		
	// add(int, String) method
	public void add(int index, String element){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException(); }
		int i = 0, old = 0;
		size += 1;
		String[] copy = new String[size];
			
		while (i < index){
			copy[i] = data[old];
			i += 1;
			old += 1; }
			
		copy[i] = element;
		i += 1;
		
		while (i < size){
			copy[i] = data[old];
			old += 1;
			i += 1; }
		data = copy; }
		
	// remove(int) method
	public String remove(int index){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException(); }
		int i = 0, old = 0;
		size -= 1;
		String[] copy = new String[size];
		String original = data[index];
		
		while (i < index){
			copy[i] = data[old];
			i += 1;
			old += 1; }
			
		old += 1;
		while (i < size){
			copy[i] = data[old];
			old += 1;
			i += 1; }
			
		data = copy;
		return original; }
		
	// toArray() method
	public String[] toArray(){
		String copy[] = new String[size];
		int i = 0;
		while (i < size){
			copy[i] = data[i];
			i += 1;}
		return copy; }
	
	// indexOf(String) method
	public int indexOf(String s){
		int x = 0, answer = -1;
		while (x < size){
			if (data[x].equals(s) ){
				answer = x;
				x = size;}
			else{
				x += 1; }
		}
		return answer; }
		
	// lastIndexOf(s) method
	public int lastIndexOf(String s){
		int x = size-1, answer = -1;
		while (x > 0){
			if(data[x].equals(s) ){
				answer = x;
				x = 0; }
			else{
				x -= 1;}
		}
		return answer; }
		
	// grow() method
	public void grow(){
		String[] copy = new String[size * 2];
		for (int i = 0; i < data.length; i++){
			copy[i] = data[i]; }
		data = copy; }
		
	// trimToSize() method
	public void trimToSize(){
		String[] copy = new String[size];
		for (int i = 0; i < size; i++){
				copy[i] = data[i]; }
		data = copy; }
	
	//12/3/16 NEW UPDATE: iterator() method
	public Iterator<String> iterator(){
		Iterator<String> iter = new Iterator<String>(){
				private int current = 0;
				
				public boolean hasNext(){
					return (current < size && data[current] != null); }
	
				public String next(){
					if( hasNext() ){
						current++;
						return data[current - 1]; }
					else{
						throw new NoSuchElementException(); } }
	
				public void remove(){
					throw new UnsupportedOperationException(); } };
					
				return iter; } 
	
	//testing 
	public static void main (String[] args){
		
		//Mr. K's test case
		SuperArray data = new SuperArray();
		int i = 0;
		while(i < 26){
			data.add(""+(char)('A'+i%26));
			i++; }

		System.out.println(data);
		System.out.println("Standard loop:");

		for(int n = 0; n < data.getSize(); n++){
			System.out.print(data.get(n)+" "); }
			
		System.out.println();
		System.out.println("for-each loop:");
		for(String s : data){
			System.out.print(s+" "); } 
			
		/** expected output:
		[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]
		Standard loop:
		A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
		for-each loop:
		A B C D E F G H I J K L M N O P Q R S T U V W X Y Z */
		
	}
}


