/*
 * MaxSumInterface.java       May 16, 2007
 *
 * Copyright (c) 2003, 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

/**
 * The interface for the classes containing algorithms for the maximum
 * subsequence sum.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public interface MaxSumInterface {

	/**
	 * Returns the maximum subsequence sum of the given array.
	 * 
	 * @param a The array over which the maximum subsequence sum is to be
	 *          computed.
	 * @return  The maximum subsequence sum of a.
	 */
	public int maxSum(int[] a);
	
}
