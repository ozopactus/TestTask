package com.sumac;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sumac.MyFunctions.Pair;

public class MyFunctionsTest {
	private MyFunctions mf;
	private Comparator<Pair> comparator;
	
	@Before
	public void setUp() {
		mf = new MyFunctions();
		comparator = (a, b) -> {return a.first==b.first ? (a.second-b.second) : (a.first-b.first);};
	}
	
	@Test
	public void testDivisibleImmutableArg() {
		Pair[] pairs = {new Pair(0, 0), new Pair(-1, 1), new Pair(2, 2), new Pair(3, -3)};
		Pair[] pairs_copy = new Pair[pairs.length];
		System.arraycopy(pairs, 0, pairs_copy, 0, pairs.length);
		mf.divisible(pairs, 11);
		assertArrayEquals(pairs_copy, pairs);
	}
	
	@Test
	public void testDivisibleExpectedResult() {
		Pair[] pairs = {new Pair(0, 0), new Pair(8, 12), new Pair(4, 36), new Pair(6, 20), new Pair(1, 16)};
		List<Pair> expected = new ArrayList<>();
		expected.add(new Pair(0, 0));
		expected.add(new Pair(8, 12));
		expected.add(new Pair(4, 36));
		assertEquals(expected, mf.divisible(pairs, 4));
		
		assertEquals(Arrays.asList(pairs), mf.divisible(pairs, 1));
		
		pairs = new Pair[]{new Pair(-4, -7), new Pair(-2, -16), new Pair(0, 6), new Pair(24, -24)};
		expected.clear();
		expected.add(new Pair(-2, -16));
		expected.add(new Pair(0, 6));
		expected.add(new Pair(24, -24));
		assertEquals(expected, mf.divisible(pairs, -2));
	}
	
	@Test
	public void testDivisibleWhenResultEmpty(){
		Pair[] pairs = {new Pair(2, 6), new Pair(8, 9), new Pair(4, 90), new Pair(10, 10)};
		assertTrue(mf.divisible(pairs, Integer.MAX_VALUE).isEmpty());
		assertTrue(mf.divisible(pairs, Integer.MIN_VALUE).isEmpty());
		
		pairs = new Pair[]{new Pair(-5, 6), new Pair(8, 9), new Pair(4, 91), new Pair(-7, -8)};
		assertTrue(mf.divisible(pairs, 2).isEmpty());
		
		assertTrue(mf.divisible(new Pair[0], 2).isEmpty());
	}
	
	@Test(expected = NullPointerException.class)
	public void testDivisibleDeletingNullPointer() {
		Pair[] pairs = {new Pair(4, 8), null, new Pair(2, 10)};
		mf.divisible(pairs, 2);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDivisibleDivideByZero() {
		Pair[] pairs = {new Pair(1, 1), new Pair(3, 3)};
		mf.divisible(pairs, 0);
	}
	
	@Test
	public void testFindPairsImmutableArg() {
		int[] ia = {9, 4, 7, 1, 3, 8, 5, 9};
		int[] ia_copy = new int[ia.length];
		System.arraycopy(ia, 0, ia_copy, 0, ia.length);
		mf.findPairs(ia, 4);
		assertArrayEquals(ia_copy, ia);
	}
	
	@Test
	public void testSecondLargestImmutableArg() {
		int[] ia = {9, 4, 7, 1, 3, 8, 5, 9};
		int[] ia_copy = new int[ia.length];
		System.arraycopy(ia, 0, ia_copy, 0, ia.length);
		mf.secondLargest(ia);
		assertArrayEquals(ia_copy, ia);
	}
	
	@Test
	public void testSecondLargestFJImmutableArg() {
		int[] ia = {9, 4, 7, 1, 3, 8, 5, 9};
		int[] ia_copy = new int[ia.length];
		System.arraycopy(ia, 0, ia_copy, 0, ia.length);
		mf.secondLargestFJ(ia);
		assertArrayEquals(ia_copy, ia);
	}
	
	@Test
	public void testFindPairsBoundaryValues() {		
		int[] ia = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, 1, 0, -Integer.MAX_VALUE};
		List<Pair> result = mf.findPairs(ia, 0);
		List<Pair> expected = new ArrayList<>();
		expected.add(new Pair(-1, 1));
		expected.add(new Pair(1, -1));
		expected.add(new Pair(0, 0));
		expected.add(new Pair(Integer.MAX_VALUE, -Integer.MAX_VALUE));
		expected.add(new Pair(-Integer.MAX_VALUE, Integer.MAX_VALUE));
		result.sort(comparator);
		expected.sort(comparator);
		assertEquals(expected, result);
		
		expected.clear();
		result = mf.findPairs(ia, Integer.MAX_VALUE);
		expected.add(new Pair(Integer.MAX_VALUE, 0));
		expected.add(new Pair(0, Integer.MAX_VALUE));
		expected.sort(comparator);
		result.sort(comparator);
		assertEquals(expected, result);
	}
	
	@Test
	public void testFindPairsExpectedResult() {		
		int[] ia = {9, 0, 1, 1, 1, 9, 5, 3, 6, 7, 2, 4, 10, 34, 12, -2, -5, -12};
		List<Pair> result = mf.findPairs(ia, 10);
		List<Pair> expected = new ArrayList<>();
		expected.add(new Pair(1, 9));
		expected.add(new Pair(9, 1));
		expected.add(new Pair(0, 10));
		expected.add(new Pair(10, 0));
		expected.add(new Pair(3, 7));
		expected.add(new Pair(7, 3));
		expected.add(new Pair(6, 4));
		expected.add(new Pair(4, 6));
		expected.add(new Pair(-2, 12));
		expected.add(new Pair(12, -2));
		result.sort(comparator);
		expected.sort(comparator);
		assertEquals(expected, result);
	}
	
	@Test
	public void testFindPairsWhenResultEmpty() {		
		int[] ia = {-9, 0, 1, 1, 1, 9, 5, 3, 6, 7, 2, 4, 10, 34, 12, -2, -5, -12};
		assertTrue(mf.findPairs(ia, -88).isEmpty());
	}
	
	@Test
	public void testSecondLargestExpectedResult() {
		int[] ia = {1};
		assertEquals(0, mf.secondLargest(ia));
		
		ia = new int[]{};
		assertEquals(0, mf.secondLargest(ia));
		
		ia = new int[]{1, 1};
		assertEquals(0, mf.secondLargest(ia));
		
		ia = new int[]{1, 1, 1, 1, 1, 1};
		assertEquals(0, mf.secondLargest(ia));
		
		ia = new int[]{1, 2, 3, 3, 3, 2, 1};
		assertEquals(2, mf.secondLargest(ia));
		
		ia = new int[]{9, 9, 9, 9, 9, 9, 1};
		assertEquals(1, mf.secondLargest(ia));
		
		ia = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE};
		assertEquals(Integer.MIN_VALUE, mf.secondLargest(ia));
		
		ia = new int[]{-2, -3, -4, -4, -2, -1, -1};
		assertEquals(-2, mf.secondLargest(ia));
		
		ia = new int[]{0, 1, 2, 3};
		assertEquals(2, mf.secondLargestFJ(ia));
		
		ia = new int[]{3, 2, 1, 0};
		assertEquals(2, mf.secondLargestFJ(ia));
	}
	
	@Test
	public void testSecondLargestFJExpectedResult() {
		int[] ia = {1};
		assertEquals(0, mf.secondLargestFJ(ia));
		
		ia = new int[]{};
		assertEquals(0, mf.secondLargestFJ(ia));
		
		ia = new int[]{1, 1};
		assertEquals(0, mf.secondLargestFJ(ia));
		
		ia = new int[]{1, 1, 1, 1, 1, 1};
		assertEquals(0, mf.secondLargestFJ(ia));
		
		ia = new int[]{1, 2, 3, 3, 3, 2, 1};
		assertEquals(2, mf.secondLargestFJ(ia));
		
		ia = new int[]{9, 9, 9, 9, 9, 9, 1};
		assertEquals(1, mf.secondLargestFJ(ia));
		
		ia = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE};
		assertEquals(Integer.MIN_VALUE, mf.secondLargestFJ(ia));
		
		ia = new int[]{-2, -3, -4, -4, -2, -1, -1};
		assertEquals(-2, mf.secondLargestFJ(ia));
		
		ia = new int[]{0, 1, 2, 3};
		assertEquals(2, mf.secondLargestFJ(ia));
		
		ia = new int[]{3, 2, 1, 0};
		assertEquals(2, mf.secondLargestFJ(ia));
	}
	
	@Test(expected = NullPointerException.class)
	public void testSecondLargestArrayNullPointer() {
		int[] ia = null;
		mf.secondLargest(ia);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSecondLargestFJArrayNullPointer() {
		int[] ia = null;
		mf.secondLargestFJ(ia);
	}
}
