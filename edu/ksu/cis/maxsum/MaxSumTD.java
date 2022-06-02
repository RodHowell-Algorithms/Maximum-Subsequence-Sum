/*
 * MaxSumTD.java       May 16, 2007
 *
 * Copyright (c) 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

/**
 * Class implementing a maximum subsequence sum algorithm that performs
 * the computation in a top-down fashion.  The running time is in O(n^2),
 * where n is the number of elements in the array.  The stack usage is in
 * O(n).
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public class MaxSumTD implements MaxSumInterface {

	  public int maxSum(int[] a) {
	    return maxSum(a, a.length);
	  }

	  /**
	   * Computes the maximum subsequence sum of a[0..n-1].
	   * 
	   * @param a The array over which the maximum subsequence sum is to
	   *          be computed.
	   * @param n The length of the segment considered.
	   * @return  The maximum subsequence sum of a[0..n-1].
	   */
	  private int maxSum(int[] a, int n) {
	    if (n == 0) return 0;
	    else return Math.max(maxSum(a, n-1), maxSuffix(a, n));
	  }

	  /**
	   * Computes the maximum suffix sum of a[0..n-1].
	   * 
	   * @param a The array over which the maximum suffix sum is to
	   *          be computed.
	   * @param n The length of the segment considered.
	   * @return  The maximum suffix sum of a[0..n-1].
	   */
	  private int maxSuffix(int[] a, int n) {
	    if (n == 0) return 0;
	    else return Math.max(0, a[n-1] + maxSuffix(a, n-1));
	  }

}
