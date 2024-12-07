package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

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
    
    @Test 
    public void testSubandAdd() {
    	//Test for x - y + x  -> (x-y) + x
    	ExpressionTreeNode e = parser.makeExpression("x - y + x");
    	assertEquals(new Addition(new Subtraction (new X(), new Y()), new X()), e);
    	
    	//Test for x + y - x -> (x+y) - x
    	ExpressionTreeNode e2 = parser.makeExpression("x + y - x");
    	assertEquals(new Subtraction(new Addition (new X(), new Y()), new X()), e2);
    }
    
    @Test
    public void testMultiplicationAndSubtractionPrecedence() {
        // Test for "x * x - x" -> (x * x) - x
        ExpressionTreeNode e = parser.makeExpression("x * x - x");
        assertEquals(new Subtraction(new Multiplication(new X(), new X()), new X()), e);
    }
    
    public void testParenthesesPrecedenceSub() {
        // Test for "x * (x - x)" -> x * (x - x)
        ExpressionTreeNode e = parser.makeExpression("x * (x - x)");
        assertEquals(new Multiplication(new X(), new Subtraction(new X(), new X())), e);
    }
}
