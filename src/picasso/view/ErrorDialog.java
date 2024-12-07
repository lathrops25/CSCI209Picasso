package picasso.view;

import javax.swing.JOptionPane;

//public class ErrorDialog extends JOptionPane {

/**
 * A simple dialog box to display an error message.
 * 
 * @author Gabriel Hogan
 */
public class ErrorDialog {

	/**
	 * Static method to display an error message dialog.
	 * 
	 * @param message the error message to display
	 */
	public static void showDialog(String message) {
		JOptionPane.showMessageDialog(null, "<html><body><p style='width: 250px;'>" + message + "</p></body></html>",
				"Uh oh! That ain't right!", JOptionPane.ERROR_MESSAGE);
	}

}
