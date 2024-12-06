package picasso.view.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.FileCommand;
import picasso.view.ErrorDialog;

/**
 * Open the chosen image file and display in the Pixmap target.
 * 
 * 
 * @author Robert C Duvall
 * @author Naka Assoumatine
 */
public class Reader extends FileCommand<Pixmap> {
	
	private JTextField textIn;

	/**
	 * Creates a Reader object, which prompts users for image files to open
	 */
	public Reader(JTextField textIn) {
		super(JFileChooser.OPEN_DIALOG);
		this.textIn = textIn;
	}

	/**
	 * Displays the image file on the given target.
	 */
	public void execute(Pixmap target) {
		String fileName = getFileName();
		
		System.out.println(fileName);
		
		if (fileName != null) {
			if (!fileName.contains(".exp")) {
				target.read(fileName);
			}
			else {
				StringBuilder inputExpression = new StringBuilder();
				BufferedReader fileReader;
				
				try {
					fileReader = new BufferedReader(new FileReader(fileName));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					// !! TODO: Temporary Error Display
					ErrorDialog.showDialog("There was am error reading that file: " + e.getMessage());
					return;
				}
			
				
				try {
					String line = fileReader.readLine();
				
					while (line != null) {
						if (!line.contains("//")) {
							inputExpression.append(line);		
						}
						
						line = fileReader.readLine();
						}
					
					fileReader.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					// !! TODO: Temporary Error Display
					ErrorDialog.showDialog("There was am error reading that file: " + e.getMessage());
				}
				
				textIn.setText(inputExpression.toString()); 
	
			}
			
		}
		
	}
	 
}
