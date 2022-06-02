/*
 * MaxSumBU.java       May 16, 2007
 *
 * Copyright (c) 2003, 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

/**
 * Class implementing a maximum subsequence sum algorithm that performs
 * the computation in a bottom-up fashion.  The running time is in O(n),
 * where n is the number of elements in the array.
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public class MaxSumBU implements MaxSumInterface {
	
	public int maxSum(int[] a) {
		int m = 0;
		int msuf = 0;
		// Invariant: m is the maximum subsequence sum for a[0..i-1],
		//            msuf is the maximum suffix sum for a[0..i-1]
		for (int i = 0; i < a.length; i++) {
			msuf = Math.max(0, msuf + a[i]);
			m = Math.max(m, msuf);
		}
		return m;
	}
	
}
