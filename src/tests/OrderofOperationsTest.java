package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.Multiplication;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.language.expressions.Z;

/**
 * Tests for verifying order of operations in the ExpressionTreeGenerator.
 */
public class OrderofOperationsTest {

    private ExpressionTreeGenerator parser = new ExpressionTreeGenerator();

    @Test
    public void testAdditionAndMultiplicationPrecedence() {
        // Test for "x + y * z" -> x + (y * z)
        ExpressionTreeNode e = parser.makeExpression("x + y * z");
        assertEquals(new Addition(new X(), new Multiplication(new Y(), new Z())), e);
    }

    @Test
    public void testMultiplicationAndAdditionPrecedence() {
        // Test for "x * y + z" -> (x * y) + z
        ExpressionTreeNode e = parser.makeExpression("x * y + z");
        assertEquals(new Addition(new Multiplication(new X(), new Y()), new Z()), e);
    }

    @Test
    public void testParenthesesPrecedence() {
        // Test for "x * (y + z)" -> x * (y + z)
        ExpressionTreeNode e = parser.makeExpression("x * (y + z)");
        assertEquals(new Multiplication(new X(), new Addition(new Y(), new Z())), e);
    }
}
