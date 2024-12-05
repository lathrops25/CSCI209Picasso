package picasso.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.ThreadedCommand;
import picasso.view.commands.*;

/**
 * Main container for the Picasso application
 *
 * @author Robert Duvall (rcd@cs.duke.edu)
 * @author Jonathan Carranza Cortes
 * @author Naka Assoumatine
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JTextField textField;
	private Evaluator eval;
	private Reader aFile;

	public Frame(Dimension size) {


		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// create GUI components
		Canvas canvas = new Canvas(this);
		canvas.setSize(size);
		setTitle("CodeCatalysts");
		
		// create an input text field
		textField = new JTextField(40);
		aFile = new Reader(textField);
		eval = new Evaluator(textField);
		
		// add commands to test here
		ButtonPanel commands = new ButtonPanel(canvas);
		commands.add("Open", new ThreadedCommand<Pixmap>(canvas,aFile));
		commands.add("Evaluate", new ThreadedCommand<Pixmap>(canvas, eval));
		commands.add("Save", new Writer());
		
		// evaluate when pressing enter
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// getting evaluate button to execute
				JButton evalButton = new JButton();
				evalButton = commands.getButton(1);
				evalButton.doClick();	// simulates clicking the evaluate button
			}
		});
		
		// add our container to Frame and show it
		getContentPane().add(canvas, BorderLayout.SOUTH);
		getContentPane().add(commands, BorderLayout.CENTER);
		getContentPane().add(textField, BorderLayout.NORTH);
		pack();
	}
	
}
