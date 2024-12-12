package picasso.database;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import picasso.Main;
import picasso.view.Frame;

/**
 * A simple java swing based database viewer for the ExpressionDB.
 * 
 * 
 * @author Gabriel Hogan
 */

@SuppressWarnings("serial")
public class DatabaseViewer extends JFrame {

	private ExpressionDB db;
	private JTable expressionTable;
	private ExpressionTableModel tableModel;
	private JButton openButton;
	private JButton deleteButton;
	private JButton refreshButton;

	/**
	 * Constructor for the DatabaseViewer
	 * 
	 */
	public DatabaseViewer() {
		db = new ExpressionDB();
		db.createTable();

		setTitle("CodeCatalysts - Expression DB Viewer");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		tableModel = new ExpressionTableModel();

		loadData();

		expressionTable = new JTable(tableModel);
		expressionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(expressionTable);

		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});

		openButton = new JButton("Open in New Window");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = expressionTable.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}

				StoredExpression expr = tableModel.getExpressionAt(selectedRow);

				Frame temp = new Frame(Main.SIZE);
				temp.setExpression(expr.getExpStr());
				temp.evaluateInNewPanel(Main.SIZE);
			}
		});

		deleteButton = new JButton("Delete Expression");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = expressionTable.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}

				StoredExpression expr = tableModel.getExpressionAt(selectedRow);

				db.deleteExpression(expr.getExpId());
				loadData();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(refreshButton);
		buttonPanel.add(openButton);
		buttonPanel.add(deleteButton);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * Load the data from the database into the table model
	 */
	public void loadData() {
		List<StoredExpression> expressions = db.getAllExpressions();
		tableModel.setExpressions(expressions);
	}

	/**
	 * Model for the table used on the DatabaseViewer
	 */
	class ExpressionTableModel extends AbstractTableModel {
		private List<StoredExpression> expressions;
		private String[] columnNames = { "Database ID", "Name", "Expression", "Evaluated At" };

		public void setExpressions(List<StoredExpression> expressions) {
			this.expressions = expressions;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return expressions == null ? 0 : expressions.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		public StoredExpression getExpressionAt(int rowIndex) {
			if (expressions == null || rowIndex < 0 || rowIndex >= expressions.size()) {
				return null;
			}
			return expressions.get(rowIndex);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			StoredExpression expr = getExpressionAt(rowIndex);
			if (expr == null)
				return null;
			switch (columnIndex) {
			case 0:
				return expr.getExpId();
			case 1:
				return expr.getExpName();
			case 2:
				return expr.getExpStr();
			case 3:
				return expr.getEvaluatedAt();
			}
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0)
				return Long.class;
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// if the column is the name allow it to be edited
			return columnIndex == 1;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			StoredExpression expr = getExpressionAt(rowIndex);
			if (expr == null)
				return;
			switch (columnIndex) {
			case 1:
				System.out.println("Updating expression name to: " + value);
				db.updateExpression(expr.getExpId(), null, (String) value);
				loadData();
				break;
			}
		}

	}
}