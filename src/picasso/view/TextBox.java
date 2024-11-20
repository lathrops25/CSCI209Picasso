package picasso.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import picasso.model.Pixmap;
import picasso.util.Command;
import picasso.util.NamedCommand;
import picasso.util.ThreadedCommand;
import picasso.view.commands.Evaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Creating a text box, adapted from Java Documentation on using Text Fields
 * https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html 
 * @author Jonathan Carranza Cortes
 */

public class TextBox extends JFrame{
	
	// text elements
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	// keeping track of our canvas
	private Canvas canvas;
	private Evaluator evaluator;
	
	/**
	 * Constructor to create the contents of a new input window
	 */
	public TextBox() {		
		
		canvas = new Canvas(this);
		evaluator = new Evaluator();
		
		// Creating the textbox dimensions
		setTitle("Input GUI");
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout()); //<- possibly change this later
		
		textField = new JTextField(40);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = textField.getText();
	
				// print out input expression on the new GUI
				textArea.append(text + "\n");
				textField.selectAll();
				
				// Make sure the new text is visible, even if there was a selection in the text area
				textArea.setCaretPosition(textArea.getDocument().getLength());
				canvas.receiveInputString(text);
				evaluator.receiveInputString(text);

			}
		});
		add(textField);
		
		// everything below here is to show the full input!
		textArea = new JTextArea(20, 40);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		add(textArea);
		
		scrollPane = new JScrollPane(textArea);
		add(scrollPane);
		
	}
	

}