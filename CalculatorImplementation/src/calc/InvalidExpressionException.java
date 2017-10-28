/** Custom Exception class to handle invalid expressions
*@author Khushbu Patil
*  Ver 1.0: 2017/10/27 
*/

package calc;

public class InvalidExpressionException  extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidExpressionException(String message) {
        super(message);
    }
}
