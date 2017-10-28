/** Class to evaluate a infix expression
*@author Khushbu Patil
*  Ver 1.0: 2017/10/27 
*/


package calc;

import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BinaryOperator;

import org.apache.log4j.Logger;

public class ExpressionEvaluator {

	private static final Logger logger = Logger.getLogger(ExpressionEvaluator.class);
	final static DecimalFormat df = new DecimalFormat("#0.00");

	public static void main(String[] args) throws InvalidExpressionException {
		logger.info("Init");

		String expression = getExpression();
		logger.info("Input Expression :" + expression);
		List<Token<?>> tokens = null;

		try {
			tokens = Tokenizer.tokenize(expression);
			if (tokens != null) {
				tokens = ShuntingYard.shuntingYardParser(tokens);
				System.out.println(df.format(evaluateExpression(tokens)));
			}
		} catch (InvalidExpressionException e) {
			logger.error("Exception is::: " + e);
			ExpressionEvaluator.handleInvalidExpression();
		} catch (ArithmeticException e) {
			logger.error("Exception is:::" + e);
			System.out.println("Cannot divide by zero");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("Expression could not be evaluated");
		}
	}

	private static String getExpression() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter expression to be evaluated");
		String expression = s.nextLine();
		if (expression.equals("")) {
			System.out.println("Not a valid expression");
			getExpression();
		}
		s.close();
		return expression;
	}

	public static Double evaluateExpression(List<Token<?>> tokens) throws Exception {
		Stack<Double> operand = new Stack<Double>();

		BinaryOperator<Double> add = (a, b) -> a + b;
		BinaryOperator<Double> mul = (a, b) -> a * b;
		BinaryOperator<Double> div = (a, b) -> a / b;
		BinaryOperator<Double> sub = (a, b) -> a - b;

		Double res = 0.0;

		for (Token<?> t : tokens) {
			Double opnd1;
			Double opnd2;
			String tokenStr = t.toString();
			// t is operand
			if (!t.isOperator)
				operand.push(new Double(t.element.toString()));
			else {
				try {
				opnd2 = operand.pop();
				opnd1 = operand.pop();
				}
				catch(EmptyStackException e) {
					throw new InvalidExpressionException("Invalid Expression");
				}
				logger.info("Evaluating " + opnd1 + " " + tokenStr +" "+ opnd2);
				if (tokenStr.equals("+"))
					res = Calculator.calculate(opnd1, opnd2, add);
				else if (tokenStr.equals("-"))
					res = Calculator.calculate(opnd1, opnd2, sub);
				else if (tokenStr.equals("*"))
					res = Calculator.calculate(opnd1, opnd2, mul);
				else if (tokenStr.equals("/")) {
					if (opnd2 == 0)
						throw new ArithmeticException();
					res = Calculator.calculate(opnd1, opnd2, div);
				} else {
					throw new InvalidExpressionException("Invalid operators");
				}
				operand.push(res);
			}
		}

		if (operand.size() > 1)
			throw new InvalidExpressionException("Stack has unprocessed operands");
		return operand.pop();
	}

	public static void handleInvalidExpression() {
		System.out.println("Not a valid expression");
	}
}
