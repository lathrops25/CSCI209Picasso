package picasso.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import picasso.view.ErrorDialog;

public class ExpressionDB {

	public static boolean dbEnabled = false;

	private static String DB_URL;

	// SQL statements
	private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS EXPRESSION ("
			+ "   EXP_ID INTEGER NOT NULL PRIMARY KEY, " + "   EXP_STR TEXT NOT NULL, " + "   EXP_NAME TEXT NOT NULL, "
			+ "   EVALUATED_AT TEXT NOT NULL" + ");";

	private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS EXPRESSION;";

	private static final String INSERT_SQL = "INSERT INTO EXPRESSION (EXP_STR, EXP_NAME, EVALUATED_AT) VALUES (?, ?, ?);";

	private static final String SELECT_ALL_SQL = "SELECT * FROM EXPRESSION;";
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM EXPRESSION WHERE EXP_ID = ?;";
	private static final String UPDATE_SQL_START = "UPDATE EXPRESSION SET ";
	private static final String UPDATE_SQL_END = " WHERE EXP_ID = ?;";
	private static final String DELETE_SQL = "DELETE FROM EXPRESSION WHERE EXP_ID = ?;";

	/**
	 * Constructor that creates a new database with the given name.
	 * 
	 * @param dbName Database file name.
	 */
	public ExpressionDB(String dbName) {
		DB_URL = "jdbc:sqlite:file:" + dbName;

		try (Connection conn = DriverManager.getConnection(DB_URL + "?mode=rwc")) {
			// Do nothing, the try is enough
		} catch (SQLException e) {
			throw new RuntimeException("There was an error while creating or reading the database ", e);
		} finally {
			DB_URL = DB_URL + "?mode=rw";
			init();
		}
	}

	/**
	 * Default constructor that creates a new database with the default name.
	 */
	public ExpressionDB() {
		this("expression.db");
	}

	public void init() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			dbEnabled = false;
			ErrorDialog.showDialog(
					"You are missing the required library to run the database. Please add the JAR file contained in the lib directory to your classpath. You can also safely close this message to use the app without database functionality.");
			return;
		}
		dbEnabled = true;
	}

	/**
	 * Creates the EXPRESSION table if it does not exist.
	 * 
	 * @throws RuntimeException if there was an error creating the table.
	 */
	public void createTable() {
		try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
			stmt.execute(CREATE_TABLE_SQL);
		} catch (SQLException e) {
			throw new RuntimeException("There was an error creating the db table: ", e);
		}
	}

	/**
	 * Drops the EXPRESSION table if it exists.
	 * 
	 * @throws RuntimeException if there was an error dropping the table.
	 */
	public void dropTable() {
		try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
			stmt.execute(DROP_TABLE_SQL);
		} catch (SQLException e) {
			throw new RuntimeException("There was an error dropping the db table: ", e);
		}
	}

	/**
	 * Inserts a new expression with only the expression string
	 * 
	 * @param expStr Expression string.
	 * @return The newly inserted row's ID or -1 on failure.
	 * @throws RuntimeException if there was an error inserting the expression.
	 */
	public long insertExpression(String expStr) {
		return insertExpression(expStr, expStr);
	}

	/**
	 * Inserts a new expression.
	 * 
	 * @param expStr  Expression string.
	 * @param expName Expression name. current UTC time.
	 * @return The newly inserted row's ID or Error on failure.
	 * @throws RuntimeException if there was an error inserting the expression.
	 */
	public long insertExpression(String expStr, String expName) {
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, expStr);
			pstmt.setString(2, expName);
			pstmt.setString(3, Instant.now().toString());

			pstmt.executeUpdate();

			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getLong(1);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("There was an error while inserting an expression to the db: ", e);
		}

		return -1;
	}

	/**
	 * Retrieves all expressions.
	 * 
	 * @return A list of Expression objects
	 * @throws RuntimeException if there was an error retrieving the expressions.
	 */
	public List<StoredExpression> getAllExpressions() {
		List<StoredExpression> expressions = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				expressions.add(new StoredExpression(rs.getLong("EXP_ID"), rs.getString("EXP_STR"),
						rs.getString("EXP_NAME"), rs.getString("EVALUATED_AT")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("There was an error while retrieving the expressions from the db: ", e);
		}

		return expressions;
	}

	/**
	 * Retrieves a single expression by its ID.
	 * 
	 * @param expId Expression ID
	 * @return The Expression or null if not found.
	 * @throws RuntimeException if there was an error retrieving the expression.
	 */
	public StoredExpression getExpressionById(long expId) {
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

			pstmt.setLong(1, expId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new StoredExpression(rs.getLong("EXP_ID"), rs.getString("EXP_STR"), rs.getString("EXP_NAME"),
							rs.getString("EVALUATED_AT"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("There was an error while retrieving the expression from the db: ", e);
		}
		return null;
	}

	/**
	 * Updates an expression by its ID. Only updates fields that are not null.
	 * 
	 * @param expId   Expression ID to update.
	 * @param expStr  New expression string or null to leave unchanged.
	 * @param expName New expression name or null to leave unchanged.
	 * @return true if updated, false otherwise.
	 * @throws RuntimeException if there was an error updating the expression.
	 */
	public boolean updateExpression(long expId, String expStr, String expName) {
		StringBuilder sb = new StringBuilder(UPDATE_SQL_START);
		List<Object> values = new ArrayList<>();

		if (expStr != null) {
			sb.append("EXP_STR = ?, ");
			values.add(expStr);
		}
		if (expName != null) {
			sb.append("EXP_NAME = ?, ");
			values.add(expName);
		}

		// If no fields to update, return early
		if (values.isEmpty()) {
			return false;
		}

		// Remove last comma and space
		sb.setLength(sb.length() - 2);
		sb.append(UPDATE_SQL_END);

		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {

			int index = 1;
			for (Object val : values) {
				pstmt.setObject(index++, val);
			}
			pstmt.setLong(index, expId);

			int affectedRows = pstmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			throw new RuntimeException("There was an error while updating the expression in the db: ", e);
		}

	}

	/**
	 * Deletes an expression by its ID.
	 * 
	 * @param expId Expression ID to delete.
	 * @throws RuntimeException if there was an error deleting the expression.
	 */
	public void deleteExpression(long expId) {
		try (Connection conn = DriverManager.getConnection(DB_URL);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {

			pstmt.setLong(1, expId);
			pstmt.executeUpdate();
			return;
		} catch (SQLException e) {
			throw new RuntimeException("There was an error while deleting the expression from the db: ", e);
		}

	}
}
