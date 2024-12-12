package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import picasso.database.ExpressionDB;
import picasso.database.StoredExpression;

/**
 * Tests of the database functionality.
 * 
 * @author Gabriel Hogan
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
public class DatabaseTests {

	private static final String DB_NAME = "test.db";

	private ExpressionDB db;

	@BeforeEach
	public void setUp() throws Exception {
		db = new ExpressionDB(DB_NAME);
		db.init();

		if (!ExpressionDB.dbEnabled) {
			return;
		}

		db.createTable();

	}

	@AfterEach
	public void tearDown() {
		try {
			Files.deleteIfExists(Paths.get(DB_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Add more tests here

	@Test
	public void testInsertExpression() {
		long id = db.insertExpression("x + y");
		List<StoredExpression> expressions = db.getAllExpressions();

		boolean found = false;
		for (StoredExpression expr : expressions) {
			if (expr.getExpId() == id) {
				found = true;
				break;
			}
		}

	}

	@Test
	public void testUpdateExpression() {
		long id = db.insertExpression("x + y");
		db.updateExpression(id, "x - y", "Test Expression");

		StoredExpression expr = db.getExpressionById(id);
		assertEquals("x - y", expr.getExpStr());
		assertEquals("Test Expression", expr.getExpName());
	}

	@Test
	public void testUpdateExpressionWithNoData() {
		long id = db.insertExpression("x + y");
		boolean wasUpdated = db.updateExpression(id, null, null);

		System.out.println("wasUpdated: " + wasUpdated);
		assertEquals(false, wasUpdated);
	}

	@Test
	public void testDeleteExpression() {
		long id = db.insertExpression("x + y");
		db.deleteExpression(id);

		StoredExpression expr = db.getExpressionById(id);
		assertEquals(null, expr);
	}

	@Test
	public void testGetAllExpressions() {
		db.insertExpression("x + y");
		db.insertExpression("x - y");
		db.insertExpression("x * y");
		db.insertExpression("x / y");

		List<StoredExpression> expressions = db.getAllExpressions();

		System.out.println("expressions: " + expressions.size());
		System.out.println("expressions: " + expressions);
		assertEquals(4, expressions.size());
	}

	@Test
	public void testGetExpressionById() {
		long id = db.insertExpression("x + y");
		StoredExpression expr = db.getExpressionById(id);
		assertEquals("x + y", expr.getExpStr());
	}

	@Test
	public void testSQLException() {
		ExpressionDB dbbad = new ExpressionDB("badtest.db");
		dbbad.init();

		// Delete the database file to cause an error
		try {
			Files.deleteIfExists(Paths.get("badtest.db"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertThrows(RuntimeException.class, () -> {
			dbbad.createTable();
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.dropTable();
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.insertExpression("x + y");
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.getAllExpressions();
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.getExpressionById(1);
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.updateExpression(1, "x + y", "Test");
		});
		assertThrows(RuntimeException.class, () -> {
			dbbad.deleteExpression(1);
		});

	}

}
