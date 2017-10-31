/** Class for Calculator operations
* 	@author Khushbu Patil
*  	Ver 1.0: 2017/10/27 
*/

package calc;

import java.util.function.BinaryOperator;

public class Calculator {

	public static Double calculate(Double a, Double b, BinaryOperator<Double> operation) {
		return operation.apply(a, b);
	}
}
