/* MaxSum.java   5/16/07
 * 
 * Copyright (c) 2003, 2007 Rod Howell.
 * 
 * This program compares the running times of 5 algorithms for finding the
 * maximum subsequence sum of an array of integers.  
 */

package edu.ksu.cis.maxsum;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A class containing a GUI for comparing algorithms for finding the maximum
 * subsequence sum of an array of integers.  The GUI allows the user to generate
 * or select a data set, then run it on any of the five algorithms presented in
 * <i>Algorithms: A Top-Down Approach</i> (R. Howell). 
 *
 * After one of the algorithms is run, a dialog box is shown giving the maximum
 * subsequence sum and the amount of time taken for the computation.
 */
public class MaxSum extends JFrame {

  /**
   * The algorithms to be tested.
   */
  private static MaxSumInterface[] algorithms = new MaxSumInterface[] {
    new MaxSumIter(), new MaxSumOpt(), new MaxSumTD(), new MaxSumDC(), new MaxSumBU()};
  
  /**
   * The cursor to use when a potentially long operation is being performed.
   */
  private static final Cursor BUSY_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
  
  /**
   * The normal cursor.
   */
  private static final Cursor NORMAL_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
  
  /**
   * The array containing the data for which the maximum subsequence sum is to be
   * found. It is initially of length 0.
   */
  private int[] data = new int[0];
  
  /**
   * The maximum value allowed in the data set. The minimum is the negative of this value.
   */
  private int max = 1;
  
  /**
   * <tt>true</tt> iff a seed has been specified for the random number generator.
   */
  private boolean seedGiven = false;
  
  /**
   * The seed for the random number generator.
   */
  private int seed;
  
  /**
   * The number of the current data set.
   */
  private int dataNum = 0;
  
  /**
   * The text field for displaying the size of the current data set.
   */
  private JTextField sizeField = new JTextField("0", 10);
  
  /**
   * The component displaying the possible algorithms to run.
   */
  private JComboBox<String> choices 
  	= new JComboBox<>(new String[] {"MaxSumIter", "MaxSumOpt", "MaxSumTD", "MaxSumDC", 
  									"MaxSumBU"});
  
  /**
   * Constructs a new GUI.
   *
   */
  public MaxSum() {
  	super("Maximum Subsequence Sum Timer");
  	try {
  		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  	}
  	catch (Exception e) {
  		// This shouldn't happen
  		e.printStackTrace();
  	}
  	
  	Container content = getContentPane();
  	content.setLayout(new GridLayout(3, 1));
  	
  	JPanel p = new JPanel();
  	p.add(new Label("Size of array:"));
  	sizeField.setEditable(false);
  	p.add(sizeField);
  	content.add(p);
  	
  	p = new JPanel();
  	p.add(new Label("Algorithm:"));
  	p.add(choices);
  	content.add(p);
  	
  	p = new JPanel();
  	JButton b = new JButton("Generate Data...");
  	b.addActionListener(new NewDataListener(this));
  	p.add(b);
  	b = new JButton("View Data...");
  	b.addActionListener(new ViewListener(this));
  	p.add(b);
  	b = new JButton("Run Algorithm");
  	b.addActionListener(new StartButtonListener(this));
  	p.add(b);
  	content.add(p);
  	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	pack();
  }

  /**
   * Starts the program.
   * @param args The command-line arguments (ignored).
   */
  public static void main(String args[]) {
  	MaxSum frame = new MaxSum();
  	frame.setVisible(true);
  }
  
  /**
   * Runs the currently-selected algorithm on the current data set.
   *
   */
  void run() {
	setCursor(BUSY_CURSOR);
  	MaxSumInterface alg = algorithms[choices.getSelectedIndex()];
  	long start = System.currentTimeMillis();
  	try {
  		int ms = alg.maxSum(data);
  	  	long time = System.currentTimeMillis() - start;
  	  	setCursor(NORMAL_CURSOR);
  	  	JOptionPane.showMessageDialog(this, "Max sum = " + ms + "; time = " 
  	  			                      + (time/1000.0) + " seconds.", "Summary", 
									  JOptionPane.INFORMATION_MESSAGE);
  	}
  	catch (Throwable e) {
  	  	setCursor(NORMAL_CURSOR);
  		showError(e);
  	}
  }
  
  /**
   * Generates a new data set using parameters obtained from the user.
   *
   */
  void newData() {
  	GenerateDialog d = new GenerateDialog(this, data.length, max, seedGiven, seed);
  	d.setVisible(true);
  	if (d.status() == GenerateDialog.OK) {
  		try {
  			// Don't save anything permanently until we've generated the entire data set
  			// (in case something goes wrong)
  			int n = d.getNum();
  			int max = d.getMax();
  			boolean seedGiven = d.isSeedGiven();
  			int seed = d.getSeed();
  			Random r = seedGiven ? new Random(seed) : new Random();
  			int lim = 2*max + 1;
  			setCursor(BUSY_CURSOR);
  			int[] a = new int[n];
  			dataNum++;
  			for (int i = 0; i < n; i++) {
  				a[i] = r.nextInt(lim) - max;
  			}
  			sizeField.setText(Integer.toString(n));
  			data = a;
  			this.max = max;
  			this.seedGiven = seedGiven;
  			this.seed = seed;
  			setCursor(NORMAL_CURSOR);
  		}
  		catch (Throwable e) {
  			setCursor(NORMAL_CURSOR);
  			showError(e);
  		}
  	}
  }
  
  /**
   * Displays the given object as an error message.
   * @param msg The message to be displayed.
   */
  private void showError(Object msg) {
  	JOptionPane.showMessageDialog(this, msg, "Error",
  			JOptionPane.WARNING_MESSAGE);
  	
  }
  
  /**
   * Displays the data in a scrolling list in a new window.
   *
   */
  void view() {
  	try {
  		setCursor(BUSY_CURSOR);
  		JFrame d = new JFrame("Data Set " + dataNum);
  		Container content = d.getContentPane();
  		JList<Integer> lst = new JList<>(new DisplayModel(data));
  		lst.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
  		JScrollPane p = new JScrollPane(lst, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
  				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  		content.add(p);
  		d.pack();
  		d.setVisible(true);
  		setCursor(NORMAL_CURSOR);
  	}
  	catch (Throwable e) {
  		setCursor(NORMAL_CURSOR);
  		showError(e);
  	}
  }
}

/**
 * Event-handler for the "Generate Data..." button.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class NewDataListener implements ActionListener {
	
	/**
	 * The MaxSum object containing the button.
	 */
	private MaxSum parent;
	
	/**
	 * Constructs a new event handler.
	 * @param p The MaxSum object containing the button.
	 */
	public NewDataListener(MaxSum p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the MaxSum object to generate a new data set.
	 */
	public void actionPerformed(ActionEvent e) {
		parent.newData();
	}
}

/**
 * Event handler for the "Run Algorithm" button.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class StartButtonListener implements ActionListener {
	
	/**
	 * The MaxSum object containing the button.
	 */
	private MaxSum parent;
	
	/**
	 * Constructs a new event handler.
	 * @param p The MaxSum object containing the button.
	 */
	public StartButtonListener(MaxSum p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the MaxSum object to run its currently-selected
	 * algorithm on its current data set.
	 */
	public void actionPerformed(ActionEvent e) {
		parent.run();
	}
}

/**
 * Event handler for the "View Data..." button.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class ViewListener implements ActionListener {
	
	/**
	 * The MaxSum that contains the button.
	 */
	private MaxSum parent;
	
	/**
	 * Constructs a new event handler.
	 * 
	 * @param p The MaxSum that contains the button.
	 */
	public ViewListener(MaxSum p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the MaxSum to display the data.
	 */
	public void actionPerformed(ActionEvent e) {
		parent.view();
	}
}

/**
 * A ListModel for displaying an int array that won't change.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 *
 */
class DisplayModel implements ListModel<Integer> {
	
	/**
	 * The list values.
	 */
	private Integer[] data;
	
	/**
	 * Constructs a new DisplayModel.
	 * 
	 * @param d The array of values to display.
	 */
	public DisplayModel(int[] d) {
		data = new Integer[d.length];
		for (int i = 0; i < d.length; i++) {
			data[i] = Integer.valueOf(d[i]);
		}
	}
	
	public void addListDataListener(ListDataListener l) {
		// Data won't change, so we'll ignore this.
	}
	
	public Integer getElementAt(int index) {
		return data[index];
	}
	
	public int getSize() {
		return data.length;
	}
	
	public void removeListDataListener(ListDataListener l) {
		// Data won't change, so we'll ignore this.
	}
}
