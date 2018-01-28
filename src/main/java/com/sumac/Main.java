package com.sumac;

import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		MyFunctions mf = new MyFunctions();
		long timeStart, timeEnd;
		
		int arraySize = 10_000_000;
		int rangeOfValues = 40_000_000;		
		int targetSum = 100;
				
		//Prepare random array
		int[] ia = new int[arraySize];		
		Random rand = new Random();
		for (int i = 0; i < ia.length; i++)
			ia[i] = rand.nextInt(rangeOfValues);
		//System.out.println("Searching in array:");
		//System.out.println(Arrays.toString(ia));		
		
		System.out.println("Sequential search");
		timeStart = System.currentTimeMillis();
		System.out.println("Second largest integer in array = " + mf.secondLargest(ia));
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " ms\n");
		
		System.out.println("Using ForkJoin framework search"); 
		timeStart = System.currentTimeMillis();
		System.out.println("Second largest integer in array = " + mf.secondLargestFJ(ia));
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " ms\n");	
		
		System.out.println("Find Pairs (sum = " + targetSum + "), algorithm using map:");
		timeStart = System.currentTimeMillis();
		System.out.println("Founded: " + mf.findPairs(ia, targetSum));
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " ms\n");				
		
	}

}
