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
import picasso.view.commands.RandomExpression;
import picasso.view.commands.Reader;
import picasso.view.commands.Writer;

/**
 * Main container for the Picasso application
 *
 * @author Robert Duvall (rcd@cs.duke.edu)
 * @author Jonathan Carranza Cortes
 * @author Naka Assoumatine
 * @author Gabriel Hogan
 * @author Allison Hidalgo
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
	private ButtonPanel commands;

	/**
	 * Creates the frame for Picasso
	 * 
	 * @param size- size of the frame
	 */
	public Frame(Dimension size, Pixmap initialImage) {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// setting up history
		history = new ArrayList<>();
		historyPTR = -1;

		// create GUI components
		Canvas canvas = new Canvas(this);
		canvas.setSize(size);
		setTitle("CodeCatalysts");

		// initial image
		if (initialImage != null) {
			canvas.setImage(initialImage);
		}

		// create an input text field
		textField = new JTextField(40);
		aFile = new Reader(textField);
		randomEx = new RandomExpression(textField);
		eval = new Evaluator(textField, history);

		// add commands to test here
		commands = new ButtonPanel(canvas);
		commands.add("Open", new ThreadedCommand<Pixmap>(canvas, aFile));
		commands.add("Evaluate", new ThreadedCommand<Pixmap>(canvas, eval));
		commands.add("Random", new ThreadedCommand<Pixmap>(canvas, randomEx));
		commands.add("Save", new Writer());

		// New button panel for an empty panel
		JButton newPanelButton = new JButton("New Panel");
		newPanelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewFrame(size);
			}
		});
		commands.add("New Panel", newPanelButton);

		// New button for evaluating an expression in the text box to a new panel
//		Functionality moved to the database viewer

//		JButton evaluateNewPanelButton = new JButton("Evaluate in New Panel");
//		evaluateNewPanelButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Frame.this.evaluateInNewPanel(size);
//			}
//		});
//		commands.add("Evaluate in New Panel", evaluateNewPanelButton);

		// evaluate when pressing enter
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// getting evaluate button to execute
				history.add(textField.getText());
				JButton evalButton = new JButton();
				evalButton = commands.getButton(1);
				evalButton.doClick(); // simulates clicking the evaluate button
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

	/**
	 * single argument constructor for compatibility
	 * 
	 * @param size
	 */
	public Frame(Dimension size) {
		this(size, null);
	}

	/**
	 * This creates a new frame with the same dimensions
	 * 
	 * @pram new frame size
	 */
	private void createNewFrame(Dimension size) {
		Frame newFrame = new Frame(size);
		newFrame.setLocationRelativeTo(null);
		newFrame.setVisible(true);
	}

	/**
	 * This is taking the input and opening a new frame with the expression
	 * evaluated
	 * 
	 * @param size of new frame
	 */
	public void evaluateInNewPanel(Dimension size) {
		if (textField.getText().isBlank()) {
			System.out.println("No expression provided");
			return;
		}

		try {
			Pixmap evaluatedImage = new Pixmap(size.width, size.height);
			eval.execute(evaluatedImage);
			Frame newFrame = new Frame(size, evaluatedImage);
			newFrame.setLocationRelativeTo(null);
			newFrame.setVisible(true);
		} catch (Exception e) {
			System.out.println("Failed to evaluate expression: " + e.getMessage());
		}
	}

	/**
	 * This sets the expression in the text field and evaluates it
	 * 
	 * @param expression
	 * @param evaluate
	 */
	public void setExpression(String expression, boolean evaluate) {
		textField.setText(expression);

		if (evaluate) {
			history.add(expression);
			JButton evalButton = new JButton();
			evalButton = commands.getButton(1);
			evalButton.doClick();
			historyPTR = history.size();
		}

	}

	/**
	 * This sets the expression in the text field
	 * 
	 * @param expression
	 */
	public void setExpression(String expression) {
		this.setExpression(expression, false);
	}
}
