# Maximum Subsequence Sum

*Algorithms: A Top-Down Approach* (R. Howell) describes five algorithms (four in Chapter 1 and one in Chapter 3) for solving the maximum subsequence sum problem, and reports the results of timing tests of implementations of each of them. This repository contains the source code used to conduct these tests, as well as an executable JAR file (`maxsum.jar`).

## Algorithm Implementations

All five algorithms are coded in the Java<sup>TM</sup> language. A driver generates random test data and runs selected algorithms on the given data set. When an algorithm completes, the program reports the time required by the algorithm.

## Running the Program

The easiest way to run the program is to download `maxsum.jar`. Provided you have the [Java Runtime Environment (JRE)](https://www.java.com/en/download/manual.jsp) installed, you can typically run this file simply by opening it. You can also run it from a command line as follows:
<pre>
java -jar maxsum.jar
</pre>
If you want to test the algorithms on very large data sets, you may need additional heap space. To get this, use the `-Xmx` option; e.g., to set the maximum heap size to 8 gigabytes:
<pre>
java -Xmx8g -jar maxsum.jar
</pre>

### Generating data

Upon pressing the "Generate Data..." button, you will be presented with a GUI for providing the parameters for generating data.
* **Size of the array:** The number of elements in the array to be passed to the algorithm(s). This can be any nonnegative integer less than 2<sup>31</sup> = 2,147,483,648 (note, however, the caution below). In most cases, the Java Virtual Machine will not have a large enough heap to store an array whose size is near the maximum allowable size. If you try to generate a data set that will not fit in the heap, you will generate a **java.lang.OutOfMemoryError**, and your previous data set will not be replaced. You may be able to generate a somewhat larger data set by first generating a data set of size 0 to cause the program to discard your current data set.
* **Max absolute value:** The upper limit on values generated. This can be any positive integer less than 2<sup>30</sup> = 1,073,741,824. The lower limit will be the negative of this value. Note that if this value is too large, overflow can cause the different algorithms to produce as many as 3 different results (try, for example, a data set of size 10, a max of 1,000,000,000, and a seed of 7); however, this should not affect the timing. Choosing a value no more than 10,000 should avoid overflow.
* **Seed (optional):** The seed for the random number generator. This can be any integer that is at least -2<sup>31</sup> and less than 2<sup>31</sup>, or it can be left blank. Choosing a seed is useful if you want to use the same data set on different occasions. If you don't choose a seed, each data set you generate will probably be different.

You may re-examine these parameters at any time by pressing the "Generate Data..." button. If you don't wish to generate a new data set, simply press the "Cancel" button.

### Viewing the data

Upon pressing the "View Data..." button, a window lising the elements of the array will be shown. If you would like to save these values to a file, you can use your system's copy/paste facility to copy them to a text editor. For a large data set, attempting to view the data may generate a **java.lang.OutOfMemoryError**.

### Selecting an algorithm

One of the five algorithms can be selected using the drop-down menu labeled "Algorithm:".

### Running an algorithm

Pressing the "Run Algorithm" button will cause the selected algorithm to be run on the current data set. When the algorithm finishes, the maximum subsequence sum and the time required will be displayed.

**Caution:** There is no facility within the program for aborting an algorithm. As a result, you may need to force-stop execution if the algorithm is taking a long time. Furthermore, the running times of some of these algorithms increase rather dramatically. A recommended approach is to start a given algorithm on a data set of size 1000, then as long as the running time is no more that 0.1 seconds, keep multiplying the size by 10. Once the running time exceeds 0.1 seconds, and as long as it is no more than 15 seconds, keep multiplying the size by 2. Proceeding in this way should keep all execution times below 2 minutes.

Note also that it is normal for `MaxSumTD` to generate a **java.lang.StackOverflowError** on arrays of moderate size. This algorithm is tail-recursive, and hence uses a lot of stack space.

## Compiling the Code

If you wish to modify the code, you will need to download a copy, either by cloning it with `git` or by downloading and decompressing a ZIP archive (see the green "Code" button). To compile the code, assuming you have the [Java Development Kit (JDK)](https://www.java.com/en/download/manual.jsp) installed, enter the following from a command line within the root folder of the project (i.e., the one containing a single subfolder, `edu`):
<pre>
javac edu/ksu/cis/maxsum/*.java
</pre>
(Depending on your shell, you may need to replace each '/' with '\\'.) To run the program after compiling it:
<pre>
java edu.ksu.cis.maxsum.MaxSum
</pre>

### Source code files

All source code files are in the folder `edu/ksu/cis/maxsum/`. The five algorithms are in the following files:
* `MaxSumIter.java`
* `MaxSumOpt.java`
* `MaxSumTD.java`
* `MaxSumDC.java`
* `MaxSumBU.java`

The remaining files are:
* `MaxSum.java` - the main driver and GUI
* `GenerateDialog.java` - the dialog for obtaining parameters for test data generation
* `MaxSumInterface.java` - interface implemented by each of the five classes containing maximum subsequence sum algorithms
