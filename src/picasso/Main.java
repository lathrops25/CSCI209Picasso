package picasso;

import java.awt.Dimension;

import picasso.database.DatabaseViewer;
import picasso.database.ExpressionDB;
import picasso.view.ErrorDialog;
import picasso.view.Frame;

/**
 * Starting point for Picasso.
 * 
 * @author Robert Duvall (rcd@cs.duke.edu)
 */
public class Main {
	public static final Dimension SIZE = new Dimension(600, 600);

	public static ExpressionDB db;
	public static DatabaseViewer viewer;

	public static void main(String[] args) {

		try {

			// Initialize the Database
			db = new ExpressionDB();

			if (ExpressionDB.dbEnabled) {
				System.out.println("Database Enabled!");
				db.createTable();
				viewer = new DatabaseViewer();

			} else {
				System.out.println("Database Disabled :(");
			}

			Frame frame = new Frame(SIZE);
			frame.setVisible(true);

			if (ExpressionDB.dbEnabled && viewer != null) {
				viewer.setVisible(true);
			}

		} catch (RuntimeException e) {
			ErrorDialog.showDialog("An error occurred: <br/>" + e.getMessage());
		} catch (Exception e) {
			ErrorDialog.showDialog("An unknown error occurred: <br/>" + e.getMessage());
		}
	}
}
