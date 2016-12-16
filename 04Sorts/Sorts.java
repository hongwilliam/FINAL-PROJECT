import java.util.*;
import java.* ;

public class Sorts{

	public static String name(){
		return "06.Hong.William"; }

	public static void selectionSort(int[] data){
		for (int i=0; i < data.length-1; i++){  
			//we are trying to find the index of the min element
            int minIndex = i;  
			
			//if inserting min into beginning was successful
			//then we should begin looping from i+1 
			//(we only want to check remaning elements. not beginning ones)
            for (int minCheck = i+1; minCheck < data.length; minCheck++){  
                if (data[minCheck] < data[minIndex]){  
					minIndex = minCheck; }  
            }  
            int min = data[minIndex]; 
				
			//this is where the swap occurs
            data[minIndex] = data[i];  
			
			//this is where we actually insert the min into its right place
            data[i] = min;  
        }  
    }
	
	public static void insertionSort(int[] data){
		int i, adj, temp;
		for (i=1; i < data.length; i++){
			//this keeps track of the element under consideration
			adj = i;
			
			//smaller elements are shifting to the left until it is in its right place
			while (adj > 0 && data[adj-1] > data[adj] ) {
				
				//this is the placeholder element directly to the left of the index under consideration
				temp = data[adj-1];
				
				//swapping occurs here
				data[adj-1] = data[adj];
				data[adj] = temp;
				
				adj--; }
		}
	}
	
	public static void bubbleSort(int[] data){
		//the bubble sort terminates early if no swaps were made
		boolean swapMade = true;
		while (swapMade){
			swapMade = false;
			for (int i=0; i < data.length-1; i++){
				//basic swapping occurs between adjacent elements
				//however, if this if statement is not satisfied, the loop stops
				if (data[i] > data[i+1]){
					int larger = data[i], smaller = data[i+1];
					data[i] = smaller;
					data[i+1] = larger; 
					swapMade = true; }
			}
		}
	}
		
	//used a classmate's sample test cases
	public static void main (String[] args){
		
		System.out.println(name());
		//06.Hong.William
		
		int[] aSelect = new int[] {6,3,1,7,5,9,4,0,2};
		selectionSort(aSelect);
		
		int[] aInsert = new int[] {6,3,1,7,5,9,4,0,2};
		insertionSort(aInsert);
		
		int[] aBubble = new int[] {6,3,1,7,5,9,4,0,2};
		bubbleSort(aBubble);
		
		System.out.println(Arrays.toString(aSelect));
		System.out.println(Arrays.toString(aInsert));
		System.out.println(Arrays.toString(aBubble));
		//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
		
		int[] bSelect = new int[] {};
		selectionSort(bSelect);
		
		int[] bInsert = new int[] {};
		insertionSort(bInsert);
		
		int[] bBubble = new int[] {};
		bubbleSort(bBubble);
		
		System.out.println(Arrays.toString(bSelect));
		System.out.println(Arrays.toString(bInsert));
		System.out.println(Arrays.toString(bBubble));
		//[]
		//[]
		
		int[] cSelect = new int[] {10};
		selectionSort(cSelect);
		
		int[] cInsert = new int[] {10};
		insertionSort(cInsert);
		
		int[] cBubble = new int[] {10};
		bubbleSort(cBubble);
		
		System.out.println(Arrays.toString(cSelect));
		System.out.println(Arrays.toString(cInsert));
		System.out.println(Arrays.toString(cBubble));
		//[10]
		//[10]
		//[10]
		
		int[] dSelect = new int[] {400,303,1500,1759,1618,13,11,1436,2168,1172,5280,2016,38387};		
		selectionSort(dSelect);
		
		int[] dInsert = new int[] {400,303,1500,1759,1618,13,11,1436,2168,1172,5280,2016,38387};
		insertionSort(dInsert);
		
		int[] dBubble = new int[] {400,303,1500,1759,1618,13,11,1436,2168,1172,5280,2016,38387};
		bubbleSort(dBubble);
		
		System.out.println(Arrays.toString(dSelect)); 
		System.out.println(Arrays.toString(dInsert)); 
		System.out.println(Arrays.toString(dBubble)); 
		//[11, 13, 303, 400, 1172, 1436, 1500, 1618, 1759, 2016, 2168, 5280, 38387]
		//[11, 13, 303, 400, 1172, 1436, 1500, 1618, 1759, 2016, 2168, 5280, 38387]
		//[11, 13, 303, 400, 1172, 1436, 1500, 1618, 1759, 2016, 2168, 5280, 38387]
	}


}