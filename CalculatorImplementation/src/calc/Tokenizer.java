/** Class to tokenize string to a list of tokens of operators and operands
*@author Khushbu Patil
*  Ver 1.0: 2017/10/27 
*/

package calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class Tokenizer {

	private static final Logger logger = Logger.getLogger(ExpressionEvaluator.class);
	private static ArrayList<Character> operatorList = new ArrayList<>(Arrays.asList('+', '-', '*', '/', '(', ')'));

	public static List<Token<?>> tokenize(String expr) throws InvalidExpressionException {

		logger.info("Tokenizing  expression: " + expr);

		List<Token<?>> res = new LinkedList<Token<?>>();

		for (int i = 0; i < expr.length();) {

			// If a token is a white space
			if (expr.charAt(i) == ' ')
				i++;

			// If a token is an operator, add it to the list of tokens
			else if (operatorList.contains(expr.charAt(i))) {
				Token<Character> tok = new Token<Character>(expr.charAt(i));
				tok.isOperator = true;
				res.add(tok);
				i++;
			}

			// If a token is an operand, parse accordingly and add it to the operand
			else {
				String temp = getOperand(expr, i);
				// if an operand is a number
				if (isNumeric(temp)) {
					Token<Double> tok = new Token<Double>(Double.valueOf(temp));
					tok.isOperator = false;
					res.add(tok);
				} else {
					throw new InvalidExpressionException("Invalid Characters");
				}

				i = i + temp.length();
			}
		}

		StringBuilder sb = new StringBuilder();
		res.forEach(t -> sb.append(t + " "));

		logger.info("Tokens generated :" + sb);
		return res;
	}

	private static String getOperand(String input, int startIndex) {
		int endIndex = startIndex;
		for (; endIndex < input.length();) {
			if (operatorList.contains(input.charAt(endIndex)) || input.charAt(endIndex) == ' ') {
				break;
			} else
				endIndex++;
		}
		return input.substring(startIndex, endIndex);
	}

	private static boolean isNumeric(String str) {
		return str != null && (str.matches("0") || str.matches("[1-9][0-9]*"));
	}

}
