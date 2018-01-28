package com.sumac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyFunctions {

	/**
	 * 
	 * find the second largest integer in an array.
	 * 
	 * @param ia
	 *            an array of integers
	 * 
	 * @return the second largest number in ia or a default value of 0
	 * 
	 */		
	public int secondLargest(final int[] ia) {
		int firstMax, secondMax;
		
		if (ia.length < 2)
			return 0;
		
		firstMax = ia[0];
		secondMax = ia[0];
				
		for (int i : ia) {
			if (i >	firstMax) {
				secondMax = firstMax;
				firstMax = i;
			} else if (i > secondMax && i != firstMax) {
				secondMax = i;
			} else if (firstMax == secondMax) {
				secondMax = i;
			}
		}
		
		if (firstMax == secondMax)
			return 0;
		
        return secondMax;
	} // secondLargest
		
	private static class SecondLargestWithFJ extends RecursiveTask<Pair> {
		private static final long serialVersionUID = 1L;
	    private final int threshold;
	    private final int[] ia;
	    private final int from;
	    private final int to;
	    public Pair result;
	 
	    public SecondLargestWithFJ(int[] ia, int from, int to, int threshold) {
	        this.threshold = threshold;
	        this.from = from;
	        this.to = to;
	        this.ia = ia;
	    }
	 
	    @Override
	    protected Pair compute() {
	        int firstMax, secondMax;
	        
	        if ((to - from) < threshold) {
	        	firstMax = ia[from];
	    		secondMax = ia[from];
	    		for (int i = from; i < to; i++) {
	    			if (ia[i] >	firstMax) {
	    				secondMax = firstMax;
	    				firstMax = ia[i];
	    			} else if (ia[i] > secondMax && ia[i] != firstMax) {
	    				secondMax = ia[i];
	    			} else if (firstMax == secondMax) {
	    				secondMax = ia[i];
	    			}
	    		}    		
	        } else {
	            int midpoint = from + (to - from)/2;
	            SecondLargestWithFJ left = new SecondLargestWithFJ(ia, from, midpoint, threshold);
	            SecondLargestWithFJ right = new SecondLargestWithFJ(ia, midpoint + 1, to, threshold);
	            left.fork();
	            right.fork();
	            left.join();
	            right.join();
	            firstMax = Math.max(left.result.first, right.result.first);
	            if (left.result.first < right.result.first)
	            	secondMax = Math.max(left.result.first, right.result.second);
	            else if (left.result.first > right.result.first)
	            	secondMax = Math.max(left.result.second, right.result.first);
	            else
	            	secondMax = Math.max(left.result.second, right.result.second);
	        }
	        
	        result = new Pair(firstMax, secondMax);
	        return result;
	    } // compute
	}
	
	/**
	 * 
	 * find the second largest integer in an array, using fork/join framework. 
	 * 
	 * @param ia
	 *            an array of integers
	 * 
	 * @return the second largest number in ia or a default value of 0
	 * 
	 */		
	public int secondLargestFJ(final int[] ia) {
		int threshold = 100_000;
		int nThreads = Runtime.getRuntime().availableProcessors() * 2;
		
		if (ia.length < 2)
			return 0;
		
		SecondLargestWithFJ mfj = new SecondLargestWithFJ(ia, 0, ia.length, threshold);
		ForkJoinPool pool = new ForkJoinPool(nThreads);
		 
		pool.invoke(mfj);
		Pair result = mfj.result;		
				
		if (result.first == result.second)
			return 0;
		
        return result.second;
	} // secondLargestFJ	

	/**
	 * 
	 * Find all pairs of numbers chosen from ia, such that each pair of numbers adds
	 * up to target.
	 * 
	 * @param ia
	 *            an array of integers
	 * 
	 * @param target
	 *            the target integer
	 * 
	 * @return a List of Pairs of numbers that add up to a specified target
	 * 
	 */
	public List<Pair> findPairs(int[] ia, int target) {
		List<Pair> result = new ArrayList<Pair>();
		Map<Integer, Boolean> pairs = new HashMap<>();
		for (int i = 0; i < ia.length; i++){
			if((long)target - (long)ia[i] < Integer.MIN_VALUE ||
			   (long)target - (long)ia[i] > Integer.MAX_VALUE)
				continue;
			if (pairs.containsKey(ia[i])) {
				if (pairs.get(ia[i]) == false) {
					result.add(new Pair(ia[i], target-ia[i]));
					//Pair(a,b) is not equal Pair(b,a)
					if (ia[i] != target-ia[i])
						result.add(new Pair(target-ia[i], ia[i]));
					pairs.put(ia[i], true);
				} 
			} else 
				pairs.putIfAbsent(target-ia[i], false);
		}
		return result; 
	} // findPairs

	public static class Pair {
		public final int first;
		public final int second;

		public Pair(int first, int second) {
			super();
			this.first = first;
			this.second = second;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + first;
			result = prime * result + second;
			return result;
		} // hashCode

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (first != other.first)
				return false;
			if (second != other.second)
				return false;
			return true;
		} // equals

		@Override
		public String toString() {
			return "(" + this.first + ", " + this.second + ")";
		} // toString
	}

	/**
	 * 
	 * Find all pairs of numbers from pa, such that each both numbers in the pair
	 * are divisible by the divisor.
	 * 
	 * @param pa
	 *            an array of pairs of integers
	 * 
	 * @param divisor
	 *            the divisor integer
	 * 
	 * @return a List of Pairs of integers that are divisible by the divisor
	 *         parameter
	 * 
	 */
	public List<Pair> divisible(Pair[] pa, int divisor) {
		List<Pair> result = new ArrayList<>();
		for (Pair p : pa) {
			if ((p.first % divisor == 0) && (p.second % divisor == 0)) {
				result.add(p);
			}
		}
		return result;
	} // divisible	
}