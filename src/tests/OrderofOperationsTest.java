package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.Addition;
import picasso.parser.language.expressions.Multiplication;
import picasso.parser.language.expressions.X;

/**
 * Tests for verifying order of operations in the ExpressionTreeGenerator.
 */
public class OrderofOperationsTest {

    private ExpressionTreeGenerator parser = new ExpressionTreeGenerator();

    @Test
    public void testAdditionAndMultiplicationPrecedence() {
        // Test for "x + x * x" -> x + (x * x)
        ExpressionTreeNode e = parser.makeExpression("x + x * x");
        assertEquals(new Addition(new X(), new Multiplication(new X(), new X())), e);
    }

    @Test
    public void testMultiplicationAndAdditionPrecedence() {
        // Test for "x * x + x" -> (x * x) + x
        ExpressionTreeNode e = parser.makeExpression("x * x + x");
        assertEquals(new Addition(new Multiplication(new X(), new X()), new X()), e);
    }

    @Test
    public void testParenthesesPrecedence() {
        // Test for "x * (x + x)" -> x * (x + x)
        ExpressionTreeNode e = parser.makeExpression("x * (x + x)");
        assertEquals(new Multiplication(new X(), new Addition(new X(), new X())), e);
    }
}
