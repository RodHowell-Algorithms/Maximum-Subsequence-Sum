/*
 * MaxSumIter.java       May 16, 2007
 *
 * Copyright (c) 2003, 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

/**
 * Class implementing a maximum subsequence sum algorithm that performs
 * the computation using a direct implementation of the definition.  The running 
 * time is in O(n^3), where n is the number of elements in the array.
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public class MaxSumIter implements MaxSumInterface {
	
	public int maxSum(int[] a) {
		int m = 0;
		for (int i = 0; i <= a.length; i++) {
			for (int j = i; j <= a.length; j++) {
				int sum = 0;
				for (int k = i; k < j; k++) {
					sum += a[k];
				}
				m = Math.max(m, sum);
			}
		}
		return m;
	}
}
