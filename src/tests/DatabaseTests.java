package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

	private static final String DB_NAME = "tests.db";

	ExpressionDB db = new ExpressionDB(DB_NAME);

	@BeforeAll
	public void setUp() throws Exception {
		db.init();

		if (!ExpressionDB.dbEnabled) {
			return;
		}

		db.createTable();

	}

	@AfterAll
	public static void tearDown() {
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
		db.updateExpression(id, "x-y", "Test Expression");

		StoredExpression expr = db.getExpressionById(id);
		assertEquals("x - y", expr.getExpStr());
		assertEquals("Test Expression", expr.getExpName());
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
		assertEquals(4, expressions.size());
	}

	@Test
	public void testGetExpressionById() {
		long id = db.insertExpression("x + y");
		StoredExpression expr = db.getExpressionById(id);
		assertEquals("x + y", expr.getExpStr());
	}

}
