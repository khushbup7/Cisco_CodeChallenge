/** Test class
*@author Khushbu Patil
*  Ver 1.0: 2017/10/27 
*/

package test;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import calc.ExpressionEvaluator;
import calc.InvalidExpressionException;
import calc.ShuntingYard;
import calc.Token;
import calc.Tokenizer;

public class ExpressionEvaluatorTest {

	final static DecimalFormat df = new DecimalFormat("#0.00");
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateExpression1() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("88)!/2+3*4");
		ExpressionEvaluator.evaluateExpression(l);
	}

	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("88+33/0");
		l = ShuntingYard.shuntingYardParser(l);
		ExpressionEvaluator.evaluateExpression(l);
	}
	
	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateExpression2() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("(88+33/(78");
		l = ShuntingYard.shuntingYardParser(l);
		ExpressionEvaluator.evaluateExpression(l);
	}

	@Test(expected = ArithmeticException.class)
	public void testEvaluateExpression3() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("897735222222222222222222222222222222222222222222222222222222222222222222222288+33/0");
		l = ShuntingYard.shuntingYardParser(l);
		ExpressionEvaluator.evaluateExpression(l);
	}
	
	@Test(expected = InvalidExpressionException.class)
	public void testEvaluateExpression4() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("783/65+90-21++89*(7/1)+(34 +5/2)");
		l = ShuntingYard.shuntingYardParser(l);
		ExpressionEvaluator.evaluateExpression(l);
	}
	
	@Test
	public void testEvaluateExpression5() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("(783/65+90-21)+89*((7/1)+(34+5/2))");
		l = ShuntingYard.shuntingYardParser(l);
		assertEquals("3952.55", df.format(ExpressionEvaluator.evaluateExpression(l)));
		
	}
	
	@Test
	public void testEvaluateExpression6() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("(1+6+0)");
		l = ShuntingYard.shuntingYardParser(l);
		assertEquals("7.00", df.format(ExpressionEvaluator.evaluateExpression(l)));
		
	}
	
	@Test
	public void testEvaluateExpression7() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("54+21+98-45-67-21*43/43");
		l = ShuntingYard.shuntingYardParser(l);
		assertEquals("40.00", df.format(ExpressionEvaluator.evaluateExpression(l)));
		
	}
	
	@Test
	public void testEvaluateExpression8() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("(99 + (34 * 78 /23) + (21/23) + 21)");
		l = ShuntingYard.shuntingYardParser(l);
		assertEquals("236.22", df.format(ExpressionEvaluator.evaluateExpression(l)));
		
	}
	
	@Test
	public void testEvaluateExpression9() throws Exception {
		List<Token<?>> l = Tokenizer.tokenize("10000/1000 + 1000 - 100 * 10 + 7000");
		l = ShuntingYard.shuntingYardParser(l);
		assertEquals("7010.00", df.format(ExpressionEvaluator.evaluateExpression(l)));
		
	}
}

