/*
 * GenerateDialog.java       May 17, 2007
 *
 * Copyright (c) 2007, Rod Howell, all rights reserved.
 */
package edu.ksu.cis.maxsum;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Dialog for obtaining parameters for generating a random data set.
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
public class GenerateDialog extends JDialog {
	
	/**
	 * The user closed the dialog by clicking "OK".
	 */
	public static final int OK = 1;
	
	/**
	 * The user canceled the operation.
	 */
	public static final int CANCEL = -1;
	
	/**
	 * The maximum size of a data set or value of a seed.
	 */
	public static final int MAX_SIZE = 0x7fffffff;
	
	/**
	 * The maximum value for the upper limit of the range of values to be
	 * generated.
	 */
	public static final int MAX_MAX = 0x3fffffff;
	
	/**
	 * The minimum value for the seed.
	 */
	public static final int MIN_SEED = 0x80000000;
	
	/**
	 * Error message to be displayed when an invalid entry is made in the "Size"
	 * field.
	 */
	private static final String SIZE_ERROR = 
		"The size must be a nonnegative integer no more than " + MAX_SIZE + ".";
	
	/**
	 * Error message to be displayed when an invalid entry is made in the "Max"
	 * field.
	 */
	private static final String MAX_ERROR = 
		"The max must be a positive integer no more than " + MAX_MAX + ".";
	
	/**
	 * Error message to be displayed when an invalid entry is made in the "Seed"
	 * field.
	 */
	private static final String SEED_ERROR = 
		"The seed must be an integer at least " + MIN_SEED + " and at most " + MAX_SIZE;
	
	/**
	 * The field for obtaining the size of the data set.
	 */
	private JTextField sizeField = new JTextField("", 10);
	
	/**
	 * The field for obtaining the upper limit of values to be generated.
	 */
	private JTextField maxField = new JTextField("", 10);
	
	/**
	 * The field for obtaining the seed for the random number generator.
	 */
	private JTextField seedField = new JTextField("", 10);
	
	/**
	 * The number of elements in the data set.
	 */
	private int size;
	
	/**
	 * The upper limit of values to be generated.  The lower limit is the negative of
	 * this value.
	 */
	private int max;
	
	/**
	 * <tt>true</tt> iff the user has given a seed.
	 */
	private boolean seedGiven;
	
	/**
	 * The seed for the random number generator.
	 */
	private int seed;
	
	/**
	 * The exit status - either OK or CANCEL.
	 */
	private int exitStatus;
	
	/**
	 * Constructs a new dialog.
	 * @param p The MaxSum object requesting the information.
	 * @param size The default size.
	 * @param max  The default maximum value.
	 * @param seedGiven <tt>true</tt> iff there is a default seed.
	 * @param seed The default seed.
	 */
	public GenerateDialog(MaxSum p, int size, int max, boolean seedGiven, int seed) {
		super(p, "Generate Data Set", true);
		this.size = size;
		this.max = max;
		this.seedGiven = seedGiven;
		this.seed = seed;
		
		Container content = getContentPane();
		content.setLayout(new GridLayout(4,1));
		
		JPanel pan = new JPanel();
		pan.add(new Label("Size of array:"));
		sizeField.setText(Integer.toString(size));
		pan.add(sizeField);
		content.add(pan);
		
		pan = new JPanel();
		pan.add(new Label("Max absolute value:"));
		maxField.setText(Integer.toString(max));
		pan.add(maxField);
		content.add(pan);
		
		pan = new JPanel();
		pan.add(new Label("Seed (optional):"));
		if (seedGiven) {
			seedField.setText(Integer.toString(seed));
		}
		pan.add(seedField);
		content.add(pan);
		
		pan = new JPanel();
		JButton b = new JButton("OK");
		b.addActionListener(new AcceptListener(this));
		pan.add(b);
		b = new JButton("Cancel");
		b.addActionListener(new CancelListener(this));
		pan.add(b);
		content.add(pan);
		
		addWindowListener(new CloseListener(this));
		sizeField.requestFocus();
		pack();
	}
	
	/**
	 * Returns the specified size of the data set.
	 * @return The specified size.
	 */
	public int getNum() {
		return size;
	}
	
	/**
	 * Returns the exit status.
	 * @return The exit status - either OK or CANCEL.
	 */
	public int status() {
		return exitStatus;
	}
	
	/**
	 * Returns the specified upper limit for values generated.
	 * @return The specified upper limit.
	 */
	public int getMax() {
		return max;
	}
	
	/**
	 * Returns <tt>true</tt> iff the user specified a seed.
	 * @return <tt>true</tt> iff the user specified a seed.
	 */
	public boolean isSeedGiven() {
		return seedGiven;
	}
	
	/**
	 * Returns the seed specified by the user.
	 * @return The seed specified, or 0 if no seed was specified.
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * Check the input for validity and close dialog. If there is an invalid input
	 * display a message and leave the dialog open.
	 *
	 */
	void accept() {
		try {
			size = Integer.parseInt(sizeField.getText());
			if (size < 0) {
				showError(SIZE_ERROR);
				sizeField.requestFocus();
				return;
			}
			try {
				max = Integer.parseInt(maxField.getText());
				if (max <= 0 || max > MAX_MAX) {
					showError(MAX_ERROR);
					maxField.requestFocus();
					return;
				}
				String s = seedField.getText();
				if (s.length() > 0) {
					try {
						seed = Integer.parseInt(s);
						seedGiven = true;
					}
					catch (NumberFormatException badSeed) {
						showError(SEED_ERROR);
						seedField.requestFocus();
						return;
					}
				}
				else seedGiven = false;
			}
			catch (NumberFormatException badMax) {
				showError(MAX_ERROR);
				maxField.requestFocus();
				return;
			}
		}
		catch (NumberFormatException badSize) {
			showError(SIZE_ERROR);
			sizeField.requestFocus();
			return;
		}
		exitStatus = OK;
		dispose();
	}
	
	/**
	 * Display the given error message.
	 * @param msg The message to be displayed.
	 */
	private void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Input Error", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Close the dialog with CANCEL status.
	 *
	 */
	void cancel() {
		exitStatus = CANCEL;
		dispose();
	}
}

/**
 * Event handler for the "OK" button.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class AcceptListener implements ActionListener {
	
	/**
	 * The GenerateDialog containing the button.
	 */
	private GenerateDialog parent;
	
	/**
	 * Constructs a new event handler.
	 * @param p The GenerateDialog containing the button.
	 */
	public AcceptListener(GenerateDialog p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the GenerateDialog to verify the input and close.
	 */
	public void actionPerformed(ActionEvent e) {
		parent.accept();
	}
}

/**
 * Event handler for the "Cancel" button.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class CancelListener implements ActionListener {
	
	/**
	 * The GenerateDialog containing the button.
	 */
	private GenerateDialog parent;
	
	/**
	 * Constructs a new event handler.
	 * @param p The GenerateDialog containing the button.
	 */
	public CancelListener(GenerateDialog p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the GenerateDialog to close with CANCEL status.
	 */
	public void actionPerformed(ActionEvent e) {
		parent.cancel();
	}	
}

/**
 * Event handler for window closing events.
 * 
 *
 * @author Rod Howell
 *         (<a href="mailto:rhowell@ksu.edu">rhowell@ksu.edu</a>)
 *
 */
class CloseListener extends WindowAdapter {
	
	/**
	 * The GenerateDialog containing the button.
	 */
	private GenerateDialog parent;
	
	/**
	 * Constructs a new event handler.
	 * @param p The GenerateDialog to be closed.
	 */
	public CloseListener(GenerateDialog p) {
		parent = p;
	}
	
	/**
	 * Handles the event by asking the GenerateDialog to close with CANCEL status.
	 */
	public void windowClosing(WindowEvent e) {
		parent.cancel();
	}		
}