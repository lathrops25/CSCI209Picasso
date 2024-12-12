package picasso.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.ThreadedCommand;
import picasso.view.commands.Evaluator;
import picasso.view.commands.Reader;
import picasso.view.commands.Writer;

/**
 * Main container for the Picasso application
 *
 * @author Robert Duvall (rcd@cs.duke.edu)
 * @author Jonathan Carranza Cortes
 * @author Naka Assoumatine
 * @author Gabriel Hogan
 * @author Sarah Lathrop
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JTextField textField;
	private Evaluator eval;
	private Reader aFile;
	private RandomExpression randomEx;
	private List<String> history;
	private int historyPTR;

	
	/**
	 * Creates the frame for Picasso
	 * @param size- size of the frame
	 */
	public Frame(Dimension size) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// setting up history
		history = new ArrayList<>();
		historyPTR = -1;

		// create GUI components
		Canvas canvas = new Canvas(this);
		canvas.setSize(size);
		setTitle("CodeCatalysts");

		// create an input text field
		textField = new JTextField(40);
		aFile = new Reader(textField);
		randomEx = new RandomExpression(textField);
		eval = new Evaluator(textField, history);

		// add commands to test here
		ButtonPanel commands = new ButtonPanel(canvas);
		commands.add("Open", new ThreadedCommand<Pixmap>(canvas, aFile));
		commands.add("Evaluate", new ThreadedCommand<Pixmap>(canvas, eval));
		commands.add("Random", new ThreadedCommand<Pixmap>(canvas, randomEx));
		commands.add("Save", new Writer());

		// evaluate when pressing enter
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// getting evaluate button to execute
				history.add(textField.getText());
				JButton evalButton = new JButton();
				evalButton = commands.getButton(1);
				evalButton.doClick();	// simulates clicking the evaluate button
				historyPTR = history.size();
			}
		});

		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					historyPTR--;
					if (historyPTR < 0) {
						historyPTR = 0;
					}
					textField.setText(history.get(historyPTR));
					;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					historyPTR++;
					if (historyPTR >= history.size()) {
						historyPTR = history.size() - 1;
					}
					textField.setText(history.get(historyPTR));
					;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// No action needed, has to be included for KeyListener()
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// no action needed, has to be included for KeyListener()
			}
		});

		// add our container to Frame and show it
		getContentPane().add(canvas, BorderLayout.SOUTH);
		getContentPane().add(commands, BorderLayout.CENTER);
		getContentPane().add(textField, BorderLayout.NORTH);
		pack();
	}

}
