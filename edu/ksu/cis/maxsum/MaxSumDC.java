/*
 * MaxSumDC.java       May 16, 2007
 *
 * Copyright (c) 2003, 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

/**
 * Class implementing a maximum subsequence sum algorithm that performs
 * the computation in a divide-and-conquer fashion.  The running time is in 
 * O(n lg n), where n is the number of elements in the array.
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public class MaxSumDC implements MaxSumInterface {
	
	public int maxSum(int[] a) {
		if (a.length == 0) return 0;
		else return maxSum(a, 0, a.length-1);
	}
	
	/**
	 * Computes the maximum subsequence sum of a[lo..hi]. It must be the case
	 * that lo <= hi.
	 * 
	 * @param a  The array over which the maximum subsequence sum is to be found.
	 * @param lo The index of the first element in the segment considered.
	 * @param hi The index of the last element in the segment considered.
	 * @return   The maximum subsequence sum of a[lo..hi].
	 */
	private int maxSum(int[] a, int lo, int hi) {
		if (lo == hi) return Math.max(0, a[lo]);
		else {
			int mid = (lo + hi)/2;
			int mid1 = mid + 1;
			int sum1 = maxSum(a, lo, mid);
			int sum2 = maxSum(a, mid1, hi);
			int sum3 = maxSuffix(a, lo, mid) + maxPrefix(a, mid1, hi);
			return Math.max(Math.max(sum1, sum2), sum3);
		}
	}
	
	/**
	 * Computes the maximum suffix sum of a[lo..hi]. It must be the case that
	 * lo <= hi.
	 * 
	 * @param a  The array over which the maximum suffix sum is to be found.
	 * @param lo The index of the first element of the segment considered.
	 * @param hi The index of the last element of the segment considered.
	 * @return   The maximum suffix sum of a[lo..hi].
	 */
	private int maxSuffix(int[] a, int lo, int hi) {
		int m = 0;
		// Invariant: m is the maximum suffix sum for a[lo..i-1]
		for (int i = lo; i <= hi; i++) {
			m = Math.max(0, m + a[i]);
		}
		return m;
	}
	
	/**
	 * Computes the maximum prefix sum of a[lo..hi]. It must be the case that
	 * lo <= hi.
	 * 
	 * @param a  The array over which the maximum prefix sum is to be found.
	 * @param lo The index of the first element of the segment considered.
	 * @param hi The index of the last element of the segment considered.
	 * @return   The maximum prefix sum of a[lo..hi].
	 */
	private int maxPrefix(int[] a, int lo, int hi) {
		int m = 0;
		// Invariant: m is the maximum prefix sum for a[i+1..hi]
		for (int i = hi; i >= lo; i--) {
			m = Math.max(0, m + a[i]);
		}
		return m;
	}
	
}
