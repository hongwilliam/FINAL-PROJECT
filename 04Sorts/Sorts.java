import java.util.*;
import java.* ;

public class Sorts{

	public static String name(){
		return "06.Hong.William"; }

	public static void selectionSort(int[] data){
		
		//this is a placeholder int[] array
		int[] answer = new int[data.length];
		
		//convert the data array into an ArrayList<Integer>
		//because ArrayList<int> is impossible
		ArrayList<Integer> a = new ArrayList<Integer>(data.length);
		for (int i=0; i < data.length; i++){
			a.add(data[i]);
		}
		
		//make the placeholder int[] array into a sorted array 
		//this is where making it to an ArrayList becomes useful: we can take its min
		for(int i=0; i < data.length; i++){
			int element = Collections.min(a);
			answer[i] = element;
			a.remove(a.indexOf(element));
		} 
		
		//just manually replace each index of the original data array with sorted elements
		for(int i=0; i < data.length; i++){
			data[i] = answer[i];
		}
	}
		
	//used a classmate's sample test cases
	public static void main (String[] args){
		
		System.out.println(name());
		//06.Hong.William
		
		int[] a = new int[] {6,3,1,7,5,9,4,0,2};
		selectionSort(a);
		System.out.println(Arrays.toString(a));
		//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		
		int[] b = new int[] {};
		selectionSort(b);
		System.out.println(Arrays.toString(b));
		//[]
		
		int[] c = new int[] {10};
		selectionSort(c);
		System.out.println(Arrays.toString(c));
		//[10]
		
		int[] d = new int[] {400,303,1500,1759,1618,13,11,1436,2168,1172,5280,2016,38387};		
		selectionSort(d);
		System.out.println(Arrays.toString(d)); 
		//[11, 13, 303, 400, 1172, 1436, 1500, 1618, 1759, 2016, 2168, 5280, 38387]
	}


}